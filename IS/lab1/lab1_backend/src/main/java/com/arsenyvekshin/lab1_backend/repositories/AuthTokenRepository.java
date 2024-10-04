package com.arsenyvekshin.lab1_backend.repositories;

import com.arsenyvekshin.lab1_backend.entities.AuthToken;
import com.arsenyvekshin.lab1_backend.entities.User;
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
