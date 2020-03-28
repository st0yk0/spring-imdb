package org.imdb.users.services.impl;

import com.auth0.jwt.JWT;
import lombok.extern.log4j.Log4j2;
import org.imdb.exceptions.HttpUnauthorizedException;
import org.imdb.users.entities.User;
import org.imdb.users.entities.UserRepository;
import org.imdb.users.model.UserModel;
import org.imdb.users.rest.LoginResponse;
import org.imdb.users.services.UserService;
import org.imdb.users.services.converters.UserConverter;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static org.imdb.config.security.PasswordEncoder.checkPassword;
import static org.imdb.config.security.PasswordEncoder.hashPassword;
import static org.imdb.config.security.SecurityConstants.EXPIRATION_TIME;
import static org.imdb.config.security.SecurityConstants.SECRET;


@Service
@Log4j2
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }


    @Override
    public UserModel registerUser(UserModel model) {
        log.info("Register user BEGIN: {}", model);

        model.setPassword(hashPassword(model.getPassword()));

        final User user = userConverter.convertToEntity(model);

        final User saved = userRepository.save(user);

        log.info("Register user END: {}", model);

        return userConverter.convertToModel(saved);
    }

    @Override
    public LoginResponse loginUser(String username, String password) {
        log.info("Login user BEGIN: {}", username);

        final User user = getUser(username);

        if (!checkPassword(password, user.getPassword())) {
            throw new HttpUnauthorizedException();
        }

        final String jwtToken = JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));

        final UserModel userModel = userConverter.convertToModel(user);

        final LoginResponse response = new LoginResponse(userModel, jwtToken);

        log.info("Login user END: {}", response);

        return response;
    }

    private User getUser(final String username) {
        final Optional<User> userOpt = userRepository
                .findByUsername(username);

        return userOpt.orElse(null);
    }
}
