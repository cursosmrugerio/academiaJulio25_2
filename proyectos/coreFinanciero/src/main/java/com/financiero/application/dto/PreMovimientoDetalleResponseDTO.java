package com.financiero.application.dto;

import java.math.BigDecimal;

public class PreMovimientoDetalleResponseDTO {
    
    private String claveGrupoEmpresa;
    private String claveEmpresa;
    private Long idPreMovimiento;
    private String claveConcepto;
    private BigDecimal importeConcepto;
    private String nota;
    
    // Constructors
    public PreMovimientoDetalleResponseDTO() {}
    
    public PreMovimientoDetalleResponseDTO(String claveGrupoEmpresa, String claveEmpresa, 
                                          Long idPreMovimiento, String claveConcepto, 
                                          BigDecimal importeConcepto, String nota) {
        this.claveGrupoEmpresa = claveGrupoEmpresa;
        this.claveEmpresa = claveEmpresa;
        this.idPreMovimiento = idPreMovimiento;
        this.claveConcepto = claveConcepto;
        this.importeConcepto = importeConcepto;
        this.nota = nota;
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
    
    public Long getIdPreMovimiento() {
        return idPreMovimiento;
    }
    
    public void setIdPreMovimiento(Long idPreMovimiento) {
        this.idPreMovimiento = idPreMovimiento;
    }
    
    public String getClaveConcepto() {
        return claveConcepto;
    }
    
    public void setClaveConcepto(String claveConcepto) {
        this.claveConcepto = claveConcepto;
    }
    
    public BigDecimal getImporteConcepto() {
        return importeConcepto;
    }
    
    public void setImporteConcepto(BigDecimal importeConcepto) {
        this.importeConcepto = importeConcepto;
    }
    
    public String getNota() {
        return nota;
    }
    
    public void setNota(String nota) {
        this.nota = nota;
    }
}