package com.ArsenyVekshin;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class MathFunctions implements Cloneable {

    public String PATH = "C:/temp-testing/";


    private String hello() {
        return "hello";
    }

    public double sin(double x) {
        x = x % (2 * Math.PI);

        double result = 0;
        double term = x;
        int n = 1;
        int sign = 1;

        for (int i = 0; i < 10000; i++) {
            result += term;
            term *= -x * x / ((2 * n) * (2 * n + 1));
            n++;
        }

        return result;
    }

    public double cos(double x) {
        return sin(Math.PI / 2 - x);
    }

    public double tan(double x) {
        if (cos(x) == 0) throw new IllegalArgumentException("Функция не существует в точке x=" + x);
        return sin(x) / cos(x);
    }

    public double cot(double x) {
        if (sin(x) == 0) throw new IllegalArgumentException("Функция не существует в точке x=" + x);
        return cos(x) / sin(x);
    }

    public double sec(double x) {
        if (cos(x) == 0) throw new IllegalArgumentException("Функция не существует в точке x=" + x);
        return 1.0 / cos(x);
    }

    public double csc(double x) {
        if (sin(x) == 0) throw new IllegalArgumentException("Функция не существует в точке x=" + x);
        return 1.0 / sin(x);
    }


    public double ln(double x) {
        if (x <= 0) {
            throw new IllegalArgumentException("x должно быть положительным");
        }

        if (x == 1) {
            return 0.0;
        }

        double result = 0.0;
        double term = (x - 1) / (x + 1);
        double termSquared = term * term;
        double power = term;

        for (int n = 1; n < 100; n += 2) {
            result += power / n;
            power *= termSquared;
        }

        return 2 * result;
    }

    public double log_2(double x) {
        return ln(x) / ln(2.0);
    }

    public double log_3(double x) {
        return ln(x) / ln(3.0);
    }

    public double log_5(double x) {
        return ln(x) / ln(5.0);
    }

    public double log_10(double x) {
        return ln(x) / ln(10.0);
    }


    public double f(double x) {
        if (x <= 0)
            return (((((((((Math.pow(((Math.pow(Math.pow((((((csc(x) + tan(x)) * sec(x)) * csc(x)) * csc(x)) - cos(x)) + cos(x), 2), 3)) + csc(x)) / (((csc(x) + sec(x)) * sec(x)) - sin(x)), 2)) * cot(x)) * sec(x)) - ((csc(x) * (cos(x) / cot(x))) - csc(x))) * (((((tan(x) * cos(x)) - (tan(x) / cos(x))) / (sin(x) + tan(x))) - cos(x)) * cot(x))) - tan(x)) - (cos(x) + ((((tan(x) / ((cot(x) / tan(x)) * cot(x))) * (Math.pow(csc(x), 3) / ((tan(x) + sin(x)) - sec(x)))) * (cos(x) / (cos(x) + sec(x)))) * ((((((sin(x) * cot(x)) + (cot(x) / cot(x))) - Math.pow(cos(x), 3)) * (sin(x) + cot(x))) / (sec(x) / (Math.pow(Math.pow(cot(x), 2), 2) + ((((sec(x) + sin(x)) - csc(x)) / cot(x)) * Math.pow(csc(x), 2))))) / ((cot(x) + sin(x)) + csc(x)))))) / (((Math.pow(cos(x), 3) * cos(x)) + (Math.pow(sec(x) + (sec(x) - (Math.pow(tan(x) - ((Math.pow(cos(x), 3) * ((csc(x) / sec(x)) / cos(x))) + (csc(x) / sec(x))), 3))), 2))) * (((cot(x) / sec(x)) / sin(x)) + (((Math.pow(cot(x) - (sec(x) / sin(x)), 3)) / sec(x)) * (sin(x) + tan(x)))))) + cos(x));
        else
            return ((((Math.pow(log_10(x) / log_5(x), 3)) + Math.pow(ln(x), 3)) + log_3(x)) / (((log_2(x) / ln(x)) + log_3(x)) + ((Math.pow(log_2(x) * ln(x), 2)) / log_3(x))));
    }


    public void generateCsv(String filename, java.util.function.Function<Double, Double> module, double start, double step, int count) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filename)))) {
            writer.write("X, Result\n");
            double x = start;
            for (int i = 0; i < count; i++) {
                writer.write(x + ", " + module.apply(x) + "\n");
                x += step;
            }
        }
    }

    public void generateTestsCsv(String filename,
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

    public void generateTestsCsv(String filename,
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

    public void generateTestsCsvLog(String filename,
                                    java.util.function.Function<Double, Double> module,
                                    double order,
                                    java.util.function.Function<Double, Double> curr,
                                    double start, double step, int count) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filename)))) {
            writer.write("X, ctrlValue1, ctrlValue2, selfExpected, funcExpected\n");
            double x = start;
            for (int i = 0; i < count; i++) {
                writer.write(x + ", " + module.apply(x) + ", " + module.apply(order) + ", " + curr.apply(x) + ", " + f(x) + "\n");
                x += step;
            }
        }
    }


    public void meow(String[] args) throws IOException {
        System.out.println("Hello world!");
        System.out.println(f(1.1));
/*        generateCsv(PATH + "sin.csv", this::sin, 0.0, 0.25, 10);
        generateCsv(PATH + "cos.csv", this::cos, 0.0, 0.25, 10);
        generateCsv(PATH + "tan.csv", this::tan, 0.0, 0.25, 10);
        generateCsv(PATH + "cot.csv", this::cot, 0.0, 0.25, 10);
        generateCsv(PATH + "sec.csv", this::sec, 0.0, 0.25, 10);
        generateCsv(PATH + "csc.csv", this::csc, 0.0, 0.25, 10);
        generateCsv(PATH + "ln.csv", this::ln, 1, 0.25, 10);
        generateCsv(PATH + "log2.csv", this::log_2, 1, 0.25, 10);
        generateCsv(PATH + "log3.csv", this::log_3, 1, 0.25, 10);
        generateCsv(PATH + "log5.csv", this::log_5, 1, 0.25, 10);
        generateCsv(PATH + "log10.csv", this::log_10, 1, 0.25, 10);
        generateCsv(PATH + "f.csv", this::f, -10, -2.5, 200);*/

        generateTestsCsv(PATH + "sin.csv", this::sin, this::sin, -2.5, 0.25, 10);
        generateTestsCsv(PATH + "cos.csv", this::cos, this::cos, -2.5, 0.25, 10);
        generateTestsCsv(PATH + "tan.csv", this::sin, this::cos, this::tan, -2.5, 0.25, 10);
        generateTestsCsv(PATH + "cot.csv", this::tan, this::cot, -2.5, 0.25, 10);
        generateTestsCsv(PATH + "sec.csv", this::cos, this::sec, -2.5, 0.25, 10);
        generateTestsCsv(PATH + "csc.csv", this::sin, this::csc, -2.5, 0.25, 10);
        generateTestsCsvLog(PATH + "log_2.csv", this::ln, 2, this::log_2, 1.0, 0.25, 10);
        generateTestsCsvLog(PATH + "log_3.csv", this::ln, 1, this::log_3, 1.0, 0.25, 10);
        generateTestsCsvLog(PATH + "log_5.csv", this::ln, 5, this::log_5, 1.0, 0.25, 10);
        generateTestsCsvLog(PATH + "log_10.csv", this::ln, 10, this::log_10, 1.0, 0.25, 10);
    }

    @Override
    public MathFunctions clone() throws CloneNotSupportedException {
        return (MathFunctions) super.clone();
    }
}
