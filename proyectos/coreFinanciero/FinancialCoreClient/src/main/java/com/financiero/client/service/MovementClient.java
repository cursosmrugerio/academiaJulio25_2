package com.financiero.client.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.financiero.client.config.Http2Configuration;
import com.financiero.client.model.*;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Cliente para operaciones de movimientos (pre-movimientos)
 * 
 * Maneja la creación y gestión de pre-movimientos:
 * - Crear pre-movimientos
 * - Agregar detalles de conceptos
 * - Consultar pre-movimientos
 * - Calcular totales
 */
public class MovementClient {
    
    private static final Logger logger = LoggerFactory.getLogger(MovementClient.class);
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final Http2Configuration configuration;
    private final String baseUrl;
    
    // Métricas
    private final Timer requestTimer;
    private final Counter successCounter;
    private final Counter errorCounter;
    
    public MovementClient(OkHttpClient httpClient, ObjectMapper objectMapper, 
                         Http2Configuration configuration, MeterRegistry meterRegistry) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.configuration = configuration;
        this.baseUrl = configuration.getBaseUrl() + "/api/v1/movimientos";
        
        // Inicializar métricas
        this.requestTimer = Timer.builder("financial_client_request_duration")
            .tag("service", "movement")
            .register(meterRegistry);
        this.successCounter = Counter.builder("financial_client_requests_total")
            .tag("service", "movement")
            .tag("status", "success")
            .register(meterRegistry);
        this.errorCounter = Counter.builder("financial_client_requests_total")
            .tag("service", "movement")
            .tag("status", "error")
            .register(meterRegistry);
    }
    
    /**
     * Crea un nuevo pre-movimiento
     */
    public CompletableFuture<ApiResponse> crearPreMovimiento(PreMovimientoRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            Timer.Sample sample = Timer.start();
            
            try {
                String json = objectMapper.writeValueAsString(request);
                RequestBody body = RequestBody.create(json, JSON);
                
                Request httpRequest = new Request.Builder()
                    .url(baseUrl + "/pre-movimiento")
                    .post(body)
                    .build();
                
                logger.debug("Creando pre-movimiento {} para empresa {}-{}", 
                           request.getIdPreMovimiento(), request.getClaveGrupoEmpresa(), request.getClaveEmpresa());
                
                try (Response response = httpClient.newCall(httpRequest).execute()) {
                    String responseBody = response.body().string();
                    
                    if (response.isSuccessful()) {
                        successCounter.increment();
                        logger.info("Pre-movimiento {} creado exitosamente", request.getIdPreMovimiento());
                        return objectMapper.readValue(responseBody, ApiResponse.class);
                    } else {
                        errorCounter.increment();
                        logger.error("Error creando pre-movimiento: HTTP {}", response.code());
                        throw new RuntimeException("Error HTTP " + response.code() + ": " + responseBody);
                    }
                }
                
            } catch (Exception e) {
                errorCounter.increment();
                logger.error("Error creando pre-movimiento: {}", e.getMessage(), e);
                throw new RuntimeException("Error creando pre-movimiento", e);
            } finally {
                sample.stop(requestTimer);
            }
        });
    }
    
    /**
     * Agrega un detalle de concepto a un pre-movimiento
     */
    public CompletableFuture<ApiResponse> agregarDetallePreMovimiento(String claveGrupoEmpresa, 
                                                                     String claveEmpresa,
                                                                     Long idPreMovimiento, 
                                                                     String claveConcepto, 
                                                                     BigDecimal importeConcepto, 
                                                                     String nota) {
        return CompletableFuture.supplyAsync(() -> {
            Timer.Sample sample = Timer.start();
            
            try {
                Map<String, Object> requestBody = Map.of(
                    "claveGrupoEmpresa", claveGrupoEmpresa,
                    "claveEmpresa", claveEmpresa,
                    "idPreMovimiento", idPreMovimiento,
                    "claveConcepto", claveConcepto,
                    "importeConcepto", importeConcepto,
                    "nota", nota != null ? nota : ""
                );
                
                String json = objectMapper.writeValueAsString(requestBody);
                RequestBody body = RequestBody.create(json, JSON);
                
                Request httpRequest = new Request.Builder()
                    .url(baseUrl + "/pre-movimiento-detalle")
                    .post(body)
                    .build();
                
                logger.debug("Agregando detalle {} al pre-movimiento {}", claveConcepto, idPreMovimiento);
                
                try (Response response = httpClient.newCall(httpRequest).execute()) {
                    String responseBodyStr = response.body().string();
                    
                    if (response.isSuccessful()) {
                        successCounter.increment();
                        logger.info("Detalle {} agregado al pre-movimiento {}", claveConcepto, idPreMovimiento);
                        return objectMapper.readValue(responseBodyStr, ApiResponse.class);
                    } else {
                        errorCounter.increment();
                        logger.error("Error agregando detalle: HTTP {}", response.code());
                        throw new RuntimeException("Error HTTP " + response.code() + ": " + responseBodyStr);
                    }
                }
                
            } catch (Exception e) {
                errorCounter.increment();
                logger.error("Error agregando detalle: {}", e.getMessage(), e);
                throw new RuntimeException("Error agregando detalle", e);
            } finally {
                sample.stop(requestTimer);
            }
        });
    }
    
    /**
     * Consulta un pre-movimiento específico
     */
    public CompletableFuture<Map<String, Object>> consultarPreMovimiento(String claveGrupoEmpresa, 
                                                                        String claveEmpresa, 
                                                                        Long idPreMovimiento) {
        return CompletableFuture.supplyAsync(() -> {
            Timer.Sample sample = Timer.start();
            
            try {
                String url = baseUrl + "/pre-movimiento/" + claveGrupoEmpresa + "/" + 
                           claveEmpresa + "/" + idPreMovimiento;
                
                Request httpRequest = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
                
                logger.debug("Consultando pre-movimiento {} para empresa {}-{}", 
                           idPreMovimiento, claveGrupoEmpresa, claveEmpresa);
                
                try (Response response = httpClient.newCall(httpRequest).execute()) {
                    String responseBody = response.body().string();
                    
                    if (response.isSuccessful()) {
                        successCounter.increment();
                        TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>() {};
                        Map<String, Object> preMovimiento = objectMapper.readValue(responseBody, typeRef);
                        logger.debug("Pre-movimiento consultado: {}", idPreMovimiento);
                        return preMovimiento;
                    } else {
                        errorCounter.increment();
                        logger.error("Error consultando pre-movimiento: HTTP {}", response.code());
                        throw new RuntimeException("Error HTTP " + response.code() + ": " + responseBody);
                    }
                }
                
            } catch (Exception e) {
                errorCounter.increment();
                logger.error("Error consultando pre-movimiento {}: {}", idPreMovimiento, e.getMessage(), e);
                throw new RuntimeException("Error consultando pre-movimiento", e);
            } finally {
                sample.stop(requestTimer);
            }
        });
    }
    
    /**
     * Consulta pre-movimientos pendientes por fecha de liquidación
     */
    public CompletableFuture<List<Map<String, Object>>> consultarMovimientosPendientes(String claveGrupoEmpresa, 
                                                                                      String claveEmpresa, 
                                                                                      LocalDate fechaLiquidacion) {
        return CompletableFuture.supplyAsync(() -> {
            Timer.Sample sample = Timer.start();
            
            try {
                HttpUrl url = HttpUrl.parse(baseUrl + "/pendientes")
                    .newBuilder()
                    .addQueryParameter("claveGrupoEmpresa", claveGrupoEmpresa)
                    .addQueryParameter("claveEmpresa", claveEmpresa)
                    .addQueryParameter("fechaLiquidacion", fechaLiquidacion.toString())
                    .build();
                
                Request httpRequest = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
                
                logger.debug("Consultando movimientos pendientes para liquidación {}", fechaLiquidacion);
                
                try (Response response = httpClient.newCall(httpRequest).execute()) {
                    String responseBody = response.body().string();
                    
                    if (response.isSuccessful()) {
                        successCounter.increment();
                        TypeReference<List<Map<String, Object>>> typeRef = new TypeReference<List<Map<String, Object>>>() {};
                        List<Map<String, Object>> movimientos = objectMapper.readValue(responseBody, typeRef);
                        logger.debug("Consultados {} movimientos pendientes", movimientos.size());
                        return movimientos;
                    } else {
                        errorCounter.increment();
                        logger.error("Error consultando pendientes: HTTP {}", response.code());
                        throw new RuntimeException("Error HTTP " + response.code() + ": " + responseBody);
                    }
                }
                
            } catch (Exception e) {
                errorCounter.increment();
                logger.error("Error consultando pendientes: {}", e.getMessage(), e);
                throw new RuntimeException("Error consultando pendientes", e);
            } finally {
                sample.stop(requestTimer);
            }
        });
    }
    
    /**
     * Obtiene los detalles de un pre-movimiento
     */
    public CompletableFuture<List<Map<String, Object>>> consultarDetallesPreMovimiento(String claveGrupoEmpresa, 
                                                                                      String claveEmpresa, 
                                                                                      Long idPreMovimiento) {
        return CompletableFuture.supplyAsync(() -> {
            Timer.Sample sample = Timer.start();
            
            try {
                String url = baseUrl + "/detalles/" + claveGrupoEmpresa + "/" + 
                           claveEmpresa + "/" + idPreMovimiento;
                
                Request httpRequest = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
                
                logger.debug("Consultando detalles del pre-movimiento {}", idPreMovimiento);
                
                try (Response response = httpClient.newCall(httpRequest).execute()) {
                    String responseBody = response.body().string();
                    
                    if (response.isSuccessful()) {
                        successCounter.increment();
                        TypeReference<List<Map<String, Object>>> typeRef = new TypeReference<List<Map<String, Object>>>() {};
                        List<Map<String, Object>> detalles = objectMapper.readValue(responseBody, typeRef);
                        logger.debug("Consultados {} detalles", detalles.size());
                        return detalles;
                    } else {
                        errorCounter.increment();
                        logger.error("Error consultando detalles: HTTP {}", response.code());
                        throw new RuntimeException("Error HTTP " + response.code() + ": " + responseBody);
                    }
                }
                
            } catch (Exception e) {
                errorCounter.increment();
                logger.error("Error consultando detalles: {}", e.getMessage(), e);
                throw new RuntimeException("Error consultando detalles", e);
            } finally {
                sample.stop(requestTimer);
            }
        });
    }
    
    /**
     * Calcula el total de conceptos de un pre-movimiento
     */
    public CompletableFuture<Map<String, Object>> calcularTotalConceptos(String claveGrupoEmpresa, 
                                                                        String claveEmpresa, 
                                                                        Long idPreMovimiento) {
        return CompletableFuture.supplyAsync(() -> {
            Timer.Sample sample = Timer.start();
            
            try {
                String url = baseUrl + "/total-conceptos/" + claveGrupoEmpresa + "/" + 
                           claveEmpresa + "/" + idPreMovimiento;
                
                Request httpRequest = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
                
                logger.debug("Calculando total de conceptos para pre-movimiento {}", idPreMovimiento);
                
                try (Response response = httpClient.newCall(httpRequest).execute()) {
                    String responseBody = response.body().string();
                    
                    if (response.isSuccessful()) {
                        successCounter.increment();
                        TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>() {};
                        Map<String, Object> total = objectMapper.readValue(responseBody, typeRef);
                        logger.debug("Total calculado para pre-movimiento {}: {}", 
                                   idPreMovimiento, total.get("totalConceptos"));
                        return total;
                    } else {
                        errorCounter.increment();
                        logger.error("Error calculando total: HTTP {}", response.code());
                        throw new RuntimeException("Error HTTP " + response.code() + ": " + responseBody);
                    }
                }
                
            } catch (Exception e) {
                errorCounter.increment();
                logger.error("Error calculando total: {}", e.getMessage(), e);
                throw new RuntimeException("Error calculando total", e);
            } finally {
                sample.stop(requestTimer);
            }
        });
    }
    
    // Métodos de conveniencia para operaciones comunes
    
    /**
     * Crea un pre-movimiento de depósito simple
     */
    public CompletableFuture<ApiResponse> crearDeposito(Long idPreMovimiento, 
                                                       String claveGrupoEmpresa, 
                                                       String claveEmpresa,
                                                       Long idCuenta, 
                                                       BigDecimal importe, 
                                                       LocalDate fechaLiquidacion,
                                                       String claveUsuario, 
                                                       String nota) {
        PreMovimientoRequest request = PreMovimientoRequest.builder()
            .empresa(claveGrupoEmpresa, claveEmpresa)
            .idPreMovimiento(idPreMovimiento)
            .deposito(importe, "MXN")
            .cuenta(idCuenta)
            .fechaLiquidacion(fechaLiquidacion)
            .usuario(claveUsuario)
            .nota(nota)
            .build();
            
        return crearPreMovimiento(request);
    }
    
    /**
     * Crea un pre-movimiento de retiro simple
     */
    public CompletableFuture<ApiResponse> crearRetiro(Long idPreMovimiento, 
                                                     String claveGrupoEmpresa, 
                                                     String claveEmpresa,
                                                     Long idCuenta, 
                                                     BigDecimal importe, 
                                                     LocalDate fechaLiquidacion,
                                                     String claveUsuario, 
                                                     String nota) {
        PreMovimientoRequest request = PreMovimientoRequest.builder()
            .empresa(claveGrupoEmpresa, claveEmpresa)
            .idPreMovimiento(idPreMovimiento)
            .retiro(importe, "MXN")
            .cuenta(idCuenta)
            .fechaLiquidacion(fechaLiquidacion)
            .usuario(claveUsuario)
            .nota(nota)
            .build();
            
        return crearPreMovimiento(request);
    }
    
    /**
     * Crea un pre-movimiento con múltiples conceptos
     */
    public CompletableFuture<ApiResponse> crearMovimientoConConceptos(PreMovimientoRequest preMovimiento,
                                                                     Map<String, BigDecimal> conceptos) {
        return crearPreMovimiento(preMovimiento)
            .thenCompose(response -> {
                if (response.isSuccess()) {
                    // Agregar cada concepto
                    CompletableFuture<ApiResponse> future = CompletableFuture.completedFuture(response);
                    
                    for (Map.Entry<String, BigDecimal> concepto : conceptos.entrySet()) {
                        future = future.thenCompose(r -> 
                            agregarDetallePreMovimiento(
                                preMovimiento.getClaveGrupoEmpresa(),
                                preMovimiento.getClaveEmpresa(),
                                preMovimiento.getIdPreMovimiento(),
                                concepto.getKey(),
                                concepto.getValue(),
                                "Concepto: " + concepto.getKey()
                            )
                        );
                    }
                    
                    return future;
                } else {
                    throw new RuntimeException("Error creando pre-movimiento: " + response.getMessage());
                }
            });
    }
    
    /**
     * Agrega conceptos comunes (intereses, comisiones)
     */
    public CompletableFuture<ApiResponse> agregarInteres(String claveGrupoEmpresa, 
                                                        String claveEmpresa,
                                                        Long idPreMovimiento, 
                                                        BigDecimal importe) {
        return agregarDetallePreMovimiento(claveGrupoEmpresa, claveEmpresa, idPreMovimiento, 
                                         "INTERES", importe, "Interés ganado");
    }
    
    public CompletableFuture<ApiResponse> agregarComision(String claveGrupoEmpresa, 
                                                         String claveEmpresa,
                                                         Long idPreMovimiento, 
                                                         BigDecimal importe) {
        return agregarDetallePreMovimiento(claveGrupoEmpresa, claveEmpresa, idPreMovimiento, 
                                         "COMISION", importe, "Comisión bancaria");
    }
    
    public CompletableFuture<ApiResponse> agregarImpuesto(String claveGrupoEmpresa, 
                                                         String claveEmpresa,
                                                         Long idPreMovimiento, 
                                                         BigDecimal importe) {
        return agregarDetallePreMovimiento(claveGrupoEmpresa, claveEmpresa, idPreMovimiento, 
                                         "ISR", importe, "Impuesto sobre la renta");
    }
}