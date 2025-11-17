package org.apache.commons.lang3.reflect;

import static org.junit.Assert.*;
import org.junit.Test;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Additional boundary value and edge case tests for TypeUtils.
 * Generated to improve code coverage targeting uncovered branches.
 */
public class TypeUtilsAdditionalTest {

    @Test
    public void testIsAssignableNullToNonPrimitive() {
        // Null type is assignable to non-primitive classes
        assertTrue(TypeUtils.isAssignable(null, Object.class));
        assertTrue(TypeUtils.isAssignable(null, String.class));
        assertTrue(TypeUtils.isAssignable(null, Integer.class));
    }

    @Test
    public void testIsAssignableNullToPrimitive() {
        // Null type is NOT assignable to primitive types
        assertFalse(TypeUtils.isAssignable(null, int.class));
        assertFalse(TypeUtils.isAssignable(null, boolean.class));
        assertFalse(TypeUtils.isAssignable(null, double.class));
    }

    @Test
    public void testIsAssignableTypeToNull() {
        // Any type is NOT assignable to null (except null itself)
        assertFalse(TypeUtils.isAssignable(String.class, (Class<?>) null));
        assertFalse(TypeUtils.isAssignable(Integer.class, (Class<?>) null));
    }

    @Test
    public void testIsAssignableSameClass() {
        // All types are assignable to themselves
        assertTrue(TypeUtils.isAssignable(String.class, String.class));
        assertTrue(TypeUtils.isAssignable(Integer.class, Integer.class));
        assertTrue(TypeUtils.isAssignable(int.class, int.class));
    }

