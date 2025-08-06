package com.mongodb.client.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.mongodb.client.config.HttpClientConfig;
import com.mongodb.client.model.ApiResponse;
import com.mongodb.client.model.Cliente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ClienteService {
    
    private static final Logger logger = LoggerFactory.getLogger(ClienteService.class);
    private final ObjectMapper objectMapper;
    
    public ClienteService() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.objectMapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
    }
    
    public ApiResponse<Cliente> crearCliente(Cliente cliente) {
        try {
            String json = objectMapper.writeValueAsString(cliente);
            
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(HttpClientConfig.getBaseUrl()))
                    .header("Content-Type", "application/json")
                    .timeout(HttpClientConfig.getRequestTimeout())
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            
            HttpResponse<String> response = HttpClientConfig.getHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 201) {
                Cliente clienteCreado = objectMapper.readValue(response.body(), Cliente.class);
                logger.info("Cliente creado exitosamente: {}", clienteCreado.getEmail());
                return ApiResponse.success(clienteCreado, response.statusCode());
            } else {
                logger.error("Error al crear cliente. Status: {}, Body: {}", 
                           response.statusCode(), response.body());
                return ApiResponse.error("Error al crear cliente: " + response.body(), 
                                       response.statusCode());
            }
            
        } catch (Exception e) {
            logger.error("Excepción al crear cliente", e);
            return ApiResponse.error("Error interno: " + e.getMessage(), 500);
        }
    }
    
    public ApiResponse<List<Cliente>> obtenerTodosLosClientes() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(HttpClientConfig.getBaseUrl()))
                    .timeout(HttpClientConfig.getRequestTimeout())
                    .GET()
                    .build();
            
            HttpResponse<String> response = HttpClientConfig.getHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                List<Cliente> clientes = objectMapper.readValue(
                    response.body(), 
                    new TypeReference<List<Cliente>>() {}
                );
                logger.info("Obtenidos {} clientes", clientes.size());
                return ApiResponse.success(clientes, response.statusCode());
            } else {
                logger.error("Error al obtener clientes. Status: {}", response.statusCode());
                return ApiResponse.error("Error al obtener clientes", response.statusCode());
            }
            
        } catch (Exception e) {
            logger.error("Excepción al obtener clientes", e);
            return ApiResponse.error("Error interno: " + e.getMessage(), 500);
        }
    }
    
    public ApiResponse<Cliente> obtenerClientePorId(String id) {
        try {
            String url = HttpClientConfig.getBaseUrl() + "/" + id;
            
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(HttpClientConfig.getRequestTimeout())
                    .GET()
                    .build();
            
            HttpResponse<String> response = HttpClientConfig.getHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                Cliente cliente = objectMapper.readValue(response.body(), Cliente.class);
                logger.info("Cliente encontrado: {}", cliente.getEmail());
                return ApiResponse.success(cliente, response.statusCode());
            } else if (response.statusCode() == 404) {
                logger.warn("Cliente no encontrado con ID: {}", id);
                return ApiResponse.error("Cliente no encontrado", response.statusCode());
            } else {
                logger.error("Error al obtener cliente por ID. Status: {}", response.statusCode());
                return ApiResponse.error("Error al obtener cliente", response.statusCode());
            }
            
        } catch (Exception e) {
            logger.error("Excepción al obtener cliente por ID", e);
            return ApiResponse.error("Error interno: " + e.getMessage(), 500);
        }
    }
    
    public ApiResponse<Cliente> obtenerClientePorEmail(String email) {
        try {
            String encodedEmail = URLEncoder.encode(email, StandardCharsets.UTF_8);
            String url = HttpClientConfig.getBaseUrl() + "/email/" + encodedEmail;
            
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(HttpClientConfig.getRequestTimeout())
                    .GET()
                    .build();
            
            HttpResponse<String> response = HttpClientConfig.getHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                Cliente cliente = objectMapper.readValue(response.body(), Cliente.class);
                logger.info("Cliente encontrado por email: {}", email);
                return ApiResponse.success(cliente, response.statusCode());
            } else if (response.statusCode() == 404) {
                logger.warn("Cliente no encontrado con email: {}", email);
                return ApiResponse.error("Cliente no encontrado", response.statusCode());
            } else {
                logger.error("Error al obtener cliente por email. Status: {}", response.statusCode());
                return ApiResponse.error("Error al obtener cliente", response.statusCode());
            }
            
        } catch (Exception e) {
            logger.error("Excepción al obtener cliente por email", e);
            return ApiResponse.error("Error interno: " + e.getMessage(), 500);
        }
    }
    
    public ApiResponse<List<Cliente>> buscarClientesPorNombre(String nombre) {
        try {
            String encodedNombre = URLEncoder.encode(nombre, StandardCharsets.UTF_8);
            String url = HttpClientConfig.getBaseUrl() + "/search?nombre=" + encodedNombre;
            
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(HttpClientConfig.getRequestTimeout())
                    .GET()
                    .build();
            
            HttpResponse<String> response = HttpClientConfig.getHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                List<Cliente> clientes = objectMapper.readValue(
                    response.body(), 
                    new TypeReference<List<Cliente>>() {}
                );
                logger.info("Encontrados {} clientes con nombre: {}", clientes.size(), nombre);
                return ApiResponse.success(clientes, response.statusCode());
            } else {
                logger.error("Error al buscar clientes por nombre. Status: {}", response.statusCode());
                return ApiResponse.error("Error al buscar clientes", response.statusCode());
            }
            
        } catch (Exception e) {
            logger.error("Excepción al buscar clientes por nombre", e);
            return ApiResponse.error("Error interno: " + e.getMessage(), 500);
        }
    }
    
    public ApiResponse<Cliente> actualizarCliente(String id, Cliente cliente) {
        try {
            String json = objectMapper.writeValueAsString(cliente);
            String url = HttpClientConfig.getBaseUrl() + "/" + id;
            
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .timeout(HttpClientConfig.getRequestTimeout())
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            
            HttpResponse<String> response = HttpClientConfig.getHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                Cliente clienteActualizado = objectMapper.readValue(response.body(), Cliente.class);
                logger.info("Cliente actualizado exitosamente: {}", clienteActualizado.getEmail());
                return ApiResponse.success(clienteActualizado, response.statusCode());
            } else if (response.statusCode() == 404) {
                logger.warn("Cliente no encontrado para actualizar con ID: {}", id);
                return ApiResponse.error("Cliente no encontrado", response.statusCode());
            } else {
                logger.error("Error al actualizar cliente. Status: {}, Body: {}", 
                           response.statusCode(), response.body());
                return ApiResponse.error("Error al actualizar cliente: " + response.body(), 
                                       response.statusCode());
            }
            
        } catch (Exception e) {
            logger.error("Excepción al actualizar cliente", e);
            return ApiResponse.error("Error interno: " + e.getMessage(), 500);
        }
    }
    
    public ApiResponse<Void> eliminarClientePorId(String id) {
        try {
            String url = HttpClientConfig.getBaseUrl() + "/" + id;
            
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(HttpClientConfig.getRequestTimeout())
                    .DELETE()
                    .build();
            
            HttpResponse<String> response = HttpClientConfig.getHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 204) {
                logger.info("Cliente eliminado exitosamente con ID: {}", id);
                return ApiResponse.success(null, response.statusCode());
            } else if (response.statusCode() == 404) {
                logger.warn("Cliente no encontrado para eliminar con ID: {}", id);
                return ApiResponse.error("Cliente no encontrado", response.statusCode());
            } else {
                logger.error("Error al eliminar cliente. Status: {}", response.statusCode());
                return ApiResponse.error("Error al eliminar cliente", response.statusCode());
            }
            
        } catch (Exception e) {
            logger.error("Excepción al eliminar cliente por ID", e);
            return ApiResponse.error("Error interno: " + e.getMessage(), 500);
        }
    }
    
    public ApiResponse<Void> eliminarClientePorEmail(String email) {
        try {
            String encodedEmail = URLEncoder.encode(email, StandardCharsets.UTF_8);
            String url = HttpClientConfig.getBaseUrl() + "/email/" + encodedEmail;
            
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(HttpClientConfig.getRequestTimeout())
                    .DELETE()
                    .build();
            
            HttpResponse<String> response = HttpClientConfig.getHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 204) {
                logger.info("Cliente eliminado exitosamente con email: {}", email);
                return ApiResponse.success(null, response.statusCode());
            } else if (response.statusCode() == 404) {
                logger.warn("Cliente no encontrado para eliminar con email: {}", email);
                return ApiResponse.error("Cliente no encontrado", response.statusCode());
            } else {
                logger.error("Error al eliminar cliente por email. Status: {}", response.statusCode());
                return ApiResponse.error("Error al eliminar cliente", response.statusCode());
            }
            
        } catch (Exception e) {
            logger.error("Excepción al eliminar cliente por email", e);
            return ApiResponse.error("Error interno: " + e.getMessage(), 500);
        }
    }
    
    public CompletableFuture<ApiResponse<Cliente>> crearClienteAsync(Cliente cliente) {
        return CompletableFuture.supplyAsync(() -> crearCliente(cliente));
    }
    
    public CompletableFuture<ApiResponse<List<Cliente>>> obtenerTodosLosClientesAsync() {
        return CompletableFuture.supplyAsync(this::obtenerTodosLosClientes);
    }
}