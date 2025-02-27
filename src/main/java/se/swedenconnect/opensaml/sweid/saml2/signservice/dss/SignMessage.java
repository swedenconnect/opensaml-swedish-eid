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
package se.swedenconnect.opensaml.sweid.saml2.signservice.dss;

import javax.xml.namespace.QName;

import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.saml.common.SAMLObject;

/**
 * Definition of the SignMessage type.
 * <p>
 * The {@code <SignMessage>} element holds a message to the signer with information about what is being signed. The sign
 * message is provided either in plain text using the {@code <Message>} child element or as an encrypted message using
 * the {@code <EncryptedMessage>} child element. This element's <b>SignMessageType</b> complex type includes the
 * following attributes and elements:
 * </p>
 * <dl>
 * <dt>{@code MustShow} [Optional] (Default "false")</dt>
 * <dd>When this attribute is set to true then the requested signature MUST NOT be created unless this message has been
 * displayed and accepted by the signer. The default is false.</dd>
 * <dt>{@code DisplayEntity} [Optional]</dt>
 * <dd>The EntityID of the entity responsible for displaying the sign message to the signer. When the sign message is
 * encrypted, then this entity is also the holder of the private decryption key necessary to decrypt the sign message.
 * </dd>
 * <dt>{@code MimeType} [Optional] (Default "text")</dt>
 * <dd>The mime type defining the message format. This is an enumeration of the valid attribute values text (plain
 * text), text/html (html) or text/markdown (markdown). This specification does not specify any particular restrictions
 * on the provided message but it is RECOMMENDED that sign message content is restricted to a limited set of valid tags
 * and attributes, and that the display entity performs filtering to enforce these restrictions before displaying the
 * message. The means through which parties agree on such restrictions are outside the scope of this specification, but
 * one valid option to communicate such restrictions could be through federation metadata.</dd>
 * <dt>{@code <Message>} [Choice]</dt>
 * <dd>The base64 encoded sign message in unencrypted form. The message MUST be encoded using UTF-8.</dd>
 * <dt>{@code <EncryptedMessage>} [Choice]</dt>
 * <dd>An encrypted {@code <Message>} element. Either a {@code <Message>} or an {@code <EncryptedMessage>} element MUST
 * be present.</dd>
 * </dl>
 *
 * The following schema fragment defines the {@code <SignMessage>} element and the SignMessageType complex type:
 *
 * <pre>{@code
 * <xs:complexType name="SignMessageType">
 *   <xs:choice>
 *     <xs:element ref="csig:Message"/>
 *     <xs:element ref="csig:EncryptedMessage"/>
 *   </xs:choice>
 *   <xs:attribute name="MustShow" type="xs:boolean" default="false"/>
 *   <xs:attribute name="DisplayEntity" type="xs:anyURI"/>
 *   <xs:attribute name="MimeType" default="text">
 *     <xs:simpleType>
 *       <xs:restriction base="xs:string">
 *         <xs:enumeration value="text/html"/>
 *         <xs:enumeration value="text"/>
 *         <xs:enumeration value="text/markdown"/>
 *       </xs:restriction>
 *     </xs:simpleType>
 *   </xs:attribute>
 *   <xs:anyAttribute namespace="##other" processContents="lax"/>
 * </xs:complexType>
 *
 * <xs:element name="Message" type="xs:base64Binary"/>
 * <xs:element name="EncryptedMessage" type="saml:EncryptedElementType"/>}
 * </pre>
 * <p>
 * See "DSS Extension for Federated Central Signing Services".
 * </p>
 *
 * @author Martin Lindström (martin@idsec.se)
 */
public interface SignMessage extends SAMLObject, AttributeExtensibleXMLObject {

  /** Name of the element. */
  String DEFAULT_ELEMENT_LOCAL_NAME = "SignMessage";

  /** Default element name. */
  QName DEFAULT_ELEMENT_NAME = new QName(DssExtensionsConstants.SWEID_DSS_EXT_NS, DEFAULT_ELEMENT_LOCAL_NAME,
      DssExtensionsConstants.SWEID_DSS_EXT_PREFIX);

  /** Local name of the type */
  String TYPE_LOCAL_NAME = "SignMessageType";

  /** QName of the XSI type. */
  QName TYPE_NAME =
      new QName(DssExtensionsConstants.SWEID_DSS_EXT_NS, TYPE_LOCAL_NAME, DssExtensionsConstants.SWEID_DSS_EXT_PREFIX);

  /** Attribute label for the MustShow attribute. */
  String MUST_SHOW_ATTR_NAME = "MustShow";

  /** Attribute label for the DisplayEntity attribute. */
  String DISPLAY_ENTITY_ATTR_NAME = "DisplayEntity";

  /** Attribute label for the MimeType attribute. */
  String MIME_TYPE_ATTR_NAME = "MimeType";

  /**
   * Returns the value of the {@code MustShow} attribute.
   *
   * @return the {@code MustShow} attribute
   */
  Boolean isMustShow();

  /**
   * Returns the {@code MustShow} attribute as a {@code XSBooleanValue}.
   *
   * @return the {@code MustShow} attribute
   * @see #isMustShow()
   */
  XSBooleanValue isMustShowXSBoolean();

  /**
   * Assigns the value of the {@code MustShow} attribute.
   *
   * @param mustShow the value to assign
   */
  void setMustShow(final Boolean mustShow);

  /**
   * Assigns the value of the {@code MustShow} attribute.
   *
   * @param mustShow the value to assign
   * @see #setMustShow(Boolean)
   */
  void setMustShow(final XSBooleanValue mustShow);

  /**
   * Returns the value of the {@code DisplayEntity} attribute.
   *
   * @return the DisplayEntity attribute
   */
  String getDisplayEntity();

  /**
   * Assigns the value for the {@code DisplayEntity} attribute.
   *
   * @param displayEntity the entityID to assign
   */
  void setDisplayEntity(final String displayEntity);

  /**
   * Returns the value of the {@code MimeType} attribute.
   *
   * @return the MimeType attribute
   */
  String getMimeType();

  /**
   * Returns the value of the {@code MimeType} attribute as an enum.
   *
   * @return the MimeType attribute
   */
  SignMessageMimeTypeEnum getMimeTypeEnum();

  /**
   * Assigns the {@code MimeType} attribute.
   *
   * @param mimeType the mime type to assign
   */
  void setMimeType(final String mimeType);

  /**
   * Assigns the {@code MimeType} attribute.
   *
   * @param mimeType the mime type as an enum to assign
   */
  void setMimeType(final SignMessageMimeTypeEnum mimeType);

  /**
   * Returns the {@code Message} element.
   *
   * @return the Message element
   */
  Message getMessage();

  /**
   * Assigns the {@code Message} element.
   *
   * @param message the message to assign
   */
  void setMessage(final Message message);

  /**
   * Returns the {@code EncryptedMessage} element.
   *
   * @return the EncryptedMessage element
   */
  EncryptedMessage getEncryptedMessage();

  /**
   * Assigns the {@code EncryptedMessage} element.
   *
   * @param encryptedMessage the EncryptedMessage element to assign
   */
  void setEncryptedMessage(final EncryptedMessage encryptedMessage);

}
