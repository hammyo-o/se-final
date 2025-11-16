package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Generated tests for com.example.App
 * - verifies that a public static main(String[]) method exists and can be invoked without throwing
 * - if a public no-arg constructor exists, verifies instantiation does not throw
 */
public class AppGeneratedTest {

    @Test
    void mainMethodRunsWithoutThrowing() throws Exception {
        assertDoesNotThrow(() -> {
            Class<?> cls = Class.forName("com.example.App");
            Method main = cls.getMethod("main", String[].class);
            String[] args = new String[0];
            // reflection requires wrapping the array as Object to avoid varargs
            main.invoke(null, (Object) args);
        });
    }

    @Test
    void instantiateIfHasPublicNoArgConstructor() throws Exception {
        assertDoesNotThrow(() -> {
            Class<?> cls = Class.forName("com.example.App");
            boolean hasNoArg = Arrays.stream(cls.getConstructors())
                    .anyMatch(c -> c.getParameterCount() == 0);
            if (hasNoArg) {
                cls.getDeclaredConstructor().newInstance();
            }
        });
    }
}
