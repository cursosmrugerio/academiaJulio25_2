package testPackage;

import other.*;

class Other { static String hello = "Hello"; }

public class Test {
		
	public static void main(String[] args) {
		String hello = "Hello", lo = "lo";
		
		System.out.println(hello); //local
		
		System.out.println(Other.hello); //Clase Other del mismo paquete
		
		System.out.println(testPackage.Other.hello); //Clase Other del mismo paquete
		
		System.out.println(other.Other.hello); //Clase Other del paquete other
		
		System.out.println("**************");
		
		System.out.print((other.Other.hello == hello) + " "); //true
		
		System.out.print((hello == ("Hel"+"lo")) + " ");
		
		String s = "Hel"+"lo";
		
		System.out.println(hello == s); //true
		
		//DOS OBJETOS DIFERENTES PUEDEN TENER EL MISMO HASHCODE
		
		
	}
}
