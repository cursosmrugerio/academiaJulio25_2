package com.curso.v0;

class Abuelo{
	void montarCaballo() {
		System.out.println("Montar caballo");
	}
}

class Padre extends Abuelo{
	void dirigirNegocio() {
		System.out.println("Dirigir Negocio");
	}
}

class Hijo extends Padre{
	void hablarIngles() {
		System.out.println("Hablar ingles");
	}
}


public class Principal {

	public static void main(String[] args) {
		Abuelo persona0 = new Hijo();
		
		//DOWNCASTING
		
		((Hijo)persona0).hablarIngles(); 
		
		Hijo persona1 = (Hijo)persona0;
		persona1.hablarIngles();
	}

}
