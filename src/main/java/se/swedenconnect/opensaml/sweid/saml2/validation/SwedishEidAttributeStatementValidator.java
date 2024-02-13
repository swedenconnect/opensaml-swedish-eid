/*
 * Copyright 2016-2024 Sweden Connect
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package se.swedenconnect.opensaml.sweid.saml2.validation;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.assertion.AssertionValidationException;
import org.opensaml.saml.common.assertion.ValidationContext;
import org.opensaml.saml.common.assertion.ValidationResult;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.Attribute;
import org.opensaml.saml.saml2.core.AttributeStatement;
import org.opensaml.saml.saml2.core.Statement;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.swedenconnect.opensaml.common.validation.CoreValidatorParameters;
import se.swedenconnect.opensaml.saml2.assertion.validation.AbstractAttributeStatementValidator;
import se.swedenconnect.opensaml.saml2.attribute.AttributeUtils;
import se.swedenconnect.opensaml.saml2.metadata.scope.ScopeUtils;
import se.swedenconnect.opensaml.sweid.saml2.attribute.AttributeSet;
import se.swedenconnect.opensaml.sweid.saml2.attribute.AttributesValidationException;

/**
 * Validator for {@link AttributeStatement}s.
 *
 * <p>
 * Supports the following {@link ValidationContext} static parameters:
 * </p>
 * <ul>
 * <li>{@link #REQUIRED_ATTRIBUTE_SET}: Optional. Holds a {@link AttributeSet} that tells which attributes we require to
 * find in the assertion.</li>
 * <li>{@link #REQUIRED_ATTRIBUTES}: Optional. Holds a collection of strings that are the attribute names that we
 * require to find in the assertion.</li>
 * <li>{@link #SCOPED_ATTRIBUTES}: Optional. Carries a {@link Collection} of strings holding attribute names of
 * requested attributes.</li>
 * </ul>
 *
 * <p>
 * Note that the two above parameters may be combined. If no parameter for requested attributes is passed, no validation
 * will be performed.
 * </p>
 *
 * @author Martin Lindström (martin@idsec.se)
 */
public class SwedishEidAttributeStatementValidator extends AbstractAttributeStatementValidator {

  /**
   * Key for a validation context parameter. Carries a {@link AttributeSet} holding the required attributes.
   */
  public static final String REQUIRED_ATTRIBUTE_SET = CoreValidatorParameters.STD_PREFIX + ".RequiredAttributeSet";

  /**
   * Key for a validation context parameter. Carries a {@link Collection} of strings holding attribute names of
   * requested attributes.
   */
  public static final String REQUIRED_ATTRIBUTES = CoreValidatorParameters.STD_PREFIX + ".RequiredAttributes";

  /**
   * Key for a validation context parameter. Carries a {@link Collection} of strings holding the attribute names for
   * attributes that are "scoped".
   */
  public static final String SCOPED_ATTRIBUTES = CoreValidatorParameters.STD_PREFIX + ".ScopedAttributes";

  /** Class logger. */
  private static final Logger log = LoggerFactory.getLogger(SwedishEidAttributeStatementValidator.class);

  /** {@inheritDoc} */
  @Override
  public ValidationResult validate(final Statement statement, final Assertion assertion,
      final ValidationContext context)
      throws AssertionValidationException {

    final ValidationResult result = super.validate(statement, assertion, context);
    if (result != ValidationResult.VALID) {
      return result;
    }

    final AttributeStatement attributeStatement = (AttributeStatement) statement;
    final List<Attribute> attributes =
        Optional.ofNullable(AttributeStatement.class.cast(statement).getAttributes())
            .orElse(Collections.emptyList());

    return this.validateScopedAttributes(attributes, attributeStatement, assertion, context);
  }

