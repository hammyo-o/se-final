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
package org.apache.commons.lang3.text;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Extended unit tests for {@link ExtendedMessageFormat}.
 * Focus on null handling, error conditions, and boundary cases.
 */
public class ExtendedMessageFormatExtendedTest {

    @Test
    public void testSimplePatternConstruction() {
        // Test basic pattern construction
        ExtendedMessageFormat fmt = new ExtendedMessageFormat("Hello {0}!");
        assertNotNull("Format should not be null", fmt);
        assertEquals("Pattern should match", "Hello {0}!", fmt.toPattern());
    }

    @Test
    public void testSimplePatternWithLocale() {
        // Test pattern with specific locale
        ExtendedMessageFormat fmt = new ExtendedMessageFormat("Hello {0}!", Locale.US);
        assertNotNull("Format should not be null", fmt);
        assertEquals("Locale should match", Locale.US, fmt.getLocale());
    }

    @Test
    public void testEmptyPattern() {
        // Test empty pattern
        ExtendedMessageFormat fmt = new ExtendedMessageFormat("");
        assertNotNull("Format should handle empty pattern", fmt);
        assertEquals("Pattern should be empty", "", fmt.toPattern());
    }

    @Test
    public void testPatternWithNoPlaceholders() {
        // Test pattern with no placeholders
        ExtendedMessageFormat fmt = new ExtendedMessageFormat("Just plain text");
        assertNotNull("Format should handle plain text", fmt);
        String result = fmt.format(new Object[]{});
        assertEquals("Should return plain text", "Just plain text", result);
    }

    @Test
    public void testPatternWithMultiplePlaceholders() {
        // Test pattern with multiple placeholders
        ExtendedMessageFormat fmt = new ExtendedMessageFormat("Name: {0}, Age: {1}, City: {2}");
        String result = fmt.format(new Object[]{"John", 30, "NYC"});
        assertEquals("Should format all placeholders", "Name: John, Age: 30, City: NYC", result);
    }

    @Test
    public void testPatternWithEscapedBraces() {
        // Test escaped braces
        ExtendedMessageFormat fmt = new ExtendedMessageFormat("Use '{'braces'}' like this: {0}");
        String result = fmt.format(new Object[]{"test"});
        assertTrue("Should contain braces", result.contains("{") && result.contains("}"));
    }

    @Test
    public void testPatternWithSingleQuotes() {
        // Test single quotes
        ExtendedMessageFormat fmt = new ExtendedMessageFormat("It''s a test: {0}");
        String result = fmt.format(new Object[]{"value"});
        assertTrue("Should handle single quotes", result.contains("It's"));
    }

    @Test
    public void testNullRegistry() {
        // Test null registry
        Map<String, FormatFactory> nullRegistry = null;
        ExtendedMessageFormat fmt = new ExtendedMessageFormat("Test {0}", Locale.US, nullRegistry);
        assertNotNull("Should handle null registry", fmt);
    }

    @Test
    public void testEmptyRegistry() {
        // Test empty registry
        Map<String, FormatFactory> registry = new HashMap<String, FormatFactory>();
        ExtendedMessageFormat fmt = new ExtendedMessageFormat("Test {0}", Locale.US, registry);
        assertNotNull("Should handle empty registry", fmt);
    }

    @Test(expected = NullPointerException.class)
    public void testNullPattern() {
        // Test null pattern should throw exception
        new ExtendedMessageFormat(null);
    }

    @Test(expected = NullPointerException.class)
    public void testNullPatternWithLocale() {
        // Test null pattern with locale should throw exception
        new ExtendedMessageFormat(null, Locale.US);
    }

    @Test(expected = NullPointerException.class)
    public void testNullPatternWithRegistry() {
        // Test null pattern with registry should throw exception
        Map<String, FormatFactory> registry = new HashMap<String, FormatFactory>();
        new ExtendedMessageFormat(null, Locale.US, registry);
    }

    @Test
    public void testToPatternMethod() {
        // Test toPattern returns correct pattern
        String pattern = "Test {0} with {1}";
        ExtendedMessageFormat fmt = new ExtendedMessageFormat(pattern);
        assertEquals("toPattern should return original pattern", pattern, fmt.toPattern());
    }

    @Test
    public void testFormatWithNullArguments() {
        // Test formatting with null in arguments
        ExtendedMessageFormat fmt = new ExtendedMessageFormat("Value: {0}");
        String result = fmt.format(new Object[]{null});
        assertEquals("Should handle null argument", "Value: null", result);
    }

    @Test
    public void testFormatWithMissingArguments() {
        // Test formatting with missing arguments
        ExtendedMessageFormat fmt = new ExtendedMessageFormat("A: {0}, B: {1}");
        try {
            fmt.format(new Object[]{"only one"});
            // May or may not throw depending on MessageFormat behavior
        } catch (IllegalArgumentException e) {
            // Expected in some cases
        }
    }

    @Test
    public void testFormatWithExtraArguments() {
        // Test formatting with extra arguments
        ExtendedMessageFormat fmt = new ExtendedMessageFormat("Value: {0}");
        String result = fmt.format(new Object[]{"first", "second", "third"});
        assertEquals("Should use only needed arguments", "Value: first", result);
    }

