package com.arsenyvekshin.lab1_backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@Schema(description = "Массив объектов для импорта из файла")
public class RoutesListDto {
    private List<RouteDto> routes;

    @JsonCreator
    public RoutesListDto(@JsonProperty("routes") List<RouteDto> routes) {
        this.routes = routes;
    }

}
