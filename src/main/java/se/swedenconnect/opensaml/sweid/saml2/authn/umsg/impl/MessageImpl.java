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
package se.swedenconnect.opensaml.sweid.saml2.authn.umsg.impl;

import com.google.common.base.Strings;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import net.shibboleth.shared.codec.Base64Support;
import net.shibboleth.shared.codec.DecodingException;
import net.shibboleth.shared.codec.EncodingException;
import org.opensaml.core.xml.LangBearing;
import org.opensaml.core.xml.XMLRuntimeException;
import org.opensaml.core.xml.schema.impl.XSBase64BinaryImpl;
import org.opensaml.core.xml.util.AttributeMap;
import se.swedenconnect.opensaml.sweid.saml2.authn.umsg.Message;

import java.nio.charset.StandardCharsets;

/**
 * Implementation class for {@link Message}.
 *
 * @author Martin Lindström
 */
public class MessageImpl extends XSBase64BinaryImpl implements Message {

  /** The language. */
  private String lang;

  /** "anyAttribute" attributes */
  private final AttributeMap unknownAttributes;

  /**
   * Constructor.
   *
   * @param namespaceURI the namespace the element is in
   * @param elementLocalName the local name of the XML element this Object represents
   * @param namespacePrefix the prefix for the given namespace
   */
  public MessageImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
    super(namespaceURI, elementLocalName, namespacePrefix);
    this.unknownAttributes = new AttributeMap(this);
  }

  /** {@inheritDoc} */
  @Nonnull
  @Override
  public AttributeMap getUnknownAttributes() {
    return this.unknownAttributes;
  }

  /** {@inheritDoc} */
  @Nullable
  @Override
  public String getXMLLang() {
    return this.lang;
  }

  /** {@inheritDoc} */
  @Override
  public void setXMLLang(@Nullable final String newLang) {
    final boolean hasValue = newLang != null && !Strings.isNullOrEmpty(newLang);
    this.lang = this.prepareForAssignment(this.lang, newLang);
    this.manageQualifiedAttributeNamespace(LangBearing.XML_LANG_ATTR_NAME, hasValue);
  }

  /** {@inheritDoc} */
  @Nullable
  @Override
  public String getContent() {
    try {
      return this.getValue() != null ? new String(Base64Support.decode(this.getValue()), StandardCharsets.UTF_8) : null;
    }
    catch (final DecodingException e) {
      throw new XMLRuntimeException(e);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void setContent(@Nullable final String messageContent) {
    if (messageContent != null) {
      try {
        this.setValue(Base64Support.encode(messageContent.getBytes(StandardCharsets.UTF_8), false));
      }
      catch (final EncodingException e) {
        throw new XMLRuntimeException(e);
      }
    }
    else {
      this.setValue(null);
    }
  }

}
