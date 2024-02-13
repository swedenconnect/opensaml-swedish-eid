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
package se.swedenconnect.opensaml.sweid.saml2.signservice;

import se.swedenconnect.opensaml.sweid.LibraryVersion;

/**
 * Exception class for SAD validation errors.
 *
 * @author Martin Lindström (martin@idsec.se)
 */
public class SADValidationException extends Exception {

  /**
   * Possible validation errors.
   */
  public enum ErrorCode {

    /** The JWT or its contained JSON could not be successfully parsed. */
    JWT_PARSE_ERROR,

    /** The SAD is not correct (i.e., missing claims). */
    BAD_SAD_FORMAT,

    /** Signature validation error. */
    SIGNATURE_VALIDATION_ERROR,

    /** SAD has expired and is no longer valid. */
    SAD_EXPIRED,

    /** Mismatching issuer - issuer of SAD is not the same as issuing IdP. */
    VALIDATION_BAD_ISSUER,

    /** Mismatching audience - the receiving entity does not match the indicated audience. */
    VALIDATION_BAD_AUDIENCE,

    /** Mismatching subject - the subject in the SAD does not match corresponding attribute from assertion. */
    VALIDATION_BAD_SUBJECT,

    /** Mismatching in-response-to - the irt claim does not match expected SADRequest ID. */
    VALIDATION_BAD_IRT,

    /** Mismatching LoA - The LoA in the SAD does not correspond with the LoA in the assertion. */
    VALIDATION_BAD_LOA,

    /** Mismatching number of documents between SAD and corresponding SADRequest. */
    VALIDATION_BAD_DOCS,

    /** Mismatching SignRequest ID (SAD has different value that what was expected). */
    VALIDATION_BAD_SIGNREQUESTID,

    /** SAD is missing from assertion. */
    NO_SAD_ATTRIBUTE,

    /** The attribute name given in the SAD for the subject was not found in the assertion. */
    MISSING_SUBJECT_ATTRIBUTE

  }

  /** For serializing. */
  private static final long serialVersionUID = LibraryVersion.SERIAL_VERSION_UID;

  /** The SAD validation error code. */
  private ErrorCode errorCode;

  /**
   * Constructor assigning the error code and error message.
   *
   * @param errorCode the error code
   * @param message the error message
   */
  public SADValidationException(final ErrorCode errorCode, final String message) {
    super(message);
    this.errorCode = errorCode;
  }

  /**
   * Constructor assigning the error code, the error message and the cause of the error.
   *
   * @param errorCode the error code
   * @param message the error message
   * @param cause the cause of the error
   */
  public SADValidationException(final ErrorCode errorCode, final String message, final Throwable cause) {
    super(message, cause);
    this.errorCode = errorCode;
  }

  /**
   * Returns the error code.
   *
   * @return error code
   */
  public ErrorCode getErrorCode() {
    return this.errorCode;
  }

}
