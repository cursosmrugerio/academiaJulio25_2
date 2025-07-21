package com.curso.v0;

import java.util.ArrayList;
import java.util.List;

class Ave{}
class Pato extends Ave {}

public class Principal {
	public static void main(String[] args) {
		
		List<Object> listObject = new ArrayList<>();
		listObject.add(new Object());
		listObject.add("Hola");
		listObject.add(new StringBuilder("Hello"));
		listObject.add(new Ave());
		listObject.add(new Pato());
		
		show1(listObject);
		System.out.println("********");
		show2(listObject);
//		System.out.println("********");
//		listObject.forEach(System.out::println);

	}
	
	static void show1(List<Object> listaObjetos) {
		for (int z=0; z<listaObjetos.size(); z++)
			System.out.println(listaObjetos.get(z));
	}
	
	static void show2(List<Object> listaObjetos) {
		for (Object x : listaObjetos)
			System.out.println(x);
	}

}
