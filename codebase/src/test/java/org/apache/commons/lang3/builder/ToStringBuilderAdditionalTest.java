package org.apache.commons.lang3.builder;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Additional tests for ToStringBuilder to improve coverage.
 * Tests static methods and edge cases.
 */
public class ToStringBuilderAdditionalTest {

    private static class TestObject {
        @SuppressWarnings("unused")
        private String name = "test";
        @SuppressWarnings("unused")
        private int value = 42;
    }

    @Test
    public void testReflectionToStringWithSuperclass() {
        TestObject obj = new TestObject();
        // Test reflectionToString with upToClass parameter
        String result = ToStringBuilder.reflectionToString(obj, ToStringStyle.DEFAULT_STYLE, false, Object.class);
        assertNotNull(result);
        assertTrue(result.contains("TestObject"));
    }
    
    @Test
    public void testReflectionToStringAllVariants() {
        TestObject obj = new TestObject();
        
        // Test with null style (should use default)
        String result1 = ToStringBuilder.reflectionToString(obj, null);
        assertNotNull(result1);
        
        // Test with explicit style and transients
        String result2 = ToStringBuilder.reflectionToString(obj, ToStringStyle.SIMPLE_STYLE, true);
        assertNotNull(result2);
        
        // Test with style and upToClass
        String result3 = ToStringBuilder.reflectionToString(obj, ToStringStyle.MULTI_LINE_STYLE, true, Object.class);
        assertNotNull(result3);
    }
    
    @Test
    public void testDefaultStyleGetterSetter() {
        ToStringStyle originalStyle = ToStringBuilder.getDefaultStyle();
        assertNotNull(originalStyle);
        
        // Set new default style
        ToStringBuilder.setDefaultStyle(ToStringStyle.SIMPLE_STYLE);
        assertEquals(ToStringStyle.SIMPLE_STYLE, ToStringBuilder.getDefaultStyle());
        
        // Restore original
        ToStringBuilder.setDefaultStyle(originalStyle);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testSetDefaultStyleNull() {
        ToStringBuilder.setDefaultStyle(null);
    }
    
    @Test
    public void testAppendSuperToString() {
        TestObject obj = new TestObject();
        ToStringBuilder builder = new ToStringBuilder(obj);
        
        // Append null super toString
        builder.appendSuper(null);
        String result = builder.toString();
        assertNotNull(result);
        
        // Append empty super toString
        builder.appendSuper("");
        result = builder.toString();
        assertNotNull(result);
        
        // Append valid super toString
        builder.appendSuper("SuperClass[field=value]");
        result = builder.toString();
        assertTrue(result.contains("SuperClass"));
    }
    
    @Test
    public void testAppendToString() {
        TestObject obj = new TestObject();
        ToStringBuilder builder = new ToStringBuilder(obj);
        
        // Append null toString
        builder.appendToString(null);
        String result = builder.toString();
        assertNotNull(result);
        
        // Append empty toString
        builder.appendToString("");
        result = builder.toString();
        assertNotNull(result);
        
        // Append valid toString
        builder.appendToString("OtherClass[data=123]");
        result = builder.toString();
        assertTrue(result.contains("OtherClass"));
    }
    
    @Test
    public void testAppendObjectWithName() {
        TestObject obj = new TestObject();
        ToStringBuilder builder = new ToStringBuilder(obj);
        
        // Test named field appends with Object type (avoiding ambiguity)
        builder.append("field1", (Object)"value1");
        builder.append("field2", (Object)null);
        builder.append("field3", (Object)Integer.valueOf(123));
        
        String result = builder.toString();
        assertNotNull(result);
        assertTrue(result.contains("field1"));
    }
    
    @Test
    public void testGetSetStyle() {
        TestObject obj = new TestObject();
        ToStringBuilder builder = new ToStringBuilder(obj);
        
        // Get initial style
        ToStringStyle style = builder.getStyle();
        assertNotNull(style);
        
        // Get object
        Object retrieved = builder.getObject();
        assertSame(obj, retrieved);
    }
    
    @Test
    public void testBuild() {
        TestObject obj = new TestObject();
        ToStringBuilder builder = new ToStringBuilder(obj);
        builder.append("name", "test");
        
        // Test build() method (from Builder interface)
        String result = builder.build();
        assertNotNull(result);
        assertTrue(result.contains("test"));
    }
    
    @Test
    public void testAppendPrimitiveValues() {
        TestObject obj = new TestObject();
        ToStringBuilder builder = new ToStringBuilder(obj);
        
        // Test named field appends with primitives (not arrays to avoid ambiguity)
        builder.append("intVal", (Object)Integer.valueOf(42));
        builder.append("longVal", (Object)Long.valueOf(100L));
        builder.append("boolVal", (Object)Boolean.valueOf(true));
        builder.append("doubleVal", (Object)Double.valueOf(3.14));
        
        String result = builder.toString();
        assertNotNull(result);
        assertTrue(result.contains("intVal"));
    }
    
    @Test
    public void testConstructorWithStyle() {
        TestObject obj = new TestObject();
        ToStringBuilder builder = new ToStringBuilder(obj, ToStringStyle.SIMPLE_STYLE);
        builder.append("field", "value");
        
        String result = builder.toString();
        assertNotNull(result);
        // Simple style shouldn't include class name formatting
        assertTrue(result.length() > 0);
    }
    
    @Test
    public void testConstructorWithStyleAndStringBuffer() {
        TestObject obj = new TestObject();
        StringBuffer buffer = new StringBuffer();
        ToStringBuilder builder = new ToStringBuilder(obj, ToStringStyle.DEFAULT_STYLE, buffer);
        builder.append("test", 123);
        
        String result = builder.toString();
        assertNotNull(result);
        // The buffer should have been used
        assertTrue(buffer.length() > 0);
    }
    
    @Test
    public void testAppendNamedArrays() {
        TestObject obj = new TestObject();
        ToStringBuilder builder = new ToStringBuilder(obj);
        
        // Test append with field names and arrays
        builder.append("booleans", new boolean[]{true, false});
        builder.append("bytes", new byte[]{1, 2, 3});
        builder.append("chars", new char[]{'a', 'b', 'c'});
        builder.append("doubles", new double[]{1.1, 2.2});
        builder.append("floats", new float[]{1.1f, 2.2f});
        builder.append("ints", new int[]{10, 20, 30});
        builder.append("longs", new long[]{100L, 200L});
        builder.append("shorts", new short[]{(short)1, (short)2});
        builder.append("objects", new Object[]{"str1", "str2"});
        
        String result = builder.toString();
        assertNotNull(result);
        assertTrue(result.contains("booleans"));
    }
    
    @Test
    public void testAppendNamedArraysWithFullDetail() {
        TestObject obj = new TestObject();
        ToStringBuilder builder = new ToStringBuilder(obj);
        
        // Test append with field names, arrays, and fullDetail flag
        builder.append("boolArray", new boolean[]{true}, false);
        builder.append("byteArray", new byte[]{1}, true);
        builder.append("charArray", new char[]{'x'}, false);
        builder.append("doubleArray", new double[]{1.5}, true);
        builder.append("floatArray", new float[]{2.5f}, false);
        builder.append("intArray", new int[]{42}, true);
        builder.append("longArray", new long[]{999L}, false);
        builder.append("shortArray", new short[]{(short)5}, true);
        builder.append("objArray", new Object[]{"test"}, false);
        
        String result = builder.toString();
        assertNotNull(result);
    }
}
