package com.financiero.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ParametroSistemaId implements Serializable {
    
    @NotBlank(message = "La clave de grupo empresa es obligatoria")
    @Size(max = 10, message = "La clave de grupo empresa no puede exceder 10 caracteres")
    @Column(name = "CVE_GPO_EMPRESA", length = 10)
    private String claveGrupoEmpresa;
    
    @NotBlank(message = "La clave de empresa es obligatoria")
    @Size(max = 10, message = "La clave de empresa no puede exceder 10 caracteres")
    @Column(name = "CVE_EMPRESA", length = 10)
    private String claveEmpresa;
    
    @NotBlank(message = "La clave de medio es obligatoria")
    @Size(max = 20, message = "La clave de medio no puede exceder 20 caracteres")
    @Column(name = "CVE_MEDIO", length = 20)
    private String claveMedio;
    
    // Constructors
    public ParametroSistemaId() {}
    
    public ParametroSistemaId(String claveGrupoEmpresa, String claveEmpresa, String claveMedio) {
        this.claveGrupoEmpresa = claveGrupoEmpresa;
        this.claveEmpresa = claveEmpresa;
        this.claveMedio = claveMedio;
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
    
    public String getClaveMedio() {
        return claveMedio;
    }
    
    public void setClaveMedio(String claveMedio) {
        this.claveMedio = claveMedio;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParametroSistemaId that = (ParametroSistemaId) o;
        return Objects.equals(claveGrupoEmpresa, that.claveGrupoEmpresa) &&
               Objects.equals(claveEmpresa, that.claveEmpresa) &&
               Objects.equals(claveMedio, that.claveMedio);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(claveGrupoEmpresa, claveEmpresa, claveMedio);
    }
    
    @Override
    public String toString() {
        return "ParametroSistemaId{" +
                "claveGrupoEmpresa='" + claveGrupoEmpresa + '\'' +
                ", claveEmpresa='" + claveEmpresa + '\'' +
                ", claveMedio='" + claveMedio + '\'' +
                '}';
    }
}