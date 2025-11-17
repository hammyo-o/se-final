/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.lang3.reflect;

import org.junit.Test;
import static org.junit.Assert.*;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Extended unit tests for {@link TypeUtils}.
 * Focus on null handling, edge cases, and boundary conditions.
 */
public class TypeUtilsExtendedTest {

    @Test
    public void testConstructor() {
        // Test that TypeUtils can be instantiated (JavaBean compatibility)
        TypeUtils typeUtils = new TypeUtils();
        assertNotNull("TypeUtils instance should not be null", typeUtils);
    }

    @Test
    public void testIsAssignableNullType() {
        // Test isAssignable with null type parameter
        boolean result = TypeUtils.isAssignable(null, String.class);
        assertTrue("null should be assignable to any class", result);
    }

    @Test
    public void testIsAssignableNullToType() {
        // Test isAssignable with null toType parameter
        boolean result = TypeUtils.isAssignable(String.class, (Type) null);
        assertFalse("No type should be assignable to null", result);
    }

    @Test
    public void testIsAssignableBothNull() {
        // Test isAssignable with both parameters null
        boolean result = TypeUtils.isAssignable(null, (Type) null);
        assertTrue("null should be assignable to null", result);
    }

    @Test
    public void testIsAssignableSameClass() {
        // Test isAssignable with same class
        boolean result = TypeUtils.isAssignable(String.class, String.class);
        assertTrue("A class should be assignable to itself", result);
    }

    @Test
    public void testIsAssignableSubclass() {
        // Test isAssignable with subclass
        boolean result = TypeUtils.isAssignable(Integer.class, Number.class);
        assertTrue("Integer should be assignable to Number", result);
    }

    @Test
    public void testIsAssignableNotSubclass() {
        // Test isAssignable with unrelated classes
        boolean result = TypeUtils.isAssignable(String.class, Number.class);
        assertFalse("String should not be assignable to Number", result);
    }

    @Test
    public void testIsAssignablePrimitives() {
        // Test isAssignable with primitive types
        boolean result = TypeUtils.isAssignable(int.class, int.class);
        assertTrue("int should be assignable to int", result);
    }

    @Test
    public void testIsAssignablePrimitiveToWrapper() {
        // Test primitive to wrapper assignability
        boolean result = TypeUtils.isAssignable(int.class, Integer.class);
        assertTrue("int should be assignable to Integer", result);
    }

    @Test
    public void testIsAssignableWrapperToPrimitive() {
        // Test wrapper to primitive assignability
        boolean result = TypeUtils.isAssignable(Integer.class, int.class);
        assertTrue("Integer should be assignable to int", result);
    }

    @Test
    public void testIsAssignableArrays() {
        // Test isAssignable with array types
        boolean result = TypeUtils.isAssignable(String[].class, Object[].class);
        assertTrue("String[] should be assignable to Object[]", result);
    }

    @Test
    public void testIsAssignableArrayToDifferentArray() {
        // Test isAssignable with incompatible array types
        boolean result = TypeUtils.isAssignable(String[].class, Integer[].class);
        assertFalse("String[] should not be assignable to Integer[]", result);
    }

    @Test
    public void testIsAssignableVoidType() {
        // Test isAssignable with void type
        boolean result = TypeUtils.isAssignable(void.class, void.class);
        assertTrue("void should be assignable to void", result);
    }

    @Test
    public void testIsAssignableVoidToOther() {
        // Test void assignability to other types
        boolean result = TypeUtils.isAssignable(void.class, Object.class);
        assertFalse("void should not be assignable to Object", result);
    }

    @Test
    public void testIsAssignableInterfaceImplementation() {
        // Test interface implementation assignability
        boolean result = TypeUtils.isAssignable(String.class, CharSequence.class);
        assertTrue("String should be assignable to CharSequence", result);
    }

    @Test
    public void testIsAssignableMultipleInterfaces() {
        // Test class implementing multiple interfaces
        boolean result = TypeUtils.isAssignable(String.class, Comparable.class);
        assertTrue("String should be assignable to Comparable", result);
    }

