package com.arsenyvekshin.backend.db;

import com.arsenyvekshin.backend.entities.AuthToken;
import com.arsenyvekshin.backend.entities.User;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {
    AuthToken findByToken(String token);
    AuthToken findByUser(User user);
    @Transactional
    void deleteByUserId(Long userId);
}
