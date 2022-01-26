/*
 * Copyright 2016-2022 Sweden Connect
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

import java.io.IOException;
import java.security.SignatureException;
import java.util.Base64;

import org.apache.commons.lang3.RandomStringUtils;
import org.opensaml.security.x509.X509Credential;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.util.Base64URL;

import se.swedenconnect.opensaml.sweid.saml2.attribute.AttributeConstants;
import se.swedenconnect.opensaml.sweid.saml2.signservice.sap.SAD;
import se.swedenconnect.opensaml.sweid.saml2.signservice.sap.SADVersion;

/**
 * A bean for building a {@code SAD} object and a signed JWT holding the SAD.
 * 
 * @author Martin Lindström (martin@idsec.se)
 */
public class SADFactory {

  /** The default validity time for a SAD (five minutes). */
  public static final int DEFAULT_VALIDITY_TIME = 300;

  /**
   * The default attribute name for the user ID ({@value AttributeConstants#ATTRIBUTE_NAME_PERSONAL_IDENTITY_NUMBER}).
   */
  public static final String DEFAULT_USER_ID_ATTRIBUTE_NAME = AttributeConstants.ATTRIBUTE_NAME_PERSONAL_IDENTITY_NUMBER;

  /** The default size for generated JWT identifiers (24) */
  public static final int DEFAULT_JWT_ID_SIZE = 24;

  /** The name of the default JWT signature algorithm to be used (RS256). */
  public static final String DEFAULT_JWT_SIGNING_ALGORITHM = JWSAlgorithm.RS256.getName();

  /** The entityID of the issuing IdP. */
  private String idpEntityID;

  /** The IdP signature credential. */
  private X509Credential signingCredential;

  /** The validity time for a SAD. Defaults to {@link #DEFAULT_VALIDITY_TIME}. */
  private int validityTime = DEFAULT_VALIDITY_TIME;

  /** The attribute name for the attribute holding the user ID. Defaults to {@link #DEFAULT_USER_ID_ATTRIBUTE_NAME}. */
  private String userIdAttributeName = DEFAULT_USER_ID_ATTRIBUTE_NAME;

  /** The size of generated JWT identifiers. The default is {@link #DEFAULT_JWT_ID_SIZE}. */
  private int jwtIdSize = DEFAULT_JWT_ID_SIZE;

  /** The JWT signature algorithm. The default is {@link #DEFAULT_JWT_SIGNING_ALGORITHM}. */
  private String jwtSigningAlgorithm = DEFAULT_JWT_SIGNING_ALGORITHM;

  /**
   * Constructor.
   * 
   * @param idpEntityID
   *          the entityID of the issuing IdP
   * @param signingCredential
   *          the IdP signature credential
   */
  public SADFactory(final String idpEntityID, final X509Credential signingCredential) {
    this.idpEntityID = idpEntityID;
    this.signingCredential = signingCredential;
  }

  /**
   * Returns a builder that allows building a SAD using a cascading builder pattern.
   * 
   * @return a SAD builder
   */
  public SADBuilder getBuilder() {
    return new SADBuilder(this);
  }

  /**
   * Given a SAD, the method builds a JWT and signs it using the configured key.
   * <p>
   * Note: Only RSA keys are supported.
   * </p>
   * 
   * @param sad
   *          the SAD to include in the JWT
   * @return a signed JWT (encoded)
   * @throws IOException
   *           for JSON processing errors
   * @throws SignatureException
   *           for JWT signature errors
   * @see SADBuilder#buildJwt()
   */
  public String createJwt(final SAD sad) throws IOException, SignatureException {

    final String encodedSad = Base64.getUrlEncoder().withoutPadding().encodeToString(sad.toJsonBytes());

    // Create JWT and sign ...
    //
    try {
      final JWSHeader header = 
          new JWSHeader.Builder(new JWSAlgorithm(this.jwtSigningAlgorithm)).type(JOSEObjectType.JWT).build();
      final JWSObject signedJwt = new JWSObject(header, new Payload(new Base64URL(encodedSad)));
      signedJwt.sign(new RSASSASigner(this.signingCredential.getPrivateKey()));

      return signedJwt.serialize();
    }
    catch (final JOSEException e) {
      throw new SignatureException("Failed to sign JWT", e);
    }
  }

  /**
   * Assigns the validity time for a SAD object (in seconds). The default is {@link #DEFAULT_VALIDITY_TIME}.
   * 
   * @param seconds
   *          validity time in seconds
   */
  public void setValidityTime(final int seconds) {
    if (seconds < 1) {
      throw new IllegalArgumentException("seconds must be a positive integer");
    }
    this.validityTime = seconds;
  }

  /**
   * Assigns the attribute name for the attribute holding the user ID. Defaults to
   * {@link #DEFAULT_USER_ID_ATTRIBUTE_NAME}.
   * 
   * @param userIdAttributeName
   *          attribute name (URI)
   */
  public void setUserIdAttributeName(final String userIdAttributeName) {
    if (userIdAttributeName == null || userIdAttributeName.isEmpty()) {
      throw new IllegalArgumentException("userIdAttributeName must not be null or empty");
    }
    this.userIdAttributeName = userIdAttributeName;
  }

