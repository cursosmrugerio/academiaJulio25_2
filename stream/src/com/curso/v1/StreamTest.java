package com.curso.v1;

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
				"adate");
		
		Stream<String> stream = items.stream();
		
		Stream<String> stream1 =stream.filter(s -> s.startsWith("a"));
		
		List<String> itemsA = stream1.collect(Collectors.toList());
		
		System.out.println(itemsA);

		

	}
}
