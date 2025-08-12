package com.curso.v6;

public class Zoo {
	
	public static void pause() {
		try {
			Thread.sleep(10_000);
		} catch (InterruptedException e) {}
		System.out.println("Thread job finished!");
	}
	
	public static void main(String[] args) {
		
		System.out.println("Main method begins!");
		
		Thread job = new Thread(() -> pause());
		job.start();
		
		System.out.println("Main method finished!");
		
	}
	

}
