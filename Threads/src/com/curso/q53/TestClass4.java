package com.curso.q53;

class Pato{}

public class TestClass4 extends Thread {

	static Pato pato = new Pato();

	static int i;

	@Override
	public void run() {
		work();
	}

	private void work() {
		synchronized (pato) {
			i++;
			// leer valor: i
			// incrementar: i+1
			// asignar incremento a: i
		}
	}

	public static void main(String[] args) throws InterruptedException {

		System.out.println("i inicio: " + i); // 0

		for (int x = 0; x < 100_000; x++)
			new TestClass4().start();

		Thread.sleep(5000);

		System.out.println("i fin: " + i);

	}

}
