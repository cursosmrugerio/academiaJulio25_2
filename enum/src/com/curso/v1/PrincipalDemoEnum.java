package com.curso.v1;

public class PrincipalDemoEnum {
	
	public enum Operation {
	    ADD {
	        public int apply(int a, int b) { return a + b; }
	    },
	    MULTIPLY {
	        public int apply(int a, int b) { return a * b; }
	    },
	    DIVIDE {
	    	public int apply(int a, int b) { return a / b; }
	    }
	    ;
	    public abstract int apply(int a, int b);
	}
	
	
	public static void main(String[] args) {
		
		Operation ope1 = Operation.ADD;
		Operation ope2 = Operation.MULTIPLY;
		Operation ope3 = Operation.DIVIDE;
		
		System.out.println("ADD: "+ope1.apply(8, 4));
		System.out.println("MULTIPLY: "+ope2.apply(8, 4));
		System.out.println("DIVIDE: "+ope3.apply(8, 4));


		
	}

}
