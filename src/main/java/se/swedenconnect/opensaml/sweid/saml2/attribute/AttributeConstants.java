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
 * Contains constants for all attributes defined in section 3.1 of
 * "Attribute Specification for the Swedish eID Framework".
 * 
 * @author Martin Lindström (martin@idsec.se)
 */
public class AttributeConstants {

  /** The attribute name for the "Surname" attribute (urn:oid:2.5.4.4). */
  public static final String ATTRIBUTE_NAME_SN = "urn:oid:2.5.4.4";

  /** The attribute friendly name for the "Surname" attribute (sn). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_SN = "sn";

  /** Attribute template for the "Surname" attribute. */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_SN = new AttributeTemplate(ATTRIBUTE_NAME_SN,
    ATTRIBUTE_FRIENDLY_NAME_SN);

  /** The attribute name for the "Given Name" attribute (urn:oid:2.5.4.42). */
  public static final String ATTRIBUTE_NAME_GIVEN_NAME = "urn:oid:2.5.4.42";

  /** The attribute friendly name for the "Given Name" attribute (givenName). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_GIVEN_NAME = "givenName";

  /** Attribute template for the "Given Name" attribute. */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_GIVEN_NAME = new AttributeTemplate(
    ATTRIBUTE_NAME_GIVEN_NAME, ATTRIBUTE_FRIENDLY_NAME_GIVEN_NAME);

  /** The attribute name for the "Display Name" attribute (urn:oid:2.16.840.1.113730.3.1.241). */
  public static final String ATTRIBUTE_NAME_DISPLAY_NAME = "urn:oid:2.16.840.1.113730.3.1.241";

  /** The attribute friendly name for the "Display Name" attribute (displayName). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_DISPLAY_NAME = "displayName";

  /** Attribute template for the "Display Name" attribute. */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_DISPLAY_NAME = new AttributeTemplate(
    ATTRIBUTE_NAME_DISPLAY_NAME, ATTRIBUTE_FRIENDLY_NAME_DISPLAY_NAME);

  /** The attribute name for the "Gender" attribute (urn:oid:1.3.6.1.5.5.7.9.3). */
  public static final String ATTRIBUTE_NAME_GENDER = "urn:oid:1.3.6.1.5.5.7.9.3";

  /** The attribute friendly name for the "Gender" attribute (gender). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_GENDER = "gender";

  /** Attribute template for the "Gender" attribute. */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_GENDER = new AttributeTemplate(ATTRIBUTE_NAME_GENDER,
    ATTRIBUTE_FRIENDLY_NAME_GENDER);

  /** The attribute name for the "National civic registration number" attribute (urn:oid:1.2.752.29.4.13). */
  public static final String ATTRIBUTE_NAME_PERSONAL_IDENTITY_NUMBER = "urn:oid:1.2.752.29.4.13";

  /** The attribute friendly name for the "National civic registration number" attribute (personalIdentityNumber). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_PERSONAL_IDENTITY_NUMBER = "personalIdentityNumber";

  /** Attribute template for the "National civic registration number" attribute. */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_PERSONAL_IDENTITY_NUMBER = new AttributeTemplate(
    ATTRIBUTE_NAME_PERSONAL_IDENTITY_NUMBER, ATTRIBUTE_FRIENDLY_NAME_PERSONAL_IDENTITY_NUMBER);
  
  /** The attribute name for the "Previous national civic registration number" attribute (urn:oid:1.2.752.201.3.15). */
  public static final String ATTRIBUTE_NAME_PREVIOUS_PERSONAL_IDENTITY_NUMBER = "urn:oid:1.2.752.201.3.15";

  /** The attribute friendly name for the "Previous national civic registration number" attribute (previousPersonalIdentityNumber). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_PREVIOUS_PERSONAL_IDENTITY_NUMBER = "previousPersonalIdentityNumber";

  /** Attribute template for the "Previous national civic registration number" attribute. */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_PREVIOUS_PERSONAL_IDENTITY_NUMBER = new AttributeTemplate(
    ATTRIBUTE_NAME_PREVIOUS_PERSONAL_IDENTITY_NUMBER, ATTRIBUTE_FRIENDLY_NAME_PREVIOUS_PERSONAL_IDENTITY_NUMBER);  

