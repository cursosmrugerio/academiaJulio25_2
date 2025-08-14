package com.curso.v3;

import java.util.Arrays;
import java.util.Comparator;
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
				"ate");
		
		List<Integer> lista = items.stream()
							.filter(s -> s.startsWith("a"))
							.map(s -> s.length())
							//.sorted((x,y)->x-y)
							.sorted(Comparator.naturalOrder())
							.collect(Collectors.toList());
		
		System.out.println(lista);

		

	}
}
