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
package se.swedenconnect.opensaml.sweid.saml2.authn.umsg;

/**
 * Defines constants for the "User Message Extension in SAML Authentication Requests" specification.
 *
 * @author Martin Lindström
 */
public class UserMessageConstants {

  /** Namespace prefix for the User Message namespace. */
  public static final String UMSG_NS_PREFIX = "umsg";

  /** The namespace for the User Message schema. */
  public static final String UMSG_NS = "http://id.swedenconnect.se/authn/1.0/user-message/ns";

  private UserMessageConstants() {
  }

}
