package com.financiero.domain.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "PFIN_DIA_FESTIVO")
public class DiaFestivo {
    
    @EmbeddedId
    private DiaFestivoId id;
    
    // Constructors
    public DiaFestivo() {}
    
    public DiaFestivo(DiaFestivoId id) {
        this.id = id;
    }
    
    // Getters and Setters
    public DiaFestivoId getId() {
        return id;
    }
    
    public void setId(DiaFestivoId id) {
        this.id = id;
    }
    
    // Domain Methods
    public LocalDate getFechaDiaFestivo() {
        return this.id.getFechaDiaFestivo();
    }
    
    public boolean esFechaFestiva(LocalDate fecha) {
        return this.id.getFechaDiaFestivo().equals(fecha);
    }
    
    public boolean esPaisMexico() {
        return "MX".equals(this.id.getClavePais());
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiaFestivo that = (DiaFestivo) o;
        return Objects.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "DiaFestivo{" +
                "id=" + id +
                '}';
    }
}