package com.financiero.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "PFIN_PARAMETRO")
public class ParametroSistema {
    
    @EmbeddedId
    private ParametroSistemaId id;
    
    @Column(name = "F_MEDIO")
    private LocalDate fechaMedio;
    
    // Constructors
    public ParametroSistema() {}
    
    public ParametroSistema(ParametroSistemaId id, LocalDate fechaMedio) {
        this.id = id;
        this.fechaMedio = fechaMedio;
    }
    
    // Getters and Setters
    public ParametroSistemaId getId() {
        return id;
    }
    
    public void setId(ParametroSistemaId id) {
        this.id = id;
    }
    
    public LocalDate getFechaMedio() {
        return fechaMedio;
    }
    
    public void setFechaMedio(LocalDate fechaMedio) {
        this.fechaMedio = fechaMedio;
    }
    
    // Domain Methods
    public void actualizarFechaMedio(LocalDate nuevaFecha) {
        this.fechaMedio = nuevaFecha;
    }
    
    public boolean esSistema() {
        return "SYSTEM".equals(this.id.getClaveMedio());
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParametroSistema that = (ParametroSistema) o;
        return Objects.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "ParametroSistema{" +
                "id=" + id +
                ", fechaMedio=" + fechaMedio +
                '}';
    }
}