package integration.log;

import com.ArsenyVekshin.log.Ln;
import com.ArsenyVekshin.log.Log_2;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static com.ArsenyVekshin.Func.f;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.atLeastOnce;

public class Log_2IntegrationTest {
    private static final double DELTA = 1e-5;
    private static final MockedStatic<Ln> mocked = Mockito.mockStatic(Ln.class);

    @AfterAll
    static void closeMock() {
        mocked.close();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "log_2.csv", numLinesToSkip = 1)
    public void testMockedLog_2(double x, double ctrlValue1, double ctrlValue2, double selfExpected, double funcExpected) {
        mocked.when(() -> Ln.ln(anyDouble())).thenAnswer(invocation -> {
            double arg = invocation.getArgument(0);
            return Math.log(arg);
        });

        assertEquals(selfExpected, Log_2.log_2(x), DELTA);
        assertEquals(funcExpected, f(x), DELTA);
        mocked.verify(() -> Log_2.log_2(anyDouble()), atLeastOnce());
    }
}
