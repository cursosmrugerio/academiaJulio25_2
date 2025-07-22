package com.curso.v2;

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
		Hijo hijo = new Hijo();
		//UPCASTING
		Padre padre = hijo;
		Abuelo abuelo = padre;
		
		//DOWNCASTING
		Padre padre2 = (Padre)abuelo;
		
		Hija hija = (Hija)padre2;
		hija.jugar(); //RuntimeException: ClassCastException
		
		
	}

}
