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
public class SaldoId implements Serializable {
    
    @NotBlank(message = "La clave de grupo empresa es obligatoria")
    @Size(max = 10, message = "La clave de grupo empresa no puede exceder 10 caracteres")
    @Column(name = "CVE_GPO_EMPRESA", length = 10)
    private String claveGrupoEmpresa;
    
    @NotBlank(message = "La clave de empresa es obligatoria")
    @Size(max = 10, message = "La clave de empresa no puede exceder 10 caracteres")
    @Column(name = "CVE_EMPRESA", length = 10)
    private String claveEmpresa;
    
    @NotNull(message = "La fecha de foto es obligatoria")
    @Column(name = "F_FOTO")
    private LocalDate fechaFoto;
    
    @NotNull(message = "El ID de cuenta es obligatorio")
    @Column(name = "ID_CUENTA")
    private Long idCuenta;
    
    @NotBlank(message = "La clave de divisa es obligatoria")
    @Size(max = 5, message = "La clave de divisa no puede exceder 5 caracteres")
    @Column(name = "CVE_DIVISA", length = 5)
    private String claveDivisa;
    
    // Constructors
    public SaldoId() {}
    
    public SaldoId(String claveGrupoEmpresa, String claveEmpresa, LocalDate fechaFoto, 
                   Long idCuenta, String claveDivisa) {
        this.claveGrupoEmpresa = claveGrupoEmpresa;
        this.claveEmpresa = claveEmpresa;
        this.fechaFoto = fechaFoto;
        this.idCuenta = idCuenta;
        this.claveDivisa = claveDivisa;
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
    
    public LocalDate getFechaFoto() {
        return fechaFoto;
    }
    
    public void setFechaFoto(LocalDate fechaFoto) {
        this.fechaFoto = fechaFoto;
    }
    
    public Long getIdCuenta() {
        return idCuenta;
    }
    
    public void setIdCuenta(Long idCuenta) {
        this.idCuenta = idCuenta;
    }
    
    public String getClaveDivisa() {
        return claveDivisa;
    }
    
    public void setClaveDivisa(String claveDivisa) {
        this.claveDivisa = claveDivisa;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaldoId saldoId = (SaldoId) o;
        return Objects.equals(claveGrupoEmpresa, saldoId.claveGrupoEmpresa) &&
               Objects.equals(claveEmpresa, saldoId.claveEmpresa) &&
               Objects.equals(fechaFoto, saldoId.fechaFoto) &&
               Objects.equals(idCuenta, saldoId.idCuenta) &&
               Objects.equals(claveDivisa, saldoId.claveDivisa);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(claveGrupoEmpresa, claveEmpresa, fechaFoto, idCuenta, claveDivisa);
    }
    
    @Override
    public String toString() {
        return "SaldoId{" +
                "claveGrupoEmpresa='" + claveGrupoEmpresa + '\'' +
                ", claveEmpresa='" + claveEmpresa + '\'' +
                ", fechaFoto=" + fechaFoto +
                ", idCuenta=" + idCuenta +
                ", claveDivisa='" + claveDivisa + '\'' +
                '}';
    }
}