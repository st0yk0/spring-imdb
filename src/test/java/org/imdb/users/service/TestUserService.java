package org.imdb.users.service;

import org.imdb.exceptions.HttpUnauthorizedException;
import org.imdb.users.model.UserModel;
import org.imdb.users.rest.LoginResponse;
import org.imdb.users.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TestUserService {


    @Autowired
    private UserService userService;

    @Test
    public void testRegisterUser() {
        final UserModel model = new UserModel(null, "Genadi", "123456", "Genadi", "Ivanov");

        final UserModel created = userService.registerUser(model);

        assertEquals(model.getUsername(), created.getUsername());
        assertEquals(model.getFirstName(), created.getFirstName());
        assertEquals(model.getLastName(), created.getLastName());
    }

    @Test
    public void testLoginUser() {
        final UserModel model = new UserModel(null, "Troyan", "password", "Troyan", "Vasilev");

        final UserModel created = userService.registerUser(model);

        assertThrows(
                HttpUnauthorizedException.class,
                () -> userService.loginUser(created.getUsername(), "root"));

        final LoginResponse troyanLogin = userService.loginUser(created.getUsername(), "password");
        assertNotNull(troyanLogin.getUser());
        assertNotNull(troyanLogin.getJwtToken());
    }

}
