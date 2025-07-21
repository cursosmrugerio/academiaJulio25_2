package com.curso.v0;

//The following class will print 'index = 2' when compiled and run.  

class Test3 {
	
	public static int[] getArray() {
		//                0,1,2,3,4,5,6
		return new int[] {1,2,3,4,5,6,7};
	}

	public static void main(String[] args) {
		
		int z = 4;

		int y = getArray()[z];
		
		System.out.println("z: "+z); //4
		System.out.println("y: "+y); //5
		
		System.out.println("**********");
		
		y = getArray()[z=10-z]++;
		
		System.out.println("z: "+z); //6
		System.out.println("y: "+y); //7
		

		
	}
}