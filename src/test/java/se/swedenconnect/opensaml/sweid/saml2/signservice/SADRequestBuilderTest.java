/*
 * Copyright 2016-2024 Sweden Connect
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
package se.swedenconnect.opensaml.sweid.saml2.signservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import se.swedenconnect.opensaml.sweid.OpenSAMLTestBase;
import se.swedenconnect.opensaml.sweid.saml2.signservice.build.SADRequestBuilder;
import se.swedenconnect.opensaml.sweid.saml2.signservice.build.SADRequestBuilder.RequestParamsBuilder;
import se.swedenconnect.opensaml.sweid.saml2.signservice.sap.SADRequest;
import se.swedenconnect.opensaml.sweid.saml2.signservice.sap.SADVersion;

/**
 * Test cases for {@code SADRequestBuilder}.
 *
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class SADRequestBuilderTest extends OpenSAMLTestBase {

  @Test
  public void testBuildSADRequest() throws Exception {

    SADRequest request = SADRequestBuilder.builder()
        .id("_a74a068d0548a919e503e5f9ef901851")
        .requesterID("http://www.example.com/sigservice")
        .signRequestID("123456")
        .docCount(5)
        .requestedVersion(SADVersion.VERSION_10)
        .requestParams(
            RequestParamsBuilder.builder()
                .parameters(RequestParamsBuilder.parameter("param1", "value1"),
                    RequestParamsBuilder.parameter("param2", "value2"))
                .build())
        .build();

    Assertions.assertEquals("_a74a068d0548a919e503e5f9ef901851", request.getID());
    Assertions.assertEquals("http://www.example.com/sigservice", request.getRequesterID());
    Assertions.assertEquals("123456", request.getSignRequestID());
    Assertions.assertEquals(Integer.valueOf(5), request.getDocCount());
    Assertions.assertEquals(SADVersion.VERSION_10, request.getRequestedVersion());
    Assertions.assertTrue(request.getRequestParams().getParameters().size() == 2);
    Assertions.assertEquals("value1", request.getRequestParams().getParameters().get(0).getValue());
    Assertions.assertEquals("param1", request.getRequestParams().getParameters().get(0).getName());
    Assertions.assertEquals("value2", request.getRequestParams().getParameters().get(1).getValue());
    Assertions.assertEquals("param2", request.getRequestParams().getParameters().get(1).getName());
  }

}
