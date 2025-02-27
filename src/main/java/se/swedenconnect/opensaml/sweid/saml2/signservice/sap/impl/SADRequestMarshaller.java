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
package se.swedenconnect.opensaml.sweid.saml2.signservice.sap.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.w3c.dom.Element;

import se.swedenconnect.opensaml.sweid.saml2.signservice.sap.SADRequest;

/**
 * Marshaller for {@link SADRequest}.
 *
 * @author Martin Lindström (martin@idsec.se)
 */
public class SADRequestMarshaller extends AbstractSAMLObjectMarshaller {

  /** {@inheritDoc} */
  @Override
  protected void marshallAttributes(final XMLObject samlObject, final Element domElement) throws MarshallingException {
    final SADRequest sadRequest = (SADRequest) samlObject;

    if (sadRequest.getID() != null) {
      domElement.setAttributeNS(null, SADRequest.ID_ATTRIB_NAME, sadRequest.getID());
      domElement.setIdAttributeNS(null, SADRequest.ID_ATTRIB_NAME, true);
    }
  }

}
