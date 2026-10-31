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
package se.swedenconnect.opensaml.sweid.saml2.signservice.sap;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A type safe SAD version enumeration.
 *
 * @author Martin Lindström (martin@idsec.se)
 */
public class SADVersion {

  /** Pattern for a version string (major.minor). */
  private static final Pattern VERSION_PATTERN = Pattern.compile("^(\\d+)\\.(\\d+)$");

  /** SAD version 1.0. */
  public static final SADVersion VERSION_10 = new SADVersion(1, 0);

  /** Major version number. */
  private final int majorVersion;

  /** Minor version number. */
  private final int minorVersion;

  /** String representation of the version. */
  private final String versionString;

  /**
   * Constructor.
   *
   * @param major SAD major version number
   * @param minor SAD minor version number
   */
  private SADVersion(final int major, final int minor) {
    this.majorVersion = major;
    this.minorVersion = minor;

    this.versionString = this.majorVersion + "." + this.minorVersion;
  }

  /**
   * Gets the SADVersion given the major and minor version number.
   *
   * @param majorVersion major version number
   * @param minorVersion minor version number
   *
   * @return the SADVersion
   */
  public static SADVersion valueOf(final int majorVersion, final int minorVersion) {
    if (majorVersion == 1) {
      if (minorVersion == 0) {
        return SADVersion.VERSION_10;
      }
    }

    return new SADVersion(majorVersion, minorVersion);
  }

  /**
   * Gets the SADVersion for a given version string, such as "1.0".
   *
   * @param version SAD version string
   * @return SADVersion for the given string
   * @throws IllegalArgumentException if the supplied string is not a valid version string (major.minor)
   */
  public static SADVersion valueOf(final String version) throws IllegalArgumentException {
    final Matcher matcher = version != null ? VERSION_PATTERN.matcher(version.trim()) : null;
    if (matcher == null || !matcher.matches()) {
      throw new IllegalArgumentException("Invalid SAD version: " + version);
    }
    try {
      return valueOf(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
    }
    catch (final NumberFormatException e) {
      throw new IllegalArgumentException("Invalid SAD version: " + version, e);
    }
  }

  /**
   * Gets the major version of the SAD version.
   *
   * @return the major version of the SAD version
   */
  public int getMajorVersion() {
    return this.majorVersion;
  }

  /**
   * Gets the minor version of the SAD version.
   *
   * @return the minor version of the SAD version
   */
  public int getMinorVersion() {
    return this.minorVersion;
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return this.versionString;
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return Objects.hash(this.majorVersion, this.minorVersion);
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    SADVersion other = (SADVersion) obj;
    if (majorVersion != other.majorVersion) {
      return false;
    }
    if (minorVersion != other.minorVersion) {
      return false;
    }
    return true;
  }

}
