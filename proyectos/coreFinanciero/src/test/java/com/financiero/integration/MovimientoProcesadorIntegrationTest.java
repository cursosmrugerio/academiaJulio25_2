package com.financiero.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.financiero.application.dto.ProcesarMovimientosRequestDTO;
import com.financiero.domain.entity.*;
import com.financiero.infrastructure.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
@DisplayName("Pruebas de integración para procesador de movimientos")
class MovimientoProcesadorIntegrationTest {
    
    private MockMvc mockMvc;
    
    @Autowired
    private WebApplicationContext webApplicationContext;
    
    @Autowired
    private PreMovimientoRepository preMovimientoRepository;
    
    @Autowired
    private MovimientoRepository movimientoRepository;
    
    @Autowired
    private CatalogoOperacionRepository catalogoOperacionRepository;
    
    @Autowired
    private SaldoRepository saldoRepository;
    
    @Autowired
    private MovimientoDetalleRepository movimientoDetalleRepository;
    
    private ObjectMapper objectMapper;
    
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        
        // Limpiar datos de prueba
        movimientoDetalleRepository.deleteAll();
        movimientoRepository.deleteAll();
        saldoRepository.deleteAll();
        
        // Crear datos de prueba
        crearDatosDePrueba();
    }
    
    private void crearDatosDePrueba() {
        // Crear catálogo de operación
        CatalogoOperacionId catId = new CatalogoOperacionId("GRP001", "EMP001", "DEPOSITO");
        CatalogoOperacion catalogoOperacion = new CatalogoOperacion();
        catalogoOperacion.setId(catId);
        catalogoOperacion.setDescripcion("Depósito en efectivo");
        catalogoOperacion.setClaveAfectaSaldo("I");
        catalogoOperacion.setEstatus("A");
        catalogoOperacionRepository.save(catalogoOperacion);
        
        // Crear pre-movimiento
        PreMovimientoId preId = new PreMovimientoId("GRP001", "EMP001", 10001L);
        PreMovimiento preMovimiento = new PreMovimiento();
        preMovimiento.setId(preId);
        preMovimiento.setIdCuenta(100001L);
        preMovimiento.setClaveDivisa("MXN");
        preMovimiento.setFechaOperacion(LocalDate.now());
        preMovimiento.setFechaLiquidacion(LocalDate.now().plusDays(1));
        preMovimiento.setClaveOperacion("DEPOSITO");
        preMovimiento.setImporteNeto(new BigDecimal("1000.00"));
        preMovimiento.setSituacionPreMovimiento("NP");
        preMovimiento.setClaveUsuario("USER01");
        preMovimiento.setNota("Depósito de prueba");
        preMovimientoRepository.save(preMovimiento);
        
        // Crear saldo inicial
        SaldoId saldoId = new SaldoId("GRP001", "EMP001", LocalDate.now().plusDays(1), 100001L, "MXN");
        Saldo saldo = new Saldo();
        saldo.setId(saldoId);
        saldo.setSaldoEfectivo(new BigDecimal("5000.00"));
        saldoRepository.save(saldo);
    }
    
    @Test
    @DisplayName("Integración completa: procesamiento de pre-movimientos a movimientos")
    void integracionCompletaProcesarPreMovimientos() throws Exception {
        // Given
        ProcesarMovimientosRequestDTO request = new ProcesarMovimientosRequestDTO();
        request.setClaveGrupoEmpresa("GRP001");
        request.setClaveEmpresa("EMP001");
        request.setFechaProceso(LocalDate.now());
        request.setClaveUsuario("USER01");
        
        // Verificar estado inicial
        assertEquals(1, preMovimientoRepository.count());
        assertEquals(0, movimientoRepository.count());
        
        Optional<Saldo> saldoInicial = saldoRepository.findByCuentaFechaDivisa(
            "GRP001", "EMP001", LocalDate.now().plusDays(1), 100001L, "MXN");
        assertTrue(saldoInicial.isPresent());
        assertEquals(new BigDecimal("5000.00"), saldoInicial.get().getSaldoEfectivo());
        
        // When
        mockMvc.perform(post("/api/v1/movimientos/procesar-pre-movimientos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"));
        
        // Then
        // Verificar que se creó el movimiento
        assertEquals(1, movimientoRepository.count());
        
        // Verificar que el pre-movimiento cambió de estado
        Optional<PreMovimiento> preMovimientoActualizado = preMovimientoRepository
            .findByIdClaveGrupoEmpresaAndIdClaveEmpresaAndIdIdPreMovimiento("GRP001", "EMP001", 10001L);
        assertTrue(preMovimientoActualizado.isPresent());
        assertEquals("PR", preMovimientoActualizado.get().getSituacionPreMovimiento());
        
        // Verificar que el saldo se actualizó correctamente
        Optional<Saldo> saldoActualizado = saldoRepository.findByCuentaFechaDivisa(
            "GRP001", "EMP001", LocalDate.now().plusDays(1), 100001L, "MXN");
        assertTrue(saldoActualizado.isPresent());
        assertEquals(new BigDecimal("6000.00"), saldoActualizado.get().getSaldoEfectivo());
        
        // Verificar propiedades del movimiento creado
        Optional<Movimiento> movimientoCreado = movimientoRepository
            .findByIdPreMovimiento(10001L);
        assertTrue(movimientoCreado.isPresent());
        assertEquals("PV", movimientoCreado.get().getSituacionMovimiento());
        assertEquals(new BigDecimal("1000.00"), movimientoCreado.get().getImporteNeto());
        assertEquals("DEPOSITO", movimientoCreado.get().getClaveOperacion());
    }
    
    @Test
    @DisplayName("Integración: procesamiento de movimientos virtuales a reales")
    void integracionProcesarMovimientosVirtualesAReales() throws Exception {
        // Given - Primero crear un movimiento virtual
        MovimientoId movId = new MovimientoId("GRP001", "EMP001", 2001L);
        Movimiento movimientoVirtual = new Movimiento();
        movimientoVirtual.setId(movId);
        movimientoVirtual.setIdCuenta(100001L);
        movimientoVirtual.setClaveDivisa("MXN");
        movimientoVirtual.setClaveOperacion("DEPOSITO");
        movimientoVirtual.setImporteNeto(new BigDecimal("500.00"));
        movimientoVirtual.setSituacionMovimiento("PV");
        movimientoRepository.save(movimientoVirtual);
        
        ProcesarMovimientosRequestDTO request = new ProcesarMovimientosRequestDTO();
        request.setClaveGrupoEmpresa("GRP001");
        request.setClaveEmpresa("EMP001");
        request.setFechaProceso(LocalDate.now());
        
        // When
        mockMvc.perform(post("/api/v1/movimientos/procesar-virtuales-a-reales")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"));
        
        // Then
        Optional<Movimiento> movimientoActualizado = movimientoRepository.findById(movId);
        assertTrue(movimientoActualizado.isPresent());
        assertEquals("PR", movimientoActualizado.get().getSituacionMovimiento());
        assertNotNull(movimientoActualizado.get().getFechaHoraActivacion());
    }
    
    @Test
    @DisplayName("Integración: cancelación de movimiento con reversión de saldo")
    void integracionCancelacionMovimientoConReversionSaldo() throws Exception {
        // Given - Crear movimiento virtual y saldo afectado
        MovimientoId movId = new MovimientoId("GRP001", "EMP001", 3001L);
        Movimiento movimiento = new Movimiento();
        movimiento.setId(movId);
        movimiento.setIdCuenta(100001L);
        movimiento.setClaveDivisa("MXN");
        movimiento.setClaveOperacion("DEPOSITO");
        movimiento.setImporteNeto(new BigDecimal("800.00"));
        movimiento.setSituacionMovimiento("PV");
        movimiento.setFechaLiquidacion(LocalDate.now().plusDays(1));
        movimientoRepository.save(movimiento);
        
        // Verificar saldo inicial
        Optional<Saldo> saldoInicial = saldoRepository.findByCuentaFechaDivisa(
            "GRP001", "EMP001", LocalDate.now().plusDays(1), 100001L, "MXN");
        assertTrue(saldoInicial.isPresent());
        BigDecimal saldoAnterior = saldoInicial.get().getSaldoEfectivo();
        
        // When
        mockMvc.perform(post("/api/v1/movimientos/GRP001/EMP001/3001/cancelar")
                .param("claveUsuario", "ADMIN01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.id_movimiento").value(3001))
                .andExpect(jsonPath("$.usuario_cancela").value("ADMIN01"));
        
        // Then
        // Verificar que el movimiento fue cancelado
        Optional<Movimiento> movimientoCancelado = movimientoRepository.findById(movId);
        assertTrue(movimientoCancelado.isPresent());
        assertEquals("CA", movimientoCancelado.get().getSituacionMovimiento());
        assertEquals("ADMIN01", movimientoCancelado.get().getClaveUsuarioCancela());
        
        // Verificar que el saldo fue reversado
        Optional<Saldo> saldoReversado = saldoRepository.findByCuentaFechaDivisa(
            "GRP001", "EMP001", LocalDate.now().plusDays(1), 100001L, "MXN");
        assertTrue(saldoReversado.isPresent());
        assertEquals(saldoAnterior.subtract(new BigDecimal("800.00")), 
                    saldoReversado.get().getSaldoEfectivo());
    }
    
    @Test
    @DisplayName("Integración: consulta de movimientos por empresa")
    void integracionConsultaMovimientosPorEmpresa() throws Exception {
        // Given - Crear varios movimientos
        for (int i = 1; i <= 3; i++) {
            MovimientoId movId = new MovimientoId("GRP001", "EMP001", (long) (4000 + i));
            Movimiento movimiento = new Movimiento();
            movimiento.setId(movId);
            movimiento.setIdCuenta(100001L);
            movimiento.setClaveDivisa("MXN");
            movimiento.setImporteNeto(new BigDecimal(String.valueOf(100 * i)));
            movimiento.setSituacionMovimiento(i <= 2 ? "PV" : "PR");
            movimientoRepository.save(movimiento);
        }
        
        // When & Then - Consultar todos los movimientos PV
        mockMvc.perform(get("/api/v1/movimientos/GRP001/EMP001")
                .param("situacion", "PV"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
        
        // Consultar movimiento específico
        mockMvc.perform(get("/api/v1/movimientos/GRP001/EMP001/4001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idMovimiento").value(4001))
                .andExpect(jsonPath("$.situacionMovimiento").value("PV"));
    }
    
    @Test
    @DisplayName("Integración: consulta de saldos por empresa")
    void integracionConsultaSaldosPorEmpresa() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/v1/movimientos/saldos/GRP001/EMP001")
                .param("fechaFoto", LocalDate.now().plusDays(1).toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].idCuenta").value(100001))
                .andExpect(jsonPath("$[0].claveDivisa").value("MXN"))
                .andExpect(jsonPath("$[0].saldoEfectivo").value(5000.00));
    }
    
    @Test
    @DisplayName("Integración: consulta de catálogo de operaciones")
    void integracionConsultaCatalogoOperaciones() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/v1/movimientos/catalogo-operaciones/GRP001/EMP001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].claveOperacion").value("DEPOSITO"))
                .andExpect(jsonPath("$[0].claveAfectaSaldo").value("I"))
                .andExpect(jsonPath("$[0].estatusOperacion").value("A"));
    }
    
    @Test
    @DisplayName("Integración: flujo completo desde pre-movimiento hasta cancelación")
    void integracionFlujoCompleto() throws Exception {
        // 1. Procesar pre-movimientos
        ProcesarMovimientosRequestDTO request = new ProcesarMovimientosRequestDTO();
        request.setClaveGrupoEmpresa("GRP001");
        request.setClaveEmpresa("EMP001");
        request.setFechaProceso(LocalDate.now());
        
        mockMvc.perform(post("/api/v1/movimientos/procesar-pre-movimientos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
        
        // 2. Obtener el movimiento creado
        Optional<Movimiento> movimientoCreado = movimientoRepository
            .findByIdPreMovimiento(10001L);
        assertTrue(movimientoCreado.isPresent());
        Long idMovimiento = movimientoCreado.get().getId().getIdMovimiento();
        
        // 3. Procesar a real
        mockMvc.perform(post("/api/v1/movimientos/procesar-virtuales-a-reales")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
        
        // 4. Verificar que está en estado real
        Optional<Movimiento> movimientoReal = movimientoRepository
            .findById(new MovimientoId("GRP001", "EMP001", idMovimiento));
        assertTrue(movimientoReal.isPresent());
        assertEquals("PR", movimientoReal.get().getSituacionMovimiento());
        
        // 5. Intentar cancelar (debe fallar porque está en estado final)
        mockMvc.perform(post("/api/v1/movimientos/GRP001/EMP001/" + idMovimiento + "/cancelar")
                .param("claveUsuario", "ADMIN01"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("error"));
    }
    
    @Test
    @DisplayName("Integración: manejo de errores con datos inválidos")
    void integracionManejoErroresDatosInvalidos() throws Exception {
        // Given - Request inválido
        ProcesarMovimientosRequestDTO requestInvalido = new ProcesarMovimientosRequestDTO();
        // Sin establecer campos obligatorios
        
        // When & Then
        mockMvc.perform(post("/api/v1/movimientos/procesar-pre-movimientos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestInvalido)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
        
        // Verificar que no se procesó nada
        assertEquals(1, preMovimientoRepository.count());
        assertEquals(0, movimientoRepository.count());
    }
}