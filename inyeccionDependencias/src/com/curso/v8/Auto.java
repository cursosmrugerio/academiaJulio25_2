package com.curso.v8;

public class Auto {
		
	//HAS-A //Bajo Acomplamiento
	private Motor motor; 
	
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
