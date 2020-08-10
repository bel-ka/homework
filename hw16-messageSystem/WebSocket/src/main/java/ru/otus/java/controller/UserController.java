package ru.otus.java.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.otus.java.model.User;
import ru.otus.java.services.FrontendService;

@Controller
@AllArgsConstructor
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final FrontendService frontendService;

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/users")
    public User userSave(User user) {
        frontendService.saveUser(user, callbackUser -> logger.info("got callbackUser:{}", callbackUser));
        return user;
    }
}
