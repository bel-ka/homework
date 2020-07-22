package ru.otus.java.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.otus.java.model.User;
import ru.otus.java.services.DBServiceUser;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@DisplayName("UserController должен ")
@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    private MockMvc mvc;

    @Mock
    private DBServiceUser dbServiceUser;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(new UserController(dbServiceUser)).build();
    }

    @Test
    @DisplayName("после перехода на узел user открывать страницу пользователей и модель users")
    void afterGoToUserEndpointShouldOpenUserViewAndUsersModel() throws Exception {
        mvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    @DisplayName("должен возвращать список ранее сохраненных пользователей")
    void shouldReturnListOfSavedUsers() throws Exception {
        User expectedUser = new User(1L, "Name", "Login", "Pass");
        given(dbServiceUser.getUsers()).willReturn(List.of(expectedUser));
        mvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("users", List.of(expectedUser)));
    }

    @Test
    @DisplayName("должен возвращать пустой список пользователей, если еще не было сохранения в БД")
    void shouldReturnEmptyListOfUsers() throws Exception {
        mvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("users", List.of()));
    }

    @Test
    @DisplayName("после сохранения пользователя делать редирект на главную страницу")
    void afterUserSaveShouldDoRedirectToMainView() throws Exception {
        mvc.perform(post("/user/save"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @DisplayName("должен сохрать переданного пользователя в БД")
    void shouldSaveUserToDb() throws Exception {
        User saveUser = new User(1L, "Name", "Login", "Pass");
        mvc.perform(post("/user/save").flashAttr("user", saveUser))
                .andExpect(redirectedUrl("/"));
        verify(dbServiceUser, times(1)).saveUser(saveUser);
    }
}