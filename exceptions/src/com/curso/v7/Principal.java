package com.curso.v7;

public class Principal {

	public static void main(String[] args) {

		int x = 0;
		
		int y = 1002;
		int z = 2;
		
		try {
			x = dividir(y,z);
		} catch (DividirCero | Negativo | UnsupportedOperationException e ) {
			e.printStackTrace();
		}
		
		System.out.println("Resultado: "+x);
		
		System.out.println("Fin Programa");
		
	}

	private static int dividir(int y, int z) throws DividirCero,Negativo,
										UnsupportedOperationException{
		if (z==0)
			throw new DividirCero("No se puede dividir entre 0");
		else if (z<0)
			throw new Negativo("Divisor no puede ser negativo");
		else if (y>1000)
			throw new UnsupportedOperationException("Dividendo no mayor a 1000"); 
		int r = y/z;
		return r;
	}



}
