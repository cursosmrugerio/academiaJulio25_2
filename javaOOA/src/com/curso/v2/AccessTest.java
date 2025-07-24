package com.curso.v2;

public class AccessTest {
	static int number; 
	int result = 10;
	
    public static void main(String[] args) {
        AccessTest at = new AccessTest();
        number = 11;
        int number = at.addSalt(at.number);
        System.out.println(number);
        
    }

    int addSalt(int salt){
        return number + result;
    }
}

