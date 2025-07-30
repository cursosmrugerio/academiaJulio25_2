package com.financiero.application.mapper;

import com.financiero.application.dto.*;
import com.financiero.domain.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas para MovimientoMapper")
class MovimientoMapperTest {
    
    private MovimientoMapper mapper;
    private Movimiento movimiento;
    private MovimientoDTO movimientoDTO;
    private Saldo saldo;
    private CatalogoOperacion catalogoOperacion;
    
    @BeforeEach
    void setUp() {
        mapper = new MovimientoMapper();
        
        // Setup Movimiento
        MovimientoId movId = new MovimientoId("GRP001", "EMP001", 1001L);
        movimiento = new Movimiento();
        movimiento.setId(movId);
        movimiento.setIdCuenta(100001L);
        movimiento.setClaveDivisa("MXN");
        movimiento.setFechaOperacion(LocalDate.of(2025, 1, 15));
        movimiento.setFechaLiquidacion(LocalDate.of(2025, 1, 16));
        movimiento.setClaveOperacion("DEPOSITO");
        movimiento.setImporteNeto(new BigDecimal("1000.00"));
        movimiento.setSituacionMovimiento("PV");
        movimiento.setClaveUsuario("USER01");
        movimiento.setNota("Depósito inicial");
        movimiento.setReferencia("REF001");
        movimiento.setFechaHoraRegistro(LocalDateTime.of(2025, 1, 15, 10, 30));
        
        // Setup MovimientoDTO
        movimientoDTO = new MovimientoDTO();
        movimientoDTO.setClaveGrupoEmpresa("GRP001");
        movimientoDTO.setClaveEmpresa("EMP001");
        movimientoDTO.setIdMovimiento(1001L);
        movimientoDTO.setIdCuenta(100001L);
        movimientoDTO.setClaveDivisa("MXN");
        movimientoDTO.setFechaOperacion(LocalDate.of(2025, 1, 15));
        movimientoDTO.setClaveOperacion("DEPOSITO");
        movimientoDTO.setImporteNeto(new BigDecimal("1000.00"));
        movimientoDTO.setSituacionMovimiento("PV");
        movimientoDTO.setTextoNota("Depósito inicial");
        
        // Setup Saldo
        SaldoId saldoId = new SaldoId("GRP001", "EMP001", LocalDate.now(), 100001L, "MXN");
        saldo = new Saldo();
        saldo.setId(saldoId);
        saldo.setSaldoEfectivo(new BigDecimal("5000.00"));
        
        // Setup CatalogoOperacion
        CatalogoOperacionId catId = new CatalogoOperacionId("GRP001", "EMP001", "DEPOSITO");
        catalogoOperacion = new CatalogoOperacion();
        catalogoOperacion.setId(catId);
        catalogoOperacion.setDescripcion("Depósito en efectivo");
        catalogoOperacion.setClaveAfectaSaldo("I");
        catalogoOperacion.setEstatus("A");
        catalogoOperacion.setObservaciones("Operación de depósito");
    }
    
    @Test
    @DisplayName("Debe convertir Movimiento a DTO correctamente")
    void debeConvertirMovimientoADTOCorrectamente() {
        // When
        MovimientoDTO dto = mapper.toDTO(movimiento);
        
        // Then
        assertNotNull(dto);
        assertEquals("GRP001", dto.getClaveGrupoEmpresa());
        assertEquals("EMP001", dto.getClaveEmpresa());
        assertEquals(1001L, dto.getIdMovimiento());
        assertEquals(100001L, dto.getIdCuenta());
        assertEquals("MXN", dto.getClaveDivisa());
        assertEquals(LocalDate.of(2025, 1, 15), dto.getFechaOperacion());
        assertEquals(LocalDate.of(2025, 1, 16), dto.getFechaLiquidacion());
        assertEquals("DEPOSITO", dto.getClaveOperacion());
        assertEquals(new BigDecimal("1000.00"), dto.getImporteNeto());
        assertEquals("PV", dto.getSituacionMovimiento());
        assertEquals("USER01", dto.getClaveUsuario());
        assertEquals("Depósito inicial", dto.getTextoNota());
        assertEquals("REF001", dto.getTextoReferencia());
        assertEquals(LocalDateTime.of(2025, 1, 15, 10, 30), dto.getFechaHoraRegistro());
    }
    
