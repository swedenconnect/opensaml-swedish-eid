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
package se.swedenconnect.opensaml.sweid.saml2.signservice.dss.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.core.xml.util.AttributeMap;

import se.swedenconnect.opensaml.sweid.saml2.signservice.dss.EncryptedMessage;
import se.swedenconnect.opensaml.sweid.saml2.signservice.dss.Message;
import se.swedenconnect.opensaml.sweid.saml2.signservice.dss.SignMessage;
import se.swedenconnect.opensaml.sweid.saml2.signservice.dss.SignMessageMimeTypeEnum;

/**
 * Implementation class for the {@link SignMessage} interface.
 *
 * @author Martin Lindström (martin@idsec.se)
 */
public class SignMessageImpl extends AbstractXMLObject implements SignMessage {

  /** The MustShow attribute. */
  private XSBooleanValue mustShow;

  /** The DisplayEntity attribute. */
  private String displayEntity;

  /** The MimeType attribute. */
  private String mimeType;

  /** The message element. */
  private Message message;

  /** The encryptedMessage element. */
  private EncryptedMessage encryptedMessage;

  /** "anyAttribute" attributes */
  private final AttributeMap unknownAttributes;

  /**
   * Constructor creating an SignMessage object given the namespace URI, local element name and namespace prefix.
   *
   * @param namespaceURI the namespace URI.
   * @param elementLocalName the element local name.
   * @param namespacePrefix the name space prefix.
   */
  public SignMessageImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
    super(namespaceURI, elementLocalName, namespacePrefix);
    this.unknownAttributes = new AttributeMap(this);
  }

  /** {@inheritDoc} */
  @Override
  public List<XMLObject> getOrderedChildren() {
    ArrayList<XMLObject> children = new ArrayList<>();

    if (this.message != null) {
      children.add(this.message);
    }
    if (this.encryptedMessage != null) {
      children.add(this.encryptedMessage);
    }
    if (children.size() == 0) {
      return null;
    }
    return Collections.unmodifiableList(children);
  }

  /** {@inheritDoc} */
  @Override
  public Boolean isMustShow() {
    return this.mustShow != null ? this.mustShow.getValue() : Boolean.FALSE;
  }

  /** {@inheritDoc} */
  @Override
  public XSBooleanValue isMustShowXSBoolean() {
    return this.mustShow;
  }

  /** {@inheritDoc} */
  @Override
  public void setMustShow(final Boolean mustShow) {
    if (mustShow != null) {
      this.mustShow = this.prepareForAssignment(this.mustShow, new XSBooleanValue(mustShow, false));
    }
    else {
      this.mustShow = prepareForAssignment(this.mustShow, null);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void setMustShow(final XSBooleanValue mustShow) {
    this.mustShow = prepareForAssignment(this.mustShow, mustShow);
  }

  /** {@inheritDoc} */
  @Override
  public String getDisplayEntity() {
    return this.displayEntity;
  }

  /** {@inheritDoc} */
  @Override
  public void setDisplayEntity(final String displayEntity) {
    this.displayEntity = this.prepareForAssignment(this.displayEntity, displayEntity);
  }

  /** {@inheritDoc} */
  @Override
  public String getMimeType() {
    return this.mimeType;
  }

  /** {@inheritDoc} */
  @Override
  public SignMessageMimeTypeEnum getMimeTypeEnum() {
    return SignMessageMimeTypeEnum.parse(this.mimeType);
  }

  /** {@inheritDoc} */
  @Override
  public void setMimeType(final String mimeType) {
    this.mimeType = this.prepareForAssignment(this.mimeType, mimeType);
  }

  /** {@inheritDoc} */
  @Override
  public void setMimeType(final SignMessageMimeTypeEnum mimeType) {
    if (mimeType != null) {
      this.mimeType = this.prepareForAssignment(this.mimeType, mimeType.getMimeType());
    }
  }

  /** {@inheritDoc} */
  @Override
  public Message getMessage() {
    return this.message;
  }

  /** {@inheritDoc} */
  @Override
  public void setMessage(final Message message) {
    this.message = this.prepareForAssignment(this.message, message);
  }

  /** {@inheritDoc} */
  @Override
  public EncryptedMessage getEncryptedMessage() {
    return this.encryptedMessage;
  }

  /** {@inheritDoc} */
  @Override
  public void setEncryptedMessage(final EncryptedMessage encryptedMessage) {
    this.encryptedMessage = this.prepareForAssignment(this.encryptedMessage, encryptedMessage);
  }

  /** {@inheritDoc} */
  @Override
  public AttributeMap getUnknownAttributes() {
    return this.unknownAttributes;
  }

}
