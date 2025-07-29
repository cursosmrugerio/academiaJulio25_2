package com.curso.v0;

//Nota: Esta clase no se muestra explícitamente, pero es necesaria para que el código compile.
//Se puede crear como una clase normal o un record de Java.
public class UsuarioDto {
	private final Long id;
	private final String nombre;

	public UsuarioDto(Long id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	public Long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	// Es útil tener los métodos equals y hashCode para las aserciones
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UsuarioDto that = (UsuarioDto) o;
		return java.util.Objects.equals(id, that.id) && java.util.Objects.equals(nombre, that.nombre);
	}

	@Override
	public int hashCode() {
		return java.util.Objects.hash(id, nombre);
	}
}
