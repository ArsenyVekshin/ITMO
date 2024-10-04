package com.arsenyvekshin.lab1_backend.entities;

import com.arsenyvekshin.lab1_backend.exceptions.InvalidFieldValueException;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Coordinates")
public class Coordinates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "x")
    private int x; //Поле не может быть null
    @Column(name = "y")
    private int y; //Значение поля должно быть больше -761

    @SneakyThrows
    public void setY(int y) {
        if(y <= -761) throw new InvalidFieldValueException("Coordinates.y must be greater than -761");
        this.y = y;
    }
}