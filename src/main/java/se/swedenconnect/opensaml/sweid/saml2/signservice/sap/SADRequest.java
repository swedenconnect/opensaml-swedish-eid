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
package se.swedenconnect.opensaml.sweid.saml2.signservice.sap;

import javax.xml.namespace.QName;

import org.opensaml.saml.common.SAMLObject;

/**
 * Definitions of the SADRequest type:
 *
 * <p>
 * The SAD Request is provided in a {@code <sap:SADRequest>} element. The element has the following elements and
 * attributes:
 * </p>
 * <dl>
 * <dt>{@code RequesterID} [Required]</dt>
 * <dd>Specifies the SAML entityID of the requesting entity. The value for this element should be the same identifier as
 * given in the {@code <saml2:Issuer>} element of the {@code <saml2p:AuthnRequest>} that encapsulates the {@code
 * <sap:SADRequest>} extension.</dd>
 * <dt>{@code SignRequestID} [Required]</dt>
 * <dd>Specifies the value of the RequestID attribute of the associated SignRequest.</dd>
 * <dt>{@code DocCount} [Required]</dt>
 * <dd>The number of requested signatures in the associated sign request.</dd>
 * <dt>{@code RequestedVersion} [Optional Default="1.0"]</dt>
 * <dd>The requested version of the SAD.</dd>
 * <dt>{@code RequestParams} [Optional]</dt>
 * <dd>Optional parameters provided as name-value pairs. This specification does not define any parameters. The use of
 * parameters may be defined in profiles of this specification or may be negotiated by other means between a remote
 * signing service and an Identity Provider.</dd>
 * <dt>{@code ID}</dt>
 * <dd>Attribute holding an unique identifier for the {@code SADRequest}.</dd>
 * </dl>
 *
 * The following schema fragment defines the {@code <sap:SADRequest>} element:
 *
 * <pre>
 * {@code
 * <xs:element name="SADRequest" type="sap:SADRequestType" />
 *
 * <xs:complexType name="SADRequestType">
 *   <xs:sequence>
 *     <xs:element name="RequesterID" type="xs:string" />
 *     <xs:element name="SignRequestID" type="xs:string" />
 *     <xs:element name="DocCount" type="xs:int" />
 *     <xs:element name="RequestedVersion" type="xs:string" default="1.0" />
 *     <xs:element minOccurs="0" name="RequestParams">
 *       <xs:complexType>
 *         <xs:sequence>
 *           <xs:element maxOccurs="unbounded" minOccurs="0" name="Parameter" type="sap:ParamType" />
 *         </xs:sequence>
 *       </xs:complexType>
 *     </xs:element>
 *   </xs:sequence>
 *   <xs:attribute name="ID" type="xs:ID" use="required" />
 * </xs:complexType>}
 * </pre>
 *
 * @author Martin Lindström (martin@idsec.se)
 */
public interface SADRequest extends SAMLObject {

  /** Name of the element. */
  String DEFAULT_ELEMENT_LOCAL_NAME = "SADRequest";

  /** Default element name. */
  QName DEFAULT_ELEMENT_NAME = new QName(SAPConstants.SAP_NS, DEFAULT_ELEMENT_LOCAL_NAME, SAPConstants.SAP_NS_PREFIX);

  /** Local name of the type */
  String TYPE_LOCAL_NAME = "SADRequestType";

  /** QName of the XSI type. */
  QName TYPE_NAME = new QName(SAPConstants.SAP_NS, TYPE_LOCAL_NAME, SAPConstants.SAP_NS_PREFIX);

  /** Name of the RequesterID element. */
  String REQUESTER_ID_LOCAL_NAME = "RequesterID";

  /** Name of the SignRequestID element. */
  String SIGN_REQUEST_ID_LOCAL_NAME = "SignRequestID";

  /** Name of the DocCount element. */
  String DOC_COUNT_LOCAL_NAME = "DocCount";

  /** Name of the RequestedVersion element. */
  String REQUESTED_VERSION_LOCAL_NAME = "RequestedVersion";

  /** ID attribute name. */
  public static final String ID_ATTRIB_NAME = "ID";

  /**
   * Returns the ID attribute of this {@code SADRequest}.
   *
   * @return the ID of this SAD request
   */
  String getID();

  /**
   * Assigns the ID of this {@code SADRequest}.
   *
   * @param id the ID of this SAD request
   */
  public void setID(final String id);

  /**
   * Returns the requester ID (entityID of the SP requesting the SAD).
   *
   * @return the entityID of the requester
   */
  String getRequesterID();

  /**
   * Assigns the requester ID (entityID of the SP requesting the SAD).
   *
   * @param requesterID the entityID of the requester
   */
  void setRequesterID(final String requesterID);

  /**
   * Returns the value of the {@code RequestID} attribute of the associated {@code SignRequest}.
   *
   * @return the signature request ID
   */
  String getSignRequestID();

  /**
   * Assigns the value of the {@code RequestID} attribute of the associated {@code SignRequest}.
   *
   * @param signRequestID the signature request ID
   */
  void setSignRequestID(final String signRequestID);

  /**
   * Returns the number of requested signatures in the associated sign request.
   *
   * @return the document count
   */
  Integer getDocCount();

  /**
   * Assigns the number of requested signatures in the associated sign request.
   *
   * @param docCount the document count
   */
  void setDocCount(final Integer docCount);

  /**
   * Returns the requested version of the SAD.
   *
   * @return the SAD version
   */
  SADVersion getRequestedVersion();

  /**
   * Assigns the requested version of the SAD.
   *
   * @param sadVersion the SAD version
   */
  void setRequestedVersion(final SADVersion sadVersion);

  /**
   * Returns the {@code RequestParams} element.
   *
   * @return the RequestParams, or null
   */
  RequestParams getRequestParams();

  /**
   * Assigns the {@code RequestParams} element.
   *
   * @param requestParams the RequestParams
   */
  void setRequestParams(final RequestParams requestParams);

}
