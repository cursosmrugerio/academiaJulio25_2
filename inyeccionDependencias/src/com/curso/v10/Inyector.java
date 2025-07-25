package com.curso.v10;

public class Inyector {
	
	static Motor motor1 = new MotorManual();
	static Motor motor2 = new MotorAutomatico();
	static Motor motor3 = new MotorHibrido();
	static Motor motor4 = new MotorMock();
	
	static Auto inyectaMotor(String tipo) {
		switch(tipo) {
		case "m":
			return new Auto(motor1); //INYECTAR
		case "a":
			return new Auto(motor2); //INYECTAR
		case "h":
			return new Auto(motor3); //INYECTAR
		default :
			return new Auto(motor4); //INYECTAR
		}
	}
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
