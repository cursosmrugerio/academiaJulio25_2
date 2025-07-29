package com.tryresource.v3;

public class ConexionMongoDb implements AutoCloseable {
	
	void open() throws Exception{
		System.out.println("Abrir conexion MongoDb");
		throw new Exception("Error abrir conexión MongoDb");
	}

	@Override
	public void close() throws Exception{
		System.out.println("Cerrar conexion MongoDb");
		//throw new Exception("Error cerrar conexión MongoDb");
	}
}
