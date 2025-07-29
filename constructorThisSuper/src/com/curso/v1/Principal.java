package com.curso.v1;

class Ave {
	
	String tipo; //"Sin Nombre"
	float peso; //0.1f
	
	public Ave(String tipo, float peso) {
		//super(); //Constructor Object
		this.tipo = tipo;
		//this.peso = peso;
		System.out.println("CONSTRUCTOR 1");
	}
	
	public Ave(float peso) {  //<-- 2
		this("Sin Nombre",peso); //Delega Constructor
		this.peso = peso;
		System.out.println("CONSTRUCTOR 2");
	}
	
	public Ave() { //<-- 1
		this(0.1f); 
		System.out.println("CONSTRUCTOR 3");
	}
	
}

public class Principal {

	public static void main(String[] args) {
				
		Ave ave1 = new Ave();
		System.out.println(ave1.tipo);
		System.out.println(ave1.peso);
		

	}

}
