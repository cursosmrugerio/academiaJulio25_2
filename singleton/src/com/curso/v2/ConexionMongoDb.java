package com.curso.v2;

public class ConexionMongoDb {
	
	private static ConexionMongoDb conexion;
	
	private ConexionMongoDb() {
	}
	
	public static ConexionMongoDb getInstance() {
		
		if (conexion == null)
			conexion = new ConexionMongoDb();
		
		return conexion;
		
	}

}
