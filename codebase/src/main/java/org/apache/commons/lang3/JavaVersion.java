/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.lang3;

/**
 * <p>An enum representing all the versions of the Java specification.
 * This is intended to mirror available values from the
 * <em>java.specification.version</em> System property. </p>
 *
 * @since 3.0
 * @version $Id: $
 */
public enum JavaVersion {
    
    /**
     * The Java version reported by Android. This is not an official Java version number.
     */
    JAVA_0_9(1.5f, "0.9"),
    
    /**
     * Java 1.1.
     */
    JAVA_1_1(1.1f, "1.1"),

    /**
     * Java 1.2.
     */
    JAVA_1_2(1.2f, "1.2"),

    /**
     * Java 1.3.
     */
    JAVA_1_3(1.3f, "1.3"),

    /**
     * Java 1.4.
     */
    JAVA_1_4(1.4f, "1.4"),

    /**
     * Java 1.5.
     */
    JAVA_1_5(1.5f, "1.5"),

    /**
     * Java 1.6.
     */
    JAVA_1_6(1.6f, "1.6"),

    /**
     * Java 1.7.
     */
    JAVA_1_7(1.7f, "1.7"),

    /**
     * Java 1.8.
     */
    JAVA_1_8(1.8f, "1.8"),

    /**
     * Java 1.8.
     *
     * @deprecated As of 3.5, replaced by {@link #JAVA_1_8}
     */
    @Deprecated
    JAVA_EIGHT(1.8f, "1.8"),

    /**
     * Java 9.
     * @since 3.5
     */
    JAVA_9(9.0f, "9"),

    /**
     * Java 10.
     * @since 3.7
     */
    JAVA_10(10.0f, "10"),

    /**
     * Java 11.
     * @since 3.8
     */
    JAVA_11(11.0f, "11"),

    /**
     * Java 12.
     * @since 3.9
     */
    JAVA_12(12.0f, "12"),

    /**
     * Java 13.
     * @since 3.9
     */
    JAVA_13(13.0f, "13"),

    /**
     * Java 25.
     * @since 3.10
     */
    JAVA_25(25.0f, "25"),

    /**
     * The most recent java version.
     * @since 3.9
     */
    JAVA_RECENT(26.0f, "25.0");

    /**
     * The float value.
     */
    private final float value;
    /**
     * The standard name.
     */
    private final String name;

    /**
     * Constructor.
     *
     * @param value  the float value
     * @param name  the standard name, not null
     */
    JavaVersion(final float value, final String name) {
        this.value = value;
        this.name = name;
    }

    //-----------------------------------------------------------------------
    /**
     * <p>Whether this version of Java is at least the version of Java passed in.</p>
     *
     * <p>For example:<br />
     *  {@code myVersion.atLeast(JavaVersion.JAVA_1_4)}<p>
     *
     * @param requiredVersion  the version to check against, not null
     * @return true if this version is equal to or greater than the specified version
     */
    public boolean atLeast(final JavaVersion requiredVersion) {
        return this.value >= requiredVersion.value;
    }

    /**
     * Transforms the given string with a Java version number to the
     * corresponding constant of this enumeration class. This method is used
     * internally.
     *
     * @param nom the Java version as string
     * @return the corresponding enumeration constant or <b>null</b> if the
     * version is unknown
     */
    // helper for static importing
    static JavaVersion getJavaVersion(final String nom) {
        return get(nom);
    }

    /**
     * Transforms the given string with a Java version number to the
     * corresponding constant of this enumeration class. This method is used
     * internally.
     *
     * @param nom the Java version as string
     * @return the corresponding enumeration constant or <b>null</b> if the
     * version is unknown
     */
    static JavaVersion get(final String nom) {
        if (nom == null) {
            return null;
        }
        if (nom.startsWith("0.9")) {
            return JAVA_0_9;
        }
        if (nom.startsWith("1.1")) {
            return JAVA_1_1;
        }
        if (nom.startsWith("1.2")) {
            return JAVA_1_2;
        }
        if (nom.startsWith("1.3")) {
            return JAVA_1_3;
        }
        if (nom.startsWith("1.4")) {
            return JAVA_1_4;
        }
        if (nom.startsWith("1.5")) {
            return JAVA_1_5;
        }
        if (nom.startsWith("1.6")) {
            return JAVA_1_6;
        }
        if (nom.startsWith("1.7")) {
            return JAVA_1_7;
        }
        if (nom.startsWith("1.8")) {
            return JAVA_1_8;
        }
        if (nom.startsWith("1.8")) {
            return JAVA_1_8;
        }
        if (nom.startsWith("9")) {
            return JAVA_9;
        }
        if (nom.startsWith("10")) {
            return JAVA_10;
        }
        if (nom.startsWith("11")) {
            return JAVA_11;
        }
        if (nom.startsWith("12")) {
            return JAVA_12;
        }
        if (nom.startsWith("13")) {
            return JAVA_13;
        }
        if (nom.startsWith("25")) {
            return JAVA_25;
        }
        final int firstDot = nom.indexOf('.');
        if (firstDot > -1) {
            final String majorVersion = nom.substring(0, firstDot);
            if (Integer.parseInt(majorVersion) >= 9) {
                return JAVA_RECENT;
            }
        }
        return null;
    }

    //-----------------------------------------------------------------------
    /**
     * <p>The string value is overridden to return the standard name.</p>
     *
     * <p>For example, <code>"1.5"</code>.</p>
     *
     * @return the name, not null
     */
    @Override
    public String toString() {
        return name;
    }

}
