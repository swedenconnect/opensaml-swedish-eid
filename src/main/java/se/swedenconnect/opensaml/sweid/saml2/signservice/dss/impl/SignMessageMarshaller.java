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

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.w3c.dom.Element;

import se.swedenconnect.opensaml.sweid.saml2.signservice.dss.SignMessage;

/**
 * Marshaller for the {@code SignMessage} element.
 *
 * @author Martin Lindström (martin@idsec.se)
 */
public class SignMessageMarshaller extends AbstractSAMLObjectMarshaller {

  /** {@inheritDoc} */
  @Override
  protected void marshallAttributes(final XMLObject xmlObject, final Element domElement) throws MarshallingException {
    final SignMessage signMessage = (SignMessage) xmlObject;

    if (signMessage.isMustShowXSBoolean() != null) {
      domElement.setAttributeNS(null, SignMessage.MUST_SHOW_ATTR_NAME,
          signMessage.isMustShowXSBoolean().getValue().toString());
    }
    if (signMessage.getDisplayEntity() != null) {
      domElement.setAttributeNS(null, SignMessage.DISPLAY_ENTITY_ATTR_NAME, signMessage.getDisplayEntity());
    }
    if (signMessage.getMimeType() != null) {
      domElement.setAttributeNS(null, SignMessage.MIME_TYPE_ATTR_NAME, signMessage.getMimeType());
    }

    this.marshallUnknownAttributes(signMessage, domElement);
  }

  /** {@inheritDoc} */
  @Override
  protected void marshallElementContent(final XMLObject xmlObject, final Element domElement)
      throws MarshallingException {
    super.marshallElementContent(xmlObject, domElement);
  }

}
