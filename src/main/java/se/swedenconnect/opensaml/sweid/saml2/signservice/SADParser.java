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
package se.swedenconnect.opensaml.sweid.saml2.signservice;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.xml.SAMLConstants;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.Attribute;
import org.opensaml.saml.saml2.core.AuthnRequest;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.IDPSSODescriptor;
import org.opensaml.security.credential.UsageType;
import org.opensaml.security.x509.X509Credential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.factories.DefaultJWSVerifierFactory;
import com.nimbusds.jose.proc.JWSVerifierFactory;
import com.nimbusds.jwt.SignedJWT;

import net.shibboleth.shared.resolver.ResolverException;
import se.swedenconnect.opensaml.saml2.attribute.AttributeUtils;
import se.swedenconnect.opensaml.saml2.metadata.EntityDescriptorUtils;
import se.swedenconnect.opensaml.saml2.metadata.provider.MetadataProvider;
import se.swedenconnect.opensaml.saml2.metadata.provider.StaticMetadataProvider;
import se.swedenconnect.opensaml.sweid.saml2.attribute.AttributeConstants;
import se.swedenconnect.opensaml.sweid.saml2.signservice.SADValidationException.ErrorCode;
import se.swedenconnect.opensaml.sweid.saml2.signservice.sap.SAD;
import se.swedenconnect.opensaml.sweid.saml2.signservice.sap.SADRequest;

