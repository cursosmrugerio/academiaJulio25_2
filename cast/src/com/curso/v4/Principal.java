package com.curso.v4;

import java.util.Random;

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
	void jugar() {
		System.out.println("Jugar Carritos");
	}
}

class Hija extends Padre{
	void jugar() {
		System.out.println("Jugar Muñecas");
	}
}



public class Principal {

	public static void main(String[] args) {
		
		Abuelo persona = getPersona();
		
		System.out.println(persona.getClass().getSimpleName());
		
		((Hija)persona).jugar();
		
	}

	private static Abuelo getPersona() {
		
		Hijo hijo = new Hijo();
		Hija hija = new Hija();
		Padre padre = new Padre();
		Abuelo abuelo = new Abuelo();
		
		Abuelo[] arreglo = {hijo,hija,padre,abuelo};
		
		int aleatorio = new Random().nextInt(arreglo.length);
		
		return arreglo[aleatorio];
	}
	
	
	

}
