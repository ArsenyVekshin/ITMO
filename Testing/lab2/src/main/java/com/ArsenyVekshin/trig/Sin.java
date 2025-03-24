package com.ArsenyVekshin.trig;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Sin {
    public static double sin(double x) {
        x = x % (2 * Math.PI);

        double result = 0;
        double term = x;
        int n = 1;

        for (int i = 0; i < 1000; i++) {
            result += term;
            term *= -x * x / ((2 * n) * (2 * n + 1));
            n++;
        }

        BigDecimal bd = new BigDecimal(result).setScale(5, RoundingMode.HALF_UP); // Указываем количество знаков после запятой
        return result;
        //return bd.doubleValue();
    }


}
