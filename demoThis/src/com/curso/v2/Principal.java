package com.curso.v2;

public class Principal {
	
	public static void main(String[] args) {
		
		Empleado emp1 = new Empleado("Epeneto",80.00);
				
		Empleado emp2 = emp1.aumentarSalario(50.0);
		
		System.out.println(emp1.getSalario()); //80.00
		System.out.println(emp2.getSalario()); //120.00
		
		System.out.println(emp1.getNombre()); //Epeneto
		System.out.println(emp2.getNombre()); //Patrobas
		
		System.out.println(emp1 == emp2); //falase

	}

}
