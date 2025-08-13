package com.curso.q01_2;

public class Bees {
    public static void main(String[] args) {
        try {
            new Bees().go();
        } catch (Exception e) {
        	e.printStackTrace();
            System.out.println("thrown to main");
        }
        System.out.println("Fin de Programa");
    }

    synchronized void go() throws InterruptedException  {
        Thread t1 = new Thread(); 
        t1.start();
        System.out.print("1 ");
        
        this.wait(5000); //Thread main
        
        System.out.print("2 ");
    }
}