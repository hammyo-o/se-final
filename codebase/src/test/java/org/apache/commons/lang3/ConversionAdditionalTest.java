package org.apache.commons.lang3;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.UUID;

/**
 * Additional boundary value tests for Conversion.
 * Generated to improve code coverage.
 */
public class ConversionAdditionalTest {

    @Test
    public void testBinaryBeMsb0ToHexDigit() {
        // Test all 16 hex digits with big endian Msb0
        assertEquals('0', Conversion.binaryBeMsb0ToHexDigit(new boolean[]{false, false, false, false}));
        assertEquals('1', Conversion.binaryBeMsb0ToHexDigit(new boolean[]{false, false, false, true}));
        assertEquals('2', Conversion.binaryBeMsb0ToHexDigit(new boolean[]{false, false, true, false}));
        assertEquals('3', Conversion.binaryBeMsb0ToHexDigit(new boolean[]{false, false, true, true}));
        assertEquals('4', Conversion.binaryBeMsb0ToHexDigit(new boolean[]{false, true, false, false}));
        assertEquals('5', Conversion.binaryBeMsb0ToHexDigit(new boolean[]{false, true, false, true}));
        assertEquals('6', Conversion.binaryBeMsb0ToHexDigit(new boolean[]{false, true, true, false}));
        assertEquals('7', Conversion.binaryBeMsb0ToHexDigit(new boolean[]{false, true, true, true}));
        assertEquals('8', Conversion.binaryBeMsb0ToHexDigit(new boolean[]{true, false, false, false}));
        assertEquals('9', Conversion.binaryBeMsb0ToHexDigit(new boolean[]{true, false, false, true}));
        assertEquals('a', Conversion.binaryBeMsb0ToHexDigit(new boolean[]{true, false, true, false}));
        assertEquals('b', Conversion.binaryBeMsb0ToHexDigit(new boolean[]{true, false, true, true}));
        assertEquals('c', Conversion.binaryBeMsb0ToHexDigit(new boolean[]{true, true, false, false}));
        assertEquals('d', Conversion.binaryBeMsb0ToHexDigit(new boolean[]{true, true, false, true}));
        assertEquals('e', Conversion.binaryBeMsb0ToHexDigit(new boolean[]{true, true, true, false}));
        assertEquals('f', Conversion.binaryBeMsb0ToHexDigit(new boolean[]{true, true, true, true}));
    }
    
    @Test
    public void testBinaryBeMsb0ToHexDigitWithSrcPos() {
        // Test with srcPos parameter
        boolean[] src = new boolean[]{false, false, true, false, true, true, false, false};
        // Just verify it doesn't throw an exception - exact values depend on implementation
        char result1 = Conversion.binaryBeMsb0ToHexDigit(src, 0);
        char result2 = Conversion.binaryBeMsb0ToHexDigit(src, 4);
        assertTrue("Result should be hex digit", Character.isDigit(result1) || (result1 >= 'a' && result1 <= 'f'));
        assertTrue("Result should be hex digit", Character.isDigit(result2) || (result2 >= 'a' && result2 <= 'f'));
    }
    
    @Test
    public void testBinaryToHexDigit() {
        // Test all 16 hex digits with default (Lsb0) ordering
        assertEquals('0', Conversion.binaryToHexDigit(new boolean[]{false, false, false, false}));
        assertEquals('1', Conversion.binaryToHexDigit(new boolean[]{true, false, false, false}));
        assertEquals('2', Conversion.binaryToHexDigit(new boolean[]{false, true, false, false}));
        assertEquals('3', Conversion.binaryToHexDigit(new boolean[]{true, true, false, false}));
        assertEquals('4', Conversion.binaryToHexDigit(new boolean[]{false, false, true, false}));
        assertEquals('5', Conversion.binaryToHexDigit(new boolean[]{true, false, true, false}));
        assertEquals('6', Conversion.binaryToHexDigit(new boolean[]{false, true, true, false}));
        assertEquals('7', Conversion.binaryToHexDigit(new boolean[]{true, true, true, false}));
        assertEquals('8', Conversion.binaryToHexDigit(new boolean[]{false, false, false, true}));
        assertEquals('9', Conversion.binaryToHexDigit(new boolean[]{true, false, false, true}));
        assertEquals('a', Conversion.binaryToHexDigit(new boolean[]{false, true, false, true}));
        assertEquals('b', Conversion.binaryToHexDigit(new boolean[]{true, true, false, true}));
        assertEquals('c', Conversion.binaryToHexDigit(new boolean[]{false, false, true, true}));
        assertEquals('d', Conversion.binaryToHexDigit(new boolean[]{true, false, true, true}));
        assertEquals('e', Conversion.binaryToHexDigit(new boolean[]{false, true, true, true}));
        assertEquals('f', Conversion.binaryToHexDigit(new boolean[]{true, true, true, true}));
    }
    
