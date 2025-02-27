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
package se.swedenconnect.opensaml.sweid.saml2.authn.umsg.impl;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import se.swedenconnect.opensaml.sweid.saml2.authn.umsg.Message;
import se.swedenconnect.opensaml.sweid.saml2.authn.umsg.UserMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementation class for the {@link UserMessage} interface.
 *
 * @author Martin Lindström
 */
public class UserMessageImpl extends AbstractXMLObject implements UserMessage {

  /** Message children. */
  private final XMLObjectChildrenList<Message> messages;

  /** The MIME type. */
  private String mimeType;

  /** "anyAttribute" attributes */
  private final AttributeMap unknownAttributes;

  /**
   * Constructor.
   *
   * @param namespaceURI the namespace the element is in
   * @param elementLocalName the local name of the XML element this Object represents
   * @param namespacePrefix the prefix for the given namespace
   */
  protected UserMessageImpl(final String namespaceURI, final String elementLocalName,
      final String namespacePrefix) {
    super(namespaceURI, elementLocalName, namespacePrefix);
    this.messages = new XMLObjectChildrenList<>(this);
    this.unknownAttributes = new AttributeMap(this);
  }

  /** {@inheritDoc} */
  @Nonnull
  @Override
  public List<Message> getMessages() {
    return this.messages;
  }

  /** {@inheritDoc} */
  @Nullable
  @Override
  public String getMimeType() {
    return this.mimeType;
  }

  /** {@inheritDoc} */
  @Override
  public void setMimeType(@Nullable final String mimeType) {
    this.mimeType = this.prepareForAssignment(this.mimeType, mimeType);
  }

  /** {@inheritDoc} */
  @Nullable
  @Override
  public List<XMLObject> getOrderedChildren() {
    final ArrayList<XMLObject> children = new ArrayList<>(this.messages);
    return Collections.unmodifiableList(children);
  }

  /** {@inheritDoc} */
  @Nonnull
  @Override
  public AttributeMap getUnknownAttributes() {
    return this.unknownAttributes;
  }

}
