package com.curso.v0;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

// 1. Habilitar la extensión de Mockito para JUnit 5
@ExtendWith(MockitoExtension.class)
class UsuarioServicioTest {

    // 2. Crear un mock (objeto simulado) para la dependencia
    @Mock
    private UsuarioRepositorio usuarioRepositorio;

    // 3. Crear una instancia de la clase a probar e inyectar los mocks
    @InjectMocks
    private UsuarioServicio usuarioServicio;

    @Test
    @DisplayName("Dado un usuario para crear, esperamos un usuario creado")
    void dadoUsuarioParaCrearEsperamosUnUsuarioCreado() {
        // Arrange (Preparación)
        // Objeto que esperamos que nos devuelva el servicio
        UsuarioDto esperado = new UsuarioDto(1L, "Prueba");

        // 4. Definir el comportamiento del mock (stubbing)
        // Cuando se llame al repositorio con "Prueba", debe devolver nuestro objeto "esperado"
        Mockito.when(usuarioRepositorio.crearUsuario("Prueba")).thenReturn(esperado);

        // Act (Ejecución)
        // Llamamos al método del servicio que queremos probar
        UsuarioDto resultado = usuarioServicio.crearUsuario("Prueba");

        // Assert (Verificación)
        // Comprobamos que el ID del resultado es el esperado
        Assertions.assertEquals(esperado.getId(), resultado.getId());
        // Comprobamos que el nombre del resultado es el esperado
        Assertions.assertEquals(esperado.getNombre(), resultado.getNombre(), "Los nombres son diferentes");
        // Comprobamos que el objeto completo es el esperado
        Assertions.assertEquals(esperado, resultado);

        // 5. Verificar que la interacción con el mock ocurrió
        Mockito.verify(usuarioRepositorio).crearUsuario("Prueba");
    }

    @Test
    void testObtenerUsuarioConMockito() {
        // Arrange
        UsuarioDto esperado = new UsuarioDto(1L, "Juan");

        // Definimos el comportamiento: cuando se busque por el id 1, devolver el usuario "esperado"
        Mockito.when(usuarioRepositorio.obtenerUsuario(1L)).thenReturn(esperado);

        // Act
        UsuarioDto resultado = usuarioServicio.obtenerUsuario(1L);

        // Assert
        Assertions.assertEquals(esperado, resultado);

        // Verify
        // Verificamos que el método obtenerUsuario fue llamado con el id 1L exactamente una vez
        Mockito.verify(usuarioRepositorio).obtenerUsuario(1L);
    }
}