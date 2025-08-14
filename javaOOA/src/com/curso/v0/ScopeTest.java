package com.curso.v0;

public class ScopeTest {
	int z; //0 //4

	public static void main(String[] args) {
		ScopeTest myScope = new ScopeTest();
		int z = 6;
		System.out.println(z); //6
		System.out.println(myScope.z); //0
		//System.out.println(this.z); //NO EXISTE EL OBJETO
		myScope.doStuff();
		System.out.println(z); //6
		System.out.print(myScope.z); //4
	}

	void doStuff() {
		int z = 5;
		doStuff2();
		System.out.println("doStuff()");
		System.out.println(this.z); //4
		System.out.println(z); //5
	}

	void doStuff2() {
		z = 4;
	}
	
	static void ejecutaStatic() {
		//System.out.println(z); 
	}
	
	
	
	
	
}