package demos.v2;

public class Test {
	static String s = "";

	//                        1      2
	public static void m0(int a, int b) {
		s += a; //1  //s = s + a
		m2(); //< NullPointerException
		m1(b);
	}

	public static void m1(int i) {
		s += i;
	}

	public static void m2() {
		throw new NullPointerException("aa");
	}

	public static void m() {
		m0(1, 2); //< NullPointerException
		m1(3);
	}

	public static void main(String args[]) {
		try {
			m(); //< NullPointerException
		} catch (Exception e) {
		}
		System.out.println(s); //1
	}
}