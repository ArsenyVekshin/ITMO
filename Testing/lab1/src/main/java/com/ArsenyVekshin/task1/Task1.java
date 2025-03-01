package com.ArsenyVekshin.task1;

import com.ArsenyVekshin.task1.ArctanSeries;


public class   Task1 {
    public static void main(String[] args) {
        // Печать заголовка таблицы
        System.out.println("   x    | arctan(x)");
        System.out.println("---------------------");

        // Генерация таблицы значений arctan(x) с шагом 0.1
        for (double x = -1.0; x <= 1.0; x += 0.1) {
            double result = ArctanSeries.arctg(x, 10000);
            // Печать значения x и arctan(x)
            System.out.printf("%6.1f  | %8.6f | %8.6f | %8.6f \n", x, result, Math.atan(x), result - Math.atan(x));
        }
    }
}
