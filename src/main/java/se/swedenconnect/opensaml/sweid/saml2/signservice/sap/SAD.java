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
package se.swedenconnect.opensaml.sweid.saml2.signservice.sap;

import java.io.IOException;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Representation of the Signature Activation Data (SAD) as described in the "Signature Activation Protocol for
 * Federated Signing" specification.
 *
 * @author Martin Lindström (martin@idsec.se)
 */
@JsonInclude(Include.NON_NULL)
public class SAD {

  /** Subject - holding the attribute value of the signer's unique identifier attribute. */
  @JsonProperty(value = "sub", required = true)
  private String subject;

  /** Audience - holds the entityID of the Signature Service which is the legitimate recipient of this SAD. */
  @JsonProperty(value = "aud", required = true)
  private String audience;

  /** Issuer - holding the entityID of the IdP that generated this SAD. */
  @JsonProperty(value = "iss", required = true)
  private String issuer;

  /** Expiry - specifying the time when this SAD is no longer valid (epoch time/seconds since 1970-01-01). */
  @JsonProperty(value = "exp", required = true)
  private Integer expiry;

  /** Issued At - specifying the time when this SAD was issued (epoch time - seconds since 1970-01-01). */
  @JsonProperty(value = "iat", required = true)
  private Integer issuedAt;

  /** Unique identifier of this JWT. */
  @JsonProperty(value = "jti", required = true)
  private String jwtId;

  /** A private claim that extends the registered claims with additional SAD data. */
  @JsonProperty(value = "seElnSadext", required = true)
  private Extension seElnSadext;

  /** The JSON object mapper. */
  private static final ObjectMapper jsonMapper = new ObjectMapper();

  /**
   * Creates a {@code SAD} object from its JSON representation.
   *
   * @param json the JSON representation
   * @return a {@code SAD} object
   * @throws IOException for parsing errors
   */
  public static SAD fromJson(final String json) throws IOException {
    return jsonMapper.readValue(json, SAD.class);
  }

  /**
   * Serializes the SAD object into its JSON representation.
   *
   * @return the JSON representation
   * @throws IOException for processing errors
   */
  public String toJson() throws IOException {
    return jsonMapper.writeValueAsString(this);
  }

  /**
   * Serializes the SAD object into its JSON byte representation.
   *
   * @return the JSON bytes
   * @throws IOException for processing errors
   */
  public byte[] toJsonBytes() throws IOException {
    return jsonMapper.writeValueAsBytes(this);
  }

  /**
   * Returns the attribute value of the signer's unique identifier attribute.
   *
   * @return the user ID
   */
  public String getSubject() {
    return this.subject;
  }

  /**
   * Assigns the attribute value of the signer's unique identifier attribute.
   *
   * @param subject the user ID
   */
  public void setSubject(final String subject) {
    this.subject = subject;
  }

  /**
   * Returns the entityID of the Signature Service which is the recipient of this SAD.
   *
   * @return the entityID of the recipient
   */
  public String getAudience() {
    return this.audience;
  }

  /**
   * Assigns the entityID of the Signature Service which is the recipient of this SAD.
   *
   * @param audience the entityID of the recipient
   */
  public void setAudience(final String audience) {
    this.audience = audience;
  }

  /**
   * Returns the entityID of the IdP that generated this SAD.
   *
   * @return the IdP entityID
   */
  public String getIssuer() {
    return this.issuer;
  }

  /**
   * Assigns the entityID of the IdP that generated this SAD.
   *
   * @param issuer the IdP entityID
   */
  public void setIssuer(final String issuer) {
    this.issuer = issuer;
  }

  /**
   * Returns the time when this SAD is no longer valid (epoch time/seconds since 1970-01-01).
   *
   * @return number of seconds since 1970-01-01
   */
  public Integer getExpiry() {
    return this.expiry;
  }

  /**
   * Returns the time when this SAD is no longer valid as a {@link Instant} instance.
   *
   * @return expiration time
   */
  @JsonIgnore
  public Instant getExpiryDateTime() {
    return this.expiry != null ? Instant.ofEpochMilli(this.expiry * 1000L) : null;
  }

  /**
   * Assigns the time when this SAD is no longer valid (epoch time/seconds since 1970-01-01).
   *
   * @param expiry number of seconds since 1970-01-01
   */
  public void setExpiry(final Integer expiry) {
    this.expiry = expiry;
  }

  /**
   * Assigns the time when this SAD is no longer valid.
   *
   * @param expiry expiration time
   */
  @JsonIgnore
  public void setExpiry(final Instant expiry) {
    this.expiry = expiry != null ? (int) (expiry.toEpochMilli() / 1000L) : null;
  }

  /**
   * Returns the time when this SAD was issued (epoch time/seconds since 1970-01-01).
   *
   * @return number of seconds since 1970-01-01
   */
  public Integer getIssuedAt() {
    return this.issuedAt;
  }

