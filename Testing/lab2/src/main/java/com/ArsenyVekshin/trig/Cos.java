package com.ArsenyVekshin.trig;

import static com.ArsenyVekshin.trig.Sin.sin;

public class Cos {
    public static double cos(double x) {
        return sin(Math.PI / 2 - x);
    }
}
