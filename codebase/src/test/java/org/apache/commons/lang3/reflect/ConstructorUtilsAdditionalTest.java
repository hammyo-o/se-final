package org.apache.commons.lang3.reflect;

import static org.junit.Assert.*;
import org.junit.Test;
import java.lang.reflect.Constructor;

/**
 * Additional tests for ConstructorUtils targeting uncovered branches.
 * Focuses on error paths, null handling, and accessibility checks.
 */
public class ConstructorUtilsAdditionalTest {

    public static class PublicClass {
        public PublicClass() {
        }
        
        public PublicClass(String arg) {
        }
        
        public PublicClass(int arg) {
        }
    }
    
    /**
     * Test invokeConstructor with null arguments array
     */
    @Test
    public void testInvokeConstructorWithNullArgs() throws Exception {
        PublicClass result = ConstructorUtils.invokeConstructor(PublicClass.class, (Object[])null);
        assertNotNull(result);
    }
    
    /**
     * Test invokeConstructor with null parameter types
     */
    @Test
    public void testInvokeConstructorWithNullParamTypes() throws Exception {
        PublicClass result = ConstructorUtils.invokeConstructor(PublicClass.class, new Object[]{}, null);
        assertNotNull(result);
    }
    
    /**
     * Test invokeConstructor when no matching constructor exists
     */
    @Test
    public void testInvokeConstructorNoMatch() {
        try {
            ConstructorUtils.invokeConstructor(PublicClass.class, new Object[]{new Double(1.0)}, new Class<?>[]{Double.class});
            fail("Should throw NoSuchMethodException");
        } catch (NoSuchMethodException e) {
            assertTrue(e.getMessage().contains("No such accessible constructor"));
        } catch (Exception e) {
            fail("Wrong exception type: " + e.getClass().getName());
        }
    }
    
    /**
     * Test invokeExactConstructor with null arguments
     */
    @Test
    public void testInvokeExactConstructorWithNullArgs() throws Exception {
        PublicClass result = ConstructorUtils.invokeExactConstructor(PublicClass.class, (Object[])null);
        assertNotNull(result);
    }
    
    /**
     * Test invokeExactConstructor with null parameter types
     */
    @Test
    public void testInvokeExactConstructorWithNullParamTypes() throws Exception {
        PublicClass result = ConstructorUtils.invokeExactConstructor(PublicClass.class, new Object[]{}, null);
        assertNotNull(result);
    }
    
    /**
     * Test invokeExactConstructor when no matching constructor exists
     */
    @Test
    public void testInvokeExactConstructorNoMatch() {
        try {
            ConstructorUtils.invokeExactConstructor(PublicClass.class, new Object[]{"test"}, new Class<?>[]{Object.class});
            fail("Should throw NoSuchMethodException");
        } catch (NoSuchMethodException e) {
            assertTrue(e.getMessage().contains("No such accessible constructor"));
        } catch (Exception e) {
            fail("Wrong exception type: " + e.getClass().getName());
        }
    }
    
    /**
     * Test getAccessibleConstructor with non-existent constructor
     */
    @Test
    public void testGetAccessibleConstructorNoMatch() {
        Constructor<PublicClass> result = ConstructorUtils.getAccessibleConstructor(PublicClass.class, Double.class);
        assertNull(result);
    }
    
    /**
     * Test getAccessibleConstructor with valid constructor
     */
    @Test
    public void testGetAccessibleConstructorWithMatch() {
        Constructor<PublicClass> result = ConstructorUtils.getAccessibleConstructor(PublicClass.class, String.class);
        assertNotNull(result);
    }
    
    /**
     * Test getAccessibleConstructor(Constructor) with null check
     */
    @Test
    public void testGetAccessibleConstructorDirectNull() throws Exception {
        Constructor<PublicClass> ctor = PublicClass.class.getConstructor();
        Constructor<PublicClass> result = ConstructorUtils.getAccessibleConstructor(ctor);
        assertNotNull(result);
    }
    
    /**
     * Test getMatchingAccessibleConstructor with exact match
     */
    @Test
    public void testGetMatchingAccessibleConstructorExactMatch() {
        Constructor<PublicClass> result = ConstructorUtils.getMatchingAccessibleConstructor(
            PublicClass.class, new Class<?>[]{String.class}
        );
        assertNotNull(result);
    }
    
    /**
     * Test getMatchingAccessibleConstructor with compatible but not exact match
     */
    @Test
    public void testGetMatchingAccessibleConstructorCompatibleMatch() {
        // Integer is compatible with int through auto-unboxing
        Constructor<PublicClass> result = ConstructorUtils.getMatchingAccessibleConstructor(
            PublicClass.class, new Class<?>[]{Integer.class}
        );
        assertNotNull(result);
    }
    
    /**
     * Test getMatchingAccessibleConstructor with no match
     */
    @Test
    public void testGetMatchingAccessibleConstructorNoMatch() {
        Constructor<PublicClass> result = ConstructorUtils.getMatchingAccessibleConstructor(
            PublicClass.class, new Class<?>[]{Double.class}
        );
        assertNull(result);
    }
}
