package com.financiero.client.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.financiero.client.config.Http2Configuration;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Cliente para operaciones del sistema de fechas
 * 
 * Maneja las operaciones relacionadas con fechas:
 * - Obtener fecha del sistema
 * - Recorrer fecha del sistema
 * - Actualizar fecha del sistema
 * - Validar días hábiles
 */
public class DateSystemClient {
    
    private static final Logger logger = LoggerFactory.getLogger(DateSystemClient.class);
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final Http2Configuration configuration;
    private final String baseUrl;
    
    // Métricas
    private final Timer requestTimer;
    private final Counter successCounter;
    private final Counter errorCounter;
    
    public DateSystemClient(OkHttpClient httpClient, ObjectMapper objectMapper, 
                           Http2Configuration configuration, MeterRegistry meterRegistry) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.configuration = configuration;
        this.baseUrl = configuration.getBaseUrl() + "/api/v1/fechas";
        
        // Inicializar métricas
        this.requestTimer = Timer.builder("financial_client_request_duration")
            .tag("service", "date_system")
            .register(meterRegistry);
        this.successCounter = Counter.builder("financial_client_requests_total")
            .tag("service", "date_system")
            .tag("status", "success")
            .register(meterRegistry);
        this.errorCounter = Counter.builder("financial_client_requests_total")
            .tag("service", "date_system")
            .tag("status", "error")
            .register(meterRegistry);
    }
    
    /**
     * Obtiene la fecha actual del sistema para una empresa
     */
    public CompletableFuture<Map<String, Object>> obtenerFechaSistema(String claveGrupoEmpresa, 
                                                                     String claveEmpresa) {
        return CompletableFuture.supplyAsync(() -> {
            Timer.Sample sample = Timer.start();
            
            try {
                HttpUrl url = HttpUrl.parse(baseUrl + "/sistema")
                    .newBuilder()
                    .addQueryParameter("claveGrupoEmpresa", claveGrupoEmpresa)
                    .addQueryParameter("claveEmpresa", claveEmpresa)
                    .build();
                
                Request httpRequest = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
                
                logger.debug("Obteniendo fecha del sistema para empresa {}-{}", 
                           claveGrupoEmpresa, claveEmpresa);
                
                try (Response response = httpClient.newCall(httpRequest).execute()) {
                    String responseBody = response.body().string();
                    
                    if (response.isSuccessful()) {
                        successCounter.increment();
                        TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>() {};
                        Map<String, Object> fechaInfo = objectMapper.readValue(responseBody, typeRef);
                        logger.debug("Fecha del sistema obtenida: {}", fechaInfo.get("fechaSistema"));
                        return fechaInfo;
                    } else {
                        errorCounter.increment();
                        logger.error("Error obteniendo fecha del sistema: HTTP {}", response.code());
                        throw new RuntimeException("Error HTTP " + response.code() + ": " + responseBody);
                    }
                }
                
            } catch (Exception e) {
                errorCounter.increment();
                logger.error("Error obteniendo fecha del sistema: {}", e.getMessage(), e);
                throw new RuntimeException("Error obteniendo fecha del sistema", e);
            } finally {
                sample.stop(requestTimer);
            }
        });
    }
    
    /**
     * Recorre la fecha del sistema al siguiente día hábil
     */
    public CompletableFuture<Map<String, Object>> recorrerFecha(String claveGrupoEmpresa, 
                                                               String claveEmpresa) {
        return CompletableFuture.supplyAsync(() -> {
            Timer.Sample sample = Timer.start();
            
            try {
                HttpUrl url = HttpUrl.parse(baseUrl + "/recorrer")
                    .newBuilder()
                    .addQueryParameter("claveGrupoEmpresa", claveGrupoEmpresa)
                    .addQueryParameter("claveEmpresa", claveEmpresa)
                    .build();
                
                Request httpRequest = new Request.Builder()
                    .url(url)
                    .post(RequestBody.create("", JSON))
                    .build();
                
                logger.debug("Recorriendo fecha del sistema para empresa {}-{}", 
                           claveGrupoEmpresa, claveEmpresa);
                
                try (Response response = httpClient.newCall(httpRequest).execute()) {
                    String responseBody = response.body().string();
                    
                    if (response.isSuccessful()) {
                        successCounter.increment();
                        TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>() {};
                        Map<String, Object> resultado = objectMapper.readValue(responseBody, typeRef);
                        logger.info("Fecha recorrida de {} a {}", 
                                  resultado.get("fechaAnterior"), resultado.get("fechaNueva"));
                        return resultado;
                    } else {
                        errorCounter.increment();
                        logger.error("Error recorriendo fecha: HTTP {}", response.code());
                        throw new RuntimeException("Error HTTP " + response.code() + ": " + responseBody);
                    }
                }
                
            } catch (Exception e) {
                errorCounter.increment();
                logger.error("Error recorriendo fecha: {}", e.getMessage(), e);
                throw new RuntimeException("Error recorriendo fecha", e);
            } finally {
                sample.stop(requestTimer);
            }
        });
    }
    
    /**
     * Actualiza manualmente la fecha del sistema
     */
    public CompletableFuture<Map<String, Object>> actualizarFechaSistema(String claveGrupoEmpresa, 
                                                                        String claveEmpresa, 
                                                                        LocalDate nuevaFecha) {
        return CompletableFuture.supplyAsync(() -> {
            Timer.Sample sample = Timer.start();
            
            try {
                HttpUrl url = HttpUrl.parse(baseUrl + "/sistema")
                    .newBuilder()
                    .addQueryParameter("claveGrupoEmpresa", claveGrupoEmpresa)
                    .addQueryParameter("claveEmpresa", claveEmpresa)
                    .addQueryParameter("nuevaFecha", nuevaFecha.toString())
                    .build();
                
                Request httpRequest = new Request.Builder()
                    .url(url)
                    .put(RequestBody.create("", JSON))
                    .build();
                
                logger.debug("Actualizando fecha del sistema a {} para empresa {}-{}", 
                           nuevaFecha, claveGrupoEmpresa, claveEmpresa);
                
                try (Response response = httpClient.newCall(httpRequest).execute()) {
                    String responseBody = response.body().string();
                    
                    if (response.isSuccessful()) {
                        successCounter.increment();
                        TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>() {};
                        Map<String, Object> resultado = objectMapper.readValue(responseBody, typeRef);
                        logger.info("Fecha del sistema actualizada a {}", nuevaFecha);
                        return resultado;
                    } else {
                        errorCounter.increment();
                        logger.error("Error actualizando fecha: HTTP {}", response.code());
                        throw new RuntimeException("Error HTTP " + response.code() + ": " + responseBody);
                    }
                }
                
            } catch (Exception e) {
                errorCounter.increment();
                logger.error("Error actualizando fecha: {}", e.getMessage(), e);
                throw new RuntimeException("Error actualizando fecha", e);
            } finally {
                sample.stop(requestTimer);
            }
        });
    }
    
    /**
     * Valida si una fecha es día hábil
     */
    public CompletableFuture<Map<String, Object>> validarDiaHabil(LocalDate fecha) {
        return CompletableFuture.supplyAsync(() -> {
            Timer.Sample sample = Timer.start();
            
            try {
                HttpUrl url = HttpUrl.parse(baseUrl + "/validar-dia-habil")
                    .newBuilder()
                    .addQueryParameter("fecha", fecha.toString())
                    .build();
                
                Request httpRequest = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
                
                logger.debug("Validando si {} es día hábil", fecha);
                
                try (Response response = httpClient.newCall(httpRequest).execute()) {
                    String responseBody = response.body().string();
                    
                    if (response.isSuccessful()) {
                        successCounter.increment();
                        TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>() {};
                        Map<String, Object> validacion = objectMapper.readValue(responseBody, typeRef);
                        boolean esDiaHabil = (Boolean) validacion.get("esDiaHabil");
                        logger.debug("Fecha {} es día hábil: {}", fecha, esDiaHabil);
                        return validacion;
                    } else {
                        errorCounter.increment();
                        logger.error("Error validando día hábil: HTTP {}", response.code());
                        throw new RuntimeException("Error HTTP " + response.code() + ": " + responseBody);
                    }
                }
                
            } catch (Exception e) {
                errorCounter.increment();
                logger.error("Error validando día hábil: {}", e.getMessage(), e);
                throw new RuntimeException("Error validando día hábil", e);
            } finally {
                sample.stop(requestTimer);
            }
        });
    }
    
    // Métodos de conveniencia
    
    /**
     * Obtiene solo la fecha actual del sistema
     */
    public CompletableFuture<LocalDate> obtenerFechaActual(String claveGrupoEmpresa, String claveEmpresa) {
        return obtenerFechaSistema(claveGrupoEmpresa, claveEmpresa)
            .thenApply(response -> {
                String fechaStr = (String) response.get("fechaSistema");
                return LocalDate.parse(fechaStr);
            });
    }
    
    /**
     * Verifica si una fecha es día hábil (método simplificado)
     */
    public CompletableFuture<Boolean> esDiaHabil(LocalDate fecha) {
        return validarDiaHabil(fecha)
            .thenApply(response -> (Boolean) response.get("esDiaHabil"));
    }
    
    /**
     * Obtiene el siguiente día hábil para una fecha
     */
    public CompletableFuture<LocalDate> obtenerSiguienteDiaHabil(LocalDate fecha) {
        return validarDiaHabil(fecha)
            .thenApply(response -> {
                String siguienteDiaStr = (String) response.get("siguienteDiaHabil");
                return LocalDate.parse(siguienteDiaStr);
            });
    }
    
    /**
     * Avanza múltiples días hábiles
     */
    public CompletableFuture<Map<String, Object>> avanzarDiasHabiles(String claveGrupoEmpresa, 
                                                                    String claveEmpresa, 
                                                                    int diasAAvanzar) {
        CompletableFuture<Map<String, Object>> future = CompletableFuture.completedFuture(null);
        
        for (int i = 0; i < diasAAvanzar; i++) {
            future = future.thenCompose(result -> recorrerFecha(claveGrupoEmpresa, claveEmpresa));
        }
        
        return future;
    }
}