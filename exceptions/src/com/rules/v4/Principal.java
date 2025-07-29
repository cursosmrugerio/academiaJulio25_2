package com.rules.v4;


class Ave{
	Ave(){
		System.out.println("Constructor Ave");
	}
}

class Pato extends Ave{
	Pato() throws Exception{
		System.out.println("Constructor Pato");
	}
}

public class Principal {
	
	public static void main(String[] args) {
		Ave ave = new Ave();
		
		try {
			Ave ave2 = new Pato();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
