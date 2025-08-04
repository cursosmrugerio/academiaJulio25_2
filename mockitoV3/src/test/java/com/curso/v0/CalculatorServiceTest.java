package com.curso.v0;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CalculatorServiceTest {

    @Mock
    private IMathService mathService;
    
    private CalculatorService calculatorService;
    
    @BeforeEach
    void setUp() {
        calculatorService = new CalculatorService(mathService);
    }
    
    @Test
    void testCalculate_Divide_Success() {
        // Arrange - Configuramos el comportamiento del mock
        when(mathService.divide(10.0, 2.0)).thenReturn(5.0);
        
        // Act - Ejecutamos el método a probar
        double result = calculatorService.calculate("divide", 10.0, 2.0);
        
        // Assert - Verificamos el resultado
        assertEquals(5.0, result);
        
        // Verify - Verificamos que se llamó al mock correctamente
        verify(mathService).divide(10.0, 2.0);
    }
    
    @Test
    void testCalculate_Multiply_Success() {
        // Arrange
        when(mathService.multiply(4.0, 3.0)).thenReturn(12.0);
        
        // Act
        double result = calculatorService.calculate("multiply", 4.0, 3.0);
        
        // Assert
        assertEquals(12.0, result);
        verify(mathService).multiply(4.0, 3.0);
    }
    
    @Test 
    void testCalculate_UnsupportedOperation() {
        // Act & Assert - Probamos que lance excepción
        assertThrows(UnsupportedOperationException.class, () -> {
            calculatorService.calculate("subtract", 5.0, 3.0);
        });
        
        // Verify - El mock NO debe ser llamado
        verifyNoInteractions(mathService);
    }
    
    @Test
    void testAnalyzeNumber_Prime() {
        // Arrange
        when(mathService.isPrime(7)).thenReturn(true);
        
        // Act
        String result = calculatorService.analyzeNumber(7);
        
        // Assert
        assertEquals("El número 7 es primo", result);
        verify(mathService).isPrime(7);
    }
    
    @Test
    void testAnalyzeNumber_NotPrime() {
        // Arrange
        when(mathService.isPrime(8)).thenReturn(false);
        
        // Act
        String result = calculatorService.analyzeNumber(8);
        
        // Assert
        assertEquals("El número 8 no es primo", result);
        verify(mathService).isPrime(8);
    }
}