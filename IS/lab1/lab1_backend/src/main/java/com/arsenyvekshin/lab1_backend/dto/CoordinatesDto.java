package com.arsenyvekshin.lab1_backend.dto;

import com.arsenyvekshin.lab1_backend.entity.Coordinates;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Schema(description = "Координаты ")
public class CoordinatesDto {

    @Schema(description = "id (если необходимо)", example = "1")
    private Long id;

    @Schema(description = "Координата X", example = "1")
    @NotBlank(message = "Координата X не может быть пустой")
    private int x;

    @Schema(description = "Координата Y", example = "1")
    @Min(value = -761, message = "Координата Y должна быть больше -761")
    private int y;

    public CoordinatesDto(Coordinates coordinates){
        this.x = coordinates.getX();
        this.y = coordinates.getY();
    }
}