  /** The attribute name for the "Date of birth" attribute (urn:oid:1.3.6.1.5.5.7.9.1). */
  public static final String ATTRIBUTE_NAME_DATE_OF_BIRTH = "urn:oid:1.3.6.1.5.5.7.9.1";

  /** The attribute friendly name for the "Date of birth" attribute (dateOfBirth). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_DATE_OF_BIRTH = "dateOfBirth";

  /** Attribute template for the "Date of birth" attribute. */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_DATE_OF_BIRTH = new AttributeTemplate(
    ATTRIBUTE_NAME_DATE_OF_BIRTH, ATTRIBUTE_FRIENDLY_NAME_DATE_OF_BIRTH);
  
  /** The attribute name for the "birth name" attribute (urn:oid:1.2.752.201.3.8). */
  public static final String ATTRIBUTE_NAME_BIRTH_NAME = "urn:oid:1.2.752.201.3.8";

  /** The attribute friendly name for the "birth name" attribute (birthName). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_BIRTH_NAME = "birthName";

  /** Attribute template for the "birth name" attribute. */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_BIRTH_NAME = new AttributeTemplate(
    ATTRIBUTE_NAME_BIRTH_NAME, ATTRIBUTE_FRIENDLY_NAME_BIRTH_NAME);  
  
  /** The attribute name for the "Street address" attribute (urn:oid:2.5.4.9). */
  public static final String ATTRIBUTE_NAME_STREET = "urn:oid:2.5.4.9";

  /** The attribute friendly name for the "Street address" attribute (street). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_STREET = "street";

  /** Attribute template for the "Street address" attribute. */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_STREET = new AttributeTemplate(ATTRIBUTE_NAME_STREET,
    ATTRIBUTE_FRIENDLY_NAME_STREET);

  /** The attribute name for the "Post box" attribute (urn:oid:2.5.4.18). */
  public static final String ATTRIBUTE_NAME_POST_OFFICE_BOX = "urn:oid:2.5.4.18";

  /** The attribute friendly name for the "Post box" attribute (postOfficeBox). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_POST_OFFICE_BOX = "postOfficeBox";

  /** Attribute template for the "Post box" attribute. */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_POST_OFFICE_BOX = new AttributeTemplate(
    ATTRIBUTE_NAME_POST_OFFICE_BOX, ATTRIBUTE_FRIENDLY_NAME_POST_OFFICE_BOX);

  /** The attribute name for the "Postal code" attribute (urn:oid:2.5.4.17). */
  public static final String ATTRIBUTE_NAME_POSTAL_CODE = "urn:oid:2.5.4.17";

  /** The attribute friendly name for the "Postal code" attribute (postalCode). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_POSTAL_CODE = "postalCode";

  /** Attribute template for the "Postal code" attribute. */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_POSTAL_CODE = new AttributeTemplate(
    ATTRIBUTE_NAME_POSTAL_CODE, ATTRIBUTE_FRIENDLY_NAME_POSTAL_CODE);

  /** The attribute name for the "Locality" attribute (urn:oid:2.5.4.7). */
  public static final String ATTRIBUTE_NAME_L = "urn:oid:2.5.4.7";

  /** The attribute friendly name for the "Locality" attribute (l). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_L = "l";

  /** Attribute template for the "Locality" attribute. */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_L = new AttributeTemplate(ATTRIBUTE_NAME_L,
    ATTRIBUTE_FRIENDLY_NAME_L);

  /** The attribute name for the "Country" attribute (urn:oid:2.5.4.6). */
  public static final String ATTRIBUTE_NAME_C = "urn:oid:2.5.4.6";

  /** The attribute friendly name for the "Country" attribute (c). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_C = "c";

  /** Attribute template for the "Country" attribute. */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_C = new AttributeTemplate(ATTRIBUTE_NAME_C,
    ATTRIBUTE_FRIENDLY_NAME_C);

  /** The attribute name for the "Place of birth" attribute (urn:oid:1.3.6.1.5.5.7.9.2). */
  public static final String ATTRIBUTE_NAME_PLACE_OF_BIRTH = "urn:oid:1.3.6.1.5.5.7.9.2";

