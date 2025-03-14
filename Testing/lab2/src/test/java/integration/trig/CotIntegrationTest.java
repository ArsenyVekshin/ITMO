package integration.trig;

import com.ArsenyVekshin.trig.Cot;
import com.ArsenyVekshin.trig.Sin;
import com.ArsenyVekshin.trig.Tan;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static com.ArsenyVekshin.Func.f;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.atLeastOnce;

public class CotIntegrationTest {
    private static final double DELTA = 1e-5;
    private static final MockedStatic<Tan> mocked = Mockito.mockStatic(Tan.class);

    @AfterAll
    static void closeMock() {
        mocked.close();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "cot.csv", numLinesToSkip = 1)
    public void testMockedCot(double x, double ctrlValue, double selfExpected, double funcExpected) {
        mocked.when(() -> Tan.tan(anyDouble())).thenAnswer(invocation -> {
            double arg = invocation.getArgument(0);
            return Math.tan(arg);
        });

        assertEquals(selfExpected, Cot.cot(x), DELTA);
        assertEquals(funcExpected, f(x), DELTA);
        mocked.verify(() -> Sin.sin(anyDouble()), atLeastOnce());
    }
}
