package com.financiero.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "PFIN_MOVIMIENTO")
public class Movimiento {
    
    @EmbeddedId
    private MovimientoId id;
    
    @Column(name = "ID_CUENTA")
    private Long idCuenta;
    
    @Size(max = 5, message = "La clave de divisa no puede exceder 5 caracteres")
    @Column(name = "CVE_DIVISA", length = 5)
    private String claveDivisa;
    
    @Column(name = "F_OPERACION")
    private LocalDate fechaOperacion;
    
    @Column(name = "F_LIQUIDACION")
    private LocalDate fechaLiquidacion;
    
    @Column(name = "F_APLICACION")
    private LocalDate fechaAplicacion;
    
    @Size(max = 10, message = "La clave de operación no puede exceder 10 caracteres")
    @Column(name = "CVE_OPERACION", length = 10)
    private String claveOperacion;
    
    @NotNull(message = "El importe neto es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El importe neto debe ser mayor a cero")
    @Column(name = "IMP_NETO", precision = 15, scale = 2)
    private BigDecimal importeNeto;
    
    @Column(name = "PREC_OPERACION", precision = 10, scale = 6)
    private BigDecimal precioOperacion = BigDecimal.ZERO;
    
    @Column(name = "TIPO_CAMBIO", precision = 10, scale = 6)
    private BigDecimal tipoCambio = BigDecimal.ZERO;
    
    @Column(name = "ID_REFERENCIA")
    private Long idReferencia;
    
    @Column(name = "ID_PRESTAMO")
    private Long idPrestamo;
    
    @Size(max = 20, message = "La clave de mercado no puede exceder 20 caracteres")
    @Column(name = "CVE_MERCADO", length = 20)
    private String claveMercado;
    
    @Size(max = 20, message = "La clave de medio no puede exceder 20 caracteres")
    @Column(name = "CVE_MEDIO", length = 20)
    private String claveMedio;
    
    @Size(max = 500, message = "La nota no puede exceder 500 caracteres")
    @Column(name = "TX_NOTA", length = 500)
    private String nota;
    
    @Size(max = 100, message = "La referencia no puede exceder 100 caracteres")
    @Column(name = "TX_REFERENCIA", length = 100)
    private String referencia;
    
    @Column(name = "ID_GRUPO")
    private Long idGrupo;
    
    @Column(name = "ID_PRE_MOVIMIENTO")
    private Long idPreMovimiento;
    
    @NotNull(message = "La situación del movimiento es obligatoria")
    @Size(max = 5, message = "La situación del movimiento no puede exceder 5 caracteres")
    @Column(name = "SIT_MOVIMIENTO", length = 5)
    private String situacionMovimiento;
    
    @Column(name = "FH_REGISTRO")
    private LocalDateTime fechaHoraRegistro;
    
    @Column(name = "FH_ACTIVACION")
    private LocalDateTime fechaHoraActivacion;
    
    @Size(max = 45, message = "La dirección IP no puede exceder 45 caracteres")
    @Column(name = "LOG_IP_ADDRESS", length = 45)
    private String logIpAddress;
    
    @Size(max = 100, message = "El usuario del SO no puede exceder 100 caracteres")
    @Column(name = "LOG_OS_USER", length = 100)
    private String logOsUser;
    
    @Size(max = 100, message = "El host no puede exceder 100 caracteres")
    @Column(name = "LOG_HOST", length = 100)
    private String logHost;
    
    @Size(max = 20, message = "La clave de usuario no puede exceder 20 caracteres")
    @Column(name = "CVE_USUARIO", length = 20)
    private String claveUsuario;
    
    @Size(max = 20, message = "La clave de usuario que cancela no puede exceder 20 caracteres")
    @Column(name = "CVE_USUARIO_CANCELA", length = 20)
    private String claveUsuarioCancela;
    
    @Column(name = "NUM_PAGO_AMORTIZACION")
    private Integer numeroPagoAmortizacion;
    
    // Relación con detalles (se manejará a nivel de servicio)
    @Transient
    private List<MovimientoDetalle> detalles = new ArrayList<>();
    
    // Constructors
    public Movimiento() {}
    
    public Movimiento(MovimientoId id) {
        this.id = id;
    }
    
    // Getters and Setters
    public MovimientoId getId() {
        return id;
    }
    
    public void setId(MovimientoId id) {
        this.id = id;
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
    
    public String getNota() {
        return nota;
    }
    
    public void setNota(String nota) {
        this.nota = nota;
    }
    
    public String getReferencia() {
        return referencia;
    }
    
    public void setReferencia(String referencia) {
        this.referencia = referencia;
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
    
    public List<MovimientoDetalle> getDetalles() {
        return detalles;
    }
    
    public void setDetalles(List<MovimientoDetalle> detalles) {
        this.detalles = detalles;
    }
    
    // Domain Methods  
    public void agregarDetalle(MovimientoDetalle detalle) {
        detalles.add(detalle);
    }
    
    public void removerDetalle(MovimientoDetalle detalle) {
        detalles.remove(detalle);
    }
    
    public boolean estaProcesadoVirtual() {
        return "PV".equals(this.situacionMovimiento);
    }
    
    public boolean estaProcesadoReal() {
        return "PR".equals(this.situacionMovimiento);
    }
    
    public boolean estaCancelado() {
        return "CA".equals(this.situacionMovimiento);
    }
    
    public boolean esPrestamo() {
        return "PRESTAMO".equals(this.claveMercado);
    }
    
    public void marcarComoProcesadoVirtual() {
        this.situacionMovimiento = "PV";
        this.fechaHoraActivacion = LocalDateTime.now();
    }
    
    public void marcarComoProcesadoReal() {
        this.situacionMovimiento = "PR";
        this.fechaHoraActivacion = LocalDateTime.now();
    }
    
    public void marcarComoCancelado(String usuarioCancela) {
        this.situacionMovimiento = "CA";
        this.claveUsuarioCancela = usuarioCancela;
        this.fechaHoraActivacion = LocalDateTime.now();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movimiento that = (Movimiento) o;
        return Objects.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Movimiento{" +
                "id=" + id +
                ", fechaOperacion=" + fechaOperacion +
                ", fechaLiquidacion=" + fechaLiquidacion +
                ", importeNeto=" + importeNeto +
                ", situacionMovimiento='" + situacionMovimiento + '\'' +
                '}';
    }
}