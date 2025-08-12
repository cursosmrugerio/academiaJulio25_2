package com.curso.v0;

class MyRunnable implements Runnable{
	@Override
	public void run() {
		System.out.println("Hello");
	}
}

public class Principal {
	
	public static void main(String[] args) throws InterruptedException {
		
		System.out.println("Hilo main inicio"); //1
		
		Thread hilo1 = new Thread(new MyRunnable());
		
		hilo1.start(); //2 //3
		
		Thread.sleep(1000); //Dormir el hilo main
		
		System.out.println("Hilo main fin"); //3 //2

	}

}
