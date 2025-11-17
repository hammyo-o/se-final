package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test suite for App.classifyNumber() method.
 * Tests all branches: positive, negative, and zero cases.
 */
public class ClassifyNumberTest {

  @Test
  void testPositiveNumber() {
    assertEquals(1, App.classifyNumber(5), "Positive number should return 1");
    assertEquals(1, App.classifyNumber(100), "Positive number should return 1");
    assertEquals(1, App.classifyNumber(1), "Positive number should return 1");
  }

  @Test
  void testNegativeNumber() {
    assertEquals(-1, App.classifyNumber(-5), "Negative number should return -1");
    assertEquals(-1, App.classifyNumber(-100), "Negative number should return -1");
    assertEquals(-1, App.classifyNumber(-1), "Negative number should return -1");
  }

  @Test
  void testZero() {
    assertEquals(0, App.classifyNumber(0), "Zero should return 0");
  }
}
