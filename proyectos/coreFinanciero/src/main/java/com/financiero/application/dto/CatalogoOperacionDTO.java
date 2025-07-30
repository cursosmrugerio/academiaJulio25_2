package com.financiero.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CatalogoOperacionDTO {
    
    @NotBlank(message = "La clave de grupo empresa es obligatoria")
    @Size(max = 10)
    private String claveGrupoEmpresa;
    
    @NotBlank(message = "La clave de empresa es obligatoria")
    @Size(max = 10)
    private String claveEmpresa;
    
    @NotBlank(message = "La clave de operación es obligatoria")
    @Size(max = 10)
    private String claveOperacion;
    
    @Size(max = 100)
    private String textoDescripcion;
    
    @NotBlank(message = "La clave afecta saldo es obligatoria")
    @Pattern(regexp = "^[IDN]$", message = "La clave afecta saldo debe ser I, D o N")
    private String claveAfectaSaldo;
    
    @Pattern(regexp = "^[AS]$", message = "El estatus debe ser A o S")
    private String estatusOperacion = "A";
    
    @Size(max = 200)
    private String textoObservaciones;
    
    // Constructors
    public CatalogoOperacionDTO() {}
    
    public CatalogoOperacionDTO(String claveGrupoEmpresa, String claveEmpresa, String claveOperacion,
                               String textoDescripcion, String claveAfectaSaldo, String estatusOperacion,
                               String textoObservaciones) {
        this.claveGrupoEmpresa = claveGrupoEmpresa;
        this.claveEmpresa = claveEmpresa;
        this.claveOperacion = claveOperacion;
        this.textoDescripcion = textoDescripcion;
        this.claveAfectaSaldo = claveAfectaSaldo;
        this.estatusOperacion = estatusOperacion;
        this.textoObservaciones = textoObservaciones;
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
    
    public String getClaveOperacion() {
        return claveOperacion;
    }
    
    public void setClaveOperacion(String claveOperacion) {
        this.claveOperacion = claveOperacion;
    }
    
    public String getTextoDescripcion() {
        return textoDescripcion;
    }
    
    public void setTextoDescripcion(String textoDescripcion) {
        this.textoDescripcion = textoDescripcion;
    }
    
    public String getClaveAfectaSaldo() {
        return claveAfectaSaldo;
    }
    
    public void setClaveAfectaSaldo(String claveAfectaSaldo) {
        this.claveAfectaSaldo = claveAfectaSaldo;
    }
    
    public String getEstatusOperacion() {
        return estatusOperacion;
    }
    
    public void setEstatusOperacion(String estatusOperacion) {
        this.estatusOperacion = estatusOperacion;
    }
    
    public String getTextoObservaciones() {
        return textoObservaciones;
    }
    
    public void setTextoObservaciones(String textoObservaciones) {
        this.textoObservaciones = textoObservaciones;
    }
}