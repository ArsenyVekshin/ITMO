package com.arsenyvekshin.lab1_backend.controller;

import com.arsenyvekshin.lab1_backend.dto.*;
import com.arsenyvekshin.lab1_backend.service.AuthenticationService;
import com.arsenyvekshin.lab1_backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "Управление пользователями", description = "Методы для управления пользователями")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Подтвердить роль ADMIN")
    @PostMapping("/approve")
    public MessageInfoDto approveUser(@RequestBody MessageInfoDto request) {
        userService.approveUser(request.getMessage());
        return new MessageInfoDto("successful");
    }

    @Operation(summary = "Список пользователей ожидающих одобрения прав ADMIN")
    @GetMapping ("/approve/list")
    public ApproveListResponse getUnapprovedUsers() {
        return new ApproveListResponse(userService.getUnapprovedUsers());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageInfoDto error(Exception ex) {
        return new MessageInfoDto(ex.getMessage());
    }

}