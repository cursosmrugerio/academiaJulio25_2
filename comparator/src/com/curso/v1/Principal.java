package com.curso.v1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class OrderByEdad implements Comparator<Empleado>{

	@Override
	public int compare(Empleado o1, Empleado o2) {
		
		int edad1 = o1.getEdad();
		int edad2 = o2.getEdad();

		if ( edad1 > edad2)
			return 99;
		else if (edad2 > edad1)
			return -999;
		else
			return 0;
	}
	
}

class OrderBySueldo implements Comparator<Empleado>{

	@Override
	public int compare(Empleado o1, Empleado o2) {
		
		int sueldo1 = (int)o1.getSueldo();
		int sueldo2 = (int)o2.getSueldo();

		if ( sueldo1 > sueldo2)
			return 44;
		else if (sueldo2 > sueldo1)
			return -1;
		else
			return 0;
	}
	
}

public class Principal {

	public static void main(String[] args) {

		List<Empleado> listaEmpleado =  new ArrayList<>( List.of(
				new Empleado("Herodion", 30, 40.0), 
				new Empleado("Aristobulo", 20, 50.0),
				new Empleado("Junias", 18, 35.0), 
				new Empleado("Trifosa", 25, 18.0),
				new Empleado("Sosipater", 35, 45.0)));
		
		System.out.println("***ORDENAR POR EDAD");
		
		listaEmpleado.sort(new OrderByEdad());

		for (Empleado e: listaEmpleado)
			System.out.println(e);
		
		System.out.println("***ORDENAR POR SUELDO");
		
		listaEmpleado.sort(new OrderBySueldo());

		for (Empleado e: listaEmpleado)
			System.out.println(e);
		
	}

}
