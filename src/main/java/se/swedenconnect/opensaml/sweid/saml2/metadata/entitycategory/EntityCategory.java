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
package se.swedenconnect.opensaml.sweid.saml2.metadata.entitycategory;

/**
 * Represents an Entity Category according to the Swedish eID Framework.
 * 
 * @author Martin Lindström (martin@idsec.se)
 */
public interface EntityCategory {

  /**
   * Each entity category is assigned an unique URI. This method returns this value.
   * 
   * @return the entity category URI
   */
  String getUri();

  /**
   * Returns the type of entity category.
   * 
   * @return the type of entity category
   */
  EntityCategoryType getType();

}
