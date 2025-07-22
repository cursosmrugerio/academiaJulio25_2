package com.curso.v0;

public class AccessTest {
    static int number;
    int result = 10;

    public static void main(String[] args) {
        AccessTest at = new AccessTest();
        at.number = 10;
        System.out.println(at.addSalt(11));
    }

    int addSalt(int salt) {
        return number + salt;
    }
}
