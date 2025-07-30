package com.financiero.domain.enums;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas para MovimientoEstado enum")
class MovimientoEstadoTest {
    
    @Test
    @DisplayName("Debe obtener enum por código correctamente")
    void debeObtenerEnumPorCodigo() {
        assertEquals(MovimientoEstado.NO_PROCESADO, MovimientoEstado.fromCodigo("NP"));
        assertEquals(MovimientoEstado.PROCESADO_VIRTUAL, MovimientoEstado.fromCodigo("PV"));
        assertEquals(MovimientoEstado.PROCESADO_REAL, MovimientoEstado.fromCodigo("PR"));
        assertEquals(MovimientoEstado.CANCELADO, MovimientoEstado.fromCodigo("CA"));
    }
    
    @Test
    @DisplayName("Debe retornar null para código null")
    void debeRetornarNullParaCodigoNull() {
        assertNull(MovimientoEstado.fromCodigo(null));
    }
    
    @Test
    @DisplayName("Debe lanzar excepción para código inválido")
    void debeLanzarExcepcionParaCodigoInvalido() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> MovimientoEstado.fromCodigo("XX")
        );
        assertEquals("Código de estado no válido: XX", exception.getMessage());
    }
    
    @Test
    @DisplayName("Debe validar transiciones correctas desde NO_PROCESADO")
    void debeValidarTransicionesDesdeNoProcesado() {
        MovimientoEstado noProcesado = MovimientoEstado.NO_PROCESADO;
        
        assertTrue(noProcesado.puedeTransicionarA(MovimientoEstado.PROCESADO_VIRTUAL));
        assertTrue(noProcesado.puedeTransicionarA(MovimientoEstado.PROCESADO_REAL));
        assertFalse(noProcesado.puedeTransicionarA(MovimientoEstado.CANCELADO));
        assertFalse(noProcesado.puedeTransicionarA(MovimientoEstado.NO_PROCESADO));
    }
    
    @Test
    @DisplayName("Debe validar transiciones correctas desde PROCESADO_VIRTUAL")
    void debeValidarTransicionesDesdeProcesadoVirtual() {
        MovimientoEstado procesadoVirtual = MovimientoEstado.PROCESADO_VIRTUAL;
        
        assertTrue(procesadoVirtual.puedeTransicionarA(MovimientoEstado.PROCESADO_REAL));
        assertTrue(procesadoVirtual.puedeTransicionarA(MovimientoEstado.CANCELADO));
        assertFalse(procesadoVirtual.puedeTransicionarA(MovimientoEstado.NO_PROCESADO));
        assertFalse(procesadoVirtual.puedeTransicionarA(MovimientoEstado.PROCESADO_VIRTUAL));
    }
    
    @Test
    @DisplayName("Debe validar que estados finales no pueden transicionar")
    void debeValidarEstadosFinales() {
        MovimientoEstado procesadoReal = MovimientoEstado.PROCESADO_REAL;
        MovimientoEstado cancelado = MovimientoEstado.CANCELADO;
        
        assertFalse(procesadoReal.puedeTransicionarA(MovimientoEstado.NO_PROCESADO));
        assertFalse(procesadoReal.puedeTransicionarA(MovimientoEstado.PROCESADO_VIRTUAL));
        assertFalse(procesadoReal.puedeTransicionarA(MovimientoEstado.CANCELADO));
        
        assertFalse(cancelado.puedeTransicionarA(MovimientoEstado.NO_PROCESADO));
        assertFalse(cancelado.puedeTransicionarA(MovimientoEstado.PROCESADO_VIRTUAL));
        assertFalse(cancelado.puedeTransicionarA(MovimientoEstado.PROCESADO_REAL));
    }
    
    @Test
    @DisplayName("Debe retornar false para transición a null")
    void debeRetornarFalseParaTransicionANull() {
        assertFalse(MovimientoEstado.NO_PROCESADO.puedeTransicionarA(null));
        assertFalse(MovimientoEstado.PROCESADO_VIRTUAL.puedeTransicionarA(null));
        assertFalse(MovimientoEstado.PROCESADO_REAL.puedeTransicionarA(null));
        assertFalse(MovimientoEstado.CANCELADO.puedeTransicionarA(null));
    }
    
    @Test
    @DisplayName("Debe identificar correctamente estados finales")
    void debeIdentificarEstadosFinales() {
        assertFalse(MovimientoEstado.NO_PROCESADO.esFinal());
        assertFalse(MovimientoEstado.PROCESADO_VIRTUAL.esFinal());
        assertTrue(MovimientoEstado.PROCESADO_REAL.esFinal());
        assertTrue(MovimientoEstado.CANCELADO.esFinal());
    }
    
    @Test
    @DisplayName("Debe identificar correctamente estados que permiten modificaciones")
    void debeIdentificarEstadosQuePermitenModificaciones() {
        assertTrue(MovimientoEstado.NO_PROCESADO.permiteModificaciones());
        assertTrue(MovimientoEstado.PROCESADO_VIRTUAL.permiteModificaciones());
        assertFalse(MovimientoEstado.PROCESADO_REAL.permiteModificaciones());
        assertFalse(MovimientoEstado.CANCELADO.permiteModificaciones());
    }
    
    @Test
    @DisplayName("Debe identificar correctamente estados que han afectado saldos")
    void debeIdentificarEstadosQueHanAfectadoSaldos() {
        assertFalse(MovimientoEstado.NO_PROCESADO.haAfectadoSaldos());
        assertFalse(MovimientoEstado.PROCESADO_VIRTUAL.haAfectadoSaldos());
        assertTrue(MovimientoEstado.PROCESADO_REAL.haAfectadoSaldos());
        assertTrue(MovimientoEstado.CANCELADO.haAfectadoSaldos());
    }
    
    @Test
    @DisplayName("Debe retornar toString correcto")
    void debeRetornarToStringCorrecto() {
        assertEquals("No Procesado (NP)", MovimientoEstado.NO_PROCESADO.toString());
        assertEquals("Procesado Virtual (PV)", MovimientoEstado.PROCESADO_VIRTUAL.toString());
        assertEquals("Procesado Real (PR)", MovimientoEstado.PROCESADO_REAL.toString());
        assertEquals("Cancelado (CA)", MovimientoEstado.CANCELADO.toString());
    }
}