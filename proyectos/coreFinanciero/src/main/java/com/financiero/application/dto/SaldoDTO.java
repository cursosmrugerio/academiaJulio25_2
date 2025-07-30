package com.financiero.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

public class SaldoDTO {
    
    @NotBlank(message = "La clave de grupo empresa es obligatoria")
    @Size(max = 10)
    private String claveGrupoEmpresa;
    
    @NotBlank(message = "La clave de empresa es obligatoria")
    @Size(max = 10)
    private String claveEmpresa;
    
    @NotNull(message = "La fecha de foto es obligatoria")
    private LocalDate fechaFoto;
    
    @NotNull(message = "El ID de cuenta es obligatorio")
    private Long idCuenta;
    
    @NotBlank(message = "La clave de divisa es obligatoria")
    @Size(max = 5)
    private String claveDivisa;
    
    @NotNull(message = "El saldo efectivo es obligatorio")
    private BigDecimal saldoEfectivo;
    
    // Constructors
    public SaldoDTO() {}
    
    public SaldoDTO(String claveGrupoEmpresa, String claveEmpresa, LocalDate fechaFoto,
                    Long idCuenta, String claveDivisa, BigDecimal saldoEfectivo) {
        this.claveGrupoEmpresa = claveGrupoEmpresa;
        this.claveEmpresa = claveEmpresa;
        this.fechaFoto = fechaFoto;
        this.idCuenta = idCuenta;
        this.claveDivisa = claveDivisa;
        this.saldoEfectivo = saldoEfectivo;
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
    
    public LocalDate getFechaFoto() {
        return fechaFoto;
    }
    
    public void setFechaFoto(LocalDate fechaFoto) {
        this.fechaFoto = fechaFoto;
    }
    
    public Long getIdCuenta() {
        return idCuenta;
    }
    
    public void setIdCuenta(Long idCuenta) {
        this.idCuenta = idCuenta;
    }
    
    public String getClaveDivisa() {
        return claveDivisa;
    }
    
    public void setClaveDivisa(String claveDivisa) {
        this.claveDivisa = claveDivisa;
    }
    
    public BigDecimal getSaldoEfectivo() {
        return saldoEfectivo;
    }
    
    public void setSaldoEfectivo(BigDecimal saldoEfectivo) {
        this.saldoEfectivo = saldoEfectivo;
    }
}