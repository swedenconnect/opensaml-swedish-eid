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
package se.swedenconnect.opensaml.sweid.saml2.discovery;

import java.util.Collection;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.swedenconnect.opensaml.sweid.saml2.metadata.entitycategory.EntityCategoryType;

/**
 * A class that implements the Discovery matching rules described in section 1.3 and 1.4 in <a href=
 * "https://docs.swedenconnect.se/technical-framework/latest/ELN-0606_-_Entity_Categories_for_the_Swedish_eID_Framework.html">Entity
 * Categories for the Swedish eID Framework</a>.
 *
 * @author Martin Lindström (martin@idsec.se)
 */
public class SwedishEidDiscoveryMatchingRules {

  /** Class logger. */
  private static final Logger log = LoggerFactory.getLogger(SwedishEidDiscoveryMatchingRules.class);

  /**
   * Determines if there if the consuming service (SP) may use the providing service (IdP) based on the entity
   * categories that they have declared.
   * <p>
   * The algorithm is defined in section 1.4 of <a href=
   * "https://docs.swedenconnect.se/technical-framework/latest/06_-_Entity_Categories_for_the_Swedish_eID_Framework.html">Entity
   * Categories for the Swedish eID Framework</a>. The discovery process SHOULD display Identity Providers as a
   * plausible choice, if and only if, the following conditions apply;
   * </p>
   * <ul>
   * <li>the Identity Provider declares at least of the Service Entity Category identifiers declared by the Service
   * Provider (see {@link #isServiceEntityMatch(Collection, Collection)}),</li>
   * <li>f the Identity Provider declares at least one Service Contract identifier, the Service Provider must declare at
   * least one of declared identifiers ({@link #isServiceContractMatch(Collection, Collection)}), and,</li>
   * <li>all of the Service Property identifiers declared by the Service Provider must be declared by the Identity
   * Provider ({@link #isServicePropertyMatch(Collection, Collection)}).</li>
   * </ul>
   *
   * @param consumingService the entity categories declared by the consuming service (SP)
   * @param providingService the entity categories declared by the providing service (IdP)
   * @return true if the above algorithm evaluates to {@code true} and {@code false} otherwise
   * @see #isServiceEntityMatch(Collection, Collection)
   * @see #isServiceContractMatch(Collection, Collection)
   * @see #isServicePropertyMatch(Collection, Collection)
   */
  public static boolean isMatch(final Collection<String> consumingService, final Collection<String> providingService) {

    // 1. Match service entity categories
    //
    if (!isServiceEntityMatch(consumingService, providingService)) {
      return false;
    }

    // 2. Match service contract identifiers
    //
    if (!isServiceContractMatch(consumingService, providingService)) {
      return false;
    }

    // 3. Service Property matching
    //
    if (!isServicePropertyMatch(consumingService, providingService)) {
      return false;
    }

    return true;
  }

  /**
   * Determines if there is a match regarding service entity categories declared by a consuming service and a providing
   * service. This is the first condition (out of three) of the algorithm defined in section 1.4 of <a href=
   * "https://docs.swedenconnect.se/technical-framework/latest/06_-_Entity_Categories_for_the_Swedish_eID_Framework.html">Entity
   * Categories for the Swedish eID Framework</a>.
   *
   * @param consumingService the entity categories declared by the consuming service (SP)
   * @param providingService the entity categories declared by the providing service (IdP)
   * @return {@code true} if the providing service declares at least one of the service entity categories declared by
   *           the consuming service and {@code false} otherwise
   */
  public static boolean isServiceEntityMatch(final Collection<String> consumingService,
      final Collection<String> providingService) {
    final Collection<String> csServiceEntityCategories = consumingService.stream()
        .filter(c -> EntityCategoryType.isType(c, EntityCategoryType.SERVICE_ENTITY))
        .collect(Collectors.toList());
    if (!csServiceEntityCategories.isEmpty()) {
      final String matchingServiceEntityCategory = csServiceEntityCategories.stream()
          .filter(c -> providingService.contains(c))
          .findFirst()
          .orElse(null);
      if (matchingServiceEntityCategory == null) {
        log.debug(
            "The providing service did not declare any of the service entity categories declared by the consuming service ({}) -> no match",
            csServiceEntityCategories);
        return false;
      }
      log.debug(
          "Consuming service declared {} which is also declared by providing service -> service entity category match",
          matchingServiceEntityCategory);
    }
    else {
      log.debug("No Service entity entity categories declared by the consuming service -> match");
    }
    return true;
  }

  /**
   * Determines if there is a match regarding service contract categories declared by a consuming service and a
   * providing service. This is the second condition (out of threee) of the algorithm defined in section 1.4 of <a href=
   * "https://docs.swedenconnect.se/technical-framework/latest/06_-_Entity_Categories_for_the_Swedish_eID_Framework.html">Entity
   * Categories for the Swedish eID Framework</a>.
   *
   * @param consumingService the entity categories declared by the consuming service (SP)
   * @param providingService the entity categories declared by the providing service (IdP)
   * @return if the providing service declares at least one service contract category and the consuming service declares
   *           at least one of those declared identifiers {@code true} is returned
   */
  public static boolean isServiceContractMatch(final Collection<String> consumingService,
      final Collection<String> providingService) {
    final Collection<String> psServiceContractCategories = providingService.stream()
        .filter(c -> EntityCategoryType.isType(c, EntityCategoryType.SERVICE_CONTRACT))
        .collect(Collectors.toList());
    if (!psServiceContractCategories.isEmpty()) {
      final String matchingServiceContractCategory = psServiceContractCategories.stream()
          .filter(c -> consumingService.contains(c))
          .findFirst()
          .orElse(null);
      if (matchingServiceContractCategory == null) {
        log.debug(
            "The providing service declared service contract category/categories {}. The consuming service did not declare any of these -> no match",
            psServiceContractCategories);
        return false;
      }
      log.debug("Providing service declared {} which is also declared by consuming service -> service contract match",
          matchingServiceContractCategory);
    }
    else {
      log.debug("No service contract categories defined by providing service -> match");
    }
    return true;
  }

  /**
   * Determines if there is a match regarding service property categories declared by a consuming service and a
   * providing service. This is the third condition (out of threee) of the algorithm defined in section 1.4 of <a href=
   * "https://docs.swedenconnect.se/technical-framework/latest/06_-_Entity_Categories_for_the_Swedish_eID_Framework.html">Entity
   * Categories for the Swedish eID Framework</a>.
   *
   * @param consumingService the entity categories declared by the consuming service (SP)
   * @param providingService the entity categories declared by the providing service (IdP)
   * @return if all of the Service Property identifiers declared by the consuming service is declared by the providing
   *           service {@code true} is returned
   */
  public static boolean isServicePropertyMatch(final Collection<String> consumingService,
      final Collection<String> providingService) {
    final Collection<String> csServicePropertyCategories = consumingService.stream()
        .filter(c -> EntityCategoryType.isType(c, EntityCategoryType.SERVICE_PROPERTY))
        .collect(Collectors.toList());
    if (!csServicePropertyCategories.isEmpty()) {
      if (!csServicePropertyCategories.stream().allMatch(c -> providingService.contains(c))) {
        log.debug(
            "Consuming service declared the service property category/categories {} - Not all are defined by providing service -> no match",
            csServicePropertyCategories);
        return false;
      }
      log.debug(
          "Consuming service declared the category/categories {} which are also declared by providing service -> service property match",
          csServicePropertyCategories);
    }
    else {
      log.debug("No service property categories defined by consuming service -> match");
    }
    return true;
  }

}
