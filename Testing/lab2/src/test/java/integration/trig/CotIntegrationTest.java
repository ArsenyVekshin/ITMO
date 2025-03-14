package integration.trig;

import com.ArsenyVekshin.trig.Cos;
import com.ArsenyVekshin.trig.Cot;
import com.ArsenyVekshin.trig.Sin;
import com.ArsenyVekshin.trig.Tan;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.ArsenyVekshin.Func.f;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.atLeastOnce;

public class CotIntegrationTest {
    private static final double DELTA = 1e-5;
    private static final MockedStatic<Sin> mockedSin = Mockito.mockStatic(Sin.class);
    private static final MockedStatic<Cos> mockedCos = Mockito.mockStatic(Cos.class);

    @AfterAll
    static void closeMock() {
        mockedSin.close();
        mockedCos.close();
    }


    @ParameterizedTest
    @CsvFileSource(resources = "cot.csv", numLinesToSkip = 1)
    public void testMockedCot(double x, double ctrlValue, double selfExpected, double funcExpected) {
        mockedSin.when(() -> Sin.sin(anyDouble())).thenAnswer(invocation -> {
            double arg = invocation.getArgument(0);
            BigDecimal bd = BigDecimal.valueOf(Math.sin(arg)).setScale(5, RoundingMode.HALF_UP);
            return bd.doubleValue();
        });
        mockedCos.when(() -> Cos.cos(anyDouble())).thenAnswer(invocation -> {
            double arg = invocation.getArgument(0);
            BigDecimal bd = BigDecimal.valueOf(Math.cos(arg)).setScale(5, RoundingMode.HALF_UP);
            return bd.doubleValue();
        });
        assertEquals(selfExpected, Cot.cot(x), DELTA);
        assertEquals(funcExpected, f(x), DELTA);
        mockedSin.verify(() -> Sin.sin(anyDouble()), atLeastOnce());
        mockedCos.verify(() -> Cos.cos(anyDouble()), atLeastOnce());
    }
}
