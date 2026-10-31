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
package se.swedenconnect.opensaml.sweid.saml2.signservice.sap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Test cases for {@link SADVersion}.
 */
public class SADVersionTest {

  @Test
  void testValueOf() {
    Assertions.assertSame(SADVersion.VERSION_10, SADVersion.valueOf("1.0"));
    Assertions.assertEquals(SADVersion.valueOf(2, 1), SADVersion.valueOf(" 2.1 "));
  }

  @Test
  void testHashCode() {
    Assertions.assertEquals(SADVersion.valueOf(2, 0).hashCode(), SADVersion.valueOf(2, 0).hashCode());
  }

  @ParameterizedTest
  @NullSource
  @ValueSource(strings = { "", "1", "1.x", "1.0.5", "a.b", "99999999999.0" })
  void testInvalid(final String version) {
    Assertions.assertThrows(IllegalArgumentException.class, () -> SADVersion.valueOf(version));
  }

}
