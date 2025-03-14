package com.ArsenyVekshin.log;

public class Ln {
    public static double ln(double x) {
        if (x <= 0) {
            throw new IllegalArgumentException("x должно быть положительным");
        }

        if (x == 1) {
            return 0.0;
        }

        double result = 0.0;
        double term = (x - 1) / (x + 1);
        double termSquared = term * term;
        double power = term;

        for (int n = 1; n < 100; n += 2) {
            result += power / n;
            power *= termSquared;
        }

        return 2 * result;
    }
}
