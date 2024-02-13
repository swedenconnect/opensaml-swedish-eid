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
package se.swedenconnect.opensaml.sweid.saml2.signservice.sap.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;

import se.swedenconnect.opensaml.sweid.saml2.signservice.sap.Parameter;
import se.swedenconnect.opensaml.sweid.saml2.signservice.sap.RequestParams;

/**
 * Unmarshaller for {@link RequestParams}.
 *
 * @author Martin Lindström (martin@idsec.se)
 */
public class RequestParamsUnmarshaller extends AbstractSAMLObjectUnmarshaller {

  /** {@inheritDoc} */
  @Override
  protected void processChildElement(final XMLObject parentSAMLObject, final XMLObject childSAMLObject)
      throws UnmarshallingException {
    final RequestParams params = (RequestParams) parentSAMLObject;

    if (childSAMLObject instanceof Parameter) {
      params.getParameters().add((Parameter) childSAMLObject);
    }
    else {
      super.processChildElement(parentSAMLObject, childSAMLObject);
    }
  }

}
