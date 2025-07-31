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

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Cliente para operaciones de procesamiento de movimientos
 * 
 * Maneja las operaciones principales del procesador de movimientos:
 * - Procesar pre-movimientos a virtuales
 * - Procesar virtuales a reales  
 * - Cancelar movimientos
 * - Consultar movimientos y saldos
 */
public class MovementProcessorClient {
    
    private static final Logger logger = LoggerFactory.getLogger(MovementProcessorClient.class);
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final Http2Configuration configuration;
    private final String baseUrl;
    
    // Métricas
    private final Timer processingTimer;
    private final Counter successCounter;
    private final Counter errorCounter;
    
    public MovementProcessorClient(OkHttpClient httpClient, ObjectMapper objectMapper, 
                                  Http2Configuration configuration, MeterRegistry meterRegistry) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.configuration = configuration;
        this.baseUrl = configuration.getBaseUrl() + "/api/v1/movimientos";
        
        // Inicializar métricas
        this.processingTimer = Timer.builder("financial_client_request_duration")
            .tag("service", "movement_processor")
            .register(meterRegistry);
        this.successCounter = Counter.builder("financial_client_requests_total")
            .tag("service", "movement_processor")
            .tag("status", "success")
            .register(meterRegistry);
        this.errorCounter = Counter.builder("financial_client_requests_total")
            .tag("service", "movement_processor")
            .tag("status", "error")
            .register(meterRegistry);
    }
    
    /**
     * Procesa pre-movimientos a estado virtual
     */
    public CompletableFuture<ApiResponse> procesarPreMovimientos(ProcesarMovimientosRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            Timer.Sample sample = Timer.start();
            
            try {
                String json = objectMapper.writeValueAsString(request);
                RequestBody body = RequestBody.create(json, JSON);
                
                Request httpRequest = new Request.Builder()
                    .url(baseUrl + "/procesar-pre-movimientos")
                    .post(body)
                    .build();
                
                logger.debug("Procesando pre-movimientos para empresa {}-{}", 
                           request.getClaveGrupoEmpresa(), request.getClaveEmpresa());
                
                try (Response response = httpClient.newCall(httpRequest).execute()) {
                    String responseBody = response.body().string();
                    
                    if (response.isSuccessful()) {
                        successCounter.increment();
                        logger.info("Pre-movimientos procesados exitosamente para empresa {}-{}", 
                                  request.getClaveGrupoEmpresa(), request.getClaveEmpresa());
                        return objectMapper.readValue(responseBody, ApiResponse.class);
                    } else {
                        errorCounter.increment();
                        logger.error("Error procesando pre-movimientos: HTTP {}", response.code());
                        throw new RuntimeException("Error HTTP " + response.code() + ": " + responseBody);
                    }
                }
                
            } catch (Exception e) {
                errorCounter.increment();
                logger.error("Error procesando pre-movimientos: {}", e.getMessage(), e);
                throw new RuntimeException("Error procesando pre-movimientos", e);
            } finally {
                sample.stop(processingTimer);
            }
        });
    }
    
    /**
     * Procesa movimientos virtuales a estado real
     */
    public CompletableFuture<ApiResponse> procesarMovimientosVirtualesAReales(ProcesarMovimientosRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            Timer.Sample sample = Timer.start();
            
            try {
                String json = objectMapper.writeValueAsString(request);
                RequestBody body = RequestBody.create(json, JSON);
                
                Request httpRequest = new Request.Builder()
                    .url(baseUrl + "/procesar-virtuales-a-reales")
                    .post(body)
                    .build();
                
                logger.debug("Procesando movimientos virtuales a reales para empresa {}-{}", 
                           request.getClaveGrupoEmpresa(), request.getClaveEmpresa());
                
                try (Response response = httpClient.newCall(httpRequest).execute()) {
                    String responseBody = response.body().string();
                    
                    if (response.isSuccessful()) {
                        successCounter.increment();
                        logger.info("Movimientos virtuales procesados a reales para empresa {}-{}", 
                                  request.getClaveGrupoEmpresa(), request.getClaveEmpresa());
                        return objectMapper.readValue(responseBody, ApiResponse.class);
                    } else {
                        errorCounter.increment();
                        logger.error("Error procesando virtuales a reales: HTTP {}", response.code());
                        throw new RuntimeException("Error HTTP " + response.code() + ": " + responseBody);
                    }
                }
                
            } catch (Exception e) {
                errorCounter.increment();
                logger.error("Error procesando virtuales a reales: {}", e.getMessage(), e);
                throw new RuntimeException("Error procesando virtuales a reales", e);
            } finally {
                sample.stop(processingTimer);
            }
        });
    }
    
    /**
     * Cancela un movimiento específico
     */
    public CompletableFuture<ApiResponse> cancelarMovimiento(String claveGrupoEmpresa, String claveEmpresa, 
                                                           Long idMovimiento, String claveUsuario) {
        return CompletableFuture.supplyAsync(() -> {
            Timer.Sample sample = Timer.start();
            
            try {
                HttpUrl url = HttpUrl.parse(baseUrl + "/" + claveGrupoEmpresa + "/" + 
                                          claveEmpresa + "/" + idMovimiento + "/cancelar")
                    .newBuilder()
                    .addQueryParameter("claveUsuario", claveUsuario)
                    .build();
                
                Request httpRequest = new Request.Builder()
                    .url(url)
                    .post(RequestBody.create("", JSON))
                    .build();
                
                logger.debug("Cancelando movimiento {} para empresa {}-{}", 
                           idMovimiento, claveGrupoEmpresa, claveEmpresa);
                
                try (Response response = httpClient.newCall(httpRequest).execute()) {
                    String responseBody = response.body().string();
                    
                    if (response.isSuccessful()) {
                        successCounter.increment();
                        logger.info("Movimiento {} cancelado exitosamente", idMovimiento);
                        return objectMapper.readValue(responseBody, ApiResponse.class);
                    } else {
                        errorCounter.increment();
                        logger.error("Error cancelando movimiento: HTTP {}", response.code());
                        throw new RuntimeException("Error HTTP " + response.code() + ": " + responseBody);
                    }
                }
                
            } catch (Exception e) {
                errorCounter.increment();
                logger.error("Error cancelando movimiento {}: {}", idMovimiento, e.getMessage(), e);
                throw new RuntimeException("Error cancelando movimiento", e);
            } finally {
                sample.stop(processingTimer);
            }
        });
    }
    
    /**
     * Consulta movimientos por empresa
     */
    public CompletableFuture<List<MovimientoResponse>> consultarMovimientos(String claveGrupoEmpresa, 
                                                                          String claveEmpresa, 
                                                                          String situacion, 
                                                                          LocalDate fechaOperacion) {
        return CompletableFuture.supplyAsync(() -> {
            Timer.Sample sample = Timer.start();
            
            try {
                HttpUrl.Builder urlBuilder = HttpUrl.parse(baseUrl + "/" + claveGrupoEmpresa + "/" + claveEmpresa)
                    .newBuilder();
                
                if (situacion != null) {
                    urlBuilder.addQueryParameter("situacion", situacion);
                }
                if (fechaOperacion != null) {
                    urlBuilder.addQueryParameter("fechaOperacion", fechaOperacion.toString());
                }
                
                Request httpRequest = new Request.Builder()
                    .url(urlBuilder.build())
                    .get()
                    .build();
                
                logger.debug("Consultando movimientos para empresa {}-{}", claveGrupoEmpresa, claveEmpresa);
                
                try (Response response = httpClient.newCall(httpRequest).execute()) {
                    String responseBody = response.body().string();
                    
                    if (response.isSuccessful()) {
                        successCounter.increment();
                        TypeReference<List<MovimientoResponse>> typeRef = new TypeReference<List<MovimientoResponse>>() {};
                        List<MovimientoResponse> movimientos = objectMapper.readValue(responseBody, typeRef);
                        logger.debug("Consultados {} movimientos", movimientos.size());
                        return movimientos;
                    } else {
                        errorCounter.increment();
                        logger.error("Error consultando movimientos: HTTP {}", response.code());
                        throw new RuntimeException("Error HTTP " + response.code() + ": " + responseBody);
                    }
                }
                
            } catch (Exception e) {
                errorCounter.increment();
                logger.error("Error consultando movimientos: {}", e.getMessage(), e);
                throw new RuntimeException("Error consultando movimientos", e);
            } finally {
                sample.stop(processingTimer);
            }
        });
    }
    
    /**
     * Consulta un movimiento específico por ID
     */
    public CompletableFuture<MovimientoResponse> consultarMovimiento(String claveGrupoEmpresa, 
                                                                   String claveEmpresa, 
                                                                   Long idMovimiento) {
        return CompletableFuture.supplyAsync(() -> {
            Timer.Sample sample = Timer.start();
            
            try {
                String url = baseUrl + "/" + claveGrupoEmpresa + "/" + claveEmpresa + "/" + idMovimiento;
                
                Request httpRequest = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
                
                logger.debug("Consultando movimiento {} para empresa {}-{}", 
                           idMovimiento, claveGrupoEmpresa, claveEmpresa);
                
                try (Response response = httpClient.newCall(httpRequest).execute()) {
                    String responseBody = response.body().string();
                    
                    if (response.isSuccessful()) {
                        successCounter.increment();
                        MovimientoResponse movimiento = objectMapper.readValue(responseBody, MovimientoResponse.class);
                        logger.debug("Movimiento consultado: {}", movimiento);
                        return movimiento;
                    } else {
                        errorCounter.increment();
                        logger.error("Error consultando movimiento: HTTP {}", response.code());
                        throw new RuntimeException("Error HTTP " + response.code() + ": " + responseBody);
                    }
                }
                
            } catch (Exception e) {
                errorCounter.increment();
                logger.error("Error consultando movimiento {}: {}", idMovimiento, e.getMessage(), e);
                throw new RuntimeException("Error consultando movimiento", e);
            } finally {
                sample.stop(processingTimer);
            }
        });
    }
    
    /**
     * Consulta saldos por empresa
     */
    public CompletableFuture<List<SaldoResponse>> consultarSaldos(String claveGrupoEmpresa, 
                                                                String claveEmpresa, 
                                                                LocalDate fechaFoto, 
                                                                Long idCuenta) {
        return CompletableFuture.supplyAsync(() -> {
            Timer.Sample sample = Timer.start();
            
            try {
                HttpUrl.Builder urlBuilder = HttpUrl.parse(baseUrl + "/saldos/" + claveGrupoEmpresa + "/" + claveEmpresa)
                    .newBuilder();
                
                if (fechaFoto != null) {
                    urlBuilder.addQueryParameter("fechaFoto", fechaFoto.toString());
                }
                if (idCuenta != null) {
                    urlBuilder.addQueryParameter("idCuenta", idCuenta.toString());
                }
                
                Request httpRequest = new Request.Builder()
                    .url(urlBuilder.build())
                    .get()
                    .build();
                
                logger.debug("Consultando saldos para empresa {}-{}", claveGrupoEmpresa, claveEmpresa);
                
                try (Response response = httpClient.newCall(httpRequest).execute()) {
                    String responseBody = response.body().string();
                    
                    if (response.isSuccessful()) {
                        successCounter.increment();
                        TypeReference<List<SaldoResponse>> typeRef = new TypeReference<List<SaldoResponse>>() {};
                        List<SaldoResponse> saldos = objectMapper.readValue(responseBody, typeRef);
                        logger.debug("Consultados {} saldos", saldos.size());
                        return saldos;
                    } else {
                        errorCounter.increment();
                        logger.error("Error consultando saldos: HTTP {}", response.code());
                        throw new RuntimeException("Error HTTP " + response.code() + ": " + responseBody);
                    }
                }
                
            } catch (Exception e) {
                errorCounter.increment();
                logger.error("Error consultando saldos: {}", e.getMessage(), e);
                throw new RuntimeException("Error consultando saldos", e);
            } finally {
                sample.stop(processingTimer);
            }
        });
    }
    
    // Métodos de conveniencia
    
    /**
     * Consulta movimientos virtuales pendientes
     */
    public CompletableFuture<List<MovimientoResponse>> consultarMovimientosVirtuales(String claveGrupoEmpresa, 
                                                                                   String claveEmpresa) {
        return consultarMovimientos(claveGrupoEmpresa, claveEmpresa, "PV", null);
    }
    
    /**
     * Consulta movimientos reales
     */
    public CompletableFuture<List<MovimientoResponse>> consultarMovimientosReales(String claveGrupoEmpresa, 
                                                                                String claveEmpresa) {
        return consultarMovimientos(claveGrupoEmpresa, claveEmpresa, "PR", null);
    }
    
    /**
     * Consulta saldos actuales
     */
    public CompletableFuture<List<SaldoResponse>> consultarSaldosActuales(String claveGrupoEmpresa, 
                                                                         String claveEmpresa) {
        return consultarSaldos(claveGrupoEmpresa, claveEmpresa, null, null);
    }
    
    /**
     * Flujo completo: procesar pre-movimientos y luego a reales
     */
    public CompletableFuture<ApiResponse> procesamientoCompleto(String claveGrupoEmpresa, 
                                                              String claveEmpresa, 
                                                              LocalDate fechaProceso, 
                                                              String claveUsuario) {
        ProcesarMovimientosRequest request = ProcesarMovimientosRequest.builder()
            .empresa(claveGrupoEmpresa, claveEmpresa)
            .fechaProceso(fechaProceso)
            .claveUsuario(claveUsuario)
            .build();
            
        return procesarPreMovimientos(request)
            .thenCompose(response -> {
                if (response.isSuccess()) {
                    logger.info("Pre-movimientos procesados, iniciando procesamiento a reales...");
                    return procesarMovimientosVirtualesAReales(request);
                } else {
                    throw new RuntimeException("Error en procesamiento de pre-movimientos: " + response.getMessage());
                }
            });
    }
}