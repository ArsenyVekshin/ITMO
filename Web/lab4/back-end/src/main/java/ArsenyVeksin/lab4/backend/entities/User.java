package ArsenyVeksin.lab4.backend.entities;

import jakarta.persistence.*;
import lombok.*;

import static ArsenyVeksin.lab4.backend.security.Security.sha256;

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

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = sha256(password);
    }

    public boolean checkPassword(String password) {
        return this.password.equals(sha256(password));
    }

}
