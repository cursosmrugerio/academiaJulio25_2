package com.curso.v0;

public class Principal0 {

	public static void main(String[] args) {
		int a = 2;
		int b = 3;
		//int resultado = ++a + b-- + a++ + --b;
		int resultado = ++a + b-- + a++;
		System.out.println("a = " + a); //4
		System. out. println("b = " + b); //2
		System.out.println("resultado = " + resultado); //9
		
		// a = a++;

	}

}
