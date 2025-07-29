package com.tryresource.v0;

public class ConexionMongoDb {
	
	void open() throws Exception{
		System.out.println("Abrir conexion MongoDb");
		//throw new Exception("Error conexión MongoDb");
	}

	void close() throws Exception{
		System.out.println("Cerrar conexion MongoDb");
	}
}
