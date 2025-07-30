package com.financiero.application.service;

import com.financiero.domain.entity.*;
import com.financiero.domain.enums.MovimientoEstado;
import com.financiero.infrastructure.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas para MovimientoProcesadorService")
class MovimientoProcesadorServiceTest {
    
    @Mock
    private MovimientoRepository movimientoRepository;
    
    @Mock
    private MovimientoDetalleRepository movimientoDetalleRepository;
    
    @Mock
    private CatalogoOperacionRepository catalogoOperacionRepository;
    
    @Mock
    private SaldoRepository saldoRepository;
    
    @Mock
    private PreMovimientoRepository preMovimientoRepository;
    
    @InjectMocks
    private MovimientoProcesadorService movimientoProcesadorService;
    
    private PreMovimiento preMovimiento;
    private CatalogoOperacion catalogoOperacion;
    private Saldo saldo;
    private Movimiento movimiento;
    
    @BeforeEach
    void setUp() {
        // Setup PreMovimiento
        PreMovimientoId preId = new PreMovimientoId("GRP001", "EMP001", 1001L);
        preMovimiento = new PreMovimiento();
        preMovimiento.setId(preId);
        preMovimiento.setIdCuenta(100001L);
        preMovimiento.setClaveDivisa("MXN");
        preMovimiento.setClaveOperacion("DEPOSITO");
        preMovimiento.setImporteNeto(new BigDecimal("1000.00"));
        preMovimiento.setFechaOperacion(LocalDate.now());
        preMovimiento.setFechaLiquidacion(LocalDate.now().plusDays(1));
        preMovimiento.setSituacionPreMovimiento("NP");
        preMovimiento.setClaveUsuario("USER01");
        
        // Setup CatalogoOperacion
        CatalogoOperacionId catId = new CatalogoOperacionId("GRP001", "EMP001", "DEPOSITO");
        catalogoOperacion = new CatalogoOperacion();
        catalogoOperacion.setId(catId);
        catalogoOperacion.setClaveAfectaSaldo("I");
        catalogoOperacion.setEstatus("A");
        catalogoOperacion.setDescripcion("Depósito en efectivo");
        
        // Setup Saldo
        SaldoId saldoId = new SaldoId("GRP001", "EMP001", LocalDate.now().plusDays(1), 100001L, "MXN");
        saldo = new Saldo();
        saldo.setId(saldoId);
        saldo.setSaldoEfectivo(new BigDecimal("5000.00"));
        
        // Setup Movimiento
        MovimientoId movId = new MovimientoId("GRP001", "EMP001", 2001L);
        movimiento = new Movimiento();
        movimiento.setId(movId);
        movimiento.setIdCuenta(100001L);
        movimiento.setClaveDivisa("MXN");
        movimiento.setClaveOperacion("DEPOSITO");
        movimiento.setImporteNeto(new BigDecimal("1000.00"));
        movimiento.setSituacionMovimiento("PV");
    }
    
    @Test
    @DisplayName("Debe procesar pre-movimientos exitosamente")
    void debeProcesarPreMovimientosExitosamente() {
        // Given
        List<PreMovimiento> preMovimientos = Arrays.asList(preMovimiento);
        
        when(preMovimientoRepository.findByIdClaveGrupoEmpresaAndIdClaveEmpresaAndSituacionPreMovimiento(
            "GRP001", "EMP001", "NP")).thenReturn(preMovimientos);
        when(catalogoOperacionRepository.findByEmpresaAndOperacion("GRP001", "EMP001", "DEPOSITO"))
            .thenReturn(Optional.of(catalogoOperacion));
        when(movimientoRepository.findMaxIdMovimiento("GRP001", "EMP001")).thenReturn(2000L);
        when(saldoRepository.findByCuentaFechaDivisa(any(), any(), any(), any(), any()))
            .thenReturn(Optional.of(saldo));
        
        // When
        movimientoProcesadorService.procesarPreMovimientos("GRP001", "EMP001", LocalDate.now());
        
        // Then
        verify(movimientoRepository).save(any(Movimiento.class));
        verify(saldoRepository).save(any(Saldo.class));
        verify(preMovimientoRepository).save(any(PreMovimiento.class));
    }
    
