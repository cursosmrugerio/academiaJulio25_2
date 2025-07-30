package com.financiero.domain.enums;

/**
 * Estados posibles de un movimiento financiero
 * Basado en las constantes del PKG_PROCESADOR_FINANCIERO
 */
public enum MovimientoEstado {
    
    /**
     * NP - No Procesado
     * Estado inicial de un pre-movimiento
     */
    NO_PROCESADO("NP", "No Procesado", "El movimiento aún no ha sido procesado"),
    
    /**
     * PV - Procesado Virtual
     * El movimiento ha sido procesado pero no ha afectado saldos reales
     */
    PROCESADO_VIRTUAL("PV", "Procesado Virtual", "El movimiento ha sido procesado virtualmente"),
    
    /**
     * PR - Procesado Real
     * El movimiento ha sido procesado y ha afectado saldos reales
     */
    PROCESADO_REAL("PR", "Procesado Real", "El movimiento ha sido procesado completamente"),
    
    /**
     * CA - Cancelado
     * El movimiento ha sido cancelado
     */
    CANCELADO("CA", "Cancelado", "El movimiento ha sido cancelado");
    
    private final String codigo;
    private final String descripcion;
    private final String detalle;
    
    MovimientoEstado(String codigo, String descripcion, String detalle) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.detalle = detalle;
    }
    
    public String getCodigo() {
        return codigo;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public String getDetalle() {
        return detalle;
    }
    
    /**
     * Obtiene el enum por su código
     */
    public static MovimientoEstado fromCodigo(String codigo) {
        if (codigo == null) {
            return null;
        }
        
        for (MovimientoEstado estado : values()) {
            if (estado.codigo.equals(codigo)) {
                return estado;
            }
        }
        
        throw new IllegalArgumentException("Código de estado no válido: " + codigo);
    }
    
    /**
     * Valida si es posible la transición de un estado a otro
     */
    public boolean puedeTransicionarA(MovimientoEstado nuevoEstado) {
        if (nuevoEstado == null) {
            return false;
        }
        
        switch (this) {
            case NO_PROCESADO:
                // Desde NP puede ir a PV o PR
                return nuevoEstado == PROCESADO_VIRTUAL || nuevoEstado == PROCESADO_REAL;
                
            case PROCESADO_VIRTUAL:
                // Desde PV puede ir a PR o CA
                return nuevoEstado == PROCESADO_REAL || nuevoEstado == CANCELADO;
                
            case PROCESADO_REAL:
                // Desde PR no puede cambiar (estado final)
                return false;
                
            case CANCELADO:
                // Desde CA no puede cambiar (estado final)
                return false;
                
            default:
                return false;
        }
    }
    
    /**
     * Indica si el estado es final (no puede cambiar)
     */
    public boolean esFinal() {
        return this == PROCESADO_REAL || this == CANCELADO;
    }
    
    /**
     * Indica si el estado permite modificaciones
     */
    public boolean permiteModificaciones() {
        return this == NO_PROCESADO || this == PROCESADO_VIRTUAL;
    }
    
    /**
     * Indica si el movimiento ha afectado saldos
     */
    public boolean haAfectadoSaldos() {
        return this == PROCESADO_REAL || this == CANCELADO;
    }
    
    @Override
    public String toString() {
        return descripcion + " (" + codigo + ")";
    }
}