/*
 * Copyright 2016-2021 Sweden Connect
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
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import se.swedenconnect.opensaml.sweid.saml2.metadata.entitycategory.EntityCategoryConstants;

/**
 * Test cases for {@code SwedishEidDiscoveryMatchingRules}.
 * 
 * @author Martin Lindström (martin@idsec.se)
 */
@RunWith(Parameterized.class)
public class SwedishEidDiscoveryMatchingRulesTest {

  @Parameters(name = "{index}: test({0}, {1})= {2}")
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][] {
        /* 00 */ { Collections.emptyList(), Collections.emptyList(), Boolean.TRUE },
        /* 01 */ { Arrays.asList(EntityCategoryConstants.SERVICE_ENTITY_CATEGORY_LOA3_PNR.getUri()), Arrays.asList(
          EntityCategoryConstants.SERVICE_ENTITY_CATEGORY_LOA3_PNR.getUri()), Boolean.TRUE },
        /* 02 */ { Collections.emptyList(), Arrays.asList(EntityCategoryConstants.SERVICE_ENTITY_CATEGORY_LOA3_PNR.getUri()),
            Boolean.TRUE },
        /* 03 */ { Arrays.asList(EntityCategoryConstants.SERVICE_ENTITY_CATEGORY_LOA3_PNR.getUri()), Collections.emptyList(),
            Boolean.FALSE },
        /* 04 */ { Arrays.asList(EntityCategoryConstants.SERVICE_ENTITY_CATEGORY_LOA3_PNR.getUri()), Arrays.asList(
          EntityCategoryConstants.SERVICE_ENTITY_CATEGORY_LOA2_PNR.getUri()), Boolean.FALSE },
        /* 05 */ { Arrays.asList(EntityCategoryConstants.SERVICE_ENTITY_CATEGORY_LOA3_PNR.getUri()), Arrays.asList(
          EntityCategoryConstants.SERVICE_ENTITY_CATEGORY_LOA2_PNR.getUri(), EntityCategoryConstants.SERVICE_ENTITY_CATEGORY_LOA3_PNR
            .getUri()), Boolean.TRUE },
        /* 06 */ { Collections.emptyList(), Arrays.asList(EntityCategoryConstants.SERVICE_CONTRACT_CATEGORY_EID_CHOICE_2017.getUri()),
            Boolean.FALSE },
        /* 07 */ { Arrays.asList(EntityCategoryConstants.SERVICE_CONTRACT_CATEGORY_EID_CHOICE_2017.getUri()), Arrays.asList(
          EntityCategoryConstants.SERVICE_CONTRACT_CATEGORY_EID_CHOICE_2017.getUri()), Boolean.TRUE },
        /* 08 */ { Arrays.asList(EntityCategoryConstants.SERVICE_CONTRACT_CATEGORY_EID_CHOICE_2017.getUri()), Arrays.asList(
          EntityCategoryConstants.SERVICE_CONTRACT_CATEGORY_EID_CHOICE_2017.getUri(),
          EntityCategoryConstants.SERVICE_CONTRACT_CATEGORY_SWEDEN_CONNECT.getUri()), Boolean.TRUE },
        /* 09 */ { Arrays.asList(EntityCategoryConstants.SERVICE_CONTRACT_CATEGORY_SWEDEN_CONNECT.getUri()), Arrays.asList(
          EntityCategoryConstants.SERVICE_CONTRACT_CATEGORY_EID_CHOICE_2017.getUri()), Boolean.FALSE },
        /* 10 */ { Arrays.asList(EntityCategoryConstants.SERVICE_PROPERTY_CATEGORY_MOBILE_AUTH.getUri()), Collections.emptyList(),
            Boolean.FALSE },
        /* 11 */ { Arrays.asList(EntityCategoryConstants.SERVICE_PROPERTY_CATEGORY_MOBILE_AUTH.getUri()), Arrays.asList(
          EntityCategoryConstants.SERVICE_PROPERTY_CATEGORY_SCAL2.getUri()), Boolean.FALSE },
        /* 12 */ { Arrays.asList(EntityCategoryConstants.SERVICE_PROPERTY_CATEGORY_MOBILE_AUTH.getUri()), Arrays.asList(
          EntityCategoryConstants.SERVICE_PROPERTY_CATEGORY_SCAL2.getUri(), EntityCategoryConstants.SERVICE_PROPERTY_CATEGORY_MOBILE_AUTH
            .getUri()), Boolean.TRUE }          
    });
  }

  @Parameter(0)
  public List<String> consumingService;

  @Parameter(1)
  public List<String> providingService;

  @Parameter(2)
  public Boolean expectedResult;

  @Test
  public void testDiscoveryMatching() {
    Assert.assertEquals(String.format("Expected %s when matching consuming service %s against %s", expectedResult ? "match" : "no match",
      consumingService, providingService),
      expectedResult.booleanValue(), SwedishEidDiscoveryMatchingRules.isMatch(consumingService, providingService));
  }

}
