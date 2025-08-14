package com.curso.v0;

public class Principal1 {

	public static void main(String[] args) {
		int a = 2; //3 //4
		int b = 3; //2 //1
		//  resultado    3  + 3   +  3  +  1        
		int resultado = ++a + b-- + a++ + --b;;
		System.out.println("a = " + a); //4
		System. out. println("b = " + b); //1
		System.out.println("resultado = " + resultado); //10
		
	}

}
