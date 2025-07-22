package com.curso.v0;

public class Principal5 {
	
	public static void main(String[] args) {
		Principal5 p = new Principal5();
		
		int r; 
		r = p.juggle(true, new boolean[2]);

		System.out.println(r); //2
	}

	public int juggle(boolean b, boolean... b2) {
		
		for(boolean bb:b2)
			System.out.println(bb);
		
		return b2.length;
	}

}
