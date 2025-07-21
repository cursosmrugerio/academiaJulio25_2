package com.curso.v0;

class Ave{
	void volar() {
		System.out.println("Volar Ave");
	}
}

class Pato extends Ave {
	@Override
	void volar() {
		System.out.println("Volar Pato");
	}
}

public class Principal {
	
	public static void main(String[] args) {
		Pato pato = new Pato();
		pato.volar(); 
		
		Ave ave = new Pato(); 
		ave.volar();  //Volar Pato
	}

}
