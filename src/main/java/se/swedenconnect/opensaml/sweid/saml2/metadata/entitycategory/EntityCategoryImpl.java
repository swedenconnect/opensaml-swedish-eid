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

import se.swedenconnect.opensaml.sweid.LibraryVersion;

/**
 * Implementation of the {@link EntityCategory} interface.
 *
 * @author Martin Lindström (martin@idsec.se)
 */
public class EntityCategoryImpl implements EntityCategory {

  private static final long serialVersionUID = LibraryVersion.SERIAL_VERSION_UID;

  /** The unique URI for this category. */
  protected String uri;

  /** The type of entity category. */
  protected EntityCategoryType type;

  /**
   * Default constructor.
   */
  public EntityCategoryImpl() {
  }

  /**
   * Constructor assigning the unique URI and the category type.
   *
   * @param uri the URI
   * @param type the type
   */
  public EntityCategoryImpl(final String uri, final EntityCategoryType type) {
    this.uri = uri;
    this.type = type;
  }

  /** {@inheritDoc} */
  @Override
  public String getUri() {
    return this.uri;
  }

  /**
   * Each entity category is assigned an unique URI. This method assigns this value.
   *
   * @param uri the URI to assign.
   */
  public void setUri(final String uri) {
    this.uri = uri;
  }

  /** {@inheritDoc} */
  @Override
  public EntityCategoryType getType() {
    return this.type;
  }

  /**
   * Assigns the type of entity category.
   *
   * @param type the type to assign.
   */
  public void setType(final EntityCategoryType type) {
    this.type = type;
  }

}
