package com.curso.v1;

class Ave{
	void volar() {
		System.out.println("Volar Ave");
	}
	void volarAve() {
		System.out.println("volarAve()");
	}
}

class Pato extends Ave {
	@Override
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
		ave.volar();
		ave.volarAve();
		//ave.volarPato(); //Error Compilation
		((Pato)ave).volarPato();
		
		Pato pato = (Pato)ave;
		pato.volarPato();
	}

}
