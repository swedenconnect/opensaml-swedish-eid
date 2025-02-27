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
package se.swedenconnect.opensaml.sweid.saml2.signservice;

import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.xmlsec.EncryptionConfiguration;
import org.opensaml.xmlsec.encryption.EncryptedData;
import org.opensaml.xmlsec.encryption.support.EncryptionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.shibboleth.shared.component.ComponentInitializationException;
import net.shibboleth.shared.logic.Constraint;
import se.swedenconnect.opensaml.sweid.saml2.signservice.dss.EncryptedMessage;
import se.swedenconnect.opensaml.sweid.saml2.signservice.dss.SignMessage;
import se.swedenconnect.opensaml.xmlsec.encryption.support.SAMLObjectEncrypter;

/**
 * Bean for encrypting {@code SignMessage} objects.
 *
 * @author Martin Lindström (martin@idsec.se)
 */
public class SignMessageEncrypter {

  /** Logger instance. */
  private Logger log = LoggerFactory.getLogger(SignMessageEncrypter.class);

  /** The encrypter to use. */
  private final SAMLObjectEncrypter encrypter;

  /**
   * Constructor.
   *
   * @param encrypter the encrypter bean
   * @throws ComponentInitializationException for init errors
   */
  public SignMessageEncrypter(final SAMLObjectEncrypter encrypter) throws ComponentInitializationException {
    this.encrypter = Constraint.isNotNull(encrypter, "encrypter must not be null");
  }

  /**
   * Given a sign message holding a cleartext {@code Message} element, the method encrypts the message using the default
   * encryption configuration and updates the supplied {@code signMessage} so that it holds an {@code EncryptedMessage}
   * instead.
   *
   * @param signMessage the sign message holding the message to encrypt
   * @param entityID the SAML entityID of the IdP that is the recipient of the message (and to whom we encrypt for)
   * @throws EncryptionException for errors during encryption
   * @see #encrypt(SignMessage, String, EncryptionConfiguration)
   */
  public void encrypt(final SignMessage signMessage, final String entityID) throws EncryptionException {
    this.encrypt(signMessage, entityID, null);
  }

  /**
   * Given a sign message holding a cleartext {@code Message} element, the method encrypts the message using the
   * supplied encryption configuration and updates the supplied {@code signMessage} so that it holds an
   * {@code EncryptedMessage} instead.
   *
   * @param signMessage the sign message holding the message to encrypt
   * @param entityID the SAML entityID of the IdP that is the recipient of the message (and to whom we encrypt for)
   * @param configuration the encryption configuration to use
   * @throws EncryptionException for errors during encryption
   */
  public void encrypt(final SignMessage signMessage, final String entityID, final EncryptionConfiguration configuration)
      throws EncryptionException {

    Constraint.isNotNull(signMessage, "signMessage must not be null");
    Constraint.isNotNull(entityID, "entityID must not be null");

    if (signMessage.getEncryptedMessage() != null) {
      throw new EncryptionException("signMessage is already encrypted");
    }
    if (signMessage.getMessage() == null) {
      throw new EncryptionException("No Message element available in SignMessage");
    }

    if (signMessage.getDisplayEntity() == null) {
      log.debug("Updated SignMessage.DisplayEntity with {}", entityID);
      signMessage.setDisplayEntity(entityID);
    }
    else if (!signMessage.getDisplayEntity().equals(entityID)) {
      throw new EncryptionException(String.format("Assigned DisplayEntity (%s) does not match supplied entityID (%s)",
          signMessage.getDisplayEntity(), entityID));
    }

    final EncryptedData encryptedData =
        this.encrypter.encrypt(signMessage.getMessage(), new SAMLObjectEncrypter.Peer(entityID), configuration);
    final EncryptedMessage encryptedMessage =
        (EncryptedMessage) XMLObjectSupport.buildXMLObject(EncryptedMessage.DEFAULT_ELEMENT_NAME);
    encryptedMessage.setEncryptedData(encryptedData);

    signMessage.setMessage(null);
    signMessage.setEncryptedMessage(encryptedMessage);
  }

}
