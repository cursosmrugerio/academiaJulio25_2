package com.tryresource.v3;

public class Principal {
	
	public static void main(String[] args) {
		System.out.println("V3");
		
		ConexionMongoDb conn = new ConexionMongoDb();
		
		try (conn) {
			conn.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//conn = null;
		
		System.out.println("Fin Programa");
		
	}

}
