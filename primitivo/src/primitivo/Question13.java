package primitivo;

public class Question13 {

	public static void main(String[] args) {

		int a = 5, b = 7, k = 0;
		Integer m = null;
		
		k = new Integer(a) + new Integer(b);
		k = new Integer(a) + b;
		k = b + new Integer(a);
		
		System.out.println(k);
		
		m = new Integer(a) + new Integer(b);
		System.out.println(m);
		
	}

}
