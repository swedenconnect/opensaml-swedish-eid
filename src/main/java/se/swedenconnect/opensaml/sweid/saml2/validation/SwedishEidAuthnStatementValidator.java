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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.opensaml.saml.common.assertion.ValidationContext;
import org.opensaml.saml.common.assertion.ValidationResult;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.AuthnRequest;
import org.opensaml.saml.saml2.core.AuthnStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.swedenconnect.opensaml.common.validation.CoreValidatorParameters;
import se.swedenconnect.opensaml.saml2.assertion.validation.AuthnStatementValidator;

/**
 * An {@link AuthnStatementValidator} that performs checks to assert that the assertion is compliant with the Swedish
 * eID Framework.
 * 
 * <p>
 * Supports the following {@link ValidationContext} static parameters:
 * </p>
 * <ul>
 * <li>The ones defined in {@link AuthnStatementValidator}.</li>
 * <li>{@link #AUTHN_REQUEST_REQUESTED_AUTHNCONTEXTURIS}: Holds a collection of AuthnContext URIs that are matched
 * against the {@code AuthnContextClassRef} element of the authentication statement. If not supplied, the values are
 * read from {@link CoreValidatorParameters#AUTHN_REQUEST}.</li>
 * </ul>
 * 
 * @author Martin Lindström (martin@idsec.se)
 */
public class SwedishEidAuthnStatementValidator extends AuthnStatementValidator {

  /**
   * Key for a validation context parameter. Carries a {@link Collection} holding the requested AuthnContext URIs
   * included in the {@code AuthnRequest}.
   */
  public static final String AUTHN_REQUEST_REQUESTED_AUTHNCONTEXTURIS = CoreValidatorParameters.STD_PREFIX
      + ".AuthnRequestRequestedAuthnContextURIs";

  /** Class logger. */
  private final Logger log = LoggerFactory.getLogger(SwedishEidAuthnStatementValidator.class);

  /**
   * Overrides default implementation with checks that assert that a {@code AuthnContextClassRef} URI was received, and
   * that it matches what was requested.
   */
  @Override
  protected ValidationResult validateAuthnContext(final AuthnStatement statement, final Assertion assertion, final ValidationContext context) {
    final ValidationResult res = super.validateAuthnContext(statement, assertion, context);
    if (res != ValidationResult.VALID) {
      return res;
    }
    if (statement.getAuthnContext().getAuthnContextClassRef() == null
        || statement.getAuthnContext().getAuthnContextClassRef().getURI() == null) {
      context.setValidationFailureMessage("Missing AuthnContextClassRef URI from Assertion/@AuthnStatement/@AuthnContext");
      return ValidationResult.INVALID;
    }

    final Collection<String> requestedUris = getRequestedAuthnContextUris(context);
    if (requestedUris.isEmpty()) {
      log.debug("No RequestedAuthnContext URI was requested - will not check issued AuthnContextClassRef");
      return ValidationResult.VALID;
    }
    return this.validateAuthnContextClassRef(
      statement.getAuthnContext().getAuthnContextClassRef().getURI(), requestedUris, statement, assertion, context);
  }

  /**
   * Checks the issued AuthnContextClassRef against the ones that were requested. This method assumes "exact" matching.
   * 
   * @param authnContextClassRef
   *          the AuthnContextClassRef from the assertion
   * @param requestedContextClassRefs
   *          the requested levels
   * @param statement
   *          the authentication statement
   * @param assertion
   *          the assertion
   * @param context
   *          the validation context
   * @return validation result
   */
  protected ValidationResult validateAuthnContextClassRef(
      final String authnContextClassRef, final Collection<String> requestedContextClassRefs,
      final AuthnStatement statement, final Assertion assertion, final ValidationContext context) {
    
    if (!requestedContextClassRefs.contains(authnContextClassRef)) {
      final String msg = String.format("Assertion contained AuthnContextClassRef '%s', but that one was not requested (%s)", 
        authnContextClassRef, requestedContextClassRefs);
      context.setValidationFailureMessage(msg);
      return ValidationResult.INVALID;
    }    
    return ValidationResult.VALID;
  }

  /**
   * Returns a collection of URIs that are the RequestedAuthnContext URIs given in the {@code AuthnRequest}. The method
   * will first check if the parameter {@link #AUTHN_REQUEST_REQUESTED_AUTHNCONTEXTURIS} is set, and if not, use the
   * {@link CoreValidatorParameters#AUTHN_REQUEST}.
   * 
   * @param context
   *          the validation context
   * @return a collection of URIs.
   */
  @SuppressWarnings("unchecked")
  protected static Collection<String> getRequestedAuthnContextUris(final ValidationContext context) {
    Collection<String> uris = (Collection<String>) context.getStaticParameters().get(AUTHN_REQUEST_REQUESTED_AUTHNCONTEXTURIS);
    if (uris == null || uris.isEmpty()) {
      final AuthnRequest authnRequest = (AuthnRequest) context.getStaticParameters().get(CoreValidatorParameters.AUTHN_REQUEST);
      if (authnRequest != null && authnRequest.getRequestedAuthnContext() != null
          && authnRequest.getRequestedAuthnContext().getAuthnContextClassRefs() != null) {
        if (!authnRequest.getRequestedAuthnContext().getAuthnContextClassRefs().isEmpty()) {
          uris = new ArrayList<String>();
          uris.addAll(authnRequest.getRequestedAuthnContext()
            .getAuthnContextClassRefs()
            .stream()
            .map(a -> a.getURI())
            .collect(Collectors.toList()));
        }
      }
    }
    return uris != null ? uris : Collections.emptyList();
  }

}
