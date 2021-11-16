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
package se.swedenconnect.opensaml.sweid.saml2.signservice.sap;

/**
 * A type safe SAD version enumeration.
 * 
 * @author Martin Lindström (martin@idsec.se)
 */
public class SADVersion {

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
   * @param major
   *          SAD major version number
   * @param minor
   *          SAD minor version number
   */
  private SADVersion(final int major, final int minor) {
    this.majorVersion = major;
    this.minorVersion = minor;

    this.versionString = this.majorVersion + "." + this.minorVersion;
  }

  /**
   * Gets the SADVersion given the major and minor version number.
   * 
   * @param majorVersion
   *          major version number
   * @param minorVersion
   *          minor version number
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
   * @param version
   *          SAD version string
   * 
   * @return SADVersion for the given string
   */
  public static SADVersion valueOf(final String version) {
    String[] components = version.split("\\.");
    return valueOf(Integer.valueOf(components[0]), Integer.valueOf(components[1]));
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
  public String toString() {
    return this.versionString;
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
