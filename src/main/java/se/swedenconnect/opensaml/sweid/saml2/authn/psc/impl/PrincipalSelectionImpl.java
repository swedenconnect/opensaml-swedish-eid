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
package se.swedenconnect.opensaml.sweid.saml2.authn.psc.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.XMLObjectChildrenList;

import se.swedenconnect.opensaml.sweid.saml2.authn.psc.MatchValue;
import se.swedenconnect.opensaml.sweid.saml2.authn.psc.PrincipalSelection;

/**
 * Implementation class for the {@link PrincipalSelection} interface.
 *
 * @author Martin Lindström (martin@idsec.se)
 */
public class PrincipalSelectionImpl extends AbstractXMLObject implements PrincipalSelection {

  /** MatchValue children. */
  private final XMLObjectChildrenList<MatchValue> matchValues;

  /**
   * Constructor.
   *
   * @param namespaceURI the namespace the element is in
   * @param elementLocalName the local name of the XML element this Object represents
   * @param namespacePrefix the prefix for the given namespace
   */
  protected PrincipalSelectionImpl(final String namespaceURI, final String elementLocalName,
      final String namespacePrefix) {
    super(namespaceURI, elementLocalName, namespacePrefix);
    this.matchValues = new XMLObjectChildrenList<>(this);
  }

  /** {@inheritDoc} */
  @Override
  public List<XMLObject> getOrderedChildren() {
    final ArrayList<XMLObject> children = new ArrayList<XMLObject>();
    children.addAll(this.matchValues);
    return Collections.unmodifiableList(children);
  }

  /** {@inheritDoc} */
  @Override
  public List<MatchValue> getMatchValues() {
    return this.matchValues;
  }

}
