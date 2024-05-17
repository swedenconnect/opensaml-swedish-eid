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
package se.swedenconnect.opensaml.sweid.saml2.authn.umsg.build;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.opensaml.core.xml.XMLRuntimeException;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import se.swedenconnect.opensaml.common.builder.AbstractSAMLObjectBuilder;
import se.swedenconnect.opensaml.sweid.saml2.authn.umsg.Message;
import se.swedenconnect.opensaml.sweid.saml2.authn.umsg.UserMessage;

import java.util.List;
import java.util.Objects;

/**
 * A builder for {@link UserMessage} objects.
 *
 * @author Martin Lindström
 */
public class UserMessageBuilder extends AbstractSAMLObjectBuilder<UserMessage> {

  /**
   * Creates a {@code UserMessageBuilder}.
   *
   * @return a {@code UserMessageBuilder}
   */
  public static UserMessageBuilder builder() {
    return new UserMessageBuilder();
  }

  /** {@inheritDoc} */
  @Nonnull
  @Override
  protected Class<UserMessage> getObjectType() {
    return UserMessage.class;
  }

  /**
   * Assigns the MIME type.
   *
   * @param mimeType the MIME type
   * @return the builder
   */
  @Nonnull
  public UserMessageBuilder mimeType(@Nullable final String mimeType) {
    this.object().setMimeType(mimeType);
    return this;
  }

  /**
   * Adds a {@link Message}.
   *
   * @param message the {@link Message} to add
   * @return the builder
   */
  @Nonnull
  public UserMessageBuilder message(@Nonnull final Message message) {
    try {
      this.object().getMessages().add(XMLObjectSupport.cloneXMLObject(
          Objects.requireNonNull(message, "message must not be null")));
    }
    catch (final MarshallingException | UnmarshallingException e) {
      throw new XMLRuntimeException(e);
    }
    return this;
  }

  /**
   * Adds a list of {@link Message}s.
   * <p>
   * If messages have already been added, these will be overwritten.
   * </p>
   *
   * @param messages the messages to add (if {@code null}, any previously added messages will be cleared)
   * @return the builder
   */
  @Nonnull
  public UserMessageBuilder messages(@Nullable final List<Message> messages) {
    this.object().getMessages().clear();
    if (messages != null) {
      messages.forEach(this::message);
    }
    return this;
  }

  /**
   * Adds {@link Message}s.
   * <p>
   * If messages have already been added, these will be overwritten.
   * </p>
   *
   * @param messages the messages to add (if {@code null}, any previously added messages will be cleared)
   * @return the builder
   */
  @Nonnull
  public UserMessageBuilder messages(@Nullable final Message... messages) {
    return this.messages(messages != null ? List.of(messages) : null);
  }

}