  /** The attribute friendly name for the "Place of birth" attribute (placeOfBirth). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_PLACE_OF_BIRTH = "placeOfBirth";

  /** Attribute template for the "Place of birth" attribute. */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_PLACE_OF_BIRTH = new AttributeTemplate(
    ATTRIBUTE_NAME_PLACE_OF_BIRTH, ATTRIBUTE_FRIENDLY_NAME_PLACE_OF_BIRTH);

  /** The attribute name for the "Country of citizenship" attribute (urn:oid:1.3.6.1.5.5.7.9.4). */
  public static final String ATTRIBUTE_NAME_COUNTRY_OF_CITIZENSHIP = "urn:oid:1.3.6.1.5.5.7.9.4";

  /** The attribute friendly name for the "Country of citizenship" attribute (countryOfCitizenship). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_COUNTRY_OF_CITIZENSHIP = "countryOfCitizenship";

  /** Attribute template for the "Country of citizenship" attribute (Multi-valued). */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_COUNTRY_OF_CITIZENSHIP = new AttributeTemplate(
    ATTRIBUTE_NAME_COUNTRY_OF_CITIZENSHIP, ATTRIBUTE_FRIENDLY_NAME_COUNTRY_OF_CITIZENSHIP);

  /** The attribute name for the "Country of Residence" attribute (urn:oid:1.3.6.1.5.5.7.9.5). */
  public static final String ATTRIBUTE_NAME_COUNTRY_OF_RESIDENCE = "urn:oid:1.3.6.1.5.5.7.9.5";

  /** The attribute friendly name for the "Country of Residence" attribute (countryOfResidence). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_COUNTRY_OF_RESIDENCE = "countryOfResidence";

  /** Attribute template for the "Country of Residence" attribute. */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_COUNTRY_OF_RESIDENCE = new AttributeTemplate(
    ATTRIBUTE_NAME_COUNTRY_OF_RESIDENCE, ATTRIBUTE_FRIENDLY_NAME_COUNTRY_OF_RESIDENCE);

  /** The attribute name for the "Telephone number" attribute (urn:oid:2.5.4.20). */
  public static final String ATTRIBUTE_NAME_TELEPHONE_NUMBER = "urn:oid:2.5.4.20";

  /** The attribute friendly name for the "Telephone number" attribute (telephoneNumber). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_TELEPHONE_NUMBER = "telephoneNumber";

  /** Attribute template for the "Telephone number" attribute (Multi-valued). */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_TELEPHONE_NUMBER = new AttributeTemplate(
    ATTRIBUTE_NAME_TELEPHONE_NUMBER, ATTRIBUTE_FRIENDLY_NAME_TELEPHONE_NUMBER);

  /** The attribute name for the "Mobile number" attribute (urn:oid:0.9.2342.19200300.100.1.41). */
  public static final String ATTRIBUTE_NAME_MOBILE = "urn:oid:0.9.2342.19200300.100.1.41";

  /** The attribute friendly name for the "Mobile number" attribute (mobile). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_MOBILE = "mobile";

  /** Attribute template for the "Mobile number" attribute (Multi-valued). */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_MOBILE = new AttributeTemplate(ATTRIBUTE_NAME_MOBILE,
    ATTRIBUTE_FRIENDLY_NAME_MOBILE);

  /** The attribute name for the "E-mail address" attribute (urn:oid:0.9.2342.19200300.100.1.3). */
  public static final String ATTRIBUTE_NAME_MAIL = "urn:oid:0.9.2342.19200300.100.1.3";

  /** The attribute friendly name for the "E-mail address" attribute (mail). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_MAIL = "mail";

  /** Attribute template for the "E-mail address" attribute (Multi-valued). */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_MAIL = new AttributeTemplate(ATTRIBUTE_NAME_MAIL,
    ATTRIBUTE_FRIENDLY_NAME_MAIL);

  /** The attribute name for the "Organization name" attribute (urn:oid:2.5.4.10). */
  public static final String ATTRIBUTE_NAME_O = "urn:oid:2.5.4.10";

