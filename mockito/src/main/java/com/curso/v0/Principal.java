package com.curso.v0;

public class Principal {

	public static void main(String[] args) {
        CalculoFinanciero calc = new CalculoFinanciero();
        double resultado = calc.calcula(1_000_000L, 10, 5.0f, (byte)4);
        System.out.println("Resultado final: " + resultado);
        
        //954453.58
	}

}
