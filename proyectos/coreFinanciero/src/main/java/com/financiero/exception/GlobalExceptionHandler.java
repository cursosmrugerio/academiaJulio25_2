package com.financiero.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Manejador global de excepciones para la aplicación
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex, WebRequest request) {
        logger.error("Error de negocio: {}", ex.getMessage(), ex);
        
        ErrorResponse errorResponse = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.BAD_REQUEST.value())
            .error("Error de Negocio")
            .message(ex.getMessage())
            .path(request.getDescription(false))
            .errorCode(ex.getErrorCode())
            .build();
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex, WebRequest request) {
        logger.error("Recurso no encontrado: {}", ex.getMessage(), ex);
        
        ErrorResponse errorResponse = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.NOT_FOUND.value())
            .error("Recurso No Encontrado")
            .message(ex.getMessage())
            .path(request.getDescription(false))
            .build();
        
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ValidationException ex, WebRequest request) {
        logger.error("Error de validación: {}", ex.getMessage(), ex);
        
        ErrorResponse errorResponse = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.BAD_REQUEST.value())
            .error("Error de Validación")
            .message(ex.getMessage())
            .path(request.getDescription(false))
            .fieldErrors(ex.getFieldErrors())
            .build();
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
        logger.error("Error de validación de argumentos: {}", ex.getMessage(), ex);
        
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
            fieldErrors.put(error.getField(), error.getDefaultMessage())
        );
        
        ErrorResponse errorResponse = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.BAD_REQUEST.value())
            .error("Error de Validación")
            .message("Datos de entrada inválidos")
            .path(request.getDescription(false))
            .fieldErrors(fieldErrors)
            .build();
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        logger.error("Error de violación de restricciones: {}", ex.getMessage(), ex);
        
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String fieldName = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            fieldErrors.put(fieldName, message);
        });
        
        ErrorResponse errorResponse = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.BAD_REQUEST.value())
            .error("Error de Restricciones")
            .message("Violación de restricciones de datos")
            .path(request.getDescription(false))
            .fieldErrors(fieldErrors)
            .build();
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, WebRequest request) {
        logger.error("Error interno del servidor: {}", ex.getMessage(), ex);
        
        ErrorResponse errorResponse = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .error("Error Interno del Servidor")
            .message("Ha ocurrido un error interno. Por favor contacte al administrador.")
            .path(request.getDescription(false))
            .build();
        
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    /**
     * Clase para la respuesta de error estandarizada
     */
    public static class ErrorResponse {
        private LocalDateTime timestamp;
        private int status;
        private String error;
        private String message;
        private String path;
        private String errorCode;
        private Map<String, String> fieldErrors;
        
        // Constructor privado para usar con builder
        private ErrorResponse() {}
        
        public static Builder builder() {
            return new Builder();
        }
        
        // Getters
        public LocalDateTime getTimestamp() { return timestamp; }
        public int getStatus() { return status; }
        public String getError() { return error; }
        public String getMessage() { return message; }
        public String getPath() { return path; }
        public String getErrorCode() { return errorCode; }
        public Map<String, String> getFieldErrors() { return fieldErrors; }
        
        public static class Builder {
            private final ErrorResponse errorResponse = new ErrorResponse();
            
            public Builder timestamp(LocalDateTime timestamp) {
                errorResponse.timestamp = timestamp;
                return this;
            }
            
            public Builder status(int status) {
                errorResponse.status = status;
                return this;
            }
            
            public Builder error(String error) {
                errorResponse.error = error;
                return this;
            }
            
            public Builder message(String message) {
                errorResponse.message = message;
                return this;
            }
            
            public Builder path(String path) {
                errorResponse.path = path;
                return this;
            }
            
            public Builder errorCode(String errorCode) {
                errorResponse.errorCode = errorCode;
                return this;
            }
            
            public Builder fieldErrors(Map<String, String> fieldErrors) {
                errorResponse.fieldErrors = fieldErrors;
                return this;
            }
            
            public ErrorResponse build() {
                return errorResponse;
            }
        }
    }
}