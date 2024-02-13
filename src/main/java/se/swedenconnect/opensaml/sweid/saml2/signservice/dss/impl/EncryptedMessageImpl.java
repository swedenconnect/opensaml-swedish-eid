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
package se.swedenconnect.opensaml.sweid.saml2.signservice.dss.impl;

import org.opensaml.saml.saml2.core.impl.EncryptedElementTypeImpl;

import se.swedenconnect.opensaml.sweid.saml2.signservice.dss.EncryptedMessage;

/**
 * Implementation of the {@link EncryptedMessage} interface.
 *
 * @author Martin Lindström (martin@idsec.se)
 */
public class EncryptedMessageImpl extends EncryptedElementTypeImpl implements EncryptedMessage {

  /**
   * Constructor creating an EncryptedMessage object given the namespace URI, local element name and namespace prefix.
   *
   * @param namespaceURI the namespace URI.
   * @param elementLocalName the element local name.
   * @param namespacePrefix the name space prefix.
   */
  protected EncryptedMessageImpl(final String namespaceURI, final String elementLocalName,
      final String namespacePrefix) {
    super(namespaceURI, elementLocalName, namespacePrefix);
  }

}
