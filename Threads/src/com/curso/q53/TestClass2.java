package com.curso.q53;

public class TestClass2 {
	
	public static void main(String[] args) {
		
		Runnable runn = () -> System.out.println("Hello");

		System.out.println("main inicio");
		
		new Thread(runn).start();
		new Thread(runn).start();

		System.out.println("main fin");

	}
	
	
	

}
