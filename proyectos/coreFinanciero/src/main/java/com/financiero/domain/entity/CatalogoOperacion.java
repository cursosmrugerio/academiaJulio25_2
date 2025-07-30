package com.financiero.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.Objects;

@Entity
@Table(name = "PFIN_CAT_OPERACION")
public class CatalogoOperacion {
    
    @EmbeddedId
    private CatalogoOperacionId id;
    
    @Size(max = 100, message = "La descripción no puede exceder 100 caracteres")
    @Column(name = "TX_DESCRIPCION", length = 100)
    private String descripcion;
    
    @NotNull(message = "La clave de afectación de saldo es obligatoria")
    @Size(max = 1, message = "La clave de afectación de saldo debe ser de 1 carácter")
    @Pattern(regexp = "[IDN]", message = "La clave de afectación de saldo debe ser I (Incrementa), D (Decrementa) o N (No afecta)")
    @Column(name = "CVE_AFECTA_SALDO", length = 1)
    private String claveAfectaSaldo;
    
    @Size(max = 1, message = "El estatus debe ser de 1 carácter")
    @Pattern(regexp = "[AS]", message = "El estatus debe ser A (Activo) o S (Suspendido)")
    @Column(name = "ST_ESTATUS", length = 1)
    private String estatus = "A";
    
    @Size(max = 200, message = "Las observaciones no pueden exceder 200 caracteres")
    @Column(name = "TX_OBSERVACIONES", length = 200)
    private String observaciones;
    
    // Constructors
    public CatalogoOperacion() {}
    
    public CatalogoOperacion(CatalogoOperacionId id, String descripcion, String claveAfectaSaldo) {
        this.id = id;
        this.descripcion = descripcion;
        this.claveAfectaSaldo = claveAfectaSaldo;
    }
    
    // Getters and Setters
    public CatalogoOperacionId getId() {
        return id;
    }
    
    public void setId(CatalogoOperacionId id) {
        this.id = id;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getClaveAfectaSaldo() {
        return claveAfectaSaldo;
    }
    
    public void setClaveAfectaSaldo(String claveAfectaSaldo) {
        this.claveAfectaSaldo = claveAfectaSaldo;
    }
    
    public String getEstatus() {
        return estatus;
    }
    
    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }
    
    public String getObservaciones() {
        return observaciones;
    }
    
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    // Domain Methods
    public boolean incrementaSaldo() {
        return "I".equals(this.claveAfectaSaldo);
    }
    
    public boolean decrementaSaldo() {
        return "D".equals(this.claveAfectaSaldo);
    }
    
    public boolean noAfectaSaldo() {
        return "N".equals(this.claveAfectaSaldo);
    }
    
    public boolean estaActiva() {
        return "A".equals(this.estatus);
    }
    
    public boolean estaSuspendida() {
        return "S".equals(this.estatus);
    }
    
    public void activar() {
        this.estatus = "A";
    }
    
    public void suspender() {
        this.estatus = "S";
    }
    
    /**
     * Calcula el factor de afectación para el saldo
     * @param esCancelacion si es una cancelación (invierte el efecto)
     * @return 1 para incrementar, -1 para decrementar, 0 para no afectar
     */
    public int calcularFactorAfectacion(boolean esCancelacion) {
        if (noAfectaSaldo()) {
            return 0;
        }
        
        int factor = incrementaSaldo() ? 1 : -1;
        
        // Si es cancelación, invertir el efecto
        if (esCancelacion) {
            factor *= -1;
        }
        
        return factor;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CatalogoOperacion that = (CatalogoOperacion) o;
        return Objects.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "CatalogoOperacion{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", claveAfectaSaldo='" + claveAfectaSaldo + '\'' +
                ", estatus='" + estatus + '\'' +
                '}';
    }
}