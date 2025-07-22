package com.curso.v0;

public class ParamTest {
	public static void printSum(int a, int b) {
		System.out.println("In int " + (a + b));
	}

	public static void printSum(double... a) {
		System.out.println("In double " + (a[0] + a[1]));
	}

	public static void printSum(float a, float b) {
		System.out.println("In float " + (a + b));
	}

	public static void printSum(int a, float b) {
		System.out.println("In intfloat " + (a + b));
	}

	public static void main(String[] args) {
		
		int i = 8;
		float f = i;
		int i2 = (int)f;
		
		printSum(1, 2.0);
	}
}
