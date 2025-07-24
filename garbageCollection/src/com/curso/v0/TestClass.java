package com.curso.v0;

public class TestClass {
    public static void main(String args[]) {
        Student s = new Student("Vinny", "930012"); //<==
        s.grade();
        System.out.println(s.getName()); //Vinny
        s = null; //DESPUES DE LA LINEA 8
        
        s = new Student("Vinny", "930012"); //<====
        s.grade();
        System.out.println(s.getName());
        s = null; //DESPUES DE LA LINEA 13
        
        s = new Student("Vinny", "930012"); //<====
        s.grade();
        System.out.println(s.getName());
        s = null; 
    }
}

