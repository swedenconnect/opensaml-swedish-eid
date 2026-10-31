![Logo](https://docs.swedenconnect.se/technical-framework/img/sweden-connect.png)

# opensaml-swedish-eid

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0) 

---

## Release Notes

### 2.3.2

**Date:** _not yet released_

- Updated opensaml-addons to 2.2.3, which fixes the handling of scoped attributes.
- The Holder-of-key check is now performed also when no specific AuthnContext was requested ([#81](https://github.com/swedenconnect/opensaml-swedish-eid/issues/81)).
- Fixed a NullPointerException when a scoped attribute was rejected ([#82](https://github.com/swedenconnect/opensaml-swedish-eid/issues/82)).
- Attribute set validation no longer fails on assertions without an AttributeStatement, and checks all AttributeStatement elements ([#83](https://github.com/swedenconnect/opensaml-swedish-eid/issues/83)).
- Under strict validation, assertions with more than one AuthnStatement or AttributeStatement are rejected ([#84](https://github.com/swedenconnect/opensaml-swedish-eid/issues/84)).
- The default allowed clock skew for assertion validation is now one minute ([#85](https://github.com/swedenconnect/opensaml-swedish-eid/issues/85)).
- The no-argument `buildObject()` of the `RequestedPrincipalSelection` builder created a `PrincipalSelection` element ([#87](https://github.com/swedenconnect/opensaml-swedish-eid/issues/87)).
- `MatchValue` and `UserMessage` attributes are now matched exactly and only without namespace. Attributes from other namespaces are kept as unknown attributes ([#88](https://github.com/swedenconnect/opensaml-swedish-eid/issues/88)).
- `getServiceEntityCategories` in the entity category registry no longer throws `ClassCastException` for incorrectly typed entries ([#91](https://github.com/swedenconnect/opensaml-swedish-eid/issues/91)).
- Added type name constants to `OrganizationNumber` ([#93](https://github.com/swedenconnect/opensaml-swedish-eid/issues/93)).

---

Copyright &copy; 2016-2026, [Sweden Connect](https://swedenconnect.se). Licensed under version 2.0 of the [Apache License](http://www.apache.org/licenses/LICENSE-2.0).