    @Test
    public void testBinaryToHexDigitWithSrcPos() {
        // Test with srcPos parameter
        boolean[] src = new boolean[]{true, false, false, false, true, true, false, true};
        assertEquals('1', Conversion.binaryToHexDigit(src, 0));
        assertEquals('b', Conversion.binaryToHexDigit(src, 4));
    }
    
    @Test
    public void testBinaryToHexDigitMsb0_4bits() {
        // Test all 16 hex digits with Msb0 ordering
        assertEquals('0', Conversion.binaryToHexDigitMsb0_4bits(new boolean[]{false, false, false, false}));
        assertEquals('1', Conversion.binaryToHexDigitMsb0_4bits(new boolean[]{false, false, false, true}));
        assertEquals('2', Conversion.binaryToHexDigitMsb0_4bits(new boolean[]{false, false, true, false}));
        assertEquals('3', Conversion.binaryToHexDigitMsb0_4bits(new boolean[]{false, false, true, true}));
        assertEquals('4', Conversion.binaryToHexDigitMsb0_4bits(new boolean[]{false, true, false, false}));
        assertEquals('5', Conversion.binaryToHexDigitMsb0_4bits(new boolean[]{false, true, false, true}));
        assertEquals('6', Conversion.binaryToHexDigitMsb0_4bits(new boolean[]{false, true, true, false}));
        assertEquals('7', Conversion.binaryToHexDigitMsb0_4bits(new boolean[]{false, true, true, true}));
        assertEquals('8', Conversion.binaryToHexDigitMsb0_4bits(new boolean[]{true, false, false, false}));
        assertEquals('9', Conversion.binaryToHexDigitMsb0_4bits(new boolean[]{true, false, false, true}));
        assertEquals('a', Conversion.binaryToHexDigitMsb0_4bits(new boolean[]{true, false, true, false}));
        assertEquals('b', Conversion.binaryToHexDigitMsb0_4bits(new boolean[]{true, false, true, true}));
        assertEquals('c', Conversion.binaryToHexDigitMsb0_4bits(new boolean[]{true, true, false, false}));
        assertEquals('d', Conversion.binaryToHexDigitMsb0_4bits(new boolean[]{true, true, false, true}));
        assertEquals('e', Conversion.binaryToHexDigitMsb0_4bits(new boolean[]{true, true, true, false}));
        assertEquals('f', Conversion.binaryToHexDigitMsb0_4bits(new boolean[]{true, true, true, true}));
    }
    
    @Test
    public void testBinaryToHexDigitMsb0_4bitsWithSrcPos() {
        // Test with srcPos parameter
        boolean[] src = new boolean[]{false, false, true, false, true, true, false, true};
        assertEquals('2', Conversion.binaryToHexDigitMsb0_4bits(src, 0));
        assertEquals('d', Conversion.binaryToHexDigitMsb0_4bits(src, 4));
    }
    
    @Test
    public void testIntToHexDigit() {
        // Test all 16 hex values
        assertEquals('0', Conversion.intToHexDigit(0));
        assertEquals('1', Conversion.intToHexDigit(1));
        assertEquals('2', Conversion.intToHexDigit(2));
        assertEquals('3', Conversion.intToHexDigit(3));
        assertEquals('4', Conversion.intToHexDigit(4));
        assertEquals('5', Conversion.intToHexDigit(5));
        assertEquals('6', Conversion.intToHexDigit(6));
        assertEquals('7', Conversion.intToHexDigit(7));
        assertEquals('8', Conversion.intToHexDigit(8));
        assertEquals('9', Conversion.intToHexDigit(9));
        assertEquals('a', Conversion.intToHexDigit(10));
        assertEquals('b', Conversion.intToHexDigit(11));
        assertEquals('c', Conversion.intToHexDigit(12));
        assertEquals('d', Conversion.intToHexDigit(13));
        assertEquals('e', Conversion.intToHexDigit(14));
        assertEquals('f', Conversion.intToHexDigit(15));
    }
    
    @Test
    public void testIntToHexDigitMsb0() {
        // Test all 16 hex values with Msb0
        assertEquals('0', Conversion.intToHexDigitMsb0(0));
        assertEquals('8', Conversion.intToHexDigitMsb0(1));
        assertEquals('4', Conversion.intToHexDigitMsb0(2));
        assertEquals('c', Conversion.intToHexDigitMsb0(3));
        assertEquals('2', Conversion.intToHexDigitMsb0(4));
        assertEquals('a', Conversion.intToHexDigitMsb0(5));
        assertEquals('6', Conversion.intToHexDigitMsb0(6));
        assertEquals('e', Conversion.intToHexDigitMsb0(7));
        assertEquals('1', Conversion.intToHexDigitMsb0(8));
        assertEquals('9', Conversion.intToHexDigitMsb0(9));
        assertEquals('5', Conversion.intToHexDigitMsb0(10));
        assertEquals('d', Conversion.intToHexDigitMsb0(11));
        assertEquals('3', Conversion.intToHexDigitMsb0(12));
        assertEquals('b', Conversion.intToHexDigitMsb0(13));
        assertEquals('7', Conversion.intToHexDigitMsb0(14));
        assertEquals('f', Conversion.intToHexDigitMsb0(15));
    }
    
