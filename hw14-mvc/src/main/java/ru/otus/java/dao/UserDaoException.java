package ru.otus.java.dao;

public class UserDaoException extends RuntimeException {
    public UserDaoException(Exception ex) {
        super(ex);
    }
}
