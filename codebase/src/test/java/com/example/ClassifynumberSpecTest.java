package com.example;

import org.junit.Test;
import static org.junit.Assert.*;

public class ClassifynumberSpecTest {

    @Test
    public void testPositiveNumber() {
        assertEquals("Positive", App.classifyNumber(1));
    }

    @Test
    public void testNegativeNumber() {
        assertEquals("Negative", App.classifyNumber(-1));
    }

    @Test
    public void testZero() {
        assertEquals("Zero", App.classifyNumber(0));
    }

    @Test
    public void testLargePositiveNumber() {
        assertEquals("Positive", App.classifyNumber(Integer.MAX_VALUE));
    }

    @Test
    public void testLargeNegativeNumber() {
        assertEquals("Negative", App.classifyNumber(Integer.MIN_VALUE));
    }
}