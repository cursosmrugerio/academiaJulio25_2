package com.curso.v2;

public class Empleado {

	private String nombre;
	private double salario;
	
	public Empleado(String nombre, double salario) {
		this.nombre = nombre;
		this.salario = salario;
	}

	public Empleado aumentarSalario(double porcentaje) {
		Empleado emp = new Empleado("Patrobas",this.salario);
		emp.salario += emp.salario * (porcentaje / 100);
		return emp; 
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
