/*
 * Copyright 2019-2023 Sweden Connect
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
import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.KeyDescriptor;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.UsageType;
import org.opensaml.security.x509.impl.KeyStoreX509CredentialAdapter;
import org.opensaml.xmlsec.encryption.support.EncryptionConstants;
import org.opensaml.xmlsec.encryption.support.EncryptionException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import net.shibboleth.shared.component.ComponentInitializationException;
import se.swedenconnect.opensaml.saml2.metadata.build.EntityDescriptorBuilder;
import se.swedenconnect.opensaml.saml2.metadata.build.IDPSSODescriptorBuilder;
import se.swedenconnect.opensaml.saml2.metadata.build.KeyDescriptorBuilder;
import se.swedenconnect.opensaml.saml2.metadata.provider.CompositeMetadataProvider;
import se.swedenconnect.opensaml.saml2.metadata.provider.MetadataProvider;
import se.swedenconnect.opensaml.saml2.metadata.provider.StaticMetadataProvider;
import se.swedenconnect.opensaml.sweid.OpenSAMLTestBase;
import se.swedenconnect.opensaml.sweid.saml2.signservice.build.SignMessageBuilder;
import se.swedenconnect.opensaml.sweid.saml2.signservice.dss.Message;
import se.swedenconnect.opensaml.sweid.saml2.signservice.dss.SignMessage;
import se.swedenconnect.opensaml.sweid.saml2.signservice.dss.SignMessageMimeTypeEnum;
import se.swedenconnect.opensaml.xmlsec.encryption.support.SAMLObjectEncrypter;

/**
 * Test cases for {@code SignMessageEncrypter}.
 *
 * @author Martin Lindström (martin@idsec.se)
 */
public class SignMessageEncrypterTest extends OpenSAMLTestBase {

  private static final String ENTITY_ID = "http://www.example.com/idp";
  private static final String CONTENTS = "This is the sign message";

  @Test
  public void testDefault() throws Exception {

    SignMessage signMessage = SignMessageBuilder.builder()
      .displayEntity(ENTITY_ID)
      .message(CONTENTS)
      .mimeType(SignMessageMimeTypeEnum.TEXT)
      .mustShow(true)
      .build();

    // Setup metadata
    //
    EntityDescriptor ed = this.createMetadata(KeyDescriptorBuilder.builder()
      .use(UsageType.ENCRYPTION)
      .certificate(new ClassPathResource("Litsec_SAML_Encryption.crt").getInputStream())
      .build(), KeyDescriptorBuilder.builder()
        .use(UsageType.SIGNING)
        .certificate(new ClassPathResource("Litsec_SAML_Signing.crt").getInputStream())
        .build());
    SAMLObjectEncrypter objectEncrypter = new SAMLObjectEncrypter(this.createMetadataProvider(ed).getMetadataResolver());

    SignMessageEncrypter encrypter = new SignMessageEncrypter(objectEncrypter);

    encrypter.encrypt(signMessage, ENTITY_ID);

    Assertions.assertNotNull(signMessage.getEncryptedMessage());

    // Element e = ObjectUtils.marshall(signMessage);
    // System.out.println(SerializeSupport.prettyPrintXML(e));

    String decryptedMsg = this.decrypt(signMessage, new ClassPathResource("Litsec_SAML_Encryption.jks"), "secret",
      "litsec_saml_encryption");

    Assertions.assertEquals(CONTENTS, decryptedMsg);
  }

  @Test
  public void testNoEncryptionCredentials() throws Exception {
    SignMessage signMessage = SignMessageBuilder.builder()
      .displayEntity(ENTITY_ID)
      .message(CONTENTS)
      .mimeType(SignMessageMimeTypeEnum.TEXT)
      .mustShow(true)
      .build();

    // Setup metadata
    //
    EntityDescriptor ed = this.createMetadata(KeyDescriptorBuilder.builder()
      .use(UsageType.SIGNING)
      .certificate(new ClassPathResource("Litsec_SAML_Signing.crt").getInputStream())
      .build());
    SAMLObjectEncrypter objectEncrypter = new SAMLObjectEncrypter(this.createMetadataProvider(ed).getMetadataResolver());
    SignMessageEncrypter encrypter = new SignMessageEncrypter(objectEncrypter);

    try {
      encrypter.encrypt(signMessage, ENTITY_ID);
      Assertions.fail("Expected error - no encryption credentials found");
    }
    catch (EncryptionException e) {
      System.out.println(e.getMessage());
    }
  }

