package com.curso.v0;

//What would be the result of compiling and running the following program?  
class SomeClass {
	public static void main(String args[]) {
		int size = 10;
		int[] arr = new int[size];
		//int[] arr = {1,2,3,4,5,6,7,8,9,10};
		for (int i = 0; i < size; ++i) {
			arr[i]= i+1;
			System.out.println(arr[i]);
		}
		
		System.out.println("********");
		
		for (int i = 0; i < arr.length; ++i)
			System.out.println(arr[i]);
	}
}