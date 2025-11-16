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
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.io.Writer;

import org.junit.Test;

/**
 * Unit tests for {@link org.apache.commons.lang3.text.translate.CharSequenceTranslator}.
 */
public class CharSequenceTranslatorTest {

    @Test
    public void testTranslateNull() throws IOException {
        final CharSequenceTranslator translator = new LookupTranslator(new CharSequence[][] { { "one", "two" } });
        assertNull("Null input should return null", translator.translate(null));
    }

    @Test
    public void testTranslateEmpty() throws IOException {
        final CharSequenceTranslator translator = new LookupTranslator(new CharSequence[][] { { "one", "two" } });
        assertEquals("Empty input should return empty", "", translator.translate(""));
    }

    @Test
    public void testTranslateNoMatch() throws IOException {
        final CharSequenceTranslator translator = new LookupTranslator(new CharSequence[][] { { "one", "two" } });
        assertEquals("No match should return original", "three", translator.translate("three"));
    }

    @Test
    public void testTranslateMatch() throws IOException {
        final CharSequenceTranslator translator = new LookupTranslator(new CharSequence[][] { { "one", "two" } });
        assertEquals("Should translate 'one' to 'two'", "two", translator.translate("one"));
    }

    @Test
    public void testTranslateStringBuilder() throws IOException {
        final CharSequenceTranslator translator = new LookupTranslator(new CharSequence[][] { { "one", "two" } });
        final StringBuilder input = new StringBuilder("one");
        assertEquals("Should translate StringBuilder", "two", translator.translate(input));
    }

    @Test
    public void translateShouldHandleVariousScenarios() throws IOException {
        // Setup: Create a concrete CharSequenceTranslator for testing.
        // This implementation translates the character 'a' to the string "FOO"
        // and the character 'b' to "BAR".
        final CharSequenceTranslator translator = new CharSequenceTranslator() {
            @Override
            public int translate(final CharSequence input, final int index, final Writer out) throws IOException {
            if (input.charAt(index) == 'a') {
                out.write("FOO");
                return 1; // Consumed 1 char
            }
            if (input.charAt(index) == 'b') {
                out.write("BAR");
                return 1; // Consumed 1 char
            }
            // Let the framework know that we haven't translated, so it can copy the original char.
            return 0;
        }
    };

    // 1. Test null input
    assertNull(translator.translate(null), "translate(null) should return null");

    // 2. Test empty string input
    assertEquals("", translator.translate(""), "translate(\"\") should return an empty string");

    // 3. Test string with no characters to translate
    assertEquals("The quick brown fox", translator.translate("The quick brown fox"), 
        "A string with no matching characters should remain unchanged");

    // 4. Test string with a single character to translate
    assertEquals("FOO", translator.translate("a"), "A single 'a' should be translated to 'FOO'");

    // 5. Test a more complex string with translations at the beginning, middle, and end
    assertEquals("FOO c BAR d FOO", translator.translate("a c b d a"), 
        "Characters at various positions should be translated correctly");

    // 6. Test a string with adjacent characters to translate
    assertEquals("FOOBARc", translator.translate("abc"), 
        "Adjacent translatable characters should be processed sequentially");

    // 7. Test a string that mixes translatable and non-translatable characters
    assertEquals("The quick brown fox jumps over the lFOOzy dog.", 
        translator.translate("The quick brown fox jumps over the lazy dog."),
        "A single character within a larger string should be translated");
}

    @Test
    void testTranslate() {
        // Setup a concrete translator for testing that replaces the character 'a' with the string "z"
        final CharSequenceTranslator translator = new CharSequenceTranslator() {
            @Override
            public int translate(final CharSequence input, final int index, final Writer out) throws IOException {
                if (input.charAt(index) == 'a') {
                    out.write("z");
                    return 1; // 1 character consumed
                }
                return 0; // 0 characters consumed
            }
        };

        // Test: Basic translation where a match is found
        assertEquals("zbc", translator.translate("abc"), "Should translate a matching character");

        // Test: No translation needed
        assertEquals("xyz", translator.translate("xyz"), "Should return the original string if no translation occurs");

        // Test: Multiple translations in one string
        assertEquals("zbcz", translator.translate("abac"), "Should translate all occurrences");

        // Test: Translation at the beginning and end of the string
        assertEquals("z z", translator.translate("a a"), "Should handle matches at the start and end of the string");
        
        // Test: Empty input string
        assertEquals("", translator.translate(""), "Should return an empty string for an empty input");

        // Test: Null input
        assertNull(translator.translate(null), "Should return null for a null input");
    }
}
