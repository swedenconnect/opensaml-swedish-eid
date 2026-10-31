/*
 * Copyright 2016-2026 Sweden Connect
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

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.opensaml.core.xml.schema.impl.XSStringImpl;

import se.swedenconnect.opensaml.sweid.saml2.signservice.sap.Parameter;

/**
 * Implementation class for {@link Parameter}.
 *
 * @author Martin Lindström (martin@idsec.se)
 */
public class ParameterImpl extends XSStringImpl implements Parameter {

  /** The name attribute. */
  private String name;

  /**
   * Constructor.
   *
   * @param namespaceURI the namespace the element is in
   * @param elementLocalName the local name of the XML element this Object represents
   * @param namespacePrefix the prefix for the given namespace
   */
  protected ParameterImpl(@Nullable final String namespaceURI, @Nonnull final String elementLocalName,
      @Nullable final String namespacePrefix) {
    super(namespaceURI, elementLocalName, namespacePrefix);
  }

  /** {@inheritDoc} */
  @Nullable
  @Override
  public String getName() {
    return this.name;
  }

  /** {@inheritDoc} */
  @Override
  public void setName(@Nullable final String name) {
    this.name = this.prepareForAssignment(this.name, name);
  }

}
