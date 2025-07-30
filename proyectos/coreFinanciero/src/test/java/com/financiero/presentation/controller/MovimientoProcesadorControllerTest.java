package com.financiero.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.financiero.application.dto.*;
import com.financiero.application.mapper.MovimientoMapper;
import com.financiero.application.service.MovimientoProcesadorService;
import com.financiero.domain.entity.*;
import com.financiero.infrastructure.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovimientoProcesadorController.class)
@DisplayName("Pruebas para MovimientoProcesadorController")
class MovimientoProcesadorControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private MovimientoProcesadorService movimientoProcesadorService;
    
    @MockBean
    private MovimientoRepository movimientoRepository;
    
    @MockBean
    private SaldoRepository saldoRepository;
    
    @MockBean
    private CatalogoOperacionRepository catalogoOperacionRepository;
    
    @MockBean
    private MovimientoMapper movimientoMapper;
    
    private ObjectMapper objectMapper;
    private ProcesarMovimientosRequestDTO request;
    private Movimiento movimiento;
    private MovimientoDTO movimientoDTO;
    private Saldo saldo;
    private SaldoDTO saldoDTO;
    private CatalogoOperacion catalogoOperacion;
    private CatalogoOperacionDTO catalogoOperacionDTO;
    
    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        
        // Setup request
        request = new ProcesarMovimientosRequestDTO();
        request.setClaveGrupoEmpresa("GRP001");
        request.setClaveEmpresa("EMP001");
        request.setFechaProceso(LocalDate.now());
        request.setClaveUsuario("USER01");
        
        // Setup Movimiento
        MovimientoId movId = new MovimientoId("GRP001", "EMP001", 1001L);
        movimiento = new Movimiento();
        movimiento.setId(movId);
        movimiento.setIdCuenta(100001L);
        movimiento.setClaveDivisa("MXN");
        movimiento.setImporteNeto(new BigDecimal("1000.00"));
        movimiento.setSituacionMovimiento("PV");
        
        // Setup MovimientoDTO
        movimientoDTO = new MovimientoDTO();
        movimientoDTO.setClaveGrupoEmpresa("GRP001");
        movimientoDTO.setClaveEmpresa("EMP001");
        movimientoDTO.setIdMovimiento(1001L);
        movimientoDTO.setImporteNeto(new BigDecimal("1000.00"));
        movimientoDTO.setSituacionMovimiento("PV");
        
        // Setup Saldo
        SaldoId saldoId = new SaldoId("GRP001", "EMP001", LocalDate.now(), 100001L, "MXN");
        saldo = new Saldo();
        saldo.setId(saldoId);
        saldo.setSaldoEfectivo(new BigDecimal("5000.00"));
        
        // Setup SaldoDTO
        saldoDTO = new SaldoDTO();
        saldoDTO.setClaveGrupoEmpresa("GRP001");
        saldoDTO.setClaveEmpresa("EMP001");
        saldoDTO.setFechaFoto(LocalDate.now());
        saldoDTO.setIdCuenta(100001L);
        saldoDTO.setClaveDivisa("MXN");
        saldoDTO.setSaldoEfectivo(new BigDecimal("5000.00"));
        
        // Setup CatalogoOperacion
        CatalogoOperacionId catId = new CatalogoOperacionId("GRP001", "EMP001", "DEPOSITO");
        catalogoOperacion = new CatalogoOperacion();
        catalogoOperacion.setId(catId);
        catalogoOperacion.setDescripcion("Depósito en efectivo");
        catalogoOperacion.setClaveAfectaSaldo("I");
        catalogoOperacion.setEstatus("A");
        
        // Setup CatalogoOperacionDTO
        catalogoOperacionDTO = new CatalogoOperacionDTO();
        catalogoOperacionDTO.setClaveGrupoEmpresa("GRP001");
        catalogoOperacionDTO.setClaveEmpresa("EMP001");
        catalogoOperacionDTO.setClaveOperacion("DEPOSITO");
        catalogoOperacionDTO.setTextoDescripcion("Depósito en efectivo");
        catalogoOperacionDTO.setClaveAfectaSaldo("I");
        catalogoOperacionDTO.setEstatusOperacion("A");
    }
    
    @Test
    @DisplayName("Debe procesar pre-movimientos exitosamente")
    void debeProcesarPreMovimientosExitosamente() throws Exception {
        // Given
        doNothing().when(movimientoProcesadorService)
            .procesarPreMovimientos(anyString(), anyString(), any(LocalDate.class));
        
        // When & Then
        mockMvc.perform(post("/api/v1/movimientos/procesar-pre-movimientos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Pre-movimientos procesados exitosamente"))
                .andExpect(jsonPath("$.empresa").value("GRP001-EMP001"));
        
        verify(movimientoProcesadorService).procesarPreMovimientos("GRP001", "EMP001", LocalDate.now());
    }
    
    @Test
    @DisplayName("Debe manejar error al procesar pre-movimientos")
    void debeManejarErrorAlProcesarPreMovimientos() throws Exception {
        // Given
        doThrow(new RuntimeException("Error de procesamiento"))
            .when(movimientoProcesadorService)
            .procesarPreMovimientos(anyString(), anyString(), any(LocalDate.class));
        
        // When & Then
        mockMvc.perform(post("/api/v1/movimientos/procesar-pre-movimientos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message").value("Error procesando pre-movimientos: Error de procesamiento"));
    }
    
    @Test
    @DisplayName("Debe procesar movimientos virtuales a reales exitosamente")
    void debeProcesarMovimientosVirtualesARealesExitosamente() throws Exception {
        // Given
        doNothing().when(movimientoProcesadorService)
            .procesarMovimientosVirtualesAReales(anyString(), anyString(), any(LocalDate.class));
        
        // When & Then
        mockMvc.perform(post("/api/v1/movimientos/procesar-virtuales-a-reales")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Movimientos virtuales procesados a reales exitosamente"));
    }
    
    @Test
    @DisplayName("Debe cancelar movimiento exitosamente")
    void debeCancelarMovimientoExitosamente() throws Exception {
        // Given
        doNothing().when(movimientoProcesadorService)
            .cancelarMovimiento(anyString(), anyString(), anyLong(), anyString());
        
        // When & Then
        mockMvc.perform(post("/api/v1/movimientos/GRP001/EMP001/1001/cancelar")
                .param("claveUsuario", "ADMIN01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Movimiento cancelado exitosamente"))
                .andExpect(jsonPath("$.id_movimiento").value(1001))
                .andExpect(jsonPath("$.usuario_cancela").value("ADMIN01"));
        
        verify(movimientoProcesadorService).cancelarMovimiento("GRP001", "EMP001", 1001L, "ADMIN01");
    }
    
    @Test
    @DisplayName("Debe manejar error al cancelar movimiento")
    void debeManejarErrorAlCancelarMovimiento() throws Exception {
        // Given
        doThrow(new RuntimeException("Movimiento no puede ser cancelado"))
            .when(movimientoProcesadorService)
            .cancelarMovimiento(anyString(), anyString(), anyLong(), anyString());
        
        // When & Then
        mockMvc.perform(post("/api/v1/movimientos/GRP001/EMP001/1001/cancelar")
                .param("claveUsuario", "ADMIN01"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message").value("Error cancelando movimiento: Movimiento no puede ser cancelado"));
    }
    
    @Test
    @DisplayName("Debe obtener movimientos por empresa")
    void debeObtenerMovimientosPorEmpresa() throws Exception {
        // Given
        List<Movimiento> movimientos = Arrays.asList(movimiento);
        List<MovimientoDTO> movimientosDTO = Arrays.asList(movimientoDTO);
        
        when(movimientoRepository.findByEmpresaAndSituacion("GRP001", "EMP001", "PV"))
            .thenReturn(movimientos);
        when(movimientoMapper.toDTOList(movimientos)).thenReturn(movimientosDTO);
        
        // When & Then
        mockMvc.perform(get("/api/v1/movimientos/GRP001/EMP001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].idMovimiento").value(1001))
                .andExpect(jsonPath("$[0].situacionMovimiento").value("PV"));
    }
    
    @Test
    @DisplayName("Debe obtener movimientos filtrados por situación")
    void debeObtenerMovimientosFiltradosPorSituacion() throws Exception {
        // Given
        List<Movimiento> movimientos = Arrays.asList(movimiento);
        List<MovimientoDTO> movimientosDTO = Arrays.asList(movimientoDTO);
        
        when(movimientoRepository.findByEmpresaAndSituacion("GRP001", "EMP001", "PR"))
            .thenReturn(movimientos);
        when(movimientoMapper.toDTOList(movimientos)).thenReturn(movimientosDTO);
        
        // When & Then
        mockMvc.perform(get("/api/v1/movimientos/GRP001/EMP001")
                .param("situacion", "PR"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
        
        verify(movimientoRepository).findByEmpresaAndSituacion("GRP001", "EMP001", "PR");
    }
    
    @Test
    @DisplayName("Debe obtener movimiento por ID")
    void debeObtenerMovimientoPorId() throws Exception {
        // Given
        MovimientoId movId = new MovimientoId("GRP001", "EMP001", 1001L);
        
        when(movimientoRepository.findById(movId)).thenReturn(Optional.of(movimiento));
        when(movimientoMapper.toDTO(movimiento)).thenReturn(movimientoDTO);
        
        // When & Then
        mockMvc.perform(get("/api/v1/movimientos/GRP001/EMP001/1001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idMovimiento").value(1001))
                .andExpect(jsonPath("$.situacionMovimiento").value("PV"));
    }
    
    @Test
    @DisplayName("Debe retornar 404 para movimiento inexistente")
    void debeRetornar404ParaMovimientoInexistente() throws Exception {
        // Given
        MovimientoId movId = new MovimientoId("GRP001", "EMP001", 9999L);
        
        when(movimientoRepository.findById(movId)).thenReturn(Optional.empty());
        
        // When & Then
        mockMvc.perform(get("/api/v1/movimientos/GRP001/EMP001/9999"))
                .andExpect(status().isNotFound());
    }
    
    @Test
    @DisplayName("Debe obtener saldos por empresa")
    void debeObtenerBaldosPorEmpresa() throws Exception {
        // Given
        List<Saldo> saldos = Arrays.asList(saldo);
        List<SaldoDTO> saldosDTO = Arrays.asList(saldoDTO);
        
        when(saldoRepository.findByEmpresaAndFecha("GRP001", "EMP001", LocalDate.now()))
            .thenReturn(saldos);
        when(movimientoMapper.toSaldoDTOList(saldos)).thenReturn(saldosDTO);
        
        // When & Then
        mockMvc.perform(get("/api/v1/movimientos/saldos/GRP001/EMP001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].idCuenta").value(100001))
                .andExpect(jsonPath("$[0].saldoEfectivo").value(5000.00));
    }
    
    @Test
    @DisplayName("Debe obtener catálogo de operaciones")
    void debeObtenerCatalogoDeOperaciones() throws Exception {
        // Given
        List<CatalogoOperacion> catalogos = Arrays.asList(catalogoOperacion);
        List<CatalogoOperacionDTO> catalogosDTO = Arrays.asList(catalogoOperacionDTO);
        
        when(catalogoOperacionRepository.findOperacionesActivas("GRP001", "EMP001"))
            .thenReturn(catalogos);
        when(movimientoMapper.toCatalogoDTOList(catalogos)).thenReturn(catalogosDTO);
        
        // When & Then
        mockMvc.perform(get("/api/v1/movimientos/catalogo-operaciones/GRP001/EMP001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].claveOperacion").value("DEPOSITO"))
                .andExpect(jsonPath("$[0].claveAfectaSaldo").value("I"));
    }
    
    @Test
    @DisplayName("Debe obtener movimientos pendientes de procesamiento")
    void debeObtenerMovimientosPendientesDeProcesamiento() throws Exception {
        // Given
        List<Movimiento> movimientosPendientes = Arrays.asList(movimiento);
        List<MovimientoDTO> movimientosDTO = Arrays.asList(movimientoDTO);
        
        when(movimientoRepository.findPendientesProcesamiento("GRP001", "EMP001"))
            .thenReturn(movimientosPendientes);
        when(movimientoMapper.toDTOList(movimientosPendientes)).thenReturn(movimientosDTO);
        
        // When & Then
        mockMvc.perform(get("/api/v1/movimientos/pendientes-procesamiento/GRP001/EMP001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].situacionMovimiento").value("PV"));
    }
    
    @Test
    @DisplayName("Debe manejar errores internos correctamente")
    void debeManejarErroresInternosCorrectamente() throws Exception {
        // Given
        when(movimientoRepository.findByEmpresaAndSituacion(anyString(), anyString(), anyString()))
            .thenThrow(new RuntimeException("Error de base de datos"));
        
        // When & Then
        mockMvc.perform(get("/api/v1/movimientos/GRP001/EMP001"))
                .andExpect(status().isInternalServerError());
    }
    
    @Test
    @DisplayName("Debe validar request con campos obligatorios")
    void debeValidarRequestConCamposObligatorios() throws Exception {
        // Given
        ProcesarMovimientosRequestDTO requestInvalido = new ProcesarMovimientosRequestDTO();
        // No se establecen campos obligatorios
        
        // When & Then
        mockMvc.perform(post("/api/v1/movimientos/procesar-pre-movimientos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestInvalido)))
                .andExpect(status().isBadRequest());
        
        verify(movimientoProcesadorService, never())
            .procesarPreMovimientos(anyString(), anyString(), any(LocalDate.class));
    }
    
    @Test
    @DisplayName("Debe retornar lista vacía cuando no hay resultados")
    void debeRetornarListaVaciaCuandoNoHayResultados() throws Exception {
        // Given
        when(movimientoRepository.findByEmpresaAndSituacion("GRP001", "EMP001", "PV"))
            .thenReturn(Collections.emptyList());
        when(movimientoMapper.toDTOList(Collections.emptyList()))
            .thenReturn(Collections.emptyList());
        
        // When & Then
        mockMvc.perform(get("/api/v1/movimientos/GRP001/EMP001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }
}