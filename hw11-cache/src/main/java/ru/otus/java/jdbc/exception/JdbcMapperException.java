package ru.otus.java.jdbc.exception;

public class JdbcMapperException extends RuntimeException {
    public JdbcMapperException(Exception ex) {
        super(ex);
    }
}
