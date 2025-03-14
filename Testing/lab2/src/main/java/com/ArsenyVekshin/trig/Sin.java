package com.ArsenyVekshin.trig;

public class Sin {
    public static double sin(double x) {
        //System.out.println("Sin.sin(" + x + ") was called");
        x = x % (2 * Math.PI);

        double result = 0;
        double term = x;
        int n = 1;

        for (int i = 0; i < 50; i++) {
            result += term;
            term *= -x * x / ((2 * n) * (2 * n + 1));
            n++;
        }

        return result;
    }


}
