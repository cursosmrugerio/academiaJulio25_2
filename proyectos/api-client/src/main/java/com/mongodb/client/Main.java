package com.mongodb.client;

import com.mongodb.client.model.ApiResponse;
import com.mongodb.client.model.Cliente;
import com.mongodb.client.service.ClienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;

public class Main {
    
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final ClienteService clienteService = new ClienteService();
    private static final Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        logger.info("=== Cliente API MongoDB CRUD ===");
        System.out.println("Bienvenido al Cliente API MongoDB CRUD");
        System.out.println("Asegúrate de que el servidor esté ejecutándose en http://localhost:8080");
        System.out.println();
        
        boolean continuar = true;
        while (continuar) {
            mostrarMenu();
            int opcion = leerOpcion();
            
            switch (opcion) {
                case 1 -> crearCliente();
                case 2 -> listarTodosLosClientes();
                case 3 -> buscarClientePorId();
                case 4 -> buscarClientePorEmail();
                case 5 -> buscarClientesPorNombre();
                case 6 -> actualizarCliente();
                case 7 -> eliminarClientePorId();
                case 8 -> eliminarClientePorEmail();
                case 9 -> {
                    System.out.println("¡Hasta luego!");
                    continuar = false;
                }
                default -> System.out.println("Opción inválida. Intenta nuevamente.");
            }
            
            if (continuar) {
                System.out.println("\nPresiona Enter para continuar...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }
    
    private static void mostrarMenu() {
        System.out.println("\n=== MENÚ PRINCIPAL ===");
        System.out.println("1. Crear cliente");
        System.out.println("2. Listar todos los clientes");
        System.out.println("3. Buscar cliente por ID");
        System.out.println("4. Buscar cliente por email");
        System.out.println("5. Buscar clientes por nombre");
        System.out.println("6. Actualizar cliente");
        System.out.println("7. Eliminar cliente por ID");
        System.out.println("8. Eliminar cliente por email");
        System.out.println("9. Salir");
        System.out.print("Selecciona una opción: ");
    }
    
    private static int leerOpcion() {
        try {
            int opcion = Integer.parseInt(scanner.nextLine().trim());
            return opcion;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private static void crearCliente() {
        System.out.println("\n=== CREAR CLIENTE ===");
        
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine().trim();
        
        if (nombre.isEmpty() || email.isEmpty()) {
            System.out.println("❌ Nombre y email son obligatorios");
            return;
        }
        
        Cliente cliente = new Cliente(nombre, email, telefono);
        ApiResponse<Cliente> response = clienteService.crearCliente(cliente);
        
        if (response.isSuccess()) {
            System.out.println("✅ Cliente creado exitosamente:");
            mostrarCliente(response.getData());
        } else {
            System.out.println("❌ Error: " + response.getMessage());
        }
    }
    
    private static void listarTodosLosClientes() {
        System.out.println("\n=== TODOS LOS CLIENTES ===");
        
        ApiResponse<List<Cliente>> response = clienteService.obtenerTodosLosClientes();
        
        if (response.isSuccess()) {
            List<Cliente> clientes = response.getData();
            if (clientes.isEmpty()) {
                System.out.println("No hay clientes registrados");
            } else {
                System.out.println("📋 Encontrados " + clientes.size() + " clientes:");
                clientes.forEach(Main::mostrarCliente);
            }
        } else {
            System.out.println("❌ Error: " + response.getMessage());
        }
    }
    
    private static void buscarClientePorId() {
        System.out.println("\n=== BUSCAR CLIENTE POR ID ===");
        
        System.out.print("ID del cliente: ");
        String id = scanner.nextLine().trim();
        
        if (id.isEmpty()) {
            System.out.println("❌ El ID es obligatorio");
            return;
        }
        
        ApiResponse<Cliente> response = clienteService.obtenerClientePorId(id);
        
        if (response.isSuccess()) {
            System.out.println("✅ Cliente encontrado:");
            mostrarCliente(response.getData());
        } else {
            System.out.println("❌ " + response.getMessage());
        }
    }
    
    private static void buscarClientePorEmail() {
        System.out.println("\n=== BUSCAR CLIENTE POR EMAIL ===");
        
        System.out.print("Email del cliente: ");
        String email = scanner.nextLine().trim();
        
        if (email.isEmpty()) {
            System.out.println("❌ El email es obligatorio");
            return;
        }
        
        ApiResponse<Cliente> response = clienteService.obtenerClientePorEmail(email);
        
        if (response.isSuccess()) {
            System.out.println("✅ Cliente encontrado:");
            mostrarCliente(response.getData());
        } else {
            System.out.println("❌ " + response.getMessage());
        }
    }
    
    private static void buscarClientesPorNombre() {
        System.out.println("\n=== BUSCAR CLIENTES POR NOMBRE ===");
        
        System.out.print("Nombre a buscar: ");
        String nombre = scanner.nextLine().trim();
        
        if (nombre.isEmpty()) {
            System.out.println("❌ El nombre es obligatorio");
            return;
        }
        
        ApiResponse<List<Cliente>> response = clienteService.buscarClientesPorNombre(nombre);
        
        if (response.isSuccess()) {
            List<Cliente> clientes = response.getData();
            if (clientes.isEmpty()) {
                System.out.println("No se encontraron clientes con ese nombre");
            } else {
                System.out.println("🔍 Encontrados " + clientes.size() + " clientes:");
                clientes.forEach(Main::mostrarCliente);
            }
        } else {
            System.out.println("❌ Error: " + response.getMessage());
        }
    }
    
    private static void actualizarCliente() {
        System.out.println("\n=== ACTUALIZAR CLIENTE ===");
        
        System.out.print("ID del cliente a actualizar: ");
        String id = scanner.nextLine().trim();
        
        if (id.isEmpty()) {
            System.out.println("❌ El ID es obligatorio");
            return;
        }
        
        ApiResponse<Cliente> clienteExistente = clienteService.obtenerClientePorId(id);
        if (!clienteExistente.isSuccess()) {
            System.out.println("❌ Cliente no encontrado");
            return;
        }
        
        Cliente cliente = clienteExistente.getData();
        System.out.println("Cliente actual:");
        mostrarCliente(cliente);
        
        System.out.println("\nIngresa los nuevos datos (Enter para mantener el actual):");
        
        System.out.print("Nuevo nombre [" + cliente.getNombre() + "]: ");
        String nuevoNombre = scanner.nextLine().trim();
        if (!nuevoNombre.isEmpty()) {
            cliente.setNombre(nuevoNombre);
        }
        
        System.out.print("Nuevo email [" + cliente.getEmail() + "]: ");
        String nuevoEmail = scanner.nextLine().trim();
        if (!nuevoEmail.isEmpty()) {
            cliente.setEmail(nuevoEmail);
        }
        
        System.out.print("Nuevo teléfono [" + cliente.getTelefono() + "]: ");
        String nuevoTelefono = scanner.nextLine().trim();
        if (!nuevoTelefono.isEmpty()) {
            cliente.setTelefono(nuevoTelefono);
        }
        
        ApiResponse<Cliente> response = clienteService.actualizarCliente(id, cliente);
        
        if (response.isSuccess()) {
            System.out.println("✅ Cliente actualizado exitosamente:");
            mostrarCliente(response.getData());
        } else {
            System.out.println("❌ Error: " + response.getMessage());
        }
    }
    
    private static void eliminarClientePorId() {
        System.out.println("\n=== ELIMINAR CLIENTE POR ID ===");
        
        System.out.print("ID del cliente a eliminar: ");
        String id = scanner.nextLine().trim();
        
        if (id.isEmpty()) {
            System.out.println("❌ El ID es obligatorio");
            return;
        }
        
        System.out.print("¿Estás seguro de eliminar este cliente? (s/N): ");
        String confirmacion = scanner.nextLine().trim().toLowerCase();
        
        if (!confirmacion.equals("s") && !confirmacion.equals("si")) {
            System.out.println("❌ Operación cancelada");
            return;
        }
        
        ApiResponse<Void> response = clienteService.eliminarClientePorId(id);
        
        if (response.isSuccess()) {
            System.out.println("✅ Cliente eliminado exitosamente");
        } else {
            System.out.println("❌ Error: " + response.getMessage());
        }
    }
    
    private static void eliminarClientePorEmail() {
        System.out.println("\n=== ELIMINAR CLIENTE POR EMAIL ===");
        
        System.out.print("Email del cliente a eliminar: ");
        String email = scanner.nextLine().trim();
        
        if (email.isEmpty()) {
            System.out.println("❌ El email es obligatorio");
            return;
        }
        
        System.out.print("¿Estás seguro de eliminar este cliente? (s/N): ");
        String confirmacion = scanner.nextLine().trim().toLowerCase();
        
        if (!confirmacion.equals("s") && !confirmacion.equals("si")) {
            System.out.println("❌ Operación cancelada");
            return;
        }
        
        ApiResponse<Void> response = clienteService.eliminarClientePorEmail(email);
        
        if (response.isSuccess()) {
            System.out.println("✅ Cliente eliminado exitosamente");
        } else {
            System.out.println("❌ Error: " + response.getMessage());
        }
    }
    
    private static void mostrarCliente(Cliente cliente) {
        System.out.println("────────────────────────────────");
        System.out.println("ID: " + cliente.getId());
        System.out.println("Nombre: " + cliente.getNombre());
        System.out.println("Email: " + cliente.getEmail());
        System.out.println("Teléfono: " + cliente.getTelefono());
        System.out.println("Fecha Registro: " + cliente.getFechaRegistro());
        System.out.println("────────────────────────────────");
    }
}