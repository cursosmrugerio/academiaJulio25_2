package com.curso.v0;

public class Empleado {

	private String nombre;
	private double salario;
	
	public Empleado(String nombre, double salario) {
		this.nombre = nombre;
		this.salario = salario;
	}

	// Método que retorna la instancia actual
	public Empleado aumentarSalario(double porcentaje) {
		this.salario += this.salario * (porcentaje / 100);
		return this; // Retorno de la instancia actual
	}

	@Override
	public String toString() {
		return "Empleado [nombre=" + nombre + ", salario=" + salario + "]";
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}
	
	

}
