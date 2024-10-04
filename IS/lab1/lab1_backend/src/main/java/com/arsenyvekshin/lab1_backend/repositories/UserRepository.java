package com.arsenyvekshin.lab1_backend.repositories;

import com.arsenyvekshin.lab1_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);

    @Query("SELECT u FROM User u WHERE u.role='ADMIN' and u.approved = false")
    List<User> getUnapprovedUsers();
}

