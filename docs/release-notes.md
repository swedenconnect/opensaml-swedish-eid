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
- SAD validation now checks that the SAD version matches the requested version ([#96](https://github.com/swedenconnect/opensaml-swedish-eid/issues/96)).
- SAD validation rejects a SAD that is missing required claims ([#97](https://github.com/swedenconnect/opensaml-swedish-eid/issues/97)).
- `SADParser.parse` reports invalid SAD JSON as `IOException` ([#99](https://github.com/swedenconnect/opensaml-swedish-eid/issues/99)).
- Invalid SAD version strings are now rejected, and `SADVersion` can be used in hash based collections ([#100](https://github.com/swedenconnect/opensaml-swedish-eid/issues/100), [#101](https://github.com/swedenconnect/opensaml-swedish-eid/issues/101)).
- The SAD `exp` and `iat` claims are now `Long` values. This changes the signatures of `SAD.getExpiry`, `setExpiry`, `getIssuedAt` and `setIssuedAt` ([#106](https://github.com/swedenconnect/opensaml-swedish-eid/issues/106)).
- The `name` attribute of a SAP `Parameter` element is now matched case sensitively, as required by the schema ([#107](https://github.com/swedenconnect/opensaml-swedish-eid/issues/107)).

---

Copyright &copy; 2016-2026, [Sweden Connect](https://swedenconnect.se). Licensed under version 2.0 of the [Apache License](http://www.apache.org/licenses/LICENSE-2.0).
