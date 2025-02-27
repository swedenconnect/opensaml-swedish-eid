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
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.w3c.dom.Attr;
import se.swedenconnect.opensaml.sweid.saml2.authn.umsg.Message;
import se.swedenconnect.opensaml.sweid.saml2.authn.umsg.UserMessage;

/**
 * Unmarshaller for {@link UserMessage}.
 *
 * @author Martin Lindström
 */
public class UserMessageUnmarshaller extends AbstractSAMLObjectUnmarshaller {

  /** {@inheritDoc} */
  @Override
  protected void processChildElement(@Nonnull final XMLObject parentXMLObject, @Nonnull final XMLObject childXMLObject)
      throws UnmarshallingException {

    final UserMessage userMessage = (UserMessage) parentXMLObject;

    if (childXMLObject instanceof Message) {
      userMessage.getMessages().add((Message) childXMLObject);
    }
    else {
      super.processChildElement(parentXMLObject, childXMLObject);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void processAttribute(@Nonnull final XMLObject xmlObject, @Nonnull final Attr attribute)
      throws UnmarshallingException {

    final UserMessage userMessage = (UserMessage) xmlObject;

    if (UserMessage.MIME_TYPE_ATTR_NAME.equalsIgnoreCase(attribute.getLocalName())) {
      userMessage.setMimeType(attribute.getValue());
    }
    else {
      this.processUnknownAttribute(userMessage, attribute);
    }

  }

}
