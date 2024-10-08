package com.arsenyvekshin.lab1_backend.dto;

import com.arsenyvekshin.lab1_backend.entity.Route;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@Schema(description = "Ответ со списком записей в коллекции")
public class CollectionDto {

    @Schema(description = "Список маршрутов", example = "дохуя писать, я в домике")
    List<Route> routes;
}
