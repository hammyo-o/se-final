package org.apache.commons.lang3;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Additional boundary value tests for CharSequenceUtils.
 * Tests target uncovered branches in indexOf, lastIndexOf, toCharArray, and regionMatches.
 */
public class CharSequenceUtilsAdditionalTest {

    /**
     * Test indexOf with non-String CharSequence (StringBuilder) to exercise else branch
     */
    @Test
    public void testIndexOfWithNonString() {
        StringBuilder sb = new StringBuilder("hello world");
        int result = CharSequenceUtils.indexOf(sb, 'o', 0);
        assertEquals(4, result);
        
        // Test not found
        result = CharSequenceUtils.indexOf(sb, 'x', 0);
        assertEquals(-1, result);
        
        // Test with negative start (should treat as 0)
        result = CharSequenceUtils.indexOf(sb, 'h', -5);
        assertEquals(0, result);
    }
    
    /**
     * Test lastIndexOf with non-String CharSequence to exercise else branch
     */
    @Test
    public void testLastIndexOfWithNonString() {
        StringBuilder sb = new StringBuilder("hello world");
        int result = CharSequenceUtils.lastIndexOf(sb, 'o', 10);
        assertEquals(7, result);
        
        // Test with negative start (should return -1)
        result = CharSequenceUtils.lastIndexOf(sb, 'o', -1);
        assertEquals(-1, result);
        
        // Test with start beyond length (should adjust to length - 1)
        result = CharSequenceUtils.lastIndexOf(sb, 'o', 100);
        assertEquals(7, result);
        
        // Test not found
        result = CharSequenceUtils.lastIndexOf(sb, 'x', 10);
        assertEquals(-1, result);
    }
    
    /**
     * Test toCharArray with non-String CharSequence
     */
    @Test
    public void testToCharArrayWithNonString() {
        StringBuilder sb = new StringBuilder("test");
        char[] result = CharSequenceUtils.toCharArray(sb);
        assertArrayEquals(new char[]{'t', 'e', 's', 't'}, result);
        
        // Test empty
        sb = new StringBuilder("");
        result = CharSequenceUtils.toCharArray(sb);
        assertEquals(0, result.length);
    }
    
    /**
     * Test regionMatches with non-String CharSequences and ignoreCase=true
     */
    @Test
    public void testRegionMatchesWithNonStringIgnoreCase() {
        StringBuilder cs = new StringBuilder("Hello World");
        StringBuilder substring = new StringBuilder("WORLD");
        
        // Test case-insensitive match
        boolean result = CharSequenceUtils.regionMatches(cs, true, 6, substring, 0, 5);
        assertTrue(result);
        
        // Test case-insensitive no match
        result = CharSequenceUtils.regionMatches(cs, true, 0, substring, 0, 5);
        assertFalse(result);
    }
    
    /**
     * Test regionMatches with non-String CharSequences and ignoreCase=false
     */
    @Test
    public void testRegionMatchesWithNonStringCaseSensitive() {
        StringBuilder cs = new StringBuilder("Hello World");
        StringBuilder substring = new StringBuilder("World");
        
        // Test case-sensitive match
        boolean result = CharSequenceUtils.regionMatches(cs, false, 6, substring, 0, 5);
        assertTrue(result);
        
        // Test case-sensitive no match due to case
        substring = new StringBuilder("world");
        result = CharSequenceUtils.regionMatches(cs, false, 6, substring, 0, 5);
        assertFalse(result);
    }
    
    /**
     * Test regionMatches where characters match after uppercase/lowercase conversion
     */
    @Test
    public void testRegionMatchesCaseConversion() {
        StringBuilder cs = new StringBuilder("Test");
        StringBuilder substring = new StringBuilder("test");
        
        // Should match with ignoreCase=true
        boolean result = CharSequenceUtils.regionMatches(cs, true, 0, substring, 0, 4);
        assertTrue(result);
        
        // Should not match with ignoreCase=false
        result = CharSequenceUtils.regionMatches(cs, false, 0, substring, 0, 4);
        assertFalse(result);
    }
    
    /**
     * Test indexOf with CharSequence parameter (calls toString internally)
     */
    @Test
    public void testIndexOfCharSequence() {
        String cs = "hello world";
        String searchChar = "world";
        int result = CharSequenceUtils.indexOf(cs, searchChar, 0);
        assertEquals(6, result);
        
        // Test with non-String CharSequence
        StringBuilder sb = new StringBuilder("hello world");
        StringBuilder search = new StringBuilder("world");
        result = CharSequenceUtils.indexOf(sb, search, 0);
        assertEquals(6, result);
    }
    
    /**
     * Test lastIndexOf with CharSequence parameter
     */
    @Test
    public void testLastIndexOfCharSequence() {
        String cs = "hello world world";
        String searchChar = "world";
        int result = CharSequenceUtils.lastIndexOf(cs, searchChar, 20);
        assertEquals(12, result);
        
        // Test with non-String CharSequence
        StringBuilder sb = new StringBuilder("hello world world");
        StringBuilder search = new StringBuilder("world");
        result = CharSequenceUtils.lastIndexOf(sb, search, 20);
        assertEquals(12, result);
    }
}