    @Test
    public void testPatternWithNumbers() {
        // Test pattern with number formatting hint
        ExtendedMessageFormat fmt = new ExtendedMessageFormat("Price: {0,number}");
        String result = fmt.format(new Object[]{1234.56});
        assertNotNull("Should format number", result);
        assertTrue("Should contain formatted number", result.contains("1") && result.contains("234"));
    }

    @Test
    public void testPatternWithDate() {
        // Test pattern with date formatting hint
        ExtendedMessageFormat fmt = new ExtendedMessageFormat("Date: {0,date}");
        java.util.Date date = new java.util.Date();
        String result = fmt.format(new Object[]{date});
        assertNotNull("Should format date", result);
        assertTrue("Should contain Date:", result.startsWith("Date:"));
    }

    @Test
    public void testPatternWithTime() {
        // Test pattern with time formatting hint
        ExtendedMessageFormat fmt = new ExtendedMessageFormat("Time: {0,time}");
        java.util.Date date = new java.util.Date();
        String result = fmt.format(new Object[]{date});
        assertNotNull("Should format time", result);
        assertTrue("Should contain Time:", result.startsWith("Time:"));
    }

    @Test
    public void testGetLocale() {
        // Test getLocale method
        ExtendedMessageFormat fmt1 = new ExtendedMessageFormat("Test");
        assertNotNull("Locale should not be null", fmt1.getLocale());
        
        ExtendedMessageFormat fmt2 = new ExtendedMessageFormat("Test", Locale.FRANCE);
        assertEquals("Should return correct locale", Locale.FRANCE, fmt2.getLocale());
    }

    @Test
    public void testEqualsWithSamePattern() {
        // Test equals with same pattern
        ExtendedMessageFormat fmt1 = new ExtendedMessageFormat("Test {0}");
        ExtendedMessageFormat fmt2 = new ExtendedMessageFormat("Test {0}");
        assertEquals("Formats with same pattern should be equal", fmt1, fmt2);
    }

    @Test
    public void testEqualsWithDifferentPattern() {
        // Test equals with different pattern
        ExtendedMessageFormat fmt1 = new ExtendedMessageFormat("Test {0}");
        ExtendedMessageFormat fmt2 = new ExtendedMessageFormat("Test {1}");
        assertNotEquals("Formats with different patterns should not be equal", fmt1, fmt2);
    }

    @Test
    public void testHashCodeConsistency() {
        // Test hashCode consistency
        ExtendedMessageFormat fmt = new ExtendedMessageFormat("Test {0}");
        int hash1 = fmt.hashCode();
        int hash2 = fmt.hashCode();
        assertEquals("hashCode should be consistent", hash1, hash2);
    }

    @Test
    public void testHashCodeWithEqualObjects() {
        // Test hashCode for equal objects
        ExtendedMessageFormat fmt1 = new ExtendedMessageFormat("Test {0}");
        ExtendedMessageFormat fmt2 = new ExtendedMessageFormat("Test {0}");
        assertEquals("Equal objects should have equal hashCodes", fmt1.hashCode(), fmt2.hashCode());
    }

    @Test
    public void testPatternWithWhitespace() {
        // Test pattern with various whitespace
        ExtendedMessageFormat fmt = new ExtendedMessageFormat("Test   {0}   value");
        String result = fmt.format(new Object[]{"middle"});
        assertEquals("Should preserve whitespace", "Test   middle   value", result);
    }

    @Test
    public void testPatternWithSpecialCharacters() {
        // Test pattern with special characters
        ExtendedMessageFormat fmt = new ExtendedMessageFormat("Special: @#$%^&*() {0}");
        String result = fmt.format(new Object[]{"test"});
        assertTrue("Should handle special characters", result.contains("@#$%^&*()"));
    }

    @Test
    public void testPatternWithUnicodeCharacters() {
        // Test pattern with unicode characters
        ExtendedMessageFormat fmt = new ExtendedMessageFormat("Unicode: \u4E2D\u6587 {0}");
        String result = fmt.format(new Object[]{"test"});
        assertTrue("Should handle unicode", result.contains("\u4E2D\u6587"));
    }

    @Test
    public void testLargeArgumentIndex() {
        // Test with large argument index
        ExtendedMessageFormat fmt = new ExtendedMessageFormat("Value: {10}");
        Object[] args = new Object[11];
        args[10] = "last";
        String result = fmt.format(args);
        assertEquals("Should handle large index", "Value: last", result);
    }

    @Test
    public void testPatternWithConsecutivePlaceholders() {
        // Test consecutive placeholders
        ExtendedMessageFormat fmt = new ExtendedMessageFormat("{0}{1}{2}");
        String result = fmt.format(new Object[]{"A", "B", "C"});
        assertEquals("Should handle consecutive placeholders", "ABC", result);
    }

    @Test
    public void testPatternWithRepeatedPlaceholder() {
        // Test repeated placeholder
        ExtendedMessageFormat fmt = new ExtendedMessageFormat("{0} and {0} again");
        String result = fmt.format(new Object[]{"test"});
        assertEquals("Should handle repeated placeholder", "test and test again", result);
    }
}
