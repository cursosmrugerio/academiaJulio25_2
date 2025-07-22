package com.curso.v0;

public class Principal4 {
	
	public static void main(String[] args) {
		Principal4 p = new Principal4();
		
		//p.juggle();
		int r; 
		//r = p.juggle(true, {true, true}); //NO SE PUEDE
		r = p.juggle(true, new boolean[]{true, true});

		System.out.println(r); //4
	}

	public int juggle(boolean b, boolean... b2) {
		return b2.length;
	}

}
