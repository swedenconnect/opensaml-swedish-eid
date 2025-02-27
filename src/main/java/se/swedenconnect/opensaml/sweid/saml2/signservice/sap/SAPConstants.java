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
package se.swedenconnect.opensaml.sweid.saml2.signservice.sap;

/**
 * Defines constants for the "Signature Activation Protocol for Federated Signing" specification.
 *
 * @author Martin Lindström (martin@idsec.se)
 */
public class SAPConstants {

  /** Namespace prefix for the SAP namespace. */
  public static final String SAP_NS_PREFIX = "sap";

  /** The namespace for the Signature Activation Protocol. */
  public static final String SAP_NS = "http://id.elegnamnden.se/csig/1.1/sap/ns";

  private SAPConstants() {
  }

}
