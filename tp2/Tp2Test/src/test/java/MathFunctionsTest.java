import net.jqwik.api.*;
import org.example.MathFunctions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MathFunctionsTest {



    @Property
    void dobroDeveSerMaiorQueONumeroOriginal(@ForAll int numero) {
        int resultado = MathFunctions.multiplyByTwo(numero);
        assertTrue(resultado >= numero, "O " + resultado + " não é maior ou igual ao " + numero);
    }


    @Property
    void dobroDeNumeroParDeveSerPar(@ForAll("numerosPares") int numeroPar) {
        int resultado = MathFunctions.multiplyByTwo(numeroPar);
        assertEquals(0, resultado % 2, "Não é par.");
    }


    @Provide
    public static Arbitrary<Integer> numerosPares() {
        return Arbitraries.integers().filter(x -> x % 2 == 0);
    }
}
