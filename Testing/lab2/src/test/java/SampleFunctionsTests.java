import com.ArsenyVekshin.MathFunctions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SampleFunctionsTests {
    private static final double DELTA = 1e-5;

    @Test
    void testSin() {
        assertEquals(Math.sin(0), MathFunctions.sin(0), DELTA);
        assertEquals(Math.sin(Math.PI / 6), MathFunctions.sin(Math.PI / 6), DELTA);
        assertEquals(Math.sin(Math.PI / 4), MathFunctions.sin(Math.PI / 4), DELTA);
        assertEquals(Math.sin(Math.PI / 3), MathFunctions.sin(Math.PI / 3), DELTA);
        assertEquals(Math.sin(Math.PI / 2), MathFunctions.sin(Math.PI / 2), DELTA);
    }

    @Test
    void testCos() {
        assertEquals(Math.cos(0), MathFunctions.cos(0), DELTA);
        assertEquals(Math.cos(Math.PI / 6), MathFunctions.cos(Math.PI / 6), DELTA);
        assertEquals(Math.cos(Math.PI / 4), MathFunctions.cos(Math.PI / 4), DELTA);
        assertEquals(Math.cos(Math.PI / 3), MathFunctions.cos(Math.PI / 3), DELTA);
        assertEquals(Math.cos(Math.PI / 2), MathFunctions.cos(Math.PI / 2), DELTA);
    }

    @Test
    void testTan() {
        assertEquals(Math.tan(Math.PI / 6), MathFunctions.tan(Math.PI / 6), DELTA);
        assertEquals(Math.tan(Math.PI / 4), MathFunctions.tan(Math.PI / 4), DELTA);
        assertEquals(Math.tan(Math.PI / 3), MathFunctions.tan(Math.PI / 3), DELTA);
    }

    @Test
    void testCot() {
        assertEquals(1 / Math.tan(Math.PI / 6), MathFunctions.cot(Math.PI / 6), DELTA);
        assertEquals(1 / Math.tan(Math.PI / 4), MathFunctions.cot(Math.PI / 4), DELTA);
        assertEquals(1 / Math.tan(Math.PI / 3), MathFunctions.cot(Math.PI / 3), DELTA);
    }

    @Test
    void testSec() {
        assertEquals(1 / Math.cos(Math.PI / 6), MathFunctions.sec(Math.PI / 6), DELTA);
        assertEquals(1 / Math.cos(Math.PI / 4), MathFunctions.sec(Math.PI / 4), DELTA);
        assertEquals(1 / Math.cos(Math.PI / 3), MathFunctions.sec(Math.PI / 3), DELTA);
    }

    @Test
    void testCsc() {
        assertEquals(1 / Math.sin(Math.PI / 6), MathFunctions.csc(Math.PI / 6), DELTA);
        assertEquals(1 / Math.sin(Math.PI / 4), MathFunctions.csc(Math.PI / 4), DELTA);
        assertEquals(1 / Math.sin(Math.PI / 3), MathFunctions.csc(Math.PI / 3), DELTA);
    }
}
