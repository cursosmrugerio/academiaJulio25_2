package chapter01;

public class Holder1 {
	
	//HAS-A
	int value = 1;
	Holder1 link;	
	
	public Holder1(int val) {
		value = val;
	}
	
	public Holder1() {
	}
	
	public static void main(String[] args) {
		Holder1 b = new Holder1();
		System.out.println(b.value); //1
	}

}
