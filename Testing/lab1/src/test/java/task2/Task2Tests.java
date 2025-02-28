package task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static com.ArsenyVekshin.task2.BucketSort.bucketSort;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task2Tests {

    @Test
    @DisplayName("Сортировка пустого списка")
    public void testEmptyList() {
        List<Number> numbers = new ArrayList<>();
        List<Number> expected = new ArrayList<>();
        bucketSort(numbers);
        assertEquals(expected, numbers);
    }

    @Test
    @DisplayName("Сортировка списка длинны 1")
    public void testSingleElementList() {
        List<Number> numbers = Arrays.asList(5);
        List<Number> expected = Arrays.asList(5);
        bucketSort(numbers);
        assertEquals(expected, numbers);
    }

    @Test
    @DisplayName("Сортировка случайного списка длинны 10^6")
    public void testLargeRandomList() {
        List<Number> numbers = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 1000000; i++) {
            numbers.add(random.nextDouble() * 1000);
        }
        List<Number> expected = new ArrayList<>(numbers);
        expected.sort(Comparator.comparingDouble(Number::doubleValue));
        bucketSort(numbers);
        assertEquals(expected, numbers);
    }


    @Test
    @DisplayName("Сортировка null списка")
    public void testNullList() {
        List<Number> numbers = null;
        assertDoesNotThrow(() -> bucketSort(numbers));
    }

    @Test
    @DisplayName("Сортировка отсортированного списка")
    public void testSortedArray() {
        List<Number> numbers = new ArrayList<>(Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0));
        List<Number> expected = new ArrayList<>(numbers);
        bucketSort(numbers);
        assertEquals(expected, numbers);
    }
}
