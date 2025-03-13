package com.ArsenyVekshin;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MathFunctions {
    public static String PATH = "C:/temp-testing/";

    // Функция для вычисления синуса через ряд Тейлора
    public static double sin(double x) {
        // Приводим x к интервалу [0, 2π]
        x = x % (2 * Math.PI);

        // Сумма ряда Тейлора
        double result = 0;
        double term = x;  // Первый элемент ряда
        int n = 1;         // Индекс степени в ряду
        int sign = 1;      // Знак для членов ряда

        for (int i = 0; i < 10; i++) { // Ограничиваем число итераций
            result += term;
            term *= -x * x / ((2 * n) * (2 * n + 1));  // Следующий член ряда
            n++;
        }

        return result;
    }

    public static double cos(double x) {
        return sin(Math.PI/2 - x);
    }

    public static double tan(double x) {
        if (cos(x) == 0) throw new IllegalArgumentException("Функция не существует в точке x=" + x);
        return sin(x) / cos(x);
    }

    public static double cot(double x) {
        if (sin(x) == 0) throw new IllegalArgumentException("Функция не существует в точке x=" + x);
        return cos(x) / sin(x);
    }
    public static double sec(double x) {
        if (cos(x) == 0) throw new IllegalArgumentException("Функция не существует в точке x="  + x);
        return 1.0 / cos(x);
    }
    public static double csc(double x) {
        if (sin(x) == 0) throw new IllegalArgumentException("Функция не существует в точке x=" + x);
        return 1.0 / sin(x);
    }


    public static double ln(double x) {
        if (x <= 0) {
            throw new IllegalArgumentException("x должно быть положительным");
        }

        // Преобразуем x так, чтобы вычислять ln(x) как ln((x - 1) / (x + 1)) для чисел > 1
        if (x == 1) {
            return 0.0;
        }

        double result = 0.0;
        double term = (x - 1) / (x + 1);
        double termSquared = term * term;
        double power = term;

        // Ряд Тейлора для ln(x) с использованием преобразования
        for (int n = 1; n < 100; n += 2) {
            result += power / n;
            power *= termSquared;
        }

        // Умножаем на 2, потому что использовали (x - 1) / (x + 1)
        return 2 * result;
    }

    public static double log_2(double x) {
        return ln(x) / ln(2.0);
    }
    public static double log_3(double x) {
        return ln(x) / ln(3.0);
    }
    public static double log_5(double x) {
        return ln(x) / ln(5.0);
    }
    public static double log_10(double x) {
        return ln(x) / ln(10.0);
    }


    public static double f(double x) {
        if (x <= 0)
            return (((((((((Math.pow(((Math.pow(Math.pow((((((csc(x) + tan(x)) * sec(x)) * csc(x)) * csc(x)) - cos(x)) + cos(x), 2), 3)) + csc(x)) / (((csc(x) + sec(x)) * sec(x)) - sin(x)), 2)) * cot(x)) * sec(x)) - ((csc(x) * (cos(x) / cot(x))) - csc(x))) * (((((tan(x) * cos(x)) - (tan(x) / cos(x))) / (sin(x) + tan(x))) - cos(x)) * cot(x))) - tan(x)) - (cos(x) + ((((tan(x) / ((cot(x) / tan(x)) * cot(x))) * (Math.pow(csc(x), 3) / ((tan(x) + sin(x)) - sec(x)))) * (cos(x) / (cos(x) + sec(x)))) * ((((((sin(x) * cot(x)) + (cot(x) / cot(x))) - Math.pow(cos(x), 3)) * (sin(x) + cot(x))) / (sec(x) / (Math.pow(Math.pow(cot(x), 2),  2) + ((((sec(x) + sin(x)) - csc(x)) / cot(x)) * Math.pow(csc(x), 2))))) / ((cot(x) + sin(x)) + csc(x)))))) / (((Math.pow(cos(x), 3) * cos(x)) + (Math.pow(sec(x) + (sec(x) - (Math.pow(tan(x) - ((Math.pow(cos(x), 3) * ((csc(x) / sec(x)) / cos(x))) + (csc(x) / sec(x))), 3))), 2))) * (((cot(x) / sec(x)) / sin(x)) + (((Math.pow(cot(x) - (sec(x) / sin(x)), 3)) / sec(x)) * (sin(x) + tan(x)))))) + cos(x));
        else
            return ((((Math.pow(log_10(x) / log_5(x), 3)) + Math.pow(ln(x), 3)) + log_3(x)) / (((log_2(x) / ln(x)) + log_3(x)) + ((Math.pow(log_2(x) * ln(x), 2)) / log_3(x))));
    }


    public static void generateCsv(String filename, java.util.function.Function<Double, Double> module, double start, double step, int count) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filename)))) {
            writer.write("X, Result\n");
            double x = start;
            for (int i = 0; i < count; i++) {
                writer.write(x + ", " + module.apply(x) + "\n");
                x += step;
            }
        }
    }

    public static void generateTestsCsv(String filename, java.util.function.Function<Double, Double> module, double start, double step, int count) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filename)))) {
            writer.write("X, ctrlValue, Expected\n");
            double x = start;
            for (int i = 0; i < count; i++) {
                writer.write(x + ", " + module.apply(x) + ", " + f(x) + "\n");
                x += step;
            }
        }
    }

    public static void generateTestsCsv(String filename,
                                            java.util.function.Function<Double, Double> module,
                                            java.util.function.Function<Double, Double> module2,
                                            double start, double step, int count) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filename)))) {
            writer.write("X, ctrlValue1, ctrlValue2, Expected\n");
            double x = start;
            for (int i = 0; i < count; i++) {
                writer.write(x + ", " + module.apply(x) + ", " + module2.apply(x) + ", " + f(x) + "\n");
                x += step;
            }
        }
    }


    public static void main(String[] args) throws IOException {
        System.out.println("Hello world!");
        System.out.println(f(1.1));
/*        generateCsv(PATH + "sin.csv", MathFunctions::sin, 0.0, 0.25, 10);
        generateCsv(PATH + "cos.csv", MathFunctions::cos, 0.0, 0.25, 10);
        generateCsv(PATH + "tan.csv", MathFunctions::tan, 0.0, 0.25, 10);
        generateCsv(PATH + "cot.csv", MathFunctions::cot, 0.0, 0.25, 10);
        generateCsv(PATH + "sec.csv", MathFunctions::sec, 0.0, 0.25, 10);
        generateCsv(PATH + "csc.csv", MathFunctions::csc, 0.0, 0.25, 10);
        generateCsv(PATH + "ln.csv", MathFunctions::ln, 1, 0.25, 10);
        generateCsv(PATH + "log2.csv", MathFunctions::log_2, 1, 0.25, 10);
        generateCsv(PATH + "log3.csv", MathFunctions::log_3, 1, 0.25, 10);
        generateCsv(PATH + "log5.csv", MathFunctions::log_5, 1, 0.25, 10);
        generateCsv(PATH + "log10.csv", MathFunctions::log_10, 1, 0.25, 10);
        generateCsv(PATH + "f.csv", MathFunctions::f, -10, 0.1, 200);*/

        generateTestsCsv(PATH + "sin.csv", MathFunctions::sin, 0.1, 0.25, 10);
        generateTestsCsv(PATH + "cos.csv", MathFunctions::cos, 0.1, 0.25, 10);
        generateTestsCsv(PATH + "tan.csv", MathFunctions::sin, MathFunctions::cos, 0.1, 0.25, 10);
        generateTestsCsv(PATH + "cot.csv", MathFunctions::tan, 0.1, 0.25, 10);
        generateTestsCsv(PATH + "sec.csv", MathFunctions::cos, 0.1, 0.25, 10);
        generateTestsCsv(PATH + "csc.csv", MathFunctions::sin, 0.1, 0.25, 10);

        generateTestsCsv(PATH + "log_2.csv", MathFunctions::ln, 1.0, 0.25, 10);
        generateTestsCsv(PATH + "log_3.csv", MathFunctions::ln, 1.0, 0.25, 10);
        generateTestsCsv(PATH + "log_5.csv", MathFunctions::ln, 1.0, 0.25, 10);
        generateTestsCsv(PATH + "log_10.csv", MathFunctions::ln, 1.0, 0.25, 10);
    }

}