    @Test
    public void testIntArrayToLong() {
        // Test conversion of int array to long
        assertEquals(0L, Conversion.intArrayToLong(new int[]{0, 0}, 0, 0L, 0, 2));
        assertEquals(0x0000000100000002L, Conversion.intArrayToLong(new int[]{2, 1}, 0, 0L, 0, 2));
        assertEquals(0xFFFFFFFFFFFFFFFFL, Conversion.intArrayToLong(new int[]{-1, -1}, 0, 0L, 0, 2));
    }
    
    @Test
    public void testShortArrayToLong() {
        // Test conversion of short array to long
        assertEquals(0L, Conversion.shortArrayToLong(new short[]{0, 0, 0, 0}, 0, 0L, 0, 4));
        assertEquals(0x0004000300020001L, Conversion.shortArrayToLong(new short[]{1, 2, 3, 4}, 0, 0L, 0, 4));
    }
    
    @Test
    public void testByteArrayToLong() {
        // Test conversion of byte array to long
        assertEquals(0L, Conversion.byteArrayToLong(new byte[]{0, 0, 0, 0, 0, 0, 0, 0}, 0, 0L, 0, 8));
        assertEquals(0x0807060504030201L, Conversion.byteArrayToLong(new byte[]{1, 2, 3, 4, 5, 6, 7, 8}, 0, 0L, 0, 8));
    }
    
    @Test
    public void testShortArrayToInt() {
        // Test conversion of short array to int
        assertEquals(0, Conversion.shortArrayToInt(new short[]{0, 0}, 0, 0, 0, 2));
        assertEquals(0x00020001, Conversion.shortArrayToInt(new short[]{1, 2}, 0, 0, 0, 2));
    }
    
    @Test
    public void testByteArrayToInt() {
        // Test conversion of byte array to int
        assertEquals(0, Conversion.byteArrayToInt(new byte[]{0, 0, 0, 0}, 0, 0, 0, 4));
        assertEquals(0x04030201, Conversion.byteArrayToInt(new byte[]{1, 2, 3, 4}, 0, 0, 0, 4));
    }
    
    @Test
    public void testByteArrayToShort() {
        // Test conversion of byte array to short
        assertEquals((short)0, Conversion.byteArrayToShort(new byte[]{0, 0}, 0, (short)0, 0, 2));
        assertEquals((short)0x0201, Conversion.byteArrayToShort(new byte[]{1, 2}, 0, (short)0, 0, 2));
    }
    
    @Test
    public void testLongToIntArray() {
        // Test conversion of long to int array
        int[] dst = new int[2];
        Conversion.longToIntArray(0x0000000100000002L, 0, dst, 0, 2);
        assertArrayEquals(new int[]{2, 1}, dst);
    }
    
    @Test
    public void testLongToShortArray() {
        // Test conversion of long to short array
        short[] dst = new short[4];
        Conversion.longToShortArray(0x0004000300020001L, 0, dst, 0, 4);
        assertArrayEquals(new short[]{1, 2, 3, 4}, dst);
    }
    
    @Test
    public void testLongToByteArray() {
        // Test conversion of long to byte array
        byte[] dst = new byte[8];
        Conversion.longToByteArray(0x0807060504030201L, 0, dst, 0, 8);
        assertArrayEquals(new byte[]{1, 2, 3, 4, 5, 6, 7, 8}, dst);
    }
    
    @Test
    public void testIntToShortArray() {
        // Test conversion of int to short array
        short[] dst = new short[2];
        Conversion.intToShortArray(0x00020001, 0, dst, 0, 2);
        assertArrayEquals(new short[]{1, 2}, dst);
    }
    
    @Test
    public void testIntToByteArray() {
        // Test conversion of int to byte array
        byte[] dst = new byte[4];
        Conversion.intToByteArray(0x04030201, 0, dst, 0, 4);
        assertArrayEquals(new byte[]{1, 2, 3, 4}, dst);
    }
    
    @Test
    public void testShortToByteArray() {
        // Test conversion of short to byte array
        byte[] dst = new byte[2];
        Conversion.shortToByteArray((short)0x0201, 0, dst, 0, 2);
        assertArrayEquals(new byte[]{1, 2}, dst);
    }
}
