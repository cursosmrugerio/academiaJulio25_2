package com.curso.v0;

public class PrincipalOr {

	public static void main(String[] args) {

		int x = 11; // 1011
		int y = 11; // 1011
		//             1011 //11
	
		x = 11; // 1011
		y = 13; // 1101
		//         1111 //15
		
		x = 90; // 1011010
		y = 20; //   10100
		//         1011110 //94
		
		int result = x | y;
		
		System.out.println(result); 
		
		
	}

}
