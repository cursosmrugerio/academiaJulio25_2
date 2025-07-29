package com.curso.v2;

import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class CalculoFinancieroTest {

	@Mock
	CalculoFinancieroCloud cfc;

	CalculoFinanciero calc;

	@BeforeEach
	public void setUp() {
		cfc = Mockito.mock(CalculoFinancieroCloud.class);
		calc = new CalculoFinanciero(cfc);
	}

	@Test
	void testCalculoFinancieroMock() {
		Mockito.when(cfc.calculoExterno(1000000L, 10, 5.0f, (byte) 4)).thenReturn(954453.58);

		double resultado = calc.calcula(1000000L, 10, 5.0f, (byte) 4);

		assertEquals(954453.58, resultado, 0.01, "El cálculo financiero básico no coincide con el valor esperado");

		// Verificamos que se llamó al mock con los argumentos correctos
		Mockito.verify(cfc).calculoExterno(1000000L, 10, 5.0f, (byte) 4);

	}
	
	@Test
	void testCalculoFinancieroMockSimple() {
		Mockito.when(cfc.calculoExterno(10L, 1, 1.0f, (byte) 1)).thenReturn(100.00);

		double resultado = calc.calcula(10L, 1, 1.0f, (byte) 1);

		assertEquals(100.00, resultado, 0.01, "El cálculo financiero básico no coincide con el valor esperado");

		// Verificamos que se llamó al mock con los argumentos correctos
		Mockito.verify(cfc).calculoExterno(10L, 1, 1.0f, (byte) 1);

	}

}
