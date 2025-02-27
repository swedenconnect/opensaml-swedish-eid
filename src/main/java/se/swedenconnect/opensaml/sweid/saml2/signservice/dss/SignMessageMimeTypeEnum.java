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
package se.swedenconnect.opensaml.sweid.saml2.signservice.dss;

/**
 * Enumeration that represents the possible values of the {@code MimeType} attribute of the {@link SignMessage} element.
 *
 * @author Martin Lindström (martin@idsec.se)
 */
public enum SignMessageMimeTypeEnum {

  /**
   * Represents HTML sign message format.
   */
  TEXT_HTML("text/html"),

  /**
   * Represents a sign message in text format.
   */
  TEXT("text"),

  /**
   * Represents markdown sign message format.
   */
  TEXT_MARKDOWN("text/markdown");

  /**
   * Returns the string representation of the MIME type.
   *
   * @return the MIME type
   */
  public String getMimeType() {
    return this.mimeType;
  }

  /**
   * Parses the supplied MIME type into its corresponding enum value.
   *
   * @param mimeType the MIME type in string representation
   * @return the enum value, or null if no match is found
   */
  public static SignMessageMimeTypeEnum parse(final String mimeType) {
    for (SignMessageMimeTypeEnum e : SignMessageMimeTypeEnum.values()) {
      if (e.getMimeType().equals(mimeType)) {
        return e;
      }
    }
    return null;
  }

  /**
   * Constructor.
   *
   * @param mimeType the MIME type
   */
  SignMessageMimeTypeEnum(final String mimeType) {
    this.mimeType = mimeType;
  }

  /** The MIME type. */
  private String mimeType;

}
