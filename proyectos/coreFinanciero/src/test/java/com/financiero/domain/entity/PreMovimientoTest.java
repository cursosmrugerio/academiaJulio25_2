package com.financiero.domain.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para PreMovimiento")
class PreMovimientoTest {

    private PreMovimiento preMovimiento;
    private PreMovimientoId id;

    @BeforeEach
    void setUp() {
        id = new PreMovimientoId("GRP001", "EMP001", 1L);
        preMovimiento = new PreMovimiento();
        preMovimiento.setId(id);
        preMovimiento.setFechaOperacion(LocalDate.of(2025, 1, 15));
        preMovimiento.setFechaLiquidacion(LocalDate.of(2025, 1, 16));
        preMovimiento.setImporteNeto(new BigDecimal("1000.00"));
        preMovimiento.setClaveMercado("CAPITAL");
        preMovimiento.setSituacionPreMovimiento("NP");
    }

    @Test
    @DisplayName("Debe crear PreMovimiento con constructor vacío")
    void debeCrearPreMovimientoConConstructorVacio() {
        PreMovimiento movimiento = new PreMovimiento();
        assertNotNull(movimiento);
        assertNull(movimiento.getId());
        assertEquals(BigDecimal.ZERO, movimiento.getPrecioOperacion());
        assertEquals(BigDecimal.ZERO, movimiento.getTipoCambio());
        assertEquals(0L, movimiento.getIdMovimiento());
        assertEquals("NP", movimiento.getSituacionPreMovimiento());
        assertNotNull(movimiento.getDetalles());
        assertTrue(movimiento.getDetalles().isEmpty());
    }

    @Test
    @DisplayName("Debe crear PreMovimiento con constructor con ID")
    void debeCrearPreMovimientoConConstructorConId() {
        PreMovimiento movimiento = new PreMovimiento(id);
        
        assertEquals(id, movimiento.getId());
        assertEquals(BigDecimal.ZERO, movimiento.getPrecioOperacion());
        assertEquals(BigDecimal.ZERO, movimiento.getTipoCambio());
        assertEquals(0L, movimiento.getIdMovimiento());
        assertEquals("NP", movimiento.getSituacionPreMovimiento());
    }

    @Test
    @DisplayName("Debe manejar getters y setters correctamente")
    void debeManejarGettersYSettersCorrectamente() {
        PreMovimiento movimiento = new PreMovimiento();
        PreMovimientoId nuevoId = new PreMovimientoId("GRP002", "EMP002", 2L);
        LocalDate fechaOperacion = LocalDate.of(2025, 2, 1);
        LocalDate fechaLiquidacion = LocalDate.of(2025, 2, 2);
        LocalDate fechaAplicacion = LocalDate.of(2025, 2, 3);
        BigDecimal importe = new BigDecimal("2000.50");
        
        movimiento.setId(nuevoId);
        movimiento.setFechaOperacion(fechaOperacion);
        movimiento.setFechaLiquidacion(fechaLiquidacion);
        movimiento.setFechaAplicacion(fechaAplicacion);
        movimiento.setIdCuenta(100001L);
        movimiento.setIdPrestamo(200001L);
        movimiento.setClaveDivisa("USD");
        movimiento.setClaveOperacion("DEPOSITO");
        movimiento.setImporteNeto(importe);
        movimiento.setPrecioOperacion(new BigDecimal("1.5"));
        movimiento.setTipoCambio(new BigDecimal("20.50"));
        movimiento.setClaveMedio("EFECTIVO");
        movimiento.setClaveMercado("CAPITAL");
        movimiento.setNota("Nota de prueba");
        movimiento.setIdGrupo(300001L);
        movimiento.setIdMovimiento(400001L);
        movimiento.setClaveUsuario("user123");
        movimiento.setSituacionPreMovimiento("PR");
        movimiento.setNumeroPagoAmortizacion(1);
        
        assertEquals(nuevoId, movimiento.getId());
        assertEquals(fechaOperacion, movimiento.getFechaOperacion());
        assertEquals(fechaLiquidacion, movimiento.getFechaLiquidacion());
        assertEquals(fechaAplicacion, movimiento.getFechaAplicacion());
        assertEquals(100001L, movimiento.getIdCuenta());
        assertEquals(200001L, movimiento.getIdPrestamo());
        assertEquals("USD", movimiento.getClaveDivisa());
        assertEquals("DEPOSITO", movimiento.getClaveOperacion());
        assertEquals(importe, movimiento.getImporteNeto());
        assertEquals(new BigDecimal("1.5"), movimiento.getPrecioOperacion());
        assertEquals(new BigDecimal("20.50"), movimiento.getTipoCambio());
        assertEquals("EFECTIVO", movimiento.getClaveMedio());
        assertEquals("CAPITAL", movimiento.getClaveMercado());
        assertEquals("Nota de prueba", movimiento.getNota());
        assertEquals(300001L, movimiento.getIdGrupo());
        assertEquals(400001L, movimiento.getIdMovimiento());
        assertEquals("user123", movimiento.getClaveUsuario());
        assertEquals("PR", movimiento.getSituacionPreMovimiento());
        assertEquals(1, movimiento.getNumeroPagoAmortizacion());
    }

    @Test
    @DisplayName("Debe identificar si es préstamo correctamente")
    void debeIdentificarSiEsPrestamo() {
        assertFalse(preMovimiento.esPrestamo());
        
        preMovimiento.setClaveMercado("PRESTAMO");
        assertTrue(preMovimiento.esPrestamo());
        
        preMovimiento.setClaveMercado("CAPITAL");
        assertFalse(preMovimiento.esPrestamo());
    }

