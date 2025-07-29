package demos.v1;

public class Test {
	static String j = "";

	public static void method(int i) {
		try {
			if (i == 2) {
				throw new Exception();
			}
			j += "1";
		} catch (Exception e) {
			j += "2";
			return;
		} finally {
			j += "3";
		}
		j += "4";
	}

	public static void main(String args[]) {
		method(1); //134
		System.out.println(j);
		
		//j = "";
		
		method(2); //23
		System.out.println(j);
	}
}