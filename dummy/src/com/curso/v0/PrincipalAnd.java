package com.curso.v0;

public class PrincipalAnd {

	public static void main(String[] args) {

		int x = 11; // 1011
		int y = 11; // 1011
		//             1011 //11
	
		x = 11; // 1011
		y = 13; // 1101
		//         1001 //9
		
		x = 90; // 1011010
		y = 20; // 0010100
		//         0010000 //16
		
		int result = x & y;
		
		System.out.println(result); 
		
		
	}

}
