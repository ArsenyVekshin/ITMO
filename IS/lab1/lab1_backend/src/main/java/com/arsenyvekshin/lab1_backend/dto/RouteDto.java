package com.arsenyvekshin.lab1_backend.dto;

import com.arsenyvekshin.lab1_backend.entity.Route;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
//@AllArgsConstructor
@Schema(description = "Маршрут")
public class RouteDto {
    @Schema(description = "id (если необходимо)", example = "1")
    private Long id;  //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @Schema(description = "Имя маршрута", example = "highway to hell")
    @NotBlank(message = "Имя маршрута не может быть пустым")
    private String name; //Поле не может быть null, Строка не может быть пустой

    @Schema(description = "Координаты", example = "404")
    @NotBlank(message = "Координаты не могут быть пустыми")
    private CoordinatesDto coordinates; //Поле не может быть null

    @Schema(description = "Локация (от куда)", example = "404")
    @NotBlank(message = "Локация (от куда) не может быть пустым")
    private LocationDto from; //Поле не может быть null

    @Schema(description = "Локация (куда)", example = "404")
    @NotBlank(message = "Локация (куда) не может быть пустым")
    private LocationDto to; //Поле может быть null

    @Schema(description = "Длинна маршрута", example = "404")
    @NotBlank(message = "Длинна маршрута не может быть пустым")
    @Min(value = 1, message = "Дистанция должна быть >1")
    private Double distance; //Поле не может быть null, Значение поля должно быть больше 1

    @Schema(description = "Рейтинг маршрута", example = "404")
    @Min(value = 1, message = "Рейтинг должен быть >1")
    @NotBlank(message = "Рейтинг не может быть пустым")
    private Integer rating; //Поле не может быть null, Значение поля должно быть больше 0

    @Schema(description = "Владелец объекта", example = "user1")
    private String owner;

    @Schema(description = "ReadOnly флаг", example = "true")
    private boolean readonly = false;


    public RouteDto(Route route) {
        this.id = route.getId();
        this.name = route.getName();
        this.coordinates = new CoordinatesDto(route.getCoordinates());
        this.from = new LocationDto(route.getFrom());
        if (route.getTo() != null)
            this.to = new LocationDto(route.getFrom());
        this.distance = route.getDistance();
        this.rating = route.getRating();
        this.owner = route.getOwner().getUsername();
        this.readonly = route.isReadonly();
    }

    @JsonCreator
    public RouteDto(
            @JsonProperty("id") Long id,
            @JsonProperty("name") String name,
            @JsonProperty("coordinates") CoordinatesDto coordinates,
            @JsonProperty("from") LocationDto from,
            @JsonProperty("to") LocationDto to,
            @JsonProperty("distance") Double distance,
            @JsonProperty("rating") Integer rating,
            @JsonProperty("owner") String owner,
            @JsonProperty("readonly") boolean readonly
    ) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.from = from;
        this.to = to;
        if (distance < 1) throw new IllegalArgumentException("Дистанция должна быть >1");
        this.distance = distance;
        if (rating < 1) throw new IllegalArgumentException("Дистанция должна быть >1");
        this.rating = rating;
        this.owner = owner;
        this.readonly = readonly;
    }

}
