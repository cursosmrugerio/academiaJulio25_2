package com.curso.v0;

import static org.junit.jupiter.api.Assertions.*;

import java.util.function.DoubleBinaryOperator;
import java.io.FileNotFoundException;
import java.time.Duration;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


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
	
	@BeforeEach
	void ejecutaAntesCadaPrueba() {
		System.out.println("**BeforeEach**");
	}
	
	@AfterEach
	void ejecutaDespuesCadaPrueba() {
		System.out.println("**AfterEach**");
	}
	
	@BeforeAll	
	static void ejecutaAntes() {
		System.out.println("**BeforeAll**");
	}
	
	@AfterAll	
	static void ejecutaDespues() {
		System.out.println("**AfterAll**");
	}
	
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
        assertTrue(name.length()>0, "Longitud nombre no es mayor a 0");
    }  
    
    @Test
    void exampleFalse() {
    	String name = "Patrobas";
        assertFalse(name == null, "name es null");
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
    
    @Test
    void compararArreglos() {
        int[] esperado = {1, 2, 3, 4};
        int[] actual = {1, 2, 3, 4};

        assertArrayEquals(esperado, actual, "Los arreglos no son iguales");
    }


    private int square(int number) {
        return number * number;
    }
    
    private int someMethod() {
        return 42;
    }
}
