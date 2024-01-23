package com.arsenyvekshin.backend.entities;


import com.arsenyvekshin.backend.exceptions.OutOfBoundException;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hitResults")
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double x;
    private double y;
    private double r;
    private boolean hit;
    private LocalDateTime time;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public Point(double x, double y, double r, User user) throws OutOfBoundException {
        if (!isValid(x, y, r)) throw new OutOfBoundException();

        this.x = x;
        this.y = y;
        this.r = r;
        this.hit = isHit(x, y, r);
        this.time = LocalDateTime.now();
        this.user = user;
    }

    public static boolean isValid(double x, double y, double r){
        return  x >= -5 && x <= 5 &&
                y >= -5 && y <= 5 &&
                r >= 0 && r <= 5;
    }

    public static boolean isHit(double x, double y, double r) {
        return isCircleHit(x, y, r) || isRectangleHit(x, y, r) || isTriangleHit(x, y, r);
    }

    private static boolean isTriangleHit(double x, double y, double r) {
        return (x >= 0 && y >= 0) && (y + x <= r);
    }

    private static boolean isRectangleHit(double x, double y, double r) {
        return (x >= 0 && y <= 0) && (x <= r && y >= -1 * r);
    }

    private static boolean isCircleHit(double x, double y, double r) {
        return (x <= 0 && y <= 0) && (x*x + y*y <= r*r);
    }


}