    @Test
    @DisplayName("Debe convertir DTO a Movimiento correctamente")
    void debeConvertirDTOAMovimientoCorrectamente() {
        // When
        Movimiento entity = mapper.toEntity(movimientoDTO);
        
        // Then
        assertNotNull(entity);
        assertNotNull(entity.getId());
        assertEquals("GRP001", entity.getId().getClaveGrupoEmpresa());
        assertEquals("EMP001", entity.getId().getClaveEmpresa());
        assertEquals(1001L, entity.getId().getIdMovimiento());
        assertEquals(100001L, entity.getIdCuenta());
        assertEquals("MXN", entity.getClaveDivisa());
        assertEquals(LocalDate.of(2025, 1, 15), entity.getFechaOperacion());
        assertEquals("DEPOSITO", entity.getClaveOperacion());
        assertEquals(new BigDecimal("1000.00"), entity.getImporteNeto());
        assertEquals("PV", entity.getSituacionMovimiento());
        assertEquals("Depósito inicial", entity.getNota());
    }
    
    @Test
    @DisplayName("Debe manejar Movimiento null")
    void debeManejarMovimientoNull() {
        // When
        MovimientoDTO dto = mapper.toDTO((Movimiento) null);
        
        // Then
        assertNull(dto);
    }
    
    @Test
    @DisplayName("Debe manejar DTO null")
    void debeManejarDTONull() {
        // When
        Movimiento entity = mapper.toEntity(null);
        
        // Then
        assertNull(entity);
    }
    
    @Test
    @DisplayName("Debe convertir lista de Movimientos a DTOs")
    void debeConvertirListaDeMovimientosADTOs() {
        // Given
        Movimiento movimiento2 = new Movimiento();
        MovimientoId movId2 = new MovimientoId("GRP001", "EMP001", 1002L);
        movimiento2.setId(movId2);
        movimiento2.setImporteNeto(new BigDecimal("500.00"));
        
        List<Movimiento> movimientos = Arrays.asList(movimiento, movimiento2);
        
        // When
        List<MovimientoDTO> dtos = mapper.toDTOList(movimientos);
        
        // Then
        assertNotNull(dtos);
        assertEquals(2, dtos.size());
        assertEquals(1001L, dtos.get(0).getIdMovimiento());
        assertEquals(1002L, dtos.get(1).getIdMovimiento());
    }
    
    @Test
    @DisplayName("Debe manejar lista null de Movimientos")
    void debeManejarListaNullDeMovimientos() {
        // When
        List<MovimientoDTO> dtos = mapper.toDTOList(null);
        
        // Then
        assertNull(dtos);
    }
    
    @Test
    @DisplayName("Debe convertir MovimientoDetalle a DTO correctamente")
    void debeConvertirMovimientoDetalleADTOCorrectamente() {
        // Given
        MovimientoDetalleId detId = new MovimientoDetalleId("GRP001", "EMP001", 1001L, "CAPITAL");
        MovimientoDetalle detalle = new MovimientoDetalle();
        detalle.setId(detId);
        detalle.setImporteConcepto(new BigDecimal("800.00"));
        detalle.setNota("Pago de capital");
        
        // When
        MovimientoDetalleDTO dto = mapper.toDTO(detalle);
        
        // Then
        assertNotNull(dto);
        assertEquals("GRP001", dto.getClaveGrupoEmpresa());
        assertEquals("EMP001", dto.getClaveEmpresa());
        assertEquals(1001L, dto.getIdMovimiento());
        assertEquals("CAPITAL", dto.getClaveConcepto());
        assertEquals(new BigDecimal("800.00"), dto.getImporteConcepto());
        assertEquals("Pago de capital", dto.getTextoNota());
    }
    
