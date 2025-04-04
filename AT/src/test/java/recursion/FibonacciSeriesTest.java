package recursion;

import net.jqwik.api.*;
import org.example.recursion.FibonacciSeries;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FibonacciSeriesTest {
    @Test
    public void testFibonacci() {
        assertEquals(0, FibonacciSeries.fibonacci(0));
        assertEquals(1, FibonacciSeries.fibonacci(1));
        assertEquals(1, FibonacciSeries.fibonacci(2));
        assertEquals(2, FibonacciSeries.fibonacci(3));
        assertEquals(3, FibonacciSeries.fibonacci(4));
        assertEquals(5, FibonacciSeries.fibonacci(5));
        assertEquals(8, FibonacciSeries.fibonacci(6));
        assertEquals(13, FibonacciSeries.fibonacci(7));
        assertEquals(21, FibonacciSeries.fibonacci(8));
        assertEquals(34, FibonacciSeries.fibonacci(9));
        assertEquals(55, FibonacciSeries.fibonacci(10));
        assertEquals(89, FibonacciSeries.fibonacci(11));
        assertEquals(144, FibonacciSeries.fibonacci(12));
        assertEquals(233, FibonacciSeries.fibonacci(13));
        assertEquals(377, FibonacciSeries.fibonacci(14));
    }


    @Property
    void testNNumerosFibonacci(@ForAll("gerarNNumeroFibonacci") int n) {
        int[] fibonacciSequence = {0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377};

        int result = FibonacciSeries.fibonacci(n);
        assertEquals(fibonacciSequence[n], result, "o enesimo número de Fibonacci deve ser valido");
    }


    @Test
    void testInstanciarClasseDeveRetornarErro() {
        assertThrows(IllegalAccessException.class, () -> {
            FibonacciSeries.class.getDeclaredConstructor().newInstance();
        });
    }

    //Deveria dar um throw para números negativos...
    @Test
    void testDeveRetornarErroQuandoForPAassadoNumerosNegativos() {
        assertThrows(UnsupportedOperationException.class, () -> {
            FibonacciSeries.fibonacci(-1);
        });
    }

    @Provide
    public static Arbitrary<Integer> gerarNNumeroFibonacci() {
        return Arbitraries.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);
    }

}
