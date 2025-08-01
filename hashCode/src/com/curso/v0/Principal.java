package com.curso.v0;

public class Principal {

	public static void main(String[] args) {

		Empleado e1 = new Empleado("Nereo",20, 100.00);
		Empleado e2 = new Empleado("Nereo",20, 100.00);
		Empleado e3 = new Empleado("Nereo",20, 100.00);
		
		System.out.println("==:"+(e1==e2)); //false
		System.out.println("equals: "+e1.equals(e2)); //true
		
		System.out.println(e1.hashCode());
		System.out.println(e2.hashCode());
		System.out.println(e3.hashCode());



	}

}
