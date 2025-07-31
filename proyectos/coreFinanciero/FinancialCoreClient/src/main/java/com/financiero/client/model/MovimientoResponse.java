package com.financiero.client.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Response para movimientos del core financiero
 */
public class MovimientoResponse {
    
    @JsonProperty("claveGrupoEmpresa")
    private String claveGrupoEmpresa;
    
    @JsonProperty("claveEmpresa")
    private String claveEmpresa;
    
    @JsonProperty("idMovimiento")
    private Long idMovimiento;
    
    @JsonProperty("idCuenta")
    private Long idCuenta;
    
    @JsonProperty("claveDivisa")
    private String claveDivisa;
    
    @JsonProperty("fechaOperacion")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaOperacion;
    
    @JsonProperty("fechaLiquidacion")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaLiquidacion;
    
    @JsonProperty("fechaAplicacion")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaAplicacion;
    
    @JsonProperty("claveOperacion")
    private String claveOperacion;
    
    @JsonProperty("importeNeto")
    private BigDecimal importeNeto;
    
    @JsonProperty("precioOperacion")
    private BigDecimal precioOperacion;
    
    @JsonProperty("tipoCambio")
    private BigDecimal tipoCambio;
    
    @JsonProperty("idPrestamo")
    private Long idPrestamo;
    
    @JsonProperty("claveMercado")
    private String claveMercado;
    
    @JsonProperty("claveMedio")
    private String claveMedio;
    
    @JsonProperty("textoNota")
    private String textoNota;
    
    @JsonProperty("textoReferencia")
    private String textoReferencia;
    
    @JsonProperty("idGrupo")
    private Long idGrupo;
    
    @JsonProperty("idPreMovimiento")
    private Long idPreMovimiento;
    
    @JsonProperty("situacionMovimiento")
    private String situacionMovimiento;
    
    @JsonProperty("fechaHoraRegistro")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaHoraRegistro;
    
    @JsonProperty("fechaHoraActivacion")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaHoraActivacion;
    
    @JsonProperty("claveUsuario")
    private String claveUsuario;
    
    @JsonProperty("claveUsuarioCancela")
    private String claveUsuarioCancela;
    
    @JsonProperty("numeroPagoAmortizacion")
    private Integer numeroPagoAmortizacion;
    
    @JsonProperty("detalles")
    private List<MovimientoDetalle> detalles;
    
    // Constructor
    public MovimientoResponse() {}
    
    // Getters and Setters
    public String getClaveGrupoEmpresa() { return claveGrupoEmpresa; }
    public void setClaveGrupoEmpresa(String claveGrupoEmpresa) { this.claveGrupoEmpresa = claveGrupoEmpresa; }
    
    public String getClaveEmpresa() { return claveEmpresa; }
    public void setClaveEmpresa(String claveEmpresa) { this.claveEmpresa = claveEmpresa; }
    
    public Long getIdMovimiento() { return idMovimiento; }
    public void setIdMovimiento(Long idMovimiento) { this.idMovimiento = idMovimiento; }
    
    public Long getIdCuenta() { return idCuenta; }
    public void setIdCuenta(Long idCuenta) { this.idCuenta = idCuenta; }
    
    public String getClaveDivisa() { return claveDivisa; }
    public void setClaveDivisa(String claveDivisa) { this.claveDivisa = claveDivisa; }
    
    public LocalDate getFechaOperacion() { return fechaOperacion; }
    public void setFechaOperacion(LocalDate fechaOperacion) { this.fechaOperacion = fechaOperacion; }
    
    public LocalDate getFechaLiquidacion() { return fechaLiquidacion; }
    public void setFechaLiquidacion(LocalDate fechaLiquidacion) { this.fechaLiquidacion = fechaLiquidacion; }
    
    public LocalDate getFechaAplicacion() { return fechaAplicacion; }
    public void setFechaAplicacion(LocalDate fechaAplicacion) { this.fechaAplicacion = fechaAplicacion; }
    
    public String getClaveOperacion() { return claveOperacion; }
    public void setClaveOperacion(String claveOperacion) { this.claveOperacion = claveOperacion; }
    
    public BigDecimal getImporteNeto() { return importeNeto; }
    public void setImporteNeto(BigDecimal importeNeto) { this.importeNeto = importeNeto; }
    
    public BigDecimal getPrecioOperacion() { return precioOperacion; }
    public void setPrecioOperacion(BigDecimal precioOperacion) { this.precioOperacion = precioOperacion; }
    
    public BigDecimal getTipoCambio() { return tipoCambio; }
    public void setTipoCambio(BigDecimal tipoCambio) { this.tipoCambio = tipoCambio; }
    
