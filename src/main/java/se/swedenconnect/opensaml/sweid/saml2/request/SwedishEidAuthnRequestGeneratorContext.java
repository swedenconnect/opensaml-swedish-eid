/*
 * Copyright 2021-2023 Sweden Connect
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
package se.swedenconnect.opensaml.sweid.saml2.request;

import java.util.function.BiFunction;
import java.util.function.Supplier;

import org.opensaml.saml.saml2.metadata.EntityDescriptor;

import se.swedenconnect.opensaml.saml2.request.AuthnRequestGeneratorContext;
import se.swedenconnect.opensaml.sweid.saml2.authn.psc.PrincipalSelection;
import se.swedenconnect.opensaml.sweid.saml2.signservice.SignMessageEncrypter;
import se.swedenconnect.opensaml.sweid.saml2.signservice.dss.SignMessage;

/**
 * An extension to {@code AuthnRequestGeneratorContext} with added functionality for the Swedish eID Framework.
 *
 * @author Martin Lindström (martin@idsec.se)
 */
public interface SwedishEidAuthnRequestGeneratorContext extends AuthnRequestGeneratorContext {

  /**
   * Gets the {@link SignMessageBuilderFunction} that is used to build {@code SignMessage} extensions.
   * <p>
   * The default implementation does not create a {@code SignMessage} extension.
   * </p>
   *
   * @return the builder function
   */
  default SignMessageBuilderFunction getSignMessageBuilderFunction() {
    return (m, e) -> null;
  }

  /**
   * Gets the {@link PrincipalSelectionBuilderFunction} that is used to build {@code PrincipalSelection} extensions.
   * <p>
   * The default implementation does not create a {@code PrincipalSelection} extension.
   * </p>
   *
   * @return the builder function
   */
  default PrincipalSelectionBuilderFunction getPrincipalSelectionBuilderFunction() {
    return () -> null;
  }

  /**
   * If the SP is a signature service, the generator will invoke the
   * {@link SwedishEidAuthnRequestGeneratorContext#getSignMessageBuilderFunction()} method in order to obtain a builder
   * of a {@code SignMessage} object. The builder function is supplied with the recipient metadata and may be supplied
   * with a {@link SignMessageEncrypter} instance. If not, encrypted messages can not be created.
   */
  public interface SignMessageBuilderFunction extends BiFunction<EntityDescriptor, SignMessageEncrypter, SignMessage> {
  }

  /**
   * The generator will invoke the {@link SwedishEidAuthnRequestGeneratorContext#getPrincipalSelectionBuilderFunction()}
   * in order to obtain a builder function that creates the {@code PrincipalSelection} extension.
   */
  public interface PrincipalSelectionBuilderFunction extends Supplier<PrincipalSelection> {
  }
}
