package com.curso.q01_1;

public class Bees {
    public static void main(String[] args) {
    	System.out.println("Inicio de Programa");
        try {
            new Bees().go();
        } catch (Exception e) {
        	e.printStackTrace();
            System.out.println("thrown to main");
        }
        System.out.println("Fin de Programa");
    }

    void go() throws InterruptedException  {
        Thread t1 = new Thread();
        t1.start();
        System.out.print("1 ");
        synchronized(t1) { //Obten el monitor t1
        	t1.wait(5000); 
        }
        System.out.print("2 ");
    }
}