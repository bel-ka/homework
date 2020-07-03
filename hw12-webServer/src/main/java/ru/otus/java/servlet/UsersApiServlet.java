package ru.otus.java.servlet;

import com.google.gson.Gson;
import ru.otus.java.model.User;
import ru.otus.java.services.DBServiceUser;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UsersApiServlet extends HttpServlet {
    private final DBServiceUser dbServiceUser;
    private final Gson gson;

    public UsersApiServlet(DBServiceUser dbServiceUser, Gson gson) {
        this.dbServiceUser = dbServiceUser;
        this.gson = gson;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User userToSave = new User();
        userToSave.setName(request.getParameter("name"));
        userToSave.setLogin(request.getParameter("login"));
        userToSave.setPassword(request.getParameter("password"));

        dbServiceUser.saveUser(userToSave);

        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        out.print(gson.toJson(userToSave));
    }
}
