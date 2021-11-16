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
package se.swedenconnect.opensaml.sweid.saml2.authn.psc.build;

import java.util.Arrays;
import java.util.List;

import org.opensaml.core.xml.XMLRuntimeException;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;

import se.swedenconnect.opensaml.common.builder.AbstractSAMLObjectBuilder;
import se.swedenconnect.opensaml.sweid.saml2.authn.psc.MatchValue;
import se.swedenconnect.opensaml.sweid.saml2.authn.psc.PrincipalSelection;

/**
 * A builder for {@link PrincipalSelection} objects.
 * 
 * @author Martin Lindström (martin@idsec.se)
 */
public class PrincipalSelectionBuilder extends AbstractSAMLObjectBuilder<PrincipalSelection> {

  /**
   * Creates a new {@code PrincipalSelectionBuilder} instance.
   * 
   * @return a PrincipalSelectionBuilder instance
   */
  public static PrincipalSelectionBuilder builder() {
    return new PrincipalSelectionBuilder();
  }

  /** {@inheritDoc} */
  @Override
  protected Class<PrincipalSelection> getObjectType() {
    return PrincipalSelection.class;
  }

  /**
   * Assigns the match values.
   * 
   * @param matchValues
   *          a list of match values
   * @return the builder
   */
  public PrincipalSelectionBuilder matchValues(final List<MatchValue> matchValues) {
    this.object().getMatchValues().clear();
    if (matchValues == null || matchValues.isEmpty()) {
      return this;
    }
    for (final MatchValue mv : matchValues) {
      try {
        this.object().getMatchValues().add(XMLObjectSupport.cloneXMLObject(mv));
      }
      catch (MarshallingException | UnmarshallingException e) {
        throw new XMLRuntimeException(e);
      }
    }
    return this;
  }

  /**
   * Assigns the match values.
   * 
   * @param matchValues
   *          the match values
   * @return the builder
   */
  public PrincipalSelectionBuilder matchValues(final MatchValue... matchValues) {
    return this.matchValues(matchValues != null ? Arrays.asList(matchValues) : null);
  }

}
