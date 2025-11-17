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
package org.apache.commons.lang3.builder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

/**
 * Extended unit tests for {@link StandardToStringStyle}.
 * Tests various getter/setter combinations and edge cases.
 */
public class StandardToStringStyleExtendedTest {

    private StandardToStringStyle style;

    @Before
    public void setUp() {
        style = new StandardToStringStyle();
    }

    @Test
    public void testConstructor() {
        assertNotNull("StandardToStringStyle should be instantiable", style);
    }

    @Test
    public void testUseClassNameGetterSetter() {
        // Test default value
        assertTrue("Default useClassName should be true", style.isUseClassName());
        
        // Test setter with false
        style.setUseClassName(false);
        assertFalse("useClassName should be false after setting", style.isUseClassName());
        
        // Test setter with true
        style.setUseClassName(true);
        assertTrue("useClassName should be true after setting", style.isUseClassName());
    }

    @Test
    public void testUseShortClassNameGetterSetter() {
        // Test default value
        assertFalse("Default useShortClassName should be false", style.isUseShortClassName());
        
        // Test setter with true
        style.setUseShortClassName(true);
        assertTrue("useShortClassName should be true after setting", style.isUseShortClassName());
        
        // Test setter with false
        style.setUseShortClassName(false);
        assertFalse("useShortClassName should be false after setting", style.isUseShortClassName());
    }

    @Test
    public void testUseIdentityHashCodeGetterSetter() {
        // Test default value
        assertTrue("Default useIdentityHashCode should be true", style.isUseIdentityHashCode());
        
        // Test setter with false
        style.setUseIdentityHashCode(false);
        assertFalse("useIdentityHashCode should be false after setting", style.isUseIdentityHashCode());
        
        // Test setter with true
        style.setUseIdentityHashCode(true);
        assertTrue("useIdentityHashCode should be true after setting", style.isUseIdentityHashCode());
    }

    @Test
    public void testUseFieldNamesGetterSetter() {
        // Test default value
        assertTrue("Default useFieldNames should be true", style.isUseFieldNames());
        
        // Test setter with false
        style.setUseFieldNames(false);
        assertFalse("useFieldNames should be false after setting", style.isUseFieldNames());
        
        // Test setter with true
        style.setUseFieldNames(true);
        assertTrue("useFieldNames should be true after setting", style.isUseFieldNames());
    }

    @Test
    public void testDefaultFullDetailGetterSetter() {
        // Test default value
        assertTrue("Default defaultFullDetail should be true", style.isDefaultFullDetail());
        
        // Test setter with false
        style.setDefaultFullDetail(false);
        assertFalse("defaultFullDetail should be false after setting", style.isDefaultFullDetail());
        
        // Test setter with true
        style.setDefaultFullDetail(true);
        assertTrue("defaultFullDetail should be true after setting", style.isDefaultFullDetail());
    }

    @Test
    public void testArrayContentDetailGetterSetter() {
        // Test default value
        assertTrue("Default arrayContentDetail should be true", style.isArrayContentDetail());
        
        // Test setter with false
        style.setArrayContentDetail(false);
        assertFalse("arrayContentDetail should be false after setting", style.isArrayContentDetail());
        
        // Test setter with true
        style.setArrayContentDetail(true);
        assertTrue("arrayContentDetail should be true after setting", style.isArrayContentDetail());
    }

    @Test
    public void testArrayStartGetterSetter() {
        // Test default value
        assertEquals("Default arrayStart should be '{'", "{", style.getArrayStart());
        
        // Test setter with custom value
        style.setArrayStart("[");
        assertEquals("arrayStart should be '[' after setting", "[", style.getArrayStart());
        
        // Test setter with null (should convert to empty string)
        style.setArrayStart(null);
        assertEquals("arrayStart should be empty string after setting null", "", style.getArrayStart());
        
        // Test setter with empty string
        style.setArrayStart("");
        assertEquals("arrayStart should be empty string", "", style.getArrayStart());
    }

