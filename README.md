![Logo](https://raw.githubusercontent.com/swedenconnect/technical-framework/master/img/sweden-connect.png)

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/se.swedenconnect.opensaml/opensaml-swedish-eid/badge.svg)](https://maven-badges.herokuapp.com/maven-central/se.swedenconnect.opensaml/opensaml-swedish-eid)

# opensaml-swedish-eid

OpenSAML extensions for the Swedish eID Framework

--

This open source package is an extension to OpenSAML that offers interfaces and classes for the Swedish eID Framework,
see https://github.com/swedenconnect/technical-framework.

The library contains support for the following functionality:

* Attribute definitions according to
  the [Attribute Specification for the Swedish eID Framework](https://docs.swedenconnect.se/technical-framework/latest/04_-_Attribute_Specification_for_the_Swedish_eID_Framework.html)
  specification.

* Mapping of level of assurance URI:s as defined by the Swedish eID
  Framework ([Swedish eID Framework - Registry for identifiers](https://docs.swedenconnect.se/technical-framework/latest/03_-_Registry_for_Identifiers.html)).

* Representation of entity categories as defined in
  the [Entity Categories for the Swedish eID Framework](https://docs.swedenconnect.se/technical-framework/latest/06_-_Entity_Categories_for_the_Swedish_eID_Framework.html)
  specification.

* Support for the `SignMessage` extension type, including utility classes for building sign message extensions and for
  decrypting sign messages. See
  the [DSS Extension for Federated Central Signing Services](https://docs.swedenconnect.se/technical-framework/latest/09_-_DSS_Extension_for_Federated_Signing_Services.html)
  specification.

* Support for the `SADRequest` extension type as well as the SAD JWT. The SAD support also includes a SAD factory for
  creating and signing a SAD JWT and a SAD parser with validation support. See
  the [Signature Activation Protocol for Federated Signing](https://docs.swedenconnect.se/technical-framework/latest/13_-_Signature_Activation_Protocol.html)
  specification.

* Support for the `PrincipalSelection` extension type that allows a requester to include a hint about the person that is
  about to be authenticated (especially useful for signature services). See
  the [Principal Selection in SAML Authentication Requests](https://docs.swedenconnect.se/technical-framework/updates/14_-_Principal_Selection_in_SAML_Authentication_Requests.html)
  specification.

* Support for the `UserMessage` extension type that allows a Service Provider to include a dynamic message for the user
  to see during the authentication phase. See
  the [User Message Extension in SAML Authentication Requests](https://docs.swedenconnect.se/technical-framework/updates/18_-_User_Message_Extension_in_SAML_Authentication_Requests.html)
  specification.

* Validation support for validating SAML responses according to
  the [Deployment Profile for the Swedish eID Framework](https://docs.swedenconnect.se/technical-framework/latest/02_-_Deployment_Profile_for_the_Swedish_eID_Framework.html)
  specification.

### API Documentation

Java API documentation of the opensaml-swedish-eid library is found
at [https://docs.swedenconnect.se/opensaml-swedish-eid/apidoc](https://docs.swedenconnect.se/opensaml-swedish-eid/apidoc/index.html).

### Maven

The opensaml-swedish-eid project artifacts are published to Maven central.

Include the following snippet in your Maven POM to add opensaml-swedish-eid as a dependency for your project.

```
<dependency>
  <groupId>se.swedenconnect.opensaml</groupId>
  <artifactId>opensaml-swedish-eid</artifactId>
  <version>${opensaml-swedish-eid.version}</version>
</dependency>
```

------

Copyright &copy; 2016-2025, [Sweden Connect](https://swedenconnect.se). Licensed under version 2.0 of
the [Apache License](http://www.apache.org/licenses/LICENSE-2.0).
