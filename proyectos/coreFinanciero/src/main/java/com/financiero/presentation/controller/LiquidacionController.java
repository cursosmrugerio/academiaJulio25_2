package com.financiero.presentation.controller;

import com.financiero.application.service.LiquidacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/liquidacion")
@Tag(name = "Liquidación", description = "Operaciones relacionadas con fechas de liquidación")
@CrossOrigin(origins = "*", maxAge = 3600)
public class LiquidacionController {
    
    private final LiquidacionService liquidacionService;
    
    @Autowired
    public LiquidacionController(LiquidacionService liquidacionService) {
        this.liquidacionService = liquidacionService;
    }
    
    @PostMapping("/crear-fechas-anio")
    @Operation(summary = "Crear Fechas de Liquidación del Año", 
               description = "Genera las fechas de liquidación de los días hábiles del año especificado")
    public ResponseEntity<Map<String, Object>> crearFechasLiquidacionAnio(
            @Parameter(description = "Clave del grupo de empresa", required = true)
            @RequestParam String claveGrupoEmpresa,
            
            @Parameter(description = "Clave de la empresa", required = true)
            @RequestParam String claveEmpresa,
            
            @Parameter(description = "Año para generar las fechas (ej: 2025)", required = true)
            @RequestParam @Min(value = 2020, message = "El año debe ser mayor o igual a 2020")
                         @Max(value = 2050, message = "El año debe ser menor o igual a 2050") 
                         Integer anio) {
        
        String resultado = liquidacionService.crearFechasLiquidacionAnio(
            claveGrupoEmpresa, claveEmpresa, anio);
        
        return ResponseEntity.ok(Map.of(
            "mensaje", resultado,
            "anio", anio,
            "claveGrupoEmpresa", claveGrupoEmpresa,
            "claveEmpresa", claveEmpresa
        ));
    }
    
    @GetMapping("/validar-fecha")
    @Operation(summary = "Validar Fecha de Liquidación", 
               description = "Valida si una fecha de liquidación es correcta para una operación")
    public ResponseEntity<Map<String, Object>> validarFechaLiquidacion(
            @Parameter(description = "Clave del grupo de empresa", required = true)
            @RequestParam String claveGrupoEmpresa,
            
            @Parameter(description = "Clave de la empresa", required = true)
            @RequestParam String claveEmpresa,
            
            @Parameter(description = "Fecha de operación (formato: yyyy-MM-dd)", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaOperacion,
            
            @Parameter(description = "Fecha de liquidación (formato: yyyy-MM-dd)", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaLiquidacion,
            
            @Parameter(description = "Clave del mercado", required = true)
            @RequestParam String claveMercado) {
        
        boolean esValida = liquidacionService.validarFechaLiquidacion(
            claveGrupoEmpresa, claveEmpresa, fechaOperacion, fechaLiquidacion, claveMercado);
        
        return ResponseEntity.ok(Map.of(
            "fechaOperacion", fechaOperacion,
            "fechaLiquidacion", fechaLiquidacion,
            "claveMercado", claveMercado,
            "esValida", esValida,
            "mensaje", esValida ? "Fecha de liquidación válida" : "Fecha de liquidación inválida"
        ));
    }
}