    @Test
    @DisplayName("Debe convertir Saldo a DTO correctamente")
    void debeConvertirSaldoADTOCorrectamente() {
        // When
        SaldoDTO dto = mapper.toDTO(saldo);
        
        // Then
        assertNotNull(dto);
        assertEquals("GRP001", dto.getClaveGrupoEmpresa());
        assertEquals("EMP001", dto.getClaveEmpresa());
        assertEquals(LocalDate.now(), dto.getFechaFoto());
        assertEquals(100001L, dto.getIdCuenta());
        assertEquals("MXN", dto.getClaveDivisa());
        assertEquals(new BigDecimal("5000.00"), dto.getSaldoEfectivo());
    }
    
    @Test
    @DisplayName("Debe convertir CatalogoOperacion a DTO correctamente")
    void debeConvertirCatalogoOperacionADTOCorrectamente() {
        // When
        CatalogoOperacionDTO dto = mapper.toDTO(catalogoOperacion);
        
        // Then
        assertNotNull(dto);
        assertEquals("GRP001", dto.getClaveGrupoEmpresa());
        assertEquals("EMP001", dto.getClaveEmpresa());
        assertEquals("DEPOSITO", dto.getClaveOperacion());
        assertEquals("Depósito en efectivo", dto.getTextoDescripcion());
        assertEquals("I", dto.getClaveAfectaSaldo());
        assertEquals("A", dto.getEstatusOperacion());
        assertEquals("Operación de depósito", dto.getTextoObservaciones());
    }
    
    @Test
    @DisplayName("Debe convertir lista de Saldos a DTOs")
    void debeConvertirListaDeSaldosADTOs() {
        // Given
        SaldoId saldoId2 = new SaldoId("GRP001", "EMP001", LocalDate.now(), 100002L, "USD");
        Saldo saldo2 = new Saldo();
        saldo2.setId(saldoId2);
        saldo2.setSaldoEfectivo(new BigDecimal("3000.00"));
        
        List<Saldo> saldos = Arrays.asList(saldo, saldo2);
        
        // When
        List<SaldoDTO> dtos = mapper.toSaldoDTOList(saldos);
        
        // Then
        assertNotNull(dtos);
        assertEquals(2, dtos.size());
        assertEquals("MXN", dtos.get(0).getClaveDivisa());
        assertEquals("USD", dtos.get(1).getClaveDivisa());
    }
    
    @Test
    @DisplayName("Debe convertir lista de CatalogoOperacion a DTOs")
    void debeConvertirListaDeCatalogoOperacionADTOs() {
        // Given
        CatalogoOperacionId catId2 = new CatalogoOperacionId("GRP001", "EMP001", "RETIRO");
        CatalogoOperacion catalogo2 = new CatalogoOperacion();
        catalogo2.setId(catId2);
        catalogo2.setDescripcion("Retiro en efectivo");
        catalogo2.setClaveAfectaSaldo("D");
        
        List<CatalogoOperacion> catalogos = Arrays.asList(catalogoOperacion, catalogo2);
        
        // When
        List<CatalogoOperacionDTO> dtos = mapper.toCatalogoDTOList(catalogos);
        
        // Then
        assertNotNull(dtos);
        assertEquals(2, dtos.size());
        assertEquals("DEPOSITO", dtos.get(0).getClaveOperacion());
        assertEquals("RETIRO", dtos.get(1).getClaveOperacion());
    }
    
    @Test
    @DisplayName("Debe manejar entidades con ID null")
    void debeManejarEntidadesConIdNull() {
        // Given
        Movimiento movSinId = new Movimiento();
        movSinId.setImporteNeto(new BigDecimal("100.00"));
        
        // When
        MovimientoDTO dto = mapper.toDTO(movSinId);
        
        // Then
        assertNotNull(dto);
        assertNull(dto.getClaveGrupoEmpresa());
        assertNull(dto.getClaveEmpresa());
        assertNull(dto.getIdMovimiento());
        assertEquals(new BigDecimal("100.00"), dto.getImporteNeto());
    }
}