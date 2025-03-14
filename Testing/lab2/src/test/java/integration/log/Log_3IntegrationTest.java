package integration.log;

import com.ArsenyVekshin.log.Ln;
import com.ArsenyVekshin.log.Log_3;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static com.ArsenyVekshin.Func.f;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.atLeastOnce;

public class Log_3IntegrationTest {
    private static final double DELTA = 1e-5;
    private static final MockedStatic<Ln> mocked = Mockito.mockStatic(Ln.class);

    @AfterAll
    static void closeMock() {
        mocked.close();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "log_3.csv", numLinesToSkip = 1)
    public void testMockedLog_3(double x, double ctrlValue1, double ctrlValue2, double selfExpected, double funcExpected) {
        mocked.when(() -> Ln.ln(anyDouble())).thenAnswer(invocation -> {
            double arg = invocation.getArgument(0);
            return Math.log(arg);
        });

        assertEquals(selfExpected, Log_3.log_3(x), DELTA);
        assertEquals(funcExpected, f(x), DELTA);
        mocked.verify(() -> Log_3.log_3(anyDouble()), atLeastOnce());
    }
}
