package com.curso.v0;

public class Principal {

	public static void main(String[] args) {

		int x = 0;
		
		int y = 8;
		int z = 0;
		
		try {
			x = dividir(y,z);
		}
		catch(ArithmeticException e) {
			//System.out.println("Error");
			e.printStackTrace();
		}
		
		System.out.println("Resultado: "+x);
		
		System.out.println("Fin Programa");
		
	}

	private static int dividir(int y, int z) {
		int r = y/z;
		return r;
	}



}
