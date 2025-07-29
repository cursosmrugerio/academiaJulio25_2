package com.rules.v1;


class Ave{
	void volar() throws Exception{
		System.out.println("Ave volar"); 
	}
}

class Pato extends Ave{
	@Override
	void volar(){
		System.out.println("Ave volar"); 
	}
}

public class Principal {

}
