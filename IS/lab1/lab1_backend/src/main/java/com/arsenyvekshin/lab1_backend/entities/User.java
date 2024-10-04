package com.arsenyvekshin.lab1_backend.entities;

import jakarta.persistence.*;
import lombok.*;

import static com.arsenyvekshin.lab1_backend.utils.Security.sha256;

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

    @Column(name = "role")
    private Role role;

    @Column(name = "approved")
    private boolean approved = false;

    public User(String login, String password) {
        this.login = login;
        this.password = sha256(password);
        this.role = Role.USER;
    }

    public User(String login, String password, Role role, boolean approved) {
        this.login = login;
        this.password = sha256(password);
        this.role = role;
        this.approved = approved;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(sha256(password));
    }

    public boolean isAdmin(){
        return this.approved && this.role == Role.ADMIN;
    }

}