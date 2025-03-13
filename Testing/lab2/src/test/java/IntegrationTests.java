import com.ArsenyVekshin.MathFunctions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.atLeastOnce;

public class IntegrationTests {
    private static final double DELTA = 1e-5;
    private static final String PATH = "C:/temp-testing/";


    @ParameterizedTest
    @CsvFileSource(resources = "sin.csv", numLinesToSkip = 1)
    public void testMockedSin(double x, double sinValue, double expected) {
        try (MockedStatic<MathFunctions> mockedSin = Mockito.mockStatic(MathFunctions.class)) {
            mockedSin.when(() -> MathFunctions.sin(anyDouble())).thenReturn(sinValue);

            double result = MathFunctions.f(x);
            assertEquals(expected, result, DELTA);
            mockedSin.verify(() -> MathFunctions.sin(anyDouble()), atLeastOnce());
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "cos.csv", numLinesToSkip = 1)
    public void testMockedCos(double x, double cosValue, double expected) {
        try (MockedStatic<MathFunctions> mockedSin = Mockito.mockStatic(MathFunctions.class)) {
            mockedSin.when(() -> MathFunctions.cos(anyDouble())).thenReturn(cosValue);

            double result = MathFunctions.f(x);
            assertEquals(expected, result, DELTA);
            mockedSin.verify(() -> MathFunctions.cos(anyDouble()), atLeastOnce());
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "tan.csv", numLinesToSkip = 1)
    public void testMockedTan(double x, double sinValue, double cosValue, double expected) {
        try (MockedStatic<MathFunctions> mockedSin = Mockito.mockStatic(MathFunctions.class)) {
            mockedSin.when(() -> MathFunctions.sin(anyDouble())).thenReturn(sinValue);
            mockedSin.when(() -> MathFunctions.cos(anyDouble())).thenReturn(cosValue);

            double result = MathFunctions.f(x);
            assertEquals(expected, result, DELTA);
            mockedSin.verify(() -> MathFunctions.tan(anyDouble()), atLeastOnce());
        }
    }
}