    @Test
    public void testIsAssignableWithGenerics() {
        // Test isAssignable with generic List
        boolean result = TypeUtils.isAssignable(List.class, List.class);
        assertTrue("List should be assignable to List", result);
    }

    @Test
    public void testIsAssignableGenericToRaw() {
        // Test generic type to raw type
        boolean result = TypeUtils.isAssignable(List.class, Object.class);
        assertTrue("List should be assignable to Object", result);
    }

    @Test
    public void testIsAssignablePrimitiveByteToInt() {
        // Test primitive widening conversion
        boolean result = TypeUtils.isAssignable(byte.class, int.class);
        assertTrue("byte should be assignable to int (widening)", result);
    }

    @Test
    public void testIsAssignablePrimitiveIntToShort() {
        // Test primitive narrowing conversion (should fail)
        boolean result = TypeUtils.isAssignable(int.class, short.class);
        assertFalse("int should not be assignable to short (narrowing not implicit)", result);
    }

    @Test
    public void testIsAssignablePrimitiveFloatToDouble() {
        // Test float to double widening
        boolean result = TypeUtils.isAssignable(float.class, double.class);
        assertTrue("float should be assignable to double (widening)", result);
    }

    @Test
    public void testIsAssignablePrimitiveDoubleToFloat() {
        // Test double to float narrowing (should fail)
        boolean result = TypeUtils.isAssignable(double.class, float.class);
        assertFalse("double should not be assignable to float (narrowing not implicit)", result);
    }

    @Test
    public void testIsAssignableCharToInt() {
        // Test char to int widening
        boolean result = TypeUtils.isAssignable(char.class, int.class);
        assertTrue("char should be assignable to int (widening)", result);
    }

    @Test
    public void testIsAssignableBooleanToInt() {
        // Test boolean to int (should fail - no conversion)
        boolean result = TypeUtils.isAssignable(boolean.class, int.class);
        assertFalse("boolean should not be assignable to int", result);
    }

    @Test
    public void testIsAssignableObjectArray() {
        // Test Object[] assignability
        boolean result = TypeUtils.isAssignable(Object[].class, Object.class);
        assertTrue("Object[] should be assignable to Object", result);
    }

    @Test
    public void testIsAssignablePrimitiveArray() {
        // Test primitive array assignability
        boolean result = TypeUtils.isAssignable(int[].class, Object.class);
        assertTrue("int[] should be assignable to Object", result);
    }

    @Test
    public void testIsAssignablePrimitiveArrayToObjectArray() {
        // Test primitive array to Object array (should fail)
        boolean result = TypeUtils.isAssignable(int[].class, Object[].class);
        assertFalse("int[] should not be assignable to Object[]", result);
    }

    @Test
    public void testIsAssignableObjectArrayToPrimitiveArray() {
        // Test Object array to primitive array (should fail)
        boolean result = TypeUtils.isAssignable(Integer[].class, int[].class);
        assertFalse("Integer[] should not be assignable to int[]", result);
    }

    @Test
    public void testIsAssignableMultidimensionalArrays() {
        // Test multidimensional arrays
        boolean result = TypeUtils.isAssignable(String[][].class, Object[][].class);
        assertTrue("String[][] should be assignable to Object[][]", result);
    }

    @Test
    public void testIsAssignableMapTypes() {
        // Test Map type assignability
        boolean result = TypeUtils.isAssignable(Map.class, Object.class);
        assertTrue("Map should be assignable to Object", result);
    }

    @Test
    public void testIsAssignableNumberSubtypes() {
        // Test various Number subtypes
        boolean result1 = TypeUtils.isAssignable(Long.class, Number.class);
        boolean result2 = TypeUtils.isAssignable(Double.class, Number.class);
        boolean result3 = TypeUtils.isAssignable(Byte.class, Number.class);
        
        assertTrue("Long should be assignable to Number", result1);
        assertTrue("Double should be assignable to Number", result2);
        assertTrue("Byte should be assignable to Number", result3);
    }
}
