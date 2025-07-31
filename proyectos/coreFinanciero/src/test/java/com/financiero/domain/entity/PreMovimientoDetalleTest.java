package com.financiero.domain.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para PreMovimientoDetalle")
class PreMovimientoDetalleTest {

    private PreMovimientoDetalle preMovimientoDetalle;
    private PreMovimientoDetalleId id;

    @BeforeEach
    void setUp() {
        id = new PreMovimientoDetalleId("GRP001", "EMP001", 1L, "INTERES");
        preMovimientoDetalle = new PreMovimientoDetalle();
        preMovimientoDetalle.setId(id);
        preMovimientoDetalle.setImporteConcepto(new BigDecimal("100.50"));
        preMovimientoDetalle.setNota("Nota de prueba");
    }

    @Test
    @DisplayName("Debe crear PreMovimientoDetalle con constructor vacío")
    void debeCrearPreMovimientoDetalleConConstructorVacio() {
        PreMovimientoDetalle detalle = new PreMovimientoDetalle();
        assertNotNull(detalle);
        assertNull(detalle.getId());
        assertNull(detalle.getImporteConcepto());
        assertNull(detalle.getNota());
    }

    @Test
    @DisplayName("Debe crear PreMovimientoDetalle con constructor completo")
    void debeCrearPreMovimientoDetalleConConstructorCompleto() {
        BigDecimal importe = new BigDecimal("250.75");
        String nota = "Nota de prueba completa";
        
        PreMovimientoDetalle detalle = new PreMovimientoDetalle(id, importe, nota);
        
        assertNotNull(detalle);
        assertEquals(id, detalle.getId());
        assertEquals(importe, detalle.getImporteConcepto());
        assertEquals(nota, detalle.getNota());
    }

    @Test
    @DisplayName("Debe identificar concepto de interés correctamente")
    void debeIdentificarConceptoDeInteres() {
        assertTrue(preMovimientoDetalle.esConceptoInteres());
    }

    @Test
    @DisplayName("Debe identificar concepto de comisión correctamente")
    void debeIdentificarConceptoDeComision() {
        PreMovimientoDetalleId idComision = new PreMovimientoDetalleId("GRP001", "EMP001", 1L, "COMISION");
        preMovimientoDetalle.setId(idComision);
        
        assertTrue(preMovimientoDetalle.esConceptoComision());
    }

    @Test
    @DisplayName("Debe retornar false para concepto que no es interés")
    void debeRetornarFalseParaConceptoQueNoEsInteres() {
        PreMovimientoDetalleId idOtro = new PreMovimientoDetalleId("GRP001", "EMP001", 1L, "CAPITAL");
        preMovimientoDetalle.setId(idOtro);
        
        assertFalse(preMovimientoDetalle.esConceptoInteres());
    }

    @Test
    @DisplayName("Debe retornar false para concepto que no es comisión")
    void debeRetornarFalseParaConceptoQueNoEsComision() {
        assertFalse(preMovimientoDetalle.esConceptoComision());
    }

    @Test
    @DisplayName("Debe manejar getters y setters correctamente")
    void debeManejarGettersYSettersCorrectamente() {
        PreMovimientoDetalle detalle = new PreMovimientoDetalle();
        BigDecimal nuevoImporte = new BigDecimal("500.25");
        String nuevaNota = "Nueva nota";
        
        detalle.setId(id);
        detalle.setImporteConcepto(nuevoImporte);
        detalle.setNota(nuevaNota);
        
        assertEquals(id, detalle.getId());
        assertEquals(nuevoImporte, detalle.getImporteConcepto());
        assertEquals(nuevaNota, detalle.getNota());
    }

    @Test
    @DisplayName("Debe implementar equals correctamente")
    void debeImplementarEqualsCorrectamente() {
        PreMovimientoDetalle detalle1 = new PreMovimientoDetalle(id, new BigDecimal("100.00"), "Nota 1");
        PreMovimientoDetalle detalle2 = new PreMovimientoDetalle(id, new BigDecimal("200.00"), "Nota 2");
        PreMovimientoDetalleId idDiferente = new PreMovimientoDetalleId("GRP002", "EMP002", 2L, "COMISION");
        PreMovimientoDetalle detalle3 = new PreMovimientoDetalle(idDiferente, new BigDecimal("100.00"), "Nota 1");

        // Mismo objeto
        assertEquals(detalle1, detalle1);
        
        // Misma ID, diferentes datos
        assertEquals(detalle1, detalle2);
        
        // Diferente ID
        assertNotEquals(detalle1, detalle3);
        
        // Comparación con null
        assertNotEquals(detalle1, null);
        
        // Comparación con objeto de clase diferente
        assertNotEquals(detalle1, "string");
    }

    @Test
    @DisplayName("Debe implementar hashCode correctamente")
    void debeImplementarHashCodeCorrectamente() {
        PreMovimientoDetalle detalle1 = new PreMovimientoDetalle(id, new BigDecimal("100.00"), "Nota 1");
        PreMovimientoDetalle detalle2 = new PreMovimientoDetalle(id, new BigDecimal("200.00"), "Nota 2");
        
        assertEquals(detalle1.hashCode(), detalle2.hashCode());
    }

    @Test
    @DisplayName("Debe generar toString correctamente")
    void debeGenerarToStringCorrectamente() {
        String toString = preMovimientoDetalle.toString();
        
        assertNotNull(toString);
        assertTrue(toString.contains("PreMovimientoDetalle"));
        assertTrue(toString.contains("id="));
        assertTrue(toString.contains("importeConcepto="));
        assertTrue(toString.contains("nota="));
    }
}