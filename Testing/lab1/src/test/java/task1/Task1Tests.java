package task1;


import com.ArsenyVekshin.task1.ArctanSeries;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task1Tests {
    @Test
    @DisplayName("arctan(0)")
    public void testArctgZero() {
        double result = ArctanSeries.arctg(0, 100);
        assertEquals(0.0, result, 1e-6);  // Погрешность 1e-6
    }

    @Test
    @DisplayName("arctan(1)")
    public void testArctgOne() {
        double result = ArctanSeries.arctg(1, 1000000);
        assertEquals(Math.PI / 4, result, 1e-6);  // Погрешность 1e-6
    }

    @Test
    @DisplayName("arctan(-1)")
    public void testArctgNegativeOne() {
        double result = ArctanSeries.arctg(-1, 1000000);
        assertEquals(-Math.PI / 4, result, 1e-6);  // Погрешность 1e-6
    }

    @Test
    @DisplayName("arctan(0.5) - сравнение с Math.atan()")
    public void testArctgPositiveHalf() {
        double result = ArctanSeries.arctg(0.5, 1000);
        assertEquals(Math.atan(0.5), result, 1e-6);
    }

    @Test
    @DisplayName("arctan(x>100) - сравнение с Math.atan()")
    public void testArctgLargeValue() {
        double result = ArctanSeries.arctg(1000, 100000);
        assertEquals(Math.atan(1000), result, 1e-6);
    }

    @Test
    @DisplayName("arctan(x->0)")
    public void testArctgSmallValue() {
        double result = ArctanSeries.arctg(0.0001, 1000);
        assertEquals(0.0001, result, 1e-6);
    }

    @Test
    @DisplayName("Увеличенная точность. arctan(0,5) - сравнение с Math.atan()")
    public void testArctgHighTerms() {
        double result = ArctanSeries.arctg(0.5, 1000000000);
        assertEquals(Math.atan(0.5), result, 1e-9);  // Точность увеличивается
    }

}