  /**
   * Validates that the required attributes have been received by using the optional context parameter
   * {@link #REQUIRED_ATTRIBUTE_SET} that holds an {@link AttributeSet} and/or the list of attribute names from the
   * parameter {@link SwedishEidAttributeStatementValidator#REQUIRED_ATTRIBUTES}.
   */
  @Override
  protected ValidationResult validateRequiredAttributes(final List<Attribute> attributes,
      final AttributeStatement statement,
      final Assertion assertion, final ValidationContext context) {

    final AttributeSet attributeSet = (AttributeSet) context.getStaticParameters().get(REQUIRED_ATTRIBUTE_SET);
    if (attributeSet != null) {
      try {
        attributeSet.validateAttributes(assertion, null);
      }
      catch (final AttributesValidationException e) {
        log.info("Required attributes check failed: {}", e.getMessage());
        context.getValidationFailureMessages().add(e.getMessage());
        return ValidationResult.INVALID;
      }
    }

    final Collection<String> requiredAttributes = this.getRequiredAttributes(context);
    if (requiredAttributes != null) {
      for (final String attr : requiredAttributes) {
        if (!attributes.stream().filter(a -> attr.equals(a.getName())).findAny().isPresent()) {
          final String msg = String.format("Required attribute '%s' was not part of the attribute statement", attr);
          log.info("Required attributes check failed: {}", msg);
          context.getValidationFailureMessages().add(msg);
          return ValidationResult.INVALID;
        }
      }
    }

    if (attributeSet == null && requiredAttributes.isEmpty()) {
      log.debug("No required attributes were supplied - can not check if required attributes were delivered");
    }

    return ValidationResult.VALID;
  }

  /**
   * Returns the required attributes.
   *
   * @param context the validation context
   * @return a collection of attribute names (never {@code null})
   */
  protected Collection<String> getRequiredAttributes(final ValidationContext context) {
    @SuppressWarnings("unchecked")
    final Collection<String> attributes = (Collection<String>) context.getStaticParameters().get(REQUIRED_ATTRIBUTES);
    return attributes != null ? attributes : Collections.emptyList();
  }

  /**
   * Validates that the issuing IdP has been authorized to issue scoped attributes.
   *
   * @param attributes a list of the attributes
   * @param statement the statement
   * @param assertion the assertion
   * @param context the validation context
   * @return a validation result
   */
  protected ValidationResult validateScopedAttributes(final List<Attribute> attributes,
      final AttributeStatement statement,
      final Assertion assertion, final ValidationContext context) {

    @SuppressWarnings("unchecked")
    final Collection<String> scopedAttributes =
        (Collection<String>) context.getStaticParameters().get(SCOPED_ATTRIBUTES);
    if (scopedAttributes == null || scopedAttributes.isEmpty()) {
      // Nothing to check
      return ValidationResult.VALID;
    }

    final List<Attribute> attributesToCheck = attributes.stream()
        .filter(a -> a.getName() != null && scopedAttributes.contains(a.getName()))
        .collect(Collectors.toList());
    if (attributesToCheck.isEmpty()) {
      // No attributes to check ...
      return ValidationResult.VALID;
    }

    // For the check we need the IdP metadata ...
    //
    final EntityDescriptor idpMetadata =
        (EntityDescriptor) context.getStaticParameters().get(CoreValidatorParameters.IDP_METADATA);
    if (idpMetadata == null) {
      final String msg = String.format("Could not check scoped attributes. '%s' parameter is missing",
          CoreValidatorParameters.IDP_METADATA);
      log.debug(msg);
      context.getValidationFailureMessages().add(msg);
      return ValidationResult.INDETERMINATE;
    }

    final List<XMLObject> authorizedScopes = ScopeUtils.getScopeExtensions(idpMetadata);
    for (final Attribute scopedAttribute : attributesToCheck) {
      if (!ScopeUtils.isAuthorized(scopedAttribute, authorizedScopes)) {
        final String msg = String.format("IdP '%s' is not authorized to issue scoped attribute '%s' for domain '%s'",
            idpMetadata.getEntityID(), scopedAttribute.getName(),
            ScopeUtils.getScopedDomain(AttributeUtils.getAttributeStringValue(scopedAttribute)));
        log.debug(msg);
        context.getValidationFailureMessages().add(msg);
        return ValidationResult.INVALID;
      }
    }

    return ValidationResult.VALID;
  }
}
