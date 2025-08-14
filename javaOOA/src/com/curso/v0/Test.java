package com.curso.v0;

class Foo {
    public Integer a = 3;
    public void addFive() {
        a += 5;
        System.out.print("f ");
    }
}

class Bar extends Foo {
    public Integer a = 8;
    //@Override
    public void addFive() {
        //super.a += 5; //13
    	a += 5; //13
        //System.out.println(a);
        System.out.print("b ");
    }
}



public class Test {
	
	public static void main(String[] args) {
        Foo f = new Bar();
        f.addFive();
        System.out.println(f.a); //8
        //System.out.println(((Bar)f).a);
    }

}
