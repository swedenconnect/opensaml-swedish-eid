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
import se.swedenconnect.opensaml.common.builder.AbstractSAMLObjectBuilder;
import se.swedenconnect.opensaml.sweid.saml2.authn.umsg.Message;

import java.util.Objects;

/**
 * A builder for {@link Message} objects.
 *
 * @author Martin Lindström
 */
public class MessageBuilder extends AbstractSAMLObjectBuilder<Message> {

  /**
   * Creates a new {@code MessageBuilder}.
   *
   * @return a {@code MessageBuilder}
   */
  public static MessageBuilder builder() {
    return new MessageBuilder();
  }

  /** {@inheritDoc} */
  @Nonnull
  @Override
  protected Class<Message> getObjectType() {
    return Message.class;
  }

  /**
   * Assigns the language tag.
   *
   * @param language the language tag
   * @return the builder
   */
  @Nonnull
  public MessageBuilder language(@Nonnull final String language) {
    this.object().setXMLLang(Objects.requireNonNull(language, "language must not be null"));
    return this;
  }

  /**
   * Assigns the message content in its non-encoded form (i.e., the text as a UTF-8 string).
   *
   * @param content the message content
   * @return the builder
   */
  @Nonnull
  public MessageBuilder content(@Nonnull final String content) {
    this.object().setContent(Objects.requireNonNull(content, "content must not be null"));
    return this;
  }

}
