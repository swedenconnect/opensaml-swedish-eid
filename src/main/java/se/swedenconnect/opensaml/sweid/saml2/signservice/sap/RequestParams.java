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
package se.swedenconnect.opensaml.sweid.saml2.signservice.sap;

import java.util.List;

import javax.xml.namespace.QName;

import org.opensaml.saml.common.SAMLObject;

/**
 * Representation of the {@code RequestParam} element.
 *
 * @author Martin Lindström (martin@idsec.se)
 */
public interface RequestParams extends SAMLObject {

  /** Name of the element. */
  String DEFAULT_ELEMENT_LOCAL_NAME = "RequestParams";

  /** Default element name. */
  QName DEFAULT_ELEMENT_NAME = new QName(SAPConstants.SAP_NS, DEFAULT_ELEMENT_LOCAL_NAME, SAPConstants.SAP_NS_PREFIX);

  /**
   * Returns a reference to the list of parameters.
   *
   * @return a list of parameters
   */
  List<Parameter> getParameters();

}
