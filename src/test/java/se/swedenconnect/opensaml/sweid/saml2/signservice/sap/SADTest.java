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
package se.swedenconnect.opensaml.sweid.saml2.signservice.sap;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import se.swedenconnect.opensaml.sweid.saml2.attribute.AttributeConstants;
import se.swedenconnect.opensaml.sweid.saml2.authn.LevelOfAssuranceUris;

/**
 * Test cases for the {@link SAD} implementation.
 *
 * @author Martin Lindström (martin@idsec.se)
 */
public class SADTest {

  /**
   * Tests creating a SAD and serializing and deserializing.
   *
   * @throws Exception
   *           for errors
   */
  @Test
  public void testEncodeDecode() throws Exception {

    Instant issuance = LocalDateTime.of(2018, 1, 17, 14, 22, 37, 0).toInstant(ZoneOffset.UTC);
    Instant expiry = issuance.plusSeconds(5 * 60);

    SAD sad = new SAD();
    sad.setSubject("196302052383");
    sad.setAudience("http://www.example.com/sigservice");
    sad.setIssuer("https://idp.svelegtest.se/idp");
    sad.setExpiry(expiry);
    sad.setIssuedAt((int) (issuance.toEpochMilli() / 1000L));
    sad.setJwtId("d4073fc74b1b9199");
    SAD.Extension ext = new SAD.Extension();
    ext.setVersion(SADVersion.VERSION_10.toString());
    ext.setInResponseTo("_a74a068d0548a919e503e5f9ef901851");
    ext.setAttributeName(AttributeConstants.ATTRIBUTE_NAME_PERSONAL_IDENTITY_NUMBER);
    ext.setLoa(LevelOfAssuranceUris.AUTHN_CONTEXT_URI_LOA3);
    ext.setRequestID("f6e7d061a23293b0053dc7b038a04dad");
    ext.setNumberOfDocuments(1);
    sad.setSeElnSadext(ext);

    Instant exp = sad.getExpiryDateTime();
    Assertions.assertEquals(sad.getExpiry().intValue(), (int) (exp.toEpochMilli() / 1000));

    String json = sad.toJson();

    SAD sad2 = SAD.fromJson(json);

    Assertions.assertEquals(sad, sad2);

    SAD sad3 = SAD.fromJson(json);

    Assertions.assertEquals(sad, sad3);
  }

}
