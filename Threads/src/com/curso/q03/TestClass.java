package com.curso.q03;

//Which statements regarding the following code are correct?  

class A implements Runnable {
	@Override
	public void run() {
		System.out.println("Starting loop"); // <=== 2
		try {
			Thread.sleep(10000); // 1 sleep for 10 secs
		} catch (Exception e) {
			e.printStackTrace(); // <=== 3
		}
		System.out.println("Ending loop"); // <=== 4
	}
}

public class TestClass {
	public static void main(String args[]) throws Exception {
		System.out.println("Iniciar main"); // <=== 1
		A a = new A();
		Thread t = new Thread(a);
		t.start();
		Thread.sleep(1000); //DUERME main
		t.interrupt(); //Hilo main despierta al hilo t
		t.join(); // Hilo main espera a que termine hilo t
		
		System.out.println("Fin main"); // <=== 

	}
}
