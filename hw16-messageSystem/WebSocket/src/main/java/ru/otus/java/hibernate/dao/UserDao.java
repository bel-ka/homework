package ru.otus.java.hibernate.dao;

import ru.otus.java.model.User;
import ru.otus.java.sessionmanager.SessionManager;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> findById(long id);

    Optional<User> findByLogin(String login);

    void insertOrUpdate(User user);

    List<User> findAll();

    SessionManager getSessionManager();
}