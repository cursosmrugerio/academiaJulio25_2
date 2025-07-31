package com.financiero.client.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Response para consultas de saldos
 */
public class SaldoResponse {
    
    @JsonProperty("claveGrupoEmpresa")
    private String claveGrupoEmpresa;
    
    @JsonProperty("claveEmpresa")
    private String claveEmpresa;
    
    @JsonProperty("fechaFoto")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaFoto;
    
    @JsonProperty("idCuenta")
    private Long idCuenta;
    
    @JsonProperty("claveDivisa")
    private String claveDivisa;
    
    @JsonProperty("saldoEfectivo")
    private BigDecimal saldoEfectivo;
    
    // Constructor
    public SaldoResponse() {}
    
    // Getters and Setters
    public String getClaveGrupoEmpresa() { return claveGrupoEmpresa; }
    public void setClaveGrupoEmpresa(String claveGrupoEmpresa) { this.claveGrupoEmpresa = claveGrupoEmpresa; }
    
    public String getClaveEmpresa() { return claveEmpresa; }
    public void setClaveEmpresa(String claveEmpresa) { this.claveEmpresa = claveEmpresa; }
    
    public LocalDate getFechaFoto() { return fechaFoto; }
    public void setFechaFoto(LocalDate fechaFoto) { this.fechaFoto = fechaFoto; }
    
    public Long getIdCuenta() { return idCuenta; }
    public void setIdCuenta(Long idCuenta) { this.idCuenta = idCuenta; }
    
    public String getClaveDivisa() { return claveDivisa; }
    public void setClaveDivisa(String claveDivisa) { this.claveDivisa = claveDivisa; }
    
    public BigDecimal getSaldoEfectivo() { return saldoEfectivo; }
    public void setSaldoEfectivo(BigDecimal saldoEfectivo) { this.saldoEfectivo = saldoEfectivo; }
    
    @Override
    public String toString() {
        return "SaldoResponse{" +
               "cuenta=" + idCuenta +
               ", divisa='" + claveDivisa + '\''+
               ", saldo=" + saldoEfectivo +
               ", fecha=" + fechaFoto +
               '}';
    }
}