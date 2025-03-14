package integration.trig;

import com.ArsenyVekshin.trig.Cos;
import com.ArsenyVekshin.trig.Sin;
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

public class CosIntegrationTest {
    private static final double DELTA = 1e-5;
    private static final MockedStatic<Sin> mocked = Mockito.mockStatic(Sin.class);

    @AfterAll
    static void closeMock() {
        mocked.close();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "cos.csv", numLinesToSkip = 1)
    public void testMockedCos(double x, double ctrlValue, double selfExpected, double funcExpected) {
        mocked.when(() -> Sin.sin(anyDouble())).thenAnswer(invocation -> {
            double arg = invocation.getArgument(0);
            BigDecimal bd = BigDecimal.valueOf(Math.sin(arg)).setScale(5, RoundingMode.HALF_UP);
            return bd.doubleValue();
        });

        assertEquals(selfExpected, Cos.cos(x), DELTA);
        assertEquals(funcExpected, f(x), DELTA);
        mocked.verify(() -> Sin.sin(anyDouble()), atLeastOnce());
    }
}
