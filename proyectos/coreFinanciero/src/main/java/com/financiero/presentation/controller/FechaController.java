package com.financiero.presentation.controller;

import com.financiero.application.service.FechaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/fechas")
@Tag(name = "Fechas", description = "Operaciones relacionadas con fechas del sistema")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FechaController {
    
    private final FechaService fechaService;
    
    @Autowired
    public FechaController(FechaService fechaService) {
        this.fechaService = fechaService;
    }
    
    @PostMapping("/recorrer")
    @Operation(summary = "Recorrer Fecha", 
               description = "Actualiza la fecha medio de la tabla de parámetros al siguiente día hábil")
    public ResponseEntity<Map<String, String>> recorrerFecha(
            @Parameter(description = "Clave del grupo de empresa", required = true)
            @RequestParam String claveGrupoEmpresa,
            
            @Parameter(description = "Clave de la empresa", required = true)
            @RequestParam String claveEmpresa) {
        
        String resultado = fechaService.recorrerFecha(claveGrupoEmpresa, claveEmpresa);
        
        return ResponseEntity.ok(Map.of(
            "mensaje", resultado,
            "claveGrupoEmpresa", claveGrupoEmpresa,
            "claveEmpresa", claveEmpresa
        ));
    }
    
    @GetMapping("/sistema")
    @Operation(summary = "Obtener Fecha del Sistema", 
               description = "Obtiene la fecha actual del sistema para una empresa específica")
    public ResponseEntity<Map<String, Object>> obtenerFechaSistema(
            @Parameter(description = "Clave del grupo de empresa", required = true)
            @RequestParam String claveGrupoEmpresa,
            
            @Parameter(description = "Clave de la empresa", required = true)
            @RequestParam String claveEmpresa) {
        
        LocalDate fechaSistema = fechaService.obtenerFechaSistema(claveGrupoEmpresa, claveEmpresa);
        
        return ResponseEntity.ok(Map.of(
            "fechaSistema", fechaSistema,
            "claveGrupoEmpresa", claveGrupoEmpresa,
            "claveEmpresa", claveEmpresa
        ));
    }
    
    @PutMapping("/sistema")
    @Operation(summary = "Actualizar Fecha del Sistema", 
               description = "Actualiza manualmente la fecha del sistema para una empresa")
    public ResponseEntity<Map<String, String>> actualizarFechaSistema(
            @Parameter(description = "Clave del grupo de empresa", required = true)
            @RequestParam String claveGrupoEmpresa,
            
            @Parameter(description = "Clave de la empresa", required = true)
            @RequestParam String claveEmpresa,
            
            @Parameter(description = "Nueva fecha del sistema (formato: yyyy-MM-dd)", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate nuevaFecha) {
        
        fechaService.actualizarFechaSistema(claveGrupoEmpresa, claveEmpresa, nuevaFecha);
        
        return ResponseEntity.ok(Map.of(
            "mensaje", "Fecha del sistema actualizada exitosamente",
            "nuevaFecha", nuevaFecha.toString(),
            "claveGrupoEmpresa", claveGrupoEmpresa,
            "claveEmpresa", claveEmpresa
        ));
    }
    
    @GetMapping("/validar-dia-habil")
    @Operation(summary = "Validar Día Hábil", 
               description = "Verifica si una fecha específica es un día hábil")
    public ResponseEntity<Map<String, Object>> validarDiaHabil(
            @Parameter(description = "Fecha a validar (formato: yyyy-MM-dd)", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        
        boolean esDiaHabil = fechaService.esDiaHabil(fecha);
        LocalDate siguienteDiaHabil = fechaService.obtenerSiguienteDiaHabil(fecha);
        
        return ResponseEntity.ok(Map.of(
            "fecha", fecha,
            "esDiaHabil", esDiaHabil,
            "siguienteDiaHabil", siguienteDiaHabil
        ));
    }
}