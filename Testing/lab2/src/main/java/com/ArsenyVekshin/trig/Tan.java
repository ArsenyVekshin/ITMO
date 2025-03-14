package com.ArsenyVekshin.trig;

import static com.ArsenyVekshin.trig.Cos.cos;
import static com.ArsenyVekshin.trig.Sin.sin;

public class Tan {
    public static double tan(double x) {
        if (cos(x) == 0) throw new IllegalArgumentException("Функция не существует в точке x=" + x);
        return sin(x) / cos(x);
    }
}
