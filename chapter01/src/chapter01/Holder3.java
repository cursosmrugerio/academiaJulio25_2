package chapter01;

public class Holder3 {
	
	//HAS-A
	int value; 
	Holder3 link;	
	String name;

	
	//CONSTRUCTOR
	public Holder3(int val) {
		//this.value = val;
	}
	
	public static void main(String[] args) {
		final Holder3 a = new Holder3(5);
		System.out.println(a.name); //null
		System.out.println(a.value);  //0
		System.out.println(a.link); //null
	}

}
