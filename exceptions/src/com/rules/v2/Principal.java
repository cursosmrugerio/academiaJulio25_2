package com.rules.v2;


class Ave{
	void volar() throws Exception{
		System.out.println("Ave volar"); 
	}
}

class Pato extends Ave{
	@Override
	void volar(){
		System.out.println("Pato volar"); 
	}
}

public class Principal {
	
	public static void main(String[] args) {
		Ave pato = new Pato();
		try {
			pato.volar();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