    @Test
    public void testArrayEndGetterSetter() {
        // Test default value
        assertEquals("Default arrayEnd should be '}'", "}", style.getArrayEnd());
        
        // Test setter with custom value
        style.setArrayEnd("]");
        assertEquals("arrayEnd should be ']' after setting", "]", style.getArrayEnd());
        
        // Test setter with null (should convert to empty string)
        style.setArrayEnd(null);
        assertEquals("arrayEnd should be empty string after setting null", "", style.getArrayEnd());
    }

    @Test
    public void testArraySeparatorGetterSetter() {
        // Test default value
        assertEquals("Default arraySeparator should be ','", ",", style.getArraySeparator());
        
        // Test setter with custom value
        style.setArraySeparator(";");
        assertEquals("arraySeparator should be ';' after setting", ";", style.getArraySeparator());
        
        // Test setter with null (should convert to empty string)
        style.setArraySeparator(null);
        assertEquals("arraySeparator should be empty string after setting null", "", style.getArraySeparator());
    }

    @Test
    public void testContentStartGetterSetter() {
        // Test default value
        assertEquals("Default contentStart should be '['", "[", style.getContentStart());
        
        // Test setter with custom value
        style.setContentStart("(");
        assertEquals("contentStart should be '(' after setting", "(", style.getContentStart());
        
        // Test setter with null (should convert to empty string)
        style.setContentStart(null);
        assertEquals("contentStart should be empty string after setting null", "", style.getContentStart());
    }

    @Test
    public void testContentEndGetterSetter() {
        // Test default value
        assertEquals("Default contentEnd should be ']'", "]", style.getContentEnd());
        
        // Test setter with custom value
        style.setContentEnd(")");
        assertEquals("contentEnd should be ')' after setting", ")", style.getContentEnd());
        
        // Test setter with null (should convert to empty string)
        style.setContentEnd(null);
        assertEquals("contentEnd should be empty string after setting null", "", style.getContentEnd());
    }

    @Test
    public void testMultipleSettersInSequence() {
        // Test multiple setters called in sequence
        style.setUseClassName(false);
        style.setUseShortClassName(true);
        style.setUseIdentityHashCode(false);
        style.setArrayStart("<");
        style.setArrayEnd(">");
        
        assertFalse("useClassName should be false", style.isUseClassName());
        assertTrue("useShortClassName should be true", style.isUseShortClassName());
        assertFalse("useIdentityHashCode should be false", style.isUseIdentityHashCode());
        assertEquals("arrayStart should be '<'", "<", style.getArrayStart());
        assertEquals("arrayEnd should be '>'", ">", style.getArrayEnd());
    }

    @Test
    public void testResetToDefaults() {
        // Change all values
        style.setUseClassName(false);
        style.setUseShortClassName(true);
        style.setUseIdentityHashCode(false);
        style.setUseFieldNames(false);
        style.setArrayStart("[");
        
        // Create new instance to verify defaults
        StandardToStringStyle defaultStyle = new StandardToStringStyle();
        
        // Reset to defaults by setting back
        style.setUseClassName(true);
        style.setUseShortClassName(false);
        style.setUseIdentityHashCode(true);
        style.setUseFieldNames(true);
        style.setArrayStart("{");
        
        assertEquals("Should match default useClassName", defaultStyle.isUseClassName(), style.isUseClassName());
        assertEquals("Should match default useShortClassName", defaultStyle.isUseShortClassName(), style.isUseShortClassName());
        assertEquals("Should match default useIdentityHashCode", defaultStyle.isUseIdentityHashCode(), style.isUseIdentityHashCode());
        assertEquals("Should match default arrayStart", defaultStyle.getArrayStart(), style.getArrayStart());
    }

    @Test
    public void testSerializability() {
        // Test that the class has the serialVersionUID field
        try {
            StandardToStringStyle.class.getDeclaredField("serialVersionUID");
        } catch (NoSuchFieldException e) {
            fail("StandardToStringStyle should have serialVersionUID field");
        }
    }
}
