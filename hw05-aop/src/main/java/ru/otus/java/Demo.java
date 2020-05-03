package ru.otus.java;

import ru.otus.java.test.TestLoggingInterface;
import ru.otus.java.proxy.Ioc;

public class Demo {
    public static void main(String[] args) {
        TestLoggingInterface testLogging = Ioc.createTestClass();
        testLogging.calculation(1, 7);
        testLogging.subtraction(2, 5);
        testLogging.printPi();
    }
}
