/*
 * Copyright 2016-2025 Sweden Connect
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
package se.swedenconnect.opensaml.sweid.saml2.signservice.build;

import org.opensaml.core.xml.util.XMLObjectSupport;

import se.swedenconnect.opensaml.common.builder.AbstractSAMLObjectBuilder;
import se.swedenconnect.opensaml.sweid.saml2.signservice.SignMessageEncrypter;
import se.swedenconnect.opensaml.sweid.saml2.signservice.dss.Message;
import se.swedenconnect.opensaml.sweid.saml2.signservice.dss.SignMessage;
import se.swedenconnect.opensaml.sweid.saml2.signservice.dss.SignMessageMimeTypeEnum;

/**
 * Creates a {@link SignMessage} instance using the builder patterns.
 *
 * @author Martin Lindström (martin@idsec.se)
 */
public class SignMessageBuilder extends AbstractSAMLObjectBuilder<SignMessage> {

  /**
   * Utility method that creates a builder.
   *
   * @return a builder
   */
  public static SignMessageBuilder builder() {
    return new SignMessageBuilder();
  }

  /**
   * Assigns the message to include.
   * <p>
   * If the message should be encrypted, use {@link SignMessageEncrypter} after the {@code SignMessage} has been built.
   * </p>
   *
   * @param message the message to include (in cleartext)
   * @return the builder
   */
  public SignMessageBuilder message(final String message) {
    final Message msg = (Message) XMLObjectSupport.buildXMLObject(Message.DEFAULT_ELEMENT_NAME);
    msg.setContent(message);
    this.object().setMessage(msg);
    return this;
  }

  /**
   * Assigns the entityID of the entity responsible for displaying the sign message to the signer. When the sign message
   * is encrypted, then this entity is also the holder of the private decryption key necessary to decrypt the sign
   * message.
   *
   * @param displayEntity the entityID of the recipient
   * @return the builder
   */
  public SignMessageBuilder displayEntity(final String displayEntity) {
    this.object().setDisplayEntity(displayEntity);
    return this;
  }

  /**
   * Assigns the MIME type of the message.
   *
   * @param mimeType the MIME type
   * @return the builder
   */
  public SignMessageBuilder mimeType(final SignMessageMimeTypeEnum mimeType) {
    this.object().setMimeType(mimeType);
    return this;
  }

  /**
   * Assigns the {@code MustShow} attribute. When this parameter is set to {@code true} then the requested signature
   * MUST NOT be created unless this message has been displayed and accepted by the signer.
   *
   * @param mustShow the must show flag
   * @return the builder
   */
  public SignMessageBuilder mustShow(final Boolean mustShow) {
    this.object().setMustShow(mustShow);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  protected Class<SignMessage> getObjectType() {
    return SignMessage.class;
  }

}
