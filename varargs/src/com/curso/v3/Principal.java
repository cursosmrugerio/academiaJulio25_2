package com.curso.v3;

class Ave extends Object{}
class Pato extends Ave {}
class Aguila extends Ave {}

public class Principal {
	public static void main(String[] args) {
		Ave ave0 = new Ave();
		Ave ave1 = new Pato();
		Ave ave2 = new Aguila();
		
		show(new Ave[]{});
		show(new Ave[]{ave0});
		show(new Ave[]{ave0,ave1,ave2});
		
	}
	
	static void show(Ave[] listaAves) {
		System.out.println("********");
		for (Ave a : listaAves)
			System.out.println(a.getClass().getSimpleName());
	}
	

}
