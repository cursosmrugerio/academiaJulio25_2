package com.financiero.application.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class PreMovimientoRequestDTO {
    
    @NotBlank(message = "La clave de grupo empresa es obligatoria")
    @Size(max = 10, message = "La clave de grupo empresa no puede exceder 10 caracteres")
    private String claveGrupoEmpresa;
    
    @NotBlank(message = "La clave de empresa es obligatoria")
    @Size(max = 10, message = "La clave de empresa no puede exceder 10 caracteres")
    private String claveEmpresa;
    
    @NotNull(message = "El ID del pre-movimiento es obligatorio")
    private Long idPreMovimiento;
    
    @NotNull(message = "La fecha de liquidación es obligatoria")
    @Future(message = "La fecha de liquidación debe ser futura")
    private LocalDate fechaLiquidacion;
    
    private Long idCuenta;
    
    private Long idPrestamo;
    
    @Size(max = 5, message = "La clave de divisa no puede exceder 5 caracteres")
    private String claveDivisa;
    
    @NotBlank(message = "La clave de operación es obligatoria")
    @Size(max = 10, message = "La clave de operación no puede exceder 10 caracteres")
    private String claveOperacion;
    
    @NotNull(message = "El importe neto es obligatorio")
    @DecimalMin(value = "0.01", message = "El importe neto debe ser mayor a cero")
    @Digits(integer = 13, fraction = 2, message = "El importe neto debe tener máximo 13 enteros y 2 decimales")
    private BigDecimal importeNeto;
    
    @NotBlank(message = "La clave de medio es obligatoria")
    @Size(max = 20, message = "La clave de medio no puede exceder 20 caracteres")
    private String claveMedio;
    
    @NotBlank(message = "La clave de mercado es obligatoria")
    @Size(max = 20, message = "La clave de mercado no puede exceder 20 caracteres")
    private String claveMercado;
    
    @Size(max = 500, message = "La nota no puede exceder 500 caracteres")
    private String nota;
    
    private Long idGrupo;
    
    @NotBlank(message = "La clave de usuario es obligatoria")
    @Size(max = 20, message = "La clave de usuario no puede exceder 20 caracteres")
    private String claveUsuario;
    
    private LocalDate fechaValor;
    
    private Integer numeroPagoAmortizacion;
    
    // Constructors
    public PreMovimientoRequestDTO() {}
    
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
    
    public Long getIdPreMovimiento() {
        return idPreMovimiento;
    }
    
    public void setIdPreMovimiento(Long idPreMovimiento) {
        this.idPreMovimiento = idPreMovimiento;
    }
    
    public LocalDate getFechaLiquidacion() {
        return fechaLiquidacion;
    }
    
    public void setFechaLiquidacion(LocalDate fechaLiquidacion) {
        this.fechaLiquidacion = fechaLiquidacion;
    }
    
    public Long getIdCuenta() {
        return idCuenta;
    }
    
    public void setIdCuenta(Long idCuenta) {
        this.idCuenta = idCuenta;
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
    
    public String getClaveOperacion() {
        return claveOperacion;
    }
    
    public void setClaveOperacion(String claveOperacion) {
        this.claveOperacion = claveOperacion;
    }
    
    public BigDecimal getImporteNeto() {
        return importeNeto;
    }
    
    public void setImporteNeto(BigDecimal importeNeto) {
        this.importeNeto = importeNeto;
    }
    
    public String getClaveMedio() {
        return claveMedio;
    }
    
    public void setClaveMedio(String claveMedio) {
        this.claveMedio = claveMedio;
    }
    
    public String getClaveMercado() {
        return claveMercado;
    }
    
    public void setClaveMercado(String claveMercado) {
        this.claveMercado = claveMercado;
    }
    
    public String getNota() {
        return nota;
    }
    
    public void setNota(String nota) {
        this.nota = nota;
    }
    
    public Long getIdGrupo() {
        return idGrupo;
    }
    
    public void setIdGrupo(Long idGrupo) {
        this.idGrupo = idGrupo;
    }
    
    public String getClaveUsuario() {
        return claveUsuario;
    }
    
    public void setClaveUsuario(String claveUsuario) {
        this.claveUsuario = claveUsuario;
    }
    
    public LocalDate getFechaValor() {
        return fechaValor;
    }
    
    public void setFechaValor(LocalDate fechaValor) {
        this.fechaValor = fechaValor;
    }
    
    public Integer getNumeroPagoAmortizacion() {
        return numeroPagoAmortizacion;
    }
    
    public void setNumeroPagoAmortizacion(Integer numeroPagoAmortizacion) {
        this.numeroPagoAmortizacion = numeroPagoAmortizacion;
    }
}