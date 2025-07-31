package com.financiero.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.financiero.client.config.Http2Configuration;
import com.financiero.client.service.DateSystemClient;
import com.financiero.client.service.LiquidationClient;
import com.financiero.client.service.MovementClient;
import com.financiero.client.service.MovementProcessorClient;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Cliente principal HTTP/2 para el Core Financiero
 * 
 * Proporciona acceso a todos los servicios del core financiero usando HTTP/2
 * con las siguientes características:
 * - Multiplexing de requests HTTP/2
 * - Pool de conexiones optimizado
 * - Métricas integradas
 * - Manejo de errores robusto
 * - Operaciones asíncronas
 */
public class FinancialCoreHttp2Client implements Closeable {
    
    private static final Logger logger = LoggerFactory.getLogger(FinancialCoreHttp2Client.class);
    
    private final Http2Configuration configuration;
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final MeterRegistry meterRegistry;
    private final ExecutorService executorService;
    
    // Clientes de servicios
    private final MovementProcessorClient movementProcessorClient;
    private final MovementClient movementClient;
    private final LiquidationClient liquidationClient;
    private final DateSystemClient dateSystemClient;
    
    /**
     * Constructor principal
     */
    public FinancialCoreHttp2Client(Http2Configuration configuration) {
        this.configuration = configuration;
        this.httpClient = configuration.createHttp2Client();
        this.objectMapper = createObjectMapper();
        this.meterRegistry = new SimpleMeterRegistry();
        this.executorService = Executors.newCachedThreadPool(r -> {
            Thread thread = new Thread(r, "FinancialCoreClient-" + System.currentTimeMillis());
            thread.setDaemon(true);
            return thread;
        });
        
        // Inicializar clientes de servicios
        this.movementProcessorClient = new MovementProcessorClient(httpClient, objectMapper, configuration, meterRegistry);
        this.movementClient = new MovementClient(httpClient, objectMapper, configuration, meterRegistry);
        this.liquidationClient = new LiquidationClient(httpClient, objectMapper, configuration, meterRegistry);
        this.dateSystemClient = new DateSystemClient(httpClient, objectMapper, configuration, meterRegistry);
        
        logger.info("FinancialCoreHttp2Client inicializado - BaseURL: {}", configuration.getBaseUrl());
    }
    
    /**
     * Constructor con configuración por defecto
     */
    public FinancialCoreHttp2Client() {
        this(Http2Configuration.defaultConfig());
    }
    
    /**
     * Constructor con URL base personalizada
     */
    public FinancialCoreHttp2Client(String baseUrl) {
        this(new Http2Configuration.Builder().baseUrl(baseUrl).build());
    }
    
    /**
     * Crea ObjectMapper configurado para el cliente
     */
    private ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
    
    // Getters para acceso a los clientes de servicios
    
    /**
     * Cliente para operaciones de procesamiento de movimientos
     * - Procesar pre-movimientos a virtuales
     * - Procesar virtuales a reales
     * - Cancelar movimientos
     * - Consultar movimientos y saldos
     */
    public MovementProcessorClient movementProcessor() {
        return movementProcessorClient;
    }
    
    /**
     * Cliente para operaciones de movimientos
     * - Crear pre-movimientos
     * - Agregar detalles
     * - Consultar pre-movimientos
     */
    public MovementClient movement() {
        return movementClient;
    }
    
    /**
     * Cliente para operaciones de liquidación
     * - Validar fechas de liquidación
     * - Crear fechas de liquidación
     */
    public LiquidationClient liquidation() {
        return liquidationClient;
    }
    
    /**
     * Cliente para operaciones del sistema de fechas
     * - Obtener fecha del sistema
     * - Recorrer fecha
     * - Validar días hábiles
     */
    public DateSystemClient dateSystem() {
        return dateSystemClient;
    }
    
    // Métodos de utilidad
    
    /**
     * Obtiene la configuración del cliente
     */
    public Http2Configuration getConfiguration() {
        return configuration;
    }
    
    /**
     * Obtiene el cliente HTTP subyacente
     */
    public OkHttpClient getHttpClient() {
        return httpClient;
    }
    
    /**
     * Obtiene el ObjectMapper configurado
     */
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
    
    /**
     * Obtiene el registro de métricas
     */
    public MeterRegistry getMeterRegistry() {
        return meterRegistry;
    }
    
    /**
     * Obtiene el ExecutorService para operaciones asíncronas
     */
    public ExecutorService getExecutorService() {
        return executorService;
    }
    
    /**
     * Verifica la conectividad con el servidor
     */
    public boolean isHealthy() {
        try {
            // Intentar obtener la fecha del sistema como health check
            dateSystemClient.obtenerFechaSistema("001", "001").get(5, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            logger.warn("Health check falló: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Obtiene estadísticas de conexión HTTP/2
     */
    public ConnectionStats getConnectionStats() {
        return new ConnectionStats(
            httpClient.connectionPool().connectionCount(),
            httpClient.connectionPool().idleConnectionCount()
        );
    }
    
    /**
     * Cierra el cliente y libera recursos
     */
    @Override
    public void close() throws IOException {
        logger.info("Cerrando FinancialCoreHttp2Client...");
        
        try {
            // Cerrar executor service
            executorService.shutdown();
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
        
        // Cerrar conexiones HTTP
        httpClient.dispatcher().executorService().shutdown();
        httpClient.connectionPool().evictAll();
        
        logger.info("FinancialCoreHttp2Client cerrado");
    }
    
    /**
     * Estadísticas de conexión
     */
    public static class ConnectionStats {
        private final int totalConnections;
        private final int idleConnections;
        
        public ConnectionStats(int totalConnections, int idleConnections) {
            this.totalConnections = totalConnections;
            this.idleConnections = idleConnections;
        }
        
        public int getTotalConnections() { return totalConnections; }
        public int getIdleConnections() { return idleConnections; }
        public int getActiveConnections() { return totalConnections - idleConnections; }
        
        @Override
        public String toString() {
            return String.format("ConnectionStats{total=%d, active=%d, idle=%d}", 
                               totalConnections, getActiveConnections(), idleConnections);
        }
    }
    
    /**
     * Factory methods para configuraciones comunes
     */
    public static class Factory {
        
        /**
         * Cliente para desarrollo local
         */
        public static FinancialCoreHttp2Client forDevelopment() {
            return new FinancialCoreHttp2Client(Http2Configuration.defaultConfig());
        }
        
        /**
         * Cliente para testing
         */
        public static FinancialCoreHttp2Client forTesting() {
            return new FinancialCoreHttp2Client(Http2Configuration.testingConfig());
        }
        
        /**
         * Cliente para producción
         */
        public static FinancialCoreHttp2Client forProduction(String baseUrl) {
            return new FinancialCoreHttp2Client(Http2Configuration.productionConfig(baseUrl));
        }
        
        /**
         * Cliente con configuración personalizada
         */
        public static FinancialCoreHttp2Client withConfiguration(Http2Configuration configuration) {
            return new FinancialCoreHttp2Client(configuration);
        }
    }
}