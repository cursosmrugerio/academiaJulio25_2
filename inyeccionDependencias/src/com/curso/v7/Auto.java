package com.curso.v7;

public class Auto {
		
	//HAS-A //Bajo Acomplamiento
	Motor motor; //INYECCION POR ATRIBUTO O CAMPO
	
	void arrancar() {
		motor.encender();
	}

}
