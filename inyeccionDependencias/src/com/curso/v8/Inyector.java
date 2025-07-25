package com.curso.v8;

public class Inyector {
	
	static Motor motor1 = new MotorManual();
	static Motor motor2 = new MotorAutomatico();
	static Motor motor3 = new MotorHibrido();
	static Motor motor4 = new MotorMock();
	
	static void inyectaMotor(Auto auto, String tipo) {
		
		switch(tipo) {
		
		case "m":
			auto.setMotor(motor1); //INYECTAR
			break;
		case "a":
			auto.setMotor(motor2); //INYECTAR
			break;
		case "h":
			auto.setMotor(motor3); //INYECTAR
			break;
		default :
			auto.setMotor(motor4); //INYECTAR
		}
		
		
	}


}
