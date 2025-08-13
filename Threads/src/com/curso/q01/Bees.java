package com.curso.q01;

public class Bees {
    public static void main(String[] args) {
        try {
            new Bees().go();
        } catch (Exception e) {
        	e.printStackTrace();
            System.out.println("thrown to main");
        }
    }

    void go() throws InterruptedException  {
        Thread t1 = new Thread();
        t1.start();
        System.out.print("1 "); //Ejecuta Thread main
        t1.wait(5000); //IllegalMonitorStateException RuntimeException
        System.out.print("2 "); //Ejecuta Thread main
    }
}