package com.financiero.domain.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para DiaFestivoId")
class DiaFestivoIdTest {

    private DiaFestivoId id;
    private LocalDate fechaFestiva;

    @BeforeEach
    void setUp() {
        fechaFestiva = LocalDate.of(2025, 1, 1);
        id = new DiaFestivoId("GRP001", "EMP001", "MX", fechaFestiva);
    }

    @Test
    @DisplayName("Debe crear DiaFestivoId con constructor vacío")
    void debeCrearDiaFestivoIdConConstructorVacio() {
        DiaFestivoId nuevoId = new DiaFestivoId();
        assertNotNull(nuevoId);
        assertNull(nuevoId.getClaveGrupoEmpresa());
        assertNull(nuevoId.getClaveEmpresa());
        assertNull(nuevoId.getClavePais());
        assertNull(nuevoId.getFechaDiaFestivo());
    }

    @Test
    @DisplayName("Debe crear DiaFestivoId con constructor completo")
    void debeCrearDiaFestivoIdConConstructorCompleto() {
        assertEquals("GRP001", id.getClaveGrupoEmpresa());
        assertEquals("EMP001", id.getClaveEmpresa());
        assertEquals("MX", id.getClavePais());
        assertEquals(fechaFestiva, id.getFechaDiaFestivo());
    }

    @Test
    @DisplayName("Debe manejar getters y setters correctamente")
    void debeManejarGettersYSettersCorrectamente() {
        DiaFestivoId nuevoId = new DiaFestivoId();
        LocalDate nuevaFecha = LocalDate.of(2025, 7, 4);
        
        nuevoId.setClaveGrupoEmpresa("GRP002");
        nuevoId.setClaveEmpresa("EMP002");
        nuevoId.setClavePais("US");
        nuevoId.setFechaDiaFestivo(nuevaFecha);
        
        assertEquals("GRP002", nuevoId.getClaveGrupoEmpresa());
        assertEquals("EMP002", nuevoId.getClaveEmpresa());
        assertEquals("US", nuevoId.getClavePais());
        assertEquals(nuevaFecha, nuevoId.getFechaDiaFestivo());
    }

    @Test
    @DisplayName("Debe implementar equals correctamente")
    void debeImplementarEqualsCorrectamente() {
        DiaFestivoId id1 = new DiaFestivoId("GRP001", "EMP001", "MX", fechaFestiva);
        DiaFestivoId id2 = new DiaFestivoId("GRP001", "EMP001", "MX", fechaFestiva);
        DiaFestivoId id3 = new DiaFestivoId("GRP002", "EMP002", "US", LocalDate.of(2025, 7, 4));

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
        DiaFestivoId id1 = new DiaFestivoId("GRP001", "EMP001", "MX", fechaFestiva);
        DiaFestivoId id2 = new DiaFestivoId("GRP001", "EMP001", "MX", fechaFestiva);
        
        assertEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    @DisplayName("Debe generar toString correctamente")
    void debeGenerarToStringCorrectamente() {
        String toString = id.toString();
        
        assertNotNull(toString);
        assertTrue(toString.contains("DiaFestivoId"));
        assertTrue(toString.contains("claveGrupoEmpresa='GRP001'"));
        assertTrue(toString.contains("claveEmpresa='EMP001'"));
        assertTrue(toString.contains("clavePais='MX'"));
        assertTrue(toString.contains("fechaDiaFestivo=2025-01-01"));
    }

    @Test
    @DisplayName("Debe manejar equals con campos nulos")
    void debeManejarEqualsConCamposNulos() {
        DiaFestivoId idConNulos = new DiaFestivoId();
        DiaFestivoId otroIdConNulos = new DiaFestivoId();
        
        assertEquals(idConNulos, otroIdConNulos);
        assertNotEquals(id, idConNulos);
    }

    @Test
    @DisplayName("Debe manejar hashCode con campos nulos")
    void debeManejarHashCodeConCamposNulos() {
        DiaFestivoId idConNulos = new DiaFestivoId();
        assertDoesNotThrow(() -> idConNulos.hashCode());
    }
}