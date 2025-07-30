package com.financiero.infrastructure.repository;

import com.financiero.domain.entity.Movimiento;
import com.financiero.domain.entity.MovimientoId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Pruebas para MovimientoRepository")
class MovimientoRepositoryTest {
    
    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    private MovimientoRepository movimientoRepository;
    
    private Movimiento movimiento1;
    private Movimiento movimiento2;
    private Movimiento movimiento3;
    
    @BeforeEach
    void setUp() {
        // Crear movimientos de prueba
        MovimientoId id1 = new MovimientoId("GRP001", "EMP001", 1001L);
        movimiento1 = new Movimiento();
        movimiento1.setId(id1);
        movimiento1.setIdCuenta(100001L);
        movimiento1.setClaveDivisa("MXN");
        movimiento1.setFechaOperacion(LocalDate.now());
        movimiento1.setFechaLiquidacion(LocalDate.now().plusDays(1));
        movimiento1.setClaveOperacion("DEPOSITO");
        movimiento1.setImporteNeto(new BigDecimal("1000.00"));
        movimiento1.setSituacionMovimiento("PV");
        movimiento1.setIdPreMovimiento(10001L);
        movimiento1.setFechaHoraRegistro(LocalDateTime.now());
        
        MovimientoId id2 = new MovimientoId("GRP001", "EMP001", 1002L);
        movimiento2 = new Movimiento();
        movimiento2.setId(id2);
        movimiento2.setIdCuenta(100002L);
        movimiento2.setClaveDivisa("USD");
        movimiento2.setFechaOperacion(LocalDate.now());
        movimiento2.setFechaLiquidacion(LocalDate.now().plusDays(2));
        movimiento2.setClaveOperacion("RETIRO");
        movimiento2.setImporteNeto(new BigDecimal("500.00"));
        movimiento2.setSituacionMovimiento("PR");
        movimiento2.setIdPrestamo(2001L);
        movimiento2.setFechaHoraRegistro(LocalDateTime.now());
        
        MovimientoId id3 = new MovimientoId("GRP001", "EMP002", 2001L);
        movimiento3 = new Movimiento();
        movimiento3.setId(id3);
        movimiento3.setIdCuenta(200001L);
        movimiento3.setClaveDivisa("MXN");
        movimiento3.setFechaOperacion(LocalDate.now());
        movimiento3.setClaveOperacion("CARGO");
        movimiento3.setImporteNeto(new BigDecimal("750.00"));
        movimiento3.setSituacionMovimiento("NP");
        movimiento3.setFechaHoraRegistro(LocalDateTime.now());
        
        entityManager.persistAndFlush(movimiento1);
        entityManager.persistAndFlush(movimiento2);
        entityManager.persistAndFlush(movimiento3);
    }
    
    @Test
    @DisplayName("Debe encontrar movimientos por empresa y situación")
    void debeEncontrarMovimientosPorEmpresaYSituacion() {
        // When
        List<Movimiento> movimientosPV = movimientoRepository
            .findByEmpresaAndSituacion("GRP001", "EMP001", "PV");
        List<Movimiento> movimientosPR = movimientoRepository
            .findByEmpresaAndSituacion("GRP001", "EMP001", "PR");
        
        // Then
        assertEquals(1, movimientosPV.size());
        assertEquals(1001L, movimientosPV.get(0).getId().getIdMovimiento());
        
        assertEquals(1, movimientosPR.size());
        assertEquals(1002L, movimientosPR.get(0).getId().getIdMovimiento());
    }
    
    @Test
    @DisplayName("Debe encontrar movimientos por empresa y fecha de operación")
    void debeEncontrarMovimientosPorEmpresaYFechaOperacion() {
        // When
        List<Movimiento> movimientos = movimientoRepository
            .findByEmpresaAndFechaOperacion("GRP001", "EMP001", LocalDate.now());
        
        // Then
        assertEquals(2, movimientos.size());
    }
    
    @Test
    @DisplayName("Debe encontrar movimientos por empresa y fecha de liquidación")
    void debeEncontrarMovimientosPorEmpresaYFechaLiquidacion() {
        // When
        List<Movimiento> movimientos = movimientoRepository
            .findByEmpresaAndFechaLiquidacion("GRP001", "EMP001", LocalDate.now().plusDays(1));
        
        // Then
        assertEquals(1, movimientos.size());
        assertEquals(1001L, movimientos.get(0).getId().getIdMovimiento());
    }
    
