import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.IntRange;
import org.example.mathFunctions.MathFunctions;
import org.example.mathFunctions.MathLogger;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class MathFunctionsTest {
    private final MathLogger mockLogger = mock(MathLogger.class);
    private final MathFunctions mathFunctions = new MathFunctions(mockLogger);

    @Property
    void testResultadoDeveSerSemprePar(@ForAll @IntRange(min = -2000, max = 2000) int number) {
        int result = MathFunctions.MultiplyByTwo(number);
        assertEquals(0, result % 2, "Resultado deve ser par.");
    }

    @Property
    void testTodosOsNumerosDevemSerMultiplosOriginal(@ForAll @IntRange(min = 1, max = 250) int number,
                                         @ForAll @IntRange(min = 1, max = 20) int limit) {
        int[] table = mathFunctions.GenerateMultiplicationTable(number, limit);
        for (int value : table) {
            assertEquals(0, value % number, "O resultado deve ser múltiplo do original.");
        }
    }

    @Property
    void testSeDivisorApenasPorEleMesmoEUm(@ForAll @IntRange(min = 2, max = 2000) int number) {
        if (MathFunctions.IsPrime(number)) {
            for (int i = 2; i <= Math.sqrt(number); i++) {
                if (number % i == 0) {
                    fail("Primo só pode ser dividido por ele e por um.");
                }
            }
        }
    }

    @Property
    void testVerificaSeOvalorEstaSempreEntreOMaiorEMenorValor(@ForAll @IntRange(min = 1, max = 5000) int[] numbers) {
        if (numbers.length > 0) {
            double average = mathFunctions.CalculateAverage(numbers);
            int min = Arrays.stream(numbers).min().orElseThrow();
            int max = Arrays.stream(numbers).max().orElseThrow();
            assertTrue(average >= min && average <= max, "A média deve estar entre o menor e o maior valor do array.");
        }
    }

    @Property
    void testArrayNulo(@ForAll int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            assertThrows(IllegalArgumentException.class, () -> {
                mathFunctions.CalculateAverage(numbers);
            }, "Deve lançar IllegalArgumentException para array nulo ou vazio.");
        }
    }
}
