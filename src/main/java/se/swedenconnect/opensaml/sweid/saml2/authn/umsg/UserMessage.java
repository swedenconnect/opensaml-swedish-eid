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
package se.swedenconnect.opensaml.sweid.saml2.authn.umsg;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.saml.common.SAMLObject;

import javax.xml.namespace.QName;
import java.util.List;

/**
 * Definition of the {@code UserMessage} type.
 *
 * <pre>
 * {@code
 *   <xs:element name="UserMessage" type="umsg:UserMessageType"/>
 *
 *   <xs:complexType name="UserMessageType">
 *     <xs:annotation>
 *       <xs:documentation>List of user messages (in different languages)</xs:documentation>
 *     </xs:annotation>
 *     <xs:sequence>
 *       <xs:element name="Message" type="umsg:MessageType" minOccurs="1" maxOccurs="unbounded"/>
 *     </xs:sequence>
 *     <xs:attribute name="mimeType" type="xs:string" default="text/plain">
 *       <xs:annotation>
 *         <xs:documentation>The MIME type of the user message(s)</xs:documentation>
 *       </xs:annotation>
 *     </xs:attribute>
 *     <xs:anyAttribute namespace="##any"/>
 *   </xs:complexType>
 *  }
 *  </pre>
 *
 * @author Martin Lindström
 */
public interface UserMessage extends SAMLObject, AttributeExtensibleXMLObject {

  /** Name of the element. */
  String DEFAULT_ELEMENT_LOCAL_NAME = "UserMessage";

  /** Default element name. */
  QName DEFAULT_ELEMENT_NAME = new QName(UserMessageConstants.UMSG_NS, DEFAULT_ELEMENT_LOCAL_NAME,
      UserMessageConstants.UMSG_NS_PREFIX);

  /** Local name of the type */
  String TYPE_LOCAL_NAME = "UserMessageType";

  /** QName of the XSI type. */
  QName TYPE_NAME = new QName(UserMessageConstants.UMSG_NS, TYPE_LOCAL_NAME,
      UserMessageConstants.UMSG_NS_PREFIX);

  /** Attribute label for the mimeType attribute. */
  String MIME_TYPE_ATTR_NAME = "mimeType";

  /** The default MIME type. */
  String DEFAULT_MIME_TYPE = "text/plain";

  /**
   * Gets a reference to the list of {@link Message} elements.
   *
   * @return a list of {@link Message} elements
   */
  @Nonnull
  List<Message> getMessages();

  /**
   * Gets the value of the {@code mimeType} attribute.
   *
   * @return the MIME type
   */
  @Nullable
  String getMimeType();

  /**
   * Assigns the value for the {@code mimeType} attribute.
   *
   * @param mimeType the MIME type
   */
  void setMimeType(@Nullable final String mimeType);

}
