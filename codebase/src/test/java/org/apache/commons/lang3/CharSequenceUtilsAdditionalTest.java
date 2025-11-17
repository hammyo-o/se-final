package org.apache.commons.lang3;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Additional boundary value tests for CharSequenceUtils.
 * Generated to improve code coverage.
 */
public class CharSequenceUtilsAdditionalTest {

    @Test
    public void testSubSequenceWithNull() {
        assertNull("Null input should return null", CharSequenceUtils.subSequence(null, 0));
    }
    
    @Test
    public void testSubSequenceEmptyString() {
        CharSequence result = CharSequenceUtils.subSequence("", 0);
        assertNotNull("Empty string should not return null", result);
        assertEquals("Empty string subsequence should be empty", 0, result.length());
    }
    
    @Test
    public void testSubSequenceValidRange() {
        CharSequence input = "Hello World";
        CharSequence result = CharSequenceUtils.subSequence(input, 6);
        assertEquals("Subsequence should start at index 6", "World", result.toString());
    }
    
    @Test
    public void testSubSequenceAtEnd() {
        CharSequence input = "Test";
        CharSequence result = CharSequenceUtils.subSequence(input, 4);
        assertNotNull("Result should not be null", result);
        assertEquals("Subsequence at end should be empty", 0, result.length());
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void testSubSequenceOutOfBounds() {
        CharSequenceUtils.subSequence("Test", 10);
    }
    
    @Test
    public void testConstructor() {
        CharSequenceUtils utils = new CharSequenceUtils();
        assertNotNull("Constructor should create instance", utils);
    }
}
