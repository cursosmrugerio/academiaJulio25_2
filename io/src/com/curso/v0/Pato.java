package com.curso.v0;

import java.io.Serializable;

public class Pato implements Serializable {
	
	String name;

	public Pato(String name) {
		this.name = name;
	}
	
	public static void main(String[] args) {
		Pato pato1 = new Pato("Donald");
		
		System.out.println(pato1);
	}

}
