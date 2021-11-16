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

import org.opensaml.saml.common.assertion.AssertionValidationException;
import org.opensaml.saml.common.assertion.ValidationContext;
import org.opensaml.saml.common.assertion.ValidationResult;
import org.opensaml.saml.saml2.assertion.SAML2AssertionValidationParameters;
import org.opensaml.saml.saml2.assertion.impl.BearerSubjectConfirmationValidator;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.AuthnRequest;
import org.opensaml.saml.saml2.core.SubjectConfirmation;

import se.swedenconnect.opensaml.common.validation.CoreValidatorParameters;

/**
 * A subject confirmation validator compliant with the Swedish eID Framework.
 * 
 * @author Martin Lindström (martin@idsec.se)
 */
public class SwedishEidSubjectConfirmationValidator extends BearerSubjectConfirmationValidator {

  /**
   * For backwards compatibility we also check for the {@link CoreValidatorParameters#AUTHN_REQUEST_ID} and
   * {@link CoreValidatorParameters#AUTHN_REQUEST}.
   */
  protected ValidationResult validateInResponseTo(final SubjectConfirmation confirmation, final Assertion assertion, 
      final ValidationContext context, final boolean required) throws AssertionValidationException {
    
    if (context.getStaticParameters().get(SAML2AssertionValidationParameters.SC_VALID_IN_RESPONSE_TO) == null) {
      String expectedInResponseTo = (String) context.getStaticParameters().get(CoreValidatorParameters.AUTHN_REQUEST_ID);
      if (expectedInResponseTo == null) {
        final AuthnRequest authnRequest = (AuthnRequest) context.getStaticParameters().get(CoreValidatorParameters.AUTHN_REQUEST);
        if (authnRequest != null) {
          expectedInResponseTo = authnRequest.getID();
        }
      }
      if (expectedInResponseTo != null) {
        context.getStaticParameters().put(SAML2AssertionValidationParameters.SC_VALID_IN_RESPONSE_TO, expectedInResponseTo);
      }
    }    
    return super.validateInResponseTo(confirmation, assertion, context, required);
  }

  /** {@inheritDoc} */
  @Override
  protected boolean isAddressRequired(final ValidationContext context) {
    final boolean flag = super.isAddressRequired(context);
    if (!flag) {
      if (context.getStaticParameters().get(SAML2AssertionValidationParameters.SC_VALID_ADDRESSES) == null) {
        // Nothing to compare with ...
        return false;
      }
    }
    return flag;
  }

  /**
   * Always returns {@code true} - It is required by the Swedish eID Framework.
   */
  @Override
  protected boolean isRecipientRequired(final ValidationContext context) {
    return true;
  }

  /**
   * Always returns {@code true} - It is required by the Swedish eID Framework.
   */
  @Override
  protected boolean isNotOnOrAfterRequired(final ValidationContext context) {
    return true;
  }

  /**
   * Always returns {@code true} - It is required by the Swedish eID Framework.
   */
  @Override
  protected boolean isInResponseToRequired(final ValidationContext context) {
    return true;
  }

}
