package com.financiero.client.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Request para crear pre-movimientos
 */
public class PreMovimientoRequest {
    
    @JsonProperty("claveGrupoEmpresa")
    private String claveGrupoEmpresa;
    
    @JsonProperty("claveEmpresa")
    private String claveEmpresa;
    
    @JsonProperty("idPreMovimiento")
    private Long idPreMovimiento;
    
    @JsonProperty("fechaLiquidacion")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaLiquidacion;
    
    @JsonProperty("idCuenta")
    private Long idCuenta;
    
    @JsonProperty("idPrestamo")
    private Long idPrestamo;
    
    @JsonProperty("claveDivisa")
    private String claveDivisa;
    
    @JsonProperty("claveOperacion")
    private String claveOperacion;
    
    @JsonProperty("importeNeto")
    private BigDecimal importeNeto;
    
    @JsonProperty("claveMedio")
    private String claveMedio;
    
    @JsonProperty("claveMercado")
    private String claveMercado;
    
    @JsonProperty("nota")
    private String nota;
    
    @JsonProperty("idGrupo")
    private Long idGrupo;
    
    @JsonProperty("claveUsuario")
    private String claveUsuario;
    
    @JsonProperty("fechaValor")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaValor;
    
    @JsonProperty("numeroPagoAmortizacion")
    private Integer numeroPagoAmortizacion;
    
    // Constructor
    public PreMovimientoRequest() {}
    
    // Builder pattern
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder {
        private String claveGrupoEmpresa;
        private String claveEmpresa;
        private Long idPreMovimiento;
        private LocalDate fechaLiquidacion;
        private Long idCuenta;
        private Long idPrestamo;
        private String claveDivisa = "MXN";
        private String claveOperacion;
        private BigDecimal importeNeto;
        private String claveMedio;
        private String claveMercado;
        private String nota;
        private Long idGrupo;
        private String claveUsuario;
        private LocalDate fechaValor;
        private Integer numeroPagoAmortizacion;
        
        public Builder empresa(String claveGrupoEmpresa, String claveEmpresa) {
            this.claveGrupoEmpresa = claveGrupoEmpresa;
            this.claveEmpresa = claveEmpresa;
            return this;
        }
        
        public Builder idPreMovimiento(Long idPreMovimiento) {
            this.idPreMovimiento = idPreMovimiento;
            return this;
        }
        
        public Builder fechaLiquidacion(LocalDate fechaLiquidacion) {
            this.fechaLiquidacion = fechaLiquidacion;
            return this;
        }
        
        public Builder cuenta(Long idCuenta) {
            this.idCuenta = idCuenta;
            return this;
        }
        
        public Builder prestamo(Long idPrestamo) {
            this.idPrestamo = idPrestamo;
            return this;
        }
        
        public Builder divisa(String claveDivisa) {
            this.claveDivisa = claveDivisa;
            return this;
        }
        
        public Builder operacion(String claveOperacion) {
            this.claveOperacion = claveOperacion;
            return this;
        }
        
        public Builder deposito(BigDecimal importe, String divisa) {
            this.claveOperacion = "DEPOSITO";
            this.claveMercado = "DEPOSITO";
            this.claveMedio = "TRANSFERENCIA";
            this.importeNeto = importe;
            this.claveDivisa = divisa;
            return this;
        }
        
        public Builder retiro(BigDecimal importe, String divisa) {
            this.claveOperacion = "RETIRO";
            this.claveMercado = "DEPOSITO";
            this.claveMedio = "TRANSFERENCIA";
            this.importeNeto = importe;
            this.claveDivisa = divisa;
            return this;
        }
        
        public Builder prestamo(BigDecimal importe, String divisa, Long idPrestamo) {
            this.claveOperacion = "PRESTAMO";
            this.claveMercado = "PRESTAMO";
            this.claveMedio = "DESEMBOLSO";
            this.importeNeto = importe;
            this.claveDivisa = divisa;
            this.idPrestamo = idPrestamo;
            return this;
        }
        
        public Builder importeNeto(BigDecimal importeNeto) {
            this.importeNeto = importeNeto;
            return this;
        }
        
        public Builder medio(String claveMedio) {
            this.claveMedio = claveMedio;
            return this;
        }
        
        public Builder mercado(String claveMercado) {
            this.claveMercado = claveMercado;
            return this;
        }
        
        public Builder nota(String nota) {
            this.nota = nota;
            return this;
        }
        
        public Builder usuario(String claveUsuario) {
            this.claveUsuario = claveUsuario;
            return this;
        }
        
        public Builder fechaValor(LocalDate fechaValor) {
            this.fechaValor = fechaValor;
            return this;
        }
        
        public Builder numeroPago(Integer numeroPagoAmortizacion) {
            this.numeroPagoAmortizacion = numeroPagoAmortizacion;
            return this;
        }
        
        public PreMovimientoRequest build() {
            PreMovimientoRequest request = new PreMovimientoRequest();
            request.claveGrupoEmpresa = this.claveGrupoEmpresa;
            request.claveEmpresa = this.claveEmpresa;
            request.idPreMovimiento = this.idPreMovimiento;
            request.fechaLiquidacion = this.fechaLiquidacion;
            request.idCuenta = this.idCuenta;
            request.idPrestamo = this.idPrestamo;
            request.claveDivisa = this.claveDivisa;
            request.claveOperacion = this.claveOperacion;
            request.importeNeto = this.importeNeto;
            request.claveMedio = this.claveMedio;
            request.claveMercado = this.claveMercado;
            request.nota = this.nota;
            request.idGrupo = this.idGrupo;
            request.claveUsuario = this.claveUsuario;
            request.fechaValor = this.fechaValor;
            request.numeroPagoAmortizacion = this.numeroPagoAmortizacion;
            return request;
        }
    }
    
