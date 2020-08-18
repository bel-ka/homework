package ru.otus.java.controller;

import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.otus.java.model.User;
import ru.otus.java.services.FrontendService;

@Controller
@AllArgsConstructor
public class UserController {
    private final FrontendService frontendService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.addUser")
    public void userSave(User user) {
        frontendService.saveUser(user, callbackUser -> messagingTemplate.convertAndSend("/topic/users",
                callbackUser));
    }
}
