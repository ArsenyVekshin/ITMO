package com.arsenyvekshin.lab1_backend.entity;

import com.arsenyvekshin.lab1_backend.dto.CoordinatesDto;
import com.arsenyvekshin.lab1_backend.utils.ComparableObj;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Coordinates")
public class Coordinates implements ComparableObj {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "x")
    private Integer x; //Поле не может быть null

    @Min(-761)
    @Column(name = "y")
    private Integer y; //Значение поля должно быть больше -761


    public void updateByDto(CoordinatesDto dto) {
        this.x = dto.getX();
        this.y = dto.getY();
    }

    @Override
    public int compareTo(String value, String fieldName) {
        switch (fieldName) {
            case "id":
                return this.id.compareTo(Long.valueOf(value));
            case "x":
                return this.x.compareTo(Integer.valueOf(value));
            case "y":
                return this.y.compareTo(Integer.valueOf(value));

            default:
                throw new IllegalArgumentException("Поле не найдено. Сравнение невозможно");
        }
    }
}