package demos.v1;

public class TestClass {
	
	public static void m1() throws Exception {
		throw new Exception("Exception from m1");
	}

	public static void m2() {
		try {
			m1();
		} catch (Exception e) { 
			System.out.println(e); //Exception from m1 //1
		} finally {
			throw new RuntimeException("Exception from finally");
		}
	}

	public static void main(String[] args) {
		try {
			m2();
		} catch (Exception e) {
			System.out.println(e); //RuntimeException Exception from finally //2
		}
	}
}