    @Test
    @DisplayName("Debe fallar al procesar pre-movimiento con operación inválida")
    void debeFallarAlProcesarPreMovimientoConOperacionInvalida() {
        // Given
        List<PreMovimiento> preMovimientos = Arrays.asList(preMovimiento);
        
        when(preMovimientoRepository.findByIdClaveGrupoEmpresaAndIdClaveEmpresaAndSituacionPreMovimiento(
            "GRP001", "EMP001", "NP")).thenReturn(preMovimientos);
        when(catalogoOperacionRepository.findByEmpresaAndOperacion("GRP001", "EMP001", "DEPOSITO"))
            .thenReturn(Optional.empty());
        
        // When & Then
        assertDoesNotThrow(() -> {
            movimientoProcesadorService.procesarPreMovimientos("GRP001", "EMP001", LocalDate.now());
        });
        
        verify(movimientoRepository, never()).save(any(Movimiento.class));
    }
    
    @Test
    @DisplayName("Debe procesar movimientos virtuales a reales exitosamente")
    void debeProcesarMovimientosVirtualesARealesExitosamente() {
        // Given
        List<Movimiento> movimientosVirtuales = Arrays.asList(movimiento);
        
        when(movimientoRepository.findByEmpresaAndSituacion("GRP001", "EMP001", "PV"))
            .thenReturn(movimientosVirtuales);
        
        // When
        movimientoProcesadorService.procesarMovimientosVirtualesAReales("GRP001", "EMP001", LocalDate.now());
        
        // Then
        verify(movimientoRepository).save(argThat(m -> "PR".equals(m.getSituacionMovimiento())));
    }
    
    @Test
    @DisplayName("Debe cancelar movimiento exitosamente")
    void debeCancelarMovimientoExitosamente() {
        // Given
        MovimientoId movId = new MovimientoId("GRP001", "EMP001", 2001L);
        movimiento.setSituacionMovimiento("PV");
        
        when(movimientoRepository.findById(movId)).thenReturn(Optional.of(movimiento));
        when(catalogoOperacionRepository.findByEmpresaAndOperacion("GRP001", "EMP001", "DEPOSITO"))
            .thenReturn(Optional.of(catalogoOperacion));
        when(saldoRepository.findByCuentaFechaDivisa(any(), any(), any(), any(), any()))
            .thenReturn(Optional.of(saldo));
        
        // When
        movimientoProcesadorService.cancelarMovimiento("GRP001", "EMP001", 2001L, "ADMIN01");
        
        // Then
        verify(movimientoRepository).save(argThat(m -> 
            "CA".equals(m.getSituacionMovimiento()) && "ADMIN01".equals(m.getClaveUsuarioCancela())));
        verify(saldoRepository).save(any(Saldo.class));
    }
    
