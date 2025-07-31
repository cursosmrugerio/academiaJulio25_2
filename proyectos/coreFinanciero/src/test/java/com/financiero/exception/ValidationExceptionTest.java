package com.financiero.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para ValidationException")
class ValidationExceptionTest {

    @Test
    @DisplayName("Debe crear ValidationException con mensaje simple")
    void debeCrearValidationExceptionConMensajeSimple() {
        String mensaje = "Error de validación";
        ValidationException exception = new ValidationException(mensaje);
        
        assertEquals(mensaje, exception.getMessage());
        assertNull(exception.getCause());
        assertNull(exception.getFieldErrors());
    }

    @Test
    @DisplayName("Debe crear ValidationException con mensaje y causa")
    void debeCrearValidationExceptionConMensajeYCausa() {
        String mensaje = "Error de validación";
        Throwable causa = new IllegalArgumentException("Causa del error");
        ValidationException exception = new ValidationException(mensaje, causa);
        
        assertEquals(mensaje, exception.getMessage());
        assertEquals(causa, exception.getCause());
        assertNull(exception.getFieldErrors());
    }

    @Test
    @DisplayName("Debe crear ValidationException con mensaje y errores de campo")
    void debeCrearValidationExceptionConMensajeYErroresDeCampo() {
        String mensaje = "Error de validación de campos";
        Map<String, String> fieldErrors = new HashMap<>();
        fieldErrors.put("nombre", "El nombre es obligatorio");
        fieldErrors.put("email", "El email no es válido");
        
        ValidationException exception = new ValidationException(mensaje, fieldErrors);
        
        assertEquals(mensaje, exception.getMessage());
        assertNull(exception.getCause());
        assertEquals(fieldErrors, exception.getFieldErrors());
        assertEquals(2, exception.getFieldErrors().size());
        assertEquals("El nombre es obligatorio", exception.getFieldErrors().get("nombre"));
        assertEquals("El email no es válido", exception.getFieldErrors().get("email"));
    }

    @Test
    @DisplayName("Debe manejar fieldErrors nulos")
    void debeManejarFieldErrorsNulos() {
        String mensaje = "Error de validación";
        ValidationException exception = new ValidationException(mensaje, (Map<String, String>) null);
        
        assertEquals(mensaje, exception.getMessage());
        assertNull(exception.getFieldErrors());
    }

    @Test
    @DisplayName("Debe manejar fieldErrors vacíos")
    void debeManejarFieldErrorsVacios() {
        String mensaje = "Error de validación";
        Map<String, String> fieldErrors = new HashMap<>();
        ValidationException exception = new ValidationException(mensaje, fieldErrors);
        
        assertEquals(mensaje, exception.getMessage());
        assertEquals(fieldErrors, exception.getFieldErrors());
        assertTrue(exception.getFieldErrors().isEmpty());
    }

    @Test
    @DisplayName("Debe heredar comportamiento de RuntimeException")
    void debeHeredarComportamientoDeRuntimeException() {
        ValidationException exception = new ValidationException("Test");
        
        assertTrue(exception instanceof RuntimeException);
        assertTrue(exception instanceof Exception);
        assertTrue(exception instanceof Throwable);
    }

    @Test
    @DisplayName("Debe permitir modificar fieldErrors después de la creación")
    void debePermitirModificarFieldErrorsDespuesDeLaCreacion() {
        Map<String, String> fieldErrors = new HashMap<>();
        fieldErrors.put("campo1", "Error inicial");
        ValidationException exception = new ValidationException("Test", fieldErrors);
        
        // Modificar el mapa original
        fieldErrors.put("campo2", "Nuevo error");
        
        // La excepción debe reflejar los cambios (no es una copia defensiva)
        assertEquals(2, exception.getFieldErrors().size());
        assertEquals("Nuevo error", exception.getFieldErrors().get("campo2"));
    }

    @Test
    @DisplayName("Debe manejar mensaje nulo")
    void debeManejarMensajeNulo() {
        ValidationException exception = new ValidationException(null);
        assertNull(exception.getMessage());
    }

    @Test
    @DisplayName("Debe manejar causa nula")
    void debeManejarCausaNula() {
        ValidationException exception = new ValidationException("Test", (Throwable) null);
        assertEquals("Test", exception.getMessage());
        assertNull(exception.getCause());
    }
}