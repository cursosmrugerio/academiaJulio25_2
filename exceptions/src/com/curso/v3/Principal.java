package com.curso.v3;

public class Principal {

	public static void main(String[] args) {

		int x = 0;
		
		int y = 8;
		int z = -2;
		
		try {
			x = dividir(y,z);
		} catch (DividirCero e) {
			e.printStackTrace();
		} catch (Negativo e) {
			e.printStackTrace();
		}
		
		System.out.println("Resultado: "+x);
		
		System.out.println("Fin Programa");
		
	}

	private static int dividir(int y, int z) throws DividirCero,Negativo  {
		if (z==0)
			throw new DividirCero("No se puede dividir entre 0");
		else if (z<0)
			throw new Negativo("Divisor no puede ser negativo");
		int r = y/z;
		return r;
	}



}
