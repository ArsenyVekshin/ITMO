package com.ArsenyVekshin;

import com.ArsenyVekshin.trig.Cos;
import com.ArsenyVekshin.trig.Sin;

import java.io.IOException;

public class Main {
    static MathFunctions mathFunctions = new MathFunctions();

    public static void main(String[] args) throws IOException {
        System.out.println(Sin.sin(-2.5));
        System.out.println(Cos.cos(-2.5));
        System.out.println(Func.f(-2.5));
        //mathFunctions.meow(args);
    }
}