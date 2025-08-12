package com.curso.q53;

public class TestClass1 extends Thread{
	
	@Override
	public void run() {
		System.out.println("Hello");
	}
	
	public static void main(String[] args) {
		System.out.println("main inicio");
		
		new TestClass1().start();
		new TestClass1().start();

		System.out.println("main fin");

	}
	
	
	

}
