package com.curso.v1;

import java.util.ArrayList;
import java.util.List;

class Ave extends Object{}
class Pato extends Ave {}

public class Principal {
	public static void main(String[] args) {
		
		List<Object> listObject = new ArrayList<>();
		listObject.add(new Object());
		listObject.add("Hola");
		listObject.add(new StringBuilder("Hello"));
		listObject.add(new Ave());
		listObject.add(new Pato());
		
		show(listObject);
		System.out.println("********");
		
		List<Ave> listAves = new ArrayList<>();
		listAves.add(new Ave());
		listAves.add(new Pato());

		show(listAves);
		
	}
	
	static void show(List<?> listaObjetos) {
		for (int z=0; z<listaObjetos.size(); z++)
			System.out.println(listaObjetos.get(z));
	}
	

}
