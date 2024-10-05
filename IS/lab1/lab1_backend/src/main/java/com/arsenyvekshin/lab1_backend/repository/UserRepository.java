package com.arsenyvekshin.lab1_backend.repository;

import com.arsenyvekshin.lab1_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String login);

    //@Query("SELECT u FROM User u WHERE u.role= \" and u.approved = false")
    @Query("select u.username from Users u where u.roleRequest = true")
    List<String> getUnapprovedUsers();
}

