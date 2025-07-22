package com.curso.v1;

class Abuelo{
	void hablarIngles() {
		System.out.println("Hablar ingles britanico");
	}
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
	@Override
	void hablarIngles() {
		System.out.println("Hablar ingles americano");
	}
}


public class Principal {

	public static void main(String[] args) {
		Abuelo persona0 = new Hijo();
		
		persona0.hablarIngles();
		
		((Padre)persona0).dirigirNegocio();
		
		((Hijo)persona0).dirigirNegocio();
		
	}

}
