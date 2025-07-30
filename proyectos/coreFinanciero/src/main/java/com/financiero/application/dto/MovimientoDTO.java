package com.financiero.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class MovimientoDTO {
    
    @NotBlank(message = "La clave de grupo empresa es obligatoria")
    @Size(max = 10)
    private String claveGrupoEmpresa;
    
    @NotBlank(message = "La clave de empresa es obligatoria")
    @Size(max = 10)
    private String claveEmpresa;
    
    @NotNull(message = "El ID de movimiento es obligatorio")
    private Long idMovimiento;
    
    private Long idCuenta;
    
    @Size(max = 5)
    private String claveDivisa;
    
    private LocalDate fechaOperacion;
    private LocalDate fechaLiquidacion;
    private LocalDate fechaAplicacion;
    
    @Size(max = 10)
    private String claveOperacion;
    
    private BigDecimal importeNeto;
    private BigDecimal precioOperacion;
    private BigDecimal tipoCambio;
    private Long idReferencia;
    private Long idPrestamo;
    
    @Size(max = 20)
    private String claveMercado;
    
    @Size(max = 20)
    private String claveMedio;
    
    @Size(max = 500)
    private String textoNota;
    
    @Size(max = 100)
    private String textoReferencia;
    
    private Long idGrupo;
    private Long idPreMovimiento;
    
    @NotBlank(message = "La situación del movimiento es obligatoria")
    @Size(max = 5)
    private String situacionMovimiento;
    
    private LocalDateTime fechaHoraRegistro;
    private LocalDateTime fechaHoraActivacion;
    
    @Size(max = 45)
    private String logIpAddress;
    
    @Size(max = 100)
    private String logOsUser;
    
    @Size(max = 100)
    private String logHost;
    
    @Size(max = 20)
    private String claveUsuario;
    
    @Size(max = 20)
    private String claveUsuarioCancela;
    
    private Integer numeroPagoAmortizacion;
    
    private List<MovimientoDetalleDTO> detalles;
    
    // Constructors
    public MovimientoDTO() {}
    
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
    
    public Long getIdMovimiento() {
        return idMovimiento;
    }
    
    public void setIdMovimiento(Long idMovimiento) {
        this.idMovimiento = idMovimiento;
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
    
    public Long getIdReferencia() {
        return idReferencia;
    }
    
    public void setIdReferencia(Long idReferencia) {
        this.idReferencia = idReferencia;
    }
    
    public Long getIdPrestamo() {
        return idPrestamo;
    }
    
    public void setIdPrestamo(Long idPrestamo) {
        this.idPrestamo = idPrestamo;
    }
    
    public String getClaveMercado() {
        return claveMercado;
    }
    
    public void setClaveMercado(String claveMercado) {
        this.claveMercado = claveMercado;
    }
    
    public String getClaveMedio() {
        return claveMedio;
    }
    
    public void setClaveMedio(String claveMedio) {
        this.claveMedio = claveMedio;
    }
    
    public String getTextoNota() {
        return textoNota;
    }
    
    public void setTextoNota(String textoNota) {
        this.textoNota = textoNota;
    }
    
    public String getTextoReferencia() {
        return textoReferencia;
    }
    
    public void setTextoReferencia(String textoReferencia) {
        this.textoReferencia = textoReferencia;
    }
    
    public Long getIdGrupo() {
        return idGrupo;
    }
    
    public void setIdGrupo(Long idGrupo) {
        this.idGrupo = idGrupo;
    }
    
    public Long getIdPreMovimiento() {
        return idPreMovimiento;
    }
    
    public void setIdPreMovimiento(Long idPreMovimiento) {
        this.idPreMovimiento = idPreMovimiento;
    }
    
    public String getSituacionMovimiento() {
        return situacionMovimiento;
    }
    
    public void setSituacionMovimiento(String situacionMovimiento) {
        this.situacionMovimiento = situacionMovimiento;
    }
    
    public LocalDateTime getFechaHoraRegistro() {
        return fechaHoraRegistro;
    }
    
    public void setFechaHoraRegistro(LocalDateTime fechaHoraRegistro) {
        this.fechaHoraRegistro = fechaHoraRegistro;
    }
    
    public LocalDateTime getFechaHoraActivacion() {
        return fechaHoraActivacion;
    }
    
    public void setFechaHoraActivacion(LocalDateTime fechaHoraActivacion) {
        this.fechaHoraActivacion = fechaHoraActivacion;
    }
    
    public String getLogIpAddress() {
        return logIpAddress;
    }
    
    public void setLogIpAddress(String logIpAddress) {
        this.logIpAddress = logIpAddress;
    }
    
    public String getLogOsUser() {
        return logOsUser;
    }
    
    public void setLogOsUser(String logOsUser) {
        this.logOsUser = logOsUser;
    }
    
    public String getLogHost() {
        return logHost;
    }
    
    public void setLogHost(String logHost) {
        this.logHost = logHost;
    }
    
    public String getClaveUsuario() {
        return claveUsuario;
    }
    
    public void setClaveUsuario(String claveUsuario) {
        this.claveUsuario = claveUsuario;
    }
    
    public String getClaveUsuarioCancela() {
        return claveUsuarioCancela;
    }
    
    public void setClaveUsuarioCancela(String claveUsuarioCancela) {
        this.claveUsuarioCancela = claveUsuarioCancela;
    }
    
    public Integer getNumeroPagoAmortizacion() {
        return numeroPagoAmortizacion;
    }
    
    public void setNumeroPagoAmortizacion(Integer numeroPagoAmortizacion) {
        this.numeroPagoAmortizacion = numeroPagoAmortizacion;
    }
    
    public List<MovimientoDetalleDTO> getDetalles() {
        return detalles;
    }
    
    public void setDetalles(List<MovimientoDetalleDTO> detalles) {
        this.detalles = detalles;
    }
}