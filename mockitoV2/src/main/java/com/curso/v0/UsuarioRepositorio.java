package com.curso.v0;

public interface UsuarioRepositorio {
    UsuarioDto crearUsuario(String nombre);
    UsuarioDto obtenerUsuario(long id);
}