![Logo](https://docs.swedenconnect.se/technical-framework/img/sweden-connect.png)

# opensaml-swedish-eid

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0) 

---

## Release Notes

### 2.3.3

**Date:** _not yet released_

-

### 2.3.2

**Date:** 2026-10-31

- Updated [opensaml-addons](https://github.com/swedenconnect/opensaml-addons) to 2.2.3. This fixes the scope check for scoped attributes: the last `@` character is now used when splitting a value, and each value of a multi-valued attribute (for example `orgAffiliation`) may match a different scope.

- [SwedishEidAuthnStatementValidator](https://github.com/swedenconnect/opensaml-swedish-eid/blob/main/src/main/java/se/swedenconnect/opensaml/sweid/saml2/validation/SwedishEidAuthnStatementValidator.java) skipped the Holder-of-key check when the `AuthnRequest` had no `RequestedAuthnContext`. See [#81](https://github.com/swedenconnect/opensaml-swedish-eid/issues/81).

- [SwedishEidAttributeStatementValidator](https://github.com/swedenconnect/opensaml-swedish-eid/blob/main/src/main/java/se/swedenconnect/opensaml/sweid/saml2/validation/SwedishEidAttributeStatementValidator.java) threw a `NullPointerException` when a scoped attribute without a string value was rejected. See [#82](https://github.com/swedenconnect/opensaml-swedish-eid/issues/82).

- `validateAttributes` of [AttributeSetImpl](https://github.com/swedenconnect/opensaml-swedish-eid/blob/main/src/main/java/se/swedenconnect/opensaml/sweid/saml2/attribute/AttributeSetImpl.java) threw an `IndexOutOfBoundsException` for an assertion without an `AttributeStatement`, and only checked the first `AttributeStatement`. See [#83](https://github.com/swedenconnect/opensaml-swedish-eid/issues/83).

- [SwedishEidAssertionValidator](https://github.com/swedenconnect/opensaml-swedish-eid/blob/main/src/main/java/se/swedenconnect/opensaml/sweid/saml2/validation/SwedishEidAssertionValidator.java) now rejects assertions with more than one `AuthnStatement` or `AttributeStatement` when strict validation is used. See [#84](https://github.com/swedenconnect/opensaml-swedish-eid/issues/84).

- [SwedishEidAssertionValidationParametersBuilder](https://github.com/swedenconnect/opensaml-swedish-eid/blob/main/src/main/java/se/swedenconnect/opensaml/sweid/saml2/validation/SwedishEidAssertionValidationParametersBuilder.java) now sets a default allowed clock skew of one minute, as stated by the Deployment Profile. Previously OpenSAML's default of five minutes was used. See [#85](https://github.com/swedenconnect/opensaml-swedish-eid/issues/85).

- The SAD validator of [SADParser](https://github.com/swedenconnect/opensaml-swedish-eid/blob/main/src/main/java/se/swedenconnect/opensaml/sweid/saml2/signservice/SADParser.java) did not check that the SAD version matches the requested version. See [#96](https://github.com/swedenconnect/opensaml-swedish-eid/issues/96).

- The SAD validator of [SADParser](https://github.com/swedenconnect/opensaml-swedish-eid/blob/main/src/main/java/se/swedenconnect/opensaml/sweid/saml2/signservice/SADParser.java) accepted a SAD that was missing required claims, as long as the expected value was also missing. See [#97](https://github.com/swedenconnect/opensaml-swedish-eid/issues/97).

- `parse` of [SADParser](https://github.com/swedenconnect/opensaml-swedish-eid/blob/main/src/main/java/se/swedenconnect/opensaml/sweid/saml2/signservice/SADParser.java) let JSON errors through as unchecked exceptions instead of `IOException`. See [#99](https://github.com/swedenconnect/opensaml-swedish-eid/issues/99).

- [SADVersion](https://github.com/swedenconnect/opensaml-swedish-eid/blob/main/src/main/java/se/swedenconnect/opensaml/sweid/saml2/signservice/sap/SADVersion.java) did not implement `hashCode`, and invalid version strings gave runtime exceptions or were accepted (for example "1.0.5"). See [#100](https://github.com/swedenconnect/opensaml-swedish-eid/issues/100) and [#101](https://github.com/swedenconnect/opensaml-swedish-eid/issues/101).

- The `exp` and `iat` claims of [SAD](https://github.com/swedenconnect/opensaml-swedish-eid/blob/main/src/main/java/se/swedenconnect/opensaml/sweid/saml2/signservice/sap/SAD.java) were stored as `Integer` and would overflow in 2038. Use the new methods `getExpirySeconds`, `setExpirySeconds`, `getIssuedAtSeconds` and `setIssuedAtSeconds`. The `Integer` methods are deprecated for removal. See [#106](https://github.com/swedenconnect/opensaml-swedish-eid/issues/106).

- The `name` attribute of a SAP [Parameter](https://github.com/swedenconnect/opensaml-swedish-eid/blob/main/src/main/java/se/swedenconnect/opensaml/sweid/saml2/signservice/sap/Parameter.java) element is now matched case sensitively, as required by the schema. See [#107](https://github.com/swedenconnect/opensaml-swedish-eid/issues/107).

- [SignMessageDecrypter](https://github.com/swedenconnect/opensaml-swedish-eid/blob/main/src/main/java/se/swedenconnect/opensaml/sweid/saml2/signservice/SignMessageDecrypter.java) ignored settings changed after the first decryption, and threw a `ClassCastException` when the decrypted content was not a `Message` element. See [#102](https://github.com/swedenconnect/opensaml-swedish-eid/issues/102) and [#103](https://github.com/swedenconnect/opensaml-swedish-eid/issues/103).

- The `MimeType` of a [SignMessage](https://github.com/swedenconnect/opensaml-swedish-eid/blob/main/src/main/java/se/swedenconnect/opensaml/sweid/saml2/signservice/dss/SignMessage.java) could not be cleared by assigning a null enum value. See [#104](https://github.com/swedenconnect/opensaml-swedish-eid/issues/104).

- `buildObject()` of [RequestedPrincipalSelectionBuilder](https://github.com/swedenconnect/opensaml-swedish-eid/blob/main/src/main/java/se/swedenconnect/opensaml/sweid/saml2/authn/psc/impl/RequestedPrincipalSelectionBuilder.java) created a `PrincipalSelection` element. See [#87](https://github.com/swedenconnect/opensaml-swedish-eid/issues/87).

- The attributes of [MatchValue](https://github.com/swedenconnect/opensaml-swedish-eid/blob/main/src/main/java/se/swedenconnect/opensaml/sweid/saml2/authn/psc/MatchValue.java) and [UserMessage](https://github.com/swedenconnect/opensaml-swedish-eid/blob/main/src/main/java/se/swedenconnect/opensaml/sweid/saml2/authn/umsg/UserMessage.java) were matched ignoring case and namespace, so an attribute from another namespace could replace the real value. See [#88](https://github.com/swedenconnect/opensaml-swedish-eid/issues/88).

- `getServiceEntityCategories` of [EntityCategoryRegistryImpl](https://github.com/swedenconnect/opensaml-swedish-eid/blob/main/src/main/java/se/swedenconnect/opensaml/sweid/saml2/metadata/entitycategory/EntityCategoryRegistryImpl.java) could throw a `ClassCastException`. See [#91](https://github.com/swedenconnect/opensaml-swedish-eid/issues/91).

- Added type name constants to [OrganizationNumber](https://github.com/swedenconnect/opensaml-swedish-eid/blob/main/src/main/java/se/swedenconnect/opensaml/sweid/saml2/metadata/ext/OrganizationNumber.java). See [#93](https://github.com/swedenconnect/opensaml-swedish-eid/issues/93).

- The public API is now annotated with `jakarta.annotation` nullability annotations. See [#94](https://github.com/swedenconnect/opensaml-swedish-eid/issues/94).

- Lower log levels for validation failures that are reported to the caller. See [#95](https://github.com/swedenconnect/opensaml-swedish-eid/issues/95).

---

Copyright &copy; 2016-2026, [Sweden Connect](https://swedenconnect.se). Licensed under version 2.0 of the [Apache License](http://www.apache.org/licenses/LICENSE-2.0).
