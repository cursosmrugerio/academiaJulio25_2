package com.curso.v0;

interface IInt{
	//public static final 
	int thevalue=0;
	
	//Métodos abstractos //SIN COMPORTAMIENTO
	
	//JAVA 8 //CON COMPORTAMIENTO
	static int getValue() {
		return thevalue;
	}
}

public class PrincipalPrimitivoStatic implements IInt {
	
	public static void main(String[] args) {
		PrincipalPrimitivoStatic p = 
				new PrincipalPrimitivoStatic();
		int x = IInt.thevalue; //<==CORRECTA
		int y = thevalue;
		int z = p.thevalue; //JAVA 1
		//int w = p.getValue(); //JAVA 8
		//int w = getValue(); //JAVA 8
		int w = IInt.getValue();
		
		System.out.println(x);
		System.out.println(y);
		System.out.println(z);

		
	}

}
