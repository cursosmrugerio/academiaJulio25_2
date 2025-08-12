package com.curso.v1;

public class Bees {
    public static void main(String[] args) {
        try {
        	System.out.println("V1");
            new Bees().go();
        } catch (Exception e) {
            System.out.println("thrown to main");
        }
    }

    void go() throws InterruptedException   {
    	
        Thread t1 = new Thread();
        t1.start();
        System.out.println("1 ");
        synchronized(t1) {
        	System.out.println("isAlive: "+t1.isAlive());
        	t1.wait(5000); //LIBERA t1
        }
		
        System.out.println("2 ");
    }
}