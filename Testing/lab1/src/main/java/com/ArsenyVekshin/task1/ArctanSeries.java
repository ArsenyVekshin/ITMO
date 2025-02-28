package com.ArsenyVekshin.task1;

public class ArctanSeries {

    public static double arctg(double x, int terms) {
        if (x > 1) {
            return Math.PI / 2 - arctg(1 / x, terms);
        } else if (x < -1) {
            return -Math.PI / 2 - arctg(1 / x, terms);
        }

        double result = 0.0;
        double power = x;
        for (int n = 0; n < terms; n++) {
            int denominator = 2 * n + 1;
            if (n % 2 == 0) {
                result += power / denominator;
            } else {
                result -= power / denominator;
            }
            power *= x * x; // x^(2n+1)
        }
        return result;
    }
}