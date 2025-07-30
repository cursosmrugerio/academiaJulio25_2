package com.financiero.domain.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@DisplayName("Pruebas para entidad Saldo")
class SaldoTest {
    
    private Saldo saldo;
    private SaldoId saldoId;
    
    @BeforeEach
    void setUp() {
        saldoId = new SaldoId("GRP001", "EMP001", LocalDate.now(), 100001L, "MXN");
        saldo = new Saldo(saldoId, new BigDecimal("1000.00"));
    }
    
    @Test
    @DisplayName("Debe crear saldo con constructor completo")
    void debeCrearSaldoConConstructorCompleto() {
        Saldo nuevoSaldo = new Saldo("GRP001", "EMP001", LocalDate.now(), 100001L, "MXN", new BigDecimal("500.00"));
        
        assertNotNull(nuevoSaldo.getId());
        assertEquals(new BigDecimal("500.00"), nuevoSaldo.getSaldoEfectivo());
    }
    
    @Test
    @DisplayName("Debe incrementar saldo correctamente")
    void debeIncrementarSaldoCorrectamente() {
        BigDecimal importeInicial = saldo.getSaldoEfectivo();
        BigDecimal incremento = new BigDecimal("250.00");
        
        saldo.incrementarSaldo(incremento);
        
        assertEquals(importeInicial.add(incremento), saldo.getSaldoEfectivo());
    }
    
    @Test
    @DisplayName("Debe decrementar saldo correctamente")
    void debeDecrementarSaldoCorrectamente() {
        BigDecimal importeInicial = saldo.getSaldoEfectivo();
        BigDecimal decremento = new BigDecimal("300.00");
        
        saldo.decrementarSaldo(decremento);
        
        assertEquals(importeInicial.subtract(decremento), saldo.getSaldoEfectivo());
    }
    
    @Test
    @DisplayName("Debe afectar saldo con factor positivo")
    void debeAfectarSaldoConFactorPositivo() {
        BigDecimal importeInicial = saldo.getSaldoEfectivo();
        BigDecimal importe = new BigDecimal("200.00");
        int factor = 1;
        
        saldo.afectarSaldo(importe, factor);
        
        assertEquals(importeInicial.add(importe), saldo.getSaldoEfectivo());
    }
    
    @Test
    @DisplayName("Debe afectar saldo con factor negativo")
    void debeAfectarSaldoConFactorNegativo() {
        BigDecimal importeInicial = saldo.getSaldoEfectivo();
        BigDecimal importe = new BigDecimal("150.00");
        int factor = -1;
        
        saldo.afectarSaldo(importe, factor);
        
        assertEquals(importeInicial.subtract(importe), saldo.getSaldoEfectivo());
    }
    
    @Test
    @DisplayName("No debe afectar saldo con factor cero")
    void noDebeAfectarSaldoConFactorCero() {
        BigDecimal importeInicial = saldo.getSaldoEfectivo();
        BigDecimal importe = new BigDecimal("100.00");
        int factor = 0;
        
        saldo.afectarSaldo(importe, factor);
        
        assertEquals(importeInicial, saldo.getSaldoEfectivo());
    }
    
    @Test
    @DisplayName("No debe afectar saldo con importe null")
    void noDebeAfectarSaldoConImporteNull() {
        BigDecimal importeInicial = saldo.getSaldoEfectivo();
        
        saldo.incrementarSaldo(null);
        saldo.decrementarSaldo(null);
        saldo.afectarSaldo(null, 1);
        
        assertEquals(importeInicial, saldo.getSaldoEfectivo());
    }
    
    @Test
    @DisplayName("Debe identificar saldo positivo correctamente")
    void debeIdentificarSaldoPositivo() {
        saldo.setSaldoEfectivo(new BigDecimal("100.00"));
        assertTrue(saldo.esSaldoPositivo());
        assertFalse(saldo.esSaldoNegativo());
        assertFalse(saldo.esSaldoCero());
    }
    
    @Test
    @DisplayName("Debe identificar saldo negativo correctamente")
    void debeIdentificarSaldoNegativo() {
        saldo.setSaldoEfectivo(new BigDecimal("-50.00"));
        assertTrue(saldo.esSaldoNegativo());
        assertFalse(saldo.esSaldoPositivo());
        assertFalse(saldo.esSaldoCero());
    }
    
    @Test
    @DisplayName("Debe identificar saldo cero correctamente")
    void debeIdentificarSaldoCero() {
        saldo.setSaldoEfectivo(BigDecimal.ZERO);
        assertTrue(saldo.esSaldoCero());
        assertFalse(saldo.esSaldoPositivo());
        assertFalse(saldo.esSaldoNegativo());
    }
    
    @Test
    @DisplayName("Debe validar saldo suficiente correctamente")
    void debeValidarSaldoSuficiente() {
        saldo.setSaldoEfectivo(new BigDecimal("1000.00"));
        
        assertTrue(saldo.tieneSaldoSuficiente(new BigDecimal("500.00")));
        assertTrue(saldo.tieneSaldoSuficiente(new BigDecimal("1000.00")));
        assertFalse(saldo.tieneSaldoSuficiente(new BigDecimal("1500.00")));
        assertTrue(saldo.tieneSaldoSuficiente(null));
    }
    
    @Test
    @DisplayName("Debe obtener saldo absoluto correctamente")
    void debeObtenerSaldoAbsoluto() {
        saldo.setSaldoEfectivo(new BigDecimal("-250.00"));
        assertEquals(new BigDecimal("250.00"), saldo.getSaldoAbsoluto());
        
        saldo.setSaldoEfectivo(new BigDecimal("300.00"));
        assertEquals(new BigDecimal("300.00"), saldo.getSaldoAbsoluto());
    }
    
    @Test
    @DisplayName("Debe comparar saldos por ID correctamente")
    void debeCompararSaldosPorId() {
        SaldoId otroId = new SaldoId("GRP001", "EMP001", LocalDate.now(), 100001L, "MXN");
        Saldo otroSaldo = new Saldo(otroId, new BigDecimal("2000.00"));
        
        assertEquals(saldo, otroSaldo);
        assertEquals(saldo.hashCode(), otroSaldo.hashCode());
    }
    
    @Test
    @DisplayName("Debe generar toString correctamente")
    void debeGenerarToStringCorrectamente() {
        String toString = saldo.toString();
        
        assertTrue(toString.contains("Saldo{"));
        assertTrue(toString.contains("id="));
        assertTrue(toString.contains("saldoEfectivo="));
    }
}