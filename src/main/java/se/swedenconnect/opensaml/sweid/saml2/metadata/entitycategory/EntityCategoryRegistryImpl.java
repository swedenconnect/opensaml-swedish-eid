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

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link EntityCategoryRegistry} interface.
 *
 * @author Martin Lindström (martin@idsec.se)
 */
public class EntityCategoryRegistryImpl implements EntityCategoryRegistry {

  /** The registered entity categories. */
  private List<EntityCategory> entityCategories;

  /**
   * Constructor.
   *
   * @param entityCategories registered entity categories
   */
  public EntityCategoryRegistryImpl(List<EntityCategory> entityCategories) {
    this.entityCategories = entityCategories;
  }

  /** {@inheritDoc} */
  @Override
  public Optional<EntityCategory> getEntityCategory(String uri) {
    return this.entityCategories.stream()
        .filter(e -> e.getUri().equals(uri))
        .findFirst();
  }

  /** {@inheritDoc} */
  @Override
  public List<EntityCategory> getEntityCategories() {
    return Collections.unmodifiableList(this.entityCategories);
  }

  /** {@inheritDoc} */
  @Override
  public List<ServiceEntityCategory> getServiceEntityCategories() {
    return this.entityCategories.stream()
        .filter(e -> EntityCategoryType.SERVICE_ENTITY.equals(e.getType()))
        .map(ServiceEntityCategory.class::cast)
        .collect(Collectors.toList());
  }

}
