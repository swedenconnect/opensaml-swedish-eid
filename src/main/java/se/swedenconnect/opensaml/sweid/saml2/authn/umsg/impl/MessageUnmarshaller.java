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

import jakarta.annotation.Nonnull;
import net.shibboleth.shared.xml.XMLConstants;
import org.opensaml.core.xml.LangBearing;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.schema.impl.XSBase64BinaryUnmarshaller;
import org.w3c.dom.Attr;
import se.swedenconnect.opensaml.sweid.saml2.authn.umsg.Message;

/**
 * Unmarshaller for {@link Message}.
 *
 * @author Martin Lindström
 */
public class MessageUnmarshaller extends XSBase64BinaryUnmarshaller {

  @Override
  protected void processAttribute(@Nonnull final XMLObject xmlObject, @Nonnull final Attr attribute)
      throws UnmarshallingException {

    final Message message = (Message) xmlObject;

    if (XMLConstants.XML_NS.equals(attribute.getNamespaceURI())
        && LangBearing.XML_LANG_ATTR_LOCAL_NAME.equals(attribute.getLocalName())) {
      message.setXMLLang(attribute.getValue());
    }
    else {
      this.processUnknownAttribute(message, attribute);
    }
  }

}
