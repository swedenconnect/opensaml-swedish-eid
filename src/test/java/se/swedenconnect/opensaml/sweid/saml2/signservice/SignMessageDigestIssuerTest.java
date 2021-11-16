/*
 * Copyright 2019-2021 Sweden Connect
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

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

import org.junit.Assert;
import org.junit.Test;
import org.opensaml.saml.saml2.core.Attribute;
import org.opensaml.xmlsec.algorithm.AlgorithmDescriptor;
import org.opensaml.xmlsec.algorithm.AlgorithmRegistry;
import org.opensaml.xmlsec.algorithm.AlgorithmSupport;
import org.opensaml.xmlsec.signature.support.SignatureConstants;

import se.swedenconnect.opensaml.saml2.attribute.AttributeUtils;
import se.swedenconnect.opensaml.sweid.OpenSAMLTestBase;
import se.swedenconnect.opensaml.sweid.saml2.signservice.build.SignMessageBuilder;
import se.swedenconnect.opensaml.sweid.saml2.signservice.dss.SignMessage;
import se.swedenconnect.opensaml.sweid.saml2.signservice.dss.SignMessageMimeTypeEnum;

/**
 * Test cases for SignMessageDigestIssuer.
 *
 * @author Martin Lindström (martin@idsec.se)
 */
public class SignMessageDigestIssuerTest extends OpenSAMLTestBase {

  private static final String CONTENTS = "I hereby confirm that I want to join example.com as a customer";

  @Test
  public void testBadInputSet() throws Exception {

    final SignMessageDigestIssuer issuer = new SignMessageDigestIssuer();

    try {
      issuer.setDefaultDigestMethod("http://not.a.real.algo");
      Assert.fail("Expected SecurityException");
    }
    catch (final SecurityException e) {
    }

    try {
      issuer.setDefaultDigestMethod(SignatureConstants.ALGO_ID_SIGNATURE_RSA_SHA256);
      Assert.fail("Expected SecurityException");
    }
    catch (final SecurityException e) {
    }
  }

  @Test
  public void testCreate() throws Exception {

    final SignMessageDigestIssuer issuer = new SignMessageDigestIssuer();

    final SignMessage signMessage = SignMessageBuilder.builder()
      .message(SignMessageDigestIssuerTest.CONTENTS)
      .mimeType(SignMessageMimeTypeEnum.TEXT)
      .build();
    
    final Attribute attr = issuer.create(signMessage.getMessage());
    Assert.assertEquals(
      String.format("%s;%s",
        SignMessageDigestIssuer.DEFAULT_DIGEST_METHOD, this.hash(CONTENTS,
          SignMessageDigestIssuer.DEFAULT_DIGEST_METHOD)),
      AttributeUtils.getAttributeStringValue(attr));
  }

  private String hash(final String text, final String digestAlgorithm) throws Exception {
    final AlgorithmRegistry registry = AlgorithmSupport.getGlobalAlgorithmRegistry();
    final AlgorithmDescriptor descriptor = registry.get(digestAlgorithm);
    final MessageDigest messageDigest = MessageDigest.getInstance(descriptor.getJCAAlgorithmID());
    final byte[] digestValue = messageDigest.digest(text.getBytes(StandardCharsets.UTF_8));
    return Base64.getEncoder().encodeToString(digestValue);
  }
}
