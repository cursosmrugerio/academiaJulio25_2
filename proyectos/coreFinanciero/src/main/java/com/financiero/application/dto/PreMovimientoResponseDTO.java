package com.financiero.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class PreMovimientoResponseDTO {
    
    private String claveGrupoEmpresa;
    private String claveEmpresa;
    private Long idPreMovimiento;
    private LocalDate fechaOperacion;
    private LocalDate fechaLiquidacion;
    private LocalDate fechaAplicacion;
    private Long idCuenta;
    private Long idPrestamo;
    private String claveDivisa;
    private String claveOperacion;
    private BigDecimal importeNeto;
    private BigDecimal precioOperacion;
    private BigDecimal tipoCambio;
    private String claveMedio;
    private String claveMercado;
    private String nota;
    private Long idGrupo;
    private Long idMovimiento;
    private String claveUsuario;
    private String situacionPreMovimiento;
    private Integer numeroPagoAmortizacion;
    private List<PreMovimientoDetalleResponseDTO> detalles;
    
    // Constructors
    public PreMovimientoResponseDTO() {}
    
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
    
    public LocalDate getFechaOperacion() {
        return fechaOperacion;
    }
    
    public void setFechaOperacion(LocalDate fechaOperacion) {
        this.fechaOperacion = fechaOperacion;
    }
    
    public LocalDate getFechaLiquidacion() {
        return fechaLiquidacion;
    }
    
    public void setFechaLiquidacion(LocalDate fechaLiquidacion) {
        this.fechaLiquidacion = fechaLiquidacion;
    }
    
    public LocalDate getFechaAplicacion() {
        return fechaAplicacion;
    }
    
    public void setFechaAplicacion(LocalDate fechaAplicacion) {
        this.fechaAplicacion = fechaAplicacion;
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
    
    public BigDecimal getPrecioOperacion() {
        return precioOperacion;
    }
    
    public void setPrecioOperacion(BigDecimal precioOperacion) {
        this.precioOperacion = precioOperacion;
    }
    
    public BigDecimal getTipoCambio() {
        return tipoCambio;
    }
    
    public void setTipoCambio(BigDecimal tipoCambio) {
        this.tipoCambio = tipoCambio;
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
    
    public Long getIdMovimiento() {
        return idMovimiento;
    }
    
    public void setIdMovimiento(Long idMovimiento) {
        this.idMovimiento = idMovimiento;
    }
    
    public String getClaveUsuario() {
        return claveUsuario;
    }
    
    public void setClaveUsuario(String claveUsuario) {
        this.claveUsuario = claveUsuario;
    }
    
    public String getSituacionPreMovimiento() {
        return situacionPreMovimiento;
    }
    
    public void setSituacionPreMovimiento(String situacionPreMovimiento) {
        this.situacionPreMovimiento = situacionPreMovimiento;
    }
    
    public Integer getNumeroPagoAmortizacion() {
        return numeroPagoAmortizacion;
    }
    
    public void setNumeroPagoAmortizacion(Integer numeroPagoAmortizacion) {
        this.numeroPagoAmortizacion = numeroPagoAmortizacion;
    }
    
    public List<PreMovimientoDetalleResponseDTO> getDetalles() {
        return detalles;
    }
    
    public void setDetalles(List<PreMovimientoDetalleResponseDTO> detalles) {
        this.detalles = detalles;
    }
}