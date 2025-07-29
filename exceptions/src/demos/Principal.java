package demos;

class Pato{
	void volar() {
		int x = 0;
		System.out.println("Volar");
		if (x==0)
			throw new ArrayIndexOutOfBoundsException();
		else 
			throw new StringIndexOutOfBoundsException();
	}
}

public class Principal {

}
