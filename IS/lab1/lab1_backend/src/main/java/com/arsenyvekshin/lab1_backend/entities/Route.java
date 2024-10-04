package com.arsenyvekshin.lab1_backend.entities;

import com.arsenyvekshin.lab1_backend.exceptions.InvalidFieldValueException;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Routes")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @Column(name = "name", nullable = false, length = 255)
    private String name; //Поле не может быть null, Строка не может быть пустой

    @ManyToOne(fetch = FetchType.LAZY) // Reference key to Coordinates table
    @JoinColumn(name = "coordinates_id", nullable = false)
    private Coordinates coordinates; //Поле не может быть null

    @Column(name = "creationDate", nullable = false, updatable = false)
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    @ManyToOne(fetch = FetchType.LAZY) // Reference key to Location table
    @JoinColumn(name = "from_id", nullable = false)
    private Location from; //Поле не может быть null

    @ManyToOne(fetch = FetchType.LAZY) // Reference key to Location table
    @JoinColumn(name = "to_id")
    private Location to; //Поле может быть null

    @Column(name = "distance", nullable = false)
    private Double distance; //Поле не может быть null, Значение поля должно быть больше 1

    @Column(name = "rating", nullable = false)
    private Integer rating; //Поле не может быть null, Значение поля должно быть больше 0

    @ManyToOne(fetch = FetchType.LAZY) // Reference key to Users table
    @JoinColumn(name = "owner")
    private User owner;

    @Column(name = "readonly", nullable = false, length = 255)
    private boolean readonly;

    @SneakyThrows
    public void setName(String name) {
        if (name.isEmpty()) throw new InvalidFieldValueException("Route.name can't be empty");
        this.name = name;
    }

    @SneakyThrows
    public void setDistance(Double distance) {
        if(distance <= 1) throw new InvalidFieldValueException("Route.distance must be greater than 1");
        this.distance = distance;
    }

    @SneakyThrows
    public void setRating(Integer rating) {
        if(distance <= 0) throw new InvalidFieldValueException("Route.rating must be greater than 0");
        this.rating = rating;
    }

    public void updateBy(Route route) {

    }
}