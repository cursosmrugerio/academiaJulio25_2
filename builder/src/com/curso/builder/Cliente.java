package com.curso.builder;

import java.util.Date;

public class Cliente {
	
	public static void main(String[] args) {
		Tarea tarea1 = new TareaBuilder(20)
				.setTarea("Comprar leche")
				.setDefinidaPor("Epeneto")
				.setAsignadaA("Andronico")
				.build();
		
		System.out.println("****TAREA1");
		System.out.println(tarea1);
		
		Tarea tarea2 = new TareaBuilder(30)
				.setHecho(false)
				.setAsignadaA("Aristobulo")
				.setDefinidaPor("Urbano")
				.setTarea("Comprar Pan")
				.build();
		
		System.out.println("****TAREA2");
		System.out.println(tarea2);

		Tarea tarea3 = new TareaBuilder(35)
				.setFechaLimite(new Date())
				.setAsignadaA("Filogono")
				.setHecho(false)
				.setTarea("Comprar tortillas")
				.setDefinidaPor("Patrobas")
				.setDescripcion("De maiz azul")
				.build();
		
		System.out.println("****TAREA3");
		System.out.println(tarea3);
		
		
		
	}

}
