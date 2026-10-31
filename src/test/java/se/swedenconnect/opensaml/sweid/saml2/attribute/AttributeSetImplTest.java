/*
 * Copyright 2016-2026 Sweden Connect
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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.AttributeStatement;
import se.swedenconnect.opensaml.saml2.attribute.AttributeBuilder;
import se.swedenconnect.opensaml.sweid.OpenSAMLTestBase;

/**
 * Test cases for {@link AttributeSetImpl}.
 */
public class AttributeSetImplTest extends OpenSAMLTestBase {

  @Test
  void testNoAttributeStatement() {
    final Assertion assertion = (Assertion) XMLObjectSupport.buildXMLObject(Assertion.DEFAULT_ELEMENT_NAME);
    assertion.setID("_id");

    Assertions.assertThrows(AttributesValidationException.class, () ->
        AttributeSetConstants.ATTRIBUTE_SET_NATURAL_PERSON_WITH_PERSONAL_ID.validateAttributes(assertion, null));
  }

  @Test
  void testAttributesInSeveralStatements() throws Exception {
    final Assertion assertion = (Assertion) XMLObjectSupport.buildXMLObject(Assertion.DEFAULT_ELEMENT_NAME);
    assertion.setID("_id");

    final AttributeStatement s1 =
        (AttributeStatement) XMLObjectSupport.buildXMLObject(AttributeStatement.DEFAULT_ELEMENT_NAME);
    s1.getAttributes().add(AttributeBuilder.builder(AttributeConstants.ATTRIBUTE_NAME_PERSONAL_IDENTITY_NUMBER)
        .value("197705232382").build());
    s1.getAttributes().add(AttributeBuilder.builder(AttributeConstants.ATTRIBUTE_NAME_GIVEN_NAME)
        .value("Frida").build());
    assertion.getAttributeStatements().add(s1);

    final AttributeStatement s2 =
        (AttributeStatement) XMLObjectSupport.buildXMLObject(AttributeStatement.DEFAULT_ELEMENT_NAME);
    s2.getAttributes().add(AttributeBuilder.builder(AttributeConstants.ATTRIBUTE_NAME_SN)
        .value("Kranstege").build());
    s2.getAttributes().add(AttributeBuilder.builder(AttributeConstants.ATTRIBUTE_NAME_DISPLAY_NAME)
        .value("Frida Kranstege").build());
    assertion.getAttributeStatements().add(s2);

    AttributeSetConstants.ATTRIBUTE_SET_NATURAL_PERSON_WITH_PERSONAL_ID.validateAttributes(assertion, null);
  }

}
