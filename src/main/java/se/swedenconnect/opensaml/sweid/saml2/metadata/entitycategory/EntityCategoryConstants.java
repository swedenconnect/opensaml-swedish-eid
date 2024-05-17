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

import se.swedenconnect.opensaml.sweid.saml2.attribute.AttributeSetConstants;
import se.swedenconnect.opensaml.sweid.saml2.authn.LevelOfAssuranceUris;

import java.util.Arrays;
import java.util.List;

/**
 * Represents the Entity Categories defined by the Swedish eID Framework.
 *
 * @author Martin Lindström (martin@idsec.se)
 */
public class EntityCategoryConstants {

  /** The prefix for Service Entity categories. */
  public static final String SERVICE_ENTITY_CATEGORY_PREFIX = "http://id.elegnamnden.se/ec/";

  /** The prefix for Service Entity categories defined by Sweden Connect. */
  public static final String SERVICE_ENTITY_CATEGORY_PREFIX_SC = "http://id.swedenconnect.se/ec/";

  /** The prefix for Service Property categories. */
  public static final String SERVICE_PROPERTY_CATEGORY_PREFIX = "http://id.elegnamnden.se/sprop/";

  /** The prefix for Service Type categories. */
  public static final String SERVICE_TYPE_CATEGORY_PREFIX = "http://id.elegnamnden.se/st/";

  /** The prefix for Service Contract categories. */
  public static final String SERVICE_CONTRACT_CATEGORY_PREFIX = "http://id.swedenconnect.se/contract/";

  /** The prefix for General categories. */
  public static final String GENERAL_CATEGORY_PREFIX = "http://id.swedenconnect.se/general-ec/";

  /**
   * Service entity category: User authentication according to assurance level 2 and attribute release according to the
   * attribute set "Natural Personal Identity with Civic Registration Number".
   */
  public static final ServiceEntityCategory SERVICE_ENTITY_CATEGORY_LOA2_PNR = new ServiceEntityCategoryImpl(
      SERVICE_ENTITY_CATEGORY_PREFIX + "1.0/loa2-pnr",
      Arrays.asList(LevelOfAssuranceUris.AUTHN_CONTEXT_URI_LOA2,
          LevelOfAssuranceUris.AUTHN_CONTEXT_URI_UNCERTIFIED_LOA2),
      AttributeSetConstants.ATTRIBUTE_SET_NATURAL_PERSON_WITH_PERSONAL_ID);

  /**
   * Service entity category: User authentication according to assurance level 3 and attribute release according to the
   * attribute set "Natural Personal Identity with Civic Registration Number".
   */
  public static final ServiceEntityCategory SERVICE_ENTITY_CATEGORY_LOA3_PNR = new ServiceEntityCategoryImpl(
      SERVICE_ENTITY_CATEGORY_PREFIX + "1.0/loa3-pnr",
      Arrays.asList(LevelOfAssuranceUris.AUTHN_CONTEXT_URI_LOA3,
          LevelOfAssuranceUris.AUTHN_CONTEXT_URI_UNCERTIFIED_LOA3),
      AttributeSetConstants.ATTRIBUTE_SET_NATURAL_PERSON_WITH_PERSONAL_ID);

  /**
   * Service entity category: User authentication according to assurance level 4 and attribute release according to the
   * attribute set "Natural Personal Identity with Civic Registration Number".
   */
  public static final ServiceEntityCategory SERVICE_ENTITY_CATEGORY_LOA4_PNR = new ServiceEntityCategoryImpl(
      SERVICE_ENTITY_CATEGORY_PREFIX + "1.0/loa4-pnr", List.of(LevelOfAssuranceUris.AUTHN_CONTEXT_URI_LOA4),
      AttributeSetConstants.ATTRIBUTE_SET_NATURAL_PERSON_WITH_PERSONAL_ID);

