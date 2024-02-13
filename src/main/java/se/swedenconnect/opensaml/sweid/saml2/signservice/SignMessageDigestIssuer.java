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

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.opensaml.saml.ext.saml2alg.DigestMethod;
import org.opensaml.saml.saml2.core.Attribute;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.xmlsec.algorithm.AlgorithmDescriptor;
import org.opensaml.xmlsec.algorithm.AlgorithmDescriptor.AlgorithmType;
import org.opensaml.xmlsec.algorithm.AlgorithmRegistry;
import org.opensaml.xmlsec.algorithm.AlgorithmSupport;
import org.opensaml.xmlsec.signature.support.SignatureConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.swedenconnect.opensaml.saml2.metadata.EntityDescriptorUtils;
import se.swedenconnect.opensaml.sweid.saml2.attribute.AttributeConstants;
import se.swedenconnect.opensaml.sweid.saml2.signservice.dss.Message;

/**
 * Utility bean that may be used by Identity Providers to issue a signMessageDigest attribute.
 *
 * @author Martin Lindström (martin@idsec.se)
 */
public class SignMessageDigestIssuer {

  /** Logger instance. */
  private final Logger log = LoggerFactory.getLogger(SignMessageDigestIssuer.class);

  /** SHA-256 is the default digest method. */
  public static final String DEFAULT_DIGEST_METHOD = SignatureConstants.ALGO_ID_DIGEST_SHA256;

  /** The default digest method to use. If not assigned, {@value #DEFAULT_DIGEST_METHOD} is used. */
  private String defaultDigestMethod;

  /**
   * Creates a signMessageDigest attribute using the default digest method.
   *
   * @param message the sign message
   * @return a signMessageDigest attribute
   */
  public Attribute create(final Message message) {
    return this.create(message, null);
  }

  /**
   * Creates a signMessageDigest attribute using the preferred digest method of the recipient, or the default digest
   * method if none is specified in the recipient metadata.
   *
   * @param message the sign message
   * @param recipient the recipient metadata (may be null)
   * @return a signMessageDigest attribute
   */
  public Attribute create(final Message message, final EntityDescriptor recipient) {
    if (message == null || message.getValue() == null) {
      throw new IllegalArgumentException("Supplied sign message is null or empty");
    }
    String digestAlgorithm = null;
    if (recipient != null) {
      try {
        digestAlgorithm = SignMessageDigestIssuer.getDigestPreference(recipient);
      }
      catch (final Exception e) {
        log.error("Error during recipient metadata analyze (looking for preferred digest algorithm)", e);
      }
    }
    if (digestAlgorithm == null) {
      digestAlgorithm = this.getDefaultDigestMethod();
    }
    log.debug("Using digest algorithm '{}' when issuing signMessageDigest", digestAlgorithm);

    final AlgorithmRegistry registry = AlgorithmSupport.getGlobalAlgorithmRegistry();
    final AlgorithmDescriptor descriptor = registry.get(digestAlgorithm);
    if (descriptor == null) {
      // Should never happen
      throw new SecurityException(digestAlgorithm + " is not supported");
    }
    MessageDigest messageDigest;
    try {
      messageDigest = MessageDigest.getInstance(descriptor.getJCAAlgorithmID());
    }
    catch (final NoSuchAlgorithmException e) {
      throw new SecurityException(e);
    }
    final byte[] digestValue = messageDigest.digest(message.getContent().getBytes(StandardCharsets.UTF_8));
    final String attributeValue =
        String.format("%s;%s", digestAlgorithm, Base64.getEncoder().encodeToString(digestValue));

    return AttributeConstants.ATTRIBUTE_TEMPLATE_SIGNMESSAGE_DIGEST.createBuilder()
        .value(attributeValue)
        .build();
  }

  /**
   * The recipient may specify the digest algorithm it prefers by including the {@code <alg:DigestMethod>} element in
   * its metadata.
   *
   * @param metadata the recipient's metadata
   * @return the preferred digest algorithm, or null if none is specified
   */
  public static String getDigestPreference(final EntityDescriptor metadata) {
    if (metadata == null) {
      return null;
    }
    List<DigestMethod> digestMethods = EntityDescriptorUtils.getDigestMethods(metadata);

    // Filter those that we don't support
    //
    final AlgorithmRegistry registry = AlgorithmSupport.getGlobalAlgorithmRegistry();
    if (!digestMethods.isEmpty()) {
      digestMethods = digestMethods.stream().filter(s -> {
        final AlgorithmDescriptor ad = registry.get(s.getAlgorithm());
        if (ad != null) {
          return AlgorithmType.MessageDigest.equals(ad.getType());
        }
        return false;
      }).collect(Collectors.toList());
    }

    if (digestMethods.isEmpty()) {
      return null;
    }

    return digestMethods.get(0).getAlgorithm();
  }

  /**
   * Gets the default digest method to use.
   *
   * @return the algorithm URI for the default digest method
   */
  public String getDefaultDigestMethod() {
    return this.defaultDigestMethod != null ? this.defaultDigestMethod : SignMessageDigestIssuer.DEFAULT_DIGEST_METHOD;
  }

  /**
   * Assigns the default digest method to use.
   *
   * @param defaultDigestMethod the algorithm URI for the default digest method
   */
  public void setDefaultDigestMethod(final String defaultDigestMethod) {
    final AlgorithmRegistry registry = AlgorithmSupport.getGlobalAlgorithmRegistry();
    final AlgorithmDescriptor descriptor = registry.get(defaultDigestMethod);
    if (descriptor == null) {
      throw new SecurityException(defaultDigestMethod + " is not supported");
    }
    if (descriptor.getType() != AlgorithmType.MessageDigest) {
      throw new SecurityException(defaultDigestMethod + " is not a valid digest algorithm");
    }

    this.defaultDigestMethod = defaultDigestMethod;
  }

}
