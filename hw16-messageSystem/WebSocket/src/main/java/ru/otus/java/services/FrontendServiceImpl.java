package ru.otus.java.services;

import lombok.AllArgsConstructor;
import ru.otus.java.model.User;
import ru.otus.messagesystem.client.MessageCallback;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageType;

@AllArgsConstructor
public class FrontendServiceImpl implements FrontendService {
    private final MsClient msClient;
    private final String databaseServiceClientName;

    @Override
    public void saveUser(User user, MessageCallback<User> dataConsumer) {
        Message outMsg = msClient.produceMessage(databaseServiceClientName, user,
                MessageType.USER_DATA, dataConsumer);
        msClient.sendMessage(outMsg);
    }
}
