package com.arsenyvekshin.lab1_backend.entity;

import com.arsenyvekshin.lab1_backend.dto.LocationDto;
import com.arsenyvekshin.lab1_backend.utils.ComparableObj;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Location")
public class Location implements ComparableObj {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "x")
    private Long x; //Поле не может быть null
    @Column(name = "y")
    private Long y;
    @Column(name = "z")
    private Long z; //Поле не может быть null

    @NotBlank(message = "Имя локации не может быть пустым")
    @Column(name = "name")
    private String name; //Строка не может быть пустой, Поле не может быть null


    public void updateByDto(LocationDto dto) {
        this.x = dto.getX();
        this.y = dto.getY();
        this.z = dto.getZ();
        this.name = dto.getName();
    }

    @Override
    public int compareTo(String value, String fieldName) {
        switch (fieldName) {
            case "id":
                return this.id.compareTo(Long.valueOf(value));
            case "x":
                return this.x.compareTo(Long.valueOf(value));
            case "y":
                return this.y.compareTo(Long.valueOf(value));
            case "z":
                return this.z.compareTo(Long.valueOf(value));
            case "name":
                return this.name.compareTo(value);

            default:
                throw new IllegalArgumentException("Поле не найдено. Сравнение невозможно");
        }
    }
}