package com.curso.v0;

class Buddy {
	public Buddy() {
	}
}

public class GCTest {
	public static void initBuddies(Buddy[] ba) {
		ba[0] = new Buddy(); // 1
		ba[1] = new Buddy(); // 2
		ba[0] = ba[1]; // 3
		ba[1] = ba[2]; // 4 // ba[1] = null;
	}

	public static void main(String[] args) {
		Buddy[] ba = new Buddy[3]; // 5
		initBuddies(ba);// 6
		
		//7   //1
		
	}
}