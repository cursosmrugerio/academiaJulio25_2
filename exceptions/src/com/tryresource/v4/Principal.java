package com.tryresource.v4;

import java.util.stream.Stream;

public class Principal {

	public static void main(String[] args) {

		// Stream son como los cotenetes.
//		try (Stream<String> stream = Stream.of("a", "bb", "ccc")) {
//			stream.map(x -> x.length()).forEach(System.out::println);
//
//			stream.map(x -> x.length()).forEach(System.out::println);
//		}

		Stream<String> stream = Stream.of("a", "bb", "ccc");

		stream.map(x -> x.length()).forEach(System.out::println);

		//stream.map(x -> x.length()).forEach(System.out::println);

	}

}