  /** The attribute friendly name for the "Organization name" attribute (o). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_O = "o";

  /** Attribute template for the "Organization name" attribute. */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_O = new AttributeTemplate(ATTRIBUTE_NAME_O,
    ATTRIBUTE_FRIENDLY_NAME_O);

  /** The attribute name for the "Organizational unit name" attribute (urn:oid:2.5.4.11). */
  public static final String ATTRIBUTE_NAME_OU = "urn:oid:2.5.4.11";

  /** The attribute friendly name for the "Organizational unit name" attribute (ou). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_OU = "ou";

  /** Attribute template for the "Organizational unit name" attribute (Multi-valued). */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_OU = new AttributeTemplate(ATTRIBUTE_NAME_OU,
    ATTRIBUTE_FRIENDLY_NAME_OU);

  /** The attribute name for the "Organizational identifier code" attribute (urn:oid:2.5.4.97). */
  public static final String ATTRIBUTE_NAME_ORGANIZATION_IDENTIFIER = "urn:oid:2.5.4.97";

  /** The attribute friendly name for the "Organizational identifier code" attribute (organizationIdentifier). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_ORGANIZATION_IDENTIFIER = "organizationIdentifier";

  /** Attribute template for the "Organizational identifier code" attribute. */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_ORGANIZATION_IDENTIFIER = new AttributeTemplate(
    ATTRIBUTE_NAME_ORGANIZATION_IDENTIFIER, ATTRIBUTE_FRIENDLY_NAME_ORGANIZATION_IDENTIFIER);

  /** The attribute name for the "Organization affiliation" attribute (urn:oid:1.2.752.201.3.1). */
  public static final String ATTRIBUTE_NAME_ORG_AFFILIATION = "urn:oid:1.2.752.201.3.1";

  /** The attribute friendly name for the "Organization affiliation" attribute (orgAffiliation). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_ORG_AFFILIATION = "orgAffiliation";

  /** Attribute template for the "Organization affiliation" attribute (Multi-valued). */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_ORG_AFFILIATION = new AttributeTemplate(
    ATTRIBUTE_NAME_ORG_AFFILIATION, ATTRIBUTE_FRIENDLY_NAME_ORG_AFFILIATION);

  /** The attribute name for the "Transaction identifier" attribute (urn:oid:1.2.752.201.3.2). */
  public static final String ATTRIBUTE_NAME_TRANSACTION_IDENTIFIER = "urn:oid:1.2.752.201.3.2";

  /** The attribute friendly name for the "Transaction identifier" attribute (transactionIdentifier). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_TRANSACTION_IDENTIFIER = "transactionIdentifier";

  /** Attribute template for the "Transaction identifier" attribute. */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_TRANSACTION_IDENTIFIER = new AttributeTemplate(
    ATTRIBUTE_NAME_TRANSACTION_IDENTIFIER, ATTRIBUTE_FRIENDLY_NAME_TRANSACTION_IDENTIFIER);

  /** The attribute name for the "Authentication context parameters" attribute (urn:oid:1.2.752.201.3.3). */
  public static final String ATTRIBUTE_NAME_AUTH_CONTEXT_PARAMS = "urn:oid:1.2.752.201.3.3";

  /** The attribute friendly name for the "Authentication context parameters" attribute (authContextParams). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_AUTH_CONTEXT_PARAMS = "authContextParams";

  /** Attribute template for the "Authentication context parameters" attribute. */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_AUTH_CONTEXT_PARAMS = new AttributeTemplate(
    ATTRIBUTE_NAME_AUTH_CONTEXT_PARAMS, ATTRIBUTE_FRIENDLY_NAME_AUTH_CONTEXT_PARAMS);
  
  /** The attribute name for the "User certificate" attribute (urn:oid:1.2.752.201.3.10). */
  public static final String ATTRIBUTE_NAME_USER_CERTIFICATE = "urn:oid:1.2.752.201.3.10";

  /** The attribute friendly name for the "user certificate" attribute (userCertificate). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_USER_CERTIFICATE = "userCertificate";

  /** Attribute template for the "user certificate" attribute. */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_USER_CERTIFICATE = new AttributeTemplate(
    ATTRIBUTE_NAME_USER_CERTIFICATE, ATTRIBUTE_FRIENDLY_NAME_USER_CERTIFICATE);
  
