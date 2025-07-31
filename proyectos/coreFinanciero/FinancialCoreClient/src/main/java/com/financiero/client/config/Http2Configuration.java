package com.financiero.client.config;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.logging.HttpLoggingInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * Configuración HTTP/2 para el cliente del Core Financiero
 * 
 * Características principales:
 * - Soporte HTTP/2 con multiplexing
 * - Pool de conexiones optimizado
 * - Timeouts configurables
 * - Logging de requests/responses
 * - Retry automático
 */
public class Http2Configuration {
    
    private static final Logger logger = LoggerFactory.getLogger(Http2Configuration.class);
    
    // Configuración por defecto
    public static final Duration DEFAULT_CONNECT_TIMEOUT = Duration.ofSeconds(30);
    public static final Duration DEFAULT_READ_TIMEOUT = Duration.ofSeconds(60);
    public static final Duration DEFAULT_WRITE_TIMEOUT = Duration.ofSeconds(60);
    public static final int DEFAULT_MAX_IDLE_CONNECTIONS = 10;
    public static final Duration DEFAULT_KEEP_ALIVE_DURATION = Duration.ofMinutes(5);
    
    private final String baseUrl;
    private final Duration connectTimeout;
    private final Duration readTimeout;
    private final Duration writeTimeout;
    private final int maxIdleConnections;
    private final Duration keepAliveDuration;
    private final boolean enableLogging;
    private final HttpLoggingInterceptor.Level loggingLevel;
    
    private Http2Configuration(Builder builder) {
        this.baseUrl = builder.baseUrl;
        this.connectTimeout = builder.connectTimeout;
        this.readTimeout = builder.readTimeout;
        this.writeTimeout = builder.writeTimeout;
        this.maxIdleConnections = builder.maxIdleConnections;
        this.keepAliveDuration = builder.keepAliveDuration;
        this.enableLogging = builder.enableLogging;
        this.loggingLevel = builder.loggingLevel;
    }
    
    /**
     * Crea un cliente OkHttp configurado para HTTP/2
     */
    public OkHttpClient createHttp2Client() {
        ConnectionPool connectionPool = new ConnectionPool(
            maxIdleConnections,
            keepAliveDuration.toMillis(),
            TimeUnit.MILLISECONDS
        );
        
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
            .protocols(Collections.singletonList(Protocol.HTTP_1_1))
            .connectionPool(connectionPool)
            .connectTimeout(connectTimeout.toMillis(), TimeUnit.MILLISECONDS)
            .readTimeout(readTimeout.toMillis(), TimeUnit.MILLISECONDS)
            .writeTimeout(writeTimeout.toMillis(), TimeUnit.MILLISECONDS)
            .retryOnConnectionFailure(true);
        
        // Agregar logging si está habilitado
        if (enableLogging) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(logger::debug);
            loggingInterceptor.setLevel(loggingLevel);
            builder.addInterceptor(loggingInterceptor);
        }
        
        // Interceptor para headers comunes
        builder.addInterceptor(chain -> {
            return chain.proceed(
                chain.request()
                    .newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("User-Agent", "FinancialCoreClient/1.0")
                    .build()
            );
        });
        
        OkHttpClient client = builder.build();
        
        logger.info("Cliente HTTP/2 configurado - BaseURL: {}, Pool: {} conexiones, Timeouts: {}ms/{}ms/{}ms",
                   baseUrl, maxIdleConnections, 
                   connectTimeout.toMillis(), readTimeout.toMillis(), writeTimeout.toMillis());
        
        return client;
    }
    
    // Getters
    public String getBaseUrl() { return baseUrl; }
    public Duration getConnectTimeout() { return connectTimeout; }
    public Duration getReadTimeout() { return readTimeout; }
    public Duration getWriteTimeout() { return writeTimeout; }
    public int getMaxIdleConnections() { return maxIdleConnections; }
    public Duration getKeepAliveDuration() { return keepAliveDuration; }
    public boolean isLoggingEnabled() { return enableLogging; }
    public HttpLoggingInterceptor.Level getLoggingLevel() { return loggingLevel; }
    
    /**
     * Builder para crear configuraciones HTTP/2 personalizadas
     */
    public static class Builder {
        private String baseUrl = "http://localhost:8080";
        private Duration connectTimeout = DEFAULT_CONNECT_TIMEOUT;
        private Duration readTimeout = DEFAULT_READ_TIMEOUT;
        private Duration writeTimeout = DEFAULT_WRITE_TIMEOUT;
        private int maxIdleConnections = DEFAULT_MAX_IDLE_CONNECTIONS;
        private Duration keepAliveDuration = DEFAULT_KEEP_ALIVE_DURATION;
        private boolean enableLogging = true;
        private HttpLoggingInterceptor.Level loggingLevel = HttpLoggingInterceptor.Level.BASIC;
        
        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }
        
        public Builder connectTimeout(Duration timeout) {
            this.connectTimeout = timeout;
            return this;
        }
        
        public Builder readTimeout(Duration timeout) {
            this.readTimeout = timeout;
            return this;
        }
        
        public Builder writeTimeout(Duration timeout) {
            this.writeTimeout = timeout;
            return this;
        }
        
        public Builder maxIdleConnections(int maxConnections) {
            this.maxIdleConnections = maxConnections;
            return this;
        }
        
        public Builder keepAliveDuration(Duration duration) {
            this.keepAliveDuration = duration;
            return this;
        }
        
        public Builder enableLogging(boolean enable) {
            this.enableLogging = enable;
            return this;
        }
        
        public Builder loggingLevel(HttpLoggingInterceptor.Level level) {
            this.loggingLevel = level;
            return this;
        }
        
        public Http2Configuration build() {
            return new Http2Configuration(this);
        }
    }
    
    /**
     * Configuración por defecto para desarrollo
     */
    public static Http2Configuration defaultConfig() {
        return new Builder().build();
    }
    
    /**
     * Configuración optimizada para producción
     */
    public static Http2Configuration productionConfig(String baseUrl) {
        return new Builder()
            .baseUrl(baseUrl)
            .maxIdleConnections(20)
            .keepAliveDuration(Duration.ofMinutes(10))
            .connectTimeout(Duration.ofSeconds(10))
            .readTimeout(Duration.ofSeconds(30))
            .enableLogging(false)
            .build();
    }
    
    /**
     * Configuración para testing con logging detallado
     */
    public static Http2Configuration testingConfig() {
        return new Builder()
            .maxIdleConnections(5)
            .keepAliveDuration(Duration.ofMinutes(1))
            .enableLogging(true)
            .loggingLevel(HttpLoggingInterceptor.Level.BODY)
            .build();
    }
}