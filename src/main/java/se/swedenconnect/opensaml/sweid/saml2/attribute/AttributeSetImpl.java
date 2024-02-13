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
package se.swedenconnect.opensaml.sweid.saml2.attribute;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.Attribute;
import org.opensaml.saml.saml2.metadata.RequestedAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.swedenconnect.opensaml.saml2.attribute.AttributeTemplate;

/**
 * A bean representing an Attribute Set as defined in Attribute Specification for the Swedish eID Framework.
 *
 * @author Martin Lindström (martin@idsec.se)
 */
public class AttributeSetImpl implements AttributeSet {

  private static final long serialVersionUID = 6392579786155678789L;

  /** Logging instance. */
  private static final Logger logger = LoggerFactory.getLogger(AttributeSetImpl.class);

  /** The unique profile identifier. */
  private String identifier;

  /** The unique profile URI. */
  private String uri;

  /** The "friendly name" of the attribute profile. */
  private String friendlyName;

  /** The required attributes for this attribute profile. */
  private List<AttributeTemplate> requiredAttributes;

  /** The recommended attributes for this attribute profile. */
  private List<AttributeTemplate> recommendedAttributes;

  /**
   * Default constructor.
   */
  public AttributeSetImpl() {
  }

  /**
   * A constructor setting all properties of this bean.
   *
   * @param identifier the unique profile identifier
   * @param uri the unique profile URI
   * @param friendlyName the "friendly name" of the attribute set
   * @param requiredAttributes the required attributes for this attribute set
   * @param recommendedAttributes the recommended attributes for this attribute set
   */
  public AttributeSetImpl(final String identifier, final String uri, final String friendlyName,
      final AttributeTemplate[] requiredAttributes, final AttributeTemplate[] recommendedAttributes) {
    this.setIdentifier(identifier);
    this.setUri(uri);
    this.setFriendlyName(friendlyName);
    this.setRequiredAttributes(requiredAttributes);
    this.setRecommendedAttributes(recommendedAttributes);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void validateAttributes(final Assertion assertion,
      final List<RequestedAttribute> explicitlyRequestedAttributes)
      throws AttributesValidationException {

    logger.trace("Validating the attributes from assertion '{}' against attribute set '{}' ({}) ...",
        assertion.getID(), this.identifier, this.uri);

    List<Attribute> attributes = assertion.getAttributeStatements().get(0).getAttributes();

    // Make sure that all attributes required by the attribute set was received in the assertion.
    //
    for (AttributeTemplate requiredAttribute : this.requiredAttributes) {
      Optional<Attribute> found =
          attributes.stream().filter(a -> requiredAttribute.getName().equals(a.getName())).findFirst();
      if (!found.isPresent()) {
        String msg = String.format(
            "Attribute '%s' (%s) is required according to the attribute set '%s' (%s) but is not included in assertion '%s'",
            requiredAttribute.getName(), requiredAttribute.getFriendlyName(), this.identifier, this.uri,
            assertion.getID());
        logger.error(msg);
        throw new AttributesValidationException(msg);
      }
    }
    logger.debug("All requested attributes according to attribute profile '{}' ({}) was received in assertion '{}'",
        this.identifier, this.uri, assertion.getID());

    // Next, check that all requested attributes are there.
    //
    if (explicitlyRequestedAttributes != null) {
      for (RequestedAttribute ra : explicitlyRequestedAttributes) {
        Optional<Attribute> found = attributes.stream().filter(a -> ra.getName().equals(a.getName())).findFirst();
        if (!found.isPresent()) {
          if (ra.isRequired() != null && ra.isRequired()) {
            String msg = String.format(
                "Attribute '%s' (%s) is listed a RequestedAttribute with isRequired=true in SP metadata, but does not appear in assertion '%s'",
                ra.getName(), ra.getFriendlyName(), assertion.getID());
            logger.error(msg);
            throw new AttributesValidationException(msg);
          }
          else {
            logger.info(
                "Attribute '{}' ({}) is listed a requested, but not required, in SP metadata. It does not appear in assertion '{}'",
                ra.getName(), ra.getFriendlyName(), assertion.getID());
          }
        }
        else {
          // Note: There are some odd cases when the SP may give also a value for the requested attribute. We do not
          // make any checks for this since it has no bearing on the Swedish eID Framework.
          //
          logger.debug("Attribute '{}' ({}) was explicitly requested in SP metadata and it appears in assertion '{}'",
              ra.getName(), ra.getFriendlyName(), assertion.getID());
        }
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getIdentifier() {
    return this.identifier;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getUri() {
    return this.uri;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getFriendlyName() {
    return this.friendlyName;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AttributeTemplate[] getRequiredAttributes() {
    return this.requiredAttributes != null ? this.requiredAttributes.toArray(new AttributeTemplate[] {})
        : new AttributeTemplate[0];
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AttributeTemplate[] getRecommendedAttributes() {
    return this.recommendedAttributes != null ? this.recommendedAttributes.toArray(new AttributeTemplate[] {})
        : new AttributeTemplate[0];
  }

  /**
   * Each attribute set within the Swedish eID Framework is assigned an unique profile identifier. This method assigns
   * this unique value.
   *
   * @param identifier the identifier to assign
   */
  public void setIdentifier(final String identifier) {
    this.identifier = identifier;
  }

  /**
   * Each attribute set within the Swedish eID Framework is assigned an unique URI. This method assigns this unique
   * value.
   *
   * @param uri the URI to assign
   */
  public void setUri(final String uri) {
    this.uri = uri;
  }

  /**
   * Assigns the friendly name for this attribute set.
   *
   * @param friendlyName the friendlyName to set
   */
  public void setFriendlyName(final String friendlyName) {
    this.friendlyName = friendlyName;
  }

  /**
   * Assigns the required attributes for this attribute set.
   *
   * @param requiredAttributes the attributes to assign
   */
  public void setRequiredAttributes(final AttributeTemplate[] requiredAttributes) {
    this.requiredAttributes = requiredAttributes != null ? Arrays.asList(requiredAttributes) : Collections.emptyList();
  }

  /**
   * Assigns the recommended attributes for this set.
   *
   * @param recommendedAttributes the attributes to assign
   */
  public void setRecommendedAttributes(final AttributeTemplate[] recommendedAttributes) {
    this.recommendedAttributes =
        recommendedAttributes != null ? Arrays.asList(recommendedAttributes) : Collections.emptyList();
  }

}
