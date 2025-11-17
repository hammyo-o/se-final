package org.apache.commons.lang3.text;

import static org.junit.Assert.*;

import java.text.ChoiceFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.Test;

/**
 * Additional boundary value tests for ExtendedMessageFormat.
 * Targets uncovered branches in readArgumentIndex, appendQuotedString, parseFormatDescription.
 */
public class ExtendedMessageFormatAdditionalTest {

    /**
     * Test malformed patterns with invalid argument indices
     * Targets readArgumentIndex() uncovered branches
     */
    @Test
    public void testMalformedArgumentIndices() {
        // Test empty argument index
        try {
            new ExtendedMessageFormat("{ }");
            fail("Should throw IllegalArgumentException for empty argument index");
        } catch (IllegalArgumentException e) {
            // Expected
        }
        
        // Test non-numeric argument index
        try {
            new ExtendedMessageFormat("{abc}");
            fail("Should throw IllegalArgumentException for non-numeric argument index");
        } catch (IllegalArgumentException e) {
            // Expected
        }
        
        // Test negative argument index
        try {
            new ExtendedMessageFormat("{-1}");
            fail("Should throw IllegalArgumentException for negative argument index");
        } catch (IllegalArgumentException e) {
            // Expected
        }
        
        // Test argument index with leading zeros - parsed as 0
        ExtendedMessageFormat fmt = new ExtendedMessageFormat("{00}", (Map<String, FormatFactory>) null);
        assertEquals("Message with leading zero index should parse as {0}", "{0}", fmt.toPattern());
    }

    /**
     * Test patterns with various quote escaping scenarios
     * Targets appendQuotedString() uncovered branches
     */
    @Test
    public void testQuotedStringEscaping() {
        // Test single quote escaping
        ExtendedMessageFormat fmt = new ExtendedMessageFormat("It''s a test", (Map<String, FormatFactory>) null);
        Object[] args = {};
        String result = fmt.format(args);
        assertEquals("Single quotes should be escaped", "It's a test", result);
        
        // Test multiple consecutive quotes
        fmt = new ExtendedMessageFormat("Test '''' quotes", (Map<String, FormatFactory>) null);
        result = fmt.format(args);
        assertEquals("Multiple quotes should be handled", "Test '' quotes", result);
        
        // Test quotes at the end
        fmt = new ExtendedMessageFormat("End with quote''", (Map<String, FormatFactory>) null);
        result = fmt.format(args);
        assertEquals("Quote at end should be escaped", "End with quote'", result);
        
        // Test unmatched quote (gets removed in parsing)
        fmt = new ExtendedMessageFormat("Literal 'quote", (Map<String, FormatFactory>) null);
        result = fmt.format(args);
        assertEquals("Unmatched quote gets removed", "Literal quote", result);
    }

    /**
     * Test patterns with whitespace around format specifiers
     * Targets parseFormatDescription() and seekNonWs() uncovered branches
     */
    @Test
    public void testWhitespaceHandling() {
        // Test whitespace after format name
        ExtendedMessageFormat fmt = new ExtendedMessageFormat("{0,number }", (Map<String, FormatFactory>) null);
        Object[] args = {123.456};
        assertNotNull("Whitespace after format should be handled", fmt.format(args));
        
        // Test whitespace in format style
        fmt = new ExtendedMessageFormat("{0,number, integer}", (Map<String, FormatFactory>) null);
        assertNotNull("Whitespace in format style should be handled", fmt.format(args));
        
        // Test whitespace after comma in format style
        fmt = new ExtendedMessageFormat("{0,number,integer }", (Map<String, FormatFactory>) null);
        assertNotNull("Whitespace after style should be handled", fmt.format(args));
    }

    /**
     * Test empty and null patterns
     * Targets boundary conditions in applyPattern()
     */
    @Test
    public void testEmptyAndNullPatterns() {
        // Test empty pattern
        ExtendedMessageFormat fmt = new ExtendedMessageFormat("", (Map<String, FormatFactory>) null);
        Object[] args = {};
        assertEquals("Empty pattern should work", "", fmt.format(args));
        
        // Test null registry
        fmt = new ExtendedMessageFormat("Test {0}", (Map<String, FormatFactory>) null);
        Object[] args2 = {"value"};
        assertEquals("Null registry should work", "Test value", fmt.format(args2));
    }

