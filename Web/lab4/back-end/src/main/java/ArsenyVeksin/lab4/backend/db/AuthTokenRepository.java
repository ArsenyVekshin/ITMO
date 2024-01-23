package ArsenyVeksin.lab4.backend.db;

import ArsenyVeksin.lab4.backend.entities.AuthToken;
import ArsenyVeksin.lab4.backend.entities.User;
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
