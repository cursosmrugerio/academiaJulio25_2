public class Program2 {

    private final Object lock = new Object(); // Objeto para sincronizar métodos

    public static void main(String[] args) {
        Program2 program = new Program2();

        Thread waitingThread = new Thread(() -> {
            program.waitingMethod();
        });

        Thread notifyingThread = new Thread(() -> {
            program.notifyingMethod();
        });

        waitingThread.start();
        notifyingThread.start();

        try {
            waitingThread.join();
            notifyingThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Fin del Programa 2.");
    }

    public synchronized void waitingMethod() {
        System.out.println("Hilo esperando: Adquiriendo el monitor (this) y esperando...");
        try {
            this.wait(); // El hilo espera y libera el monitor (this)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Hilo esperando: Notificado y re-adquiriendo el monitor (this). Continuando...");
    }

    public synchronized void notifyingMethod() {
        try {
            Thread.sleep(2000); // Espera un tiempo para que el hilo esperando comience
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Hilo notificador: Adquiriendo el monitor (this) y notificando...");
        this.notify(); // Notifica a un hilo que está esperando en este monitor (this)
        System.out.println("Hilo notificador: Notificación enviada. Manteniendo el monitor (this) por un tiempo...");
        try {
            Thread.sleep(3000); // Mantiene el monitor por un tiempo después de notificar
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Hilo notificador: Liberando el monitor (this).");
    }
}

