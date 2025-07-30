package com.financiero.application.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class PreMovimientoDetalleRequestDTO {
    
    @NotBlank(message = "La clave de grupo empresa es obligatoria")
    @Size(max = 10, message = "La clave de grupo empresa no puede exceder 10 caracteres")
    private String claveGrupoEmpresa;
    
    @NotBlank(message = "La clave de empresa es obligatoria")
    @Size(max = 10, message = "La clave de empresa no puede exceder 10 caracteres")
    private String claveEmpresa;
    
    @NotNull(message = "El ID del pre-movimiento es obligatorio")
    private Long idPreMovimiento;
    
    @NotBlank(message = "La clave del concepto es obligatoria")
    @Size(max = 10, message = "La clave del concepto no puede exceder 10 caracteres")
    private String claveConcepto;
    
    @NotNull(message = "El importe del concepto es obligatorio")
    @DecimalMin(value = "0.01", message = "El importe del concepto debe ser mayor a cero")
    @Digits(integer = 13, fraction = 2, message = "El importe del concepto debe tener máximo 13 enteros y 2 decimales")
    private BigDecimal importeConcepto;
    
    @Size(max = 500, message = "La nota no puede exceder 500 caracteres")
    private String nota;
    
    // Constructors
    public PreMovimientoDetalleRequestDTO() {}
    
    public PreMovimientoDetalleRequestDTO(String claveGrupoEmpresa, String claveEmpresa, 
                                        Long idPreMovimiento, String claveConcepto, 
                                        BigDecimal importeConcepto, String nota) {
        this.claveGrupoEmpresa = claveGrupoEmpresa;
        this.claveEmpresa = claveEmpresa;
        this.idPreMovimiento = idPreMovimiento;
        this.claveConcepto = claveConcepto;
        this.importeConcepto = importeConcepto;
        this.nota = nota;
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
    
    public Long getIdPreMovimiento() {
        return idPreMovimiento;
    }
    
    public void setIdPreMovimiento(Long idPreMovimiento) {
        this.idPreMovimiento = idPreMovimiento;
    }
    
    public String getClaveConcepto() {
        return claveConcepto;
    }
    
    public void setClaveConcepto(String claveConcepto) {
        this.claveConcepto = claveConcepto;
    }
    
    public BigDecimal getImporteConcepto() {
        return importeConcepto;
    }
    
    public void setImporteConcepto(BigDecimal importeConcepto) {
        this.importeConcepto = importeConcepto;
    }
    
    public String getNota() {
        return nota;
    }
    
    public void setNota(String nota) {
        this.nota = nota;
    }
}