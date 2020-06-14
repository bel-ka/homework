package ru.otus.java.core.dao;

import ru.otus.java.core.model.User;
import ru.otus.java.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface UserDao {
    Optional<User> findById(long id);

    long insertUser(User user);

    //void updateUser(User user);
    //void insertOrUpdate(User user);

    SessionManager getSessionManager();
}
