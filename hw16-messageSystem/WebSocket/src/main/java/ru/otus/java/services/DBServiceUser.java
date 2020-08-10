package ru.otus.java.services;

import ru.otus.java.model.User;

import java.util.List;
import java.util.Optional;

public interface DBServiceUser {

    long saveUser(User user);

    Optional<User> getUser(String login);

    List<User> getUsers();
}
