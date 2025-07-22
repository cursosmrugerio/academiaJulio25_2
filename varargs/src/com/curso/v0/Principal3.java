package com.curso.v0;

public class Principal3 {
	
	public static void main(String[] args) {
		Principal3 p = new Principal3();
		
		//p.juggle();
		int[] r; 
		r = p.juggle(true); //0
		r = p.juggle(true, true);//1
		r = p.juggle(false, true, true);//2
		r = p.juggle(true, true, true, false, false);//4

		System.out.println(r[0] + " : "+r[1]); //10 : 4
	}

	public int[] juggle(boolean b, boolean... b2) {
		
		int z;
		z = b ? 10 : 20;
		
		int x;
		x = b2.length;
		
		return new int[] {z,x};
	}

}
