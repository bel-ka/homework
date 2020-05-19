package ru.otus.java.exception;

public class BanknotesNotFoundException extends RuntimeException {
    public BanknotesNotFoundException(String message) {
        super(message);
    }
}
