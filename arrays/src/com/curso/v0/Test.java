package com.curso.v0;

//The following class will print 'index = 2' when compiled and run.  

class Test {
	
	public static int[] getArray() {
		return null;
	}

	public static void main(String[] args) {
		System.out.println("Array0");
		var index = 1;
		
		try {
			getArray()[index = 2]++;
		} 
		catch (Exception e) {
			//ATRAPA LA EXCEPTION
			//System.out.println(e);
//			e.getMessage();
//			e.printStackTrace();
		} 
		finally {
			System.out.println("index = " + index); //2
		}
		
	}
}