package com.curso.v5;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Principal {

	public static void main(String[] args) {
		
		System.out.println("V4");

		List<Empleado> listaEmpleado =  new ArrayList<>( List.of(
				new Empleado("Herodion", 30, 40.0), 
				new Empleado("Aristobulo", 20, 50.0),
				new Empleado("Junias", 18, 35.0), 
				new Empleado("Trifosa", 25, 18.0),
				new Empleado("Sosipater", 35, 45.0)));
		
		System.out.println("***ORDENAR POR EDAD");
		
		listaEmpleado.sort((x,y) -> x.getEdad() - y.getEdad());

		for (Empleado e: listaEmpleado)
			System.out.println(e);
		
		System.out.println("***ORDENAR POR SUELDO");
		
		listaEmpleado.sort((emp1,emp2) -> (int)(emp1.getSueldo() - emp2.getSueldo()));
					
		for (Empleado e: listaEmpleado)
			System.out.println(e);
		
		System.out.println("***ORDENAR POR NOMBRE");
		
		listaEmpleado.sort((pato1,pato2) -> pato1.getNombre().compareTo(pato2.getNombre()));
					
		for (Empleado e: listaEmpleado)
			System.out.println(e);
		
	}

}
