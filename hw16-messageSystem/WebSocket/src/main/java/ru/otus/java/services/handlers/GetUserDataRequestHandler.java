package ru.otus.java.services.handlers;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.java.model.User;
import ru.otus.java.services.DBServiceUser;
import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageBuilder;
import ru.otus.messagesystem.message.MessageHelper;

import java.util.Optional;

@AllArgsConstructor
public class GetUserDataRequestHandler implements RequestHandler<User> {
    private static final Logger logger = LoggerFactory.getLogger(GetUserDataRequestHandler.class);
    private final DBServiceUser dbService;

    @Override
    public Optional<Message> handle(Message msg) {
        logger.info("new message:{}", msg);
        User userReq = MessageHelper.getPayload(msg);
        dbService.saveUser(userReq);
        return Optional.of(MessageBuilder.buildReplyMessage(msg, userReq));
    }
}
