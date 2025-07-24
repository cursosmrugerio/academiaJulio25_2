package com.curso.v3;

class Ave{	
	void volar() {
		System.out.println("Volar Ave");
	}
}
//                             *
//private -> default(*) -> protected -> public

class Pato extends Ave {
	@Override
	public void volar() {
		System.out.println("Volar Pato");
	}
}

public class Principal {
	
	public static void main(String[] args) {
		Ave ave = new Pato();
				
	}

}
