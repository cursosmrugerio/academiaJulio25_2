package com.curso.v3;

public class Principal {

	public static void main(String[] args) {

		Runnable printInventory = () -> {
			System.out.println("Printing zoo inventory, " + Thread.currentThread().getName());
		};
							
		System.out.println("begin"); //1
		
		Thread hilo1 = new Thread(printInventory);
		hilo1.setName("Hilo Inventory 1");		
		hilo1.start(); 
		
		Thread hilo2 = new Thread(printInventory);
		hilo2.setName("Hilo Inventory 2");		
		hilo2.start(); 
		
		Thread hilo3 = new Thread(printInventory);
		hilo3.setName("Hilo Inventory 3");		
		hilo3.start(); 
		
		System.out.println("end");

	}

}
