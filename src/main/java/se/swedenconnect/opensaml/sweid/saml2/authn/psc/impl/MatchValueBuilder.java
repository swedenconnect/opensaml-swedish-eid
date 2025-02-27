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
package se.swedenconnect.opensaml.sweid.saml2.authn.psc.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;

import se.swedenconnect.opensaml.sweid.saml2.authn.psc.MatchValue;

/**
 * A builder for {@link MatchValue} objects.
 *
 * @author Martin Lindström (martin@idsec.se)
 */
public class MatchValueBuilder extends AbstractSAMLObjectBuilder<MatchValue> {

  /** {@inheritDoc} */
  @Override
  public MatchValue buildObject() {
    return this.buildObject(MatchValue.DEFAULT_ELEMENT_NAME.getNamespaceURI(), MatchValue.DEFAULT_ELEMENT_LOCAL_NAME,
        MatchValue.DEFAULT_ELEMENT_NAME.getPrefix());
  }

  /** {@inheritDoc} */
  @Override
  public MatchValue buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
    return new MatchValueImpl(namespaceURI, localName, namespacePrefix);
  }

}