    @Test
    @DisplayName("Debe encontrar movimientos por cuenta y rango de fechas")
    void debeEncontrarMovimientosPorCuentaYRangoFechas() {
        // When
        List<Movimiento> movimientos = movimientoRepository
            .findByCuentaAndRangoFechas(100001L, LocalDate.now().minusDays(1), LocalDate.now().plusDays(1));
        
        // Then
        assertEquals(1, movimientos.size());
        assertEquals(1001L, movimientos.get(0).getId().getIdMovimiento());
    }
    
    @Test
    @DisplayName("Debe encontrar movimientos por préstamo")
    void debeEncontrarMovimientosPorPrestamo() {
        // When
        List<Movimiento> movimientos = movimientoRepository.findByPrestamo(2001L);
        
        // Then
        assertEquals(1, movimientos.size());
        assertEquals(1002L, movimientos.get(0).getId().getIdMovimiento());
    }
    
    @Test
    @DisplayName("Debe encontrar movimiento por ID de pre-movimiento")
    void debeEncontrarMovimientoPorIdPreMovimiento() {
        // When
        Optional<Movimiento> movimiento = movimientoRepository.findByIdPreMovimiento(10001L);
        
        // Then
        assertTrue(movimiento.isPresent());
        assertEquals(1001L, movimiento.get().getId().getIdMovimiento());
    }
    
    @Test
    @DisplayName("Debe encontrar movimientos pendientes de procesamiento")
    void debeEncontrarMovimientosPendientesProcesamiento() {
        // When
        List<Movimiento> movimientosPendientes = movimientoRepository
            .findPendientesProcesamiento("GRP001", "EMP001");
        
        // Then
        assertEquals(1, movimientosPendientes.size());
        assertEquals("PV", movimientosPendientes.get(0).getSituacionMovimiento());
    }
    
    @Test
    @DisplayName("Debe encontrar máximo ID de movimiento")
    void debeEncontrarMaximoIdMovimiento() {
        // When
        Long maxId = movimientoRepository.findMaxIdMovimiento("GRP001", "EMP001");
        
        // Then
        assertEquals(1002L, maxId);
    }
    
    @Test
    @DisplayName("Debe retornar 0 cuando no hay movimientos para una empresa")
    void debeRetornarCeroCuandoNoHayMovimientos() {
        // When
        Long maxId = movimientoRepository.findMaxIdMovimiento("GRP999", "EMP999");
        
        // Then
        assertEquals(0L, maxId);
    }
    
    @Test
    @DisplayName("Debe retornar lista vacía para empresa sin movimientos")
    void debeRetornarListaVaciaParaEmpresaSinMovimientos() {
        // When
        List<Movimiento> movimientos = movimientoRepository
            .findByEmpresaAndSituacion("GRP999", "EMP999", "PV");
        
        // Then
        assertTrue(movimientos.isEmpty());
    }
    
    @Test
    @DisplayName("Debe persistir y recuperar movimiento correctamente")
    void debePersistirYRecuperarMovimientoCorrectamente() {
        // Given
        MovimientoId nuevoId = new MovimientoId("GRP002", "EMP001", 3001L);
        Movimiento nuevoMovimiento = new Movimiento();
        nuevoMovimiento.setId(nuevoId);
        nuevoMovimiento.setIdCuenta(300001L);
        nuevoMovimiento.setClaveDivisa("EUR");
        nuevoMovimiento.setImporteNeto(new BigDecimal("1500.00"));
        nuevoMovimiento.setSituacionMovimiento("CA");
        nuevoMovimiento.setFechaHoraRegistro(LocalDateTime.now());
        
        // When
        Movimiento movimientoGuardado = movimientoRepository.save(nuevoMovimiento);
        entityManager.flush();
        entityManager.clear();
        
        Optional<Movimiento> movimientoRecuperado = movimientoRepository.findById(nuevoId);
        
        // Then
        assertNotNull(movimientoGuardado);
        assertTrue(movimientoRecuperado.isPresent());
        assertEquals("EUR", movimientoRecuperado.get().getClaveDivisa());
        assertEquals(new BigDecimal("1500.00"), movimientoRecuperado.get().getImporteNeto());
        assertEquals("CA", movimientoRecuperado.get().getSituacionMovimiento());
    }
}