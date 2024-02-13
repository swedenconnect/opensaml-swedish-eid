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
package se.swedenconnect.opensaml.sweid.saml2.authn.psc;

import java.util.List;

import javax.xml.namespace.QName;

import org.opensaml.saml.common.SAMLObject;

/**
 * Definition for the {@code PrincipalSelection} type:
 *
 * <pre>
 * {@code
 * <xs:element name="PrincipalSelection" type="psc:PrincipalSelectionType" />
 *
 * <xs:complexType name="PrincipalSelectionType">
 *   <xs:sequence>
 *     <xs:element maxOccurs="unbounded" name="MatchValue" type="psc:MatchValueType" minOccurs="1" />
 *   </xs:sequence>
 * </xs:complexType>
 * }
 * </pre>
 *
 * @author Martin Lindström (martin@idsec.se)
 */
public interface PrincipalSelection extends SAMLObject {

  /** Name of the element. */
  String DEFAULT_ELEMENT_LOCAL_NAME = "PrincipalSelection";

  /** Default element name. */
  QName DEFAULT_ELEMENT_NAME = new QName(PscConstants.PSC_NS, DEFAULT_ELEMENT_LOCAL_NAME, PscConstants.PSC_NS_PREFIX);

  /** Local name of the type */
  String TYPE_LOCAL_NAME = "PrincipalSelectionType";

  /** QName of the XSI type. */
  QName TYPE_NAME = new QName(PscConstants.PSC_NS, TYPE_LOCAL_NAME, PscConstants.PSC_NS_PREFIX);

  /** Name of the MatchValue element. */
  String MATCH_VALUE_LOCAL_NAME = "MatchValue";

  /**
   * Returns a reference to the list of match values.
   *
   * @return a list of match values
   */
  List<MatchValue> getMatchValues();

}
