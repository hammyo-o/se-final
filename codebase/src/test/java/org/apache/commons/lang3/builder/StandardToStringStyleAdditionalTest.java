package org.apache.commons.lang3.builder;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Additional tests for StandardToStringStyle to improve coverage.
 * Tests getter/setter methods and configuration options.
 */
public class StandardToStringStyleAdditionalTest {

    @Test
    public void testUseClassNameGetterSetter() {
        StandardToStringStyle style = new StandardToStringStyle();
        
        // Test default value
        assertTrue(style.isUseClassName());
        
        // Test setter
        style.setUseClassName(false);
        assertFalse(style.isUseClassName());
        
        style.setUseClassName(true);
        assertTrue(style.isUseClassName());
    }
    
    @Test
    public void testUseShortClassNameGetterSetter() {
        StandardToStringStyle style = new StandardToStringStyle();
        
        // Test default value
        assertFalse(style.isUseShortClassName());
        
        // Test setter
        style.setUseShortClassName(true);
        assertTrue(style.isUseShortClassName());
        
        style.setUseShortClassName(false);
        assertFalse(style.isUseShortClassName());
    }
    
    @Test
    public void testUseIdentityHashCodeGetterSetter() {
        StandardToStringStyle style = new StandardToStringStyle();
        
        // Test default value
        assertTrue(style.isUseIdentityHashCode());
        
        // Test setter
        style.setUseIdentityHashCode(false);
        assertFalse(style.isUseIdentityHashCode());
        
        style.setUseIdentityHashCode(true);
        assertTrue(style.isUseIdentityHashCode());
    }
    
    @Test
    public void testUseFieldNamesGetterSetter() {
        StandardToStringStyle style = new StandardToStringStyle();
        
        // Test default value
        assertTrue(style.isUseFieldNames());
        
        // Test setter
        style.setUseFieldNames(false);
        assertFalse(style.isUseFieldNames());
        
        style.setUseFieldNames(true);
        assertTrue(style.isUseFieldNames());
    }
    
    @Test
    public void testArrayContentDetailGetterSetter() {
        StandardToStringStyle style = new StandardToStringStyle();
        
        // Test default value
        assertTrue(style.isArrayContentDetail());
        
        // Test setter
        style.setArrayContentDetail(false);
        assertFalse(style.isArrayContentDetail());
        
        style.setArrayContentDetail(true);
        assertTrue(style.isArrayContentDetail());
    }
    
    @Test
    public void testDefaultFullDetailGetterSetter() {
        StandardToStringStyle style = new StandardToStringStyle();
        
        // Test default value
        assertTrue(style.isDefaultFullDetail());
        
        // Test setter
        style.setDefaultFullDetail(false);
        assertFalse(style.isDefaultFullDetail());
        
        style.setDefaultFullDetail(true);
        assertTrue(style.isDefaultFullDetail());
    }
}
