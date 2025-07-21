package chapter01;

public class Holder {

	// HAS-A
	int value;
	Holder link;

	// CONSTRUCTOR
	public Holder(int val) {
		this.value = val;
	}

	public static void main(String[] args) {
		final Holder a = new Holder(5);
		Holder b = new Holder(10);
		a.link = b;

		System.out.println(a.link == b); // true
		System.out.println(b.link); // null

		b.link = setIt(a,b);
		
		System.out.println("b.link == a: "+(b.link == a)); //true
		System.out.println("a.link: "+a.link); //null
		
		
		System.out.println(a.link.value); //NullPointerException
		System.out.println(b.link.value); //5
	}

	public static Holder setIt(final Holder x, final Holder y) {
		System.out.println(x.value); //5
		System.out.println(y.value); //10

		x.link = y.link;
		return x;
	}

}
