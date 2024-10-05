package com.arsenyvekshin.lab1_backend.entity;

import com.arsenyvekshin.lab1_backend.dto.CoordinatesDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.io.IOException;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Coordinates")
public class Coordinates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "x")
    private int x; //Поле не может быть null

    @Min(-761)
    @Column(name = "y")
    private int y; //Значение поля должно быть больше -761


    public void updateByDto(CoordinatesDto dto){
        this.x = dto.getX();
        this.y = dto.getY();
    }
}