  @Test
  public void testUnspecified() throws Exception {

    SignMessage signMessage = SignMessageBuilder.builder()
      .displayEntity(ENTITY_ID)
      .message(CONTENTS)
      .mimeType(SignMessageMimeTypeEnum.TEXT)
      .mustShow(true)
      .build();

    // Setup metadata
    //
    EntityDescriptor ed = this.createMetadata(KeyDescriptorBuilder.builder()
      .use(UsageType.UNSPECIFIED)
      .certificate(new ClassPathResource("Litsec_SAML_Encryption.crt").getInputStream())
      .build(), KeyDescriptorBuilder.builder()
        .use(UsageType.SIGNING)
        .certificate(new ClassPathResource("Litsec_SAML_Signing.crt").getInputStream())
        .build());
    SAMLObjectEncrypter objectEncrypter = new SAMLObjectEncrypter(this.createMetadataProvider(ed).getMetadataResolver());

    SignMessageEncrypter encrypter = new SignMessageEncrypter(objectEncrypter);

    encrypter.encrypt(signMessage, ENTITY_ID);

    Assertions.assertNotNull(signMessage.getEncryptedMessage());

    // Element e = ObjectUtils.marshall(signMessage);
    // System.out.println(SerializeSupport.prettyPrintXML(e));

    String decryptedMsg = this.decrypt(signMessage, new ClassPathResource("Litsec_SAML_Encryption.jks"), "secret",
      "litsec_saml_encryption");

    Assertions.assertEquals(CONTENTS, decryptedMsg);
  }

  @Test
  public void testCapabilitiesSimple() throws Exception {

    SignMessage signMessage = SignMessageBuilder.builder()
      .displayEntity(ENTITY_ID)
      .message(CONTENTS)
      .mimeType(SignMessageMimeTypeEnum.TEXT)
      .mustShow(true)
      .build();

    // Setup metadata
    //
    EntityDescriptor ed = this.createMetadata(KeyDescriptorBuilder.builder()
      .use(UsageType.ENCRYPTION)
      .certificate(new ClassPathResource("Litsec_SAML_Encryption.crt").getInputStream())
      .encryptionMethods(EncryptionConstants.ALGO_ID_BLOCKCIPHER_AES256_GCM, EncryptionConstants.ALGO_ID_KEYTRANSPORT_RSA15)
      .build(),
      KeyDescriptorBuilder.builder()
        .use(UsageType.SIGNING)
        .certificate(new ClassPathResource("Litsec_SAML_Signing.crt").getInputStream())
        .build());
    SAMLObjectEncrypter objectEncrypter = new SAMLObjectEncrypter(this.createMetadataProvider(ed).getMetadataResolver());
    SignMessageEncrypter encrypter = new SignMessageEncrypter(objectEncrypter);

    encrypter.encrypt(signMessage, ENTITY_ID);

    Assertions.assertNotNull(signMessage.getEncryptedMessage());

//    Element e = ObjectUtils.marshall(signMessage);
//    System.out.println(SerializeSupport.prettyPrintXML(e));

    String decryptedMsg = this.decrypt(signMessage, new ClassPathResource("Litsec_SAML_Encryption.jks"), "secret",
      "litsec_saml_encryption");

    Assertions.assertEquals(CONTENTS, decryptedMsg);
  }

  private String decrypt(SignMessage signMessage, Resource jks, String password, String alias) throws Exception {
    KeyStore keyStore = loadKeyStore(jks.getInputStream(), password, "JKS");
    Credential cred = new KeyStoreX509CredentialAdapter(keyStore, alias, password.toCharArray());

    SignMessageDecrypter decrypter = new SignMessageDecrypter(cred);
    Message msg = decrypter.decrypt(signMessage);
    return msg.getContent();
  }

  private EntityDescriptor createMetadata(KeyDescriptor... descriptors) {
    return EntityDescriptorBuilder.builder()
      .entityID(ENTITY_ID)
      .id("_id123456")
      .ssoDescriptor(IDPSSODescriptorBuilder.builder()
        .keyDescriptors(descriptors)
        .build())
      .build();
  }

  private MetadataProvider createMetadataProvider(EntityDescriptor... descriptors) throws MarshallingException,
      ComponentInitializationException {
    MetadataProvider mp;
    if (descriptors.length == 1) {
      mp = new StaticMetadataProvider(descriptors[0]);
    }
    else {
      mp = new CompositeMetadataProvider("md", Arrays.asList(descriptors).stream().map(d -> {
        try {
          return new StaticMetadataProvider(d);
        }
        catch (MarshallingException e) {
          throw new RuntimeException(e);
        }
      }).collect(Collectors.toList()));
    }
    mp.initialize();
    return mp;
  }

}
