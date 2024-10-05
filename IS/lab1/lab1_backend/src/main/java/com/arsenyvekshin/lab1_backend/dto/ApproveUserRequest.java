package com.arsenyvekshin.lab1_backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Запрос на изменение прав пользователя")
public class ApproveUserRequest {
    @Schema(description = "Username целевого пользователя", example = "User1")
    private String username;

}