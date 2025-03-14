package com.ArsenyVekshin.trig;

import static com.ArsenyVekshin.trig.Cos.cos;
import static com.ArsenyVekshin.trig.Sin.sin;

public class Cot {
    public static double cot(double x) {
        if (sin(x) == 0) throw new IllegalArgumentException("Функция не существует в точке x=" + x);
        return cos(x) / sin(x);
    }
}