    public Long getIdPrestamo() { return idPrestamo; }
    public void setIdPrestamo(Long idPrestamo) { this.idPrestamo = idPrestamo; }
    
    public String getClaveMercado() { return claveMercado; }
    public void setClaveMercado(String claveMercado) { this.claveMercado = claveMercado; }
    
    public String getClaveMedio() { return claveMedio; }
    public void setClaveMedio(String claveMedio) { this.claveMedio = claveMedio; }
    
    public String getTextoNota() { return textoNota; }
    public void setTextoNota(String textoNota) { this.textoNota = textoNota; }
    
    public String getTextoReferencia() { return textoReferencia; }
    public void setTextoReferencia(String textoReferencia) { this.textoReferencia = textoReferencia; }
    
    public Long getIdGrupo() { return idGrupo; }
    public void setIdGrupo(Long idGrupo) { this.idGrupo = idGrupo; }
    
    public Long getIdPreMovimiento() { return idPreMovimiento; }
    public void setIdPreMovimiento(Long idPreMovimiento) { this.idPreMovimiento = idPreMovimiento; }
    
    public String getSituacionMovimiento() { return situacionMovimiento; }
    public void setSituacionMovimiento(String situacionMovimiento) { this.situacionMovimiento = situacionMovimiento; }
    
    public LocalDateTime getFechaHoraRegistro() { return fechaHoraRegistro; }
    public void setFechaHoraRegistro(LocalDateTime fechaHoraRegistro) { this.fechaHoraRegistro = fechaHoraRegistro; }
    
    public LocalDateTime getFechaHoraActivacion() { return fechaHoraActivacion; }
    public void setFechaHoraActivacion(LocalDateTime fechaHoraActivacion) { this.fechaHoraActivacion = fechaHoraActivacion; }
    
    public String getClaveUsuario() { return claveUsuario; }
    public void setClaveUsuario(String claveUsuario) { this.claveUsuario = claveUsuario; }
    
    public String getClaveUsuarioCancela() { return claveUsuarioCancela; }
    public void setClaveUsuarioCancela(String claveUsuarioCancela) { this.claveUsuarioCancela = claveUsuarioCancela; }
    
    public Integer getNumeroPagoAmortizacion() { return numeroPagoAmortizacion; }
    public void setNumeroPagoAmortizacion(Integer numeroPagoAmortizacion) { this.numeroPagoAmortizacion = numeroPagoAmortizacion; }
    
    public List<MovimientoDetalle> getDetalles() { return detalles; }
    public void setDetalles(List<MovimientoDetalle> detalles) { this.detalles = detalles; }
    
    // Métodos de utilidad
    public boolean isVirtual() {
        return "PV".equals(situacionMovimiento);
    }
    
    public boolean isReal() {
        return "PR".equals(situacionMovimiento);
    }
    
    public boolean isCancelado() {
        return "CA".equals(situacionMovimiento);
    }
    
    public boolean isPendiente() {
        return "NP".equals(situacionMovimiento);
    }
    
    public String getEstadoDescripcion() {
        return switch (situacionMovimiento) {
            case "NP" -> "No Procesado";
            case "PV" -> "Procesado Virtual";
            case "PR" -> "Procesado Real";
            case "CA" -> "Cancelado";
            default -> "Estado Desconocido";
        };
    }
    
    @Override
    public String toString() {
        return "MovimientoResponse{" +
               "id=" + idMovimiento +
               ", operacion='" + claveOperacion + '\''+
               ", importe=" + importeNeto +
               ", divisa='" + claveDivisa + '\''+
               ", situacion='" + situacionMovimiento + '\''+
               '}';
    }
    
    /**
     * Detalle de movimiento anidado
     */
    public static class MovimientoDetalle {
        @JsonProperty("claveConcepto")
        private String claveConcepto;
        
        @JsonProperty("importeConcepto")
        private BigDecimal importeConcepto;
        
        @JsonProperty("nota")
        private String nota;
        
        // Constructor
        public MovimientoDetalle() {}
        
        // Getters and Setters
        public String getClaveConcepto() { return claveConcepto; }
        public void setClaveConcepto(String claveConcepto) { this.claveConcepto = claveConcepto; }
        
        public BigDecimal getImporteConcepto() { return importeConcepto; }
        public void setImporteConcepto(BigDecimal importeConcepto) { this.importeConcepto = importeConcepto; }
        
        public String getNota() { return nota; }
        public void setNota(String nota) { this.nota = nota; }
        
        @Override
        public String toString() {
            return "MovimientoDetalle{" +
                   "concepto='" + claveConcepto + '\''+
                   ", importe=" + importeConcepto +
                   '}';
        }
    }
}