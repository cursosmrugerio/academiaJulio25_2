package com.curso.v3;

public class Motor {
	String tipo = "electrico";

	public Motor(String tipo) {
		this.tipo = tipo;
	} 
	
	void encender() {
		System.out.println("Encender motor: "+tipo);
		
	}
}
