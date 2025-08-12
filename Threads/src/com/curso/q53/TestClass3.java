package com.curso.q53;

public class TestClass3 extends Thread {

	static Object o = new Object();

	static int i;

	@Override
	public void run() {
		work();
	}

	private void work() {
		synchronized (o) {
			i++;
			// leer valor: i
			// incrementar: i+1
			// asignar incremento a: i
		}
	}

	public static void main(String[] args) throws InterruptedException {

		System.out.println("i inicio: " + i); // 0

		for (int x = 0; x < 100_000; x++)
			new TestClass3().start();

		Thread.sleep(5000);

		System.out.println("i fin: " + i);

	}

}
