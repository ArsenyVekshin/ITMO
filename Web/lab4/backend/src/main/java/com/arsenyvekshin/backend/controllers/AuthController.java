package com.arsenyvekshin.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

import com.arsenyvekshin.backend.db.AuthTokenRepository;
import com.arsenyvekshin.backend.db.UserRepository;
import com.arsenyvekshin.backend.entities.AuthToken;
import com.arsenyvekshin.backend.entities.User;
import static com.arsenyvekshin.backend.security.Security.getRandomHash;
import static com.arsenyvekshin.backend.utils.JsonUtil.*;


@RestController
public class AuthController {
    private static final int MIN_LOGIN_LEN = 4;
    private static final int MIN_PASS_LEN = 8;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthTokenRepository authTokenRepository;

    @GetMapping(value = "/user/auth", produces = "application/json")
    public String authUser(@RequestParam String login, @RequestParam String password) {

        User user = userRepository.findByLogin(login);

        if (user == null) return authError("User not found");
        if (!user.checkPassword(password)) return authError("Wrong password");

        if(authTokenRepository.findByUser(user) != null)    //повторный логин -> обновляем токен для пользователя
            authTokenRepository.deleteByUserId(user.getId());

        String newToken = getRandomHash();
        authTokenRepository.save(new AuthToken(user, newToken, LocalDateTime.now()));
        return jsonfield("token", newToken);
    }

    @GetMapping(value = "/user/logout", produces = "application/json")
    public String logoutUser(@RequestParam String userToken) {
        AuthToken token = authTokenRepository.findByToken(userToken);
        if(token == null)
            return authError(" wrong token");
        authTokenRepository.deleteByUserId(token.getUser().getId());
        return jsonMessage("User logout successfully");
    }

/*    public boolean checkToken(String userToken) {
        return authTokenRepository.findByToken(userToken) == null;
        if(token == null)
            return authError("wrong token");
        return jsonMessage("token is correct");
    }*/

    @PostMapping(value = "/user/reg", produces = "application/json")
    public String createUser(@RequestParam String login, @RequestParam String password) {
        if(login.length() < MIN_LOGIN_LEN)
            return jsonMessage("Incorrect login (must be at least"+ MIN_LOGIN_LEN +")");
        if(password.length() < MIN_PASS_LEN)
            return jsonMessage("Incorrect password (must be at least"+ MIN_PASS_LEN +")");
        if(userRepository.findByLogin(login) != null)
            return jsonMessage("User with this login already exist");

        userRepository.save(new User(login, password));
        return jsonMessage("user created successfully");
    }


    private String authError(String mes){
        return jsonMessage("Auth error:" + mes);
    }
}