  /**
   * Returns the time when this SAD was issued as a {@link Instant} instance.
   *
   * @return timestamp
   */
  @JsonIgnore
  public Instant getIssuedAtDateTime() {
    return this.issuedAt != null ? Instant.ofEpochMilli(this.issuedAt * 1000L) : null;
  }

  /**
   * Assigns the time when this SAD was issued (epoch time/seconds since 1970-01-01).
   *
   * @param issuedAt number of seconds since 1970-01-01
   */
  public void setIssuedAt(final Integer issuedAt) {
    this.issuedAt = issuedAt;
  }

  /**
   * Assigns the time when this SAD was issued.
   *
   * @param issuedAt issue time
   */
  @JsonIgnore
  public void setIssuedAt(final Instant issuedAt) {
    this.issuedAt = issuedAt != null ? (int) (issuedAt.toEpochMilli() / 1000L) : null;
  }

  /**
   * Returns the unique identifier of this JWT.
   *
   * @return JWT ID
   */
  public String getJwtId() {
    return this.jwtId;
  }

  /**
   * Assigns the unique identifier of this JWT.
   *
   * @param jwtId JWT ID
   */
  public void setJwtId(final String jwtId) {
    this.jwtId = jwtId;
  }

  /**
   * Returns the SAD extension claim.
   *
   * @return SAD extension claim
   */
  public Extension getSeElnSadext() {
    return this.seElnSadext;
  }

