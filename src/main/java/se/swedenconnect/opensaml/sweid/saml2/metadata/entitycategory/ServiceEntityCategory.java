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
package se.swedenconnect.opensaml.sweid.saml2.metadata.entitycategory;

import java.util.List;

import se.swedenconnect.opensaml.sweid.saml2.attribute.AttributeSet;

/**
 * Represents a "Service Entity Category".
 *
 * @author Martin Lindström (martin@idsec.se)
 */
public interface ServiceEntityCategory extends EntityCategory {

  /**
   * Returns the Level of Assurance URI:s associated with this service entity category.
   * <p>
   * The identifier indicates that only services conforming to at least the specified level of assurance have the
   * capability to satisfy the security requirements of the Service Provider. An Identity Provider declaring this
   * Service Entity Category MUST be able to provide this level of assurance.
   * </p>
   *
   * @return the URI:s representing the Level of Assurance, or null if no level of assurance is associated with the
   *           category
   */
  List<String> getLevelOfAssuranceUris();

  /**
   * Returns the attribute set tied to this service entity category.
   * <p>
   * The attribute set Indicates that only services that implement attribute release according to the identified
   * attribute set have the capability to satisfy the minimum attribute requirements of the Service Provider. An
   * Identity Provider declaring this Service Entity Category MUST be able to provide these attributes.
   * </p>
   *
   * @return the attribute set
   */
  AttributeSet getAttributeSet();

}
