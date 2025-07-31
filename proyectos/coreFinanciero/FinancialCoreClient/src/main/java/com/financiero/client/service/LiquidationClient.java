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
 * Cliente para operaciones de liquidación
 * 
 * Maneja las operaciones relacionadas con liquidación:
 * - Crear fechas de liquidación del año
 * - Validar fechas de liquidación
 * - Gestionar días hábiles
 */
public class LiquidationClient {
    
    private static final Logger logger = LoggerFactory.getLogger(LiquidationClient.class);
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final Http2Configuration configuration;
    private final String baseUrl;
    
    // Métricas
    private final Timer requestTimer;
    private final Counter successCounter;
    private final Counter errorCounter;
    
    public LiquidationClient(OkHttpClient httpClient, ObjectMapper objectMapper, 
                            Http2Configuration configuration, MeterRegistry meterRegistry) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.configuration = configuration;
        this.baseUrl = configuration.getBaseUrl() + "/api/v1/liquidacion";
        
        // Inicializar métricas
        this.requestTimer = Timer.builder("financial_client_request_duration")
            .tag("service", "liquidation")
            .register(meterRegistry);
        this.successCounter = Counter.builder("financial_client_requests_total")
            .tag("service", "liquidation")
            .tag("status", "success")
            .register(meterRegistry);
        this.errorCounter = Counter.builder("financial_client_requests_total")
            .tag("service", "liquidation")
            .tag("status", "error")
            .register(meterRegistry);
    }
    
    /**
     * Crea todas las fechas de liquidación para un año
     */
    public CompletableFuture<Map<String, Object>> crearFechasLiquidacionAnio(String claveGrupoEmpresa, 
                                                                            String claveEmpresa, 
                                                                            int anio) {
        return CompletableFuture.supplyAsync(() -> {
            Timer.Sample sample = Timer.start();
            
            try {
                HttpUrl url = HttpUrl.parse(baseUrl + "/crear-fechas-anio")
                    .newBuilder()
                    .addQueryParameter("claveGrupoEmpresa", claveGrupoEmpresa)
                    .addQueryParameter("claveEmpresa", claveEmpresa)
                    .addQueryParameter("anio", String.valueOf(anio))
                    .build();
                
                Request httpRequest = new Request.Builder()
                    .url(url)
                    .post(RequestBody.create("", JSON))
                    .build();
                
                logger.debug("Creando fechas de liquidación para el año {} en empresa {}-{}", 
                           anio, claveGrupoEmpresa, claveEmpresa);
                
                try (Response response = httpClient.newCall(httpRequest).execute()) {
                    String responseBody = response.body().string();
                    
                    if (response.isSuccessful()) {
                        successCounter.increment();
                        TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>() {};
                        Map<String, Object> resultado = objectMapper.readValue(responseBody, typeRef);
                        logger.info("Fechas de liquidación creadas para el año {}: {} fechas", 
                                  anio, resultado.get("fechas_creadas"));
                        return resultado;
                    } else {
                        errorCounter.increment();
                        logger.error("Error creando fechas de liquidación: HTTP {}", response.code());
                        throw new RuntimeException("Error HTTP " + response.code() + ": " + responseBody);
                    }
                }
                
            } catch (Exception e) {
                errorCounter.increment();
                logger.error("Error creando fechas de liquidación: {}", e.getMessage(), e);
                throw new RuntimeException("Error creando fechas de liquidación", e);
            } finally {
                sample.stop(requestTimer);
            }
        });
    }
    
    /**
     * Valida una fecha de liquidación específica
     */
    public CompletableFuture<Map<String, Object>> validarFechaLiquidacion(String claveGrupoEmpresa, 
                                                                          String claveEmpresa, 
                                                                          LocalDate fechaOperacion, 
                                                                          LocalDate fechaLiquidacion, 
                                                                          String claveMercado) {
        return CompletableFuture.supplyAsync(() -> {
            Timer.Sample sample = Timer.start();
            
            try {
                HttpUrl url = HttpUrl.parse(baseUrl + "/validar-fecha")
                    .newBuilder()
                    .addQueryParameter("claveGrupoEmpresa", claveGrupoEmpresa)
                    .addQueryParameter("claveEmpresa", claveEmpresa)
                    .addQueryParameter("fechaOperacion", fechaOperacion.toString())
                    .addQueryParameter("fechaLiquidacion", fechaLiquidacion.toString())
                    .addQueryParameter("claveMercado", claveMercado)
                    .build();
                
                Request httpRequest = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
                
                logger.debug("Validando fecha de liquidación {} para operación {} en mercado {}", 
                           fechaLiquidacion, fechaOperacion, claveMercado);
                
                try (Response response = httpClient.newCall(httpRequest).execute()) {
                    String responseBody = response.body().string();
                    
                    if (response.isSuccessful()) {
                        successCounter.increment();
                        TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>() {};
                        Map<String, Object> validacion = objectMapper.readValue(responseBody, typeRef);
                        boolean esValida = (Boolean) validacion.get("valida");
                        logger.debug("Fecha de liquidación {} es válida: {} ({})", 
                                   fechaLiquidacion, esValida, validacion.get("tipoLiquidacion"));
                        return validacion;
                    } else {
                        errorCounter.increment();
                        logger.error("Error validando fecha de liquidación: HTTP {}", response.code());
                        throw new RuntimeException("Error HTTP " + response.code() + ": " + responseBody);
                    }
                }
                
            } catch (Exception e) {
                errorCounter.increment();
                logger.error("Error validando fecha de liquidación: {}", e.getMessage(), e);
                throw new RuntimeException("Error validando fecha de liquidación", e);
            } finally {
                sample.stop(requestTimer);
            }
        });
    }
    
    // Métodos de conveniencia
    
    /**
     * Valida si una fecha de liquidación es correcta (método simplificado)
     */
    public CompletableFuture<Boolean> esFechaLiquidacionValida(String claveGrupoEmpresa, 
                                                              String claveEmpresa, 
                                                              LocalDate fechaOperacion, 
                                                              LocalDate fechaLiquidacion, 
                                                              String claveMercado) {
        return validarFechaLiquidacion(claveGrupoEmpresa, claveEmpresa, fechaOperacion, 
                                     fechaLiquidacion, claveMercado)
            .thenApply(response -> (Boolean) response.get("valida"));
    }
    
    /**
     * Obtiene el tipo de liquidación (T+0, T+1, etc.)
     */
    public CompletableFuture<String> obtenerTipoLiquidacion(String claveGrupoEmpresa, 
                                                           String claveEmpresa, 
                                                           LocalDate fechaOperacion, 
                                                           LocalDate fechaLiquidacion, 
                                                           String claveMercado) {
        return validarFechaLiquidacion(claveGrupoEmpresa, claveEmpresa, fechaOperacion, 
                                     fechaLiquidacion, claveMercado)
            .thenApply(response -> (String) response.get("tipoLiquidacion"));
    }
    
    /**
     * Crea fechas de liquidación para múltiples años
     */
    public CompletableFuture<Map<String, Object>> crearFechasMultiplesAnios(String claveGrupoEmpresa, 
                                                                           String claveEmpresa, 
                                                                           int anioInicio, 
                                                                           int anioFin) {
        CompletableFuture<Map<String, Object>> future = CompletableFuture.completedFuture(
            Map.of("anios_procesados", 0, "total_fechas_creadas", 0)
        );
        
        for (int anio = anioInicio; anio <= anioFin; anio++) {
            final int anioActual = anio;
            future = future.thenCompose(resultado -> 
                crearFechasLiquidacionAnio(claveGrupoEmpresa, claveEmpresa, anioActual)
                    .thenApply(nuevoResultado -> {
                        int aniosProcesados = (Integer) resultado.get("anios_procesados") + 1;
                        int totalFechas = (Integer) resultado.get("total_fechas_creadas") + 
                                        (Integer) nuevoResultado.get("fechas_creadas");
                        
                        return Map.of(
                            "anios_procesados", aniosProcesados,
                            "total_fechas_creadas", totalFechas,
                            "ultimo_anio_procesado", anioActual
                        );
                    })
            );
        }
        
        return future;
    }
    
    /**
     * Validación de fechas para operaciones comunes
     */
    public CompletableFuture<Boolean> validarDepositoT0(String claveGrupoEmpresa, 
                                                       String claveEmpresa, 
                                                       LocalDate fechaOperacion) {
        return esFechaLiquidacionValida(claveGrupoEmpresa, claveEmpresa, 
                                       fechaOperacion, fechaOperacion, "DEPOSITO");
    }
    
    public CompletableFuture<Boolean> validarDepositoT1(String claveGrupoEmpresa, 
                                                       String claveEmpresa, 
                                                       LocalDate fechaOperacion) {
        LocalDate fechaLiquidacion = fechaOperacion.plusDays(1);
        return esFechaLiquidacionValida(claveGrupoEmpresa, claveEmpresa, 
                                       fechaOperacion, fechaLiquidacion, "DEPOSITO");
    }
    
    public CompletableFuture<Boolean> validarPrestamoT0(String claveGrupoEmpresa, 
                                                       String claveEmpresa, 
                                                       LocalDate fechaOperacion) {
        return esFechaLiquidacionValida(claveGrupoEmpresa, claveEmpresa, 
                                       fechaOperacion, fechaOperacion, "PRESTAMO");
    }
    
    /**
     * Inicialización de fechas para una nueva empresa
     */
    public CompletableFuture<Map<String, Object>> inicializarFechasEmpresa(String claveGrupoEmpresa, 
                                                                          String claveEmpresa, 
                                                                          int anioActual) {
        logger.info("Inicializando fechas de liquidación para empresa {}-{}", 
                   claveGrupoEmpresa, claveEmpresa);
        
        // Crear fechas para el año actual y siguiente
        return crearFechasMultiplesAnios(claveGrupoEmpresa, claveEmpresa, anioActual, anioActual + 1)
            .thenApply(resultado -> {
                logger.info("Inicialización completada para empresa {}-{}: {} fechas creadas", 
                           claveGrupoEmpresa, claveEmpresa, resultado.get("total_fechas_creadas"));
                return resultado;
            });
    }
}