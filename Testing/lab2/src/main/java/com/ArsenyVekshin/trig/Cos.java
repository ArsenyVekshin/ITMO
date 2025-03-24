package com.ArsenyVekshin.trig;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class Cos {
    public static double cos(double x) {
        BigDecimal bd = BigDecimal.valueOf(Sin.sin(Math.PI / 2 - x)).setScale(5, RoundingMode.HALF_UP);
        //return bd.doubleValue();
        return Sin.sin(Math.PI / 2 - x);
    }
}
