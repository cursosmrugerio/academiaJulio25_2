package com.financiero.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
public class DiaLiquidacionId implements Serializable {
    
    @NotBlank(message = "La clave de grupo empresa es obligatoria")
    @Size(max = 10, message = "La clave de grupo empresa no puede exceder 10 caracteres")
    @Column(name = "CVE_GPO_EMPRESA", length = 10)
    private String claveGrupoEmpresa;
    
    @NotBlank(message = "La clave de empresa es obligatoria")
    @Size(max = 10, message = "La clave de empresa no puede exceder 10 caracteres")
    @Column(name = "CVE_EMPRESA", length = 10)
    private String claveEmpresa;
    
    @NotBlank(message = "La clave de liquidación es obligatoria")
    @Size(max = 10, message = "La clave de liquidación no puede exceder 10 caracteres")
    @Column(name = "CVE_LIQUIDACION", length = 10)
    private String claveLiquidacion;
    
    @NotNull(message = "La fecha de información es obligatoria")
    @Column(name = "F_INFORMACION")
    private LocalDate fechaInformacion;
    
    // Constructors
    public DiaLiquidacionId() {}
    
    public DiaLiquidacionId(String claveGrupoEmpresa, String claveEmpresa, 
                           String claveLiquidacion, LocalDate fechaInformacion) {
        this.claveGrupoEmpresa = claveGrupoEmpresa;
        this.claveEmpresa = claveEmpresa;
        this.claveLiquidacion = claveLiquidacion;
        this.fechaInformacion = fechaInformacion;
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
    
    public String getClaveLiquidacion() {
        return claveLiquidacion;
    }
    
    public void setClaveLiquidacion(String claveLiquidacion) {
        this.claveLiquidacion = claveLiquidacion;
    }
    
    public LocalDate getFechaInformacion() {
        return fechaInformacion;
    }
    
    public void setFechaInformacion(LocalDate fechaInformacion) {
        this.fechaInformacion = fechaInformacion;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiaLiquidacionId that = (DiaLiquidacionId) o;
        return Objects.equals(claveGrupoEmpresa, that.claveGrupoEmpresa) &&
               Objects.equals(claveEmpresa, that.claveEmpresa) &&
               Objects.equals(claveLiquidacion, that.claveLiquidacion) &&
               Objects.equals(fechaInformacion, that.fechaInformacion);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(claveGrupoEmpresa, claveEmpresa, claveLiquidacion, fechaInformacion);
    }
    
    @Override
    public String toString() {
        return "DiaLiquidacionId{" +
                "claveGrupoEmpresa='" + claveGrupoEmpresa + '\'' +
                ", claveEmpresa='" + claveEmpresa + '\'' +
                ", claveLiquidacion='" + claveLiquidacion + '\'' +
                ", fechaInformacion=" + fechaInformacion +
                '}';
    }
}