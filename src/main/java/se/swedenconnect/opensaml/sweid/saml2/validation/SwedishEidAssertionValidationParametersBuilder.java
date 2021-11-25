/*
 * Copyright 2021 Sweden Connect
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

import org.opensaml.saml.common.assertion.ValidationContext;
import org.opensaml.saml.saml2.assertion.SAML2AssertionValidationParameters;
import org.opensaml.saml.saml2.core.AuthnRequest;

import se.swedenconnect.opensaml.common.validation.CoreValidatorParameters;
import se.swedenconnect.opensaml.saml2.assertion.validation.AbstractAssertionValidationParametersBuilder;
import se.swedenconnect.opensaml.sweid.saml2.attribute.AttributeConstants;

/**
 * An extension to {@link AbstractAssertionValidationParametersBuilder} setting defaults according to the Swedish eID
 * Framework.
 * 
 * @author Martin Lindström (martin@idsec.se)
 */
public class SwedishEidAssertionValidationParametersBuilder
    extends AbstractAssertionValidationParametersBuilder<SwedishEidAssertionValidationParametersBuilder> {

  /**
   * Utility method that returns a builder instance.
   * 
   * @return a builder
   */
  public static SwedishEidAssertionValidationParametersBuilder builder() {
    return new SwedishEidAssertionValidationParametersBuilder();
  }

  /** {@inheritDoc} */
  @Override
  public ValidationContext build() {
    this.addStaticParameterIfMissing(SAML2AssertionValidationParameters.SC_RECIPIENT_REQUIRED, Boolean.TRUE);
    this.addStaticParameterIfMissing(SAML2AssertionValidationParameters.SC_NOT_ON_OR_AFTER_REQUIRED, Boolean.TRUE);
    this.addStaticParameterIfMissing(SAML2AssertionValidationParameters.SC_IN_RESPONSE_TO_REQUIRED, Boolean.TRUE);
    final Boolean addressRequired = (Boolean) this.getStaticParameter(SAML2AssertionValidationParameters.SC_ADDRESS_REQUIRED);
    if (addressRequired != null && addressRequired.booleanValue()) {
      if (this.getStaticParameter(SAML2AssertionValidationParameters.SC_VALID_ADDRESSES) == null) {
        // Nothing to compare with ...
        this.addStaticParameter(SAML2AssertionValidationParameters.SC_ADDRESS_REQUIRED, Boolean.FALSE);
      }
    }
    // For backwards compatibility we also check for the CoreValidatorParameters.AUTHN_REQUEST_ID and
    // CoreValidatorParameters.AUTHN_REQUEST.
    //
    if (this.getStaticParameter(SAML2AssertionValidationParameters.SC_VALID_IN_RESPONSE_TO) == null) {
      String expectedInResponseTo = (String) this.getStaticParameter(CoreValidatorParameters.AUTHN_REQUEST_ID);
      if (expectedInResponseTo == null) {
        final AuthnRequest authnRequest = (AuthnRequest) this.getStaticParameter(CoreValidatorParameters.AUTHN_REQUEST);
        if (authnRequest != null) {
          expectedInResponseTo = authnRequest.getID();
        }
      }
      if (expectedInResponseTo != null) {
        this.addStaticParameter(SAML2AssertionValidationParameters.SC_VALID_IN_RESPONSE_TO, expectedInResponseTo);
      }
    }
    this.addStaticParameterIfMissing(SwedishEidAttributeStatementValidator.SCOPED_ATTRIBUTES,
      Collections.singletonList(AttributeConstants.ATTRIBUTE_NAME_ORG_AFFILIATION));
    

    return super.build();
  }

  public SwedishEidAssertionValidationParametersBuilder scopedAttributes(final Collection<String> attributes) {
    return this.staticParameter(SwedishEidAttributeStatementValidator.SCOPED_ATTRIBUTES, attributes);
  }

  /** {@inheritDoc} */
  @Override
  protected SwedishEidAssertionValidationParametersBuilder getThis() {
    return this;
  }

}
