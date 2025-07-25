package com.curso.v7;

public class Inyector {
	
	static Motor motor1 = new MotorManual();
	static Motor motor2 = new MotorAutomatico();
	static Motor motor3 = new MotorHibrido();
	static Motor motor4 = new MotorMock();
	
	static void inyectaMotor(Auto auto, String tipo) {
		
		switch(tipo) {
		
		case "m":
			auto.motor = motor1; //INYECTAR
			break;
		case "a":
			auto.motor = motor2; //INYECTAR
			break;
		case "h":
			auto.motor = motor3; //INYECTAR
			break;
		default :
			auto.motor = motor4; //INYECTAR
		}
		
		
	}


}
