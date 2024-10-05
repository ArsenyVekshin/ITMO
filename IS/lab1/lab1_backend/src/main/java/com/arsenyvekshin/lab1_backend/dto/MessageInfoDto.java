package com.arsenyvekshin.lab1_backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Передача строки")
public class MessageInfoDto {
    @Schema(description = "Произвольная строка", example = "hello world!")
    private String message;

}
