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
import net.shibboleth.shared.xml.AttributeSupport;
import net.shibboleth.shared.xml.XMLConstants;
import org.opensaml.core.xml.LangBearing;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.schema.impl.XSBase64BinaryMarshaller;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import se.swedenconnect.opensaml.sweid.saml2.authn.umsg.Message;

/**
 * A marshaller for {@link Message}.
 *
 * @author Martin Lindström
 */
public class MessageMarshaller extends XSBase64BinaryMarshaller {

  /** {@inheritDoc} */
  @Override
  protected void marshallAttributes(@Nonnull final XMLObject xmlObject, @Nonnull final Element domElement)
      throws MarshallingException {

    final Message message = (Message) xmlObject;

    if (message.getXMLLang() != null) {
      final Attr attribute = AttributeSupport.constructAttribute(domElement.getOwnerDocument(),
          XMLConstants.XML_NS, LangBearing.XML_LANG_ATTR_LOCAL_NAME, XMLConstants.XML_PREFIX);
      attribute.setValue(message.getXMLLang());
      domElement.setAttributeNodeNS(attribute);
    }

    this.marshallUnknownAttributes(message, domElement);
  }
}
