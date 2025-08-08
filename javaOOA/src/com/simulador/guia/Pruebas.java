package com.simulador.guia;

public class Pruebas {
	public static void main(String[] args) {
		divide(4, 0);
	}

	public static int divide(int a, int b) {
		int c = -1;
		try {
			c = a / b;
		} catch (Exception e) {
			System.out.println("Exception");
		} finally {
			System.err.println("Finally");
		}
		System.out.println("Fin Programa");
		return c;
	}
}