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
package se.swedenconnect.opensaml.sweid.saml2.authn.psc;

import javax.xml.namespace.QName;

import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.schema.XSString;
import org.opensaml.saml.common.SAMLObject;

/**
 * Definition for the {@code MatchValue} type:
 * 
 * <pre>
 * {@code
 * <xs:complexType name="MatchValueType">
 *   <xs:simpleContent>
 *     <xs:extension base="xs:string">
 *       <xs:attribute name="NameFormat" type="xs:anyURI" default="urn:oasis:names:tc:SAML:2.0:attrname-format:uri" />
 *       <xs:attribute name="Name" type="xs:string" use="required" />
 *       <xs:anyAttribute namespace="##any" />
 *     </xs:extension>
 *   </xs:simpleContent>
 * </xs:complexType>
 * }
 * </pre>
 * 
 * @author Martin Lindström (martin@idsec.se)
 */
public interface MatchValue extends XSString, SAMLObject, AttributeExtensibleXMLObject {

  /** Name of the element. */
  String DEFAULT_ELEMENT_LOCAL_NAME = "MatchValue";

  /** Default element name. */
  QName DEFAULT_ELEMENT_NAME = new QName(PscConstants.PSC_NS, DEFAULT_ELEMENT_LOCAL_NAME, PscConstants.PSC_NS_PREFIX);

  /** Local name of the type */
  String TYPE_LOCAL_NAME = "MatchValueType";

  /** QName of the XSI type. */
  QName TYPE_NAME = new QName(PscConstants.PSC_NS, TYPE_LOCAL_NAME, PscConstants.PSC_NS_PREFIX);

  /** Attribute label for the NameFormat attribute. */
  String NAME_FORMAT_ATTR_NAME = "NameFormat";

  /** Attribute label for the Name attribute. */
  String NAME_ATTR_NAME = "Name";

  /**
   * Returns the {@code NameFormat} attribute.
   * 
   * @return the name format attribute
   */
  String getNameFormat();

  /**
   * Assigns the {@code NameFormat} attribute.
   * 
   * @param nameFormat
   *          the name format
   */
  void setNameFormat(final String nameFormat);

  /**
   * Returns the {@code Name} attribute.
   * 
   * @return the name
   */
  String getName();

  /**
   * Assigns the {@code Name} attribute.
   * 
   * @param name
   *          the name
   */
  void setName(final String name);

}