  /**
   * Assigns the size of generated JWT identifiers. The default is {@link #DEFAULT_JWT_ID_SIZE}.
   * 
   * @param jwtIdSize
   *          the size
   */
  public void setJwtIdSize(final int jwtIdSize) {
    if (jwtIdSize < 12) {
      throw new IllegalArgumentException("The jwtIdSize must be at least 12 characters");
    }
    this.jwtIdSize = jwtIdSize;
  }

  /**
   * Assigns the JWT signature algorithm. The default is {@link #DEFAULT_JWT_SIGNING_ALGORITHM}.
   * 
   * @param jwtSigningAlgorithm
   *          JWT algorithm name
   */
  public void setJwtSigningAlgorithm(final String jwtSigningAlgorithm) {
    this.jwtSigningAlgorithm = jwtSigningAlgorithm;
  }

  /**
   * A builder for creating a SAD and a signed SAD JWT.
   * 
   * @author Martin Lindström (martin@idsec.se)
   */
  public static class SADBuilder {

    /** Reference to the actual factory. */
    private final SADFactory sadFactory;

    /** The SAD object being built. */
    private SAD sad;

    /**
     * Constructor.
     * 
     * @param sadFactory
     *          the SAD factory
     */
    SADBuilder(final SADFactory sadFactory) {
      this.sadFactory = sadFactory;
      this.sad = new SAD();
      this.sad.setIssuer(sadFactory.idpEntityID);
      this.sad.setSeElnSadext(new SAD.Extension());
      this.sad.getSeElnSadext().setAttributeName(sadFactory.userIdAttributeName);
    }

    /**
     * Builds a SAD.
     * 
     * @return the SAD
     */
    public SAD buildSAD() {

      if (this.sad.getJwtId() == null) {
        this.sad.setJwtId(RandomStringUtils.random(this.sadFactory.jwtIdSize, true, true));
      }

      this.sad.setIssuedAt((int) (System.currentTimeMillis() / 1000));
      this.sad.setExpiry(this.sad.getIssuedAt() + this.sadFactory.validityTime);

      if (this.sad.getSeElnSadext().getVersion() == null) {
        this.sad.getSeElnSadext().setVersion(SADVersion.VERSION_10.toString());
      }

      return this.sad;
    }

    /**
     * Builds a SAD, creates a JWT, signs it and returns its serialization.
     * 
     * @return serialized JWT
     * @throws IOException
     *           for JSON processing errors
     * @throws SignatureException
     *           for signature errors
     */
    public String buildJwt() throws IOException, SignatureException {
      return this.sadFactory.createJwt(this.buildSAD());
    }

    /**
     * Assigns the attribute value of the signer's unique identifier attribute.
     * 
     * @param subject
     *          the user ID
     * @return the SAD builder
     */
    public SADBuilder subject(final String subject) {
      this.sad.setSubject(subject);
      return this;
    }

    /**
     * Assigns the entityID of the Signature Service which is the recipient of this SAD.
     * 
     * @param audience
     *          the entityID of the recipient
     * @return the SAD builder
     */
    public SADBuilder audience(final String audience) {
      this.sad.setAudience(audience);
      return this;
    }

    /**
     * Assigns the unique identifier of this JWT.
     * 
     * @param jwtId
     *          JWT ID
     * @return the SAD builder
     */
    public SADBuilder jwtId(final String jwtId) {
      this.sad.setJwtId(jwtId);
      return this;
    }

    /**
     * Assigns the version of the SAD claim.
     * 
     * @param version
     *          the version
     * @return the SAD builder
     */
    public SADBuilder version(final SADVersion version) {
      this.sad.getSeElnSadext().setVersion(version.toString());
      return this;
    }

    /**
     * Assigns the ID of the {@code SADRequest} message that requested this SAD.
     * 
     * @param irt
     *          ID of corresponding SADRequest
     * @return the SAD builder
     */
    public SADBuilder inResponseTo(final String irt) {
      this.sad.getSeElnSadext().setInResponseTo(irt);
      return this;
    }

    /**
     * Assigns the URI identifier of the level of assurance (LoA) used to authenticate the signer.
     * 
     * @param loa
     *          LoA URI
     * @return the SAD builder
     */
    public SADBuilder loa(final String loa) {
      this.sad.getSeElnSadext().setLoa(loa);
      return this;
    }

    /**
     * Assigns the ID of the Sign Request associated with this SAD.
     * 
     * @param requestID
     *          SignRequest ID
     * @return the SAD builder
     */
    public SADBuilder requestID(final String requestID) {
      this.sad.getSeElnSadext().setRequestID(requestID);
      return this;
    }

    /**
     * Assigns the number of documents to be signed in the associated sign request.
     * 
     * @param docs
     *          the number of documents to be signed
     * @return the SAD builder
     */
    public SADBuilder numberOfDocuments(final int docs) {
      this.sad.getSeElnSadext().setNumberOfDocuments(docs);
      return this;
    }

  }

}