    @Test
    @DisplayName("Debe fallar al cancelar movimiento inexistente")
    void debeFallarAlCancelarMovimientoInexistente() {
        // Given
        MovimientoId movId = new MovimientoId("GRP001", "EMP001", 9999L);
        
        when(movimientoRepository.findById(movId)).thenReturn(Optional.empty());
        
        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            movimientoProcesadorService.cancelarMovimiento("GRP001", "EMP001", 9999L, "ADMIN01");
        });
        
        assertEquals("Movimiento no encontrado: 9999", exception.getMessage());
    }
    
    @Test
    @DisplayName("Debe fallar al cancelar movimiento en estado final")
    void debeFallarAlCancelarMovimientoEnEstadoFinal() {
        // Given
        MovimientoId movId = new MovimientoId("GRP001", "EMP001", 2001L);
        movimiento.setSituacionMovimiento("PR");
        
        when(movimientoRepository.findById(movId)).thenReturn(Optional.of(movimiento));
        
        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            movimientoProcesadorService.cancelarMovimiento("GRP001", "EMP001", 2001L, "ADMIN01");
        });
        
        assertTrue(exception.getMessage().contains("Movimiento no puede ser cancelado"));
    }
    
    @Test
    @DisplayName("Debe crear saldo nuevo cuando no existe")
    void debeCrearSaldoNuevoCoandoNoExiste() {
        // Given
        List<PreMovimiento> preMovimientos = Arrays.asList(preMovimiento);
        
        when(preMovimientoRepository.findByIdClaveGrupoEmpresaAndIdClaveEmpresaAndSituacionPreMovimiento(
            "GRP001", "EMP001", "NP")).thenReturn(preMovimientos);
        when(catalogoOperacionRepository.findByEmpresaAndOperacion("GRP001", "EMP001", "DEPOSITO"))
            .thenReturn(Optional.of(catalogoOperacion));
        when(movimientoRepository.findMaxIdMovimiento("GRP001", "EMP001")).thenReturn(2000L);
        when(saldoRepository.findByCuentaFechaDivisa(any(), any(), any(), any(), any()))
            .thenReturn(Optional.empty());
        
        // When
        movimientoProcesadorService.procesarPreMovimientos("GRP001", "EMP001", LocalDate.now());
        
        // Then
        verify(saldoRepository).save(argThat(s -> 
            s.getSaldoEfectivo().equals(new BigDecimal("1000.00"))));
    }
    
    @Test
    @DisplayName("Debe manejar operación que no afecta saldos")
    void debeManejarOperacionQueNoAfectaSaldos() {
        // Given
        catalogoOperacion.setClaveAfectaSaldo("N");
        List<PreMovimiento> preMovimientos = Arrays.asList(preMovimiento);
        
        when(preMovimientoRepository.findByIdClaveGrupoEmpresaAndIdClaveEmpresaAndSituacionPreMovimiento(
            "GRP001", "EMP001", "NP")).thenReturn(preMovimientos);
        when(catalogoOperacionRepository.findByEmpresaAndOperacion("GRP001", "EMP001", "DEPOSITO"))
            .thenReturn(Optional.of(catalogoOperacion));
        when(movimientoRepository.findMaxIdMovimiento("GRP001", "EMP001")).thenReturn(2000L);
        
        // When
        movimientoProcesadorService.procesarPreMovimientos("GRP001", "EMP001", LocalDate.now());
        
        // Then
        verify(movimientoRepository).save(any(Movimiento.class));
        verify(saldoRepository, never()).save(any(Saldo.class));
    }
    
    @Test
    @DisplayName("Debe procesar lista vacía sin errores")
    void debeProcesarListaVaciaSinErrores() {
        // Given
        when(preMovimientoRepository.findByIdClaveGrupoEmpresaAndIdClaveEmpresaAndSituacionPreMovimiento(
            "GRP001", "EMP001", "NP")).thenReturn(Collections.emptyList());
        
        // When & Then
        assertDoesNotThrow(() -> {
            movimientoProcesadorService.procesarPreMovimientos("GRP001", "EMP001", LocalDate.now());
        });
        
        verify(movimientoRepository, never()).save(any(Movimiento.class));
    }
    
    @Test
    @DisplayName("Debe calcular siguiente ID de movimiento correctamente")
    void debeCalcularSiguienteIdMovimientoCorrectamente() {
        // Given
        List<PreMovimiento> preMovimientos = Arrays.asList(preMovimiento);
        
        when(preMovimientoRepository.findByIdClaveGrupoEmpresaAndIdClaveEmpresaAndSituacionPreMovimiento(
            "GRP001", "EMP001", "NP")).thenReturn(preMovimientos);
        when(catalogoOperacionRepository.findByEmpresaAndOperacion("GRP001", "EMP001", "DEPOSITO"))
            .thenReturn(Optional.of(catalogoOperacion));
        when(movimientoRepository.findMaxIdMovimiento("GRP001", "EMP001")).thenReturn(1500L);
        when(saldoRepository.findByCuentaFechaDivisa(any(), any(), any(), any(), any()))
            .thenReturn(Optional.of(saldo));
        
        // When
        movimientoProcesadorService.procesarPreMovimientos("GRP001", "EMP001", LocalDate.now());
        
        // Then
        verify(movimientoRepository).save(argThat(m -> 
            m.getId().getIdMovimiento().equals(1501L)));
    }
}