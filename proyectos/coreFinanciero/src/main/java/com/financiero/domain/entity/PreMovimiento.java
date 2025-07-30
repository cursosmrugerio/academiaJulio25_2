package com.financiero.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "PFIN_PRE_MOVIMIENTO")
public class PreMovimiento {
    
    @EmbeddedId
    private PreMovimientoId id;
    
    @Column(name = "F_OPERACION")
    private LocalDate fechaOperacion;
    
    @Column(name = "F_LIQUIDACION")
    private LocalDate fechaLiquidacion;
    
    @Column(name = "F_APLICACION")
    private LocalDate fechaAplicacion;
    
    @Column(name = "ID_CUENTA")
    private Long idCuenta;
    
    @Column(name = "ID_PRESTAMO")
    private Long idPrestamo;
    
    @Size(max = 5, message = "La clave de divisa no puede exceder 5 caracteres")
    @Column(name = "CVE_DIVISA", length = 5)
    private String claveDivisa;
    
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
    
    @Size(max = 20, message = "La clave de medio no puede exceder 20 caracteres")
    @Column(name = "CVE_MEDIO", length = 20)
    private String claveMedio;
    
    @Size(max = 20, message = "La clave de mercado no puede exceder 20 caracteres")
    @Column(name = "CVE_MERCADO", length = 20)
    private String claveMercado;
    
    @Size(max = 500, message = "La nota no puede exceder 500 caracteres")
    @Column(name = "TX_NOTA", length = 500)
    private String nota;
    
    @Column(name = "ID_GRUPO")
    private Long idGrupo;
    
    @Column(name = "ID_MOVIMIENTO")
    private Long idMovimiento = 0L;
    
    @Size(max = 20, message = "La clave de usuario no puede exceder 20 caracteres")
    @Column(name = "CVE_USUARIO", length = 20)
    private String claveUsuario;
    
    @Size(max = 5, message = "La situación del pre-movimiento no puede exceder 5 caracteres")
    @Column(name = "SIT_PRE_MOVIMIENTO", length = 5)
    private String situacionPreMovimiento = "NP";
    
    @Column(name = "NUM_PAGO_AMORTIZACION")
    private Integer numeroPagoAmortizacion;
    
    // Relaciones se manejarán a nivel de servicio para evitar problemas de mapeo
    @Transient
    private List<PreMovimientoDetalle> detalles = new ArrayList<>();
    
    // Constructors
    public PreMovimiento() {}
    
    public PreMovimiento(PreMovimientoId id) {
        this.id = id;
    }
    
    // Getters and Setters
    public PreMovimientoId getId() {
        return id;
    }
    
    public void setId(PreMovimientoId id) {
        this.id = id;
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
    
    public List<PreMovimientoDetalle> getDetalles() {
        return detalles;
    }
    
    public void setDetalles(List<PreMovimientoDetalle> detalles) {
        this.detalles = detalles;
    }
    
    // Domain Methods
    public void agregarDetalle(PreMovimientoDetalle detalle) {
        detalles.add(detalle);
    }
    
    public void removerDetalle(PreMovimientoDetalle detalle) {
        detalles.remove(detalle);
    }
    
    public boolean esPrestamo() {
        return "PRESTAMO".equals(this.claveMercado);
    }
    
    public boolean estaPendiente() {
        return "NP".equals(this.situacionPreMovimiento);
    }
    
    public void marcarComoProcesado() {
        this.situacionPreMovimiento = "PR";
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PreMovimiento that = (PreMovimiento) o;
        return Objects.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "PreMovimiento{" +
                "id=" + id +
                ", fechaOperacion=" + fechaOperacion +
                ", fechaLiquidacion=" + fechaLiquidacion +
                ", importeNeto=" + importeNeto +
                ", situacionPreMovimiento='" + situacionPreMovimiento + '\'' +
                '}';
    }
}