package com.curso.v0;

import static org.junit.jupiter.api.Assertions.*;

import java.util.function.DoubleBinaryOperator;
import java.io.FileNotFoundException;
import java.time.Duration;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class InstanciaExecutable implements org.junit.jupiter.api.function.Executable{
	@Override
	public void execute()  {
    	String name = null;
    	int l = name.length();
	}
}

class InstanciaExecutable2 implements org.junit.jupiter.api.function.Executable{
	@Override
	public void execute() throws Throwable  {
    	throw new FileNotFoundException("Archivo no encontrado");
	}
}

public class MyTest {
	
    @Test
    @DisplayName("Equals enteros")
    void exampleEquals() {
    	int dato = 42;
        int result = someMethod();
        assertEquals(dato, result, "The result should be 42");
    }
    
    @Test
    void exampleTrue() {
    	String name = "Patrobas";
        assertTrue(name.length()>0, "Longitud nombre mayor a 0");
    }  
    
    @Test
    void exampleFalse() {
    	String name = "Patrobas";
        assertFalse(name == null, "name no es false");
    } 
    
    @Test
    void exampleNotNull() {
    	String name = "Epeneto";
        assertNotNull(name, "Nombre no es null");
    } 
    
    @Test
    void exampleThrows() {
    	String name = null;
    	assertThrows(NullPointerException.class, () -> name.length());
    } 
    
    @Test
    void exampleThrows2() {
    	InstanciaExecutable instanciaExecutable = new InstanciaExecutable();
    	assertThrows(NullPointerException.class, instanciaExecutable);
    } 
    
    @Test
    void exampleThrows3() {
    	InstanciaExecutable2 instanciaExecutable = new InstanciaExecutable2();
    	assertThrows(FileNotFoundException.class, instanciaExecutable);
    } 
    
    @Test
    void exampleThrows4() {
    	assertThrows(FileNotFoundException.class, () -> {
    		throw new FileNotFoundException("Archivo no encontrado");} 				
    	);
    } 
    
    @Test
    void exampleEqualsDouble() {
    	double esperado = 10.0;
    	double d1 = 6.0;
    	double d2 = 4.0;
    	
    	DoubleBinaryOperator dbo = (x,y) -> x+y;
    	
    	assertEquals(esperado, dbo.applyAsDouble(d1, d2), "Valor double igual");
    }
    

    @Test @Disabled
    void exampleTest2() {
        boolean condition = "true".equalsIgnoreCase(System.getProperty("runTest"));
        Assumptions.assumeTrue(condition, "Skipping the test because the condition is not met");
    }
    
    @Test @Disabled
    void pruebaRapida() {
        assertTimeout(Duration.ofSeconds(2), () -> {
        	for (int x=0; x<1_000_000; x++) {
        		System.out.println(x);
        	}
        });
    }
    
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    void testSquare(int value) {
        int result = square(value);
        assertEquals(value * value, result, "Square calculation is incorrect");
    }

    private int square(int number) {
        return number * number;
    }
    
    private int someMethod() {
        return 42;
    }
}
