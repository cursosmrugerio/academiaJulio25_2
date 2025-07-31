package com.financiero.domain.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para PreMovimientoDetalleId")
class PreMovimientoDetalleIdTest {

    private PreMovimientoDetalleId id;

    @BeforeEach
    void setUp() {
        id = new PreMovimientoDetalleId("GRP001", "EMP001", 1L, "INTERES");
    }

    @Test
    @DisplayName("Debe crear PreMovimientoDetalleId con constructor vacío")
    void debeCrearPreMovimientoDetalleIdConConstructorVacio() {
        PreMovimientoDetalleId nuevoId = new PreMovimientoDetalleId();
        assertNotNull(nuevoId);
        assertNull(nuevoId.getClaveGrupoEmpresa());
        assertNull(nuevoId.getClaveEmpresa());
        assertNull(nuevoId.getIdPreMovimiento());
        assertNull(nuevoId.getClaveConcepto());
    }

    @Test
    @DisplayName("Debe crear PreMovimientoDetalleId con constructor completo")
    void debeCrearPreMovimientoDetalleIdConConstructorCompleto() {
        assertEquals("GRP001", id.getClaveGrupoEmpresa());
        assertEquals("EMP001", id.getClaveEmpresa());
        assertEquals(1L, id.getIdPreMovimiento());
        assertEquals("INTERES", id.getClaveConcepto());
    }

    @Test
    @DisplayName("Debe manejar getters y setters correctamente")
    void debeManejarGettersYSettersCorrectamente() {
        PreMovimientoDetalleId nuevoId = new PreMovimientoDetalleId();
        
        nuevoId.setClaveGrupoEmpresa("GRP002");
        nuevoId.setClaveEmpresa("EMP002");
        nuevoId.setIdPreMovimiento(5L);
        nuevoId.setClaveConcepto("COMISION");
        
        assertEquals("GRP002", nuevoId.getClaveGrupoEmpresa());
        assertEquals("EMP002", nuevoId.getClaveEmpresa());
        assertEquals(5L, nuevoId.getIdPreMovimiento());
        assertEquals("COMISION", nuevoId.getClaveConcepto());
    }

    @Test
    @DisplayName("Debe implementar equals correctamente")
    void debeImplementarEqualsCorrectamente() {
        PreMovimientoDetalleId id1 = new PreMovimientoDetalleId("GRP001", "EMP001", 1L, "INTERES");
        PreMovimientoDetalleId id2 = new PreMovimientoDetalleId("GRP001", "EMP001", 1L, "INTERES");
        PreMovimientoDetalleId id3 = new PreMovimientoDetalleId("GRP002", "EMP002", 2L, "COMISION");

        // Mismo objeto
        assertEquals(id1, id1);
        
        // Objetos con mismos valores
        assertEquals(id1, id2);
        assertEquals(id2, id1);
        
        // Objetos con valores diferentes
        assertNotEquals(id1, id3);
        
        // Comparación con null
        assertNotEquals(id1, null);
        
        // Comparación con objeto de clase diferente
        assertNotEquals(id1, "string");
    }

    @Test
    @DisplayName("Debe implementar hashCode correctamente")
    void debeImplementarHashCodeCorrectamente() {
        PreMovimientoDetalleId id1 = new PreMovimientoDetalleId("GRP001", "EMP001", 1L, "INTERES");
        PreMovimientoDetalleId id2 = new PreMovimientoDetalleId("GRP001", "EMP001", 1L, "INTERES");
        
        assertEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    @DisplayName("Debe generar toString correctamente")
    void debeGenerarToStringCorrectamente() {
        String toString = id.toString();
        
        assertNotNull(toString);
        assertTrue(toString.contains("PreMovimientoDetalleId"));
        assertTrue(toString.contains("claveGrupoEmpresa='GRP001'"));
        assertTrue(toString.contains("claveEmpresa='EMP001'"));
        assertTrue(toString.contains("idPreMovimiento=1"));
        assertTrue(toString.contains("claveConcepto='INTERES'"));
    }

    @Test
    @DisplayName("Debe manejar equals con campos nulos")
    void debeManejarEqualsConCamposNulos() {
        PreMovimientoDetalleId idConNulos = new PreMovimientoDetalleId();
        PreMovimientoDetalleId otroIdConNulos = new PreMovimientoDetalleId();
        
        assertEquals(idConNulos, otroIdConNulos);
        assertNotEquals(id, idConNulos);
    }
}