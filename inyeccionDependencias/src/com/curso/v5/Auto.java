package com.curso.v5;

public class Auto {
	
	//HAS-A
	//ALTO ACOMPLAMIENTO
	MotorManual motor = new MotorManual();
	
	void arrancar() {
		motor.encender();
	}

}
