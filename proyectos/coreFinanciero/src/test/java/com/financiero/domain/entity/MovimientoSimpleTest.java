package com.financiero.domain.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@DisplayName("Pruebas básicas para entidad Movimiento")
class MovimientoSimpleTest {
    
    private Movimiento movimiento;
    private MovimientoId movimientoId;
    
    @BeforeEach
    void setUp() {
        movimientoId = new MovimientoId("GRP001", "EMP001", 1001L);
        movimiento = new Movimiento();
        movimiento.setId(movimientoId);
        movimiento.setIdCuenta(100001L);
        movimiento.setClaveDivisa("MXN");
        movimiento.setImporteNeto(new BigDecimal("1000.00"));
        movimiento.setSituacionMovimiento("NP");
    }
    
    @Test
    @DisplayName("Debe crear movimiento con constructor vacío")
    void debeCrearMovimientoConConstructorVacio() {
        Movimiento nuevoMovimiento = new Movimiento();
        assertNotNull(nuevoMovimiento);
        assertNull(nuevoMovimiento.getId());
    }
    
    @Test
    @DisplayName("Debe establecer y obtener propiedades básicas correctamente")
    void debeEstablecerYObtenerPropiedadesBasicas() {
        assertEquals(movimientoId, movimiento.getId());
        assertEquals(100001L, movimiento.getIdCuenta());
        assertEquals("MXN", movimiento.getClaveDivisa());
        assertEquals(new BigDecimal("1000.00"), movimiento.getImporteNeto());
        assertEquals("NP", movimiento.getSituacionMovimiento());
    }
    
    @Test
    @DisplayName("Debe establecer fechas correctamente")
    void debeEstablecerFechasCorrectamente() {
        LocalDate fechaOperacion = LocalDate.now();
        LocalDate fechaLiquidacion = LocalDate.now().plusDays(1);
        LocalDate fechaAplicacion = LocalDate.now().plusDays(2);
        
        movimiento.setFechaOperacion(fechaOperacion);
        movimiento.setFechaLiquidacion(fechaLiquidacion);
        movimiento.setFechaAplicacion(fechaAplicacion);
        
        assertEquals(fechaOperacion, movimiento.getFechaOperacion());
        assertEquals(fechaLiquidacion, movimiento.getFechaLiquidacion());
        assertEquals(fechaAplicacion, movimiento.getFechaAplicacion());
    }
    
    @Test
    @DisplayName("Debe manejar importes con decimales")
    void debeManejarImportesConDecimales() {
        BigDecimal importe = new BigDecimal("1234.56");
        movimiento.setImporteNeto(importe);
        
        assertEquals(importe, movimiento.getImporteNeto());
    }
    
    @Test
    @DisplayName("Debe comparar movimientos por ID correctamente")
    void debeCompararMovimientosPorId() {
        MovimientoId otroId = new MovimientoId("GRP001", "EMP001", 1001L);
        Movimiento otroMovimiento = new Movimiento();
        otroMovimiento.setId(otroId);
        
        assertEquals(movimiento, otroMovimiento);
        assertEquals(movimiento.hashCode(), otroMovimiento.hashCode());
    }
    
    @Test
    @DisplayName("Debe generar toString correctamente")
    void debeGenerarToStringCorrectamente() {
        String toString = movimiento.toString();
        
        assertTrue(toString.contains("Movimiento{"));
        assertTrue(toString.contains("id="));
    }
}