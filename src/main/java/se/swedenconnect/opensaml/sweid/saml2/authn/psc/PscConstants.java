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
package se.swedenconnect.opensaml.sweid.saml2.authn.psc;

/**
 * Defines constants for the "Principal Selection in SAML Authentication Requests" specification.
 *
 * @author Martin Lindström (martin@idsec.se)
 */
public class PscConstants {

  /** Namespace prefix for the PSC (Principal Selection Criteria) namespace. */
  public static final String PSC_NS_PREFIX = "psc";

  /** The namespace for the Principal Selection Criteria schema. */
  public static final String PSC_NS = "http://id.swedenconnect.se/authn/1.0/principal-selection/ns";

  private PscConstants() {
  }

}
