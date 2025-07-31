package com.financiero.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para BusinessException")
class BusinessExceptionTest {

    @Test
    @DisplayName("Debe crear BusinessException con mensaje simple")
    void debeCrearBusinessExceptionConMensajeSimple() {
        String mensaje = "Error de negocio";
        BusinessException exception = new BusinessException(mensaje);
        
        assertEquals(mensaje, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    @DisplayName("Debe crear BusinessException con mensaje y causa")
    void debeCrearBusinessExceptionConMensajeYCausa() {
        String mensaje = "Error de negocio";
        Throwable causa = new IllegalArgumentException("Causa del error");
        BusinessException exception = new BusinessException(mensaje, causa);
        
        assertEquals(mensaje, exception.getMessage());
        assertEquals(causa, exception.getCause());
    }

    @Test
    @DisplayName("Debe heredar comportamiento de RuntimeException")
    void debeHeredarComportamientoDeRuntimeException() {
        BusinessException exception = new BusinessException("Test");
        
        assertTrue(exception instanceof RuntimeException);
        assertTrue(exception instanceof Exception);
        assertTrue(exception instanceof Throwable);
    }

    @Test
    @DisplayName("Debe manejar mensaje nulo")
    void debeManejarMensajeNulo() {
        BusinessException exception = new BusinessException(null);
        assertNull(exception.getMessage());
    }

    @Test
    @DisplayName("Debe manejar causa nula")
    void debeManejarCausaNula() {
        BusinessException exception = new BusinessException("Test", (Throwable) null);
        assertEquals("Test", exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    @DisplayName("Debe permitir stack trace normal")
    void debePermitirStackTraceNormal() {
        BusinessException exception = new BusinessException("Test error");
        StackTraceElement[] stackTrace = exception.getStackTrace();
        assertNotNull(stackTrace);
        assertTrue(stackTrace.length > 0);
    }

    @Test
    @DisplayName("Debe crear BusinessException con código de error")
    void debeCrearBusinessExceptionConCodigoDeError() {
        String errorCode = "BUSINESS_001";
        String mensaje = "Error de validación de negocio";
        BusinessException exception = new BusinessException(errorCode, mensaje);
        
        assertEquals(mensaje, exception.getMessage());
        assertEquals(errorCode, exception.getErrorCode());
        assertNull(exception.getCause());
    }

    @Test
    @DisplayName("Debe crear BusinessException con código de error y causa")
    void debeCrearBusinessExceptionConCodigoDeErrorYCausa() {
        String errorCode = "BUSINESS_002";
        String mensaje = "Error de validación de negocio";
        Throwable causa = new IllegalArgumentException("Causa del error");
        BusinessException exception = new BusinessException(errorCode, mensaje, causa);
        
        assertEquals(mensaje, exception.getMessage());
        assertEquals(errorCode, exception.getErrorCode());
        assertEquals(causa, exception.getCause());
    }
}