  /**
   * Service entity category: For asserting a Swedish identity to a foreign service provider via the Swedish eIDAS Proxy
   * Service. This entity category MUST NOT be set by any entity other than Identity Provider providing identity
   * assertions to the Swedish eIDAS Proxy Service and by the Swedish eIDAS Proxy Service itself.
   * <p>
   * Note that the Identity Providers release attributes according to the "Natural Personal Identity with Civic
   * Registration Number" attribute set. It is the responsibility of the Swedish eIDAS Proxy Service to transform these
   * attributes into eIDAS attributes.
   * </p>
   */
  public static final ServiceEntityCategory SERVICE_ENTITY_CATEGORY_EIDAS_PNR_DELIVERY = new ServiceEntityCategoryImpl(
      SERVICE_ENTITY_CATEGORY_PREFIX + "1.0/eidas-pnr-delivery", null,
      AttributeSetConstants.ATTRIBUTE_SET_NATURAL_PERSON_WITH_PERSONAL_ID);

  /**
   * Service entity category: User authentication according to any of the eIDAS assurance levels and attribute release
   * according to "eIDAS Natural Person Attribute Set".
   */
  public static final ServiceEntityCategory SERVICE_ENTITY_CATEGORY_EIDAS_NATURAL_PERSON =
      new ServiceEntityCategoryImpl(
          SERVICE_ENTITY_CATEGORY_PREFIX + "1.0/eidas-naturalperson", null,
          AttributeSetConstants.ATTRIBUTE_SET_EIDAS_NATURAL_PERSON);

  /**
   * Service entity category: User authentication according to LoA 2 and attributes release according to "Organizational
   * Identity for Natural Persons" (http://id.elegnamnden.se/ap/1.0/org-person-01).
   */
  public static final ServiceEntityCategory SERVICE_ENTITY_CATEGORY_LOA2_ORGID = new ServiceEntityCategoryImpl(
      SERVICE_ENTITY_CATEGORY_PREFIX_SC + "1.0/loa2-orgid", Arrays.asList(LevelOfAssuranceUris.AUTHN_CONTEXT_URI_LOA2,
      LevelOfAssuranceUris.AUTHN_CONTEXT_URI_UNCERTIFIED_LOA2,
      LevelOfAssuranceUris.AUTHN_CONTEXT_URI_LOA2_NONRESIDENT),
      AttributeSetConstants.ATTRIBUTE_SET_ORGANIZATIONAL_IDENTITY_FOR_NATURAL_PERSONS);

  /**
   * Service entity category: User authentication according to LoA 3 and attributes release according to "Organizational
   * Identity for Natural Persons" (http://id.elegnamnden.se/ap/1.0/org-person-01).
   */
  public static final ServiceEntityCategory SERVICE_ENTITY_CATEGORY_LOA3_ORGID = new ServiceEntityCategoryImpl(
      SERVICE_ENTITY_CATEGORY_PREFIX_SC + "1.0/loa3-orgid", Arrays.asList(LevelOfAssuranceUris.AUTHN_CONTEXT_URI_LOA3,
      LevelOfAssuranceUris.AUTHN_CONTEXT_URI_UNCERTIFIED_LOA3,
      LevelOfAssuranceUris.AUTHN_CONTEXT_URI_LOA3_NONRESIDENT),
      AttributeSetConstants.ATTRIBUTE_SET_ORGANIZATIONAL_IDENTITY_FOR_NATURAL_PERSONS);

  /**
   * Service entity category: User authentication according to LoA 4 and attributes release according to "Organizational
   * Identity for Natural Persons" (http://id.elegnamnden.se/ap/1.0/org-person-01).
   */
  public static final ServiceEntityCategory SERVICE_ENTITY_CATEGORY_LOA4_ORGID = new ServiceEntityCategoryImpl(
      SERVICE_ENTITY_CATEGORY_PREFIX_SC + "1.0/loa4-orgid", Arrays.asList(
      LevelOfAssuranceUris.AUTHN_CONTEXT_URI_LOA4, LevelOfAssuranceUris.AUTHN_CONTEXT_URI_LOA4_NONRESIDENT),
      AttributeSetConstants.ATTRIBUTE_SET_ORGANIZATIONAL_IDENTITY_FOR_NATURAL_PERSONS);

