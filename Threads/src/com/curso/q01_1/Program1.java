package com.curso.q01_1;

public class Program1 {

    public static void main(String[] args) {
        Object lock = new Object(); // Objeto compartido para el monitor

        Thread waitingThread = new Thread(() -> {
            synchronized (lock) {
                System.out.println("Hilo esperando: Adquiriendo el monitor y esperando..."); //1
                try {
                    lock.wait(); // El hilo espera y libera el monitor
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Hilo esperando: Notificado y re-adquiriendo el monitor. Continuando..."); //5
            }
        });

        Thread notifyingThread = new Thread(() -> {
            try {
                Thread.sleep(2000); // Espera un tiempo para que el hilo esperando comience
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock) {
                System.out.println("Hilo notificador: Adquiriendo el monitor y notificando..."); //2
                lock.notify(); // Notifica a un hilo que está esperando en este monitor
                System.out.println("Hilo notificador: Notificación enviada. Manteniendo el monitor por un tiempo..."); //3
                try {
                    Thread.sleep(3000); // Mantiene el monitor por un tiempo después de notificar
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Hilo notificador: Liberando el monitor."); //4
            }
        });

        waitingThread.start();
        notifyingThread.start();

        try {
            waitingThread.join();
            notifyingThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Fin del Programa 1."); //6
    }
}

