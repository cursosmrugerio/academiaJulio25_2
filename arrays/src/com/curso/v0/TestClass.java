package com.curso.v0;

import java.util.Arrays;

class A {}
class B extends A {}

public class TestClass {
	public static void main(String args[]) {
		A[] a, a1 = null; //0 OBJETOS //2 VARIABLES DE REFERENCIA
		B[] b; //1 VARIABLE DE REFERENCIA
		
		a = new A[10];
		
//		System.out.println(Arrays.toString(a));
//		System.out.println(Arrays.toString(a1));
		
		a1 = a;
		
		b = new B[20];
		
		a = b; // 1
		
		System.out.println(Arrays.toString(a));
		
		//b = a; //ERROR COMPILATION

		b = (B[]) a; // 2
		b = (B[]) a1; // 3 //ClassCastException
	}
}

