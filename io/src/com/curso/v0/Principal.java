package com.curso.v0;

import java.io.PrintStream;
import java.util.Scanner;

public class Principal {

	public static void main(String[] args) {

		System.out.println("Hello World");
		
		PrintStream ps = System.out;
		ps.println("Hola Mundo");
		
		PrintStream ps2 = System.err;
		ps2.println("Academy Java");
		
		Scanner sc = new Scanner(System.in);
	    int i = sc.nextInt();
	    
	    System.out.println("Captura usuario: "+i);
		
	}

}
