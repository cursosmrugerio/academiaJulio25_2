package com.curso.v0;

public class PrincipalOr2 {

	public static void main(String[] args) {

		int x = 11; // 1011
		int y = 11; // 1011
		//             0000 //0
	
		x = 11; // 1011
		y = 13; // 1101
		//         0110 //6
		
		x = 90; // 1011010
		y = 20; // 0010100
		//         1001110 //78
		
		int result = x ^ y;
		
		System.out.println(result); 
		
		
	}

}
