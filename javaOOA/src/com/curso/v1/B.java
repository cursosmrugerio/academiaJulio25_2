package com.curso.v1;

class A {
    A() { 
        print(); //PRIMERA
    }
    void print() { 
        System.out.print("A "); 
    }
}

class B extends A {
	
	B(){
		super();
	}
	
    int i = 4;
    public static void main(String[] args) {
        A a = new B();
        a.print();
    }
    void print() { //EJECUTA DEL CONSTRUCTOR A
        System.out.print(i + " "); //0 4
    }
}
