package com.ArsenyVekshin.lab3.utils;

import com.ArsenyVekshin.lab3.beans.Coordinates;

public class CoordinatesValidation {
    static public boolean validate(Coordinates coordinates) {
        return validateVariable(coordinates.getX(), Bounds.X_BOUNDS) &&
                validateVariable(coordinates.getY(), Bounds.Y_BOUNDS) &&
                validateVariable(coordinates.getR(), Bounds.R_BOUNDS);
    }

    static public boolean validateVariable(double var, Bounds bounds){
        if (var > bounds.getLeft() && var < bounds.getRight()) return true;
        return bounds.isInclusive() && (var == bounds.getLeft() || var == bounds.getRight());
    }
}
