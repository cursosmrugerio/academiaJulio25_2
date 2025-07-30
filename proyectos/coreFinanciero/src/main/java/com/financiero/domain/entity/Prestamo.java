package com.financiero.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "SIM_PRESTAMO")
public class Prestamo {
    
    @EmbeddedId
    private PrestamoId id;
    
    @NotNull(message = "El ID del préstamo es obligatorio")
    @Column(name = "ID_PRESTAMO", unique = true)
    private Long idPrestamo;
    
    @Size(max = 5, message = "La clave de divisa no puede exceder 5 caracteres")
    @Column(name = "CVE_DIVISA", length = 5)
    private String claveDivisa;
    
    @DecimalMin(value = "0.0", inclusive = false, message = "El importe del préstamo debe ser mayor a cero")
    @Column(name = "IMP_PRESTAMO", precision = 15, scale = 2)
    private BigDecimal importePrestamo;
    
    // Constructors
    public Prestamo() {}
    
    public Prestamo(PrestamoId id, Long idPrestamo) {
        this.id = id;
        this.idPrestamo = idPrestamo;
    }
    
    // Getters and Setters
    public PrestamoId getId() {
        return id;
    }
    
    public void setId(PrestamoId id) {
        this.id = id;
    }
    
    public Long getIdPrestamo() {
        return idPrestamo;
    }
    
    public void setIdPrestamo(Long idPrestamo) {
        this.idPrestamo = idPrestamo;
    }
    
    public String getClaveDivisa() {
        return claveDivisa;
    }
    
    public void setClaveDivisa(String claveDivisa) {
        this.claveDivisa = claveDivisa;
    }
    
    public BigDecimal getImportePrestamo() {
        return importePrestamo;
    }
    
    public void setImportePrestamo(BigDecimal importePrestamo) {
        this.importePrestamo = importePrestamo;
    }
    
    // Domain Methods
    public boolean esDivisaNacional() {
        return "MXN".equals(this.claveDivisa);
    }
    
    public boolean esDivisaExtranjera() {
        return !esDivisaNacional();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prestamo prestamo = (Prestamo) o;
        return Objects.equals(id, prestamo.id) && Objects.equals(idPrestamo, prestamo.idPrestamo);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, idPrestamo);
    }
    
    @Override
    public String toString() {
        return "Prestamo{" +
                "id=" + id +
                ", idPrestamo=" + idPrestamo +
                ", claveDivisa='" + claveDivisa + '\'' +
                ", importePrestamo=" + importePrestamo +
                '}';
    }
}