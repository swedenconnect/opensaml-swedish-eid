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
package se.swedenconnect.opensaml.sweid.saml2.signservice.sap.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.schema.impl.XSStringUnmarshaller;
import org.w3c.dom.Attr;

import se.swedenconnect.opensaml.sweid.saml2.signservice.sap.Parameter;

/**
 * Unmarshaller for {@link Parameter}.
 * 
 * @author Martin Lindström (martin@idsec.se)
 */
public class ParameterUnmarshaller extends XSStringUnmarshaller {

  @Override
  protected void processAttribute(final XMLObject xmlObject, final Attr attribute) throws UnmarshallingException {    
    final Parameter p = (Parameter) xmlObject;
    
    if (Parameter.NAME_ATTR_NAME.equalsIgnoreCase(attribute.getLocalName())) {
      p.setName(attribute.getValue());
    }
  }

  
  
}
