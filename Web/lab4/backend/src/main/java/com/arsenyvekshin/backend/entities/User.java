package com.arsenyvekshin.backend.entities;

import jakarta.persistence.*;
import lombok.*;

import static com.arsenyvekshin.backend.security.Security.sha256;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "login", unique = true)
    private String login;

    @Column(name = "password")
    private String password;

    public User(String login, String password) {
        this.login = login;
        this.password = sha256(password);
    }

    public boolean checkPassword(String password) {
        return this.password.equals(sha256(password));
    }

}
