package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClassifyNumberEdgeTest {

  @Test
  void testLargePositive() {
    assertEquals(1, App.classifyNumber(Integer.MAX_VALUE));
  }

  @Test
  void testLargeNegative() {
    assertEquals(-1, App.classifyNumber(Integer.MIN_VALUE));
  }

  @Test
  void testZeroAgain() {
    assertEquals(0, App.classifyNumber(0));
  }
}
