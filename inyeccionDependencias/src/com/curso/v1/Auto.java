package com.curso.v1;

class Motor{
	public void encender() {
		System.out.println("Encender Motor");
	}
}


class Auto {
	//HAS-A
	private Motor motor;

    // Se inyecta el motor desde fuera (por constructor)
    public Auto(Motor motor) {
        this.motor = motor;
    }

    void arrancar() {
        motor.encender();
    }
    
    public static void main(String[] args) {
    	Motor motor = new Motor();
		Auto auto = new Auto(motor);
		auto.arrancar();
		
	}
}