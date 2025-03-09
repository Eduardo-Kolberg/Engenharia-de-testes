import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.example.Calculadora;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculadoraTest {

    private final Calculadora calculadora = new Calculadora();


    @Property
    void calculoDeveDarIgualASomaDosElementos(@ForAll int a, @ForAll int b) {
        assertEquals(calculadora.somar(a, b), calculadora.somar(b, a));
    }


}
