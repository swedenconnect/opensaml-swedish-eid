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

import javax.xml.namespace.QName;

/**
 * Definition for the {@code RequestedPrincipalSelection} type:
 *
 * <pre>
 * {@code
 * <xs:element name="RequestedPrincipalSelection" type="psc:RequestedPrincipalSelectionType" />
 *
 * <xs:complexType name="RequestedPrincipalSelectionType">
 *   <xs:complexContent>
 *     <xs:extension base="psc:PrincipalSelectionType" />
 *   </xs:complexContent>
 * </xs:complexType>
 * }
 * </pre>
 *
 * @author Martin Lindström (martin@idsec.se)
 */
public interface RequestedPrincipalSelection extends PrincipalSelection {

  /** Name of the element. */
  String DEFAULT_ELEMENT_LOCAL_NAME = "RequestedPrincipalSelection";

  /** Default element name. */
  QName DEFAULT_ELEMENT_NAME = new QName(PscConstants.PSC_NS, DEFAULT_ELEMENT_LOCAL_NAME, PscConstants.PSC_NS_PREFIX);

  /** Local name of the type */
  String TYPE_LOCAL_NAME = "RequestedPrincipalSelectionType";

  /** QName of the XSI type. */
  QName TYPE_NAME = new QName(PscConstants.PSC_NS, TYPE_LOCAL_NAME, PscConstants.PSC_NS_PREFIX);

}
