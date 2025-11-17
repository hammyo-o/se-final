package org.apache.commons.lang3.builder;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Focused tests for ToStringBuilder array methods with field names.
 * These methods were identified as 100% uncovered in JaCoCo reports.
 */
public class ToStringBuilderArrayTest {

    private static class TestBean {
        private String name = "test";
    }

    @Test
    public void testAppendBooleanArrayWithName() {
        TestBean obj = new TestBean();
        ToStringBuilder builder = new ToStringBuilder(obj);
        
        boolean[] array = {true, false, true};
        builder.append("flags", array);
        
        String result = builder.toString();
        assertNotNull(result);
        assertTrue(result.contains("flags"));
    }

    @Test
    public void testAppendBooleanArrayWithNameAndFullDetail() {
        TestBean obj = new TestBean();
        ToStringBuilder builder = new ToStringBuilder(obj);
        
        boolean[] array = {true, false};
        builder.append("values", array, true);
        
        String result = builder.toString();
        assertNotNull(result);
    }

    @Test
    public void testAppendByteArrayWithName() {
        TestBean obj = new TestBean();
        ToStringBuilder builder = new ToStringBuilder(obj);
        
        byte[] array = {1, 2, 3};
        builder.append("bytes", array);
        
        String result = builder.toString();
        assertNotNull(result);
        assertTrue(result.contains("bytes"));
    }

    @Test
    public void testAppendByteArrayWithNameAndFullDetail() {
        TestBean obj = new TestBean();
        ToStringBuilder builder = new ToStringBuilder(obj);
        
        byte[] array = {10, 20};
        builder.append("data", array, false);
        
        String result = builder.toString();
        assertNotNull(result);
    }

    @Test
    public void testAppendCharArrayWithName() {
        TestBean obj = new TestBean();
        ToStringBuilder builder = new ToStringBuilder(obj);
        
        char[] array = {'a', 'b', 'c'};
        builder.append("chars", array);
        
        String result = builder.toString();
        assertNotNull(result);
        assertTrue(result.contains("chars"));
    }

    @Test
    public void testAppendCharArrayWithNameAndFullDetail() {
        TestBean obj = new TestBean();
        ToStringBuilder builder = new ToStringBuilder(obj);
        
        char[] array = {'x', 'y', 'z'};
        builder.append("letters", array, true);
        
        String result = builder.toString();
        assertNotNull(result);
    }

    @Test
    public void testAppendDoubleArrayWithName() {
        TestBean obj = new TestBean();
        ToStringBuilder builder = new ToStringBuilder(obj);
        
        double[] array = {1.1, 2.2, 3.3};
        builder.append("doubles", array);
        
        String result = builder.toString();
        assertNotNull(result);
        assertTrue(result.contains("doubles"));
    }

    @Test
    public void testAppendDoubleArrayWithNameAndFullDetail() {
        TestBean obj = new TestBean();
        ToStringBuilder builder = new ToStringBuilder(obj);
        
        double[] array = {1.5, 2.5};
        builder.append("decimals", array, false);
        
        String result = builder.toString();
        assertNotNull(result);
    }

    @Test
    public void testAppendFloatArrayWithName() {
        TestBean obj = new TestBean();
        ToStringBuilder builder = new ToStringBuilder(obj);
        
        float[] array = {1.1f, 2.2f, 3.3f};
        builder.append("floats", array);
        
        String result = builder.toString();
        assertNotNull(result);
        assertTrue(result.contains("floats"));
    }

    @Test
    public void testAppendFloatArrayWithNameAndFullDetail() {
        TestBean obj = new TestBean();
        ToStringBuilder builder = new ToStringBuilder(obj);
        
        float[] array = {1.5f, 2.5f};
        builder.append("numbers", array, true);
        
        String result = builder.toString();
        assertNotNull(result);
    }

    @Test
    public void testAppendIntArrayWithName() {
        TestBean obj = new TestBean();
        ToStringBuilder builder = new ToStringBuilder(obj);
        
        int[] array = {10, 20, 30};
        builder.append("ints", array);
        
        String result = builder.toString();
        assertNotNull(result);
        assertTrue(result.contains("ints"));
    }

    @Test
    public void testAppendIntArrayWithNameAndFullDetail() {
        TestBean obj = new TestBean();
        ToStringBuilder builder = new ToStringBuilder(obj);
        
        int[] array = {100, 200};
        builder.append("values", array, false);
        
        String result = builder.toString();
        assertNotNull(result);
    }

    @Test
    public void testAppendLongArrayWithName() {
        TestBean obj = new TestBean();
        ToStringBuilder builder = new ToStringBuilder(obj);
        
        long[] array = {100L, 200L, 300L};
        builder.append("longs", array);
        
        String result = builder.toString();
        assertNotNull(result);
        assertTrue(result.contains("longs"));
    }

    @Test
    public void testAppendLongArrayWithNameAndFullDetail() {
        TestBean obj = new TestBean();
        ToStringBuilder builder = new ToStringBuilder(obj);
        
        long[] array = {1000L, 2000L};
        builder.append("ids", array, true);
        
        String result = builder.toString();
        assertNotNull(result);
    }

    @Test
    public void testAppendShortArrayWithName() {
        TestBean obj = new TestBean();
        ToStringBuilder builder = new ToStringBuilder(obj);
        
        short[] array = {(short)1, (short)2, (short)3};
        builder.append("shorts", array);
        
        String result = builder.toString();
        assertNotNull(result);
        assertTrue(result.contains("shorts"));
    }

    @Test
    public void testAppendShortArrayWithNameAndFullDetail() {
        TestBean obj = new TestBean();
        ToStringBuilder builder = new ToStringBuilder(obj);
        
        short[] array = {(short)10, (short)20};
        builder.append("values", array, false);
        
        String result = builder.toString();
        assertNotNull(result);
    }

    @Test
    public void testAppendObjectArrayWithName() {
        TestBean obj = new TestBean();
        ToStringBuilder builder = new ToStringBuilder(obj);
        
        Object[] array = {"string1", "string2", Integer.valueOf(42)};
        builder.append("objects", array);
        
        String result = builder.toString();
        assertNotNull(result);
        assertTrue(result.contains("objects"));
    }

    @Test
    public void testAppendObjectArrayWithNameAndFullDetail() {
        TestBean obj = new TestBean();
        ToStringBuilder builder = new ToStringBuilder(obj);
        
        Object[] array = {"test1", "test2"};
        builder.append("items", array, true);
        
        String result = builder.toString();
        assertNotNull(result);
    }
}
