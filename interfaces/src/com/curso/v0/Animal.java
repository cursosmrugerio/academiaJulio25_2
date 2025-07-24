package com.curso.v0;

public interface Animal {
	void comer();
	double getPeso();
	//JAVA 8
	static String getNombre() {
		return null;
	}
	default String getCollar() {
		return null;
	}
	//JAVA 9
	private static String getApodo() {
		return null;
	}
	private String getCadena() {
		return null;
	}
}

class Perro implements Animal{
	@Override
	public void comer() {
	}
	@Override
	public double getPeso() {
		return 0;
	}
}
