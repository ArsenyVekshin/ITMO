package com.ArsenyVekshin.trig;

import static com.ArsenyVekshin.trig.Cos.cos;

public class Sec {
    public static double sec(double x) {
        if (cos(x) == 0) throw new IllegalArgumentException("Функция не существует в точке x=" + x);
        return 1.0 / cos(x);
    }
}
