package com.curso.v2;

public class Cliente {
	
	private String nombre;
	static private int contador; //0
	
	public Cliente(String nombre) {
		this.nombre = nombre;
		++contador;
	}

	static public int getContador() {
		return contador;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
}
