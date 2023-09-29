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

import java.nio.charset.StandardCharsets;

import org.opensaml.core.xml.XMLRuntimeException;
import org.opensaml.core.xml.schema.impl.XSBase64BinaryImpl;

import net.shibboleth.shared.codec.Base64Support;
import net.shibboleth.shared.codec.DecodingException;
import net.shibboleth.shared.codec.EncodingException;
import se.swedenconnect.opensaml.sweid.saml2.signservice.dss.Message;

/**
 * Implementation of the {@link Message} interface.
 *
 * @author Martin Lindström (martin@idsec.se)
 */
public class MessageImpl extends XSBase64BinaryImpl implements Message {

  /**
   * Constructor creating an Message object given the namespace URI, local element name and namespace prefix.
   *
   * @param namespaceURI the namespace URI.
   * @param elementLocalName the element local name.
   * @param namespacePrefix the name space prefix.
   */
  protected MessageImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
    super(namespaceURI, elementLocalName, namespacePrefix);
  }

  /** {@inheritDoc} */
  @Override
  public String getContent() {
    try {
      return this.getValue() != null ? new String(Base64Support.decode(this.getValue()), StandardCharsets.UTF_8) : null;
    }
    catch (DecodingException e) {
      throw new XMLRuntimeException(e);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void setContent(final String messageContent) {
    if (messageContent != null) {
      try {
        this.setValue(Base64Support.encode(messageContent.getBytes(StandardCharsets.UTF_8), false));
      }
      catch (EncodingException e) {
        throw new XMLRuntimeException(e);
      }
    }
    else {
      this.setValue(null);
    }
  }

}
