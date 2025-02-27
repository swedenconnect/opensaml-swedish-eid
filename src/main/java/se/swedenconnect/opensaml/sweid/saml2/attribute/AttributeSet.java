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
package se.swedenconnect.opensaml.sweid.saml2.attribute;

import java.io.Serializable;
import java.util.List;

import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.metadata.RequestedAttribute;

import se.swedenconnect.opensaml.saml2.attribute.AttributeTemplate;

/**
 * The specification "Attribute Specification for the Swedish eID Framework" of the Swedish eID Framework defines a
 * number of "Attribute Sets". This interface represents such an attribute set.
 *
 * @author Martin Lindström (martin@idsec.se)
 * @see AttributeSetConstants
 */
public interface AttributeSet extends Serializable {

  /**
   * Each attribute set within the Swedish eID Framework is assigned an unique profile identifier. This method
   * returns this unique value.
   *
   * @return the attribute set identifier
   * @see #getUri()
   */
  String getIdentifier();

  /**
   * Each attribute set within the Swedish eID Framework is assigned an unique URI. This method returns this value.
   *
   * @return the attribute set URI
   * @see #getIdentifier()
   */
  String getUri();

  /**
   * Returns the friendly name for this attribute set.
   *
   * @return the attribute set friendly name
   */
  String getFriendlyName();

  /**
   * Returns the required attributes for this attribute set
   *
   * @return an array of required attributes for this set
   */
  AttributeTemplate[] getRequiredAttributes();

  /**
   * Returns the recommended attributes for this attribute set
   *
   * @return an array of recommended attributes for this set
   */
  AttributeTemplate[] getRecommendedAttributes();

  /**
   * Validates the attributes received in the assertion against the attribute set.
   *
   * The validation logic is as follows:
   * <ul>
   * <li>Make sure that all the attributes that the set states as "required" are included in the assertion.</li>
   * <li>Make sure that all explicitly requested attributes, that has the attribute isRequired set, are included in the
   * assertion. These requested attributes are listed in the SP metadata record as {@code <md:RequestedAttribute>}
   * elements.</li>
   * </ul>
   *
   * @param assertion
   *          the assertion containing the attributes to validate
   * @param explicitlyRequestedAttributes
   *          a list of explicitly requested attributes that the Service Provider has specified in its metadata record
   *          (using {@code <md:RequestedAttribute>} elements). This parameter may be {@code null} if no explicitly
   *          requested attributes exist
   * @throws AttributesValidationException
   *           for violations of the attribute set
   */
  void validateAttributes(final Assertion assertion, final List<RequestedAttribute> explicitlyRequestedAttributes)
      throws AttributesValidationException;

}
