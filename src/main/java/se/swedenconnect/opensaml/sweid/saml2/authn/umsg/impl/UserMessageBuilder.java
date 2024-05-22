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
package se.swedenconnect.opensaml.sweid.saml2.authn.umsg.impl;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import se.swedenconnect.opensaml.sweid.saml2.authn.umsg.UserMessage;

/**
 * Builder for {@link UserMessage} objects.
 *
 * @author Martin Lindström
 */
public class UserMessageBuilder extends AbstractSAMLObjectBuilder<UserMessage> {

  /** {@inheritDoc} */
  @Nonnull
  @Override
  public UserMessage buildObject() {
    return this.buildObject(UserMessage.DEFAULT_ELEMENT_NAME.getNamespaceURI(),
        UserMessage.DEFAULT_ELEMENT_LOCAL_NAME, UserMessage.DEFAULT_ELEMENT_NAME.getPrefix());
  }

  /** {@inheritDoc} */
  @Nonnull
  @Override
  public UserMessage buildObject(@Nullable final String namespaceURI, @Nonnull final String localName,
      @Nullable final String namespacePrefix) {
    return new UserMessageImpl(namespaceURI, localName, namespacePrefix);
  }
}