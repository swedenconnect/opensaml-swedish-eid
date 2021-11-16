/*
 * Copyright 2016-2021 Sweden Connect
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

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.w3c.dom.Attr;

import se.swedenconnect.opensaml.sweid.saml2.signservice.dss.EncryptedMessage;
import se.swedenconnect.opensaml.sweid.saml2.signservice.dss.Message;
import se.swedenconnect.opensaml.sweid.saml2.signservice.dss.SignMessage;

/**
 * Unmarshaller for the {@code SignMessage} element.
 * 
 * @author Martin Lindström (martin@idsec.se)
 */
public class SignMessageUnmarshaller extends AbstractSAMLObjectUnmarshaller {

  /** {@inheritDoc} */
  @Override
  protected void processChildElement(final XMLObject parentSAMLObject, final XMLObject childSAMLObject)
      throws UnmarshallingException {

    final SignMessage signMessage = (SignMessage) parentSAMLObject;

    if (childSAMLObject instanceof EncryptedMessage) {
      signMessage.setEncryptedMessage((EncryptedMessage) childSAMLObject);
    }
    else if (childSAMLObject instanceof Message) {
      signMessage.setMessage((Message) childSAMLObject);
    }
    else {
      super.processChildElement(parentSAMLObject, childSAMLObject);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void processAttribute(final XMLObject samlObject, final Attr attribute) throws UnmarshallingException {
    final SignMessage signMessage = (SignMessage) samlObject;

    if (attribute.getLocalName().equals(SignMessage.MUST_SHOW_ATTR_NAME)) {
      signMessage.setMustShow(XSBooleanValue.valueOf(attribute.getValue()));
    }
    else if (attribute.getLocalName().equals(SignMessage.DISPLAY_ENTITY_ATTR_NAME)) {
      signMessage.setDisplayEntity(attribute.getValue());
    }
    else if (attribute.getLocalName().equals(SignMessage.MIME_TYPE_ATTR_NAME)) {
      signMessage.setMimeType(attribute.getValue());
    }
    else {
      this.processUnknownAttribute(signMessage, attribute);
    }
  }

}
