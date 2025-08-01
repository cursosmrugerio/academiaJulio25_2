package com.curso.v0;

public class PrincipalCharNull {

	public static void main(String[] args) {

		String cadena = null;
		
		char ch = 'a';
		
		cadena = ch + cadena;
		
		System.out.println(cadena);
		
		System.out.println("*******");
		
		String cadena2 = null;
		
		Character ch2 = Character.valueOf('b');
		
		cadena2 = cadena2 + ch2;
		
		System.out.println("cadena2: "+cadena2);
		
		System.out.println("*******");

		String cadena3 = "a";
		
		String cadena4 = null;
		
		cadena3 = cadena3 + cadena4;
		//cadena3 = cadena3.concat(cadena4);
		
		System.out.println("cadena3: "+cadena3);
		
		System.out.println("*******");

		StringBuilder sb1 = null;
		
		StringBuilder sb2 = new StringBuilder("a");
		
		sb2 = sb2.append(sb1);
		
		System.out.println("StringBuilder: "+sb2);
		
		
		
	}

}
