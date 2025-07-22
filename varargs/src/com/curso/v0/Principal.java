package com.curso.v0;

public class Principal {
	
	public static void main(String[] args) {
		Principal p = new Principal();
		
		//p.juggle();
		int r; 
		r = p.juggle(true); //0
		r = p.juggle(true, true);//1
		r = p.juggle(false, true, true);//2
		r = p.juggle(false, true, true, false, false);//4

		System.out.println(r); //4
	}

	public int juggle(boolean b, boolean... b2) {
		return b2.length;
	}

}
