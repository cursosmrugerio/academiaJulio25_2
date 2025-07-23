package com.curso.v1;

public class Cliente {
	
	public String nombre;
	static public int contador; //0
	
	public Cliente(String nombre) {
		this.nombre = nombre;
		++contador;
	}

	public int getContador() {
		return contador;
	}

	public void setContador(int contador) {
		this.contador = contador;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
}
