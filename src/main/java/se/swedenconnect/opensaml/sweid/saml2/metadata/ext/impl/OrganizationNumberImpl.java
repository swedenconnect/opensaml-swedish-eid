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
package se.swedenconnect.opensaml.sweid.saml2.metadata.ext.impl;

import org.opensaml.core.xml.schema.impl.XSStringImpl;
import se.swedenconnect.opensaml.sweid.saml2.metadata.ext.OrganizationNumber;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Implementation of the {@link OrganizationNumber} interface.
 *
 * @author Martin Lindström
 */
public class OrganizationNumberImpl extends XSStringImpl implements OrganizationNumber {

  /**
   * Constructor.
   *
   * @param namespaceURI the namespace the element is in
   * @param elementLocalName the local name of the XML element this Object represents
   * @param namespacePrefix the prefix for the given namespace
   */
  protected OrganizationNumberImpl(@Nullable final String namespaceURI,
      @Nonnull final String elementLocalName, @Nullable final String namespacePrefix) {
    super(namespaceURI, elementLocalName, namespacePrefix);
  }

}
