package com.curso.v2;

public class Principal {

	public static void main(String[] args) {

		int x = 0;
		
		int y = 8;
		int z = 0;
		
		try {
			x = dividir(y,z);
		} catch (DividirCero e) {
			e.printStackTrace();
		}
		
		System.out.println("Resultado: "+x);
		
		System.out.println("Fin Programa");
		
	}

	private static int dividir(int y, int z) throws DividirCero  {
		if (z==0)
			throw new DividirCero("No se puede dividir entre 0");
		int r = y/z;
		return r;
	}



}
