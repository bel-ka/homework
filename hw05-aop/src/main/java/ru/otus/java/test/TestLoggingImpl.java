package ru.otus.java.test;

import ru.otus.java.annotations.Log;

import static java.lang.Math.PI;

public class TestLoggingImpl implements TestLoggingInterface {

    @Override
    public void calculation(int param1, int param2) {
        System.out.println("Sum: " + (param1 + param2));
    }

    @Log
    @Override
    public void subtraction(int param1, int param2) {
        System.out.println("Minus: " + Math.abs(param1 - param2));
    }

    @Log
    @Override
    public void printPi() {
        System.out.println("PI: " + PI);
    }


}
