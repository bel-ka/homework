package ru.otus.java.services;

import ru.otus.java.model.User;
import ru.otus.messagesystem.client.MessageCallback;

public interface FrontendService {
    void saveUser(User user, MessageCallback<User> dataConsumer);
}

