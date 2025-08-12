package com.curso.v5;

public class Principal {

	public static void main(String[] args) {

		Runnable printInventory = () -> {
			System.out.println("Printing zoo inventory, " + Thread.currentThread().getName());
		};

		Runnable printRecords = () -> {
			for (int i = 0; i < 3; i++)
				System.out.println("Printing record: " + i);
		};

		System.out.println("begin"); // 1

		Thread hilo1 = new Thread(printInventory);
		hilo1.setName("Hilo Inventory 1");
		hilo1.run();
		
		new Thread(printRecords).run();

		Thread hilo2 = new Thread(printInventory);
		hilo2.setName("Hilo Inventory 2");
		hilo2.run();

		System.out.println("end");

	}

}
