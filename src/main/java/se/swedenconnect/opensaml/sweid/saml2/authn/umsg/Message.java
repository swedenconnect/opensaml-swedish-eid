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

import jakarta.annotation.Nullable;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.LangBearing;
import org.opensaml.core.xml.schema.XSBase64Binary;
import org.opensaml.saml.common.SAMLObject;

import javax.xml.namespace.QName;

/**
 * Definition for the {@code MessageType} type:
 *
 * <pre>
 * {@code
 *   <xs:complexType name="MessageType">
 *     <xs:annotation>
 *       <xs:documentation>
 *         The Base64-encoding of UTF-8 string holding the user message.
 *       </xs:documentation>
 *     </xs:annotation>
 *     <xs:simpleContent>
 *       <xs:extension base="xs:base64Binary">
 *         <xs:attribute ref="xml:lang" use="required"/>
 *         <xs:anyAttribute namespace="##any"/>
 *       </xs:extension>
 *     </xs:simpleContent>
 *   </xs:complexType>
 * }
 * </pre>
 *
 * @author Martin Lindström
 */
public interface Message extends XSBase64Binary, LangBearing, SAMLObject, AttributeExtensibleXMLObject {

  /** Name of the element. */
  String DEFAULT_ELEMENT_LOCAL_NAME = "Message";

  /** Default element name. */
  QName DEFAULT_ELEMENT_NAME = new QName(UserMessageConstants.UMSG_NS, DEFAULT_ELEMENT_LOCAL_NAME,
      UserMessageConstants.UMSG_NS_PREFIX);

  /** Local name of the type */
  String TYPE_LOCAL_NAME = "MessageType";

  /** QName of the XSI type. */
  QName TYPE_NAME = new QName(UserMessageConstants.UMSG_NS, TYPE_LOCAL_NAME,
      UserMessageConstants.UMSG_NS_PREFIX);

  /**
   * Gets the content of the {@code Message} element, i.e., its Base64 decoded form.
   * <p>
   * The {@link #getValue()} method will return the Base64 encoded value.
   * </p>
   *
   * @return the message content
   * @see #getValue()
   */
  @Nullable
  String getContent();

  /**
   * Assigns the {@code Message} element by assigning the text that it should hold. The method will Base64 encode the
   * text.
   * <p>
   * The {@link #setValue(String)} is used to assign the Base64 encoded value.
   * </p>
   *
   * @param messageContent content of the Message element
   * @see #setValue(String)
   */
  void setContent(@Nullable final String messageContent);

}
