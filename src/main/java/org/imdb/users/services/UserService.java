package org.imdb.users.services;

import org.imdb.users.model.UserModel;
import org.imdb.users.rest.LoginResponse;

public interface UserService {

    UserModel registerUser(UserModel model);

    LoginResponse loginUser(String username, String password);

}
