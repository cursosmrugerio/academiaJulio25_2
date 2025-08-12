package com.curso.v3;

public class Bees {
    public static void main(String[] args) {
        try {
        	System.out.println("V3");
            Thread t1 = new Thread();
        	Bees bees1 = new Bees();
        	bees1.go(t1);
        	
        	Thread.sleep(1000);
        	
        	Bees bees2 = new Bees();
        	bees2.out(t1);
        } catch (Exception e) {
            System.out.println("Thrown to main");
        }
    }


	void go(Thread t1) throws InterruptedException {
        t1.start();
        System.out.println("1 ");
        synchronized(t1) {
        	System.out.println("isAlive: "+t1.isAlive());
        	t1.wait(); //LIBERA t1
        }
		
        System.out.println("2 ");
    }
	
    private void out(Thread t1) {
    	//t1 work..........
    	synchronized(t1) {
    		t1.notify();
    	}
	}

}