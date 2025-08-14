package com.curso.v0;

class A {
    A() { 
        print(); //PRIMER  //0 
    }
    void print() { 
        System.out.print("A "); 
    }
}

class B extends A {
    int i = 4;
    public static void main(String[] args) {
        A a = new B();
        a.print();
    }
    void print() { 
        System.out.print(i + " "); //4
    }
}
