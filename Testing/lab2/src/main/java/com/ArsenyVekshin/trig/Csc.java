package com.ArsenyVekshin.trig;


public class Csc {
    public static double csc(double x) {
        if (Sin.sin(x) == 0) throw new IllegalArgumentException("Функция не существует в точке x=" + x);
        return 1.0 / Sin.sin(x);
    }
}
