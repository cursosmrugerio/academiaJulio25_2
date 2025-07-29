package com.tryresource.v0;

public class Principal {
	
	public static void main(String[] args) {
		
		ConexionMongoDb conn = new ConexionMongoDb();
		
		try {
			conn.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Fin Programa");
		
	}

}
