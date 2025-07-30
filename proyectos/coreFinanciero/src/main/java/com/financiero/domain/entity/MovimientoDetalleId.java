package com.financiero.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MovimientoDetalleId implements Serializable {
    
    @NotBlank(message = "La clave de grupo empresa es obligatoria")
    @Size(max = 10, message = "La clave de grupo empresa no puede exceder 10 caracteres")
    @Column(name = "CVE_GPO_EMPRESA", length = 10)
    private String claveGrupoEmpresa;
    
    @NotBlank(message = "La clave de empresa es obligatoria")
    @Size(max = 10, message = "La clave de empresa no puede exceder 10 caracteres")
    @Column(name = "CVE_EMPRESA", length = 10)
    private String claveEmpresa;
    
    @NotNull(message = "El ID del movimiento es obligatorio")
    @Column(name = "ID_MOVIMIENTO")
    private Long idMovimiento;
    
    @NotBlank(message = "La clave del concepto es obligatoria")
    @Size(max = 10, message = "La clave del concepto no puede exceder 10 caracteres")
    @Column(name = "CVE_CONCEPTO", length = 10)
    private String claveConcepto;
    
    // Constructors
    public MovimientoDetalleId() {}
    
    public MovimientoDetalleId(String claveGrupoEmpresa, String claveEmpresa, 
                              Long idMovimiento, String claveConcepto) {
        this.claveGrupoEmpresa = claveGrupoEmpresa;
        this.claveEmpresa = claveEmpresa;
        this.idMovimiento = idMovimiento;
        this.claveConcepto = claveConcepto;
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
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovimientoDetalleId that = (MovimientoDetalleId) o;
        return Objects.equals(claveGrupoEmpresa, that.claveGrupoEmpresa) &&
               Objects.equals(claveEmpresa, that.claveEmpresa) &&
               Objects.equals(idMovimiento, that.idMovimiento) &&
               Objects.equals(claveConcepto, that.claveConcepto);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(claveGrupoEmpresa, claveEmpresa, idMovimiento, claveConcepto);
    }
    
    @Override
    public String toString() {
        return "MovimientoDetalleId{" +
                "claveGrupoEmpresa='" + claveGrupoEmpresa + '\'' +
                ", claveEmpresa='" + claveEmpresa + '\'' +
                ", idMovimiento=" + idMovimiento +
                ", claveConcepto='" + claveConcepto + '\'' +
                '}';
    }
}