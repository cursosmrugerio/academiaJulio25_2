package com.curso.v0;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTest {
	public static void main(String[] args) {
		List<String> items = Arrays.asList(
				"apple", 
				"banana", 
				"cherry",
				"banana",
				"date");
		
		Stream<String> stream = items.stream();
		long contar = stream.count();
		System.out.println(contar);
		
		Stream<String> stream1 = items.stream();
		stream1.forEach(System.out::println);
		
		Stream<String> stream2 = items.stream();
		Set<String> set = stream2.collect(Collectors.toSet());
		System.out.println(set);
		

	}
}
