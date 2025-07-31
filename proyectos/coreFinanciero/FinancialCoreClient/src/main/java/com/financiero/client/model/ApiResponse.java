package com.financiero.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.Map;

/**
 * Response genérico para operaciones del API
 */
public class ApiResponse {
    
    @JsonProperty("status")
    private String status;
    
    @JsonProperty(value = "message", access = JsonProperty.Access.WRITE_ONLY)
    private String message;
    
    @JsonProperty(value = "mensaje", access = JsonProperty.Access.WRITE_ONLY)
    private String mensaje;
    
    @JsonProperty("fecha_proceso")
    private LocalDate fechaProceso;
    
    @JsonProperty("empresa")
    private String empresa;
    
    @JsonProperty("id_movimiento")
    private Long idMovimiento;
    
    @JsonProperty("usuario_cancela")
    private String usuarioCancela;
    
    @JsonProperty("data")
    private Map<String, Object> data;
    
    // Constructor
    public ApiResponse() {}
    
    // Getters and Setters
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public LocalDate getFechaProceso() { return fechaProceso; }
    public void setFechaProceso(LocalDate fechaProceso) { this.fechaProceso = fechaProceso; }
    
    public String getEmpresa() { return empresa; }
    public void setEmpresa(String empresa) { this.empresa = empresa; }
    
    public Long getIdMovimiento() { return idMovimiento; }
    public void setIdMovimiento(Long idMovimiento) { this.idMovimiento = idMovimiento; }
    
    public String getUsuarioCancela() { return usuarioCancela; }
    public void setUsuarioCancela(String usuarioCancela) { this.usuarioCancela = usuarioCancela; }
    
    public Map<String, Object> getData() { return data; }
    public void setData(Map<String, Object> data) { this.data = data; }
    
    // Métodos de utilidad
    public boolean isSuccess() {
        return "success".equals(status);
    }
    
    public boolean isError() {
        return "error".equals(status);
    }
    
    @Override
    public String toString() {
        return "ApiResponse{" +
               "status='" + status + '\''+
               ", message='" + message + '\''+
               ", empresa='" + empresa + '\''+
               '}';
    }
}