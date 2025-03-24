package com.ArsenyVekshin;

import com.ArsenyVekshin.log.*;
import com.ArsenyVekshin.trig.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static com.ArsenyVekshin.Func.f;

public class Main {
    public static String PATH = "C:/temp-testing/";
    public static String OUTPATH = "C:/temp-testing/out/";

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

    public static void generateFuncPlot(String filename,
                                            java.util.function.Function<Double, Double> module,
                                        double start, double step, int count) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filename)))) {
            writer.write("x, y\n");
            double x = start;
            double value;
            for (int i = 0; i < count; i++) {
                if(i%10000 == 0) System.out.println(i/10000 + " / " + count/10000);
                try {
                    value = module.apply(x);
                    if(value > 1000) value = 1000.0;
                    if(value < -1000) value = -1000.0;
                    writer.write(x + ", " + value + "\n");
                } catch (Exception e) { }
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

    }

    public static void genPlots() throws IOException {
        generateFuncPlot(OUTPATH + "f.csv", Func::f, -5.0, 0.01, 1000);
        generateFuncPlot(OUTPATH + "sin.csv", Sin::sin, -5.0, 0.01, 1000);
        generateFuncPlot(OUTPATH + "cos.csv", Cos::cos, -5.0, 0.01, 1000);
        generateFuncPlot(OUTPATH + "tan.csv", Tan::tan, -5.0, 0.01, 1000);
        generateFuncPlot(OUTPATH + "cot.csv", Cot::cot, -5.0, 0.01, 1000);
        generateFuncPlot(OUTPATH + "sec.csv", Sec::sec, -5.0, 0.01, 1000);
        generateFuncPlot(OUTPATH + "csc.csv", Csc::csc, -5.0, 0.01, 1000);

        generateFuncPlot(OUTPATH + "ln.csv", Ln::ln, 0.1, 0.01, 1000);
        generateFuncPlot(OUTPATH + "log_2.csv", Log_2::log_2, 0.01, 0.01, 1000);
        generateFuncPlot(OUTPATH + "log_3.csv", Log_3::log_3, 0.01, 0.01, 1000);
        generateFuncPlot(OUTPATH + "log_5.csv", Log_5::log_5, 0.01, 0.01, 1000);
        generateFuncPlot(OUTPATH + "log_10.csv", Log_10::log_10, 0.01, 0.01, 1000);



    }

    public static void main(String[] args) throws IOException {
        //System.out.println(Sin.sin(-2.5));
        //System.out.println(Cos.cos(-2.5));
        //System.out.println(Func.f(-2.5));
        //meow();
        genPlots();
    }
}