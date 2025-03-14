package com.ArsenyVekshin.trig;

import static com.ArsenyVekshin.trig.Sin.sin;

public class Csc {
    public static double csc(double x) {
        if (sin(x) == 0) throw new IllegalArgumentException("Функция не существует в точке x=" + x);
        return 1.0 / sin(x);
    }
}
