package com.ArsenyVekshin.trig;



public class Cot {
    public static double cot(double x) {
        if (Sin.sin(x) == 0) throw new IllegalArgumentException("Функция не существует в точке x=" + x);
        return Cos.cos(x) / Sin.sin(x);
    }
}
