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
package se.swedenconnect.opensaml.sweid.saml2.attribute;

import se.swedenconnect.opensaml.saml2.attribute.AttributeTemplate;

/**
 * Defines all Attribute Set defined in section 2 of "Attribute Specification for the Swedish eID Framework".
 * 
 * @author Martin Lindström (martin@idsec.se)
 */
public class AttributeSetConstants {

  /**
   * Pseudonym Identity - This attribute set specifies the condition where there are no mandatory or recommended
   * attributes.
   * <p>
   * <b>Typical use:</b> In a pseudonym attribute release policy that just provides a persistent NameID identifier in
   * the assertion but no attributes.
   * </p>
   */
  public static final AttributeSet ATTRIBUTE_SET_PSEUDONYM_IDENTITY = new AttributeSetImpl("ELN-AP-Pseudonym-01",
    "http://id.elegnamnden.se/ap/1.0/pseudonym-01", "Pseudonym Identity", null, null);

  /**
   * Natural Personal Identity without Civic Registration Number - The “Personal Identity without Civic Registration
   * Number” attribute set provides basic natural person information without revealing the civic registration number of
   * the subject.
   * <p>
   * <b>Typical use:</b> In an attribute release policy that provides basic user name information together with a
   * persistent NameID identifier in the assertion.
   * </p>
   */
  public static final AttributeSet ATTRIBUTE_SET_NATURAL_PERSON_NO_PERSONAL_ID = new AttributeSetImpl(
    "ELN-AP-NaturalPerson-01", "http://id.elegnamnden.se/ap/1.0/natural-person-01",
    "Natural Personal Identity without Civic Registration Number",
    new AttributeTemplate[] {
        AttributeConstants.ATTRIBUTE_TEMPLATE_DISPLAY_NAME,
        AttributeConstants.ATTRIBUTE_TEMPLATE_SN,
        AttributeConstants.ATTRIBUTE_TEMPLATE_GIVEN_NAME },
    null);

  /**
   * Natural Personal Identity with Civic Registration Number - The “Personal Identity with Civic Registration Number”
   * attribute set provides basic personal identity information including a Swedish civic registration number of the
   * subject.
   * <p>
   * <b>Typical use:</b> In an attribute release policy that provides basic user name information together with the
   * person’s Swedish civic registration number.
   * </p>
   */
  public static final AttributeSet ATTRIBUTE_SET_NATURAL_PERSON_WITH_PERSONAL_ID = new AttributeSetImpl(
    "ELN-AP-Pnr-01", "http://id.elegnamnden.se/ap/1.0/pnr-01",
    "Natural Personal Identity with Civic Registration Number",
    new AttributeTemplate[] {
        AttributeConstants.ATTRIBUTE_TEMPLATE_PERSONAL_IDENTITY_NUMBER,
        AttributeConstants.ATTRIBUTE_TEMPLATE_SN,
        AttributeConstants.ATTRIBUTE_TEMPLATE_GIVEN_NAME,
        AttributeConstants.ATTRIBUTE_TEMPLATE_DISPLAY_NAME },
    new AttributeTemplate[] {
        AttributeConstants.ATTRIBUTE_TEMPLATE_DATE_OF_BIRTH
    });

  /**
   * Organizational Identity for Natural Persons - The “Organizational Identity for Natural Persons” attribute set
   * provides basic organizational identity information about a person. The organizational identity does not necessarily
   * imply that the subject has any particular relationship with or standing within the organization, but rather that
   * this identity has been issued/provided by that organization for any particular reason (employee, customer,
   * consultant, etc.).
   * <p>
   * <b>Typical use:</b> In an attribute release policy that provides basic organizational identity information about a
   * natural person.
   * </p>
   */
  public static final AttributeSet ATTRIBUTE_SET_ORGANIZATIONAL_IDENTITY_FOR_NATURAL_PERSONS = new AttributeSetImpl(
    "ELN-AP-OrgPerson-01", "http://id.elegnamnden.se/ap/1.0/org-person-01",
    "Organizational Identity for Natural Persons",
    new AttributeTemplate[] {
        AttributeConstants.ATTRIBUTE_TEMPLATE_DISPLAY_NAME,
        AttributeConstants.ATTRIBUTE_TEMPLATE_ORG_AFFILIATION,
        AttributeConstants.ATTRIBUTE_TEMPLATE_O },
    new AttributeTemplate[] {
        AttributeConstants.ATTRIBUTE_TEMPLATE_ORGANIZATION_IDENTIFIER
    });

  /**
   * eIDAS Natural Person Attribute Set - The “eIDAS Natural Person Attribute Set” provides personal identity
   * information for a subject that has been authenticated via the eIDAS Framework.
   * <p>
   * <b>Typical use:</b> In an attribute release policy implemented by an eIDAS connector that provides a complete set
   * of attributes to a requesting Service Provider.
   * </p>
   */
  public static final AttributeSet ATTRIBUTE_SET_EIDAS_NATURAL_PERSON = new AttributeSetImpl(
    "ELN-AP-eIDAS-NatPer-01", "http://id.elegnamnden.se/ap/1.0/eidas-natural-person-01",
    "eIDAS Natural Person Attribute Set", new AttributeTemplate[] {
        AttributeConstants.ATTRIBUTE_TEMPLATE_PRID,
        AttributeConstants.ATTRIBUTE_TEMPLATE_PRID_PERSISTENCE,
        AttributeConstants.ATTRIBUTE_TEMPLATE_EIDAS_PERSON_IDENTIFIER,
        AttributeConstants.ATTRIBUTE_TEMPLATE_DATE_OF_BIRTH,
        AttributeConstants.ATTRIBUTE_TEMPLATE_SN,
        AttributeConstants.ATTRIBUTE_TEMPLATE_GIVEN_NAME,
        AttributeConstants.ATTRIBUTE_TEMPLATE_TRANSACTION_IDENTIFIER,
        AttributeConstants.ATTRIBUTE_TEMPLATE_C },
    new AttributeTemplate[] {
        AttributeConstants.ATTRIBUTE_TEMPLATE_BIRTH_NAME,
        AttributeConstants.ATTRIBUTE_TEMPLATE_PLACE_OF_BIRTH,
        AttributeConstants.ATTRIBUTE_TEMPLATE_EIDAS_NATURAL_PERSON_ADDRESS,
        AttributeConstants.ATTRIBUTE_TEMPLATE_GENDER,
        AttributeConstants.ATTRIBUTE_TEMPLATE_MAPPED_PERSONAL_IDENTITY_NUMBER,
        AttributeConstants.ATTRIBUTE_TEMPLATE_PERSONAL_IDENTITY_NUMBER_BINDING
    });

  /*
   * Hidden constructor.
   */
  private AttributeSetConstants() {
  }

}
