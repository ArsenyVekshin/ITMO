package com.arsenyvekshin.lab1_backend.controllers;

import com.arsenyvekshin.lab1_backend.entities.AuthToken;
import com.arsenyvekshin.lab1_backend.entities.Role;
import com.arsenyvekshin.lab1_backend.entities.User;
import com.arsenyvekshin.lab1_backend.repositories.AuthTokenRepository;
import com.arsenyvekshin.lab1_backend.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static com.arsenyvekshin.lab1_backend.utils.JsonUtil.*;
import static com.arsenyvekshin.lab1_backend.utils.Security.getRandomHash;


@RestController
@CrossOrigin(origins = "*")

public class AuthController {
    private static final int MIN_LOGIN_LEN = 4;
    private static final int MIN_PASS_LEN = 8;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthTokenRepository authTokenRepository;


    @GetMapping(value = "/user/auth", produces = "application/json")
    public ResponseEntity<String> authUser(@RequestParam String login, @RequestParam String password) {
        User user = userRepository.findByLogin(login);

        if (user == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong login");
        if (!user.checkPassword(password))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong password");

        if (authTokenRepository.findByUser(user) != null)    //повторный логин -> обновляем токен для пользователя
            authTokenRepository.deleteByUserId(user.getId());

        String newToken = getRandomHash();
        authTokenRepository.save(new AuthToken(user, newToken, LocalDateTime.now()));
        return ResponseEntity.ok(jsonfield("token", newToken));
    }

    @GetMapping(value = "/user/logout", produces = "application/json")
    public ResponseEntity<String> logoutUser(HttpServletRequest request) {
        AuthToken token = authTokenRepository.findByToken(request.getHeader("auth-token"));
        if (token == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong auth token");
        authTokenRepository.deleteByUserId(token.getUser().getId());
        return ResponseEntity.ok(jsonMessage("User logout successfully"));
    }

    @PostMapping(value = "/user/reg", produces = "application/json")
    public ResponseEntity<String> createUser(HttpServletRequest request) {
        String login = request.getHeader("login");
        String password = request.getHeader("password");
        if (login == null || password == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonMessage("wrong request structure"));
        if (login.length() < MIN_LOGIN_LEN)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonMessage("Incorrect login (must be at least" + MIN_LOGIN_LEN + ")"));
        if (password.length() < MIN_PASS_LEN)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonMessage("Incorrect password (must be at least" + MIN_PASS_LEN + ")"));
        if (userRepository.findByLogin(login) != null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonMessage("User with this login already exist"));

        if (request.getHeader("adminRequest").equals("true")) {
            if (isAdminRequest(request)) {
                userRepository.save(new User(login, password, Role.ADMIN, true));
                return ResponseEntity.ok(jsonMessage("User with admin-role created successfully."));
            } else {
                userRepository.save(new User(login, password, Role.ADMIN, false));
                return ResponseEntity.ok(jsonMessage("User created successfully. The request for admin-role has been sent."));
            }
        }
        userRepository.save(new User(login, password));
        return ResponseEntity.ok(jsonMessage("User created successfully"));
    }

    @GetMapping(value = "/user/approve", produces = "application/json")
    public ResponseEntity<String> approveList(HttpServletRequest request) {
        if (isAdminRequest(request)) {
            return ResponseEntity.ok(gson.toJson(userRepository.getUnapprovedUsers()));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("This function is available only to the admin");
    }

    @PostMapping(value = "/user/approve", produces = "application/json")
    public ResponseEntity<String> approveUser(HttpServletRequest request) {
        if (isAdminRequest(request)) {
            User user = userRepository.findByLogin(request.getHeader("target"));
            if(user == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Target-user not found");
            user.setApproved(true);
            userRepository.save(user); // TODO: точно нужно?
            return ResponseEntity.ok(jsonMessage("User " + user.getLogin() + " approved successfully."));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("This function is available only to the admin");
    }


    public boolean isAdminRequest(HttpServletRequest request){
        AuthToken token = authTokenRepository.findByToken(request.getHeader("auth-token"));
        return token != null && token.getUser().isAdmin();
    }


}
