package com.curso.builder;

import java.util.Date;

public class Cliente1 {
	
	public static void main(String[] args) {
				
		TareaBuilder tb1 = new TareaBuilder(20);
		
		TareaBuilder tb2 = tb1.setTarea("Comprar leche");
		
		TareaBuilder tb3 = tb2.setDefinidaPor("Epeneto");
		
		TareaBuilder tb4 = tb3.setAsignadaA("Andronico");
		
		Tarea tarea = tb4.build();
		
		System.out.println(tarea);
		
	}

}
