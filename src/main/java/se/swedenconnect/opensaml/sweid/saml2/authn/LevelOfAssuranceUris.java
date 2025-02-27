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
package se.swedenconnect.opensaml.sweid.saml2.authn;

/**
 * Constants representing Level of Assurance URI:s.
 *
 * @author Martin Lindström (martin@idsec.se)
 */
public class LevelOfAssuranceUris {

  /** The Authentication Context URI for Level of Assurance 1. */
  public static final String AUTHN_CONTEXT_URI_LOA1 = "http://id.elegnamnden.se/loa/1.0/loa1";

  /** The Authentication Context URI for Level of Assurance 2. */
  public static final String AUTHN_CONTEXT_URI_LOA2 = "http://id.elegnamnden.se/loa/1.0/loa2";

  /** The Authentication Context URI for uncertified (self-declared) Level of Assurance 2 compliance. */
  public static final String AUTHN_CONTEXT_URI_UNCERTIFIED_LOA2 = "http://id.swedenconnect.se/loa/1.0/uncertified-loa2";

  /** The Authentication Context URI for Level of Assurance 2 for non residents. */
  public static final String AUTHN_CONTEXT_URI_LOA2_NONRESIDENT = "http://id.swedenconnect.se/loa/1.0/loa2-nonresident";

  /** The Authentication Context URI for Level of Assurance 3. */
  public static final String AUTHN_CONTEXT_URI_LOA3 = "http://id.elegnamnden.se/loa/1.0/loa3";

  /** The Authentication Context URI for uncertified (self-declared) Level of Assurance 3 compliance. */
  public static final String AUTHN_CONTEXT_URI_UNCERTIFIED_LOA3 = "http://id.swedenconnect.se/loa/1.0/uncertified-loa3";

  /** The Authentication Context URI for Level of Assurance 3 for non residents. */
  public static final String AUTHN_CONTEXT_URI_LOA3_NONRESIDENT = "http://id.swedenconnect.se/loa/1.0/loa3-nonresident";

  /** The Authentication Context URI for Level of Assurance 4. */
  public static final String AUTHN_CONTEXT_URI_LOA4 = "http://id.elegnamnden.se/loa/1.0/loa4";

  /** The Authentication Context URI for Level of Assurance 4 for non residents. */
  public static final String AUTHN_CONTEXT_URI_LOA4_NONRESIDENT = "http://id.swedenconnect.se/loa/1.0/loa4-nonresident";

  /** The Authentication Context URI for eIDAS "low". */
  public static final String AUTHN_CONTEXT_URI_EIDAS_LOW = "http://id.elegnamnden.se/loa/1.0/eidas-low";

  /** The Authentication Context URI for eIDAS "low" for notified eID:s. */
  public static final String AUTHN_CONTEXT_URI_EIDAS_LOW_NF = "http://id.elegnamnden.se/loa/1.0/eidas-nf-low";

  /** The Authentication Context URI for uncertified eIDAS "low". */
  public static final String AUTHN_CONTEXT_URI_UNCERTIFIED_EIDAS_LOW =
      "http://id.swedenconnect.se/loa/1.0/uncertified-eidas-low";

  /** The Authentication Context URI for eIDAS "substantial". */
  public static final String AUTHN_CONTEXT_URI_EIDAS_SUBSTANTIAL = "http://id.elegnamnden.se/loa/1.0/eidas-sub";

  /** The Authentication Context URI for eIDAS "substantial" for notified eID:s. */
  public static final String AUTHN_CONTEXT_URI_EIDAS_SUBSTANTIAL_NF = "http://id.elegnamnden.se/loa/1.0/eidas-nf-sub";

  /** The Authentication Context URI for uncertified eIDAS "substantial". */
  public static final String AUTHN_CONTEXT_URI_UNCERTIFIED_EIDAS_SUBSTANTIAL =
      "http://id.swedenconnect.se/loa/1.0/uncertified-eidas-sub";

  /** The Authentication Context URI for eIDAS "high". */
  public static final String AUTHN_CONTEXT_URI_EIDAS_HIGH = "http://id.elegnamnden.se/loa/1.0/eidas-high";

  /** The Authentication Context URI for eIDAS "high" for notified eID:s. */
  public static final String AUTHN_CONTEXT_URI_EIDAS_HIGH_NF = "http://id.elegnamnden.se/loa/1.0/eidas-nf-high";

  /** The Authentication Context URI for uncertified eIDAS "high". */
  public static final String AUTHN_CONTEXT_URI_UNCERTIFIED_EIDAS_HIGH =
      "http://id.swedenconnect.se/loa/1.0/uncertified-eidas-high";

  private LevelOfAssuranceUris() {
  }

}
