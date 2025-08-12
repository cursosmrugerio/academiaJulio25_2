package com.curso.q53;

public class TestClass5 extends Thread {

	static Object lock = new Object();

	static volatile int i1, i2; //0

	@Override
	public void run() {
		workWithLooks();
		workWithoutLooks();
	}

	private void workWithLooks() {
		synchronized (lock) { //UN SOLO HILO ENTRA
			i1++; i2++; 
			// leer valor: i
			// incrementar: i+1
			// asignar incremento a: i
		}
	}
	
	private void workWithoutLooks() {
		//synchronized (lock) {
			if (i1 != i2) //SI SE LLEGA A CUMPLIR
				System.out.println("i");
		//}
	}

	public static void main(String[] args) throws InterruptedException {
		System.out.println("Inicio");
		for (int x = 0; x < 10_000_000; x++)
			new TestClass5().start();


	}

}
