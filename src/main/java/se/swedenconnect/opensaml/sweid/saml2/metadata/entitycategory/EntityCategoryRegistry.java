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
package se.swedenconnect.opensaml.sweid.saml2.metadata.entitycategory;

import java.util.List;
import java.util.Optional;

/**
 * A registry that handles all "registered" entity categories. It is used to find the definition of an entity category
 * based on its URI.
 *
 * @author Martin Lindström (martin@idsec.se)
 */
public interface EntityCategoryRegistry {

  /**
   * Given an URI, the method will find the {@link EntityCategory} object that is registered for this identifier.
   *
   * @param uri the entity category URI
   * @return the {@code EntityCategory} object
   */
  Optional<EntityCategory> getEntityCategory(String uri);

  /**
   * Returns a list of all registered entity categories.
   *
   * @return a list of all registered entity categories
   */
  List<EntityCategory> getEntityCategories();

  /**
   * Returns a list of all registered entity categories that are of the type "Service entity category".
   *
   * @return a list of all registered service entity categories
   */
  List<ServiceEntityCategory> getServiceEntityCategories();

}