  /**
   * Service entity category: User authentication according to LoA 2 and attributes release according to "Natural
   * Personal Identity without Civic Registration Number" (http://id.elegnamnden.se/ap/1.0/natural-person-01).
   */
  public static final ServiceEntityCategory SERVICE_ENTITY_CATEGORY_LOA2_NAME = new ServiceEntityCategoryImpl(
      SERVICE_ENTITY_CATEGORY_PREFIX_SC + "1.0/loa2-name", Arrays.asList(LevelOfAssuranceUris.AUTHN_CONTEXT_URI_LOA2,
      LevelOfAssuranceUris.AUTHN_CONTEXT_URI_UNCERTIFIED_LOA2,
      LevelOfAssuranceUris.AUTHN_CONTEXT_URI_LOA2_NONRESIDENT),
      AttributeSetConstants.ATTRIBUTE_SET_NATURAL_PERSON_NO_PERSONAL_ID);

  /**
   * Service entity category: User authentication according to LoA 3 and attributes release according to "Natural
   * Personal Identity without Civic Registration Number" (http://id.elegnamnden.se/ap/1.0/natural-person-01).
   */
  public static final ServiceEntityCategory SERVICE_ENTITY_CATEGORY_LOA3_NAME = new ServiceEntityCategoryImpl(
      SERVICE_ENTITY_CATEGORY_PREFIX_SC + "1.0/loa3-name", Arrays.asList(LevelOfAssuranceUris.AUTHN_CONTEXT_URI_LOA3,
      LevelOfAssuranceUris.AUTHN_CONTEXT_URI_UNCERTIFIED_LOA3,
      LevelOfAssuranceUris.AUTHN_CONTEXT_URI_LOA3_NONRESIDENT),
      AttributeSetConstants.ATTRIBUTE_SET_NATURAL_PERSON_NO_PERSONAL_ID);

  /**
   * Service entity category: User authentication according to LoA 4 and attributes release according to "Natural
   * Personal Identity without Civic Registration Number" (http://id.elegnamnden.se/ap/1.0/natural-person-01).
   */
  public static final ServiceEntityCategory SERVICE_ENTITY_CATEGORY_LOA4_NAME = new ServiceEntityCategoryImpl(
      SERVICE_ENTITY_CATEGORY_PREFIX_SC + "1.0/loa4-name", Arrays.asList(
      LevelOfAssuranceUris.AUTHN_CONTEXT_URI_LOA4, LevelOfAssuranceUris.AUTHN_CONTEXT_URI_LOA4_NONRESIDENT),
      AttributeSetConstants.ATTRIBUTE_SET_NATURAL_PERSON_NO_PERSONAL_ID);

  /**
   * Service property category: For a providing service, i.e. an Identity Provider, inclusion of the mobile-auth
   * category states that the Identity Provider supports authentication using mobile devices, and that the end-user
   * interface of the Identity Provider is adapted for mobile clients.
   * <p>
   * Note that an Identity Provider may of course support authentication for both desktop and mobile users. In these
   * cases the service must be able to display end user interfaces for both types of clients.
   * </p>
   */
  public static final EntityCategory SERVICE_PROPERTY_CATEGORY_MOBILE_AUTH = new EntityCategoryImpl(
      SERVICE_PROPERTY_CATEGORY_PREFIX + "1.0/mobile-auth", EntityCategoryType.SERVICE_PROPERTY);

  /**
   * Service property category: A service property declaring that the service is adapted to support Sole Control
   * Assurance Level 2 (SCAL2) in accordance with the "Signature Activation Protocol for Federated Signing"
   * specification.
   * <p>
   * For a providing service, i.e. an Identity Provider, inclusion of the scal2 service property states that the
   * Identity Provider will return a "SAD" in response to a {@code SADRequest} in an authentication requests from a
   * signing service.
   * </p>
   * <p>
   * For consuming services, Signature Services MAY include this service property if all authentication requests from
   * the particular Signature Service include a {@code SADRequest} extension. A Service Provider that is not declared as
   * a Signature Service MUST NOT include this service property in its metadata.
   * </p>
   */
  public static final EntityCategory SERVICE_PROPERTY_CATEGORY_SCAL2 = new EntityCategoryImpl(
      SERVICE_PROPERTY_CATEGORY_PREFIX + "1.0/scal2", EntityCategoryType.SERVICE_PROPERTY);

