package com.curso.v2;

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
		
		List<String> lista = items.stream()
							.filter(s -> s.startsWith("a"))
							.collect(Collectors.toList());
		
		System.out.println(lista);

		

	}
}
