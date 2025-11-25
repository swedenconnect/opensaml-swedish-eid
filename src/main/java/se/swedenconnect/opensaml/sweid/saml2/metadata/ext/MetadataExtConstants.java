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
package se.swedenconnect.opensaml.sweid.saml2.metadata.ext;

/**
 * Defines contants for SAML metadata extensions.
 *
 * @author Martin Lindström
 */
public class MetadataExtConstants {

  /** Namespace prefix for the mdorgext (Metadata Organization Extensions) namespace. */
  public static final String MDORGEXT_NS_PREFIX = "mdorgext";

  /** The namespace for the OrganizationNumber schema. */
  public static final String MDORGEXT_NS = "http://id.swedenconnect.se/authn/1.0/md-org-ext/ns";

  private MetadataExtConstants() {}
}
