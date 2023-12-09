package com.ArsenyVekshin.lab3.db;

import com.ArsenyVekshin.lab3.beans.Coordinates;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hitResults")
public class HitResult implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    private Integer id;
    private String sessionId;

    private double x;
    private double y;
    private double r;
    private String currentTime;
    private boolean result;

    private boolean removed = false;

    public HitResult(String sessionId, Coordinates coordinates, String currentTime, boolean result) {
        this.sessionId = sessionId;
        this.x = coordinates.getX();
        this.y = coordinates.getY();
        this.r = coordinates.getR();
        this.currentTime = currentTime;
        this.result = result;
    }
}
