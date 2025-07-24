package com.curso.v2;

interface A{
	void comer();
}

interface B{
	void volar();
}

interface C extends A,B{
}

public class Principal extends Object implements C {

	@Override
	public void comer() {
		
	}

	@Override
	public void volar() {
		
	}

}
