package com.curso.record;

import java.util.Objects;

public final class Estudiante {
	
	final private String name;
	final private int age;
	final private StringBuilder code;
	
	public Estudiante(String name, int age, StringBuilder code) {
		super();
		this.name = name;
		this.age = age;
		this.code = code;
	}
	
	@Override
	public String toString() {
		return "Estudiante [name=" + name + ", age=" + age + ", code=" + code + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(age, code, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Estudiante other = (Estudiante) obj;
		return age == other.age && Objects.equals(code, other.code) && Objects.equals(name, other.name);
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public StringBuilder getCode() {
		return code;
	}



	
}
