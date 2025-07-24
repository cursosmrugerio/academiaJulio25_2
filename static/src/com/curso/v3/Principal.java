package com.curso.v3;

public class Principal {
	
	public static void main(String[] args) {
		
		Cliente.contador = 999;
				
		Cliente c1 = new Cliente("Patrobas");
		Cliente c2 = new Cliente("Epeneto");
		Cliente c3 = new Cliente("Andronico");
		Cliente c4 = new Cliente("Tercio");
		
		c4.contador = 0;
		
		System.out.println(c1.contador);  //1003
		System.out.println(c2.contador);  //1003
		System.out.println(c3.contador);  //1003
		System.out.println(c4.contador);  //1003
		


		

	}

}
