package com.financiero.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para NotFoundException")
class NotFoundExceptionTest {

    @Test
    @DisplayName("Debe crear NotFoundException con mensaje simple")
    void debeCrearNotFoundExceptionConMensajeSimple() {
        String mensaje = "Recurso no encontrado";
        NotFoundException exception = new NotFoundException(mensaje);
        
        assertEquals(mensaje, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    @DisplayName("Debe crear NotFoundException con mensaje y causa")
    void debeCrearNotFoundExceptionConMensajeYCausa() {
        String mensaje = "Recurso no encontrado";
        Throwable causa = new IllegalArgumentException("Causa del error");
        NotFoundException exception = new NotFoundException(mensaje, causa);
        
        assertEquals(mensaje, exception.getMessage());
        assertEquals(causa, exception.getCause());
    }

    @Test
    @DisplayName("Debe heredar comportamiento de RuntimeException")
    void debeHeredarComportamientoDeRuntimeException() {
        NotFoundException exception = new NotFoundException("Test");
        
        assertTrue(exception instanceof RuntimeException);
        assertTrue(exception instanceof Exception);
        assertTrue(exception instanceof Throwable);
    }

    @Test
    @DisplayName("Debe manejar mensaje nulo")
    void debeManejarMensajeNulo() {
        NotFoundException exception = new NotFoundException(null);
        assertNull(exception.getMessage());
    }

    @Test
    @DisplayName("Debe manejar causa nula")
    void debeManejarCausaNula() {
        NotFoundException exception = new NotFoundException("Test", (Throwable) null);
        assertEquals("Test", exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    @DisplayName("Debe permitir stack trace normal")
    void debePermitirStackTraceNormal() {
        NotFoundException exception = new NotFoundException("Test error");
        StackTraceElement[] stackTrace = exception.getStackTrace();
        assertNotNull(stackTrace);
        assertTrue(stackTrace.length > 0);
    }

    @Test
    @DisplayName("Debe crear NotFoundException con nombre de recurso e ID")
    void debeCrearNotFoundExceptionConNombreDeRecursoEId() {
        String resourceName = "Usuario";
        String resourceId = "123";
        NotFoundException exception = new NotFoundException(resourceName, resourceId);
        
        String expectedMessage = "Recurso 'Usuario' con ID '123' no encontrado";
        assertEquals(expectedMessage, exception.getMessage());
        assertEquals(resourceName, exception.getResourceName());
        assertEquals(resourceId, exception.getResourceId());
        assertNull(exception.getCause());
    }
}