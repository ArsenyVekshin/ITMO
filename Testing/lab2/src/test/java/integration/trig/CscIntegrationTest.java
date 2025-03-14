package integration.trig;

import com.ArsenyVekshin.trig.Csc;
import com.ArsenyVekshin.trig.Sin;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static com.ArsenyVekshin.Func.f;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.atLeastOnce;

public class CscIntegrationTest {
    private static final double DELTA = 1e-5;
    private static final MockedStatic<Sin> mocked = Mockito.mockStatic(Sin.class);

    @AfterAll
    static void closeMock() {
        mocked.close();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "csc.csv", numLinesToSkip = 1)
    public void testMockedCsc(double x, double ctrlValue, double selfExpected, double funcExpected) {
        mocked.when(() -> Sin.sin(anyDouble())).thenAnswer(invocation -> {
            double arg = invocation.getArgument(0);
            return Math.sin(arg);
        });

        assertEquals(selfExpected, Csc.csc(x), DELTA);
        assertEquals(selfExpected, f(x), DELTA);
        mocked.verify(() -> Sin.sin(anyDouble()), atLeastOnce());
    }
}
