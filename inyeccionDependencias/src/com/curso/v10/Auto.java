package com.curso.v10;

public class Auto {
		
	//HAS-A //Bajo Acomplamiento
	private Motor motor; 
	
	Auto(Motor motor){ //INYECCION POR CONSTRUCTOR
		this.motor = motor;
	}
	
	void arrancar() {
		motor.encender();
	}
	
	public Motor getMotor() {
		return motor;
	}

	public void setMotor(Motor motor) { //INYECCION POR SETTER
		this.motor = motor;
	}
	
	
}
