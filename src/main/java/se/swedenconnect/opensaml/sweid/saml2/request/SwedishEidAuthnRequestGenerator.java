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
package se.swedenconnect.opensaml.sweid.saml2.request;

import java.util.List;

import org.opensaml.saml.metadata.resolver.MetadataResolver;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.security.x509.X509Credential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.swedenconnect.opensaml.saml2.core.build.AuthnRequestBuilder;
import se.swedenconnect.opensaml.saml2.core.build.ExtensionsBuilder;
import se.swedenconnect.opensaml.saml2.metadata.EntityDescriptorUtils;
import se.swedenconnect.opensaml.saml2.request.AuthnRequestGenerator;
import se.swedenconnect.opensaml.saml2.request.AuthnRequestGeneratorContext;
import se.swedenconnect.opensaml.saml2.request.DefaultAuthnRequestGenerator;
import se.swedenconnect.opensaml.saml2.request.RequestGenerationException;
import se.swedenconnect.opensaml.sweid.saml2.authn.psc.PrincipalSelection;
import se.swedenconnect.opensaml.sweid.saml2.authn.umsg.UserMessage;
import se.swedenconnect.opensaml.sweid.saml2.metadata.entitycategory.EntityCategoryConstants;
import se.swedenconnect.opensaml.sweid.saml2.signservice.SignMessageEncrypter;
import se.swedenconnect.opensaml.sweid.saml2.signservice.dss.SignMessage;

/**
 * An {@link AuthnRequestGenerator} for the Swedish eID Framework.
 *
 * @author Martin Lindström (martin@idsec.se)
 */
public class SwedishEidAuthnRequestGenerator extends DefaultAuthnRequestGenerator {

  /** Logging instance. */
  private final Logger log = LoggerFactory.getLogger(SwedishEidAuthnRequestGenerator.class);

  /** The sign message encrypter. */
  private SignMessageEncrypter signMessageEncrypter;

  /** Is this SP a signature service? */
  private Boolean signServiceFlag;

  /**
   * Constructor.
   *
   * @param spEntityID the SP entityID
   * @param signCredential the signing credential
   * @param metadataResolver the metadata resolver
   */
  public SwedishEidAuthnRequestGenerator(final String spEntityID, final X509Credential signCredential,
      final MetadataResolver metadataResolver) {
    super(spEntityID, signCredential, metadataResolver);
  }

  /**
   * Constructor.
   *
   * @param spMetadata the SP metadata
   * @param signCredential the signing credential
   * @param metadataResolver the metadata resolver
   */
  public SwedishEidAuthnRequestGenerator(final EntityDescriptor spMetadata, final X509Credential signCredential,
      final MetadataResolver metadataResolver) {
    super(spMetadata, signCredential, metadataResolver);
  }

  /**
   * If the {@code context} is a {@link SwedishEidAuthnRequestGeneratorContext}, the method will ask the context for
   * builders for the {@code SignMessage} and {@code PrincipalSelection} extensions, and add them if present.
   */
  @Override
  protected void addExtensions(final AuthnRequestBuilder builder, final AuthnRequestGeneratorContext context,
      final EntityDescriptor idpMetadata) throws RequestGenerationException {

    if (context instanceof final SwedishEidAuthnRequestGeneratorContext scontext) {
      final SignMessage signMessage = this.isSignatureService()
          ? scontext.getSignMessageBuilderFunction().apply(idpMetadata, this.signMessageEncrypter)
          : null;
      final PrincipalSelection principalSelection = scontext.getPrincipalSelectionBuilderFunction().get();
      final UserMessage userMessage = this.supportsUserMessage(idpMetadata)
          ? scontext.getUserMessageBuilderFunction().apply(idpMetadata)
          : null;

      if (signMessage != null || principalSelection != null || userMessage != null) {
        builder.extensions(ExtensionsBuilder.builder()
            .extension(signMessage)
            .extension(principalSelection)
            .extension(userMessage)
            .build());
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  protected List<String> getAssuranceCertificationUris(
      final EntityDescriptor idpMetadata, final AuthnRequestGeneratorContext context)
      throws RequestGenerationException {

    final List<String> defaultUris = super.getAssuranceCertificationUris(idpMetadata, context);

    return defaultUris.stream()
        .filter(uri -> {
          if (uri.contains("sigm")) {
            log.debug("Excluding '{}' from metadata for '{}' since this URI is deprecated",
                uri, idpMetadata.getEntityID());
            return false;
          }
          return true;
        })
        .toList();
  }

  /**
   * Predicate that tells if the entity that we are serving is a signature service SP.
   *
   * @return true if this is a signature service, and false otherwise
   */
  protected boolean isSignatureService() {
    if (this.signServiceFlag == null) {
      this.signServiceFlag = EntityDescriptorUtils.getEntityCategories(this.getSpMetadata())
          .stream()
          .anyMatch(c -> c.equals(EntityCategoryConstants.SERVICE_TYPE_CATEGORY_SIGSERVICE.getUri()));
    }
    return this.signServiceFlag;
  }

  /**
   * Predicate that tells whether an IdP supports the {@code UserMessage} extension.
   *
   * @param idpMetadata the IdP metadata entry
   * @return {@code true} if the IdP supports the extension, and {@code false} otherwise
   */
  protected boolean supportsUserMessage(final EntityDescriptor idpMetadata) {
    return EntityDescriptorUtils.getEntityCategories(idpMetadata).stream()
        .anyMatch(c -> EntityCategoryConstants.GENERAL_CATEGORY_SUPPORTS_USER_MESSAGE.getUri().equals(c));
  }

  /**
   * Adds the sign message encrypter.
   *
   * @param signMessageEncrypter the encrypter to add
   */
  public void setSignMessageEncrypter(final SignMessageEncrypter signMessageEncrypter) {
    this.signMessageEncrypter = signMessageEncrypter;
  }

}
