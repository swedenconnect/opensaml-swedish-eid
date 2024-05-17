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
package se.swedenconnect.opensaml.sweid.saml2.authn.umsg;

import net.shibboleth.shared.codec.Base64Support;
import org.junit.jupiter.api.Test;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.w3c.dom.Element;
import se.swedenconnect.opensaml.sweid.OpenSAMLTestBase;
import se.swedenconnect.opensaml.sweid.saml2.authn.umsg.build.MessageBuilder;
import se.swedenconnect.opensaml.sweid.saml2.authn.umsg.build.UserMessageBuilder;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test cases for {@link UserMessage} and {@link Message}.
 *
 * @author Martin Lindström
 */
public class UserMessageTest extends OpenSAMLTestBase {

  /**
   * Test to marshall and unmarshall the object.
   *
   * @throws Exception for errors
   */
  @Test
  public void testMarshallUnmarshall() throws Exception {

    final UserMessage userMessage = (UserMessage) XMLObjectSupport.buildXMLObject(UserMessage.DEFAULT_ELEMENT_NAME);
    userMessage.setMimeType("text/plain");

    final Message message1 = (Message) XMLObjectSupport.buildXMLObject(Message.DEFAULT_ELEMENT_NAME);
    message1.setContent("Hejsan");
    message1.setXMLLang("sv");
    userMessage.getMessages().add(message1);

    final Message message2 = (Message) XMLObjectSupport.buildXMLObject(Message.DEFAULT_ELEMENT_NAME);
    final String content2 = "Hello";
    message2.setValue(Base64Support.encode(content2.getBytes(StandardCharsets.UTF_8), false));
    message2.setXMLLang("en");
    userMessage.getMessages().add(message2);

    final Element element = XMLObjectSupport.marshall(userMessage);

    // System.out.println(SerializeSupport.prettyPrintXML(element));

    final UserMessage userMessage2 =
        (UserMessage) Objects.requireNonNull(XMLObjectSupport.getUnmarshaller(element)).unmarshall(element);

    assertEquals(userMessage.getMimeType(), userMessage2.getMimeType());
    assertEquals(userMessage.getMessages().size(), userMessage2.getMessages().size());
    for (int i = 0; i < userMessage.getMessages().size(); i++) {
      final Message originalMessage = userMessage.getMessages().get(i);
      final Message unmarshalledMessage = userMessage2.getMessages().get(i);
      assertEquals(originalMessage.getContent(), unmarshalledMessage.getContent());
      assertEquals(originalMessage.getXMLLang(), unmarshalledMessage.getXMLLang());
    }
    assertEquals(content2, userMessage2.getMessages().get(1).getContent());
  }

  /**
   * Tests using builders to create the objects.
   *
   * @throws Exception for errors
   */
  @Test
  public void testBuilders() throws Exception {
    final UserMessage userMessage = UserMessageBuilder.builder()
        .mimeType("text/plain")
        .message(MessageBuilder.builder()
            .language("sv")
            .content("Hejsan")
            .build())
        .message(MessageBuilder.builder()
            .language("en")
            .content("Hello")
            .build())
        .build();

    final Element element = XMLObjectSupport.marshall(userMessage);

    // System.out.println(SerializeSupport.prettyPrintXML(element));

    final UserMessage userMessage2 =
        (UserMessage) Objects.requireNonNull(XMLObjectSupport.getUnmarshaller(element)).unmarshall(element);

    assertEquals(userMessage.getMimeType(), userMessage2.getMimeType());
    assertEquals(userMessage.getMessages().size(), userMessage2.getMessages().size());
    for (int i = 0; i < userMessage.getMessages().size(); i++) {
      final Message originalMessage = userMessage.getMessages().get(i);
      final Message unmarshalledMessage = userMessage2.getMessages().get(i);
      assertEquals(originalMessage.getContent(), unmarshalledMessage.getContent());
      assertEquals(originalMessage.getXMLLang(), unmarshalledMessage.getXMLLang());
    }
  }

}
