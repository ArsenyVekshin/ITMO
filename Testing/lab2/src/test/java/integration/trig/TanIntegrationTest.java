package integration.trig;

import com.ArsenyVekshin.trig.Cos;
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

public class TanIntegrationTest {
    private static final double DELTA = 1e-5;
    private static final MockedStatic<Sin> mockedSin = Mockito.mockStatic(Sin.class);
    private static final MockedStatic<Cos> mockedCos = Mockito.mockStatic(Cos.class);

    @AfterAll
    static void closeMock() {
        mockedSin.close();
        mockedCos.close();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "tan.csv", numLinesToSkip = 1)
    public void testMockedTan(double x, double ctrlValue1, double ctrlValue2, double selfExpected, double funcExpected) {
        mockedSin.when(() -> Sin.sin(anyDouble())).thenAnswer(invocation -> {
            double arg = invocation.getArgument(0);
            return Math.sin(arg);
        });
        mockedCos.when(() -> Cos.cos(anyDouble())).thenAnswer(invocation -> {
            double arg = invocation.getArgument(0);
            return Math.cos(arg);
        });


        assertEquals(selfExpected, Tan.tan(x), DELTA);
        assertEquals(funcExpected, f(x), DELTA);
        mockedSin.verify(() -> Sin.sin(anyDouble()), atLeastOnce());
        mockedCos.verify(() -> Cos.cos(anyDouble()), atLeastOnce());
    }

}
