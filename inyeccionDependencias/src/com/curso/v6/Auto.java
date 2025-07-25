package com.curso.v6;

public class Auto {
		
	//HAS-A
	//ALTO ACOMPLAMIENTO
	MotorManual motorManu;
	MotorAutomatico motorAut;
	
	Auto(String tipo){
		if (tipo == "A")
			motorAut  = new MotorAutomatico();
		else 
			motorManu = new MotorManual();
	}
	
	void arrancar() {
		if (motorAut==null)
			motorManu.encender();
		else
			motorAut.encender();
	}

}
