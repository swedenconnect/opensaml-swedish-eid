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
package se.swedenconnect.opensaml.sweid.saml2.signservice.dss;

/**
 * Holds constants defined in "DSS Extension for Federated Central Signing Services".
 *
 * @author Martin Lindström (martin@idsec.se)
 */
public class DssExtensionsConstants {

  /** Namespace prefix for the DSS extension for the Swedish eID Framework. */
  public static final String SWEID_DSS_EXT_PREFIX = "csig";

  /** The namespace for the DSS extension for the Swedish eID Framework. */
  public static final String SWEID_DSS_EXT_NS = "http://id.elegnamnden.se/csig/1.1/dss-ext/ns";

  private DssExtensionsConstants() {
  }

}
