package com.financiero.presentation.controller;

import com.financiero.application.dto.*;
import com.financiero.application.service.MovimientoService;
import com.financiero.domain.entity.PreMovimiento;
import com.financiero.domain.entity.PreMovimientoDetalle;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/movimientos")
@Tag(name = "Movimientos", description = "Operaciones relacionadas con pre-movimientos de tesorería")
@CrossOrigin(origins = "*", maxAge = 3600)
public class MovimientoController {
    
    private final MovimientoService movimientoService;
    
    @Autowired
    public MovimientoController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }
    
    @PostMapping("/pre-movimiento")
    @Operation(summary = "Generar Pre-Movimiento", 
               description = "Genera un movimiento de abono o cargo de tesorería")
    public ResponseEntity<Map<String, Object>> generarPreMovimiento(
            @Valid @RequestBody PreMovimientoRequestDTO request) {
        
        String resultado = movimientoService.generarPreMovimiento(
            request.getClaveGrupoEmpresa(),
            request.getClaveEmpresa(),
            request.getIdPreMovimiento(),
            request.getFechaLiquidacion(),
            request.getIdCuenta(),
            request.getIdPrestamo(),
            request.getClaveDivisa(),
            request.getClaveOperacion(),
            request.getImporteNeto(),
            request.getClaveMedio(),
            request.getClaveMercado(),
            request.getNota(),
            request.getIdGrupo(),
            request.getClaveUsuario(),
            request.getFechaValor(),
            request.getNumeroPagoAmortizacion()
        );
        
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
            "mensaje", resultado,
            "idPreMovimiento", request.getIdPreMovimiento(),
            "status", "CREADO"
        ));
    }
    
    @PostMapping("/pre-movimiento-detalle")
    @Operation(summary = "Generar Detalle de Pre-Movimiento", 
               description = "Genera un registro con el detalle de un concepto de una operación de tesorería")
    public ResponseEntity<Map<String, Object>> generarPreMovimientoDetalle(
            @Valid @RequestBody PreMovimientoDetalleRequestDTO request) {
        
        String resultado = movimientoService.generarPreMovimientoDetalle(
            request.getClaveGrupoEmpresa(),
            request.getClaveEmpresa(),
            request.getIdPreMovimiento(),
            request.getClaveConcepto(),
            request.getImporteConcepto(),
            request.getNota()
        );
        
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
            "mensaje", resultado,
            "idPreMovimiento", request.getIdPreMovimiento(),
            "claveConcepto", request.getClaveConcepto(),
            "status", "CREADO"
        ));
    }
    
    @GetMapping("/pre-movimiento/{claveGrupoEmpresa}/{claveEmpresa}/{idPreMovimiento}")
    @Operation(summary = "Obtener Pre-Movimiento", 
               description = "Obtiene los detalles de un pre-movimiento específico")
    public ResponseEntity<PreMovimientoResponseDTO> obtenerPreMovimiento(
            @Parameter(description = "Clave del grupo de empresa", required = true)
            @PathVariable String claveGrupoEmpresa,
            
            @Parameter(description = "Clave de la empresa", required = true)
            @PathVariable String claveEmpresa,
            
            @Parameter(description = "ID del pre-movimiento", required = true)
            @PathVariable Long idPreMovimiento) {
        
        PreMovimiento preMovimiento = movimientoService.obtenerPreMovimiento(
            claveGrupoEmpresa, claveEmpresa, idPreMovimiento);
        
        // Convertir entidad a DTO
        PreMovimientoResponseDTO response = convertirAResponseDTO(preMovimiento);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/pendientes")
    @Operation(summary = "Obtener Movimientos Pendientes", 
               description = "Obtiene los pre-movimientos pendientes por fecha de liquidación")
    public ResponseEntity<List<PreMovimientoResponseDTO>> obtenerMovimientosPendientes(
            @Parameter(description = "Clave del grupo de empresa", required = true)
            @RequestParam String claveGrupoEmpresa,
            
            @Parameter(description = "Clave de la empresa", required = true)
            @RequestParam String claveEmpresa,
            
            @Parameter(description = "Fecha de liquidación (formato: yyyy-MM-dd)", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaLiquidacion) {
        
        List<PreMovimiento> movimientosPendientes = movimientoService.obtenerMovimientosPendientes(
            claveGrupoEmpresa, claveEmpresa, fechaLiquidacion);
        
        List<PreMovimientoResponseDTO> response = movimientosPendientes.stream()
            .map(this::convertirAResponseDTO)
            .toList();
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/procesar-pendientes")
    @Operation(summary = "Procesar Movimientos Pendientes", 
               description = "Procesa todos los movimientos pendientes de una empresa")
    public ResponseEntity<Map<String, String>> procesarMovimientosPendientes(
            @Parameter(description = "Clave del grupo de empresa", required = true)
            @RequestParam String claveGrupoEmpresa,
            
            @Parameter(description = "Clave de la empresa", required = true)
            @RequestParam String claveEmpresa) {
        
        movimientoService.procesarMovimientosPendientes(claveGrupoEmpresa, claveEmpresa);
        
        return ResponseEntity.ok(Map.of(
            "mensaje", "Movimientos pendientes procesados exitosamente",
            "claveGrupoEmpresa", claveGrupoEmpresa,
            "claveEmpresa", claveEmpresa
        ));
    }
    
    @GetMapping("/detalles/{claveGrupoEmpresa}/{claveEmpresa}/{idPreMovimiento}")
    @Operation(summary = "Obtener Detalles de Pre-Movimiento", 
               description = "Obtiene todos los detalles (conceptos) de un pre-movimiento")
    public ResponseEntity<List<PreMovimientoDetalleResponseDTO>> obtenerDetallesPreMovimiento(
            @Parameter(description = "Clave del grupo de empresa", required = true)
            @PathVariable String claveGrupoEmpresa,
            
            @Parameter(description = "Clave de la empresa", required = true)
            @PathVariable String claveEmpresa,
            
            @Parameter(description = "ID del pre-movimiento", required = true)
            @PathVariable Long idPreMovimiento) {
        
        List<PreMovimientoDetalle> detalles = movimientoService.obtenerDetallesPreMovimiento(
            claveGrupoEmpresa, claveEmpresa, idPreMovimiento);
        
        List<PreMovimientoDetalleResponseDTO> response = detalles.stream()
            .map(this::convertirADetalleResponseDTO)
            .toList();
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/total-conceptos/{claveGrupoEmpresa}/{claveEmpresa}/{idPreMovimiento}")
    @Operation(summary = "Calcular Total de Conceptos", 
               description = "Calcula el total de todos los conceptos de un pre-movimiento")
    public ResponseEntity<Map<String, Object>> calcularTotalConceptos(
            @Parameter(description = "Clave del grupo de empresa", required = true)
            @PathVariable String claveGrupoEmpresa,
            
            @Parameter(description = "Clave de la empresa", required = true)
            @PathVariable String claveEmpresa,
            
            @Parameter(description = "ID del pre-movimiento", required = true)
            @PathVariable Long idPreMovimiento) {
        
        BigDecimal total = movimientoService.calcularTotalConceptos(
            claveGrupoEmpresa, claveEmpresa, idPreMovimiento);
        
        return ResponseEntity.ok(Map.of(
            "idPreMovimiento", idPreMovimiento,
            "totalConceptos", total,
            "claveGrupoEmpresa", claveGrupoEmpresa,
            "claveEmpresa", claveEmpresa
        ));
    }
    
    // Métodos auxiliares para conversión de DTOs
    private PreMovimientoResponseDTO convertirAResponseDTO(PreMovimiento preMovimiento) {
        PreMovimientoResponseDTO dto = new PreMovimientoResponseDTO();
        dto.setClaveGrupoEmpresa(preMovimiento.getId().getClaveGrupoEmpresa());
        dto.setClaveEmpresa(preMovimiento.getId().getClaveEmpresa());
        dto.setIdPreMovimiento(preMovimiento.getId().getIdPreMovimiento());
        dto.setFechaOperacion(preMovimiento.getFechaOperacion());
        dto.setFechaLiquidacion(preMovimiento.getFechaLiquidacion());
        dto.setFechaAplicacion(preMovimiento.getFechaAplicacion());
        dto.setIdCuenta(preMovimiento.getIdCuenta());
        dto.setIdPrestamo(preMovimiento.getIdPrestamo());
        dto.setClaveDivisa(preMovimiento.getClaveDivisa());
        dto.setClaveOperacion(preMovimiento.getClaveOperacion());
        dto.setImporteNeto(preMovimiento.getImporteNeto());
        dto.setPrecioOperacion(preMovimiento.getPrecioOperacion());
        dto.setTipoCambio(preMovimiento.getTipoCambio());
        dto.setClaveMedio(preMovimiento.getClaveMedio());
        dto.setClaveMercado(preMovimiento.getClaveMercado());
        dto.setNota(preMovimiento.getNota());
        dto.setIdGrupo(preMovimiento.getIdGrupo());
        dto.setIdMovimiento(preMovimiento.getIdMovimiento());
        dto.setClaveUsuario(preMovimiento.getClaveUsuario());
        dto.setSituacionPreMovimiento(preMovimiento.getSituacionPreMovimiento());
        dto.setNumeroPagoAmortizacion(preMovimiento.getNumeroPagoAmortizacion());
        
        // Convertir detalles si están presentes
        if (preMovimiento.getDetalles() != null && !preMovimiento.getDetalles().isEmpty()) {
            List<PreMovimientoDetalleResponseDTO> detallesDTO = preMovimiento.getDetalles().stream()
                .map(this::convertirADetalleResponseDTO)
                .toList();
            dto.setDetalles(detallesDTO);
        }
        
        return dto;
    }
    
    private PreMovimientoDetalleResponseDTO convertirADetalleResponseDTO(PreMovimientoDetalle detalle) {
        return new PreMovimientoDetalleResponseDTO(
            detalle.getId().getClaveGrupoEmpresa(),
            detalle.getId().getClaveEmpresa(),
            detalle.getId().getIdPreMovimiento(),
            detalle.getId().getClaveConcepto(),
            detalle.getImporteConcepto(),
            detalle.getNota()
        );
    }
}