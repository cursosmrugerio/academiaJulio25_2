package com.curso.v0;

public class Principal {

	public static void main(String[] args) {
		int a = 2;
		int b = 3;
		//int resultado = ++a + b-- + a++ + --b;
		int resultado = ++a + b--;
		System.out.println("a = " + a); //3
		System. out. println("b = " + b); //2
		System.out.println("resultado = " + resultado); //6

	}

}
