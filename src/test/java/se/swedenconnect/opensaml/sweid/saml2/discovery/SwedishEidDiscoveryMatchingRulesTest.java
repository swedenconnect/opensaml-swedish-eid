/*
 * Copyright 2016-2023 Sweden Connect
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
package se.swedenconnect.opensaml.sweid.saml2.discovery;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import se.swedenconnect.opensaml.sweid.saml2.metadata.entitycategory.EntityCategoryConstants;

/**
 * Test cases for {@code SwedishEidDiscoveryMatchingRules}.
 *
 * @author Martin Lindström (martin@idsec.se)
 */
public class SwedishEidDiscoveryMatchingRulesTest {

  private static Stream<Arguments> data() {
    return Stream.of(
        Arguments.of(Collections.emptyList(), Collections.emptyList(), Boolean.TRUE),
        Arguments.of(Arrays.asList(EntityCategoryConstants.SERVICE_ENTITY_CATEGORY_LOA3_PNR.getUri()), Arrays.asList(
            EntityCategoryConstants.SERVICE_ENTITY_CATEGORY_LOA3_PNR.getUri()), Boolean.TRUE),
        Arguments.of(Collections.emptyList(),
            Arrays.asList(EntityCategoryConstants.SERVICE_ENTITY_CATEGORY_LOA3_PNR.getUri()),
            Boolean.TRUE),
        Arguments.of(Arrays.asList(EntityCategoryConstants.SERVICE_ENTITY_CATEGORY_LOA3_PNR.getUri()),
            Collections.emptyList(),
            Boolean.FALSE),
        Arguments.of(Arrays.asList(EntityCategoryConstants.SERVICE_ENTITY_CATEGORY_LOA3_PNR.getUri()), Arrays.asList(
            EntityCategoryConstants.SERVICE_ENTITY_CATEGORY_LOA2_PNR.getUri()), Boolean.FALSE),
        Arguments.of(Arrays.asList(EntityCategoryConstants.SERVICE_ENTITY_CATEGORY_LOA3_PNR.getUri()), Arrays.asList(
            EntityCategoryConstants.SERVICE_ENTITY_CATEGORY_LOA2_PNR.getUri(),
            EntityCategoryConstants.SERVICE_ENTITY_CATEGORY_LOA3_PNR
                .getUri()),
            Boolean.TRUE),
        Arguments.of(Collections.emptyList(),
            Arrays.asList(EntityCategoryConstants.SERVICE_CONTRACT_CATEGORY_EID_CHOICE_2017.getUri()),
            Boolean.FALSE),
        Arguments.of(Arrays.asList(EntityCategoryConstants.SERVICE_CONTRACT_CATEGORY_EID_CHOICE_2017.getUri()),
            Arrays.asList(
                EntityCategoryConstants.SERVICE_CONTRACT_CATEGORY_EID_CHOICE_2017.getUri()),
            Boolean.TRUE),
        Arguments.of(Arrays.asList(EntityCategoryConstants.SERVICE_CONTRACT_CATEGORY_EID_CHOICE_2017.getUri()),
            Arrays.asList(
                EntityCategoryConstants.SERVICE_CONTRACT_CATEGORY_EID_CHOICE_2017.getUri(),
                EntityCategoryConstants.SERVICE_CONTRACT_CATEGORY_SWEDEN_CONNECT.getUri()),
            Boolean.TRUE),
        Arguments.of(Arrays.asList(EntityCategoryConstants.SERVICE_CONTRACT_CATEGORY_SWEDEN_CONNECT.getUri()),
            Arrays.asList(
                EntityCategoryConstants.SERVICE_CONTRACT_CATEGORY_EID_CHOICE_2017.getUri()),
            Boolean.FALSE),
        Arguments.of(Arrays.asList(EntityCategoryConstants.SERVICE_PROPERTY_CATEGORY_MOBILE_AUTH.getUri()),
            Collections.emptyList(),
            Boolean.FALSE),
        Arguments.of(Arrays.asList(EntityCategoryConstants.SERVICE_PROPERTY_CATEGORY_MOBILE_AUTH.getUri()),
            Arrays.asList(
                EntityCategoryConstants.SERVICE_PROPERTY_CATEGORY_SCAL2.getUri()),
            Boolean.FALSE),
        Arguments.of(Arrays.asList(EntityCategoryConstants.SERVICE_PROPERTY_CATEGORY_MOBILE_AUTH.getUri()),
            Arrays.asList(
                EntityCategoryConstants.SERVICE_PROPERTY_CATEGORY_SCAL2.getUri(),
                EntityCategoryConstants.SERVICE_PROPERTY_CATEGORY_MOBILE_AUTH
                    .getUri()),
            Boolean.TRUE));
  }

  @ParameterizedTest
  @MethodSource("data")
  public void testDiscoveryMatching(final List<String> consumingService, final List<String> providingService,
      final Boolean expectedResult) {
    Assertions.assertEquals(expectedResult.booleanValue(),
        SwedishEidDiscoveryMatchingRules.isMatch(consumingService, providingService),
        String.format("Expected %s when matching consuming service %s against %s",
            expectedResult ? "match" : "no match",
            consumingService, providingService));
  }

}
