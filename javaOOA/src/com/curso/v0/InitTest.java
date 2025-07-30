package com.curso.v0;

public class InitTest {
    public InitTest() {
        s1 = sM1("1");
    }

    static String s1 = sM1("a"); 

    String s3 = sM1("2");

    {
        s1 = sM1("3");
    }

    static {
        s1 = sM1("b");
    }

    static String s2 = sM1("c");

    String s4 = sM1("4");

    public static void main(String args[]) {
    	//abc
        InitTest t1 = new InitTest(); //234 1
        System.out.println("***");
        InitTest t2 = new InitTest(); //234 1
    	System.out.println("Hola Mundo");  
    }

    private static String sM1(String s) {
        System.out.println(s); //abc
        return s;
    }
}

