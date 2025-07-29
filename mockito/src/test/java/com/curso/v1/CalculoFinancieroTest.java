package com.curso.v1;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CalculoFinancieroTest {

	CalculoFinanciero calc = new CalculoFinanciero();
	
	CalculoFinancieroCloud cfcAnonima = new CalculoFinancieroCloud() {
		@Override
		public double calculoExterno(long principal, int years, float annualRate, byte compoundingsPerYear) {
			System.out.println("Ejecuta Clase Anonima");
			return 954453.58;
		}		
	};
	
	@Test
	void testCalculoFinancieroBasico() {
		//calc.cfc = (principal, years, annualRate, compoundingsPerYear) -> 954453.58;
		
		calc.cfc = cfcAnonima;
			
		double resultado = calc.calcula(9000000L, 10, 5.0f, (byte) 4);
		double valorEsperado = 954453.58;
		assertEquals(valorEsperado, resultado, 0.01, "El cálculo financiero básico no coincide con el valor esperado");
	}

}
