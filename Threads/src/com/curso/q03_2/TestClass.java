package com.curso.q03_2;

//Which statements regarding the following code are correct?  

class A implements Runnable {
	@Override
	public void run() {
		System.out.println("Starting loop"); 
		try {
			Thread.sleep(10000); //1
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		System.out.println("Ending loop"); 
	}
}

public class TestClass {
	public static void main(String args[]) throws Exception {
		System.out.println("Iniciar main"); 
		A a = new A();
		Thread t = Thread.startVirtualThread(a); //Son Demon Threads
		Thread.sleep(1000); //DUERME main
		t.interrupt(); //Hilo main despierta al hilo t
		//t.join(); // Hilo main espera a que termine hilo t
		
		System.out.println("Fin main"); // <=== 

	}
}
