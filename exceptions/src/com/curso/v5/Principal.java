package com.curso.v5;

public class Principal {

	public static void main(String[] args) {

		int x = 0;
		
		int y = 1002;
		int z = 4;
		
		try {
			x = dividir(y,z);
		} catch (DividirCero e) {
			e.printStackTrace();
		} catch (Negativo e) {
			e.printStackTrace();
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Resultado: "+x);
		
		System.out.println("Fin Programa");
		
	}

	private static int dividir(int y, int z) throws Exception{
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
