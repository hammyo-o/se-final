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

package org.apache.commons.lang3.text.translate;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit tests for {@link org.apache.commons.lang3.text.translate.UnicodeEscaper}.
 * @version $Id$
 */
public class UnicodeEscaperTest  {

    @Test
    public void testBelow() {
        final UnicodeEscaper ue = UnicodeEscaper.below('F');

        final String input = "ADFGZ";
        final String result = ue.translate(input);
        assertEquals("Failed to escape Unicode characters via the below method", "\\u0041\\u0044FGZ", result);
    }

    @Test
    public void testBetween() {
        final UnicodeEscaper ue = UnicodeEscaper.between('F', 'L');

        final String input = "ADFGZ";
        final String result = ue.translate(input);
        assertEquals("Failed to escape Unicode characters via the between method", "AD\\u0046\\u0047Z", result);
    }

    @Test
    public void testAbove() {
        final UnicodeEscaper ue = UnicodeEscaper.above('F');

        final String input = "ADFGZ";
        final String result = ue.translate(input);
        assertEquals("Failed to escape Unicode characters via the above method", "ADF\\u0047\\u005A", result);
    }

    import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.lang.reflect.Constructor;
import org.apache.commons.text.translate.UnicodeEscaper;

class UnicodeEscaperTest { // Outer class is needed for the @Test method.
    @Test
    void testNoArgsConstructorIsAbsent() {
        // The UnicodeEscaper class from Apache Commons Text does not have a public or protected
        // no-argument constructor (<init>()V). Attempting to access it via reflection
        // is expected to throw a NoSuchMethodException.
        assertThrows(NoSuchMethodException.class, () -> {
            Constructor<UnicodeEscaper> constructor = UnicodeEscaper.class.getDeclaredConstructor();
            // If a no-argument constructor somehow existed (unexpectedly),
            // further logic would be needed to test its behavior.
            // For now, asserting its absence is the correct test for "<init>()V" for this class.
        }, "UnicodeEscaper should not have a no-argument constructor.");
    }
}
}
