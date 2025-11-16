package com.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class AppPrintTest {

    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void mainPrintsHelloWorld() throws Exception {
        // Call main and verify output contains the expected text
        App.main(new String[0]);
        String output = outContent.toString().trim();
        assertTrue(output.contains("Hello World!"), "Expected output to contain 'Hello World!' but was: " + output);
    }
}
