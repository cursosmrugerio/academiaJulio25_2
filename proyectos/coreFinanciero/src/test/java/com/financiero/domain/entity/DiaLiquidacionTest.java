package com.financiero.domain.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para DiaLiquidacion")
class DiaLiquidacionTest {

    private DiaLiquidacion diaLiquidacion;
    private DiaLiquidacionId id;
    private LocalDate fechaLiquidacion;

    @BeforeEach
    void setUp() {
        fechaLiquidacion = LocalDate.of(2025, 1, 16);
        id = new DiaLiquidacionId("GRP001", "EMP001", "T+1", LocalDate.of(2025, 1, 15));
        diaLiquidacion = new DiaLiquidacion();
        diaLiquidacion.setId(id);
        diaLiquidacion.setFechaLiquidacion(fechaLiquidacion);
    }

    @Test
    @DisplayName("Debe crear DiaLiquidacion con constructor vacío")
    void debeCrearDiaLiquidacionConConstructorVacio() {
        DiaLiquidacion dia = new DiaLiquidacion();
        assertNotNull(dia);
        assertNull(dia.getId());
        assertNull(dia.getFechaLiquidacion());
    }

    @Test
    @DisplayName("Debe crear DiaLiquidacion con constructor completo")
    void debeCrearDiaLiquidacionConConstructorCompleto() {
        DiaLiquidacion dia = new DiaLiquidacion(id, fechaLiquidacion);
        
        assertEquals(id, dia.getId());
        assertEquals(fechaLiquidacion, dia.getFechaLiquidacion());
    }

    @Test
    @DisplayName("Debe manejar getters y setters correctamente")
    void debeManejarGettersYSettersCorrectamente() {
        DiaLiquidacion dia = new DiaLiquidacion();
        DiaLiquidacionId nuevoId = new DiaLiquidacionId("GRP002", "EMP002", "T+2", LocalDate.now());
        LocalDate nuevaFecha = LocalDate.of(2025, 2, 20);
        
        dia.setId(nuevoId);
        dia.setFechaLiquidacion(nuevaFecha);
        
        assertEquals(nuevoId, dia.getId());
        assertEquals(nuevaFecha, dia.getFechaLiquidacion());
    }

    @Test
    @DisplayName("Debe identificar si es tipo T correctamente")
    void debeIdentificarSiEsTipoT() {
        assertTrue(diaLiquidacion.esTipoT());
        
        // Cambiar a T+0
        DiaLiquidacionId idT0 = new DiaLiquidacionId("GRP001", "EMP001", "T+0", LocalDate.of(2025, 1, 15));
        diaLiquidacion.setId(idT0);
        assertTrue(diaLiquidacion.esTipoT());
        
        // Cambiar a T+5
        DiaLiquidacionId idT5 = new DiaLiquidacionId("GRP001", "EMP001", "T+5", LocalDate.of(2025, 1, 15));
        diaLiquidacion.setId(idT5);
        assertTrue(diaLiquidacion.esTipoT());
        
        // Cambiar a otro tipo
        DiaLiquidacionId idOtro = new DiaLiquidacionId("GRP001", "EMP001", "AYER", LocalDate.of(2025, 1, 15));
        diaLiquidacion.setId(idOtro);
        assertFalse(diaLiquidacion.esTipoT());
    }

    @Test
    @DisplayName("Debe identificar si es fecha anterior correctamente")
    void debeIdentificarSiEsFechaAnterior() {
        assertFalse(diaLiquidacion.esFechaAnterior());
        
        DiaLiquidacionId idAyer = new DiaLiquidacionId("GRP001", "EMP001", "AYER", LocalDate.of(2025, 1, 15));
        diaLiquidacion.setId(idAyer);
        assertTrue(diaLiquidacion.esFechaAnterior());
        
        // Cambiar a otro tipo
        DiaLiquidacionId idOtro = new DiaLiquidacionId("GRP001", "EMP001", "MAÑANA", LocalDate.of(2025, 1, 15));
        diaLiquidacion.setId(idOtro);
        assertFalse(diaLiquidacion.esFechaAnterior());
    }

