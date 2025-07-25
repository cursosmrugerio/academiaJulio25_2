package com.curso.v6;

public class Principal {
	
	public static void main(String[] args) {
		Auto auto1 = new Auto("M");
		System.out.println("Manual");
		auto1.arrancar(); 

		Auto auto2 = new Auto("A");
		System.out.println("Automatico");
		auto2.arrancar(); 
		
		Auto auto3 = new Auto("M");
		System.out.println("Manual");
		auto3.arrancar(); 

	}

}
