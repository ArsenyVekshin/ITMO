package com.arsenyvekshin.lab1_backend.entity;

import com.arsenyvekshin.lab1_backend.utils.ComparableObj;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.*;

import java.util.Collection;
import java.util.List;



@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "authorities", "accountNonExpired", "credentialsNonExpired", "accountNonLocked"})
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Users")
public class User implements UserDetails, ComparableObj {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "roleRequest")
    private boolean roleRequest = false;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority((role.name())));
    }

    @Override
    public int compareTo(String value, String fieldName){
        switch (fieldName){
            case "id":
                return this.id.compareTo(Long.valueOf(value));
            case "username":
                return this.username.compareTo(value);
            default:
                throw new IllegalArgumentException("Поле не найдено или недоступно. Сравнение невозможно");
        }
    }
}