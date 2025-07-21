package chapter01;

public class Holder2 {
	
	//HAS-A
	int value = 1; //<===
	Holder2 link;	
	
	//CONSTRUCTOR
	public Holder2(int value) {
		this.value = value;
	}
	
	public static void main(String[] args) {
		Holder2 b = new Holder2(999999999);
		System.out.println(b.value); //2
	}

}
