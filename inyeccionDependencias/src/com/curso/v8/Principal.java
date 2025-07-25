package com.curso.v8;

public class Principal {
	
	public static void main(String[] args) {
		
		System.out.println("Inyección por Setter");
		
		Auto auto1 = new Auto();
		auto1.arrancar(); 
		System.out.println("Hibrido");
		Inyector.inyectaMotor(auto1, "h");
		auto1.arrancar(); 
		
		Auto auto2 = new Auto();
		System.out.println("Manual");
		Inyector.inyectaMotor(auto2, "m");
		auto2.arrancar(); 
		
		Auto auto3 = new Auto();
		System.out.println("Automatico");
		Inyector.inyectaMotor(auto3, "a");
		auto3.arrancar();
		
		Auto auto4 = new Auto();
		System.out.println("Mock");
		Inyector.inyectaMotor(auto4, "zxy");
		auto4.arrancar();

	}

}
