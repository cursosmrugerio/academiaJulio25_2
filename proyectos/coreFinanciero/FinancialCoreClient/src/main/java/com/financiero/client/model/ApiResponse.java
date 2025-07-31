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
    
    @JsonProperty("message")
    private String message;
    
    @JsonProperty("mensaje")
    private String mensaje;
    
    @JsonProperty("fecha_proceso")
    private LocalDate fechaProceso;
    
    @JsonProperty("empresa")
    private String empresa;
    
    @JsonProperty("id_movimiento")
    private Long idMovimiento;
    
    @JsonProperty("idPreMovimiento")
    private Long idPreMovimiento;
    
    @JsonProperty("usuario_cancela")
    private String usuarioCancela;
    
    @JsonProperty("data")
    private Map<String, Object> data;
    
    // Constructor
    public ApiResponse() {}
    
    // Getters and Setters
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getMessage() { 
        // Devolver mensaje si existe, sino devolver message
        return mensaje != null ? mensaje : message; 
    }
    public void setMessage(String message) { this.message = message; }
    
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    
    public LocalDate getFechaProceso() { return fechaProceso; }
    public void setFechaProceso(LocalDate fechaProceso) { this.fechaProceso = fechaProceso; }
    
    public String getEmpresa() { return empresa; }
    public void setEmpresa(String empresa) { this.empresa = empresa; }
    
    public Long getIdMovimiento() { return idMovimiento; }
    public void setIdMovimiento(Long idMovimiento) { this.idMovimiento = idMovimiento; }
    
    public Long getIdPreMovimiento() { return idPreMovimiento; }
    public void setIdPreMovimiento(Long idPreMovimiento) { this.idPreMovimiento = idPreMovimiento; }
    
    public String getUsuarioCancela() { return usuarioCancela; }
    public void setUsuarioCancela(String usuarioCancela) { this.usuarioCancela = usuarioCancela; }
    
    public Map<String, Object> getData() { return data; }
    public void setData(Map<String, Object> data) { this.data = data; }
    
    // Métodos de utilidad
    public boolean isSuccess() {
        return "success".equals(status) || "CREADO".equals(status) || "OK".equals(status);
    }
    
    public boolean isError() {
        return "error".equals(status) || "ERROR".equals(status);
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