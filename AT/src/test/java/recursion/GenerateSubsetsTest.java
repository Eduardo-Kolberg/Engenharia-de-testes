package recursion;

import java.util.HashSet;
import java.util.List;

import net.jqwik.api.*;

import org.example.recursion.GenerateSubsets;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GenerateSubsetsTest {

    @Test
    void subsetRecursionTestOne() {
        String str = "abc";
        String[] expected = new String[] {"abc", "ab", "ac", "a", "bc", "b", "c", ""};

        List<String> ans = GenerateSubsets.subsetRecursion(str);
        assertArrayEquals(ans.toArray(), expected);
    }

    @Test
    void subsetRecursionTestTwo() {
        String str = "cbf";
        String[] expected = new String[] {"cbf", "cb", "cf", "c", "bf", "b", "f", ""};

        List<String> ans = GenerateSubsets.subsetRecursion(str);
        assertArrayEquals(ans.toArray(), expected);
    }

    @Test
    void subsetRecursionTestThree() {
        String str = "aba";
        String[] expected = new String[] {"aba", "ab", "aa", "a", "ba", "b", "a", ""};

        List<String> ans = GenerateSubsets.subsetRecursion(str);
        assertArrayEquals(ans.toArray(), expected);
    }

    @Test
    void testInstanciarClasseDeveRetornarErro() {
        assertThrows(IllegalAccessException.class, () -> {
            GenerateSubsets.class.getDeclaredConstructor().newInstance();
        });
    }
    
    @Property
    void testPosiveisCombinacoes(@ForAll("strings") String str) {
        List<String> resultado = GenerateSubsets.subsetRecursion(str);
        int numeroEsperadoDeCombinacoes = (int) Math.pow(2, str.length());
        assertEquals(numeroEsperadoDeCombinacoes, resultado.size(), "Número de combinações deve ser dois elevado ao tamanho da string");

        HashSet<String> resultadoSet = new HashSet<>(resultado);

        for (String subset : resultadoSet) {
            assertTrue(isValidSubset(str, subset), "A combinação deve ser válida.");
        }
    }

    @Test
    void testEmptyString() {
        List<String> resultado = GenerateSubsets.subsetRecursion("");
        assertEquals(1, resultado.size(), "Já que é uma string vazia só deve ter uma combinação.");
        assertTrue(resultado.contains(""), "Já que é uma string vazia deve ter apenas uma string vaiz");
    }

    @Test
    void testSingleCharacterString() {
        String str = "a";
        List<String> resultado = GenerateSubsets.subsetRecursion(str);
        assertEquals(2, resultado.size(), "Combinações com apenas uma letra devem ser 2.");
        assertTrue(resultado.contains(""), "Deve contar vazio");
        assertTrue(resultado.contains("a"), "A letra deve ser uma das combinações");
    }

    @Property
    void testStringWithSpecialCharsAndNumbers(@ForAll("specialStrings") String str) {
        List<String> resultado = GenerateSubsets.subsetRecursion(str);
        int numeroEsperadoDeCombinacoes = (int) Math.pow(2, str.length());
        assertEquals(numeroEsperadoDeCombinacoes, resultado.size(), "Número de combinações deve ser dois elevado ao tamanho da string");

        HashSet<String> resultadoSet = new HashSet<>(resultado);

        for (String subset : resultadoSet) {
            assertTrue(isValidSubset(str, subset), "A combinação deve ser válida.");
        }
    }

    private boolean isValidSubset(String str, String subset) {
        int i = 0;
        for (char c : subset.toCharArray()) {
            i = str.indexOf(c, i);
            if (i == -1) {
                return false;
            }
            i++;
        }
        return true;
    }

    @Provide
    public static Arbitrary<String> strings() {
        return Arbitraries.strings().withCharRange(' ', '~')
                .ofLength(4);
    }

    @Provide
    public static Arbitrary<String> specialStrings() {
        return Arbitraries.strings().withCharRange(' ', '~')
                .ofLength(3);
    }
}
