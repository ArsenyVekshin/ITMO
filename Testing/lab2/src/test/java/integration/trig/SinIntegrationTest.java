package integration.trig;

import com.ArsenyVekshin.trig.Sin;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static com.ArsenyVekshin.Func.f;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.atLeastOnce;

public class SinIntegrationTest {
    private static final double DELTA = 1e-5;
    //private static MockedStatic<Sin> mocked = Mockito.mockStatic(Sin.class);

    @Disabled
    @ParameterizedTest
    @CsvFileSource(resources = "sin.csv", numLinesToSkip = 1)
    public void testMockedSin(double x, double ctrlValue, double selfExpected, double funcExpected) {
        try (MockedStatic<Sin> mocked = Mockito.mockStatic(Sin.class)) {
            mocked.when(() -> Sin.sin(anyDouble())).thenReturn(ctrlValue);

            assertEquals(funcExpected, f(x), DELTA);

            mocked.verify(() -> Sin.sin(anyDouble()), atLeastOnce());
        }
    }

}
