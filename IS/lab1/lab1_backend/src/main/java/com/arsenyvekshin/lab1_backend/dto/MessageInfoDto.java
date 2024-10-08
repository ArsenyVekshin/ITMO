package com.arsenyvekshin.lab1_backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Передача строки")
public class MessageInfoDto {
    @Schema(description = "Произвольная строка", example = "hello world!")
    private String message;
}
