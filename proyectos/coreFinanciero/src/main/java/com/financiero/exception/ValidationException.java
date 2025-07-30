package com.financiero.exception;

import java.util.Map;

/**
 * Excepción para errores de validación
 */
public class ValidationException extends RuntimeException {
    
    private Map<String, String> fieldErrors;
    
    public ValidationException(String message) {
        super(message);
    }
    
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public ValidationException(String message, Map<String, String> fieldErrors) {
        super(message);
        this.fieldErrors = fieldErrors;
    }
    
    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }
}