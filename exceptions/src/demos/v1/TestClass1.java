package demos.v1;

public class TestClass1 {
	
	public static void m1() throws Exception {
		throw new Exception("Exception from m1");
	}

	public static void m2() throws Exception {
		try {
			m1();
		} catch (Exception e) { 
			throw e;
		} finally {
			throw new RuntimeException("Exception from finally");
		}
	}

	public static void main(String[] args) {
		try {
			m2();
		} catch (Exception e) {
			System.out.println(e);
			Throwable[] ta = e.getSuppressed();
			for (Throwable t : ta) {
				System.out.println(t.getMessage());
			}
		}
		System.out.println("Fin Programa");
	}
}
