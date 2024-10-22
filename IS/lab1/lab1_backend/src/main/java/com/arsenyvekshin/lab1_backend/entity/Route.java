package com.arsenyvekshin.lab1_backend.entity;

import com.arsenyvekshin.lab1_backend.dto.RouteDto;
import com.arsenyvekshin.lab1_backend.utils.ComparableObj;
import com.arsenyvekshin.lab1_backend.utils.Converter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Route")
public class Route implements ComparableObj {
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

    @Min(value = 1, message = "Дистанция должна быть >1")
    @Column(name = "distance", nullable = false)
    private Double distance; //Поле не может быть null, Значение поля должно быть больше 1

    @Min(value = 1, message = "Рейтинг должен быть >1")
    @Column(name = "rating", nullable = false)
    private Integer rating; //Поле не может быть null, Значение поля должно быть больше 0

    @ManyToOne(fetch = FetchType.LAZY) // Reference key to Users table
    @JoinColumn(name = "owner")
    private User owner;

    @Column(name = "readonly", nullable = false, length = 255)
    private boolean readonly;


    public void updateByDto(RouteDto dto) throws IOException {
        if (isReadonly()) throw new IOException("Объект помечен как ReadOnly");
        this.name = dto.getName();
        this.distance = dto.getDistance();
        this.rating = dto.getRating();
        this.readonly = dto.isReadonly();
    }

    @Override
    public int compareTo(String value, String fieldName){
        switch (fieldName){
            case "id":
                return this.id.compareTo(Long.valueOf(value));
            case "name":
                return this.name.compareTo(value);
            case "creationDate":
                return this.creationDate.compareTo(Converter.convert(LocalDate.class, value));
            case "distance":
                return this.distance.compareTo(Double.valueOf(value));
            case "rating":
                return this.rating.compareTo(Integer.valueOf(value));
            default:
                String _class = fieldName.substring(0, fieldName.indexOf('.')).trim();
                String _field = fieldName.substring(fieldName.indexOf('.')+1).trim();
                switch (_class){
                    case "coordinates":
                        return this.coordinates.compareTo(value, _field);
                    case "from":
                        return this.from.compareTo(value, _field);
                    case "to":
                        return this.to.compareTo(value, _field);
                    case "owner":
                        return this.owner.compareTo(value, _field);
                    default:
                        throw new IllegalArgumentException("Поле не найдено. Сравнение невозможно");

                }

        }
    }
}