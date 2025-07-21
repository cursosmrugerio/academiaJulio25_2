package com.staticFinal.v2;

class Ave{
	final static void volar() {
		System.out.println("Ave volar");
	}
}

class Pato extends Ave{
	//Hidden (Ocultar)
//	static void volar() {
//		System.out.println("Pato volar");
//	}
}

public class Principal2 {
	
	public static void main(String[] args) {
		
		Ave.volar();
		Pato.volar();
		
		Ave ave = new Pato();
		ave.volar();
	}

}
