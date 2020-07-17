package ru.otus.java.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.java.model.User;
import ru.otus.java.services.DBServiceUser;

import java.util.List;

@Controller
@AllArgsConstructor
public class UserController {

    private final DBServiceUser dbServiceUser;

    @GetMapping({"/", "/user"})
    public String userListView(Model model) {
        List<User> users = dbServiceUser.getUsers();
        model.addAttribute("users", users);
        model.addAttribute("user", new User());
        return "users";
    }

    @PostMapping("/user/save")
    public RedirectView userSave(@ModelAttribute User user) {
        dbServiceUser.saveUser(user);

        return new RedirectView("/", true);
    }
}
