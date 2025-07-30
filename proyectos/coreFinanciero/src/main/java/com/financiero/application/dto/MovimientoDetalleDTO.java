package com.financiero.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public class MovimientoDetalleDTO {
    
    @NotBlank(message = "La clave de grupo empresa es obligatoria")
    @Size(max = 10)
    private String claveGrupoEmpresa;
    
    @NotBlank(message = "La clave de empresa es obligatoria")
    @Size(max = 10)
    private String claveEmpresa;
    
    @NotNull(message = "El ID de movimiento es obligatorio")
    private Long idMovimiento;
    
    @NotBlank(message = "La clave de concepto es obligatoria")
    @Size(max = 10)
    private String claveConcepto;
    
    private BigDecimal importeConcepto;
    
    @Size(max = 500)
    private String textoNota;
    
    // Constructors
    public MovimientoDetalleDTO() {}
    
    public MovimientoDetalleDTO(String claveGrupoEmpresa, String claveEmpresa, Long idMovimiento, 
                               String claveConcepto, BigDecimal importeConcepto, String textoNota) {
        this.claveGrupoEmpresa = claveGrupoEmpresa;
        this.claveEmpresa = claveEmpresa;
        this.idMovimiento = idMovimiento;
        this.claveConcepto = claveConcepto;
        this.importeConcepto = importeConcepto;
        this.textoNota = textoNota;
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
    
    public Long getIdMovimiento() {
        return idMovimiento;
    }
    
    public void setIdMovimiento(Long idMovimiento) {
        this.idMovimiento = idMovimiento;
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
    
    public String getTextoNota() {
        return textoNota;
    }
    
    public void setTextoNota(String textoNota) {
        this.textoNota = textoNota;
    }
}