package com.ArsenyVekshin.lab3.utils;

public enum Bounds {
    X_BOUNDS(-5, 5, true),
    Y_BOUNDS(-5, 5, false),
    R_BOUNDS(1, 3, true);

    private final double left;
    private final double right;
    private final boolean inclusive;

    Bounds(double left, double right, boolean inclusive) {
        this.left = left;
        this.right = right;
        this.inclusive = inclusive;
    }

    public double getLeft() {
        return left;
    }

    public double getRight() {
        return right;
    }

    public boolean isInclusive() {
        return inclusive;
    }
}
