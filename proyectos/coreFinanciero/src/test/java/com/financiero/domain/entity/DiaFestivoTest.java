package com.financiero.domain.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para DiaFestivo")
class DiaFestivoTest {

    private DiaFestivo diaFestivo;
    private DiaFestivoId id;
    private LocalDate fechaFestiva;

    @BeforeEach
    void setUp() {
        fechaFestiva = LocalDate.of(2025, 1, 1);
        id = new DiaFestivoId("GRP001", "EMP001", "MX", fechaFestiva);
        diaFestivo = new DiaFestivo(id);
    }

    @Test
    @DisplayName("Debe crear DiaFestivo con constructor vacío")
    void debeCrearDiaFestivoConConstructorVacio() {
        DiaFestivo dia = new DiaFestivo();
        assertNotNull(dia);
        assertNull(dia.getId());
    }

    @Test
    @DisplayName("Debe crear DiaFestivo con constructor completo")
    void debeCrearDiaFestivoConConstructorCompleto() {
        assertEquals(id, diaFestivo.getId());
    }

    @Test
    @DisplayName("Debe manejar getters y setters correctamente")
    void debeManejarGettersYSettersCorrectamente() {
        DiaFestivo dia = new DiaFestivo();
        DiaFestivoId nuevoId = new DiaFestivoId("GRP002", "EMP002", "US", LocalDate.of(2025, 7, 4));
        
        dia.setId(nuevoId);
        
        assertEquals(nuevoId, dia.getId());
    }

    @Test
    @DisplayName("Debe obtener fecha del día festivo")
    void debeObtenerFechaDelDiaFestivo() {
        LocalDate fechaObtenida = diaFestivo.getFechaDiaFestivo();
        assertEquals(fechaFestiva, fechaObtenida);
    }

    @Test
    @DisplayName("Debe verificar si es fecha festiva correctamente")
    void debeVerificarSiEsFechaFestiva() {
        assertTrue(diaFestivo.esFechaFestiva(fechaFestiva));
        assertFalse(diaFestivo.esFechaFestiva(LocalDate.of(2025, 1, 2)));
    }

    @Test
    @DisplayName("Debe identificar si es país México")
    void debeIdentificarSiEsPaisMexico() {
        assertTrue(diaFestivo.esPaisMexico());
        
        DiaFestivoId idUS = new DiaFestivoId("GRP001", "EMP001", "US", fechaFestiva);
        DiaFestivo diaUS = new DiaFestivo(idUS);
        assertFalse(diaUS.esPaisMexico());
    }

    @Test
    @DisplayName("Debe implementar equals correctamente")
    void debeImplementarEqualsCorrectamente() {
        DiaFestivo dia1 = new DiaFestivo(id);
        DiaFestivo dia2 = new DiaFestivo(id);
        DiaFestivoId idDiferente = new DiaFestivoId("GRP002", "EMP002", "US", LocalDate.of(2025, 7, 4));
        DiaFestivo dia3 = new DiaFestivo(idDiferente);

        // Mismo objeto
        assertEquals(dia1, dia1);
        
        // Objetos con mismo ID
        assertEquals(dia1, dia2);
        
        // Objetos con ID diferente
        assertNotEquals(dia1, dia3);
        
        // Comparación con null
        assertNotEquals(dia1, null);
        
        // Comparación con objeto de clase diferente
        assertNotEquals(dia1, "string");
    }

    @Test
    @DisplayName("Debe implementar hashCode correctamente")
    void debeImplementarHashCodeCorrectamente() {
        DiaFestivo dia1 = new DiaFestivo(id);
        DiaFestivo dia2 = new DiaFestivo(id);
        
        assertEquals(dia1.hashCode(), dia2.hashCode());
    }

    @Test
    @DisplayName("Debe generar toString correctamente")
    void debeGenerarToStringCorrectamente() {
        String toString = diaFestivo.toString();
        
        assertNotNull(toString);
        assertTrue(toString.contains("DiaFestivo"));
        assertTrue(toString.contains("id="));
    }

    @Test
    @DisplayName("Debe manejar ID nulo en métodos de dominio")
    void debeManejarIdNuloEnMetodosDeDominio() {
        DiaFestivo diaConIdNulo = new DiaFestivo();
        
        assertThrows(NullPointerException.class, () -> diaConIdNulo.getFechaDiaFestivo());
        assertThrows(NullPointerException.class, () -> diaConIdNulo.esFechaFestiva(LocalDate.now()));
        assertThrows(NullPointerException.class, () -> diaConIdNulo.esPaisMexico());
    }
}