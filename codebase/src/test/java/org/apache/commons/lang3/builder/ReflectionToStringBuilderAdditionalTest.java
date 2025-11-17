package org.apache.commons.lang3.builder;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

/**
 * Null safety tests for ReflectionToStringBuilder.
 * Generated to improve code coverage.
 */
public class ReflectionToStringBuilderAdditionalTest {

    private static class TestObject {
        private String field1 = "value1";
        private int field2 = 42;
        private transient String transientField = "transient";
        private static String staticField = "static";
        
        public String getField1() { return field1; }
        public int getField2() { return field2; }
    }

    @Test
    public void testToStringWithNullStyle() {
        TestObject obj = new TestObject();
        String result = ReflectionToStringBuilder.toString(obj, null);
        assertNotNull("Result should not be null even with null style", result);
        assertTrue("Result should contain field values", result.contains("field1"));
    }
    
    @Test
    public void testToStringWithOutputTransients() {
        TestObject obj = new TestObject();
        String result = ReflectionToStringBuilder.toString(obj, null, true);
        assertNotNull("Result should not be null", result);
    }
    
    @Test
    public void testToStringWithOutputStatics() {
        TestObject obj = new TestObject();
        String result = ReflectionToStringBuilder.toString(obj, null, false, true);
        assertNotNull("Result should not be null", result);
    }
    
    @Test
    public void testToStringWithBothTransientsAndStatics() {
        TestObject obj = new TestObject();
        String result = ReflectionToStringBuilder.toString(obj, null, true, true);
        assertNotNull("Result should not be null", result);
    }
    
    @Test
    public void testToStringExcludeWithCollection() {
        TestObject obj = new TestObject();
        Collection<String> excludeFields = Arrays.asList("field1");
        String result = ReflectionToStringBuilder.toStringExclude(obj, excludeFields);
        assertNotNull("Result should not be null", result);
        assertFalse("Result should not contain excluded field", result.contains("value1"));
    }
    
    @Test
    public void testToStringExcludeWithEmptyCollection() {
        TestObject obj = new TestObject();
        Collection<String> excludeFields = Arrays.asList();
        String result = ReflectionToStringBuilder.toStringExclude(obj, excludeFields);
        assertNotNull("Result should not be null", result);
    }
    
    @Test
    public void testToStringExcludeWithVarargs() {
        TestObject obj = new TestObject();
        String result = ReflectionToStringBuilder.toStringExclude(obj, "field1", "field2");
        assertNotNull("Result should not be null", result);
    }
    
    @Test
    public void testToStringExcludeWithNoExclusions() {
        TestObject obj = new TestObject();
        String result = ReflectionToStringBuilder.toStringExclude(obj);
        assertNotNull("Result should not be null", result);
    }
    
    @Test
    public void testToStringWithReflectUpToClass() {
        TestObject obj = new TestObject();
        String result = ReflectionToStringBuilder.toString(obj, null, false, false, TestObject.class);
        assertNotNull("Result should not be null", result);
    }
    
    @Test
    public void testReflectionToStringBuilderConstructor() {
        TestObject obj = new TestObject();
        ReflectionToStringBuilder builder = new ReflectionToStringBuilder(obj);
        assertNotNull("Builder should not be null", builder);
        String result = builder.toString();
        assertNotNull("Result should not be null", result);
    }
    
    @Test
    public void testReflectionToStringBuilderWithStyle() {
        TestObject obj = new TestObject();
        ReflectionToStringBuilder builder = new ReflectionToStringBuilder(obj, ToStringStyle.SHORT_PREFIX_STYLE);
        assertNotNull("Builder should not be null", builder);
        String result = builder.toString();
        assertNotNull("Result should not be null", result);
    }
    
    @Test
    public void testSetAppendTransients() {
        TestObject obj = new TestObject();
        ReflectionToStringBuilder builder = new ReflectionToStringBuilder(obj);
        builder.setAppendTransients(true);
        String result = builder.toString();
        assertNotNull("Result should not be null", result);
    }
    
    @Test
    public void testSetAppendStatics() {
        TestObject obj = new TestObject();
        ReflectionToStringBuilder builder = new ReflectionToStringBuilder(obj);
        builder.setAppendStatics(true);
        String result = builder.toString();
        assertNotNull("Result should not be null", result);
    }
    
    @Test
    public void testSetUpToClass() {
        TestObject obj = new TestObject();
        ReflectionToStringBuilder builder = new ReflectionToStringBuilder(obj);
        builder.setUpToClass(Object.class);
        String result = builder.toString();
        assertNotNull("Result should not be null", result);
    }
    
    @Test
    public void testSetExcludeFieldNames() {
        TestObject obj = new TestObject();
        ReflectionToStringBuilder builder = new ReflectionToStringBuilder(obj);
        builder.setExcludeFieldNames("field1");
        String result = builder.toString();
        assertNotNull("Result should not be null", result);
    }
}
