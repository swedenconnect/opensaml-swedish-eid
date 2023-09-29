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
package se.swedenconnect.opensaml.sweid.xmlsec.config;

import java.util.Arrays;

import org.opensaml.xmlsec.EncryptionConfiguration;
import org.opensaml.xmlsec.encryption.support.EncryptionConstants;
import org.opensaml.xmlsec.impl.BasicEncryptionConfiguration;

import se.swedenconnect.opensaml.xmlsec.config.SAML2IntSecurityConfiguration;
import se.swedenconnect.opensaml.xmlsec.config.SecurityConfiguration;

/**
 * A {@link SecurityConfiguration} instance with algorithm defaults according to the Swedish eID Framework (see
 * https://docs.swedenconnect.se).
 * <p>
 * Currently the implementation equals {@link SAML2IntSecurityConfiguration} with the exception that it defaults to
 * AES-CBC before AES-GCM for compatibility reasons.
 * </p>
 *
 * @author Martin Lindström (martin@idsec.se)
 */
public class SwedishEidSecurityConfiguration extends SAML2IntSecurityConfiguration {

  /** {@inheritDoc} */
  @Override
  public String getProfileName() {
    return "swedish-eid-framework";
  }

  /** {@inheritDoc} */
  @Override
  protected EncryptionConfiguration createDefaultEncryptionConfiguration() {
    final BasicEncryptionConfiguration config =
        (BasicEncryptionConfiguration) super.createDefaultEncryptionConfiguration();

    config.setDataEncryptionAlgorithms(Arrays.asList(
        EncryptionConstants.ALGO_ID_BLOCKCIPHER_AES256,
        EncryptionConstants.ALGO_ID_BLOCKCIPHER_AES192,
        EncryptionConstants.ALGO_ID_BLOCKCIPHER_AES128,
        EncryptionConstants.ALGO_ID_BLOCKCIPHER_AES256_GCM,
        EncryptionConstants.ALGO_ID_BLOCKCIPHER_AES192_GCM,
        EncryptionConstants.ALGO_ID_BLOCKCIPHER_AES128_GCM,
        EncryptionConstants.ALGO_ID_BLOCKCIPHER_TRIPLEDES));

    return config;
  }

}
