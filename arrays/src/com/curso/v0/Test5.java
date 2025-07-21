package com.curso.v0;

//The following class will print 'index = 2' when compiled and run.  

class Test5 {
	
	public static int[] getArray() {
		//                  0,1,2,3,4,5,6
		//return new int[] {1,2,3,4,5,6,7};
		return null;
	}

	public static void main(String[] args) {
		
		int z = 4;

		try {
			int y = getArray()[z=999];
		}
		catch(Exception e){
			//NO SE DESBORDA EL PROGRAMA
		}
		System.out.println("z: "+z); //999
		
		

		
	}
}