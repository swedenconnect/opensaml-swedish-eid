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
package se.swedenconnect.opensaml.sweid.saml2.authn.psc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.saml.saml2.core.Attribute;
import org.w3c.dom.Element;

import se.swedenconnect.opensaml.sweid.OpenSAMLTestBase;
import se.swedenconnect.opensaml.sweid.saml2.attribute.AttributeConstants;
import se.swedenconnect.opensaml.sweid.saml2.authn.psc.build.MatchValueBuilder;
import se.swedenconnect.opensaml.sweid.saml2.authn.psc.build.PrincipalSelectionBuilder;
import se.swedenconnect.opensaml.sweid.saml2.authn.psc.build.RequestedPrincipalSelectionBuilder;

/**
 * Test cases for {@link PrincipalSelection}, {@link RequestedPrincipalSelection} and {@link MatchValue}.
 *
 * @author Martin Lindström (martin@idsec.se)
 */
public class PrincipalSelectionTest extends OpenSAMLTestBase {

  /**
   * Test to marshall and unmarshall the object.
   *
   * @throws Exception for errors
   */
  @Test
  public void testMarshallUnmarshall() throws Exception {

    PrincipalSelection ps =
        (PrincipalSelection) XMLObjectSupport.buildXMLObject(PrincipalSelection.DEFAULT_ELEMENT_NAME);

    MatchValue mv1 = (MatchValue) XMLObjectSupport.buildXMLObject(MatchValue.DEFAULT_ELEMENT_NAME);
    mv1.setValue("198906059483");
    mv1.setNameFormat(Attribute.URI_REFERENCE);
    mv1.setName(AttributeConstants.ATTRIBUTE_NAME_PERSONAL_IDENTITY_NUMBER);
    ps.getMatchValues().add(mv1);

    MatchValue mv2 = (MatchValue) XMLObjectSupport.buildXMLObject(MatchValue.DEFAULT_ELEMENT_NAME);
    mv2.setValue("NO:05068907693");
    mv2.setName(AttributeConstants.ATTRIBUTE_NAME_PRID);
    ps.getMatchValues().add(mv2);

    Element element = XMLObjectSupport.marshall(ps);

    // System.out.println(SerializeSupport.prettyPrintXML(element));

    PrincipalSelection ps2 =
        PrincipalSelection.class.cast(XMLObjectSupport.getUnmarshaller(element).unmarshall(element));
    Assertions.assertTrue(ps2.getMatchValues().size() == 2, "Expected two MatchValue elements");
    Assertions.assertEquals("198906059483", ps2.getMatchValues().get(0).getValue());
    Assertions.assertEquals(Attribute.URI_REFERENCE, ps2.getMatchValues().get(0).getNameFormat());
    Assertions.assertEquals(AttributeConstants.ATTRIBUTE_NAME_PERSONAL_IDENTITY_NUMBER,
        ps2.getMatchValues().get(0).getName());
    Assertions.assertEquals("NO:05068907693", ps2.getMatchValues().get(1).getValue());
    Assertions.assertNull(ps2.getMatchValues().get(1).getNameFormat());
    Assertions.assertEquals(AttributeConstants.ATTRIBUTE_NAME_PRID, ps2.getMatchValues().get(1).getName());

    RequestedPrincipalSelection rps =
        (RequestedPrincipalSelection) XMLObjectSupport.buildXMLObject(RequestedPrincipalSelection.DEFAULT_ELEMENT_NAME);

    MatchValue rmv1 = (MatchValue) XMLObjectSupport.buildXMLObject(MatchValue.DEFAULT_ELEMENT_NAME);
    rmv1.setName(AttributeConstants.ATTRIBUTE_NAME_PERSONAL_IDENTITY_NUMBER);
    rps.getMatchValues().add(rmv1);

    MatchValue rmv2 = (MatchValue) XMLObjectSupport.buildXMLObject(MatchValue.DEFAULT_ELEMENT_NAME);
    rmv2.setName(AttributeConstants.ATTRIBUTE_NAME_PRID);
    rps.getMatchValues().add(rmv2);

    Element relement = XMLObjectSupport.marshall(rps);

    // System.out.println(SerializeSupport.prettyPrintXML(relement));

    RequestedPrincipalSelection rps2 = RequestedPrincipalSelection.class.cast(
        XMLObjectSupport.getUnmarshaller(relement).unmarshall(relement));
    Assertions.assertTrue(rps2.getMatchValues().size() == 2, "Expected two MatchValue elements");
    Assertions.assertEquals(AttributeConstants.ATTRIBUTE_NAME_PERSONAL_IDENTITY_NUMBER,
        rps2.getMatchValues().get(0).getName());
    Assertions.assertEquals(AttributeConstants.ATTRIBUTE_NAME_PRID, rps2.getMatchValues().get(1).getName());
  }

