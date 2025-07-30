package com.financiero.exception;

/**
 * Excepción para recursos no encontrados
 */
public class NotFoundException extends RuntimeException {
    
    private String resourceName;
    private String resourceId;
    
    public NotFoundException(String message) {
        super(message);
    }
    
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public NotFoundException(String resourceName, String resourceId) {
        super(String.format("Recurso '%s' con ID '%s' no encontrado", resourceName, resourceId));
        this.resourceName = resourceName;
        this.resourceId = resourceId;
    }
    
    public String getResourceName() {
        return resourceName;
    }
    
    public String getResourceId() {
        return resourceId;
    }
}