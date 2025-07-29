package com.tryresource.v1;

public class ConexionMongoDb  {
	
	void open() throws Exception{
		System.out.println("Abrir conexion MongoDb");
		//throw new Exception("Error abrir conexión MongoDb");
	}

	void close() throws Exception{
		System.out.println("Cerrar conexion MongoDb");
		throw new Exception("Error cerrar conexión MongoDb");
	}
}
