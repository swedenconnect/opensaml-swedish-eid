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

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;

import se.swedenconnect.opensaml.sweid.saml2.signservice.sap.Parameter;

/**
 * Builder for {@link Parameter}.
 * 
 * @author Martin Lindström (martin@idsec.se)
 */
public class ParameterBuilder extends AbstractSAMLObjectBuilder<Parameter> {

  /** {@inheritDoc} */
  @Override
  public Parameter buildObject() {
    return this.buildObject(Parameter.DEFAULT_ELEMENT_NAME.getNamespaceURI(), Parameter.DEFAULT_ELEMENT_LOCAL_NAME,
      Parameter.DEFAULT_ELEMENT_NAME.getPrefix());
  }

  /** {@inheritDoc} */
  @Override
  public Parameter buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
    return new ParameterImpl(namespaceURI, localName, namespacePrefix);
  }

}
