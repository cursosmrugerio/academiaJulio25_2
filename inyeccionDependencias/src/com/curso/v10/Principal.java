package com.curso.v10;

public class Principal {
	
	public static void main(String[] args) {
		
		//Cambiar de motor hibrido a motor automatico, 
		//solo cambias lo que le inyectas, 
		//sin modificar la clase Auto.
				
		Auto auto = Inyector.inyectaMotor("h");
		System.out.println("Nace como Hibrido");
		auto.arrancar(); 
		
		Inyector.inyectaMotor(auto, "a");
		auto.arrancar(); //POLIMORFISMO
		
		Inyector.inyectaMotor(auto, "m");
		auto.arrancar(); //POLIMORFISMO

	}
}
