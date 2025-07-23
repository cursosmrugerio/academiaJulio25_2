package com.curso.v0;

public class Principal {
	
	public static void main(String[] args) {
		Cliente c1 = new Cliente("Patrobas");
		Cliente c2 = new Cliente("Epeneto");
		Cliente c3 = new Cliente("Andronico");
		Cliente c4 = new Cliente("Tercio");

		System.out.println(c1.getNombre()); //Patrobas
		System.out.println(c2.getNombre()); //Epeneto
		System.out.println(c3.getNombre()); //Andronico
		System.out.println(c4.getNombre()); //Tercio
		
		System.out.println(c1.getContador()); //1
		System.out.println(c2.getContador()); //1
		System.out.println(c3.getContador()); //1
		System.out.println(c4.getContador()); //1

		

	}

}
