package com.ArsenyVekshin.trig;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.ArsenyVekshin.trig.Sin.sin;

public class Cos {
    public static double cos(double x) {
        BigDecimal bd = BigDecimal.valueOf(sin(Math.PI / 2 - x)).setScale(5, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