  /**
   * Assigns the SAD extension claim.
   *
   * @param seElnSadext SAD extension claim
   */
  public void setSeElnSadext(final Extension seElnSadext) {
    this.seElnSadext = seElnSadext;
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return String.format("sub='%s', aud='%s', iss='%s', exp='%s', iss='%s', jti='%s', seElnSadext=[%s]",
        this.subject, this.audience, this.issuer, this.expiry, this.issuedAt, this.jwtId, this.seElnSadext);
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.audience == null) ? 0 : this.audience.hashCode());
    result = prime * result + ((this.expiry == null) ? 0 : this.expiry.hashCode());
    result = prime * result + ((this.issuedAt == null) ? 0 : this.issuedAt.hashCode());
    result = prime * result + ((this.issuer == null) ? 0 : this.issuer.hashCode());
    result = prime * result + ((this.jwtId == null) ? 0 : this.jwtId.hashCode());
    result = prime * result + ((this.seElnSadext == null) ? 0 : this.seElnSadext.hashCode());
    result = prime * result + ((this.subject == null) ? 0 : this.subject.hashCode());
    return result;
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    SAD other = (SAD) obj;
    if (this.audience == null) {
      if (other.audience != null) {
        return false;
      }
    }
    else if (!this.audience.equals(other.audience)) {
      return false;
    }
    if (this.expiry == null) {
      if (other.expiry != null) {
        return false;
      }
    }
    else if (!this.expiry.equals(other.expiry)) {
      return false;
    }
    if (this.issuedAt == null) {
      if (other.issuedAt != null) {
        return false;
      }
    }
    else if (!this.issuedAt.equals(other.issuedAt)) {
      return false;
    }
    if (this.issuer == null) {
      if (other.issuer != null) {
        return false;
      }
    }
    else if (!this.issuer.equals(other.issuer)) {
      return false;
    }
    if (this.jwtId == null) {
      if (other.jwtId != null) {
        return false;
      }
    }
    else if (!this.jwtId.equals(other.jwtId)) {
      return false;
    }
    if (this.seElnSadext == null) {
      if (other.seElnSadext != null) {
        return false;
      }
    }
    else if (!this.seElnSadext.equals(other.seElnSadext)) {
      return false;
    }
    if (this.subject == null) {
      if (other.subject != null) {
        return false;
      }
    }
    else if (!this.subject.equals(other.subject)) {
      return false;
    }
    return true;
  }

  /**
   * Represents the SAD Extension claim as described in section 3.2.1.2 of the "Signature Activation Protocol for
   * Federated Signing" specification.
   *
   * @author Martin Lindström (martin@idsec.se)
   */
  @JsonInclude(Include.NON_NULL)
  public static class Extension {

    /** The version of this claim, default 1.0 (Optional). */
    @JsonProperty(value = "ver", defaultValue = "1.0")
    private String version;

    /** In Response To - holding the identifier of the {@code SADRequest} message that requested this SAD. */
    @JsonProperty(value = "irt", required = true)
    private String inResponseTo;

    /**
     * Attribute - holding the URI identifier (attribute name) of the attribute specifying the user's unique identifier
     * value.
     */
    @JsonProperty(value = "attr", required = true)
    private String attributeName;

    /**
     * Level of assurance - holding the URI identifier of the level of assurance (LoA) used to authenticate the signer.
     */
    @JsonProperty(value = "loa", required = true)
    private String loa;

    /** RequestID - holding the ID of the Sign Request associated with this SAD. */
    @JsonProperty(value = "reqid", required = true)
    private String requestID;

    /** Specifies the number of documents to be signed in the associated sign request. */
    @JsonProperty(value = "docs", required = true)
    private Integer numberOfDocuments;

    /**
     * Returns the version of this claim.
     *
     * @return the version
     */
    public String getVersion() {
      return this.version;
    }

    /**
     * Returns the version of this claim represented as a {@link SADVersion} object.
     *
     * @return the version
     */
    @JsonIgnore
    public SADVersion getSADVersion() {
      return this.version != null ? SADVersion.valueOf(this.version) : SADVersion.VERSION_10;
    }

    /**
     * Assigns the version of this claim.
     *
     * @param version the version
     */
    public void setVersion(final String version) {
      this.version = version;
    }

    /**
     * Returns the ID of the {@code SADRequest} message that requested this SAD.
     *
     * @return ID of corresponding {@code SADRequest}
     */
    public String getInResponseTo() {
      return this.inResponseTo;
    }

    /**
     * Assigns the ID of the {@code SADRequest} message that requested this SAD.
     *
     * @param inResponseTo ID of corresponding {@code SADRequest}
     */
    public void setInResponseTo(final String inResponseTo) {
      this.inResponseTo = inResponseTo;
    }

    /**
     * Returns the URI identifier (attribute name) of the attribute specifying the user's unique identifier value.
     *
     * @return attribute name for the user ID attribute value
     */
    public String getAttributeName() {
      return this.attributeName;
    }

    /**
     * Assigns the URI identifier (attribute name) of the attribute specifying the user's unique identifier value.
     *
     * @param attributeName attribute name for the user ID attribute value
     */
    public void setAttributeName(final String attributeName) {
      this.attributeName = attributeName;
    }

    /**
     * Returns the URI identifier of the level of assurance (LoA) used to authenticate the signer.
     *
     * @return LoA URI
     */
    public String getLoa() {
      return this.loa;
    }

    /**
     * Assigns the URI identifier of the level of assurance (LoA) used to authenticate the signer.
     *
     * @param loa LoA URI
     */
    public void setLoa(final String loa) {
      this.loa = loa;
    }

    /**
     * Returns the ID of the Sign Request associated with this SAD.
     *
     * @return SignRequest ID
     */
    public String getRequestID() {
      return this.requestID;
    }

    /**
     * Assigns the ID of the Sign Request associated with this SAD.
     *
     * @param requestID SignRequest ID
     */
    public void setRequestID(final String requestID) {
      this.requestID = requestID;
    }

    /**
     * Returns the number of documents to be signed in the associated sign request.
     *
     * @return the number of documents to be signed
     */
    public Integer getNumberOfDocuments() {
      return this.numberOfDocuments;
    }

    /**
     * Assigns the number of documents to be signed in the associated sign request.
     *
     * @param numberOfDocuments the number of documents to be signed
     */
    public void setNumberOfDocuments(final Integer numberOfDocuments) {
      this.numberOfDocuments = numberOfDocuments;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
      return String.format("ver='%s', irt='%s', attr='%s', loa='%s', reqid='%s', docs='%d'",
          this.version, this.inResponseTo, this.attributeName, this.loa, this.requestID, this.numberOfDocuments);
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((this.attributeName == null) ? 0 : this.attributeName.hashCode());
      result = prime * result + ((this.inResponseTo == null) ? 0 : this.inResponseTo.hashCode());
      result = prime * result + ((this.loa == null) ? 0 : this.loa.hashCode());
      result = prime * result + ((this.numberOfDocuments == null) ? 0 : this.numberOfDocuments.hashCode());
      result = prime * result + ((this.requestID == null) ? 0 : this.requestID.hashCode());
      result = prime * result + ((this.version == null) ? 0 : this.version.hashCode());
      return result;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(final Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      if (this.getClass() != obj.getClass()) {
        return false;
      }
      Extension other = (Extension) obj;
      if (this.attributeName == null) {
        if (other.attributeName != null) {
          return false;
        }
      }
      else if (!this.attributeName.equals(other.attributeName)) {
        return false;
      }
      if (this.inResponseTo == null) {
        if (other.inResponseTo != null) {
          return false;
        }
      }
      else if (!this.inResponseTo.equals(other.inResponseTo)) {
        return false;
      }
      if (this.loa == null) {
        if (other.loa != null) {
          return false;
        }
      }
      else if (!this.loa.equals(other.loa)) {
        return false;
      }
      if (this.numberOfDocuments == null) {
        if (other.numberOfDocuments != null) {
          return false;
        }
      }
      else if (!this.numberOfDocuments.equals(other.numberOfDocuments)) {
        return false;
      }
      if (this.requestID == null) {
        if (other.requestID != null) {
          return false;
        }
      }
      else if (!this.requestID.equals(other.requestID)) {
        return false;
      }
      if (this.version == null) {
        if (other.version != null) {
          return false;
        }
      }
      else if (!this.version.equals(other.version)) {
        return false;
      }
      return true;
    }

  }
}
