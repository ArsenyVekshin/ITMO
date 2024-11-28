package com.arsenyvekshin.lab1_backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Schema(description = "Ответ со списком пользователей, ожидающих подтверждения прав")
public class ApproveListResponse {
    @Schema(description = "Список пользователей", example = "{\"User1\", \"User2\"}")
    private List<String> usernameList;
}
