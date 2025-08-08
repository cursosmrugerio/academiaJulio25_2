package com.curso.v0;

import java.util.function.Consumer;
import java.util.function.LongToDoubleFunction;
import java.util.function.BiPredicate;

public class Principal {
	
	public static void main(String[] args) {
		
		//CONSUMER //DEFINICION LAMBDA
		Consumer<String> cons = cadena -> System.out.println("Cadena: "+cadena);
		
		cons.accept("Hello Java");
		
		System.out.println("End program");
		
		//LongToDoubleFunction
		LongToDoubleFunction ldf = x -> Double.valueOf(x);
		
		double d = ldf.applyAsDouble(100L);
		
		System.out.println(d);
		
		//BiPredicate
		BiPredicate<String,Integer> bp = (s,i) -> s.length()>i;
		
		boolean b = bp.test("HelloWorldJava", 20);
		
		System.out.println(b);
		
		Runnable run = () -> System.out.println("Hola");
		
		
	}

}
