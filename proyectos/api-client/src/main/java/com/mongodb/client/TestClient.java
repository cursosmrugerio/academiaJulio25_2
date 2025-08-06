package com.mongodb.client;

import com.mongodb.client.model.ApiResponse;
import com.mongodb.client.model.Cliente;
import com.mongodb.client.service.ClienteService;

public class TestClient {
    
    public static void main(String[] args) {
        ClienteService clienteService = new ClienteService();
        
        // Test creating a client
        System.out.println("=== Testing Client Creation ===");
        Cliente nuevoCliente = new Cliente("Juan Pérez", "juan.perez@example.com", "987654321");
        ApiResponse<Cliente> response = clienteService.crearCliente(nuevoCliente);
        
        if (response.isSuccess()) {
            System.out.println("✅ SUCCESS: Client created successfully");
            System.out.println("Client: " + response.getData());
            System.out.println("Date parsed correctly: " + response.getData().getFechaRegistro());
        } else {
            System.out.println("❌ ERROR: " + response.getMessage());
        }
    }
}