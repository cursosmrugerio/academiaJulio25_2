package com.curso.v3;

public class ConexionMongoDb {
	
	private static ConexionMongoDb conexion = new ConexionMongoDb();
	
	private ConexionMongoDb() {
	}
	
	public static ConexionMongoDb getInstance() {
		
		return conexion;
		
	}

}
