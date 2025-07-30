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
public class DiaFestivoId implements Serializable {
    
    @NotBlank(message = "La clave de grupo empresa es obligatoria")
    @Size(max = 10, message = "La clave de grupo empresa no puede exceder 10 caracteres")
    @Column(name = "CVE_GPO_EMPRESA", length = 10)
    private String claveGrupoEmpresa;
    
    @NotBlank(message = "La clave de empresa es obligatoria")
    @Size(max = 10, message = "La clave de empresa no puede exceder 10 caracteres")
    @Column(name = "CVE_EMPRESA", length = 10)
    private String claveEmpresa;
    
    @NotBlank(message = "La clave del país es obligatoria")
    @Size(max = 5, message = "La clave del país no puede exceder 5 caracteres")
    @Column(name = "CVE_PAIS", length = 5)
    private String clavePais;
    
    @NotNull(message = "La fecha del día festivo es obligatoria")
    @Column(name = "F_DIA_FESTIVO")
    private LocalDate fechaDiaFestivo;
    
    // Constructors
    public DiaFestivoId() {}
    
    public DiaFestivoId(String claveGrupoEmpresa, String claveEmpresa, 
                       String clavePais, LocalDate fechaDiaFestivo) {
        this.claveGrupoEmpresa = claveGrupoEmpresa;
        this.claveEmpresa = claveEmpresa;
        this.clavePais = clavePais;
        this.fechaDiaFestivo = fechaDiaFestivo;
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
    
    public String getClavePais() {
        return clavePais;
    }
    
    public void setClavePais(String clavePais) {
        this.clavePais = clavePais;
    }
    
    public LocalDate getFechaDiaFestivo() {
        return fechaDiaFestivo;
    }
    
    public void setFechaDiaFestivo(LocalDate fechaDiaFestivo) {
        this.fechaDiaFestivo = fechaDiaFestivo;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiaFestivoId that = (DiaFestivoId) o;
        return Objects.equals(claveGrupoEmpresa, that.claveGrupoEmpresa) &&
               Objects.equals(claveEmpresa, that.claveEmpresa) &&
               Objects.equals(clavePais, that.clavePais) &&
               Objects.equals(fechaDiaFestivo, that.fechaDiaFestivo);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(claveGrupoEmpresa, claveEmpresa, clavePais, fechaDiaFestivo);
    }
    
    @Override
    public String toString() {
        return "DiaFestivoId{" +
                "claveGrupoEmpresa='" + claveGrupoEmpresa + '\'' +
                ", claveEmpresa='" + claveEmpresa + '\'' +
                ", clavePais='" + clavePais + '\'' +
                ", fechaDiaFestivo=" + fechaDiaFestivo +
                '}';
    }
}