package com.curso.v0;

class Ave {
	
	String tipo;
	float peso;
	
	public Ave(String tipo, float peso) {
		super(); //Constructor Object
		this.tipo = tipo;
		this.peso = peso;
		System.out.println("CONSTRUCTOR 1");
	}
	
	public Ave(float peso) { //<====1
		this("Sin Nombre",peso); //Delega Constructor
		System.out.println("CONSTRUCTOR 2");
	}
	
}

public class Principal {

	public static void main(String[] args) {
				
		Ave ave1 = new Ave((float)5.0);
		

	}

}
