package com.tryresource.v2;

public class Principal {
	
	public static void main(String[] args) {
		System.out.println("V2");
		
		//try with resources
		try (ConexionMongoDb conn = new ConexionMongoDb()) {
			conn.open();
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Error e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		System.out.println("Fin Programa");
		
	}

}
