package com.curso.v1;

public class AccessTest {
    static int number;
    int result = 10;

    public static void main(String[] args) {
        AccessTest at = new AccessTest();
        var result = 11;
        number = result;
        System.out.println(at.addSalt(at.result));
    }

    int addSalt(int salt){
        return number + salt + this.result;
    }
}

