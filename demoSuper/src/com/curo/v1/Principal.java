package com.curo.v1;

class Ave {
	void volar(){
		System.out.println("Ave volar");
	}
	
	void volarAve() {
		System.out.println("Volar como todas las aves");
	}
}

class Aguila extends Ave{
	@Override
	void volar(){
		System.out.println("Aguila volar");
		super.volar();
	}
}

class A {
    public A() {
        System.out.println("A's constructor");
    }
}

class B extends A {
    public B() {
        super();
        System.out.println("B's constructor");
    }
}

class C extends B {
    public C() {
        System.out.println("C's constructor");
    }
}

public class Principal {

	public static void main(String[] args) {

		Ave ave = new Aguila();
		ave.volar();
		
		System.out.println("******");
		
		C c = new C();
	}

}
