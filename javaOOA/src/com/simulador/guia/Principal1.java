package com.simulador.guia;

public class Principal1 {

	public static void main(String[] args) {

		for (int i = 10,j=1; i>j; --i,++j) {
			System.out.println("/: "+j/i);
			System.out.println("%: "+j%i);
			System.out.println("******");
		}
		
		System.out.println("------------------");
		
		int i1 = 01;
		int i2 = 02;
		int i7 = 07;
		int i8 = 010;
		
		//0 Octal
		//0B 0b Binario
		//0X 0x Hexadecimal
		//int i8 = 08;
		
		System.out.println(i1);
		System.out.println(i2);
		System.out.println(i7);
		System.out.println(i8);
		
		int hexA = 0xA;
		int hexF = 0xF;
		
		System.out.println(hexF);
		


		
		// j  i    j/i      j%i
		// 1  10    0        1
		// 2   9    0        2
		// 3   8    0        3
		// 4   7    0        4
		// 5   6    0        5
		// 6   5    
		
	}

}
