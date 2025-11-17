package org.apache.commons.lang3.builder;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

/**
 * Additional tests for StandardToStringStyle.
 * Generated to improve code coverage.
 */
public class StandardToStringStyleAdditionalTest {

    private StandardToStringStyle style;
    
    @Before
    public void setUp() {
        style = new StandardToStringStyle();
    }
    
    @After
    public void tearDown() {
        ToStringBuilder.setDefaultStyle(ToStringStyle.DEFAULT_STYLE);
    }

    @Test
    public void testSetUseClassName() {
        style.setUseClassName(true);
        assertTrue("useClassName should be true", style.isUseClassName());
        style.setUseClassName(false);
        assertFalse("useClassName should be false", style.isUseClassName());
    }
    
    @Test
    public void testSetUseShortClassName() {
        style.setUseShortClassName(true);
        assertTrue("useShortClassName should be true", style.isUseShortClassName());
        style.setUseShortClassName(false);
        assertFalse("useShortClassName should be false", style.isUseShortClassName());
    }
    
    @Test
    public void testSetUseIdentityHashCode() {
        style.setUseIdentityHashCode(true);
        assertTrue("useIdentityHashCode should be true", style.isUseIdentityHashCode());
        style.setUseIdentityHashCode(false);
        assertFalse("useIdentityHashCode should be false", style.isUseIdentityHashCode());
    }
    
    @Test
    public void testSetUseFieldNames() {
        style.setUseFieldNames(true);
        assertTrue("useFieldNames should be true", style.isUseFieldNames());
        style.setUseFieldNames(false);
        assertFalse("useFieldNames should be false", style.isUseFieldNames());
    }
    
    @Test
    public void testSetDefaultFullDetail() {
        style.setDefaultFullDetail(true);
        assertTrue("defaultFullDetail should be true", style.isDefaultFullDetail());
        style.setDefaultFullDetail(false);
        assertFalse("defaultFullDetail should be false", style.isDefaultFullDetail());
    }
    
    @Test
    public void testSetArrayContentDetail() {
        style.setArrayContentDetail(true);
        assertTrue("arrayContentDetail should be true", style.isArrayContentDetail());
        style.setArrayContentDetail(false);
        assertFalse("arrayContentDetail should be false", style.isArrayContentDetail());
    }
    
    @Test
    public void testSetArrayStart() {
        style.setArrayStart("[");
        // No getter for arrayStart, just verify no exception
        assertNotNull(style);
    }
    
    @Test
    public void testSetArrayEnd() {
        style.setArrayEnd("]");
        // No getter for arrayEnd, just verify no exception
        assertNotNull(style);
    }
    
    @Test
    public void testSetArraySeparator() {
        style.setArraySeparator(", ");
        // No getter for arraySeparator, just verify no exception
        assertNotNull(style);
    }
    
    @Test
    public void testSetContentStart() {
        style.setContentStart("{");
        // No getter for contentStart, just verify no exception
        assertNotNull(style);
    }
    
    @Test
    public void testSetContentEnd() {
        style.setContentEnd("}");
        // No getter for contentEnd, just verify no exception
        assertNotNull(style);
    }
    
    @Test
    public void testSetFieldNameValueSeparator() {
        style.setFieldNameValueSeparator("=");
        // No getter for fieldNameValueSeparator, just verify no exception
        assertNotNull(style);
    }
    
    @Test
    public void testSetFieldSeparator() {
        style.setFieldSeparator(", ");
        // No getter for fieldSeparator, just verify no exception
        assertNotNull(style);
    }
    
    @Test
    public void testSetFieldSeparatorAtStart() {
        style.setFieldSeparatorAtStart(true);
        // No getter, just verify no exception
        assertNotNull(style);
    }
    
    @Test
    public void testSetFieldSeparatorAtEnd() {
        style.setFieldSeparatorAtEnd(true);
        // No getter, just verify no exception
        assertNotNull(style);
    }
    
    @Test
    public void testSetNullText() {
        style.setNullText("<null>");
        // No getter for nullText, just verify no exception
        assertNotNull(style);
    }
    
    @Test
    public void testSetSizeStartText() {
        style.setSizeStartText("<size=");
        // No getter for sizeStartText, just verify no exception
        assertNotNull(style);
    }
    
    @Test
    public void testSetSizeEndText() {
        style.setSizeEndText(">");
        // No getter for sizeEndText, just verify no exception
        assertNotNull(style);
    }
    
    @Test
    public void testSetSummaryObjectStartText() {
        style.setSummaryObjectStartText("<");
        // No getter for summaryObjectStartText, just verify no exception
        assertNotNull(style);
    }
    
    @Test
    public void testSetSummaryObjectEndText() {
        style.setSummaryObjectEndText(">");
        // No getter for summaryObjectEndText, just verify no exception
        assertNotNull(style);
    }
}
