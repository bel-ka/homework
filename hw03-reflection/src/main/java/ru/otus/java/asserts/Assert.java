package ru.otus.java.asserts;

import ru.otus.java.exception.TestFailException;

public class Assert {
    public static void assertTrue(boolean statement) {
        if (!statement) {
            throw new TestFailException("Test fail. Statement return false.");
        }
    }
}
