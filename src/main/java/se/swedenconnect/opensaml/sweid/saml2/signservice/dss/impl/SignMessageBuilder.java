/*
 * Copyright 2016-2023 Sweden Connect
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

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;

import se.swedenconnect.opensaml.sweid.saml2.signservice.dss.SignMessage;

/**
 * Builder class for {@link SignMessage}.
 *
 * @author Martin Lindström (martin@idsec.se)
 */
public class SignMessageBuilder extends AbstractSAMLObjectBuilder<SignMessage> {

  /** {@inheritDoc} */
  @Override
  public SignMessage buildObject() {
    return buildObject(SignMessage.DEFAULT_ELEMENT_NAME.getNamespaceURI(),
        SignMessage.DEFAULT_ELEMENT_NAME.getLocalPart(), SignMessage.DEFAULT_ELEMENT_NAME.getPrefix());
  }

  /** {@inheritDoc} */
  @Override
  public SignMessage buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
    return new SignMessageImpl(namespaceURI, localName, namespacePrefix);
  }

}
