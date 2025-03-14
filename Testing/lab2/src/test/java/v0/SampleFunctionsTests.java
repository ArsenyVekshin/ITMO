package v0;

import com.ArsenyVekshin.MathFunctions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SampleFunctionsTests {

    private static final double DELTA = 1e-5;
    private final MathFunctions mathFunctions = new MathFunctions();

    @Test
    void testSin() {
        assertEquals(Math.sin(0), mathFunctions.sin(0), DELTA);
        assertEquals(Math.sin(Math.PI / 6), mathFunctions.sin(Math.PI / 6), DELTA);
        assertEquals(Math.sin(Math.PI / 4), mathFunctions.sin(Math.PI / 4), DELTA);
        assertEquals(Math.sin(Math.PI / 3), mathFunctions.sin(Math.PI / 3), DELTA);
        assertEquals(Math.sin(Math.PI / 2), mathFunctions.sin(Math.PI / 2), DELTA);
    }

    @Test
    void testCos() {
        assertEquals(Math.cos(0), mathFunctions.cos(0), DELTA);
        assertEquals(Math.cos(Math.PI / 6), mathFunctions.cos(Math.PI / 6), DELTA);
        assertEquals(Math.cos(Math.PI / 4), mathFunctions.cos(Math.PI / 4), DELTA);
        assertEquals(Math.cos(Math.PI / 3), mathFunctions.cos(Math.PI / 3), DELTA);
        assertEquals(Math.cos(Math.PI / 2), mathFunctions.cos(Math.PI / 2), DELTA);
    }

    @Test
    void testTan() {
        assertEquals(Math.tan(Math.PI / 6), mathFunctions.tan(Math.PI / 6), DELTA);
        assertEquals(Math.tan(Math.PI / 4), mathFunctions.tan(Math.PI / 4), DELTA);
        assertEquals(Math.tan(Math.PI / 3), mathFunctions.tan(Math.PI / 3), DELTA);
    }

    @Test
    void testCot() {
        assertEquals(1 / Math.tan(Math.PI / 6), mathFunctions.cot(Math.PI / 6), DELTA);
        assertEquals(1 / Math.tan(Math.PI / 4), mathFunctions.cot(Math.PI / 4), DELTA);
        assertEquals(1 / Math.tan(Math.PI / 3), mathFunctions.cot(Math.PI / 3), DELTA);
    }

    @Test
    void testSec() {
        assertEquals(1 / Math.cos(Math.PI / 6), mathFunctions.sec(Math.PI / 6), DELTA);
        assertEquals(1 / Math.cos(Math.PI / 4), mathFunctions.sec(Math.PI / 4), DELTA);
        assertEquals(1 / Math.cos(Math.PI / 3), mathFunctions.sec(Math.PI / 3), DELTA);
    }

    @Test
    void testCsc() {
        assertEquals(1 / Math.sin(Math.PI / 6), mathFunctions.csc(Math.PI / 6), DELTA);
        assertEquals(1 / Math.sin(Math.PI / 4), mathFunctions.csc(Math.PI / 4), DELTA);
        assertEquals(1 / Math.sin(Math.PI / 3), mathFunctions.csc(Math.PI / 3), DELTA);
    }
}
