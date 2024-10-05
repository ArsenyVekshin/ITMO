package com.arsenyvekshin.lab1_backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Локация")
public class LocationDto {
    @Schema(description = "id (если необходимо)", example = "1")
    private Long id;

    @Schema(description = "Координата X", example = "1")
    @NotBlank(message = "Координата X не может быть пустой")
    private long x;

    @Schema(description = "Координата Y", example = "1")
    private long y;

    @Schema(description = "Координата Z", example = "1")
    @NotBlank(message = "Координата Z не может быть пустой")
    private long z;

    @Schema(description = "Название локации", example = "podval")
    @NotBlank(message = "Имя локации не может быть пустым")
    private String name;
}
