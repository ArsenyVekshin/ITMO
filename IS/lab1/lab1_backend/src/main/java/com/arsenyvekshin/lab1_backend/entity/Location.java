package com.arsenyvekshin.lab1_backend.entity;

import com.arsenyvekshin.lab1_backend.dto.LocationDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "x")
    private long x; //Поле не может быть null
    @Column(name = "y")
    private long y;
    @Column(name = "z")
    private long z; //Поле не может быть null

    @NotBlank(message = "Имя локации не может быть пустым")
    @Column(name = "name")
    private String name; //Строка не может быть пустой, Поле не может быть null


    public void updateByDto(LocationDto dto){
        this.x = dto.getX();
        this.y = dto.getY();
        this.z = dto.getZ();
        this.name = dto.getName();
    }

}