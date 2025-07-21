package com.curso.v0;

public class Principal3 {

	public static void main(String[] args) {

		String cadena1 = "Hello"; 
		String cadena2 = new String("Hello"); 
		
		System.out.println(cadena1 == cadena2.intern()); //true
		
		System.out.println(cadena1 == cadena2); //false
		
		
		
	}

}
