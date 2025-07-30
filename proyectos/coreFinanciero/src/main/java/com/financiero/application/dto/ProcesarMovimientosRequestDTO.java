package com.financiero.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class ProcesarMovimientosRequestDTO {
    
    @NotBlank(message = "La clave de grupo empresa es obligatoria")
    @Size(max = 10)
    private String claveGrupoEmpresa;
    
    @NotBlank(message = "La clave de empresa es obligatoria")
    @Size(max = 10)
    private String claveEmpresa;
    
    @NotNull(message = "La fecha de proceso es obligatoria")
    private LocalDate fechaProceso;
    
    @Size(max = 20)
    private String claveUsuario;
    
    // Constructors
    public ProcesarMovimientosRequestDTO() {}
    
    public ProcesarMovimientosRequestDTO(String claveGrupoEmpresa, String claveEmpresa, 
                                        LocalDate fechaProceso, String claveUsuario) {
        this.claveGrupoEmpresa = claveGrupoEmpresa;
        this.claveEmpresa = claveEmpresa;
        this.fechaProceso = fechaProceso;
        this.claveUsuario = claveUsuario;
    }
    
    // Getters and Setters
    public String getClaveGrupoEmpresa() {
        return claveGrupoEmpresa;
    }
    
    public void setClaveGrupoEmpresa(String claveGrupoEmpresa) {
        this.claveGrupoEmpresa = claveGrupoEmpresa;
    }
    
    public String getClaveEmpresa() {
        return claveEmpresa;
    }
    
    public void setClaveEmpresa(String claveEmpresa) {
        this.claveEmpresa = claveEmpresa;
    }
    
    public LocalDate getFechaProceso() {
        return fechaProceso;
    }
    
    public void setFechaProceso(LocalDate fechaProceso) {
        this.fechaProceso = fechaProceso;
    }
    
    public String getClaveUsuario() {
        return claveUsuario;
    }
    
    public void setClaveUsuario(String claveUsuario) {
        this.claveUsuario = claveUsuario;
    }
}