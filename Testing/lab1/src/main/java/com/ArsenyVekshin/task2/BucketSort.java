package com.ArsenyVekshin.task2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BucketSort {

    public static void bucketSort(List<Number> list) {
        if (list == null || list.size() <= 1) return;

        // Создаем изменяемую копию списка
        List<Number> mutableList = new ArrayList<>(list);

        // Преобразуем список чисел в список double
        List<Double> doubleList = new ArrayList<>();
        for (Number num : mutableList) {
            doubleList.add(num.doubleValue());
        }

        // Определяем минимальное и максимальное значение
        double minValue = Collections.min(doubleList);
        double maxValue = Collections.max(doubleList);

        // Количество корзин
        int bucketCount = (int) Math.ceil(Math.sqrt(doubleList.size()));
        List<List<Double>> buckets = new ArrayList<>(bucketCount);
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }

        // Распределение элементов по корзинам
        for (double num : doubleList) {
            int bucketIndex = (int) ((num - minValue) / (maxValue - minValue + 1) * bucketCount);
            buckets.get(bucketIndex).add(num);
        }

        // Сортировка каждой корзины и сбор элементов обратно в список
        mutableList.clear();
        for (List<Double> bucket : buckets) {
            Collections.sort(bucket);
            mutableList.addAll(bucket);
        }

        // Обновляем входной список
        list.clear();
        list.addAll(mutableList);
    }

    public static void main(String[] args) {
        List<Number> numbers = Arrays.asList(4.2, 2, 7.5, 3, 1.1, 9, 6, 5.3);
        System.out.println("До сортировки: " + numbers);

        List<Number> mutableList = new ArrayList<>(numbers);
        bucketSort(mutableList);

        System.out.println("После сортировки: " + mutableList);
    }
}
