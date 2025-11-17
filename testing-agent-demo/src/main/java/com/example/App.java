package com.example;

/**
 * Hello world application.
 */
public class App {

  public static void main(String[] args) {
    System.out.println("Hello World!");
  }

  /**
   * Method added to intentionally lower code coverage.
   * The agent will generate tests for this.
   */
  public static int classifyNumber(int x) {
    if (x > 0) {
      return 1;
    } else if (x < 0) {
      return -1;
    } else {
      return 0;
    }
  }
}
