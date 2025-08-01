package com.curso.v2;

import java.util.HashSet;
import java.util.Set;

public class Principal {

	public static void main(String[] args) {

		Empleado e1 = new Empleado("Nereo",50,500.00); //<==
		Empleado e2 = new Empleado("Nereo",50,500.00);
		Empleado e3 = new Empleado("Urbano",20,100.00);
		Empleado e4 = new Empleado("Filologo",20,100.00);
		Empleado e5 = new Empleado("Nereo",1,0.00);

		System.out.println("==:"+(e1==e5)); //false
		System.out.println("equals: "+e1.equals(e5)); //false
		
		System.out.println(e1.hashCode());
		System.out.println(e5.hashCode());

		System.out.println("*************");
		
		Set<Empleado> setEmpleados = new HashSet<>();
		
		setEmpleados.add(e1);
		setEmpleados.add(e2);
		setEmpleados.add(e3);
		setEmpleados.add(e4);
		setEmpleados.add(e5);

		for (Empleado e:setEmpleados)
			System.out.println(e);
		
//		setEmpleados.forEach(System.out::println);
		

	}

}
