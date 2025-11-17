package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Specification-based tests for App.classifyNumber() method.
 *
 * Tests cover:
 * - Boundary value analysis (zero, min/max int values)
 * - Equivalence partitioning (positive, negative, zero)
 * - Edge cases (Integer.MIN_VALUE, Integer.MAX_VALUE)
 */
public class ClassifyNumberSpecTest {

  /**
   * Boundary test: zero is the boundary between negative and positive.
   */
  @Test
  void testZeroBoundary() {
    assertEquals(0, App.classifyNumber(0), "Zero should return 0");
  }

  /**
   * Boundary test: smallest positive integer.
   */
  @Test
  void testSmallestPositive() {
    assertEquals(1, App.classifyNumber(1), "1 should return 1");
  }

  /**
   * Boundary test: largest negative integer.
   */
  @Test
  void testLargestNegative() {
    assertEquals(-1, App.classifyNumber(-1), "−1 should return -1");
  }

  /**
   * Edge case: Integer.MAX_VALUE.
   */
  @Test
  void testMaxInt() {
    assertEquals(1, App.classifyNumber(Integer.MAX_VALUE), "Integer.MAX_VALUE should return 1");
  }

  /**
   * Edge case: Integer.MIN_VALUE.
   */
  @Test
  void testMinInt() {
    assertEquals(-1, App.classifyNumber(Integer.MIN_VALUE), "Integer.MIN_VALUE should return -1");
  }

  /**
   * Equivalence partition: typical positive values.
   */
  @ParameterizedTest
  @ValueSource(ints = {2, 10, 100, 1000, 999999})
  void testPositivePartition(int x) {
    assertEquals(1, App.classifyNumber(x), x + " should return 1");
  }

  /**
   * Equivalence partition: typical negative values.
   */
  @ParameterizedTest
  @ValueSource(ints = {-2, -10, -100, -1000, -999999})
  void testNegativePartition(int x) {
    assertEquals(-1, App.classifyNumber(x), x + " should return -1");
  }
}
