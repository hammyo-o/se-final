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

import java.io.IOException;
import java.io.Writer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
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
    assertNull("translate(null) should return null", translator.translate(null));

    // 2. Test empty string input
    assertEquals("translate(\"\") should return an empty string", "", translator.translate(""));

    // 3. Test string with characters to translate
    assertEquals("A string with a matching character should be translated", "The quick BARrown fox", translator.translate("The quick brown fox"));

    // 4. Test string with a single character to translate
    assertEquals("A single 'a' should be translated to 'FOO'", "FOO", translator.translate("a"));

    // 5. Test a more complex string with translations at the beginning, middle, and end
    assertEquals("Characters at various positions should be translated correctly", "FOO c BAR d FOO", translator.translate("a c b d a"));

    // 6. Test a string with adjacent characters to translate
    assertEquals("Adjacent translatable characters should be processed sequentially", "FOOBARc", translator.translate("abc"));

    // 7. Test a string that mixes translatable and non-translatable characters
    assertEquals("A single character within a larger string should be translated",
        "The quick BARrown fox jumps over the lFOOzy dog.",
        translator.translate("The quick brown fox jumps over the lazy dog."));
}

    @Test
    public void translateShouldCorrectlyProcessVariousInputs() {
        // Setup a concrete translator to test the abstract parent's orchestration logic.
        // Using a LookupTranslator is a standard way to exercise the abstract class.
        final CharSequenceTranslator translator =
                new LookupTranslator(new CharSequence[][]{{"one", "uno"}, {"two", "dos"}, {"&", "&amp;"}});

        // 1. Test null input
        assertNull("Null input should return null as per the method contract", translator.translate(null));

        // 2. Test empty input
        assertEquals("Empty CharSequence should result in an empty String", "", translator.translate(""));

        // 3. Test input with no characters that need translation
        assertEquals("CharSequence with no matching lookup values should be returned unchanged", "three four", translator.translate("three four"));

        // 4. Test input with characters that need translation
        assertEquals("CharSequence with matching lookup values should be translated", "uno dos", translator.translate("one two"));

        // 5. Test input with a mix of translatable and non-translatable characters
        assertEquals("CharSequence with mixed values should be translated correctly", "uno &amp; dos", translator.translate("one & two"));

        // 6. Test with a different CharSequence implementation (StringBuilder)
        assertEquals("Should correctly handle different CharSequence implementations like StringBuilder", "uno", translator.translate(new StringBuilder("one")));
    }

    @Test
    public void testTranslateSurrogatePairs() throws IOException {
        // Setup: Create a concrete CharSequenceTranslator that handles surrogate pairs.
        final CharSequenceTranslator surrogateTranslator = new CharSequenceTranslator() {
            @Override
            public int translate(final CharSequence input, final int index, final Writer out) throws IOException {
                if (Character.isHighSurrogate(input.charAt(index))) {
                    // A very basic surrogate pair handler for testing purposes
                    out.write("SURROGATE");
                    return 2; // Consumed 2 chars (the surrogate pair)
                }
                return 0;
            }
        };

        // Unicode surrogate pair for a character outside the BMP (e.g., U+1D400)
        final String surrogatePairString = "\uD835\uDC00";
        assertEquals("A surrogate pair should be translated correctly", "SURROGATE", surrogateTranslator.translate(surrogatePairString));
    }

    @Test(expected = RuntimeException.class)
    public void testTranslateWithIOException() throws IOException {
        final CharSequenceTranslator translator = new CharSequenceTranslator() {
            @Override
            public int translate(final CharSequence input, final int index, final Writer out) throws IOException {
                throw new IOException("Test Exception");
            }
        };
        translator.translate("test");
    }
}