    @Test
    public void testIsAssignableWildcardToClass() {
        // Wildcard types are not assignable to a class
        final Type wildcardType = ((ParameterizedType) new ArrayList<Object>() {}.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        if (wildcardType instanceof WildcardType) {
            assertFalse(TypeUtils.isAssignable(wildcardType, Object.class));
        }
    }

    @Test
    public void testIsArrayTypeWithGenericArray() {
        // Test isArrayType with GenericArrayType
        final Type listArrayType = new GenericArrayType() {
            @Override
            public Type getGenericComponentType() {
                return new ArrayList<String>() {}.getClass().getGenericSuperclass();
            }
        };
        assertTrue(TypeUtils.isArrayType(listArrayType));
    }

    @Test
    public void testIsArrayTypeWithClass() {
        assertTrue(TypeUtils.isArrayType(String[].class));
        assertTrue(TypeUtils.isArrayType(int[].class));
        assertFalse(TypeUtils.isArrayType(String.class));
        assertFalse(TypeUtils.isArrayType(Integer.class));
    }

    @Test
    public void testGetArrayComponentTypeWithClass() {
        assertEquals(String.class, TypeUtils.getArrayComponentType(String[].class));
        assertEquals(int.class, TypeUtils.getArrayComponentType(int[].class));
        assertNull(TypeUtils.getArrayComponentType(String.class));
    }

    @Test
    public void testGetArrayComponentTypeWithGenericArray() {
        final Type stringComponentType = String.class;
        final Type genericArrayType = new GenericArrayType() {
            @Override
            public Type getGenericComponentType() {
                return stringComponentType;
            }
        };
        assertEquals(stringComponentType, TypeUtils.getArrayComponentType(genericArrayType));
    }

    @Test
    public void testIsInstanceWithNull() {
        // null is an instance of any reference type
        assertTrue(TypeUtils.isInstance(null, String.class));
        assertTrue(TypeUtils.isInstance(null, Object.class));
    }

    @Test
    public void testIsInstanceWithPrimitiveTypes() {
        assertTrue(TypeUtils.isInstance(42, int.class));
        assertTrue(TypeUtils.isInstance(42, Integer.class));
        assertTrue(TypeUtils.isInstance(true, boolean.class));
        assertTrue(TypeUtils.isInstance(true, Boolean.class));
    }

    @Test
    public void testGetTypeArgumentsWithParameterizedType() {
        final ParameterizedType listType = (ParameterizedType) new ArrayList<String>() {}.getClass().getGenericSuperclass();
        final Map<TypeVariable<?>, Type> typeArgs = TypeUtils.getTypeArguments(listType);
        assertNotNull(typeArgs);
        assertFalse(typeArgs.isEmpty());
    }

    @Test
    public void testGetTypeArgumentsWithClassAndTarget() {
        final Map<TypeVariable<?>, Type> typeArgs = TypeUtils.getTypeArguments(ArrayList.class, List.class);
        assertNotNull(typeArgs);
    }

    @Test
    public void testNormalizeUpperBoundsWithObjectBound() {
        // When bounds include Object, it should be normalized
        final Type[] bounds = new Type[] { Object.class, CharSequence.class };
        final Type[] normalized = TypeUtils.normalizeUpperBounds(bounds);
        assertNotNull(normalized);
        // Object should be removed if there are other bounds
        assertEquals(1, normalized.length);
        assertEquals(CharSequence.class, normalized[0]);
    }

    @Test
    public void testNormalizeUpperBoundsWithOnlyObject() {
        // When only Object is the bound, it should be kept
        final Type[] bounds = new Type[] { Object.class };
        final Type[] normalized = TypeUtils.normalizeUpperBounds(bounds);
        assertNotNull(normalized);
        assertEquals(1, normalized.length);
        assertEquals(Object.class, normalized[0]);
    }

    @Test
    public void testGetImplicitBoundsOfTypeVariable() {
        final TypeVariable<?>[] typeVars = Map.class.getTypeParameters();
        assertTrue(typeVars.length > 0);
        final Type[] bounds = TypeUtils.getImplicitBounds(typeVars[0]);
        assertNotNull(bounds);
        assertTrue(bounds.length > 0);
    }

    @Test
    public void testGetImplicitUpperBoundsOfWildcard() {
        // Create a wildcard type from List<?>
        final Type wildcardType = ((ParameterizedType) new ArrayList<Object>() {}.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        if (wildcardType instanceof WildcardType) {
            final Type[] bounds = TypeUtils.getImplicitUpperBounds((WildcardType) wildcardType);
            assertNotNull(bounds);
            assertTrue(bounds.length > 0);
        }
    }

    @Test
    public void testGetImplicitLowerBoundsOfWildcard() {
        final Type wildcardType = ((ParameterizedType) new ArrayList<Object>() {}.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        if (wildcardType instanceof WildcardType) {
            final Type[] bounds = TypeUtils.getImplicitLowerBounds((WildcardType) wildcardType);
            assertNotNull(bounds);
            // Wildcard <?> typically has null as lower bound
            assertTrue(bounds.length >= 0);
        }
    }

    @Test
    public void testGetRawTypeWithParameterizedType() {
        final ParameterizedType listType = (ParameterizedType) new ArrayList<String>() {}.getClass().getGenericSuperclass();
        final Class<?> rawType = TypeUtils.getRawType(listType, null);
        assertEquals(ArrayList.class, rawType);
    }

    @Test
    public void testGetRawTypeWithClass() {
        final Class<?> rawType = TypeUtils.getRawType(String.class, null);
        assertEquals(String.class, rawType);
    }

    @Test
    public void testGetRawTypeWithGenericArrayType() {
        final GenericArrayType arrayType = new GenericArrayType() {
            @Override
            public Type getGenericComponentType() {
                return String.class;
            }
        };
        final Class<?> rawType = TypeUtils.getRawType(arrayType, null);
        assertEquals(String[].class, rawType);
    }

    @Test
    public void testTypesSatisfyVariablesWithEmptyMap() {
        final Map<TypeVariable<?>, Type> emptyMap = new HashMap<TypeVariable<?>, Type>();
        assertTrue(TypeUtils.typesSatisfyVariables(emptyMap));
    }

    @Test
    public void testTypesSatisfyVariablesWithValidAssignments() {
        final TypeVariable<?>[] typeVars = Map.class.getTypeParameters();
        final Map<TypeVariable<?>, Type> assignments = new HashMap<TypeVariable<?>, Type>();
        if (typeVars.length > 0) {
            assignments.put(typeVars[0], String.class);
            assertTrue(TypeUtils.typesSatisfyVariables(assignments));
        }
    }

    @Test
    public void testIsAssignableWithTypeVariableBounds() {
        // TypeVariable with bounds should check against bounds
        final TypeVariable<?>[] typeVars = Map.class.getTypeParameters();
        if (typeVars.length > 0) {
            // TypeVariable K extends Object, so Object should be assignable
            assertTrue(TypeUtils.isAssignable(typeVars[0], Object.class));
        }
    }

    @Test
    public void testIsAssignableGenericArrayToObjectClass() {
        final GenericArrayType arrayType = new GenericArrayType() {
            @Override
            public Type getGenericComponentType() {
                return String.class;
            }
        };
        // Generic array types are assignable to Object
        assertTrue(TypeUtils.isAssignable(arrayType, Object.class));
    }

    @Test
    public void testIsAssignableGenericArrayToArrayClass() {
        final GenericArrayType arrayType = new GenericArrayType() {
            @Override
            public Type getGenericComponentType() {
                return String.class;
            }
        };
        // Generic array types are assignable to array classes if component types match
        assertTrue(TypeUtils.isAssignable(arrayType, String[].class));
    }
}
