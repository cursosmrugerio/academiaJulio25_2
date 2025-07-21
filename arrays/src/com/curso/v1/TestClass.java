package com.curso.v1;

class A {}
class B extends A {}

public class TestClass {
	
	public static void main(String args[]) {
		//a = b; 
		A aa = new B();
		A[] a = new B[20]; //<====
		
		//NO SE PUEDE
		//B b = new A();
		//B[] b = new A[2];

	}
	
}

