package com.curso.v0;

public class Principal extends Object {

	public static void main(String[] args) {

		String s1 = "Hello"; //Pool String
		String s2 = "Hello"; //Pool String
		String s3 = new String("Hello"); 
		
		System.out.println(s1==s2); //true
		System.out.println(s1==s3); //false
		
		System.out.println(s1.equals(s3)); //true
		
		StringBuilder sb1 = new StringBuilder("Hola");
		StringBuilder sb2 = new StringBuilder("Hola");
		
		System.out.println(sb1==sb2); //false
		System.out.println(sb1.equals(sb2)); //false

	}

}
