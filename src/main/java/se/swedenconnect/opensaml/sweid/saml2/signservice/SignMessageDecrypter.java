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
package se.swedenconnect.opensaml.sweid.saml2.signservice;

import java.util.Collection;
import java.util.List;

import org.opensaml.security.credential.Credential;
import org.opensaml.xmlsec.DecryptionParameters;
import org.opensaml.xmlsec.encryption.support.Decrypter;
import org.opensaml.xmlsec.encryption.support.DecryptionException;
import org.opensaml.xmlsec.encryption.support.EncryptedKeyResolver;
import org.opensaml.xmlsec.encryption.support.InlineEncryptedKeyResolver;
import org.opensaml.xmlsec.keyinfo.KeyInfoCredentialResolver;
import org.opensaml.xmlsec.keyinfo.impl.StaticKeyInfoCredentialResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.swedenconnect.opensaml.sweid.saml2.signservice.dss.Message;
import se.swedenconnect.opensaml.sweid.saml2.signservice.dss.SignMessage;
import se.swedenconnect.opensaml.xmlsec.encryption.support.Pkcs11Decrypter;

/**
 * A bean for decrypting encrypted messages within {@link SignMessage} objects.
 * 
 * @author Martin Lindström (martin@idsec.se)
 */
public class SignMessageDecrypter {

  /** Logger instance. */
  private Logger logger = LoggerFactory.getLogger(SignMessageDecrypter.class);

  /** The resolver for key encryption keys. */
  private KeyInfoCredentialResolver keyEncryptionKeyResolver;

  /** The resolver for encrypted keys - always inlined for EncryptedMessage. */
  private EncryptedKeyResolver encryptedKeyResolver = new InlineEncryptedKeyResolver();

  /** Optional black list of algorithms. */
  private Collection<String> blacklistedAlgorithms;

  /** Optional white list of algorithms. */
  private Collection<String> whitelistedAlgorithms;

  /** The decrypter. */
  private Decrypter decrypter;
  
  /**
   * If using a HSM it is likely that the SunPKCS11 crypto provider is used. This provider does not have support for
   * OAEP padding. This is used commonly for XML encryption since
   * {@code http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p} is the default algorithm to use for key encryption. This
   * class has a workaround for this limitation that is enabled by setting the {@code pkcs11Workaround} flag.
   */
  private boolean pkcs11Workaround = false;  

  /**
   * Constructor given the credential to use to decrypt the messages (certificate or key pair)
   * 
   * @param decryptionCredential
   *          decryption credential
   */
  public SignMessageDecrypter(final Credential decryptionCredential) {
    this.keyEncryptionKeyResolver = new StaticKeyInfoCredentialResolver(decryptionCredential);
  }

  /**
   * Constructor accepting several credentials (certificates or key pairs) to be used when decrypting. This may be
   * useful after a key rollover.
   * 
   * @param decryptionCredentials
   *          decryption credentials
   */
  public SignMessageDecrypter(final List<Credential> decryptionCredentials) {
    this.keyEncryptionKeyResolver = new StaticKeyInfoCredentialResolver(decryptionCredentials);
  }

  /**
   * Constructor accepting a key encryption key resolver.
   * 
   * @param keyEncryptionKeyResolver
   *          the resolver
   */
  public SignMessageDecrypter(final KeyInfoCredentialResolver keyEncryptionKeyResolver) {
    this.keyEncryptionKeyResolver = keyEncryptionKeyResolver;
  }
  
  /**
   * Decrypts the encrypted message of a {@link SignMessage} and returns the cleartext {@code Message}.
   * 
   * @param signMessage
   *          the element holding the encrypted message
   * @return a cleartext {@code Message} element
   * @throws DecryptionException
   *           for decryption errors
   */
  public Message decrypt(final SignMessage signMessage) throws DecryptionException {
    if (signMessage.getEncryptedMessage() == null && signMessage.getMessage() != null) {
      logger.info("No decryption required - SignMessage contains cleartext message");
      return signMessage.getMessage();
    }
    if (signMessage.getEncryptedMessage() == null) {
      final String msg = "No message available";
      logger.error(msg);
      throw new DecryptionException(msg);
    }
    return (Message) this.getDecrypter().decryptData(signMessage.getEncryptedMessage().getEncryptedData());
  }

  /**
   * Returns the decrypter to use.
   * 
   * @return the decrypter
   */
  private Decrypter getDecrypter() {
    if (this.decrypter == null) {
      DecryptionParameters pars = new DecryptionParameters();
      pars.setKEKKeyInfoCredentialResolver(this.keyEncryptionKeyResolver);
      pars.setEncryptedKeyResolver(this.encryptedKeyResolver);
      pars.setExcludedAlgorithms(this.blacklistedAlgorithms);
      pars.setIncludedAlgorithms(this.whitelistedAlgorithms);      
      this.decrypter = this.pkcs11Workaround ? new Pkcs11Decrypter(pars) : new Decrypter(pars);
      this.decrypter.setRootInNewDocument(true);
    }
    return this.decrypter;
  }

  /**
   * Assigns a list of black listed algorithms
   * 
   * @param blacklistedAlgorithms
   *          non allowed algorithms
   */
  public void setBlacklistedAlgorithms(final Collection<String> blacklistedAlgorithms) {
    this.blacklistedAlgorithms = blacklistedAlgorithms;
  }

  /**
   * Assigns a list of white listed algorithms
   * 
   * @param whitelistedAlgorithms
   *          white listed algorithms
   */
  public void setWhitelistedAlgorithms(final Collection<String> whitelistedAlgorithms) {
    this.whitelistedAlgorithms = whitelistedAlgorithms;
  }
  
  /**
   * If using a HSM it is likely that the SunPKCS11 crypto provider is used. This provider does not have support for
   * OAEP padding. This is used commonly for XML encryption since
   * {@code http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p} is the default algorithm to use for key encryption. This
   * class has a workaround for this limitation that is enabled by setting the {@code pkcs11Workaround} flag.
   * 
   * @param pkcs11Workaround
   *          whether to run in PKCS11 workaround mode
   */
  public void setPkcs11Workaround(final boolean pkcs11Workaround) {
    this.pkcs11Workaround = pkcs11Workaround;
  }  

}