    /**
     * Test patterns with nested and complex format structures
     * Targets equals() and complex parsing branches
     */
    @Test
    public void testComplexFormatPatterns() {
        // Test with custom format factory
        Map<String, FormatFactory> registry = new HashMap<String, FormatFactory>();
        registry.put("upper", new FormatFactory() {
            @Override
            public Format getFormat(String name, String arguments, Locale locale) {
                return new Format() {
                    @Override
                    public StringBuffer format(Object obj, StringBuffer toAppendTo, java.text.FieldPosition pos) {
                        return toAppendTo.append(obj.toString().toUpperCase());
                    }
                    @Override
                    public Object parseObject(String source, java.text.ParsePosition pos) {
                        return source;
                    }
                };
            }
        });
        
        ExtendedMessageFormat fmt = new ExtendedMessageFormat("{0,upper}", registry);
        Object[] args = {"hello"};
        String result = fmt.format(args);
        assertEquals("Custom format should work", "HELLO", result);
        
        // Test with format style
        fmt = new ExtendedMessageFormat("{0,upper,style}", registry);
        result = fmt.format(args);
        assertEquals("Custom format with style should work", "HELLO", result);
    }

    /**
     * Test equals() method edge cases
     * Targets equals() uncovered branches
     */
    @Test
    public void testEqualsMethod() {
        ExtendedMessageFormat fmt1 = new ExtendedMessageFormat("Test {0}", (Map<String, FormatFactory>) null);
        ExtendedMessageFormat fmt2 = new ExtendedMessageFormat("Test {0}", (Map<String, FormatFactory>) null);
        ExtendedMessageFormat fmt3 = new ExtendedMessageFormat("Test {1}", (Map<String, FormatFactory>) null);
        
        // Test equals with same pattern
        assertTrue("Same patterns should be equal", fmt1.equals(fmt2));
        
        // Test equals with different pattern
        assertFalse("Different patterns should not be equal", fmt1.equals(fmt3));
        
        // Test equals with null
        assertFalse("Should not equal null", fmt1.equals(null));
        
        // Test equals with different type
        assertFalse("Should not equal different type", fmt1.equals("Test {0}"));
        
        // Test equals with registry
        Map<String, FormatFactory> registry = new HashMap<String, FormatFactory>();
        ExtendedMessageFormat fmt4 = new ExtendedMessageFormat("Test {0}", registry);
        ExtendedMessageFormat fmt5 = new ExtendedMessageFormat("Test {0}", registry);
        assertTrue("Same pattern with same registry should be equal", fmt4.equals(fmt5));
    }

    /**
     * Test malformed format patterns
     * Targets error handling branches
     */
    @Test
    public void testMalformedFormatPatterns() {
        // Test unclosed brace
        try {
            new ExtendedMessageFormat("{0");
            fail("Should throw IllegalArgumentException for unclosed brace");
        } catch (IllegalArgumentException e) {
            // Expected
        }
        
        // Test unmatched closing brace (treated as literal)
        ExtendedMessageFormat fmt2 = new ExtendedMessageFormat("Test }", (Map<String, FormatFactory>) null);
        assertNotNull("Unmatched closing brace should be treated as literal", fmt2);
        assertEquals("Closing brace should appear in result", "Test }", fmt2.format(new Object[]{}));
        
        // Test empty format specifier
        ExtendedMessageFormat fmt = new ExtendedMessageFormat("{0,}", (Map<String, FormatFactory>) null);
        // Should not throw, format name can be empty
        assertNotNull("Empty format name should be allowed", fmt);
    }

    /**
     * Test toPattern() method
     * Targets getFormat() and insertFormats() branches
     */
    @Test
    public void testToPattern() {
        String pattern = "Test {0,number,integer} and {1}";
        ExtendedMessageFormat fmt = new ExtendedMessageFormat(pattern, (Map<String, FormatFactory>) null);
        String result = fmt.toPattern();
        assertNotNull("toPattern should not return null", result);
        assertTrue("toPattern should contain placeholders", result.contains("{0"));
    }

    /**
     * Test hashCode consistency
     * Targets hashCode() method
     */
    @Test
    public void testHashCode() {
        ExtendedMessageFormat fmt1 = new ExtendedMessageFormat("Test {0}", (Map<String, FormatFactory>) null);
        ExtendedMessageFormat fmt2 = new ExtendedMessageFormat("Test {0}", (Map<String, FormatFactory>) null);
        
        assertEquals("Equal objects should have same hashCode", fmt1.hashCode(), fmt2.hashCode());
    }

    /**
     * Test format with various argument types
     * Targets format handling branches
     */
    @Test
    public void testFormatWithVariousTypes() {
        ExtendedMessageFormat fmt = new ExtendedMessageFormat("{0} {1} {2} {3}", (Map<String, FormatFactory>) null);
        Object[] args = {"string", 123, 45.67, null};
        String result = fmt.format(args);
        assertNotNull("Format should handle various types", result);
        assertTrue("Format should include string", result.contains("string"));
        assertTrue("Format should include integer", result.contains("123"));
        assertTrue("Format should include double", result.contains("45.67"));
    }
}
