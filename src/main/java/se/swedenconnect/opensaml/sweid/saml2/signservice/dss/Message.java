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
package se.swedenconnect.opensaml.sweid.saml2.signservice.dss;

import javax.xml.namespace.QName;

import org.opensaml.core.xml.schema.XSBase64Binary;

/**
 * XMLObject representing the {@code Message} element that is a child to {@link SignMessage}.
 *
 * @author Martin Lindström (martin@idsec.se)
 */
public interface Message extends XSBase64Binary {

  /** Element local name. */
  String DEFAULT_ELEMENT_LOCAL_NAME = "Message";

  /** Default element name. */
  QName DEFAULT_ELEMENT_NAME = new QName(DssExtensionsConstants.SWEID_DSS_EXT_NS,
      DEFAULT_ELEMENT_LOCAL_NAME, DssExtensionsConstants.SWEID_DSS_EXT_PREFIX);

  /**
   * Returns string content of the {@code Message} element, i.e., its Base64 decoded form.
   *
   * @return the message content
   */
  String getContent();

  /**
   * Assigns the {@code Message} element by assigning the text that it should hold. The method will Base64 encode the
   * text.
   *
   * @param messageContent content of the Message element
   */
  void setContent(final String messageContent);

}
