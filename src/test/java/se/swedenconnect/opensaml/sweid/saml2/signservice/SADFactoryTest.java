/*
 * Copyright 2016-2024 Sweden Connect
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

import java.nio.charset.Charset;
import java.security.KeyStore;
import java.security.PublicKey;
import java.util.Base64;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opensaml.security.x509.impl.KeyStoreX509CredentialAdapter;
import org.springframework.core.io.ClassPathResource;

import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.factories.DefaultJWSVerifierFactory;
import com.nimbusds.jose.proc.JWSVerifierFactory;
import com.nimbusds.jwt.SignedJWT;

import se.swedenconnect.opensaml.sweid.OpenSAMLTestBase;
import se.swedenconnect.opensaml.sweid.saml2.authn.LevelOfAssuranceUris;
import se.swedenconnect.opensaml.sweid.saml2.signservice.sap.SAD;

/**
 * Testcases for building a SAD.
 *
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class SADFactoryTest {

  @Test
  public void createSignedJwt() throws Exception {
    KeyStore keyStore = OpenSAMLTestBase.loadKeyStore(new ClassPathResource("Litsec_SAML_Signing.jks").getInputStream(), "secret", "JKS");
    KeyStoreX509CredentialAdapter credential = new KeyStoreX509CredentialAdapter(keyStore, "litsec_saml_signing", "secret".toCharArray());
    PublicKey publicKey = credential.getEntityCertificate().getPublicKey();

    SADFactory factory = new SADFactory("https://idp.svelegtest.se/idp", credential);

    // Create the SAD ...
    //
    SAD sad = factory.getBuilder()
        .subject("196302052383")
        .audience("http://www.example.com/sigservice")
        .inResponseTo("_a74a068d0548a919e503e5f9ef901851")
        .loa(LevelOfAssuranceUris.AUTHN_CONTEXT_URI_LOA3)
        .requestID("f6e7d061a23293b0053dc7b038a04dad")
        .numberOfDocuments(1)
        .buildSAD();

    // Create the signed JWT
    //
    String jwt = factory.createJwt(sad);

    // Decode
    //
    SignedJWT signedJwt = SignedJWT.parse(jwt);

    String payload = signedJwt.getPayload().toBase64URL().toString();
    SAD sad2 = SAD.fromJson(new String(Base64.getUrlDecoder().decode(payload), Charset.forName("UTF-8")));

    // Make sure the decode SAD is the same.
    Assertions.assertEquals(sad, sad2);

    // Verify signature ...
    //
    JWSVerifierFactory verifierFactory = new DefaultJWSVerifierFactory();
    JWSVerifier verifier = verifierFactory.createJWSVerifier(signedJwt.getHeader(), publicKey);

    Assertions.assertTrue(verifier.verify(signedJwt.getHeader(), signedJwt.getSigningInput(), signedJwt.getSignature()));
  }

}
