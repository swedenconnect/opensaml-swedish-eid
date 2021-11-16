/*
 * Copyright 2016-2021 Sweden Connect
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

import org.opensaml.saml.common.assertion.ValidationContext;
import org.opensaml.saml.common.assertion.ValidationResult;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.Attribute;
import org.opensaml.saml.saml2.core.AttributeStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.swedenconnect.opensaml.common.validation.CoreValidatorParameters;
import se.swedenconnect.opensaml.saml2.assertion.validation.AbstractAttributeStatementValidator;
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

  /** Class logger. */
  private final Logger log = LoggerFactory.getLogger(SwedishEidAttributeStatementValidator.class);

  /**
   * Validates that the required attributes have been received by using the optional context parameter
   * {@link #REQUIRED_ATTRIBUTE_SET} that holds an {@link AttributeSet} and/or the list of attribute names from the
   * parameter {@link SwedishEidAttributeStatementValidator#REQUIRED_ATTRIBUTES}.
   */
  @Override
  protected ValidationResult validateRequiredAttributes(final List<Attribute> attributes, final AttributeStatement statement,
      final Assertion assertion, ValidationContext context) {

    final AttributeSet attributeSet = (AttributeSet) context.getStaticParameters().get(REQUIRED_ATTRIBUTE_SET);
    if (attributeSet != null) {
      try {
        attributeSet.validateAttributes(assertion, null);
      }
      catch (AttributesValidationException e) {
        log.info("Required attributes check failed: {}", e.getMessage());
        context.setValidationFailureMessage(e.getMessage());
        return ValidationResult.INVALID;
      }
    }

    final Collection<String> requiredAttributes = this.getRequiredAttributes(context);
    if (requiredAttributes != null) {
      for (String attr : requiredAttributes) {
        if (!attributes.stream().filter(a -> attr.equals(a.getName())).findAny().isPresent()) {
          final String msg = String.format("Required attribute '%s' was not part of the attribute statement", attr);
          log.info("Required attributes check failed: {}", msg);
          context.setValidationFailureMessage(msg);
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
   * @param context
   *          the validation context
   * @return a collection of attribute names (never {@code null})
   */
  protected Collection<String> getRequiredAttributes(final ValidationContext context) {
    @SuppressWarnings("unchecked")
    final Collection<String> attributes = (Collection<String>) context.getStaticParameters().get(REQUIRED_ATTRIBUTES);
    return attributes != null ? attributes : Collections.emptyList();
  }
}
