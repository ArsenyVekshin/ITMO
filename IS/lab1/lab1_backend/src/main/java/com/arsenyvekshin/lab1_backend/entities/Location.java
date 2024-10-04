package com.arsenyvekshin.lab1_backend.entities;

import com.arsenyvekshin.lab1_backend.exceptions.InvalidFieldValueException;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Location")
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
    @Column(name = "name")
    private String name; //Строка не может быть пустой, Поле не может быть null

    @SneakyThrows
    public void setName(String name) {
        if (name.isEmpty()) throw new InvalidFieldValueException("Location.name can't be empty");
        this.name = name;
    }


}