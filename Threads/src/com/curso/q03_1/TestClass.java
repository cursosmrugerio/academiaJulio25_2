package com.curso.q03_1;

//Which statements regarding the following code are correct?  

class A implements Runnable {
	@Override
	public void run() {
		System.out.println("Starting loop"); 
		try {
			while(!Thread.interrupted()) { }; //1
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
		Thread t = new Thread(a);
		t.start();
		Thread.sleep(5000); //DUERME main
		t.interrupt(); //Hilo main despierta al hilo t
		t.join(); // Hilo main espera a que termine hilo t
		
		System.out.println("Fin main"); // <=== 

	}
}
