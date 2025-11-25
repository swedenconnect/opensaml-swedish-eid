/*
 * Copyright 2016-2025 Sweden Connect
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
package se.swedenconnect.opensaml.sweid.saml2.metadata.ext;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.w3c.dom.Element;
import se.swedenconnect.opensaml.sweid.OpenSAMLTestBase;

/**
 * Test cases for {@link OrganizationNumber}.
 *
 * @author Martin Lindström
 */
public class OrganizationNumberTest extends OpenSAMLTestBase {

  /**
   * Test to marshall and unmarshall the object.
   *
   * @throws Exception for errors
   */
  @Test
  void testMarshallUnmarshall() throws Exception {
    final OrganizationNumber n =
        (OrganizationNumber) XMLObjectSupport.buildXMLObject(OrganizationNumber.DEFAULT_ELEMENT_NAME);
    n.setValue("5590026042");

    final Element element = XMLObjectSupport.marshall(n);

    final OrganizationNumber n2 = (OrganizationNumber) XMLObjectSupport.getUnmarshaller(element).unmarshall(element);
    Assertions.assertEquals(n.getValue(), n2.getValue());
  }

}
