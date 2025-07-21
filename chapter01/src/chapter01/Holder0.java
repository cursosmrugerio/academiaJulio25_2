package chapter01;

public class Holder0 {
	
	//HAS-A
	int value = 1;
	Holder0 link;	
	
	//NO ES UN CONSTRUCTOR
	public void Holder(int val) {
		value = val;
	}
	
	public static void main(String[] args) {
		Holder0 b = new Holder0();
		System.out.println(b.value); //1
	}

}
