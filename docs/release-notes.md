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
- `SignMessageDecrypter` now applies settings changed after the first decryption, and reports content that is not a `Message` element as a decryption error ([#102](https://github.com/swedenconnect/opensaml-swedish-eid/issues/102), [#103](https://github.com/swedenconnect/opensaml-swedish-eid/issues/103)).
- The `MimeType` of a `SignMessage` can be cleared by assigning null ([#104](https://github.com/swedenconnect/opensaml-swedish-eid/issues/104)).

---

Copyright &copy; 2016-2026, [Sweden Connect](https://swedenconnect.se). Licensed under version 2.0 of the [Apache License](http://www.apache.org/licenses/LICENSE-2.0).
