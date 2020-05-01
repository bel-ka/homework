package ru.otus.java;

import ru.otus.java.reflection.ReflectionHelper;

public class ReflectionDemo {
    public static void main(String[] args) {
        ReflectionHelper reflectionHelper = new ReflectionHelper();
        try {
            reflectionHelper.runTest("ru.otus.java.test.TestMap");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