/**
 * Class for parsing and validation of SAD JWT:s.
 *
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class SADParser {

  // Hidden constructor
  private SADParser() {
  }

  /**
   * Parses the supplied (encoded) JWT and returns the contained JWT.
   * <p>
   * <b>Note:</b> The parse method does not peform any validation. Use the {@link SADValidator} class for this purpose.
   * </p>
   *
   * @param sadJwt the signed JWT holding the SAD
   * @return the SAD object
   * @throws IOException for parsing errors
   */
  public static SAD parse(final String sadJwt) throws IOException {
    try {
      final SignedJWT signedJwt = SignedJWT.parse(sadJwt);
      final String payload = signedJwt.getPayload().toBase64URL().toString();
      return SAD.fromJson(new String(Base64.getUrlDecoder().decode(payload), Charset.forName("UTF-8")));
    }
    catch (ParseException e) {
      throw new IOException(e);
    }
  }

  /**
   * Returns a SAD validator initialized with a set of certificates that are to be used for JWT signature validation.
   * These certificates are the IdP signing certificates obtained from the IdP metadata entry.
   *
   * @param validationCertificates certificate(s) to be used when verifying the JWT signature
   * @return a SADValidator instance
   */
  public static SADValidator getValidator(final X509Certificate... validationCertificates) {
    return new SADValidator(validationCertificates);
  }

  /**
   * Returns a SAD validator initialized with a {@link MetadataProvider} instance. During JWT signature validation the
   * IdP signature certificate will be obtained from the IdP metadata entry held by the metadata provider.
   *
   * @param metadataProvider metadata provider
   * @return a SADValidator instance
   */
  public static SADValidator getValidator(final MetadataProvider metadataProvider) {
    return new SADValidator(metadataProvider);
  }

  /**
   * Returns a SAD validator initialized with the IdP {@link EntityDescriptor} (metadata) from which the IdP signing
   * key/certificate will be read (needed for JWT signature validation).
   *
   * @param idpMetadata the IdP metadata
   * @return a SADValidator instance
   */
  public static SADValidator getValidator(final EntityDescriptor idpMetadata) {
    return new SADValidator(idpMetadata);
  }

  /**
   * A validator for verifying the SAD JWT.
   *
   * @author Martin Lindström (martin@idsec.se)
   */
  public static class SADValidator {

    /** Default allowed clock skew. */
    public static final Duration DEFAULT_ALLOWED_CLOCK_SKEW = Duration.ofSeconds(15);

    /** Logger instance. */
    private Logger logger = LoggerFactory.getLogger(SADValidator.class);

    /** The certificate(s) to use when verifying the JWT signature. */
    private List<X509Certificate> validationCertificates;

    /** A provider for federation metadata (in which we later will locate the IdP signing keys). */
    private MetadataProvider metadataProvider;

    private static final JWSVerifierFactory verifierFactory = new DefaultJWSVerifierFactory();

    /** Allowed clock skew. */
    private Duration allowedClockSkew = DEFAULT_ALLOWED_CLOCK_SKEW;

    /**
     * Constructor initializing the validator with a set of certificates that are to be used for JWT signature
     * validation. These certificates are the IdP signing certificates obtained from the IdP metadata entry.
     *
     * @param certificates certificate(s) to be used when verifying the JWT signature
     */
    public SADValidator(final X509Certificate... certificates) {
      this.validationCertificates = Arrays.asList(certificates);
    }

    /**
     * Constructor creating a SAD validator initialized with a {@link MetadataProvider} instance. During JWT signature
     * validation the IdP signature certificate will be obtained from the IdP metadata entry held by the metadata
     * provider.
     *
     * @param metadataProvider metadata provider
     */
    public SADValidator(final MetadataProvider metadataProvider) {
      this.metadataProvider = metadataProvider;
    }

    /**
     * Creates a SAD validator initialized with the IdP {@link EntityDescriptor} (metadata) from which the IdP signing
     * key/certificate will be read (needed for JWT signature validation).
     *
     * @param idpMetadata the IdP metadata
     */
    public SADValidator(final EntityDescriptor idpMetadata) {
      try {
        this.metadataProvider = new StaticMetadataProvider(idpMetadata);
      }
      catch (MarshallingException e) {
        throw new SecurityException("Invalid IdP metadata", e);
      }
    }

    /**
     * A method that validates the SAD issued in an {@code Assertion} based on the {@code AuthnRequest} containing a
     * {@code SADRequest}.
     *
     * @param authnRequest the AuthnRequest holding the SADRequest
     * @param assertion the Assertion holding the sad attribute (as a encoded JWT)
     * @return a SAD object, or null if no SAD was requested (and issued)
     * @throws SADValidationException for SAD validation errors
     * @throws IllegalArgumentException if the supplied AuthnRequest does not contain a SADRequest extension, or is
     *           invalid by other means (e.g., missing LoA)
     * @see #validate(String, String, String, String, String, String, int, String)
     */
    public SAD validate(final AuthnRequest authnRequest, final Assertion assertion) throws SADValidationException,
        IllegalArgumentException {

      final long now = System.currentTimeMillis() / 1000;

      // First locate the SADRequest extension.
      //
      final SADRequest sadRequest = authnRequest.getExtensions() != null
          ? authnRequest.getExtensions()
              .getUnknownXMLObjects()
              .stream()
              .filter(SADRequest.class::isInstance)
              .map(SADRequest.class::cast)
              .findFirst()
              .orElse(null)
          : null;

      if (sadRequest == null) {
        final String msg = String.format("AuthnRequest '%s' does not contain a SADRequest", authnRequest.getID());
        logger.info(msg);
        throw new IllegalArgumentException(msg);
      }

      // Next, locate the SAD attribute.
      //
      if (assertion.getAttributeStatements().isEmpty()) {
        final String msg =
            String.format("Assertion '%s' does not contain any attributes (and thus no SAD)", assertion.getID());
        logger.info(msg);
        throw new SADValidationException(ErrorCode.NO_SAD_ATTRIBUTE, msg);
      }
      final List<Attribute> attributes = assertion.getAttributeStatements().get(0).getAttributes();
      final Attribute sadAttribute = AttributeUtils.getAttribute(AttributeConstants.ATTRIBUTE_NAME_SAD, attributes);
      if (sadAttribute == null) {
        final String msg = String.format("Assertion '%s' does not contain a SAD attribute", assertion.getID());
        logger.info(msg);
        throw new SADValidationException(ErrorCode.NO_SAD_ATTRIBUTE, msg);
      }

      // Parse the JWT and SAD.
      //
      SignedJWT signedJwt;
      SAD sad;
      try {
        signedJwt = SignedJWT.parse(AttributeUtils.getAttributeStringValue(sadAttribute));
        final String payload = signedJwt.getPayload().toBase64URL().toString();
        sad = SAD.fromJson(new String(Base64.getUrlDecoder().decode(payload), Charset.forName("UTF-8")));
      }
      catch (ParseException | IOException e) {
        throw new SADValidationException(ErrorCode.JWT_PARSE_ERROR, "Failed to parse SAD JWT", e);
      }

      // The SAD contains an attribute name reference for the attribute that the issuing IdP
      // used as the user subject. Let's find that attribute value ...
      //
      if (sad.getSeElnSadext() == null) {
        final String msg = "seElnSadext extension claims are missing from SAD";
        logger.info(msg);
        throw new SADValidationException(ErrorCode.BAD_SAD_FORMAT, msg);
      }
      if (sad.getSeElnSadext().getAttributeName() == null) {
        final String msg = "SAD does not contain the attribute name (attr) for the subject";
        logger.info(msg);
        throw new SADValidationException(ErrorCode.BAD_SAD_FORMAT, msg);
      }
      final Attribute subjectAttribute =
          AttributeUtils.getAttribute(sad.getSeElnSadext().getAttributeName(), attributes);
      if (subjectAttribute == null) {
        final String msg = String.format(
            "Assertion '%s' does not contain a '%s' attribute - this is listed as the subject attribute in the SAD",
            assertion.getID(), sad.getSeElnSadext().getAttributeName());
        logger.info(msg);
        throw new SADValidationException(ErrorCode.MISSING_SUBJECT_ATTRIBUTE, msg);
      }

      // Next, get hold of the AuthnContextClassRef holding the LoA.
      // We want to compare that with the 'loa' from the SAD.
      //
      final String loa = getLoa(assertion);
      if (loa == null) {
        String msg = String.format("Assertion '%s' does not contain a LoA URI", assertion.getID());
        logger.error(msg);
        throw new IllegalArgumentException(msg);
      }

      if (sadRequest.getDocCount() == null) {
        throw new IllegalArgumentException("Bad SADRequest - missing DocCount");
      }

      // Now, validate!
      //
      return this.validate(signedJwt, sad, now,
          assertion.getIssuer().getValue(), /* The IdP entityID = issuer of the SAD. */
          sadRequest.getRequesterID(), /* The requester ID = expected recipient ID of the SAD. */
          AttributeUtils.getAttributeStringValue(subjectAttribute), /* The expected subject name. */
          loa, /* The expected LoA. */
          sadRequest.getID(), /* The expected in-response-to ID. */
          sadRequest.getDocCount().intValue(), /* The expected number of documents indicated in the SAD. */
          sadRequest.getSignRequestID()); /* The SignRequest ID. */
    }

    /**
     * Validates a SAD based on expected data. If the {@code AuthnRequest} and issued {@code Assertion} is available,
     * the method {@link #validate(AuthnRequest, Assertion)} is a better option.
     *
     * <p>
     * Note: It is assumed that the supplied {@code expectedSubject} parameter is a attribute value read from the
     * assertion having the attribute name indicated in the 'attr' field of the SAD. If this attribute name is not known
     * in advance, the SAD needs to be parsed ({@link SADParser#parse(String)}) so that the 'attr' field can be read,
     * and the correct attribute value be located from the assertion.
     * </p>
     *
     * @param sadJwt the encoded SAD JWT (found in the sad attribute of a received assertion)
     * @param idpEntityID the entityID of the issuing IdP (the issuer of the received assertion holding the sad
     *          attribute)
     * @param expectedRecipientEntityID the entityID of the recipient (the signature service SP that issued the
     *          SADRequest)
     * @param expectedSubject the expected subject name (user ID). See note above
     * @param expectedLoa the expected level of assurance to be found in the SAD (should be the LoA found in the
     *          assertion)
     * @param sadRequestID the ID of the SADRequest extension that was sent to the IdP
     * @param expectedNoDocs expected number of documents (from the DocCount element of the SADRequest)
     * @param signRequestID ID for the SignRequest that was included in the SADRequest
     * @return a SAD object
     * @throws SADValidationException for validation errors
     */
    public SAD validate(final String sadJwt, final String idpEntityID, final String expectedRecipientEntityID,
        final String expectedSubject, final String expectedLoa, final String sadRequestID, final int expectedNoDocs,
        final String signRequestID) throws SADValidationException {

      final long now = System.currentTimeMillis() / 1000;

      try {
        // Parse the JWT
        //
        final SignedJWT signedJwt = SignedJWT.parse(sadJwt);

        // Next, parse the SAD.
        //
        final String payload = signedJwt.getPayload().toBase64URL().toString();
        final SAD sad = SAD.fromJson(new String(Base64.getUrlDecoder().decode(payload), Charset.forName("UTF-8")));

        return this.validate(signedJwt, sad, now, idpEntityID, expectedRecipientEntityID, expectedSubject, expectedLoa,
            sadRequestID, expectedNoDocs, signRequestID);
      }
      catch (ParseException | IOException e) {
        throw new SADValidationException(ErrorCode.JWT_PARSE_ERROR, "Failed to parse SAD JWT", e);
      }
    }

    /**
     * Validates the supplied SAD JWT.
     *
     * @param signedJwt the SAD JWT
     * @param sad the SAD (parsed for pre-checks)
     * @param now the current time (seconds since 1970-01-01)
     * @param idpEntityID the entityID of the issuing IdP (the issuer of the received assertion holding the sad
     *          attribute)
     * @param expectedRecipientEntityID the entityID of the recipient (the signature service SP that issued the
     *          SADRequest)
     * @param expectedSubject the expected subject name (user ID). See note above
     * @param expectedLoa the expected level of assurance to be found in the SAD (should be the LoA found in the
     *          assertion)
     * @param sadRequestID the ID of the SADRequest extension that was sent to the IdP
     * @param expectedNoDocs expected number of documents (from the DocCount element of the SADRequest
     * @param signRequestID ID for the SignRequest that was included in the SADRequest
     * @return a SAD object
     * @throws SADValidationException for validation errors
     */
    private SAD validate(final SignedJWT signedJwt, final SAD sad, final long now, final String idpEntityID,
        final String expectedRecipientEntityID, final String expectedSubject, final String expectedLoa,
        final String sadRequestID, final int expectedNoDocs, final String signRequestID)
        throws SADValidationException {

      // Verify the JWT signature
      //
      this.verifyJwtSignature(signedJwt, idpEntityID);

      // Ensure that we have a JWT ID.
      //
      if (sad.getJwtId() == null || sad.getJwtId().isEmpty()) {
        final String msg = "Invalid SAD JWT - jti is missing";
        logger.info(msg);
        throw new SADValidationException(ErrorCode.BAD_SAD_FORMAT, msg);
      }

      // Make sure that the SAD issuer is the same as the IdP that issued the Assertion
      // that contained the SAD.
      //
      if (!Objects.equals(idpEntityID, sad.getIssuer())) {
        final String msg = String.format("SAD contains issuer '%s' - expected '%s'", sad.getIssuer(), idpEntityID);
        logger.info(msg);
        throw new SADValidationException(ErrorCode.VALIDATION_BAD_ISSUER, msg);
      }

      // Make sure that this SAD was issued for "me".
      //
      if (!Objects.equals(expectedRecipientEntityID, sad.getAudience())) {
        final String msg =
            String.format("SAD contains audience '%s' - expected '%s'", sad.getAudience(), expectedRecipientEntityID);
        logger.info(msg);
        throw new SADValidationException(ErrorCode.VALIDATION_BAD_AUDIENCE, msg);
      }

      // Make sure that the SAD is still valid.
      //
      if (sad.getExpiry() == null || sad.getIssuedAt() == null) {
        final String msg = "SAD is missing 'exp' and/or 'iat' - Invalid SAD";
        logger.info(msg);
        throw new SADValidationException(ErrorCode.BAD_SAD_FORMAT, msg);
      }

      if (sad.getExpiry() < now - this.allowedClockSkew.getSeconds()) {
        final String msg = String.format("SAD has expired - expiration: '%s', current time: '%s'",
            sad.getExpiryDateTime(), Instant.ofEpochSecond(now));
        logger.info(msg);
        throw new SADValidationException(ErrorCode.SAD_EXPIRED, msg);
      }

      if (sad.getIssuedAt() > now + this.allowedClockSkew.getSeconds()) {
        final String msg = String.format("SAD is not yet valid - issue-time: '%s', current time: '%s'",
            sad.getIssuedAtDateTime(), Instant.ofEpochSecond(now));
        logger.info(msg);
        throw new SADValidationException(ErrorCode.BAD_SAD_FORMAT, msg);
      }

      // Assert that we received a SAD for the expected subject ID (userID).
      //
      if (!Objects.equals(expectedSubject, sad.getSubject())) {
        final String msg =
            String.format("SAD contains subject '%s' - expected '%s'", sad.getSubject(), expectedSubject);
        logger.info(msg);
        throw new SADValidationException(ErrorCode.VALIDATION_BAD_SUBJECT, msg);
      }

      // Ensure SAD format.
      //
      if (sad.getSeElnSadext() == null) {
        final String msg = "seElnSadext extension claims are missing from SAD";
        logger.info(msg);
        throw new SADValidationException(ErrorCode.BAD_SAD_FORMAT, msg);
      }

      // Assert that the SAD was issued based on the given SAD request.
      //
      if (!Objects.equals(sadRequestID, sad.getSeElnSadext().getInResponseTo())) {
        final String msg =
            String.format("SAD contains in-response-to (irt) '%s' - expected SAD to belong to SADRequest with ID '%s'",
                sad.getSeElnSadext().getInResponseTo(), sadRequestID);
        logger.info(msg);
        throw new SADValidationException(ErrorCode.VALIDATION_BAD_IRT, msg);
      }

      // Assert that the SAD was issued under the LoA that we expects (should be the same as found in the assertion).
      //
      if (!Objects.equals(expectedLoa, sad.getSeElnSadext().getLoa())) {
        final String msg =
            String.format("SAD contains LoA '%s' - expected '%s'", sad.getSeElnSadext().getLoa(), expectedLoa);
        logger.info(msg);
        throw new SADValidationException(ErrorCode.VALIDATION_BAD_LOA, msg);
      }

      // Assert the the number of documents indicated in the SAD corresponds with the number given in the SADRequest.
      //
      if (!Objects.equals(Integer.valueOf(expectedNoDocs), sad.getSeElnSadext().getNumberOfDocuments())) {
        final String msg = String.format("SAD indicated '%s' number of documents - expected '%d'",
            sad.getSeElnSadext().getNumberOfDocuments(), expectedNoDocs);
        logger.info(msg);
        throw new SADValidationException(ErrorCode.VALIDATION_BAD_DOCS, msg);
      }

      // Assert that the given SignRequest ID corresponds with the SAD reqid.
      //
      if (!Objects.equals(signRequestID, sad.getSeElnSadext().getRequestID())) {
        final String msg = String.format("SAD contains SignRequest ID (reqid) '%s' - expected '%s'",
            sad.getSeElnSadext().getRequestID(), signRequestID);
        logger.info(msg);
        throw new SADValidationException(ErrorCode.VALIDATION_BAD_SIGNREQUESTID, msg);
      }

      logger.debug("SAD with ID '{}' was successfully validated", sad.getJwtId());
      return sad;
    }

    /**
     * Verifies the signature on the supplied SAD JWT.
     *
     * @param sadJwt the SAD JWT
     * @param idpEntityID the entityID of the IdP that signed the JWT
     * @throws SADValidationException for signature validation errors
     */
    public void verifyJwtSignature(final String sadJwt, final String idpEntityID) throws SADValidationException {
      try {
        this.verifyJwtSignature(SignedJWT.parse(sadJwt), idpEntityID);
      }
      catch (ParseException e) {
        throw new SADValidationException(ErrorCode.JWT_PARSE_ERROR, "Failed to parse SAD JWT", e);
      }
    }

    /**
     * Verifies the signature on the supplied SAD JWT.
     *
     * @param sadJwt the SAD JWT
     * @param idpEntityID the entityID of the IdP that signed the JWT
     * @throws SADValidationException for signature validation errors
     */
    private void verifyJwtSignature(final SignedJWT signedJwt, final String idpEntityID) throws SADValidationException {
      try {
        // Verify the JWT signature
        //
        final List<X509Certificate> idpCerts = this.getValidationCertificates(idpEntityID);
        if (idpCerts.isEmpty()) {
          throw new SADValidationException(ErrorCode.SIGNATURE_VALIDATION_ERROR,
              "No suitable IdP signature certificate was found - can not verify SAD JWT signature");
        }
        logger.debug("Verifying SAD JWT signature. Will try {} IdP key(s) ...", idpCerts.size());

        boolean verificationSuccess = false;
        for (X509Certificate idpCert : idpCerts) {
          try {
            final JWSVerifier verifier =
                verifierFactory.createJWSVerifier(signedJwt.getHeader(), idpCert.getPublicKey());
            if (verifier.verify(signedJwt.getHeader(), signedJwt.getSigningInput(), signedJwt.getSignature())) {
              logger.debug("SAD JWT signature successfully verified");
              verificationSuccess = true;
              break;
            }
          }
          catch (JOSEException e) {
            logger.debug("Failed to perform signature validation of SAD JWT - {}", e.getMessage());
            logger.trace("", e);
          }
        }
        if (!verificationSuccess) {
          throw new SADValidationException(ErrorCode.SIGNATURE_VALIDATION_ERROR,
              "Signature on SAD JWT could not be validated using any of the IdP certificates found");
        }
      }
      catch (ResolverException e) {
        throw new SADValidationException(ErrorCode.SIGNATURE_VALIDATION_ERROR, "Failed to find validation certificate",
            e);
      }
    }

    /**
     * Returns a list of possible IdP validation certificates to use when verifying the SAD signature.
     *
     * @param idpEntityID the IdP entityID
     * @return a list of certificates
     * @throws ResolverException for metadata resolver errors
     */
    private List<X509Certificate> getValidationCertificates(final String idpEntityID) throws ResolverException {
      if (this.validationCertificates != null && !this.validationCertificates.isEmpty()) {
        return this.validationCertificates;
      }
      else if (this.metadataProvider != null) {
        final IDPSSODescriptor metadata = Optional.ofNullable(this.metadataProvider.getEntityDescriptor(idpEntityID))
            .map(e -> e.getIDPSSODescriptor(SAMLConstants.SAML20P_NS)).orElse(null);

        if (metadata == null) {
          logger.warn("No metadata found for IdP '{}' - cannot find key to use when verifying SAD JWT signature",
              idpEntityID);
          return Collections.emptyList();
        }
        List<X509Credential> creds = EntityDescriptorUtils.getMetadataCertificates(metadata, UsageType.SIGNING);
        return creds.stream().map(X509Credential::getEntityCertificate).collect(Collectors.toList());
      }
      else {
        return Collections.emptyList();
      }
    }

    /**
     * Returns the LoA (level of assurance) URI from the supplied assertion.
     *
     * @param assertion the assertion
     * @return the LoA URI, or null
     */
    private static String getLoa(final Assertion assertion) {
      try {
        return assertion.getAuthnStatements().get(0).getAuthnContext().getAuthnContextClassRef().getURI();
      }
      catch (Exception e) {
        return null;
      }
    }

    /**
     * Assigned the allowed clock skew. The default is {@link #DEFAULT_ALLOWED_CLOCK_SKEW}.
     *
     * @param allowedClockSkew allowed clock skew
     */
    public void setAllowedClockSkew(final Duration allowedClockSkew) {
      if (allowedClockSkew != null) {
        this.allowedClockSkew = allowedClockSkew;
      }
    }

  }

}
