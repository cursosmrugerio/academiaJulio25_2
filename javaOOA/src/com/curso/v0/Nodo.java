package com.curso.v0;

public class Nodo {
	
	//HAS-A
    int numero;
    Nodo siguiente;
 
    Nodo(int numero) {
        this.numero = numero;
    }
 
    public static void main(String[] args) {
        Nodo x = new Nodo(1);
        Nodo y = new Nodo(2);
        Nodo z = new Nodo(3);
 
        x.siguiente = y;
        y.siguiente = z;
        
        System.out.println(z == x.siguiente.siguiente); //true
        System.out.println(z == y.siguiente); //true
        System.out.println(z.numero);
 
        cambiar(x, z);
        System.out.println("x.siguiente.numero = " + x.siguiente.numero); //99
        System.out.println("y.siguiente.numero = " + y.siguiente.numero); //99
    }
 
    static void cambiar(Nodo a, Nodo b) {
        a.siguiente = b;
        System.out.println(b.siguiente); //null
        //b.siguiente = a.siguiente;
        //System.out.println("b.numero: "+b.numero); //3
        b.siguiente.numero = 99; //NullPointerException
    }
}  