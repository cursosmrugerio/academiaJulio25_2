package com.rules.v0;

import java.sql.SQLException;

class Ave{
	void volar() throws Exception{
		System.out.println("Ave volar"); 
	}
}

class Pato extends Ave{
	@Override
	void volar() throws SQLException{
		System.out.println("Ave volar"); 
	}
}

public class Principal {

}
