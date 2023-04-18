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

import java.util.Collections;
import java.util.List;

import se.swedenconnect.opensaml.sweid.saml2.attribute.AttributeSet;

/**
 * Implementation of the {@link ServiceEntityCategory} interface.
 * 
 * @author Martin Lindström (martin@idsec.se)
 */
public class ServiceEntityCategoryImpl extends EntityCategoryImpl implements ServiceEntityCategory {

  private static final long serialVersionUID = -8531917204430501608L;

  /** The level of assurance URI of this category. */
  private List<String> loaUris;

  /** The attribute profile for this category. */
  private AttributeSet attributeSet;

  /**
   * Default constructor.
   */
  public ServiceEntityCategoryImpl() {
    this.setType(EntityCategoryType.SERVICE_ENTITY);
  }

  /**
   * Constructor assigning the URI, Level of Assurance URI:s and the attribute set.
   * 
   * @param uri
   *          the unique URI
   * @param loaUris
   *          the Level of Assurance URI:s
   * @param attributeSet
   *          the attribute set
   */
  public ServiceEntityCategoryImpl(final String uri, final List<String> loaUris, final AttributeSet attributeSet) {
    super(uri, EntityCategoryType.SERVICE_ENTITY);
    this.loaUris = loaUris;
    this.attributeSet = attributeSet;
  }

  /** {@inheritDoc} */
  @Override
  public String getUri() {
    return this.uri;
  }

  /** {@inheritDoc} */
  @Override
  public EntityCategoryType getType() {
    return EntityCategoryType.SERVICE_ENTITY;
  }

  /** {@inheritDoc} */
  @Override
  public List<String> getLevelOfAssuranceUris() {
    return this.loaUris != null ? this.loaUris : Collections.emptyList();
  }

  /**
   * Sets the Level of Assurance URI:s associated with this service entity category.
   * 
   * @param loaUris
   *          the LoA URI:s
   */
  public void setLevelOfAssuranceUris(final List<String> loaUris) {
    this.loaUris = loaUris;
  }

  /** {@inheritDoc} */
  @Override
  public AttributeSet getAttributeSet() {
    return this.attributeSet;
  }

  /**
   * Assigns the attribute set tied to this service entity category.
   * 
   * @param attributeSet
   *          the attribute set to assign
   */
  public void setAttributeProfile(final AttributeSet attributeSet) {
    this.attributeSet = attributeSet;
  }

}
