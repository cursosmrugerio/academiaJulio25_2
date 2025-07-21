package primitivo;

public class Empleado {
	
	String nombre;
	StringBuilder curp;
	int edad; //0
	boolean esNomina; //false
	double peso; //0.0
	
	//byte,short,char,float,double,int,long,boolean
	//byte,short,char (int) operaciones

	public static void main(String[] args) {
		Empleado e = new Empleado();
		System.out.println(e.nombre); //null
		System.out.println(e.curp); //null
		System.out.println(e.edad); //0
		System.out.println(e.esNomina); //false
		System.out.println(e.peso); //0.0
		
		short sh1 = 10;
		short sh2 = 20;
		
		int sh3 = sh1 + sh2;
		
		System.out.println(sh3);
		
		int x = 1;
		int y = 010; //OCTAL
		int z = 0b101; //BINARIO
		int w = 0xFEB; //HEXADECIMAL
		
		System.out.println(x+y);
		System.out.println(w);
		
		
		
	}

}
