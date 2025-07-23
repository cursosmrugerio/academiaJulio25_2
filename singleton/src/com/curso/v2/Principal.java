package com.curso.v2;

public class Principal {
	
	public static void main(String[] args) {
		
		ConexionMongoDb conn1 = ConexionMongoDb.getInstance();
		
//		System.out.println(conn1);  
		
		ConexionMongoDb conn2 = ConexionMongoDb.getInstance();
		ConexionMongoDb conn3 = ConexionMongoDb.getInstance();
		ConexionMongoDb conn4 = ConexionMongoDb.getInstance();
		ConexionMongoDb conn5 = ConexionMongoDb.getInstance();

		System.out.println(conn1 == conn4); //true
				
	}

}
