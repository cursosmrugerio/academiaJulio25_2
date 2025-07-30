package com.financiero.domain.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "PFIN_DIA_LIQUIDACION")
public class DiaLiquidacion {
    
    @EmbeddedId
    private DiaLiquidacionId id;
    
    @Column(name = "F_LIQUIDACION")
    private LocalDate fechaLiquidacion;
    
    // Constructors
    public DiaLiquidacion() {}
    
    public DiaLiquidacion(DiaLiquidacionId id, LocalDate fechaLiquidacion) {
        this.id = id;
        this.fechaLiquidacion = fechaLiquidacion;
    }
    
    // Getters and Setters
    public DiaLiquidacionId getId() {
        return id;
    }
    
    public void setId(DiaLiquidacionId id) {
        this.id = id;
    }
    
    public LocalDate getFechaLiquidacion() {
        return fechaLiquidacion;
    }
    
    public void setFechaLiquidacion(LocalDate fechaLiquidacion) {
        this.fechaLiquidacion = fechaLiquidacion;
    }
    
    // Domain Methods
    public boolean esTipoT() {
        return this.id.getClaveLiquidacion().startsWith("T+");
    }
    
    public boolean esFechaAnterior() {
        return "AYER".equals(this.id.getClaveLiquidacion());
    }
    
    public boolean esFinDeMes() {
        return this.id.getClaveLiquidacion().startsWith("FM");
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiaLiquidacion that = (DiaLiquidacion) o;
        return Objects.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "DiaLiquidacion{" +
                "id=" + id +
                ", fechaLiquidacion=" + fechaLiquidacion +
                '}';
    }
}