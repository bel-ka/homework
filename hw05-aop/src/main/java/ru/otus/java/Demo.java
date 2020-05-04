package ru.otus.java;

import ru.otus.java.test.TestLoggingImpl;
import ru.otus.java.test.TestLoggingInterface;
import ru.otus.java.proxy.Ioc;
import ru.otus.java.test.UserMapLoggingImpl;
import ru.otus.java.test.UserMapLoggingInterface;

public class Demo {
    public static void main(String[] args) {
        TestLoggingInterface testObj = new TestLoggingImpl();
        TestLoggingInterface testLogging = (TestLoggingInterface) Ioc.createTestClass(testObj);
        testLogging.calculation(1, 7);
        testLogging.subtraction(2, 5);
        testLogging.printPi();

        UserMapLoggingInterface userMapObj = new UserMapLoggingImpl();
        UserMapLoggingInterface userMapLogging = (UserMapLoggingInterface) Ioc.createTestClass(userMapObj);
        userMapLogging.addUserToMap("Ivan");
        userMapLogging.findUserIdInMap("Oleg");
    }
}
