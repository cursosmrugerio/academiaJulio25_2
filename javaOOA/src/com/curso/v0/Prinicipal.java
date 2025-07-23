package com.curso.v0;

public class Prinicipal {
	
	//VARIABLE DE CLASE
	static String cadena1; //null
	
	//VARIABLE DE INSTANCIA DE CLASE (OBJETO)
	String cadena2; //null
	
	public static void main(String[] args) {
		
		//VARIABLE LOCAL (NO SE INICIALIZAN)
		String cadena3 = "HolaMundoJavax";
		
		System.out.println(cadena1);
		
		System.out.println(new Prinicipal().cadena2);
		
		System.out.println(cadena3.charAt(cadena3.length()-1));
		
	}

}
