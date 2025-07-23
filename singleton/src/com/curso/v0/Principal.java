package com.curso.v0;

public class Principal {
	
	public static void main(String[] args) {
		
		ConexionMongoDb conn1 = new ConexionMongoDb();
		ConexionMongoDb conn2 = new ConexionMongoDb();
		ConexionMongoDb conn3 = new ConexionMongoDb();
		ConexionMongoDb conn4 = new ConexionMongoDb();
		ConexionMongoDb conn5 = new ConexionMongoDb();

		System.out.println(conn1 == conn5); //false
				
	}

}
