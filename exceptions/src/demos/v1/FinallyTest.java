package demos.v1;

public class FinallyTest {
	public static void main(String args[]) throws Exception {
		try {
			m1(); //<===
			System.out.println("A");
		}
//		catch(Exception e) {
//			System.out.println("Z"); //Z
//		} 
		finally {
			System.out.println("B"); //B
		}
		System.out.println("C"); //C
	}

	public static void m1() throws Exception {
		throw new Exception();
	}
}