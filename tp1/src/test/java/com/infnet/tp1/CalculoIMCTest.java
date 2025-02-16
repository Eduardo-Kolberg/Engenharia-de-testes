package com.infnet.tp1;

import net.jqwik.api.*;
import net.jqwik.api.constraints.DoubleRange;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;

@SpringBootTest
class CalculoIMCTest {

	private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

	//1.4
	@Test
	public void deveCalcularIMCValidoQuandoAlturaEPesoValidos() {
		Assertions.assertEquals(23.15, CalculoIMC.calcularPeso(75.0, 1.8), 0.06);
		Assertions.assertEquals(27.7, CalculoIMC.calcularPeso(85.0, 1.75), 0.06);
	}


	// O correto seria testar isso na entrada de dados.
	@Test
	public void deveLancarExcecaoQuandoPesoOuAlturaInvalido() {
		IllegalArgumentException exception = (IllegalArgumentException)Assertions.assertThrows(IllegalArgumentException.class, () -> {
			CalculoIMC.calcularPeso(-75.0, 1.8);
		});
		Assertions.assertEquals("Peso e altura devem ser positivos e maiores que zero.", exception.getMessage());
		exception = (IllegalArgumentException)Assertions.assertThrows(IllegalArgumentException.class, () -> {
			CalculoIMC.calcularPeso(75.0, -1.8);
		});
		Assertions.assertEquals("Peso e altura devem ser positivos e maiores que zero.", exception.getMessage());
		exception = (IllegalArgumentException)Assertions.assertThrows(IllegalArgumentException.class, () -> {
			CalculoIMC.calcularPeso(0.0, 1.8);
		});
		Assertions.assertEquals("Peso e altura devem ser positivos e maiores que zero.", exception.getMessage());
		exception = (IllegalArgumentException)Assertions.assertThrows(IllegalArgumentException.class, () -> {
			CalculoIMC.calcularPeso(75.0, 0.0);
		});
		Assertions.assertEquals("Peso e altura devem ser positivos e maiores que zero.", exception.getMessage());
		exception = (IllegalArgumentException)Assertions.assertThrows(IllegalArgumentException.class, () -> {
			CalculoIMC.calcularPeso(Double.parseDouble("a"), 0.0);
		});
		Assertions.assertEquals("Peso e altura devem ser positivos e maiores que zero.", exception.getMessage());
		exception = (IllegalArgumentException)Assertions.assertThrows(IllegalArgumentException.class, () -> {
			CalculoIMC.calcularPeso(Double.parseDouble(""), 0.0);
		});
		Assertions.assertEquals("Peso e altura devem ser positivos e maiores que zero.", exception.getMessage());
	}

	//1.5
	@Test
	public void deveLancarExcecaoQuandoPesoOuAlturaForaDosLimites() {
		IllegalArgumentException exception = (IllegalArgumentException)Assertions.assertThrows(IllegalArgumentException.class, () -> {
			CalculoIMC.calcularPeso(29.0, 1.8);
		});
		Assertions.assertEquals("Peso deve estar entre 30kg e 300kg.", exception.getMessage());
		exception = (IllegalArgumentException)Assertions.assertThrows(IllegalArgumentException.class, () -> {
			CalculoIMC.calcularPeso(301.0, 1.8);
		});
		Assertions.assertEquals("Peso deve estar entre 30kg e 300kg.", exception.getMessage());
		exception = (IllegalArgumentException)Assertions.assertThrows(IllegalArgumentException.class, () -> {
			CalculoIMC.calcularPeso(75.0, 0.9);
		});
		Assertions.assertEquals("Altura deve estar entre 1m e 2.75m.", exception.getMessage());
		exception = (IllegalArgumentException)Assertions.assertThrows(IllegalArgumentException.class, () -> {
			CalculoIMC.calcularPeso(75.0, 3.0);
		});
		Assertions.assertEquals("Altura deve estar entre 1m e 2.75m.", exception.getMessage());
	}

	// 2.2 e 2.6 Em vez de usar @Positive usei um range de valores válido de acordo com os requerimentos criados
	// na questão anterior.
	@Property
	void deveCalcularIMCCorretamente(@ForAll @DoubleRange(min = 30, max = 300) double peso,
									 @ForAll @DoubleRange(min = 1.0, max = 2.75) double altura) {
		double imcEsperado = peso / (altura * altura);
		double imcCalculado = CalculoIMC.calcularPeso(peso, altura);

		Assertions.assertEquals(imcEsperado, imcCalculado, 0.01);
	}

	@Property
	void deveLancarExcecaoParaValoresInvalidos(@ForAll @DoubleRange(min = -100, max = 29) double peso,
											   @ForAll @DoubleRange(min = 0.1, max = 0.99) double altura) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> CalculoIMC.calcularPeso(peso, altura));
	}

	//2.3
	@Property
	void imcDeveSerValidoParaValoresExtremos(@ForAll("valoresExtremos") double[] entrada) {
		double peso = entrada[0];
		double altura = entrada[1];

		if (peso < 30 || peso > 300) {
			Assertions.assertThrows(IllegalArgumentException.class, () -> CalculoIMC.calcularPeso(peso, altura),
					"Peso deve estar entre 30kg e 300kg.");
		}
		else if (altura < 1.0 || altura > 2.75) {
			Assertions.assertThrows(IllegalArgumentException.class, () -> CalculoIMC.calcularPeso(peso, altura),
					"Altura deve estar entre 1m e 2.75m.");
		}
		else {
			double imc = CalculoIMC.calcularPeso(peso, altura);
			Assertions.assertTrue(imc >= 0, "O IMC deve ser sempre maior ou igual a zero.");
		}
	}

	@Provide
	Arbitrary<double[]> valoresExtremos() {
		return Arbitraries.of(
				new double[]{10.0, 1.75},
				new double[]{400.0, 1.75},
				new double[]{75.0, 0.1},
				new double[]{75.0, 3.0},
				new double[]{0.0, 1.8},
				new double[]{75.0, 0.0}
		);
	}

	//2.4
	@Property
	void testIMCComValoresAleatorios(@ForAll double peso, @ForAll double altura) {
		Assertions.assertTrue(CalculoIMC.calcularPeso(peso, altura) > 0);
	}

	//1.6
	@Property
	void testClassificarIMC(@ForAll @DoubleRange(min = 10, max = 50) double imc) {
		String classificacao = CalculoIMC.classificarIMC(imc);

		if (imc < 16.0) {
			Assertions.assertEquals("Magreza grave", classificacao);
		} else if (imc < 17.0) {
			Assertions.assertEquals("Magreza moderada", classificacao);
		} else if (imc < 18.5) {
			Assertions.assertEquals("Magreza leve", classificacao);
		} else if (imc < 25.0) {
			Assertions.assertEquals("Saudável", classificacao);
		} else if (imc < 30.0) {
			Assertions.assertEquals("Sobrepeso", classificacao);
		} else if (imc < 35.0) {
			Assertions.assertEquals("Obesidade Grau I", classificacao);
		} else if (imc < 40.0) {
			Assertions.assertEquals("Obesidade Grau II", classificacao);
		} else {
			Assertions.assertEquals("Obesidade Grau III", classificacao);
		}
	}


}