  /**
   * Tests using builders to create the objects.
   *
   * @throws Exception for errors
   */
  @Test
  public void testBuilders() throws Exception {
    PrincipalSelection ps = PrincipalSelectionBuilder.builder()
        .matchValues(
            MatchValueBuilder.builder()
                .value("198906059483")
                .nameFormat(Attribute.URI_REFERENCE)
                .name(AttributeConstants.ATTRIBUTE_NAME_PERSONAL_IDENTITY_NUMBER)
                .build(),
            MatchValueBuilder.builder().value("NO:05068907693").name(AttributeConstants.ATTRIBUTE_NAME_PRID).build())
        .build();

    Element element = XMLObjectSupport.marshall(ps);

    // System.out.println(SerializeSupport.prettyPrintXML(element));

    PrincipalSelection ps2 = PrincipalSelection.class.cast(
        XMLObjectSupport.getUnmarshaller(element).unmarshall(element));
    Assertions.assertTrue(ps2.getMatchValues().size() == 2, "Expected two MatchValue elements");
    Assertions.assertEquals("198906059483", ps2.getMatchValues().get(0).getValue());
    Assertions.assertEquals(Attribute.URI_REFERENCE, ps2.getMatchValues().get(0).getNameFormat());
    Assertions.assertEquals(AttributeConstants.ATTRIBUTE_NAME_PERSONAL_IDENTITY_NUMBER,
        ps2.getMatchValues().get(0).getName());
    Assertions.assertEquals("NO:05068907693", ps2.getMatchValues().get(1).getValue());
    Assertions.assertNull(ps2.getMatchValues().get(1).getNameFormat());
    Assertions.assertEquals(AttributeConstants.ATTRIBUTE_NAME_PRID, ps2.getMatchValues().get(1).getName());

    RequestedPrincipalSelection rps = RequestedPrincipalSelectionBuilder.builder()
        .matchValues(
            MatchValueBuilder.builder().name(AttributeConstants.ATTRIBUTE_NAME_PERSONAL_IDENTITY_NUMBER).build(),
            MatchValueBuilder.builder().name(AttributeConstants.ATTRIBUTE_NAME_PRID).build())
        .build();

    Element relement = XMLObjectSupport.marshall(rps);

    // System.out.println(SerializeSupport.prettyPrintXML(relement));

    PrincipalSelection rps2 = RequestedPrincipalSelection.class.cast(
        XMLObjectSupport.getUnmarshaller(relement).unmarshall(relement));
    Assertions.assertTrue(rps2.getMatchValues().size() == 2, "Expected two MatchValue elements");
    Assertions.assertEquals(AttributeConstants.ATTRIBUTE_NAME_PERSONAL_IDENTITY_NUMBER,
        rps2.getMatchValues().get(0).getName());
    Assertions.assertEquals(AttributeConstants.ATTRIBUTE_NAME_PRID, rps2.getMatchValues().get(1).getName());
  }

}
