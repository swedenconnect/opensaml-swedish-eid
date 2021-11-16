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
package se.swedenconnect.opensaml.sweid.saml2.signservice.dss;

import javax.xml.namespace.QName;

import org.opensaml.saml.saml2.core.EncryptedElementType;

/**
 * XMLObject representing the {@code EncryptedMessage} element that is a child to {@link SignMessage}.
 * 
 * @author Martin Lindström (martin@idsec.se)
 */
public interface EncryptedMessage extends EncryptedElementType {
  
  /** Element local name. */
  String DEFAULT_ELEMENT_LOCAL_NAME = "EncryptedMessage";

  /** Default element name. */
  QName DEFAULT_ELEMENT_NAME = new QName(DssExtensionsConstants.SWEID_DSS_EXT_NS,
    DEFAULT_ELEMENT_LOCAL_NAME, DssExtensionsConstants.SWEID_DSS_EXT_PREFIX);

}