  /**
   * Service type category: A service type for a Service Provider that provides electronic signature services within the
   * Swedish eID framework.
   */
  public static final EntityCategory SERVICE_TYPE_CATEGORY_SIGSERVICE = new EntityCategoryImpl(
      SERVICE_TYPE_CATEGORY_PREFIX + "1.0/sigservice", EntityCategoryType.SERVICE_TYPE);

  /**
   * Service type category: A service type that indicates that a Service Provider is a "public sector" SP. This
   * category MUST be used by public sector Service Providers wishing to use eIDAS authentication so that the Swedish
   * eIDAS connector may include this information in the eIDAS authentication request.
   */
  public static final EntityCategory SERVICE_TYPE_CATEGORY_PUBLIC_SECTOR_SP = new EntityCategoryImpl(
      SERVICE_TYPE_CATEGORY_PREFIX + "1.0/public-sector-sp", EntityCategoryType.SERVICE_TYPE);

  /**
   * Service type category: A service type that indicates that a Service Provider is a "private sector" SP. This
   * category MUST be used by public sector Service Providers wishing to use eIDAS authentication so that the Swedish
   * eIDAS connector may include this information in the eIDAS authentication request.
   */
  public static final EntityCategory SERVICE_TYPE_CATEGORY_PRIVATE_SECTOR_SP = new EntityCategoryImpl(
      SERVICE_TYPE_CATEGORY_PREFIX + "1.0/private-sector-sp", EntityCategoryType.SERVICE_TYPE);

  /**
   * Service contract category: A service contract type that indicates that the holder has signed the Sweden Connect
   * federation contract.
   */
  public static final EntityCategory SERVICE_CONTRACT_CATEGORY_SWEDEN_CONNECT = new EntityCategoryImpl(
      SERVICE_CONTRACT_CATEGORY_PREFIX + "sc/sweden-connect", EntityCategoryType.SERVICE_CONTRACT);

  /**
   * Service contract category: A service contract type that indicates that the holder has signed the "eID system of
   * choice 2017" (Valfrihetssystem 2017) contract.
   */
  public static final EntityCategory SERVICE_CONTRACT_CATEGORY_EID_CHOICE_2017 = new EntityCategoryImpl(
      SERVICE_CONTRACT_CATEGORY_PREFIX + "sc/eid-choice-2017", EntityCategoryType.SERVICE_CONTRACT);

  /**
   * General category: A category that indicates that secure authenticator binding is requested (SP) or is supported
   * (IdP).
   */
  public static final EntityCategory GENERAL_CATEGORY_SECURE_AUTHENTICATOR_BINDING = new EntityCategoryImpl(
      GENERAL_CATEGORY_PREFIX + "1.0/secure-authenticator-binding", EntityCategoryType.GENERAL);

  /**
   * General category: A category that indicates that the SP accepts a Swedish coordination number.
   */
  public static final EntityCategory GENERAL_CATEGORY_ACCEPTS_COORDINATION_NUMBER = new EntityCategoryImpl(
      GENERAL_CATEGORY_PREFIX + "1.0/accepts-coordination-number", EntityCategoryType.GENERAL);

  /**
   * General category: A category that indicates that a declaring IdP supports the {@code umsg:UserMessage}
   * authentication request extension.
   */
  public static final EntityCategory GENERAL_CATEGORY_SUPPORTS_USER_MESSAGE = new EntityCategoryImpl(
      GENERAL_CATEGORY_PREFIX + "1.0/supports-user-message", EntityCategoryType.GENERAL);

  /*
   * Hidden.
   */
  private EntityCategoryConstants() {
  }

}
