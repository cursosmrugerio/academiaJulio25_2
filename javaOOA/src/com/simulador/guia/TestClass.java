package com.simulador.guia;

class TestClass{
    public static void main(String args[]){
        int k = 0;
        int m = 0;
        for ( int i = 0; i <= 3; i++){
            k++;
            if ( i == 2){
                // line 1
            	i = 4;
            	// continue;
            }
            m++;
        }
        System.out.println( k + ", " + m );
    }
}

//  i  k  m
//  0  0  0
//     1  1
//  1  2  2
//  2  3
//  4     3
//  5


// break
// k  m   i
// 0  0   0
// 1  1
// 2  2   1
// 3      2
