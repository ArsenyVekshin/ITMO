package com.ArsenyVekshin;

import com.ArsenyVekshin.trig.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static com.ArsenyVekshin.Func.f;

public class Main {
    public static String PATH = "C:/temp-testing/";

    static MathFunctions mathFunctions = new MathFunctions();


    public static void generateTestsCsv(String filename,
                                        java.util.function.Function<Double, Double> module,
                                        java.util.function.Function<Double, Double> curr,
                                        double start, double step, int count) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filename)))) {
            writer.write("X, ctrlValue, selfExpected, funcExpected\n");
            double x = start;
            for (int i = 0; i < count; i++) {
                writer.write(x + ", " + module.apply(x) + ", " + curr.apply(x) + ", " + f(x) + "\n");
                x += step;
            }
        }
    }

    public static void generateTestsCsv(String filename,
                                 java.util.function.Function<Double, Double> module,
                                 java.util.function.Function<Double, Double> module2,
                                 java.util.function.Function<Double, Double> curr,
                                 double start, double step, int count) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filename)))) {
            writer.write("X, ctrlValue1, ctrlValue2, selfExpected, funcExpected\n");
            double x = start;
            for (int i = 0; i < count; i++) {
                writer.write(x + ", " + module.apply(x) + ", " + module2.apply(x) + ", " + curr.apply(x) + ", " + f(x) + "\n");
                x += step;
            }
        }
    }

    public static void generateFuncTestsCsv(String filename,
                                            java.util.function.Function<Double, Double> module,
                                        double start, double step, int count) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filename)))) {
            writer.write("X, funcExpected\n");
            double x = start;
            for (int i = 0; i < count; i++) {
                writer.write(x + ", " + module.apply(x) + "\n");
                x += step;
            }
        }
    }




    public static void meow() throws IOException {
        generateTestsCsv(PATH + "sin.csv", Sin::sin, Sin::sin, -2.5, 0.25, 10);
        generateTestsCsv(PATH + "cos.csv", Cos::cos, Cos::cos, -2.5, 0.25, 10);
        generateTestsCsv(PATH + "tan.csv", Sin::sin, Cos::cos, Tan::tan, -2.5, 0.25, 10);
        generateTestsCsv(PATH + "cot.csv", Tan::tan, Cot::cot, -2.5, 0.25, 10);
        generateTestsCsv(PATH + "sec.csv", Cos::cos, Sec::sec, -2.5, 0.25, 10);
        generateTestsCsv(PATH + "csc.csv", Sin::sin, Csc::csc, -2.5, 0.25, 10);
        generateFuncTestsCsv(PATH + "func.csv", Func::f, 0.25, 0.0005, 1000000000);
    }

    public static void main(String[] args) throws IOException {
        //System.out.println(Sin.sin(-2.5));
        //System.out.println(Cos.cos(-2.5));
        //System.out.println(Func.f(-2.5));
        meow();
    }
}