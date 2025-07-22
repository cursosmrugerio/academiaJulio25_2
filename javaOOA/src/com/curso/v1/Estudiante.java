package com.curso.v1;

public class Estudiante {
	
	String nombre;
	int edad;
	
	public Estudiante(String nombre, int edad) {
		this.nombre = nombre;
		this.edad = edad;
	}
	
	void getName() {
		System.out.println(this.nombre);
	}
	
	void getEdad() {
		System.out.println(this.edad);
	}
	
	

}
