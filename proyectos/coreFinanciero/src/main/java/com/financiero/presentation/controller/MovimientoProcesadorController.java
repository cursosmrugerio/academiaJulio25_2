package com.financiero.presentation.controller;

import com.financiero.application.dto.*;
import com.financiero.application.mapper.MovimientoMapper;
import com.financiero.application.service.MovimientoProcesadorService;
import com.financiero.domain.entity.*;
import com.financiero.infrastructure.repository.*;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/movimientos")
@CrossOrigin(origins = "*")
public class MovimientoProcesadorController {
    
    private static final Logger logger = LoggerFactory.getLogger(MovimientoProcesadorController.class);
    
    @Autowired
    private MovimientoProcesadorService movimientoProcesadorService;
    
    @Autowired
    private MovimientoRepository movimientoRepository;
    
    @Autowired
    private SaldoRepository saldoRepository;
    
    @Autowired
    private CatalogoOperacionRepository catalogoOperacionRepository;
    
    @Autowired
    private MovimientoMapper movimientoMapper;
    
    @PostMapping("/procesar-pre-movimientos")
    public ResponseEntity<Map<String, Object>> procesarPreMovimientos(
            @Valid @RequestBody ProcesarMovimientosRequestDTO request) {
        
        logger.info("Procesando pre-movimientos para empresa {}-{} fecha {}", 
                   request.getClaveGrupoEmpresa(), request.getClaveEmpresa(), request.getFechaProceso());
        
        try {
            movimientoProcesadorService.procesarPreMovimientos(
                request.getClaveGrupoEmpresa(),
                request.getClaveEmpresa(),
                request.getFechaProceso()
            );
            
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Pre-movimientos procesados exitosamente");
            response.put("fecha_proceso", request.getFechaProceso());
            response.put("empresa", request.getClaveGrupoEmpresa() + "-" + request.getClaveEmpresa());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Error procesando pre-movimientos: {}", e.getMessage());
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Error procesando pre-movimientos: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    @PostMapping("/procesar-virtuales-a-reales")
    public ResponseEntity<Map<String, Object>> procesarMovimientosVirtualesAReales(
            @Valid @RequestBody ProcesarMovimientosRequestDTO request) {
        
        logger.info("Procesando movimientos virtuales a reales para empresa {}-{} fecha {}", 
                   request.getClaveGrupoEmpresa(), request.getClaveEmpresa(), request.getFechaProceso());
        
        try {
            movimientoProcesadorService.procesarMovimientosVirtualesAReales(
                request.getClaveGrupoEmpresa(),
                request.getClaveEmpresa(),
                request.getFechaProceso()
            );
            
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Movimientos virtuales procesados a reales exitosamente");
            response.put("fecha_proceso", request.getFechaProceso());
            response.put("empresa", request.getClaveGrupoEmpresa() + "-" + request.getClaveEmpresa());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Error procesando movimientos virtuales a reales: {}", e.getMessage());
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Error procesando movimientos virtuales a reales: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    @PostMapping("/{claveGrupoEmpresa}/{claveEmpresa}/{idMovimiento}/cancelar")
    public ResponseEntity<Map<String, Object>> cancelarMovimiento(
            @PathVariable String claveGrupoEmpresa,
            @PathVariable String claveEmpresa,
            @PathVariable Long idMovimiento,
            @RequestParam String claveUsuario) {
        
        logger.info("Cancelando movimiento {} para empresa {}-{}", idMovimiento, claveGrupoEmpresa, claveEmpresa);
        
        try {
            movimientoProcesadorService.cancelarMovimiento(claveGrupoEmpresa, claveEmpresa, idMovimiento, claveUsuario);
            
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Movimiento cancelado exitosamente");
            response.put("id_movimiento", idMovimiento);
            response.put("usuario_cancela", claveUsuario);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Error cancelando movimiento {}: {}", idMovimiento, e.getMessage());
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Error cancelando movimiento: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
    
    @GetMapping("/{claveGrupoEmpresa}/{claveEmpresa}")
    public ResponseEntity<List<MovimientoDTO>> obtenerMovimientosPorEmpresa(
            @PathVariable String claveGrupoEmpresa,
            @PathVariable String claveEmpresa,
            @RequestParam(required = false) String situacion,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaOperacion) {
        
        try {
            List<Movimiento> movimientos;
            
            if (situacion != null) {
                movimientos = movimientoRepository.findByEmpresaAndSituacion(claveGrupoEmpresa, claveEmpresa, situacion);
            } else if (fechaOperacion != null) {
                movimientos = movimientoRepository.findByEmpresaAndFechaOperacion(claveGrupoEmpresa, claveEmpresa, fechaOperacion);
            } else {
                movimientos = movimientoRepository.findByEmpresaAndSituacion(claveGrupoEmpresa, claveEmpresa, "PV");
            }
            
            List<MovimientoDTO> movimientosDTO = movimientoMapper.toDTOList(movimientos);
            return ResponseEntity.ok(movimientosDTO);
            
        } catch (Exception e) {
            logger.error("Error obteniendo movimientos: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/{claveGrupoEmpresa}/{claveEmpresa}/{idMovimiento}")
    public ResponseEntity<MovimientoDTO> obtenerMovimientoPorId(
            @PathVariable String claveGrupoEmpresa,
            @PathVariable String claveEmpresa,
            @PathVariable Long idMovimiento) {
        
        try {
            Optional<Movimiento> movimientoOpt = movimientoRepository.findById(
                new MovimientoId(claveGrupoEmpresa, claveEmpresa, idMovimiento)
            );
            
            if (movimientoOpt.isPresent()) {
                MovimientoDTO movimientoDTO = movimientoMapper.toDTO(movimientoOpt.get());
                return ResponseEntity.ok(movimientoDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
            
        } catch (Exception e) {
            logger.error("Error obteniendo movimiento {}: {}", idMovimiento, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/saldos/{claveGrupoEmpresa}/{claveEmpresa}")
    public ResponseEntity<List<SaldoDTO>> obtenerSaldosPorEmpresa(
            @PathVariable String claveGrupoEmpresa,
            @PathVariable String claveEmpresa,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFoto,
            @RequestParam(required = false) Long idCuenta) {
        
        try {
            List<Saldo> saldos;
            
            if (fechaFoto != null) {
                saldos = saldoRepository.findByEmpresaAndFecha(claveGrupoEmpresa, claveEmpresa, fechaFoto);
            } else if (idCuenta != null) {
                saldos = saldoRepository.findByCuentaDivisaOrderByFechaDesc(claveGrupoEmpresa, claveEmpresa, idCuenta, "MXN");
            } else {
                saldos = saldoRepository.findByEmpresaAndFecha(claveGrupoEmpresa, claveEmpresa, LocalDate.now());
            }
            
            List<SaldoDTO> saldosDTO = movimientoMapper.toSaldoDTOList(saldos);
            return ResponseEntity.ok(saldosDTO);
            
        } catch (Exception e) {
            logger.error("Error obteniendo saldos: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/catalogo-operaciones/{claveGrupoEmpresa}/{claveEmpresa}")
    public ResponseEntity<List<CatalogoOperacionDTO>> obtenerCatalogoOperaciones(
            @PathVariable String claveGrupoEmpresa,
            @PathVariable String claveEmpresa,
            @RequestParam(required = false) String estatus) {
        
        try {
            List<CatalogoOperacion> catalogos;
            
            if (estatus != null) {
                catalogos = catalogoOperacionRepository.findByEmpresaAndEstatus(claveGrupoEmpresa, claveEmpresa, estatus);
            } else {
                catalogos = catalogoOperacionRepository.findOperacionesActivas(claveGrupoEmpresa, claveEmpresa);
            }
            
            List<CatalogoOperacionDTO> catalogosDTO = movimientoMapper.toCatalogoDTOList(catalogos);
            return ResponseEntity.ok(catalogosDTO);
            
        } catch (Exception e) {
            logger.error("Error obteniendo catálogo de operaciones: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/pendientes-procesamiento/{claveGrupoEmpresa}/{claveEmpresa}")
    public ResponseEntity<List<MovimientoDTO>> obtenerMovimientosPendientesProcesamiento(
            @PathVariable String claveGrupoEmpresa,
            @PathVariable String claveEmpresa) {
        
        try {
            List<Movimiento> movimientosPendientes = movimientoRepository
                .findPendientesProcesamiento(claveGrupoEmpresa, claveEmpresa);
            
            List<MovimientoDTO> movimientosDTO = movimientoMapper.toDTOList(movimientosPendientes);
            return ResponseEntity.ok(movimientosDTO);
            
        } catch (Exception e) {
            logger.error("Error obteniendo movimientos pendientes: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}