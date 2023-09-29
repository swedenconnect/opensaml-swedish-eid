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
package se.swedenconnect.opensaml.sweid.saml2.metadata.entitycategory;

/**
 * Represents the different Entity Category types defined within the Swedish eiD Framework.
 *
 * @author Martin Lindström (martin@idsec.se)
 */
public enum EntityCategoryType {

  /**
   * Meaning for a consuming service: Each declared category represents an alternative set of requirements for the
   * service. Meaning for a providing service: Represents the ability to deliver assertions in accordance with each
   * declared category.
   */
  SERVICE_ENTITY(EntityCategoryConstants.SERVICE_ENTITY_CATEGORY_PREFIX,
      EntityCategoryConstants.SERVICE_ENTITY_CATEGORY_PREFIX_SC),
  /**
   * Meaning for a consuming service: Represents a property of this service. Meaning for a providing service: Represents
   * the ability to deliver assertions to a consuming service that has the declared property.
   */
  SERVICE_PROPERTY(EntityCategoryConstants.SERVICE_PROPERTY_CATEGORY_PREFIX),
  /**
   * Declares the type of service provided by a consuming service.
   */
  SERVICE_TYPE(EntityCategoryConstants.SERVICE_TYPE_CATEGORY_PREFIX),

  /**
   * Declares a service contract entity category.
   */
  SERVICE_CONTRACT(EntityCategoryConstants.SERVICE_CONTRACT_CATEGORY_PREFIX),

  /**
   * Declares a general entity category.
   */
  GENERAL(EntityCategoryConstants.GENERAL_CATEGORY_PREFIX);

  /**
   * Given an entity category URI, the method returns the type of entity category.
   *
   * @param uri entity category URI
   * @return the entity category type, or null if no match is found
   */
  public static EntityCategoryType getType(final String uri) {
    if (uri == null) {
      return null;
    }
    for (final EntityCategoryType type : EntityCategoryType.values()) {
      for (final String p : type.prefix) {
        if (uri.startsWith(p)) {
          return type;
        }
      }
    }
    return null;
  }

  /**
   * Predicate that tells if the supplied entity category URI is of the supplied entity category type.
   *
   * @param uri entity category URI
   * @param type the entity category type to test for
   * @return if the supplied URI is of the given type true is returned, otherwise false
   */
  public static boolean isType(final String uri, final EntityCategoryType type) {
    if (uri == null) {
      return false;
    }
    for (final String p : type.prefix) {
      if (uri.startsWith(p)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Hidden constructor.
   *
   * @param prefix the URI prefix(es) for the type
   */
  private EntityCategoryType(final String... prefix) {
    this.prefix = prefix;
  }

  /** The URI prefix(es) for the type. */
  private String[] prefix;

}
