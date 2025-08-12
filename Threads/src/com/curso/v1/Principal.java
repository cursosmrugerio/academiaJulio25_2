package com.curso.v1;

public class Principal {
	
	public static void main(String[] args) throws InterruptedException {
		
		System.out.println("Hilo main inicio"); //1
		
		Thread hilo1 = new Thread(() -> System.out.println("Hello 1"));
		
		Thread hilo2 = new Thread(() -> System.out.println("Hello 2"));
		
		Thread hilo3 = new Thread(() -> System.out.println("Hello 3"));

		hilo1.start(); 
		
		hilo2.start();
		
		hilo3.start();
		
		System.out.println("Hilo main fin"); 

	}

}
