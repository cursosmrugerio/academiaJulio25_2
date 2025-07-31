package com.financiero.client.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Request para procesar movimientos (pre-movimientos a virtuales o virtuales a reales)
 */
public class ProcesarMovimientosRequest {
    
    @JsonProperty("claveGrupoEmpresa")
    private String claveGrupoEmpresa;
    
    @JsonProperty("claveEmpresa")
    private String claveEmpresa;
    
    @JsonProperty("fechaProceso")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaProceso;
    
    @JsonProperty("claveUsuario")
    private String claveUsuario;
    
    // Constructors
    public ProcesarMovimientosRequest() {}
    
    public ProcesarMovimientosRequest(String claveGrupoEmpresa, String claveEmpresa, 
                                    LocalDate fechaProceso, String claveUsuario) {
        this.claveGrupoEmpresa = claveGrupoEmpresa;
        this.claveEmpresa = claveEmpresa;
        this.fechaProceso = fechaProceso;
        this.claveUsuario = claveUsuario;
    }
    
    // Builder pattern
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder {
        private String claveGrupoEmpresa;
        private String claveEmpresa;
        private LocalDate fechaProceso;
        private String claveUsuario;
        
        public Builder claveGrupoEmpresa(String claveGrupoEmpresa) {
            this.claveGrupoEmpresa = claveGrupoEmpresa;
            return this;
        }
        
        public Builder claveEmpresa(String claveEmpresa) {
            this.claveEmpresa = claveEmpresa;
            return this;
        }
        
        public Builder empresa(String claveGrupoEmpresa, String claveEmpresa) {
            this.claveGrupoEmpresa = claveGrupoEmpresa;
            this.claveEmpresa = claveEmpresa;
            return this;
        }
        
        public Builder fechaProceso(LocalDate fechaProceso) {
            this.fechaProceso = fechaProceso;
            return this;
        }
        
        public Builder claveUsuario(String claveUsuario) {
            this.claveUsuario = claveUsuario;
            return this;
        }
        
        public ProcesarMovimientosRequest build() {
            return new ProcesarMovimientosRequest(claveGrupoEmpresa, claveEmpresa, fechaProceso, claveUsuario);
        }
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
    
    public LocalDate getFechaProceso() {
        return fechaProceso;
    }
    
    public void setFechaProceso(LocalDate fechaProceso) {
        this.fechaProceso = fechaProceso;
    }
    
    public String getClaveUsuario() {
        return claveUsuario;
    }
    
    public void setClaveUsuario(String claveUsuario) {
        this.claveUsuario = claveUsuario;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcesarMovimientosRequest that = (ProcesarMovimientosRequest) o;
        return Objects.equals(claveGrupoEmpresa, that.claveGrupoEmpresa) &&
               Objects.equals(claveEmpresa, that.claveEmpresa) &&
               Objects.equals(fechaProceso, that.fechaProceso) &&
               Objects.equals(claveUsuario, that.claveUsuario);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(claveGrupoEmpresa, claveEmpresa, fechaProceso, claveUsuario);
    }
    
    @Override
    public String toString() {
        return "ProcesarMovimientosRequest{" +
               "claveGrupoEmpresa='" + claveGrupoEmpresa + '\''+
               ", claveEmpresa='" + claveEmpresa + '\''+
               ", fechaProceso=" + fechaProceso +
               ", claveUsuario='" + claveUsuario + '\''+
               '}';
    }
}