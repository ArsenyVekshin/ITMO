package com.ArsenyVekshin.lab3.utils;

import com.ArsenyVekshin.lab3.beans.Coordinates;

public class AreaCheck {
    public boolean isHit(Coordinates coordinates) {
        return coordinates != null && isHit(coordinates.getX(), coordinates.getY(), coordinates.getR());
    }

    public boolean isHit(double x, double y, double r) {
        return isCircleHit(x, y, r) || isRectangleHit(x, y, r) || isTriangleHit(x, y, r);
    }

    private boolean isTriangleHit(double x, double y, double r) {
        return (x >= 0 && y >= 0) && (y + x <= r/2);
    }

    private boolean isRectangleHit(double x, double y, double r) {
        return (x <= 0 && y <= 0) && (x >= -1*r && y >= -1*r);
    }

    private boolean isCircleHit(double x, double y, double r) {
        return (x <= 0 && y >= 0) && (x*x + y*y <= r*r);
    }
}
