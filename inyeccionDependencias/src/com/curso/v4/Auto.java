package com.curso.v4;

public class Auto {
	
	//HAS-A
	//ALTO ACOMPLAMIENTO
	Motor motor = new Motor("Manual");
	
	void arrancar() {
		motor.encender();
	}

}
