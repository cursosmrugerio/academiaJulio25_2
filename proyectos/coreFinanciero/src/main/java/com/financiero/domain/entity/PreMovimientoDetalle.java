package com.financiero.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "PFIN_PRE_MOVIMIENTO_DET")
public class PreMovimientoDetalle {
    
    @EmbeddedId
    private PreMovimientoDetalleId id;
    
    @NotNull(message = "El importe del concepto es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El importe del concepto debe ser mayor a cero")
    @Column(name = "IMP_CONCEPTO", precision = 15, scale = 2)
    private BigDecimal importeConcepto;
    
    @Size(max = 500, message = "La nota no puede exceder 500 caracteres")
    @Column(name = "TX_NOTA", length = 500)
    private String nota;
    
    // Constructors
    public PreMovimientoDetalle() {}
    
    public PreMovimientoDetalle(PreMovimientoDetalleId id, BigDecimal importeConcepto, String nota) {
        this.id = id;
        this.importeConcepto = importeConcepto;
        this.nota = nota;
    }
    
    // Getters and Setters
    public PreMovimientoDetalleId getId() {
        return id;
    }
    
    public void setId(PreMovimientoDetalleId id) {
        this.id = id;
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
    
    // Domain Methods
    public boolean esConceptoInteres() {
        return "INTERES".equals(this.id.getClaveConcepto());
    }
    
    public boolean esConceptoComision() {
        return "COMISION".equals(this.id.getClaveConcepto());
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PreMovimientoDetalle that = (PreMovimientoDetalle) o;
        return Objects.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "PreMovimientoDetalle{" +
                "id=" + id +
                ", importeConcepto=" + importeConcepto +
                ", nota='" + nota + '\'' +
                '}';
    }
}