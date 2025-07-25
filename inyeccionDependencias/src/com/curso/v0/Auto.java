package com.curso.v0;

class Motor{
	public void encender() {
		System.out.println("Encender Motor");
	}
}


class Auto {
	//HAS-A
	//INYECTAR POR CAMPO
	private Motor motor;

    void arrancar() {
        motor.encender();
    }
    
    public static void main(String[] args) {
    	Motor motor = new Motor();
		Auto auto = new Auto();
		auto.motor = motor;
		auto.arrancar();
		
	}
}