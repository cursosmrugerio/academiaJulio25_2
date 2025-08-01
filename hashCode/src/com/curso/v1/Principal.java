package com.curso.v1;

public class Principal {

	public static void main(String[] args) {

		Empleado e1 = new Empleado("Nereo",50,500.00);
		Empleado e2 = new Empleado("Nereo",20,100.00);
		
		System.out.println("==:"+(e1==e2)); //false
		System.out.println("equals: "+e1.equals(e2)); //false
		
		System.out.println(e1.hashCode());
		System.out.println(e2.hashCode());



	}

}
