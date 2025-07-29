package demos;

class NewException extends Exception {
}

class AnotherException extends Exception {
}

public class ExceptionTest {
	public static void main(String[] args) throws Exception {
		try {
			m2();
		} finally {
			m3();
		}
	}

	public static void m2()  {
		try {
			throw new NewException();
		}catch(NewException e) {
			System.out.println(e);
		}
	}

	public static void m3() throws AnotherException {
		throw new AnotherException();
	}
}