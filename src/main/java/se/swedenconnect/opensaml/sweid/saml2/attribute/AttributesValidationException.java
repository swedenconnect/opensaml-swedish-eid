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
package se.swedenconnect.opensaml.sweid.saml2.attribute;

import se.swedenconnect.opensaml.sweid.LibraryVersion;

/**
 * Exception class that is used to indicate validation errors for attributes.
 *
 * @author Martin Lindström (martin@idsec.se)
 */
public class AttributesValidationException extends Exception {

  /** For serializing. */
  private static final long serialVersionUID = LibraryVersion.SERIAL_VERSION_UID;

  /**
   * Constructor assigning an error message.
   *
   * @param message the error message.
   */
  public AttributesValidationException(final String message) {
    super(message);
  }

  /**
   * Constructor assigning an error message and the underlying cause of the error.
   *
   * @param message the error message.
   * @param cause the underlying cause of the error.
   */
  public AttributesValidationException(final String message, final Throwable cause) {
    super(message, cause);
  }

}