    @Test
    @DisplayName("Debe identificar si está pendiente correctamente")
    void debeIdentificarSiEstaPendiente() {
        assertTrue(preMovimiento.estaPendiente());
        
        preMovimiento.setSituacionPreMovimiento("PR");
        assertFalse(preMovimiento.estaPendiente());
        
        preMovimiento.setSituacionPreMovimiento("CA");
        assertFalse(preMovimiento.estaPendiente());
    }

    @Test
    @DisplayName("Debe marcar como procesado correctamente")
    void debeMarcarComoProcesadoCorrectamente() {
        assertTrue(preMovimiento.estaPendiente());
        
        preMovimiento.marcarComoProcesado();
        
        assertEquals("PR", preMovimiento.getSituacionPreMovimiento());
        assertFalse(preMovimiento.estaPendiente());
    }

    @Test
    @DisplayName("Debe agregar detalle correctamente")
    void debeAgregarDetalleCorrectamente() {
        PreMovimientoDetalleId detalleId = new PreMovimientoDetalleId("GRP001", "EMP001", 1L, "INTERES");
        PreMovimientoDetalle detalle = new PreMovimientoDetalle(detalleId, new BigDecimal("50.00"), "Interés");
        
        assertTrue(preMovimiento.getDetalles().isEmpty());
        
        preMovimiento.agregarDetalle(detalle);
        
        assertEquals(1, preMovimiento.getDetalles().size());
        assertTrue(preMovimiento.getDetalles().contains(detalle));
    }

    @Test
    @DisplayName("Debe remover detalle correctamente")
    void debeRemoverDetalleCorrectamente() {
        PreMovimientoDetalleId detalleId = new PreMovimientoDetalleId("GRP001", "EMP001", 1L, "INTERES");
        PreMovimientoDetalle detalle = new PreMovimientoDetalle(detalleId, new BigDecimal("50.00"), "Interés");
        
        preMovimiento.agregarDetalle(detalle);
        assertEquals(1, preMovimiento.getDetalles().size());
        
        preMovimiento.removerDetalle(detalle);
        
        assertTrue(preMovimiento.getDetalles().isEmpty());
    }

    @Test
    @DisplayName("Debe manejar lista de detalles correctamente")
    void debeManejarListaDeDetallesCorrectamente() {
        List<PreMovimientoDetalle> nuevosDetalles = new ArrayList<>();
        PreMovimientoDetalleId detalleId1 = new PreMovimientoDetalleId("GRP001", "EMP001", 1L, "INTERES");
        PreMovimientoDetalleId detalleId2 = new PreMovimientoDetalleId("GRP001", "EMP001", 1L, "COMISION");
        
        nuevosDetalles.add(new PreMovimientoDetalle(detalleId1, new BigDecimal("50.00"), "Interés"));
        nuevosDetalles.add(new PreMovimientoDetalle(detalleId2, new BigDecimal("25.00"), "Comisión"));
        
        preMovimiento.setDetalles(nuevosDetalles);
        
        assertEquals(2, preMovimiento.getDetalles().size());
        assertEquals(nuevosDetalles, preMovimiento.getDetalles());
    }

    @Test
    @DisplayName("Debe implementar equals correctamente")
    void debeImplementarEqualsCorrectamente() {
        PreMovimiento movimiento1 = new PreMovimiento(id);
        movimiento1.setImporteNeto(new BigDecimal("1000.00"));
        
        PreMovimiento movimiento2 = new PreMovimiento(id);
        movimiento2.setImporteNeto(new BigDecimal("2000.00"));
        
        PreMovimientoId idDiferente = new PreMovimientoId("GRP002", "EMP002", 2L);
        PreMovimiento movimiento3 = new PreMovimiento(idDiferente);

        // Mismo objeto
        assertEquals(movimiento1, movimiento1);
        
        // Misma ID, diferentes datos
        assertEquals(movimiento1, movimiento2);
        
        // Diferente ID
        assertNotEquals(movimiento1, movimiento3);
        
        // Comparación con null
        assertNotEquals(movimiento1, null);
        
        // Comparación con objeto de clase diferente
        assertNotEquals(movimiento1, "string");
    }

    @Test
    @DisplayName("Debe implementar hashCode correctamente")
    void debeImplementarHashCodeCorrectamente() {
        PreMovimiento movimiento1 = new PreMovimiento(id);
        PreMovimiento movimiento2 = new PreMovimiento(id);
        
        assertEquals(movimiento1.hashCode(), movimiento2.hashCode());
    }

    @Test
    @DisplayName("Debe generar toString correctamente")
    void debeGenerarToStringCorrectamente() {
        String toString = preMovimiento.toString();
        
        assertNotNull(toString);
        assertTrue(toString.contains("PreMovimiento"));
        assertTrue(toString.contains("id="));
        assertTrue(toString.contains("fechaOperacion="));
        assertTrue(toString.contains("fechaLiquidacion="));
        assertTrue(toString.contains("importeNeto="));
        assertTrue(toString.contains("situacionPreMovimiento="));
    }

    @Test
    @DisplayName("Debe manejar clave mercado nula en métodos de dominio")
    void debeManejarClaveMercadoNulaEnMetodosDeDominio() {
        preMovimiento.setClaveMercado(null);
        assertFalse(preMovimiento.esPrestamo());
    }

    @Test
    @DisplayName("Debe manejar situación nula en métodos de dominio")
    void debeManejarSituacionNulaEnMetodosDeDominio() {
        preMovimiento.setSituacionPreMovimiento(null);
        assertFalse(preMovimiento.estaPendiente());
    }
}