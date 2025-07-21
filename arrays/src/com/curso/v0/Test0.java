package com.curso.v0;

//The following class will print 'index = 2' when compiled and run.  

class Test0 {
	
	public static int[] getArray() {
		//                0,1,2,3,4
		return new int[] {1,2,3,4,5};
	}

	public static void main(String[] args) {

		int[] x = getArray();
		int y = getArray()[4];
		
		System.out.println("y: "+y);
		

		
	}
}