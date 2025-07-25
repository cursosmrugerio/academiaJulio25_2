package com.curso.v9;

public class Auto {
		
	//HAS-A //Bajo Acomplamiento
	private Motor motor; 
	
	Auto(Motor motor){ //INYECCION POR CONSTRUCTOR
		this.motor = motor;
	}
	
	void arrancar() {
		motor.encender();
	}
}
