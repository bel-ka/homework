package ru.otus.java.reflection;

import ru.otus.java.annotations.After;
import ru.otus.java.annotations.Before;
import ru.otus.java.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectionHelper {
    private Class<?> clazz;
    private Method beforeTestMethod;
    private Method afterTestMethod;
    private List<Method> testMethods = new ArrayList<>();
    private int testCount = 0;
    private int testFailCount = 0;

    public void runTest(String testClass) throws ClassNotFoundException {
        clazz = Class.forName(testClass);
        Method[] methodsPublic = clazz.getDeclaredMethods();
        findTestAnnotatedMethods(methodsPublic);
        invokeTestMethod();
        printStatistic();
    }

    private Object initTestObject() throws InstantiationException, IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        return clazz.getDeclaredConstructor().newInstance();
    }

    private void findTestAnnotatedMethods(Method[] methods) {
        Arrays.stream(methods).forEach(method -> {
                    try {
                        if (clazz.getMethod(method.getName()).isAnnotationPresent(Before.class)) {
                            beforeTestMethod = method;
                        } else if (clazz.getMethod(method.getName()).isAnnotationPresent(After.class)) {
                            afterTestMethod = method;
                        } else if (clazz.getMethod(method.getName()).isAnnotationPresent(Test.class)) {
                            testMethods.add(method);
                        }
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    private void invokeTestMethod() {
        testMethods.forEach(method -> {
            try {
                Object testObject = initTestObject();
                beforeTestMethod.invoke(testObject);
                try {
                    method.invoke(testObject);
                } catch (InvocationTargetException e) {
                    if (e.getTargetException().toString().contains("TestFailException")) {
                        System.out.println(method.getName() + ": " + e.getTargetException().getMessage());
                        ++testFailCount;
                    }
                }
                afterTestMethod.invoke(testObject);
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
                e.printStackTrace();
            }

            ++testCount;
        });
    }

    private void printStatistic() {
        System.out.println("Test count: " + testCount +
                ". Success: " + (testCount - testFailCount) +
                ". Fail: " + testFailCount);
    }
}
