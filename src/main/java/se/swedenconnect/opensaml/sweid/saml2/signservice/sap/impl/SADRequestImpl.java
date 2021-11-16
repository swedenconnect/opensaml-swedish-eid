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
package se.swedenconnect.opensaml.sweid.saml2.signservice.sap.impl;

import java.util.ArrayList;
import java.util.List;

import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.schema.XSInteger;
import org.opensaml.core.xml.schema.XSString;
import org.opensaml.core.xml.schema.impl.XSIntegerBuilder;
import org.opensaml.core.xml.schema.impl.XSStringBuilder;

import se.swedenconnect.opensaml.sweid.saml2.signservice.sap.RequestParams;
import se.swedenconnect.opensaml.sweid.saml2.signservice.sap.SADRequest;
import se.swedenconnect.opensaml.sweid.saml2.signservice.sap.SADVersion;

/**
 * Implementation class for the {@link SADRequest} interface.
 * 
 * @author Martin Lindström (martin@idsec.se)
 */
public class SADRequestImpl extends AbstractXMLObject implements SADRequest {

  /** ID of the message. */
  private String id;

  /** The requester ID. */
  private XSString requesterID;

  /** The signature request ID. */
  private XSString signRequestID;

  /** The document count element. */
  private XSInteger docCount;

  /** The SAD version. */
  private XSString requestedVersion;

  /** The {@code RequestParams} element. */
  private RequestParams requestParams;

  /**
   * Constructor.
   * 
   * @param namespaceURI
   *          the namespace the element is in
   * @param elementLocalName
   *          the local name of the XML element this Object represents
   * @param namespacePrefix
   *          the prefix for the given namespace
   */
  protected SADRequestImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
    super(namespaceURI, elementLocalName, namespacePrefix);
  }

  /** {@inheritDoc} */
  @Override
  public List<XMLObject> getOrderedChildren() {
    final ArrayList<XMLObject> children = new ArrayList<>();
    if (this.requesterID != null) {
      children.add(this.requesterID);
    }
    if (this.signRequestID != null) {
      children.add(this.signRequestID);
    }
    if (this.docCount != null) {
      children.add(this.docCount);
    }
    if (this.requestedVersion != null) {
      children.add(this.requestedVersion);
    }
    if (this.requestParams != null) {
      children.add(this.requestParams);
    }
    if (children.size() == 0) {
      return null;
    }
    return children;
  }

  /** {@inheritDoc} */
  @Override
  public String getID() {
    return this.id;
  }

  /** {@inheritDoc} */
  @Override
  public void setID(final String id) {
    final String oldID = this.id;
    this.id = this.prepareForAssignment(this.id, id);
    this.registerOwnID(oldID, this.id);
  }

  /** {@inheritDoc} */
  @Override
  public String getRequesterID() {
    return this.requesterID != null ? this.requesterID.getValue() : null;
  }

  /** {@inheritDoc} */
  @Override
  public void setRequesterID(final String requesterID) {
    XSString id = null;
    if (requesterID != null) {
      id = (new XSStringBuilder()).buildObject(
        this.getElementQName().getNamespaceURI(), REQUESTER_ID_LOCAL_NAME, this.getElementQName().getPrefix());
      id.setValue(requesterID);
    }
    this.setRequesterID(id);
  }

  /**
   * Assigns the requester ID as a {@code XSString} string type.
   * 
   * @param requesterID
   *          the requester ID
   */
  public void setRequesterID(final XSString requesterID) {
    this.requesterID = this.prepareForAssignment(this.requesterID, requesterID);
  }

  /** {@inheritDoc} */
  @Override
  public String getSignRequestID() {
    return this.signRequestID != null ? this.signRequestID.getValue() : null;
  }

  /** {@inheritDoc} */
  @Override
  public void setSignRequestID(final String signRequestID) {
    XSString id = null;
    if (signRequestID != null) {
      id = (new XSStringBuilder()).buildObject(
        this.getElementQName().getNamespaceURI(), SIGN_REQUEST_ID_LOCAL_NAME, this.getElementQName().getPrefix());
      id.setValue(signRequestID);
    }
    this.setSignRequestID(id);
  }

  /**
   * Assigns the sign request as a {@code XSString} string type.
   * 
   * @param signRequestID
   *          the sign request id
   */
  public void setSignRequestID(final XSString signRequestID) {
    this.signRequestID = this.prepareForAssignment(this.signRequestID, signRequestID);
  }

  /** {@inheritDoc} */
  @Override
  public Integer getDocCount() {
    return this.docCount != null ? this.docCount.getValue() : null;
  }

  /** {@inheritDoc} */
  @Override
  public void setDocCount(final Integer docCount) {
    XSInteger count = null;
    if (docCount != null) {
      count = (new XSIntegerBuilder()).buildObject(
        this.getElementQName().getNamespaceURI(), DOC_COUNT_LOCAL_NAME, this.getElementQName().getPrefix());
      count.setValue(docCount);
    }
    this.setDocCount(count);
  }

  /**
   * Assigns the {@code DocCount} element.
   * 
   * @param docCount
   *          the document count
   */
  public void setDocCount(final XSInteger docCount) {
    this.docCount = this.prepareForAssignment(this.docCount, docCount);
  }

  /** {@inheritDoc} */
  @Override
  public SADVersion getRequestedVersion() {
    return this.requestedVersion != null && this.requestedVersion.getValue() != null
        ? SADVersion.valueOf(this.requestedVersion.getValue()) : SADVersion.VERSION_10;
  }

  /** {@inheritDoc} */
  @Override
  public void setRequestedVersion(final SADVersion sadVersion) {
    XSString ver = null;
    if (sadVersion != null) {
      ver = (new XSStringBuilder()).buildObject(
        this.getElementQName().getNamespaceURI(), REQUESTED_VERSION_LOCAL_NAME, this.getElementQName().getPrefix());
      ver.setValue(sadVersion.toString());
    }
    this.setRequestedVersion(ver);
  }

  /**
   * Assigns the requested version as a string.
   * 
   * @param sadVersion
   *          the requested SAD version
   */
  public void setRequestedVersion(final XSString sadVersion) {
    this.requestedVersion = this.prepareForAssignment(this.requestedVersion, sadVersion);
  }

  /** {@inheritDoc} */
  @Override
  public RequestParams getRequestParams() {
    return this.requestParams;
  }

  /** {@inheritDoc} */
  @Override
  public void setRequestParams(final RequestParams requestParams) {
    this.requestParams = this.prepareForAssignment(this.requestParams, requestParams);
  }

}
