package com.curso.v0;

public class CalculatorService {
    
    private final IMathService mathService;
    
    public CalculatorService(IMathService mathService) {
        this.mathService = mathService;
    }
    
    public double calculate(String operation, double a, double b) {
        switch (operation.toLowerCase()) {
            case "divide":
                return mathService.divide(a, b);
            case "multiply":
                return mathService.multiply(a, b);
            default:
                throw new UnsupportedOperationException("Operación no soportada: " + operation);
        }
    }
    
    public String analyzeNumber(int number) {
        if (mathService.isPrime(number)) {
            return "El número " + number + " es primo";
        } else {
            return "El número " + number + " no es primo";
        }
    }
}