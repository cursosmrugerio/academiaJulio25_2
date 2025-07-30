package com.financiero.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "PFIN_SALDO")
public class Saldo {
    
    @EmbeddedId
    private SaldoId id;
    
    @NotNull(message = "El saldo efectivo es obligatorio")
    @Column(name = "SDO_EFECTIVO", precision = 15, scale = 2)
    private BigDecimal saldoEfectivo = BigDecimal.ZERO;
    
    // Constructors
    public Saldo() {}
    
    public Saldo(SaldoId id, BigDecimal saldoEfectivo) {
        this.id = id;
        this.saldoEfectivo = saldoEfectivo;
    }
    
    // Constructor de conveniencia
    public Saldo(String claveGrupoEmpresa, String claveEmpresa, java.time.LocalDate fechaFoto,
                 Long idCuenta, String claveDivisa, BigDecimal saldoEfectivo) {
        this.id = new SaldoId(claveGrupoEmpresa, claveEmpresa, fechaFoto, idCuenta, claveDivisa);
        this.saldoEfectivo = saldoEfectivo;
    }
    
    // Getters and Setters
    public SaldoId getId() {
        return id;
    }
    
    public void setId(SaldoId id) {
        this.id = id;
    }
    
    public BigDecimal getSaldoEfectivo() {
        return saldoEfectivo;
    }
    
    public void setSaldoEfectivo(BigDecimal saldoEfectivo) {
        this.saldoEfectivo = saldoEfectivo;
    }
    
    // Domain Methods
    public void incrementarSaldo(BigDecimal importe) {
        if (importe != null) {
            this.saldoEfectivo = this.saldoEfectivo.add(importe);
        }
    }
    
    public void decrementarSaldo(BigDecimal importe) {
        if (importe != null) {
            this.saldoEfectivo = this.saldoEfectivo.subtract(importe);
        }
    }
    
    public void afectarSaldo(BigDecimal importe, int factor) {
        if (importe != null && factor != 0) {
            BigDecimal afectacion = importe.multiply(BigDecimal.valueOf(factor));
            this.saldoEfectivo = this.saldoEfectivo.add(afectacion);
        }
    }
    
    public boolean esSaldoPositivo() {
        return this.saldoEfectivo.compareTo(BigDecimal.ZERO) > 0;
    }
    
    public boolean esSaldoNegativo() {
        return this.saldoEfectivo.compareTo(BigDecimal.ZERO) < 0;
    }
    
    public boolean esSaldoCero() {
        return this.saldoEfectivo.compareTo(BigDecimal.ZERO) == 0;
    }
    
    public boolean tieneSaldoSuficiente(BigDecimal importeRequerido) {
        if (importeRequerido == null) {
            return true;
        }
        return this.saldoEfectivo.compareTo(importeRequerido) >= 0;
    }
    
    public BigDecimal getSaldoAbsoluto() {
        return this.saldoEfectivo.abs();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Saldo saldo = (Saldo) o;
        return Objects.equals(id, saldo.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Saldo{" +
                "id=" + id +
                ", saldoEfectivo=" + saldoEfectivo +
                '}';
    }
}