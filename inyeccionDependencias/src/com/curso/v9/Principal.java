package com.curso.v9;

public class Principal {
	
	public static void main(String[] args) {
		
		System.out.println("Inyección por Constructor");
		
		Auto auto1 = Inyector.inyectaMotor("h");
		System.out.println("Hibrido");
		auto1.arrancar(); 
		
		Auto auto2 = Inyector.inyectaMotor("m");
		System.out.println("Manual");
		auto2.arrancar(); 
		
		Auto auto3 = Inyector.inyectaMotor("a");
		System.out.println("Automatico");
		auto3.arrancar();
		
		Auto auto4 = Inyector.inyectaMotor("zxy");
		System.out.println("Mock");
		auto4.arrancar();

	}

}
