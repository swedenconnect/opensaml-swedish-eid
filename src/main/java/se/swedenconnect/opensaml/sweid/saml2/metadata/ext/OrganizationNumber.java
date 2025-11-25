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
package se.swedenconnect.opensaml.sweid.saml2.metadata.ext;

import net.shibboleth.shared.annotation.constraint.NotEmpty;
import org.opensaml.core.xml.schema.XSString;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.common.xml.SAMLConstants;

import javax.annotation.Nonnull;
import javax.xml.namespace.QName;

/**
 * SAML metadata extension for the {@code mdorgext:OrganizationNumber} element.
 *
 * <pre>
 * {@code
 * <xs:element name="OrganizationNumber" type="mdorgext:OrganizationNumberType" />
 *
 * <xs:simpleType name="OrganizationNumberType">
 *   <xs:annotation>
 *     <xs:documentation>An generic organization number</xs:documentation>
 *   </xs:annotation>
 *   <xs:restriction base="xs:string" />
 * </xs:simpleType>
 * }
 * </pre>
 *
 * @author Martin Lindström
 */
public interface OrganizationNumber extends SAMLObject, XSString {

  /** Element local name. */
  @Nonnull
  @NotEmpty
  String DEFAULT_ELEMENT_LOCAL_NAME = "OrganizationNumber";

  /** Default element name. */
  @Nonnull
  QName DEFAULT_ELEMENT_NAME = new QName(
      MetadataExtConstants.MDORGEXT_NS, DEFAULT_ELEMENT_LOCAL_NAME, MetadataExtConstants.MDORGEXT_NS_PREFIX);

}
