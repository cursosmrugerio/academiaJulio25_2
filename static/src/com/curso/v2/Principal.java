package com.curso.v2;

public class Principal {
	
	public static void main(String[] args) {
		
		System.out.println(Cliente.getContador()); //0
		
		Cliente c1 = new Cliente("Patrobas");
		Cliente c2 = new Cliente("Epeneto");
		
		System.out.println(Cliente.getContador()); //2

		Cliente c3 = new Cliente("Andronico");
		Cliente c4 = new Cliente("Tercio");
		
//		System.out.println(c1.contador); //1 //4
//		System.out.println(c2.contador); //2 //4
//		System.out.println(c3.contador); //3 //4
//		System.out.println(c4.contador); //4 //4
		
		System.out.println(Cliente.getContador()); //4


//		System.out.println(c1.getNombre()); //Patrobas
//		System.out.println(c2.getNombre()); //Epeneto
//		System.out.println(c3.getNombre()); //Andronico
//		System.out.println(c4.getNombre()); //Tercio
		

		

	}

}
