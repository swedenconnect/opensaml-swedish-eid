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
package se.swedenconnect.opensaml.sweid.saml2.authn.psc.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;

import se.swedenconnect.opensaml.sweid.saml2.authn.psc.MatchValue;
import se.swedenconnect.opensaml.sweid.saml2.authn.psc.PrincipalSelection;

/**
 * An unmarshaller for {@link PrincipalSelection}.
 * 
 * @author Martin Lindström (martin@idsec.se)
 */
public class PrincipalSelectionUnmarshaller extends AbstractSAMLObjectUnmarshaller {

  /** {@inheritDoc} */
  @Override
  protected void processChildElement(final XMLObject parentXMLObject, final XMLObject childXMLObject) throws UnmarshallingException {
    final PrincipalSelection ps = (PrincipalSelection) parentXMLObject;
    if (childXMLObject instanceof MatchValue) {
      ps.getMatchValues().add((MatchValue) childXMLObject);
    }
    else {
      super.processChildElement(parentXMLObject, childXMLObject);
    }
  }

}
