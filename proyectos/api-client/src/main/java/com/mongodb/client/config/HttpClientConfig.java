package com.mongodb.client.config;

import java.net.http.HttpClient;
import java.time.Duration;

public class HttpClientConfig {
    
    private static final String BASE_URL = "http://localhost:8080/api/clientes";
    private static final Duration CONNECT_TIMEOUT = Duration.ofSeconds(10);
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(30);
    
    private static HttpClient httpClient;
    
    public static HttpClient getHttpClient() {
        if (httpClient == null) {
            httpClient = HttpClient.newBuilder()
                    .connectTimeout(CONNECT_TIMEOUT)
                    .build();
        }
        return httpClient;
    }
    
    public static String getBaseUrl() {
        return BASE_URL;
    }
    
    public static Duration getRequestTimeout() {
        return REQUEST_TIMEOUT;
    }
}