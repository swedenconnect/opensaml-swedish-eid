/*
 * Copyright 2016-2026 Sweden Connect
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

import java.security.KeyStore;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.saml2.core.AuthnRequest;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.x509.impl.KeyStoreX509CredentialAdapter;
import org.opensaml.xmlsec.encryption.support.DecryptionException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import se.swedenconnect.opensaml.sweid.OpenSAMLTestBase;
import se.swedenconnect.opensaml.sweid.saml2.signservice.dss.Message;
import se.swedenconnect.opensaml.sweid.saml2.signservice.dss.SignMessage;

/**
 * Test cases for SignMessageDecrypter.
 *
 * @author Martin Lindström (martin@idsec.se)
 */
public class SignMessageDecrypterTest extends OpenSAMLTestBase {

  private Credential credential;

  public SignMessageDecrypterTest() throws Exception {
    KeyStore keyStore = loadKeyStore(new ClassPathResource("idp-credentials.jks").getInputStream(), "secret", "JKS");
    this.credential = new KeyStoreX509CredentialAdapter(keyStore, "encryption", "secret".toCharArray());
  }

  /**
   * The encrypted content is not a Message element. Should give a DecryptionException.
   *
   * @throws Exception for errors
   */
  @Test
  public void testDecryptBadType() throws Exception {
    final SignMessageDecrypter decrypter = new SignMessageDecrypter(this.credential);

    final Resource xml = new ClassPathResource("signmessage_bad_type.xml");
    final AuthnRequest authnRequest = unmarshall(xml.getInputStream(), AuthnRequest.class);

    final SignMessage signMessage =
        (SignMessage) authnRequest.getExtensions().getUnknownXMLObjects(SignMessage.DEFAULT_ELEMENT_NAME).get(0);

    Assertions.assertThrows(DecryptionException.class, () -> decrypter.decrypt(signMessage));
  }

  @Test
  public void testDecrypt2() throws Exception {
    SignMessageDecrypter decrypter = new SignMessageDecrypter(this.credential);

    Resource xml = new ClassPathResource("signmessage_rsa_1_5.xml");
    AuthnRequest authnRequest = unmarshall(xml.getInputStream(), AuthnRequest.class);

    XMLObject sm = authnRequest.getExtensions().getUnknownXMLObjects(SignMessage.DEFAULT_ELEMENT_NAME).get(0);
    SignMessage signMessage = (SignMessage) sm;

    Message message = decrypter.decrypt(signMessage);
    Assertions.assertNotNull(message);
    Assertions.assertNotNull(message.getContent());
    // <ns:Message
    // xmlns:ns="http://id.elegnamnden.se/csig/1.1/dss-ext/ns">WW91IGFyZSByZXF1ZXN0ZWQgdG8gc2lnbiB0aGUgZm9sbG93aW5nIGRvY3VtZW50OgoKRG9jdW1lbnQgbmFtZTogZjgzOGE3MDRjNjc3NDYyYjkxYWM4NjQ3ZTdkNmRmZjYueG1sClNpZ25lciBuYW1lOiBPc2thciBKb2hhbnNzb24KU2lnbmVyIElEOiBwZXJzb25hbElkZW50aXR5TnVtYmVyOiAxOTkwMDgyNTIzOTgK</ns:Message>
  }

}
