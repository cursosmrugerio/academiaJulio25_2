package com.curso.v3;

public class AccessTest {
    static int number;
    int result = 10;
    //int salt;

    public static void main(String[] args) {
        AccessTest at = new AccessTest();
        number = 11;
        number = at.addSalt(at.result);
    }

    int addSalt(int salt) {
        //var salt = 10;
        return number + salt;
    }
}