    // Getters and Setters
    public String getClaveGrupoEmpresa() { return claveGrupoEmpresa; }
    public void setClaveGrupoEmpresa(String claveGrupoEmpresa) { this.claveGrupoEmpresa = claveGrupoEmpresa; }
    
    public String getClaveEmpresa() { return claveEmpresa; }
    public void setClaveEmpresa(String claveEmpresa) { this.claveEmpresa = claveEmpresa; }
    
    public Long getIdPreMovimiento() { return idPreMovimiento; }
    public void setIdPreMovimiento(Long idPreMovimiento) { this.idPreMovimiento = idPreMovimiento; }
    
    public LocalDate getFechaLiquidacion() { return fechaLiquidacion; }
    public void setFechaLiquidacion(LocalDate fechaLiquidacion) { this.fechaLiquidacion = fechaLiquidacion; }
    
    public Long getIdCuenta() { return idCuenta; }
    public void setIdCuenta(Long idCuenta) { this.idCuenta = idCuenta; }
    
    public Long getIdPrestamo() { return idPrestamo; }
    public void setIdPrestamo(Long idPrestamo) { this.idPrestamo = idPrestamo; }
    
    public String getClaveDivisa() { return claveDivisa; }
    public void setClaveDivisa(String claveDivisa) { this.claveDivisa = claveDivisa; }
    
    public String getClaveOperacion() { return claveOperacion; }
    public void setClaveOperacion(String claveOperacion) { this.claveOperacion = claveOperacion; }
    
    public BigDecimal getImporteNeto() { return importeNeto; }
    public void setImporteNeto(BigDecimal importeNeto) { this.importeNeto = importeNeto; }
    
    public String getClaveMedio() { return claveMedio; }
    public void setClaveMedio(String claveMedio) { this.claveMedio = claveMedio; }
    
    public String getClaveMercado() { return claveMercado; }
    public void setClaveMercado(String claveMercado) { this.claveMercado = claveMercado; }
    
    public String getNota() { return nota; }
    public void setNota(String nota) { this.nota = nota; }
    
    public Long getIdGrupo() { return idGrupo; }
    public void setIdGrupo(Long idGrupo) { this.idGrupo = idGrupo; }
    
    public String getClaveUsuario() { return claveUsuario; }
    public void setClaveUsuario(String claveUsuario) { this.claveUsuario = claveUsuario; }
    
    public LocalDate getFechaValor() { return fechaValor; }
    public void setFechaValor(LocalDate fechaValor) { this.fechaValor = fechaValor; }
    
    public Integer getNumeroPagoAmortizacion() { return numeroPagoAmortizacion; }
    public void setNumeroPagoAmortizacion(Integer numeroPagoAmortizacion) { this.numeroPagoAmortizacion = numeroPagoAmortizacion; }
    
    @Override
    public String toString() {
        return "PreMovimientoRequest{" +
               "claveGrupoEmpresa='" + claveGrupoEmpresa + '\''+
               ", claveEmpresa='" + claveEmpresa + '\''+
               ", idPreMovimiento=" + idPreMovimiento +
               ", claveOperacion='" + claveOperacion + '\''+
               ", importeNeto=" + importeNeto +
               ", claveDivisa='" + claveDivisa + '\''+
               '}';
    }
}