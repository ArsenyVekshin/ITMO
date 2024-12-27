package com.arsenyvekshin.lab1_backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Запрос на отсортированный список маршрутов")
public class SortedObjectListRequest {
    @Schema(description = "Тип сортировки", example = "'=' / '>' / '<'")
    private char sign = '=';

    @Schema(description = "Поле для сортировки", example = "to.name")
    private String field = "";

    @Schema(description = "Ключевое значение для сортировки", example = "12")
    private String value = "";
}
