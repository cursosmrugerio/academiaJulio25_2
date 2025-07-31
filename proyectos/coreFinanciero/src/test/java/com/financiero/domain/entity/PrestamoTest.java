package com.financiero.domain.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para Prestamo")
class PrestamoTest {

    private Prestamo prestamo;
    private PrestamoId id;

    @BeforeEach
    void setUp() {
        id = new PrestamoId("GRP001", "EMP001");
        prestamo = new Prestamo();
        prestamo.setId(id);
        prestamo.setIdPrestamo(1001L);
        prestamo.setClaveDivisa("MXN");
        prestamo.setImportePrestamo(new BigDecimal("100000.00"));
    }

    @Test
    @DisplayName("Debe crear Prestamo con constructor vacío")
    void debeCrearPrestamoConConstructorVacio() {
        Prestamo nuevoPrestamo = new Prestamo();
        assertNotNull(nuevoPrestamo);
        assertNull(nuevoPrestamo.getId());
        assertNull(nuevoPrestamo.getIdPrestamo());
        assertNull(nuevoPrestamo.getClaveDivisa());
        assertNull(nuevoPrestamo.getImportePrestamo());
    }

    @Test
    @DisplayName("Debe crear Prestamo con constructor con parámetros")
    void debeCrearPrestamoConConstructorConParametros() {
        Prestamo nuevoPrestamo = new Prestamo(id, 2002L);
        
        assertNotNull(nuevoPrestamo);
        assertEquals(id, nuevoPrestamo.getId());
        assertEquals(2002L, nuevoPrestamo.getIdPrestamo());
        assertNull(nuevoPrestamo.getClaveDivisa());
        assertNull(nuevoPrestamo.getImportePrestamo());
    }

    @Test
    @DisplayName("Debe manejar getters y setters correctamente")
    void debeManejarGettersYSettersCorrectamente() {
        Prestamo nuevoPrestamo = new Prestamo();
        PrestamoId nuevoId = new PrestamoId("GRP002", "EMP002");
        BigDecimal nuevoImporte = new BigDecimal("250000.50");
        
        nuevoPrestamo.setId(nuevoId);
        nuevoPrestamo.setIdPrestamo(3003L);
        nuevoPrestamo.setClaveDivisa("USD");
        nuevoPrestamo.setImportePrestamo(nuevoImporte);
        
        assertEquals(nuevoId, nuevoPrestamo.getId());
        assertEquals(3003L, nuevoPrestamo.getIdPrestamo());
        assertEquals("USD", nuevoPrestamo.getClaveDivisa());
        assertEquals(nuevoImporte, nuevoPrestamo.getImportePrestamo());
    }

    @Test
    @DisplayName("Debe identificar divisa nacional correctamente")
    void debeIdentificarDivisaNacional() {
        assertTrue(prestamo.esDivisaNacional());
        
        prestamo.setClaveDivisa("USD");
        assertFalse(prestamo.esDivisaNacional());
    }

    @Test
    @DisplayName("Debe identificar divisa extranjera correctamente")
    void debeIdentificarDivisaExtranjera() {
        assertFalse(prestamo.esDivisaExtranjera());
        
        prestamo.setClaveDivisa("USD");
        assertTrue(prestamo.esDivisaExtranjera());
    }

    @Test
    @DisplayName("Debe manejar divisa nula")
    void debeManejarDivisaNula() {
        prestamo.setClaveDivisa(null);
        assertFalse(prestamo.esDivisaNacional());
        assertTrue(prestamo.esDivisaExtranjera());
    }

    @Test
    @DisplayName("Debe implementar equals correctamente")
    void debeImplementarEqualsCorrectamente() {
        Prestamo prestamo1 = new Prestamo(id, 1001L);
        prestamo1.setClaveDivisa("MXN");
        prestamo1.setImportePrestamo(new BigDecimal("100000.00"));
        
        Prestamo prestamo2 = new Prestamo(id, 1001L);
        prestamo2.setClaveDivisa("USD");
        prestamo2.setImportePrestamo(new BigDecimal("200000.00"));
        
        PrestamoId idDiferente = new PrestamoId("GRP002", "EMP002");
        Prestamo prestamo3 = new Prestamo(idDiferente, 2002L);

        // Mismo objeto
        assertEquals(prestamo1, prestamo1);
        
        // Misma ID y mismo idPrestamo, diferentes otros campos
        assertEquals(prestamo1, prestamo2);
        
        // Diferente ID
        assertNotEquals(prestamo1, prestamo3);
        
        // Comparación con null
        assertNotEquals(prestamo1, null);
        
        // Comparación con objeto de clase diferente
        assertNotEquals(prestamo1, "string");
    }

    @Test
    @DisplayName("Debe implementar hashCode correctamente")
    void debeImplementarHashCodeCorrectamente() {
        Prestamo prestamo1 = new Prestamo(id, 1001L);
        Prestamo prestamo2 = new Prestamo(id, 1001L);
        
        assertEquals(prestamo1.hashCode(), prestamo2.hashCode());
    }

    @Test
    @DisplayName("Debe generar toString correctamente")
    void debeGenerarToStringCorrectamente() {
        String toString = prestamo.toString();
        
        assertNotNull(toString);
        assertTrue(toString.contains("Prestamo"));
        assertTrue(toString.contains("id="));
        assertTrue(toString.contains("idPrestamo="));
        assertTrue(toString.contains("claveDivisa="));
        assertTrue(toString.contains("importePrestamo="));
    }

    @Test
    @DisplayName("Debe manejar equals con diferentes idPrestamo")
    void debeManejarEqualsConDiferentesIdPrestamo() {
        Prestamo prestamo1 = new Prestamo(id, 1001L);
        Prestamo prestamo2 = new Prestamo(id, 2002L);
        
        assertNotEquals(prestamo1, prestamo2);
    }

    @Test
    @DisplayName("Debe manejar equals con campos nulos")
    void debeManejarEqualsConCamposNulos() {
        Prestamo prestamoConNulos = new Prestamo();
        Prestamo otroPrestamoConNulos = new Prestamo();
        
        assertEquals(prestamoConNulos, otroPrestamoConNulos);
        assertNotEquals(prestamo, prestamoConNulos);
    }
}