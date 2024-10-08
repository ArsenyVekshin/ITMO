package com.arsenyvekshin.lab1_backend.service;

import com.arsenyvekshin.lab1_backend.entity.Role;
import com.arsenyvekshin.lab1_backend.entity.User;
import com.arsenyvekshin.lab1_backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;


    /**
     * Подтвердить права пользователя на роль администратора
     */
    public void approveUser(String username) {
        User user = getByUsername(username);

        if (user.getRole() == Role.ADMIN) throw new IllegalArgumentException("User is already an administrator");
        if (!user.isRoleRequest()) throw new IllegalArgumentException("User don't need to be approved");

        user.setRoleRequest(false);
        user.setRole(Role.ADMIN);
        userRepository.save(user);
    }

    /**
     * Сохранение пользователя
     *
     * @return сохраненный пользователь
     */
    public User save(User user) {
        return userRepository.save(user);
    }


    /**
     * Создание пользователя
     *
     * @return созданный пользователь
     */
    public User create(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new EntityNotFoundException("Пользователь с таким именем уже существует");
        }
        return save(user);
    }

    /**
     * Получение пользователя по имени пользователя
     *
     * @return пользователь
     */
    public User getByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if(user == null) throw new EntityNotFoundException("Пользователь не найден");
        return user;
    }

    /**
     * Получение пользователя по имени пользователя
     * <p>
     * Нужен для Spring Security
     *
     * @return пользователь
     */
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    /**
     * Получение текущего пользователя
     *
     * @return текущий пользователь
     */
    public User getCurrentUser() {
        // Получение имени пользователя из контекста Spring Security
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }


    /**
     * Выдача прав администратора текущему пользователю
     * <p>
     * Нужен для демонстрации
     */
    @Deprecated
    public void getAdmin() {
        var user = getCurrentUser();
        user.setRole(Role.ADMIN);
        save(user);
    }


    public List<String> getUnapprovedUsers(){
        return userRepository.getUnapprovedUsers();
    }
}

