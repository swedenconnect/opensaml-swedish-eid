/*
 * Copyright 2016-2021 Sweden Connect
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

import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.junit.Assert;
import org.junit.Test;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.AttributeStatement;
import org.opensaml.saml.saml2.core.AuthnContext;
import org.opensaml.saml.saml2.core.AuthnContextClassRef;
import org.opensaml.saml.saml2.core.AuthnRequest;
import org.opensaml.saml.saml2.core.AuthnStatement;
import org.opensaml.saml.saml2.core.Extensions;
import org.opensaml.saml.saml2.core.Issuer;
import org.opensaml.security.x509.impl.KeyStoreX509CredentialAdapter;
import org.springframework.core.io.ClassPathResource;

import se.swedenconnect.opensaml.saml2.attribute.AttributeBuilder;
import se.swedenconnect.opensaml.saml2.core.build.AuthnRequestBuilder;
import se.swedenconnect.opensaml.sweid.OpenSAMLTestBase;
import se.swedenconnect.opensaml.sweid.saml2.attribute.AttributeConstants;
import se.swedenconnect.opensaml.sweid.saml2.authn.LevelOfAssuranceUris;
import se.swedenconnect.opensaml.sweid.saml2.signservice.SADParser.SADValidator;
import se.swedenconnect.opensaml.sweid.saml2.signservice.SADValidationException.ErrorCode;
import se.swedenconnect.opensaml.sweid.saml2.signservice.build.SADRequestBuilder;
import se.swedenconnect.opensaml.sweid.saml2.signservice.sap.SAD;
import se.swedenconnect.opensaml.sweid.saml2.signservice.sap.SADRequest;
import se.swedenconnect.opensaml.sweid.saml2.signservice.sap.SADVersion;

/**
 * Test cases for SAD parsing and validation.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class SADParserTest extends OpenSAMLTestBase {

  private static final String IDP_ENTITYID = "https://idp.svelegtest.se/idp";
  private static final String SIGNSERVICE_ENTITYID = "http://www.example.com/sigservice";
  private static final String USER_ID = "196302052383";
  private static final String SAD_REQUEST_ID = "_a74a068d0548a919e503e5f9ef901851";
  private static final String SIGN_REQUEST_ID = "f6e7d061a23293b0053dc7b038a04dad";

  /** Factory for building SAD:s. */
  private SADFactory sadFactory;

  /** Certificate for verifying SAD signature. */
  private X509Certificate validationCertificate;

  /** Validation certificate that won't work - for error tests. */
  private X509Certificate wrongValidationCertificate;

  /**
   * Constructor setting up the tests
   * 
   * @throws Exception
   *           for errors
   */
  public SADParserTest() throws Exception {
    KeyStore keyStore = loadKeyStore(new ClassPathResource("Litsec_SAML_Signing.jks").getInputStream(), "secret", "JKS");
    KeyStoreX509CredentialAdapter credential = new KeyStoreX509CredentialAdapter(keyStore, "litsec_saml_signing", "secret".toCharArray());

    this.sadFactory = new SADFactory(IDP_ENTITYID, credential);
    this.validationCertificate = credential.getEntityCertificate();

    this.wrongValidationCertificate = decodeCertificate(new ClassPathResource("Litsec_SAML_Encryption.crt").getInputStream());
  }

  /**
   * Tests the SAD parsing.
   * 
   * @throws Exception
   *           for errors
   */
  @Test
  public void testParse() throws Exception {

    SAD sad = this.getTestSAD();
    String jwt = this.sadFactory.createJwt(sad);

    SAD sad2 = SADParser.parse(jwt);

    Assert.assertEquals(sad, sad2);
  }

  /**
   * Tests validation of SAD by supplying expected parameters.
   * 
   * @throws Exception
   *           for errors
   */
  @Test
  public void testValidatePars() throws Exception {

    SAD sad = this.getTestSAD();
    String jwt = this.sadFactory.createJwt(sad);

    SADValidator validator = SADParser.getValidator(this.validationCertificate);

    validator.validate(jwt, IDP_ENTITYID, SIGNSERVICE_ENTITYID, USER_ID, LevelOfAssuranceUris.AUTHN_CONTEXT_URI_LOA3,
      SAD_REQUEST_ID, 1, SIGN_REQUEST_ID);
  }

  /**
   * Tests validation of SAD by supplying an {@code AuthnRequest}.
   * 
   * @throws Exception
   *           for errors
   */
  @Test
  public void testValidateAuthnRequest() throws Exception {

    SAD sad = this.getTestSAD();
    String jwt = this.sadFactory.createJwt(sad);

    SADRequest sadRequest = SADRequestBuilder.builder()
      .id(SAD_REQUEST_ID)
      .requesterID(SIGNSERVICE_ENTITYID)
      .signRequestID(SIGN_REQUEST_ID)
      .docCount(1)
      .requestedVersion(SADVersion.VERSION_10)
      .build();
    AuthnRequest authnRequest = buildAuthnRequest(sadRequest);

    Assertion assertion = buildAssertion(IDP_ENTITYID, LevelOfAssuranceUris.AUTHN_CONTEXT_URI_LOA3); 
    addAttribute(assertion, AttributeConstants.ATTRIBUTE_NAME_PERSONAL_IDENTITY_NUMBER, USER_ID);
    addAttribute(assertion, AttributeConstants.ATTRIBUTE_NAME_SAD, jwt);

    SADValidator validator = SADParser.getValidator(this.validationCertificate);
    validator.validate(authnRequest, assertion);
  }

  /**
   * A SADRequest needs to be present in an AuthnRequest.
   * 
   * @throws Exception
   *           for errors
   */
  @Test(expected = IllegalArgumentException.class)
  public void testMissingSADRequest() throws Exception {
    SAD sad = this.getTestSAD();
    String jwt = this.sadFactory.createJwt(sad);

    AuthnRequest authnRequest = buildAuthnRequest(null);

    Assertion assertion = buildAssertion(IDP_ENTITYID, LevelOfAssuranceUris.AUTHN_CONTEXT_URI_LOA3); 
    addAttribute(assertion, AttributeConstants.ATTRIBUTE_NAME_PERSONAL_IDENTITY_NUMBER, USER_ID);
    addAttribute(assertion, AttributeConstants.ATTRIBUTE_NAME_SAD, jwt);

    SADValidator validator = SADParser.getValidator(this.validationCertificate);
    validator.validate(authnRequest, assertion);
  }

  /**
   * The sad attribute need to be present in the assertion.
   * 
   * @throws Exception
   *           for errors
   */
  @Test
  public void testMissingSADAttribute() throws Exception {

    SADRequest sadRequest = SADRequestBuilder.builder()
      .id(SAD_REQUEST_ID)
      .requesterID(SIGNSERVICE_ENTITYID)
      .signRequestID(SIGN_REQUEST_ID)
      .docCount(1)
      .requestedVersion(SADVersion.VERSION_10)
      .build();

    AuthnRequest authnRequest = buildAuthnRequest(sadRequest);

    Assertion assertion = buildAssertion(IDP_ENTITYID, LevelOfAssuranceUris.AUTHN_CONTEXT_URI_LOA3); 
    addAttribute(assertion, AttributeConstants.ATTRIBUTE_NAME_PERSONAL_IDENTITY_NUMBER, USER_ID);

    SADValidator validator = SADParser.getValidator(this.validationCertificate);
    try {
      validator.validate(authnRequest, assertion);
      Assert.fail("Expected SADValidationException - NO_SAD_ATTRIBUTE");
    }
    catch (SADValidationException e) {
      Assert.assertEquals("Expected error code NO_SAD_ATTRIBUTE", ErrorCode.NO_SAD_ATTRIBUTE, e.getErrorCode());
    }
  }

  /**
   * Test parse errors of the SAD JWT.
   * 
   * @throws Exception
   *           for errors
   */
  @Test
  public void testBadJwt() throws Exception {
    SADRequest sadRequest = SADRequestBuilder.builder()
      .id(SAD_REQUEST_ID)
      .requesterID(SIGNSERVICE_ENTITYID)
      .signRequestID(SIGN_REQUEST_ID)
      .docCount(1)
      .requestedVersion(SADVersion.VERSION_10)
      .build();

    AuthnRequest authnRequest = buildAuthnRequest(sadRequest);

    Assertion assertion = buildAssertion(IDP_ENTITYID, LevelOfAssuranceUris.AUTHN_CONTEXT_URI_LOA3);
    addAttribute(assertion, AttributeConstants.ATTRIBUTE_NAME_PERSONAL_IDENTITY_NUMBER, USER_ID);
    addAttribute(assertion, AttributeConstants.ATTRIBUTE_NAME_SAD, "JWT_THAT_IS_NOT_A_JWT");

    SADValidator validator = SADParser.getValidator(this.validationCertificate);
    try {
      validator.validate(authnRequest, assertion);
      Assert.fail("Expected SADValidationException - JWT_PARSE_ERROR");
    }
    catch (SADValidationException e) {
      Assert.assertEquals("Expected error code JWT_PARSE_ERROR", ErrorCode.JWT_PARSE_ERROR, e.getErrorCode());
    }

    try {
      validator.validate("JWT_THAT_IS_NOT_A_JWT", IDP_ENTITYID, SIGNSERVICE_ENTITYID, USER_ID,
        LevelOfAssuranceUris.AUTHN_CONTEXT_URI_LOA3,
        SAD_REQUEST_ID, 1, SIGN_REQUEST_ID);
      Assert.fail("Expected SADValidationException - JWT_PARSE_ERROR");
    }
    catch (SADValidationException e) {
      Assert.assertEquals("Expected error code JWT_PARSE_ERROR", ErrorCode.JWT_PARSE_ERROR, e.getErrorCode());
    }

  }

  /**
   * Tests mismatch between what the IdP thinks is the user ID and what the SP thinks.
   * 
   * @throws Exception
   */
  @Test
  public void testMissingAttribute() throws Exception {

    SAD sad = this.getTestSAD();
    sad.getSeElnSadext().setAttributeName(AttributeConstants.ATTRIBUTE_NAME_PRID);
    String jwt = this.sadFactory.createJwt(sad);

    SADRequest sadRequest = SADRequestBuilder.builder()
      .id(SAD_REQUEST_ID)
      .requesterID(SIGNSERVICE_ENTITYID)
      .signRequestID(SIGN_REQUEST_ID)
      .docCount(1)
      .requestedVersion(SADVersion.VERSION_10)
      .build();
    AuthnRequest authnRequest = buildAuthnRequest(sadRequest);

    Assertion assertion = buildAssertion(IDP_ENTITYID, LevelOfAssuranceUris.AUTHN_CONTEXT_URI_LOA3);
    addAttribute(assertion, AttributeConstants.ATTRIBUTE_NAME_PERSONAL_IDENTITY_NUMBER, USER_ID);
    addAttribute(assertion, AttributeConstants.ATTRIBUTE_NAME_SAD, jwt);

    SADValidator validator = SADParser.getValidator(this.validationCertificate);
    try {
      validator.validate(authnRequest, assertion);
      Assert.fail("Expected SADValidationException - MISSING_SUBJECT_ATTRIBUTE");
    }
    catch (SADValidationException e) {
      Assert.assertEquals("Expected error code MISSING_SUBJECT_ATTRIBUTE", ErrorCode.MISSING_SUBJECT_ATTRIBUTE, e.getErrorCode());
    }
  }

  /**
   * Test that we require LoA in the assertion.
   * 
   * @throws Exception
   *           for errors
   */
  @Test(expected = IllegalArgumentException.class)
  public void testMissingLoa() throws Exception {
    SAD sad = this.getTestSAD();
    String jwt = this.sadFactory.createJwt(sad);

    SADRequest sadRequest = SADRequestBuilder.builder()
      .id(SAD_REQUEST_ID)
      .requesterID(SIGNSERVICE_ENTITYID)
      .signRequestID(SIGN_REQUEST_ID)
      .docCount(1)
      .requestedVersion(SADVersion.VERSION_10)
      .build();
    AuthnRequest authnRequest = buildAuthnRequest(sadRequest);

    Assertion assertion = buildAssertion(IDP_ENTITYID, LevelOfAssuranceUris.AUTHN_CONTEXT_URI_LOA3);
    addAttribute(assertion, AttributeConstants.ATTRIBUTE_NAME_PERSONAL_IDENTITY_NUMBER, USER_ID);
    addAttribute(assertion, AttributeConstants.ATTRIBUTE_NAME_SAD, jwt);

    assertion.getAuthnStatements().clear();

    SADValidator validator = SADParser.getValidator(this.validationCertificate);
    validator.validate(authnRequest, assertion);
  }

  /**
   * Supply wrong certificate as validation cert - should result in a bad signature error.
   * 
   * @throws Exception
   *           for errors
   */
  @Test
  public void testValidateBadSignature() throws Exception {

    SAD sad = this.getTestSAD();
    String jwt = this.sadFactory.createJwt(sad);

    SADValidator validator = SADParser.getValidator(this.wrongValidationCertificate);
    try {
      validator.verifyJwtSignature(jwt, IDP_ENTITYID);
      Assert.fail("Expected SADValidationException - SIGNATURE_VALIDATION_ERROR");
    }
    catch (SADValidationException e) {
      Assert.assertEquals("Expected error code SIGNATURE_VALIDATION_ERROR", ErrorCode.SIGNATURE_VALIDATION_ERROR, e.getErrorCode());
    }

    try {
      validator.validate(jwt, IDP_ENTITYID, SIGNSERVICE_ENTITYID, USER_ID, LevelOfAssuranceUris.AUTHN_CONTEXT_URI_LOA3,
        SAD_REQUEST_ID, 1, SIGN_REQUEST_ID);
      Assert.fail("Expected SADValidationException - SIGNATURE_VALIDATION_ERROR");
    }
    catch (SADValidationException e) {
      Assert.assertEquals("Expected error code SIGNATURE_VALIDATION_ERROR", ErrorCode.SIGNATURE_VALIDATION_ERROR, e.getErrorCode());
    }
  }

  /**
   * Test exprired SAD.
   * 
   * @throws Exception
   *           for errors
   */
  @Test
  public void testExpiredSAD() throws Exception {

    SAD sad = this.getTestSAD();
    sad.setIssuedAt(LocalDateTime.of(2017, 1, 1, 13, 10).toInstant(ZoneOffset.UTC));
    sad.setExpiry(LocalDateTime.of(2017, 1, 1, 13, 11).toInstant(ZoneOffset.UTC));
    String jwt = this.sadFactory.createJwt(sad);

    SADValidator validator = SADParser.getValidator(this.validationCertificate);
    try {
      validator.validate(jwt, IDP_ENTITYID, SIGNSERVICE_ENTITYID, USER_ID, LevelOfAssuranceUris.AUTHN_CONTEXT_URI_LOA3,
        SAD_REQUEST_ID, 1, SIGN_REQUEST_ID);
      Assert.fail("Expected SADValidationException - SAD_EXPIRED");
    }
    catch (SADValidationException e) {
      Assert.assertEquals("Expected error code SAD_EXPIRED", ErrorCode.SAD_EXPIRED, e.getErrorCode());
    }
  }

  /**
   * Verifies that we require the JWT ID.
   * 
   * @throws Exception
   *           for errors
   */
  @Test
  public void testMissingJwtId() throws Exception {

    SAD sad = this.getTestSAD();
    sad.setJwtId(null);
    String jwt = this.sadFactory.createJwt(sad);

    SADValidator validator = SADParser.getValidator(this.validationCertificate);

    try {
      validator.validate(jwt, IDP_ENTITYID, SIGNSERVICE_ENTITYID, USER_ID, LevelOfAssuranceUris.AUTHN_CONTEXT_URI_LOA3,
        SAD_REQUEST_ID, 1, SIGN_REQUEST_ID);
      Assert.fail("Expected SADValidationException - BAD_SAD_FORMAT");
    }
    catch (SADValidationException e) {
      Assert.assertEquals("Expected error code BAD_SAD_FORMAT", ErrorCode.BAD_SAD_FORMAT, e.getErrorCode());
    }
  }
  
  /**
   * Verifies that the issuer is correct.
   * 
   * @throws Exception
   *           for errors
   */
  @Test
  public void testMismatchingIssuer() throws Exception {

    SAD sad = this.getTestSAD();
    String jwt = this.sadFactory.createJwt(sad);

    SADRequest sadRequest = SADRequestBuilder.builder()
      .id(SAD_REQUEST_ID)
      .requesterID(SIGNSERVICE_ENTITYID)
      .signRequestID(SIGN_REQUEST_ID)
      .docCount(1)
      .requestedVersion(SADVersion.VERSION_10)
      .build();
    AuthnRequest authnRequest = buildAuthnRequest(sadRequest);

    Assertion assertion = buildAssertion("http://www.anotheridp.se", LevelOfAssuranceUris.AUTHN_CONTEXT_URI_LOA3);
    addAttribute(assertion, AttributeConstants.ATTRIBUTE_NAME_PERSONAL_IDENTITY_NUMBER, USER_ID);
    addAttribute(assertion, AttributeConstants.ATTRIBUTE_NAME_SAD, jwt);

    SADValidator validator = SADParser.getValidator(this.validationCertificate);
    
    try {
      validator.validate(authnRequest, assertion);
      Assert.fail("Expected SADValidationException - VALIDATION_BAD_ISSUER");
    }
    catch (SADValidationException e) {
      Assert.assertEquals("Expected error code VALIDATION_BAD_ISSUER", ErrorCode.VALIDATION_BAD_ISSUER, e.getErrorCode());
    }
  }
  
  /**
   * Verifies that the audience is correct.
   * 
   * @throws Exception
   *           for errors
   */
  @Test
  public void testMismatchingAudience() throws Exception {

    SAD sad = this.getTestSAD();
    String jwt = this.sadFactory.createJwt(sad);

    SADRequest sadRequest = SADRequestBuilder.builder()
      .id(SAD_REQUEST_ID)
      .requesterID("http://another-sp.com")
      .signRequestID(SIGN_REQUEST_ID)
      .docCount(1)
      .requestedVersion(SADVersion.VERSION_10)
      .build();
    AuthnRequest authnRequest = buildAuthnRequest(sadRequest);

    Assertion assertion = buildAssertion(IDP_ENTITYID, LevelOfAssuranceUris.AUTHN_CONTEXT_URI_LOA3);
    addAttribute(assertion, AttributeConstants.ATTRIBUTE_NAME_PERSONAL_IDENTITY_NUMBER, USER_ID);
    addAttribute(assertion, AttributeConstants.ATTRIBUTE_NAME_SAD, jwt);

    SADValidator validator = SADParser.getValidator(this.validationCertificate);
    
    try {
      validator.validate(authnRequest, assertion);
      Assert.fail("Expected SADValidationException - VALIDATION_BAD_AUDIENCE");
    }
    catch (SADValidationException e) {
      Assert.assertEquals("Expected error code VALIDATION_BAD_AUDIENCE", ErrorCode.VALIDATION_BAD_AUDIENCE, e.getErrorCode());
    }
  }
  
  /**
   * Verifies that the subject is correct.
   * 
   * @throws Exception
   *           for errors
   */
  @Test
  public void testMismatchingSubject() throws Exception {

    SAD sad = this.getTestSAD();
    String jwt = this.sadFactory.createJwt(sad);

    SADRequest sadRequest = SADRequestBuilder.builder()
      .id(SAD_REQUEST_ID)
      .requesterID(SIGNSERVICE_ENTITYID)
      .signRequestID(SIGN_REQUEST_ID)
      .docCount(1)
      .requestedVersion(SADVersion.VERSION_10)
      .build();
    AuthnRequest authnRequest = buildAuthnRequest(sadRequest);

    Assertion assertion = buildAssertion(IDP_ENTITYID, LevelOfAssuranceUris.AUTHN_CONTEXT_URI_LOA3);
    addAttribute(assertion, AttributeConstants.ATTRIBUTE_NAME_PERSONAL_IDENTITY_NUMBER, "198801012222");
    addAttribute(assertion, AttributeConstants.ATTRIBUTE_NAME_SAD, jwt);

    SADValidator validator = SADParser.getValidator(this.validationCertificate);
    
    try {
      validator.validate(authnRequest, assertion);
      Assert.fail("Expected SADValidationException - VALIDATION_BAD_SUBJECT");
    }
    catch (SADValidationException e) {
      Assert.assertEquals("Expected error code VALIDATION_BAD_SUBJECT", ErrorCode.VALIDATION_BAD_SUBJECT, e.getErrorCode());
    }
  }
  
  /**
   * Verifies that the "in-response-to" is correct.
   * 
   * @throws Exception
   *           for errors
   */
  @Test
  public void testMismatchingInResponseTo() throws Exception {

    SAD sad = this.getTestSAD();
    String jwt = this.sadFactory.createJwt(sad);

    SADRequest sadRequest = SADRequestBuilder.builder()
      .id("another-id")
      .requesterID(SIGNSERVICE_ENTITYID)
      .signRequestID(SIGN_REQUEST_ID)
      .docCount(1)
      .requestedVersion(SADVersion.VERSION_10)
      .build();
    AuthnRequest authnRequest = buildAuthnRequest(sadRequest);

    Assertion assertion = buildAssertion(IDP_ENTITYID, LevelOfAssuranceUris.AUTHN_CONTEXT_URI_LOA3);
    addAttribute(assertion, AttributeConstants.ATTRIBUTE_NAME_PERSONAL_IDENTITY_NUMBER, USER_ID);
    addAttribute(assertion, AttributeConstants.ATTRIBUTE_NAME_SAD, jwt);

    SADValidator validator = SADParser.getValidator(this.validationCertificate);
    
    try {
      validator.validate(authnRequest, assertion);
      Assert.fail("Expected SADValidationException - VALIDATION_BAD_IRT");
    }
    catch (SADValidationException e) {
      Assert.assertEquals("Expected error code VALIDATION_BAD_IRT", ErrorCode.VALIDATION_BAD_IRT, e.getErrorCode());
    }
  }
  
  /**
   * Verifies that the LoA from the SAD matches the assertion LoA is correct.
   * 
   * @throws Exception
   *           for errors
   */
  @Test
  public void testMismatchingLoa() throws Exception {

    SAD sad = this.getTestSAD();
    sad.getSeElnSadext().setLoa(LevelOfAssuranceUris.AUTHN_CONTEXT_URI_LOA4);
    String jwt = this.sadFactory.createJwt(sad);

    SADRequest sadRequest = SADRequestBuilder.builder()
      .id(SAD_REQUEST_ID)
      .requesterID(SIGNSERVICE_ENTITYID)
      .signRequestID(SIGN_REQUEST_ID)
      .docCount(1)
      .requestedVersion(SADVersion.VERSION_10)
      .build();
    AuthnRequest authnRequest = buildAuthnRequest(sadRequest);

    Assertion assertion = buildAssertion(IDP_ENTITYID, LevelOfAssuranceUris.AUTHN_CONTEXT_URI_LOA3);
    addAttribute(assertion, AttributeConstants.ATTRIBUTE_NAME_PERSONAL_IDENTITY_NUMBER, USER_ID);
    addAttribute(assertion, AttributeConstants.ATTRIBUTE_NAME_SAD, jwt);

    SADValidator validator = SADParser.getValidator(this.validationCertificate);
    
    try {
      validator.validate(authnRequest, assertion);
      Assert.fail("Expected SADValidationException - VALIDATION_BAD_LOA");
    }
    catch (SADValidationException e) {
      Assert.assertEquals("Expected error code VALIDATION_BAD_LOA", ErrorCode.VALIDATION_BAD_LOA, e.getErrorCode());
    }
  }
  
  /**
   * Verifies that the doc count matches.
   * 
   * @throws Exception
   *           for errors
   */
  @Test
  public void testMismatchingDocCount() throws Exception {

    SAD sad = this.getTestSAD();
    String jwt = this.sadFactory.createJwt(sad);

    SADRequest sadRequest = SADRequestBuilder.builder()
      .id(SAD_REQUEST_ID)
      .requesterID(SIGNSERVICE_ENTITYID)
      .signRequestID(SIGN_REQUEST_ID)
      .docCount(5)
      .requestedVersion(SADVersion.VERSION_10)
      .build();
    AuthnRequest authnRequest = buildAuthnRequest(sadRequest);

    Assertion assertion = buildAssertion(IDP_ENTITYID, LevelOfAssuranceUris.AUTHN_CONTEXT_URI_LOA3);
    addAttribute(assertion, AttributeConstants.ATTRIBUTE_NAME_PERSONAL_IDENTITY_NUMBER, USER_ID);
    addAttribute(assertion, AttributeConstants.ATTRIBUTE_NAME_SAD, jwt);

    SADValidator validator = SADParser.getValidator(this.validationCertificate);
    
    try {
      validator.validate(authnRequest, assertion);
      Assert.fail("Expected SADValidationException - VALIDATION_BAD_DOCS");
    }
    catch (SADValidationException e) {
      Assert.assertEquals("Expected error code VALIDATION_BAD_DOCS", ErrorCode.VALIDATION_BAD_DOCS, e.getErrorCode());
    }
  }
  
  /**
   * Verifies that the sign request ID from the SADRequest matches the reqid from the SAD.
   * 
   * @throws Exception
   *           for errors
   */
  @Test
  public void testMismatchingSignRequestId() throws Exception {

    SAD sad = this.getTestSAD();
    String jwt = this.sadFactory.createJwt(sad);

    SADRequest sadRequest = SADRequestBuilder.builder()
      .id(SAD_REQUEST_ID)
      .requesterID(SIGNSERVICE_ENTITYID)
      .signRequestID("ANOTHER_SIGN_REQUEST_ID")
      .docCount(1)
      .requestedVersion(SADVersion.VERSION_10)
      .build();
    AuthnRequest authnRequest = buildAuthnRequest(sadRequest);

    Assertion assertion = buildAssertion(IDP_ENTITYID, LevelOfAssuranceUris.AUTHN_CONTEXT_URI_LOA3);
    addAttribute(assertion, AttributeConstants.ATTRIBUTE_NAME_PERSONAL_IDENTITY_NUMBER, USER_ID);
    addAttribute(assertion, AttributeConstants.ATTRIBUTE_NAME_SAD, jwt);

    SADValidator validator = SADParser.getValidator(this.validationCertificate);
    
    try {
      validator.validate(authnRequest, assertion);
      Assert.fail("Expected SADValidationException - VALIDATION_BAD_SIGNREQUESTID");
    }
    catch (SADValidationException e) {
      Assert.assertEquals("Expected error code VALIDATION_BAD_SIGNREQUESTID", ErrorCode.VALIDATION_BAD_SIGNREQUESTID, e.getErrorCode());
    }
  }  

  private SAD getTestSAD() {
    return this.sadFactory.getBuilder()
      .subject(USER_ID)
      .audience(SIGNSERVICE_ENTITYID)
      .inResponseTo(SAD_REQUEST_ID)
      .loa(LevelOfAssuranceUris.AUTHN_CONTEXT_URI_LOA3)
      .requestID(SIGN_REQUEST_ID)
      .numberOfDocuments(1)
      .buildSAD();
  }

  private static AuthnRequest buildAuthnRequest(SADRequest sadRequest) {
    AuthnRequestBuilder builder = AuthnRequestBuilder.builder()
      .id("123")
      .issuer(SIGNSERVICE_ENTITYID);

    if (sadRequest != null) {
      Extensions exts = (Extensions) XMLObjectSupport.buildXMLObject(Extensions.DEFAULT_ELEMENT_NAME); 
      exts.getUnknownXMLObjects().add(sadRequest);
      builder = builder.extensions(exts);
    }

    return builder.build();
  }

  private static Assertion buildAssertion(String issuer, String loa) {
    Assertion assertion = (Assertion) XMLObjectSupport.buildXMLObject(Assertion.DEFAULT_ELEMENT_NAME); 
    assertion.setID("123456");
    Issuer _issuer = (Issuer) XMLObjectSupport.buildXMLObject(Issuer.DEFAULT_ELEMENT_NAME); 
    _issuer.setValue(issuer);
    assertion.setIssuer(_issuer);

    AuthnContextClassRef authnContextClassRef = (AuthnContextClassRef) XMLObjectSupport.buildXMLObject(AuthnContextClassRef.DEFAULT_ELEMENT_NAME); 
    authnContextClassRef.setURI(loa);

    AuthnContext authnContext = (AuthnContext) XMLObjectSupport.buildXMLObject(AuthnContext.DEFAULT_ELEMENT_NAME); 
    authnContext.setAuthnContextClassRef(authnContextClassRef);

    AuthnStatement authnStatement = (AuthnStatement) XMLObjectSupport.buildXMLObject(AuthnStatement.DEFAULT_ELEMENT_NAME); 
    authnStatement.setAuthnContext(authnContext);

    assertion.getAuthnStatements().add(authnStatement);

    return assertion;
  }

  private static void addAttribute(Assertion assertion, String name, String value) {
    if (assertion.getAttributeStatements().isEmpty()) {
      AttributeStatement stmnt = (AttributeStatement) XMLObjectSupport.buildXMLObject(AttributeStatement.DEFAULT_ELEMENT_NAME); 
      assertion.getAttributeStatements().add(stmnt);
    }
    assertion.getAttributeStatements().get(0).getAttributes().add(
      AttributeBuilder.builder(name).value(value).build());
  }
}