    @Test
    @DisplayName("Debe identificar si es fin de mes correctamente")
    void debeIdentificarSiEsFinDeMes() {
        assertFalse(diaLiquidacion.esFinDeMes());
        
        DiaLiquidacionId idFM = new DiaLiquidacionId("GRP001", "EMP001", "FM", LocalDate.of(2025, 1, 15));
        diaLiquidacion.setId(idFM);
        assertTrue(diaLiquidacion.esFinDeMes());
        
        // Cambiar a FM+1
        DiaLiquidacionId idFM1 = new DiaLiquidacionId("GRP001", "EMP001", "FM+1", LocalDate.of(2025, 1, 15));
        diaLiquidacion.setId(idFM1);
        assertTrue(diaLiquidacion.esFinDeMes());
        
        // Cambiar a otro tipo
        DiaLiquidacionId idOtro = new DiaLiquidacionId("GRP001", "EMP001", "INICIO", LocalDate.of(2025, 1, 15));
        diaLiquidacion.setId(idOtro);
        assertFalse(diaLiquidacion.esFinDeMes());
    }

    @Test
    @DisplayName("Debe implementar equals correctamente")
    void debeImplementarEqualsCorrectamente() {
        DiaLiquidacion dia1 = new DiaLiquidacion(id, fechaLiquidacion);
        DiaLiquidacion dia2 = new DiaLiquidacion(id, LocalDate.of(2025, 2, 20));
        DiaLiquidacionId idDiferente = new DiaLiquidacionId("GRP002", "EMP002", "T+2", LocalDate.of(2025, 1, 15));
        DiaLiquidacion dia3 = new DiaLiquidacion(idDiferente, fechaLiquidacion);

        // Mismo objeto
        assertEquals(dia1, dia1);
        
        // Misma ID, diferentes fechas
        assertEquals(dia1, dia2);
        
        // Diferente ID
        assertNotEquals(dia1, dia3);
        
        // Comparación con null
        assertNotEquals(dia1, null);
        
        // Comparación con objeto de clase diferente
        assertNotEquals(dia1, "string");
    }

    @Test
    @DisplayName("Debe implementar hashCode correctamente")
    void debeImplementarHashCodeCorrectamente() {
        DiaLiquidacion dia1 = new DiaLiquidacion(id, fechaLiquidacion);
        DiaLiquidacion dia2 = new DiaLiquidacion(id, LocalDate.of(2025, 2, 20));
        
        assertEquals(dia1.hashCode(), dia2.hashCode());
    }

    @Test
    @DisplayName("Debe generar toString correctamente")
    void debeGenerarToStringCorrectamente() {
        String toString = diaLiquidacion.toString();
        
        assertNotNull(toString);
        assertTrue(toString.contains("DiaLiquidacion"));
        assertTrue(toString.contains("id="));
        assertTrue(toString.contains("fechaLiquidacion="));
    }

    @Test
    @DisplayName("Debe manejar ID nulo en métodos de dominio")
    void debeManejarIdNuloEnMetodosDeDominio() {
        DiaLiquidacion diaConIdNulo = new DiaLiquidacion();
        
        assertThrows(NullPointerException.class, () -> diaConIdNulo.esTipoT());
        assertThrows(NullPointerException.class, () -> diaConIdNulo.esFechaAnterior());
        assertThrows(NullPointerException.class, () -> diaConIdNulo.esFinDeMes());
    }

    @Test
    @DisplayName("Debe manejar clave liquidación nula en métodos de dominio")
    void debeManejarClaveLiquidacionNulaEnMetodosDeDominio() {
        // Crear un ID con clave liquidación nula mediante constructor por defecto
        DiaLiquidacionId idNulo = new DiaLiquidacionId();
        DiaLiquidacion diaConClaveNula = new DiaLiquidacion();
        diaConClaveNula.setId(idNulo);
        
        assertThrows(NullPointerException.class, () -> diaConClaveNula.esTipoT());
        assertFalse(diaConClaveNula.esFechaAnterior());
        assertThrows(NullPointerException.class, () -> diaConClaveNula.esFinDeMes());
    }
}