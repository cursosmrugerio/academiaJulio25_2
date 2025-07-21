package com.curso.v2;

class Ave{
	
	String tipo = "Ave";
	
	void volar() {
		System.out.println("Volar Ave");
	}
	void volarAve() {
		System.out.println("volarAve()");
	}
}

class Pato extends Ave {
	
	String tipo = "Pato";
	
	void volar() {
		System.out.println("Volar Pato");
	}
	void volarPato() {
		System.out.println("volarPato()");
	}
}

public class Principal {
	
	public static void main(String[] args) {
		Ave ave = new Pato();
		System.out.println(ave.tipo); //Ave
		
		System.out.println(((Pato)ave).tipo); //Pato
				
	}

}
