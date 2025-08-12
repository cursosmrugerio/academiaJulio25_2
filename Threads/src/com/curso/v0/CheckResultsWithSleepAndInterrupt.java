package com.curso.v0;

public class CheckResultsWithSleepAndInterrupt {

	private static int counter = 0;

	public static void main(String[] args) {

		final Thread mainThread = Thread.currentThread();

		new Thread(() -> {
			for (int i = 0; i < 1_000_000; i++) {
				counter++;
			}
			mainThread.interrupt(); // DESPIERTA AL MAIN EN LA LINEA 23
			                        // LANZANDO LA EXCEPTION InterruptedException
		}).start();

		while (counter < 1_000_000) {
			System.out.println("Not reached yet");

			try {
				Thread.sleep(1); // 1 SECOND
				System.out.println("SLEEPING....");
			} catch (InterruptedException e) {
				System.out.println("Interrupted! (Me despertaron!!!)");
			}
		}

		System.out.println("Reached: " + counter);

	}

}