  /** The attribute name for the "User signature" attribute (urn:oid:1.2.752.201.3.11). */
  public static final String ATTRIBUTE_NAME_USER_SIGNATURE = "urn:oid:1.2.752.201.3.11";

  /** The attribute friendly name for the "user signature" attribute (userSignature). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_USER_SIGNATURE = "userSignature";

  /** Attribute template for the "user signature" attribute. */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_USER_SIGNATURE = new AttributeTemplate(
    ATTRIBUTE_NAME_USER_SIGNATURE, ATTRIBUTE_FRIENDLY_NAME_USER_SIGNATURE);  
  
  /** The attribute name for the "Authentication server signature" attribute (urn:oid:1.2.752.201.3.13). */
  public static final String ATTRIBUTE_NAME_AUTH_SERVER_SIGNATURE = "urn:oid:1.2.752.201.3.13";

  /** The attribute friendly name for the "Authentication server signature" attribute (authServerSignature). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_AUTH_SERVER_SIGNATURE = "authServerSignature";

  /** Attribute template for the "Authentication server signature" attribute. */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_AUTH_SERVER_SIGNATURE = new AttributeTemplate(
    ATTRIBUTE_NAME_AUTH_SERVER_SIGNATURE, ATTRIBUTE_FRIENDLY_NAME_AUTH_SERVER_SIGNATURE);  
  
  /** The attribute name for the "Signature activation data" attribute (urn:oid:1.2.752.201.3.12). */
  public static final String ATTRIBUTE_NAME_SAD = "urn:oid:1.2.752.201.3.12";

  /** The attribute friendly name for the "Signature activation data" attribute (sad). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_SAD = "sad";

  /** Attribute template for the "Signature activation data" attribute. */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_SAD = new AttributeTemplate(
    ATTRIBUTE_NAME_SAD, ATTRIBUTE_FRIENDLY_NAME_SAD);
  
  /** The attribute name for the "Sign message digest" attribute (urn:oid:1.2.752.201.3.14). */
  public static final String ATTRIBUTE_NAME_SIGNMESSAGE_DIGEST = "urn:oid:1.2.752.201.3.14";
  
  /** The attribute friendly name for the "Sign message digest" attribute (signMessageDigest). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_SIGNMESSAGE_DIGEST = "signMessageDigest";
  
  /** Attribute template for the "Sign message digest" attribute. */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_SIGNMESSAGE_DIGEST = new AttributeTemplate(
    ATTRIBUTE_NAME_SIGNMESSAGE_DIGEST, ATTRIBUTE_FRIENDLY_NAME_SIGNMESSAGE_DIGEST);
  
  /** The attribute name for the "Provisional identifier" attribute (urn:oid:1.2.752.201.3.4). */
  public static final String ATTRIBUTE_NAME_PRID = "urn:oid:1.2.752.201.3.4";

  /** The attribute friendly name for the "Provisional identifier" attribute (prid). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_PRID = "prid";

  /** Attribute template for the "Provisional identifier" attribute. */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_PRID = new AttributeTemplate(
    ATTRIBUTE_NAME_PRID, ATTRIBUTE_FRIENDLY_NAME_PRID);
  
  /** The attribute name for the "Provisional identifier persistence indicator" attribute (urn:oid:1.2.752.201.3.5). */
  public static final String ATTRIBUTE_NAME_PRID_PERSISTENCE = "urn:oid:1.2.752.201.3.5";

  /** The attribute friendly name for the "Provisional identifier persistence indicator" attribute (pridPersistence). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_PRID_PERSISTENCE = "pridPersistence";

  /** Attribute template for the "Provisional identifier persistence indicator" attribute. */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_PRID_PERSISTENCE = new AttributeTemplate(
    ATTRIBUTE_NAME_PRID_PERSISTENCE, ATTRIBUTE_FRIENDLY_NAME_PRID_PERSISTENCE);  
    
  /** The attribute name for the "National civic registration number/code binding URI" attribute (urn:oid:1.2.752.201.3.6). */
  public static final String ATTRIBUTE_NAME_PERSONAL_IDENTITY_NUMBER_BINDING = "urn:oid:1.2.752.201.3.6";

