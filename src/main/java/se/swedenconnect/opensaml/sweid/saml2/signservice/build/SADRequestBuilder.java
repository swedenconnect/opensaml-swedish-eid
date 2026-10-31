/*
 * Copyright 2016-2026 Sweden Connect
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
package se.swedenconnect.opensaml.sweid.saml2.signservice.build;

import java.util.Arrays;
import java.util.List;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.opensaml.core.xml.util.XMLObjectSupport;

import se.swedenconnect.opensaml.common.builder.AbstractSAMLObjectBuilder;
import se.swedenconnect.opensaml.sweid.saml2.signservice.sap.Parameter;
import se.swedenconnect.opensaml.sweid.saml2.signservice.sap.RequestParams;
import se.swedenconnect.opensaml.sweid.saml2.signservice.sap.SADRequest;
import se.swedenconnect.opensaml.sweid.saml2.signservice.sap.SADVersion;

/**
 * Builder for creating a {@link SADRequest} using the builder pattern.
 *
 * @author Martin Lindström (martin@idsec.se)
 */
public class SADRequestBuilder extends AbstractSAMLObjectBuilder<SADRequest> {

  /**
   * Utility method that creates a builder.
   *
   * @return a builder
   */
  @Nonnull
  public static SADRequestBuilder builder() {
    return new SADRequestBuilder();
  }

  /**
   * Assigns the ID attribute for the {@code SADRequest}.
   *
   * @param id the ID attribute
   * @return the builder
   */
  @Nonnull
  public SADRequestBuilder id(@Nullable final String id) {
    this.object().setID(id);
    return this;
  }

  /**
   * Assigns the requester ID (entityID of the SP requesting the SAD).
   *
   * @param requesterID the entityID of the requester
   * @return the builder
   */
  @Nonnull
  public SADRequestBuilder requesterID(@Nullable final String requesterID) {
    this.object().setRequesterID(requesterID);
    return this;
  }

  /**
   * Assigns the value of the {@code RequestID} attribute of the associated {@code SignRequest}.
   *
   * @param signRequestID the signature request ID
   * @return the builder
   */
  @Nonnull
  public SADRequestBuilder signRequestID(@Nullable final String signRequestID) {
    this.object().setSignRequestID(signRequestID);
    return this;
  }

  /**
   * Assigns the number of requested signatures in the associated sign request.
   *
   * @param docCount the document count
   * @return the builder
   */
  @Nonnull
  public SADRequestBuilder docCount(@Nullable final Integer docCount) {
    this.object().setDocCount(docCount);
    return this;
  }

  /**
   * Assigns the requested version of the SAD.
   *
   * @param sadVersion the SAD version
   * @return the builder
   */
  @Nonnull
  public SADRequestBuilder requestedVersion(@Nullable final SADVersion sadVersion) {
    this.object().setRequestedVersion(sadVersion);
    return this;
  }

  /**
   * Assigns the {@code RequestParams} element.
   *
   * @param requestParams the RequestParams
   * @return the builder
   */
  @Nonnull
  public SADRequestBuilder requestParams(@Nullable final RequestParams requestParams) {
    this.object().setRequestParams(requestParams);
    return this;
  }

  /** {@inheritDoc} */
  @Nonnull
  @Override
  protected Class<SADRequest> getObjectType() {
    return SADRequest.class;
  }

  /**
   * Builder for creating a {@link RequestParams} using the builder pattern.
   *
   * @author Martin Lindström (martin@idsec.se)
   */
  public static class RequestParamsBuilder extends AbstractSAMLObjectBuilder<RequestParams> {

    /**
     * Utility method that creates a builder.
     *
     * @return a builder
     */
    @Nonnull
    public static RequestParamsBuilder builder() {
      return new RequestParamsBuilder();
    }

    /**
     * Utility method creating a {@link Parameter} object.
     *
     * @param name the parameter name
     * @param value the parameter value
     * @return the Parameter object
     */
    @Nonnull
    public static Parameter parameter(@Nullable final String name, @Nullable final String value) {
      Parameter p = (Parameter) XMLObjectSupport.buildXMLObject(Parameter.DEFAULT_ELEMENT_NAME);
      p.setName(name);
      p.setValue(value);
      return p;
    }

    /**
     * Assigns the parameters to the request params.
     *
     * @param parameters a list of parameters
     * @return the builder
     */
    @Nonnull
    public RequestParamsBuilder parameters(@Nullable final List<Parameter> parameters) {
      this.object().getParameters().clear();
      if (parameters != null) {
        this.object().getParameters().addAll(parameters);
      }
      return this;
    }

    /**
     * Assigns the parameters to the request params.
     *
     * @param parameters a list of parameters
     * @return the builder
     */
    @Nonnull
    public RequestParamsBuilder parameters(@Nullable final Parameter... parameters) {
      return this.parameters(parameters != null ? Arrays.asList(parameters) : null);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected Class<RequestParams> getObjectType() {
      return RequestParams.class;
    }

  }

}
