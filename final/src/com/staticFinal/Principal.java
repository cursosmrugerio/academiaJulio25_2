package com.staticFinal;

public class Principal {
	
	public static void main(String[] args) {
		final int x = 10; //CONSTANTE CON PRIMITIVOS
		//x = 5;
		System.out.println(x);
		
		//MUTABLE
		final StringBuilder sb = new StringBuilder("Hello");
		//sb = sb.append(" World");
		//sb = null;
		System.out.println(sb);
		
		//sb = new StringBuilder("Hola");
		
	}

}
