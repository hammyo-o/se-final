package org.apache.commons.lang3.text.translate;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Unit tests for {@link org.apache.commons.lang3.text.translate.JavaUnicodeEscaper}.
 */
public class JavaUnicodeEscaperTest {

    @Test
    public void testAbove() {
        // Create an escaper that escapes characters with codepoints 127 and above.
        final JavaUnicodeEscaper escaper = JavaUnicodeEscaper.above(127);

        // Test a character below the threshold (should not be escaped)
        final String inputBelow = "\u007E"; // Tilde character (126)
        assertEquals("Character below threshold should not be escaped", inputBelow, escaper.translate(inputBelow));

        // Test a character at the threshold (should be escaped)
        final String inputAt = "\u007F"; // Delete character (127)
        assertEquals("Character at threshold should be escaped", "\\u007F", escaper.translate(inputAt));

        // Test a character above the threshold (should be escaped)
        final String inputAbove = "\u0080"; // Padding character (128)
        assertEquals("Character above threshold should be escaped", "\\u0080", escaper.translate(inputAbove));
    }

    @Test
    public void testBelow() {
        // The below(codepoint) method creates an escaper that translates
        // characters with codepoints from 0 up to and INCLUDING the specified codepoint.
        int boundaryCodepoint = 97; // Represents 'a'

        JavaUnicodeEscaper escaper = JavaUnicodeEscaper.below(boundaryCodepoint);

        // Input string containing:
        // - Character AT the boundary: 'a' (97)
        // - Characters above the boundary: 'b' (98)
        String input = "ab";

        // Expected output:
        // - 'a' (97) -> \u0061 (escaped, as 97 <= 97 is true)
        // - 'b' (98) -> b (not escaped, as 98 > 97)
        String expected = "\\u0061b";

        // Translate the input string
        String actual = escaper.translate(input);

        // Assert that the translation is as expected
        assertEquals("Characters from 0 up to 'a' should be escaped, others not.", expected, actual);
    }

    @Test
    public void testBetween() {
        // Escaper that escapes only characters in the inclusive range [0x0061, 0x0061] (just 'a').
        final JavaUnicodeEscaper escaper = JavaUnicodeEscaper.between(0x0061, 0x0061);

        // Below range (96 '`') should not be escaped
        assertEquals("Below range should not be escaped", "\u0060", escaper.translate("\u0060"));

        // In range (97 'a') should be escaped
        assertEquals("In-range should be escaped", "\\u0061", escaper.translate("\u0061"));

        // Above range (98 'b') should not be escaped
        assertEquals("Above range should not be escaped", "\u0062", escaper.translate("\u0062"));
    }
}
