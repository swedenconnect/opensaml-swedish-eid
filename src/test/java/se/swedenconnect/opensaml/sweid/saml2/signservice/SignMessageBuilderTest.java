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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import se.swedenconnect.opensaml.sweid.OpenSAMLTestBase;
import se.swedenconnect.opensaml.sweid.saml2.signservice.build.SignMessageBuilder;
import se.swedenconnect.opensaml.sweid.saml2.signservice.dss.SignMessage;
import se.swedenconnect.opensaml.sweid.saml2.signservice.dss.SignMessageMimeTypeEnum;

/**
 * Test cases for {@code SignMessageBuilder}.
 *
 * @author Martin Lindström (martin@idsec.se)
 */
public class SignMessageBuilderTest extends OpenSAMLTestBase {

  @Test
  public void testBuild() throws Exception {

    SignMessage msg = SignMessageBuilder.builder()
      .mustShow(true)
      .displayEntity("http://www.example.com/idp")
      .mimeType(SignMessageMimeTypeEnum.TEXT)
      .message("This is the sign message")
      .build();

    Assertions.assertNotNull(msg);
    Assertions.assertNull(msg.getEncryptedMessage());

  }

}
