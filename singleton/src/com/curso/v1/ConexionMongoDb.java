package com.curso.v1;

public class ConexionMongoDb {
	
	private static ConexionMongoDb conexion;
	
	private ConexionMongoDb() {
		
	}
	
	public static ConexionMongoDb getInstance() {
		
		return conexion;
		
	}

}
