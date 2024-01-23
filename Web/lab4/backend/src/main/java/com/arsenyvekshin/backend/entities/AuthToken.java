package com.arsenyvekshin.backend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "auth_tokens")
public class AuthToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;
    @Column(name = "token")
    private String token;
    @Column(name = "time")
    private LocalDateTime time;

    public static final int EXPIRED_IN_DAYS = 1;

    public boolean isExpired() {
        LocalDateTime expiredTime = time.plusDays(EXPIRED_IN_DAYS);
        return expiredTime.isBefore(LocalDateTime.now());
    }

    public AuthToken(User user, String token, LocalDateTime time) {
        this.user = user;
        this.token = token;
        this.time = time;
    }
}
