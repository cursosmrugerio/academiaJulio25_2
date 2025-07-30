package com.financiero.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PreMovimientoId implements Serializable {
    
    @NotBlank(message = "La clave de grupo empresa es obligatoria")
    @Size(max = 10, message = "La clave de grupo empresa no puede exceder 10 caracteres")
    @Column(name = "CVE_GPO_EMPRESA", length = 10)
    private String claveGrupoEmpresa;
    
    @NotBlank(message = "La clave de empresa es obligatoria")
    @Size(max = 10, message = "La clave de empresa no puede exceder 10 caracteres")
    @Column(name = "CVE_EMPRESA", length = 10)
    private String claveEmpresa;
    
    @NotNull(message = "El ID del pre-movimiento es obligatorio")
    @Column(name = "ID_PRE_MOVIMIENTO")
    private Long idPreMovimiento;
    
    // Constructors
    public PreMovimientoId() {}
    
    public PreMovimientoId(String claveGrupoEmpresa, String claveEmpresa, Long idPreMovimiento) {
        this.claveGrupoEmpresa = claveGrupoEmpresa;
        this.claveEmpresa = claveEmpresa;
        this.idPreMovimiento = idPreMovimiento;
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
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PreMovimientoId that = (PreMovimientoId) o;
        return Objects.equals(claveGrupoEmpresa, that.claveGrupoEmpresa) &&
               Objects.equals(claveEmpresa, that.claveEmpresa) &&
               Objects.equals(idPreMovimiento, that.idPreMovimiento);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(claveGrupoEmpresa, claveEmpresa, idPreMovimiento);
    }
    
    @Override
    public String toString() {
        return "PreMovimientoId{" +
                "claveGrupoEmpresa='" + claveGrupoEmpresa + '\'' +
                ", claveEmpresa='" + claveEmpresa + '\'' +
                ", idPreMovimiento=" + idPreMovimiento +
                '}';
    }
}