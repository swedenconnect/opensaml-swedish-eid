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
package se.swedenconnect.opensaml.sweid.saml2.signservice.sap;

import javax.xml.namespace.QName;

import org.opensaml.core.xml.schema.XSString;
import org.opensaml.saml.common.SAMLObject;

/**
 * Definition of the SAP {@code ParameterType} type.
 * 
 * The following schema fragment defines the {@code <sap:SADRequest>} element:
 * 
 * <pre>
 * {@code 
 * <xs:complexType name="ParameterType">
 *   <xs:simpleContent>
 *     <xs:extension base="xs:string">
 *       <xs:attribute name="Name" type="xs:string" use="required" />
 *     </xs:extension>
 *   </xs:simpleContent>
 * </xs:complexType>}
 * </pre>
 * 
 * @author Martin Lindström (martin@idsec.se)
 */
public interface Parameter extends XSString, SAMLObject {

  /** Name of the element. */
  String DEFAULT_ELEMENT_LOCAL_NAME = "Parameter";

  /** Default element name. */
  QName DEFAULT_ELEMENT_NAME = new QName(SAPConstants.SAP_NS, DEFAULT_ELEMENT_LOCAL_NAME, SAPConstants.SAP_NS_PREFIX);

  /** Local name of the type */
  String TYPE_LOCAL_NAME = "ParameterType";

  /** QName of the XSI type. */
  QName TYPE_NAME = new QName(SAPConstants.SAP_NS, TYPE_LOCAL_NAME, SAPConstants.SAP_NS_PREFIX);

  /** Attribute label for the Name attribute. */
  String NAME_ATTR_NAME = "name";

  /**
   * Returns the "Name" attribute.
   * 
   * @return the name attribute
   */
  String getName();

  /**
   * Assigns the name attribute.
   * 
   * @param name
   *          the name attribute
   */
  void setName(final String name);

}