  /** The attribute friendly name for the "National civic registration number/code binding URI" attribute (personalIdentityNumberBinding). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_PERSONAL_IDENTITY_NUMBER_BINDING = "personalIdentityNumberBinding";

  /** Attribute template for the "National civic registration number/code binding URI" attribute. */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_PERSONAL_IDENTITY_NUMBER_BINDING = new AttributeTemplate(
    ATTRIBUTE_NAME_PERSONAL_IDENTITY_NUMBER_BINDING, ATTRIBUTE_FRIENDLY_NAME_PERSONAL_IDENTITY_NUMBER_BINDING);
  
  /** The attribute name for the "Mapped national civic registration number" attribute (urn:oid:1.2.752.201.3.16). */
  public static final String ATTRIBUTE_NAME_MAPPED_PERSONAL_IDENTITY_NUMBER = "urn:oid:1.2.752.201.3.16";

  /** The attribute friendly name for the "Mapped national civic registration number" attribute (mappedPersonalIdentityNumber). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_MAPPED_PERSONAL_IDENTITY_NUMBER = "mappedPersonalIdentityNumber";

  /** Attribute template for the "National civic registration number/code binding URI" attribute. */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_MAPPED_PERSONAL_IDENTITY_NUMBER = new AttributeTemplate(
    ATTRIBUTE_NAME_MAPPED_PERSONAL_IDENTITY_NUMBER, ATTRIBUTE_FRIENDLY_NAME_MAPPED_PERSONAL_IDENTITY_NUMBER);  
  
  /** The attribute name for the "eIDAS uniqueness identifier for natural persons" attribute (urn:oid:1.2.752.201.3.7). */
  public static final String ATTRIBUTE_NAME_EIDAS_PERSON_IDENTIFIER = "urn:oid:1.2.752.201.3.7";

  /** The attribute friendly name for the "eIDAS uniqueness identifier for natural persons" attribute (eidasPersonIdentifier). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_EIDAS_PERSON_IDENTIFIER = "eidasPersonIdentifier";

  /** Attribute template for the "eIDAS uniqueness identifier for natural persons" attribute. */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_EIDAS_PERSON_IDENTIFIER = new AttributeTemplate(
    ATTRIBUTE_NAME_EIDAS_PERSON_IDENTIFIER, ATTRIBUTE_FRIENDLY_NAME_EIDAS_PERSON_IDENTIFIER);
  
  /** The attribute name for the "eIDAS Natural Person Address" attribute (urn:oid:1.2.752.201.3.9). */
  public static final String ATTRIBUTE_NAME_EIDAS_NATURAL_PERSON_ADDRESS = "urn:oid:1.2.752.201.3.9";

  /** The attribute friendly name for the "eIDAS Natural Person Address" attribute (eidasNaturalPersonAddress). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_EIDAS_NATURAL_PERSON_ADDRESS = "eidasNaturalPersonAddress";

  /** Attribute template for the "eIDAS Natural Person Address" attribute. */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_EIDAS_NATURAL_PERSON_ADDRESS = new AttributeTemplate(
    ATTRIBUTE_NAME_EIDAS_NATURAL_PERSON_ADDRESS, ATTRIBUTE_FRIENDLY_NAME_EIDAS_NATURAL_PERSON_ADDRESS);
  
  /** The attribute name for the "Employee HSA-ID" attribute (urn:oid:1.2.752.29.6.2.1). */
  public static final String ATTRIBUTE_NAME_EMPLOYEE_HSA_ID = "urn:oid:1.2.752.29.6.2.1";

  /** The attribute friendly name for the "Employee HSA-ID" attribute (employeeHsaId). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_EMPLOYEE_HSA_ID = "employeeHsaId";

  /** Attribute template for the "Employee HSA-ID" attribute. */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_EMPLOYEE_HSA_ID = new AttributeTemplate(
    ATTRIBUTE_NAME_EMPLOYEE_HSA_ID, ATTRIBUTE_FRIENDLY_NAME_EMPLOYEE_HSA_ID);  
  
  /*
   * Hidden constructor.
   */
  private AttributeConstants() {
  }

}
