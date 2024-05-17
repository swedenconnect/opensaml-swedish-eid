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
import net.shibboleth.shared.xml.AttributeSupport;
import net.shibboleth.shared.xml.XMLConstants;
import org.opensaml.core.xml.LangBearing;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import se.swedenconnect.opensaml.sweid.saml2.authn.umsg.Message;
import se.swedenconnect.opensaml.sweid.saml2.authn.umsg.UserMessage;

/**
 * Marshaller for {@link UserMessage}.
 *
 * @author Martin Lindström
 */
public class UserMessageMarshaller extends AbstractSAMLObjectMarshaller {

  /** {@inheritDoc} */
  @Override
  protected void marshallAttributes(@Nonnull final XMLObject xmlObject, @Nonnull final Element domElement)
      throws MarshallingException {

    final UserMessage userMessage = (UserMessage) xmlObject;

    if (userMessage.getMimeType() != null) {
      domElement.setAttributeNS(null, UserMessage.MIME_TYPE_ATTR_NAME, userMessage.getMimeType());
    }
    this.marshallUnknownAttributes(userMessage, domElement);
  }

}
