package com.academy.mvc.config;

import com.academy.mvc.entity.Cliente;
import com.academy.mvc.entity.PedidoCompra;
import com.academy.mvc.repository.ClienteRepository;
import com.academy.mvc.repository.PedidoCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PedidoCompraRepository pedidoRepository;

    @Override
    public void run(String... args) throws Exception {
        cargarDatosIniciales();
    }

    private void cargarDatosIniciales() {
        // Crear clientes
        Cliente cliente1 = new Cliente("Juan Pérez", "juan.perez@email.com", "555-0101", "Av. Principal 123, Ciudad");
        Cliente cliente2 = new Cliente("María González", "maria.gonzalez@email.com", "555-0102", "Calle Secundaria 456, Pueblo");
        Cliente cliente3 = new Cliente("Carlos Rodríguez", "carlos.rodriguez@email.com", "555-0103", "Plaza Central 789, Villa");
        Cliente cliente4 = new Cliente("Ana Martínez", "ana.martinez@email.com", "555-0104", "Barrio Norte 321, Centro");
        Cliente cliente5 = new Cliente("Luis Fernández", "luis.fernandez@email.com", "555-0105", "Zona Sur 654, Distrito");

        // Guardar clientes
        clienteRepository.save(cliente1);
        clienteRepository.save(cliente2);
        clienteRepository.save(cliente3);
        clienteRepository.save(cliente4);
        clienteRepository.save(cliente5);

        // Crear pedidos para cliente1
        PedidoCompra pedido1 = new PedidoCompra("PED-001", new BigDecimal("150.75"), "Pedido de productos de oficina", cliente1);
        pedido1.setFechaPedido(LocalDateTime.now().minusDays(5));
        pedido1.setEstado(PedidoCompra.EstadoPedido.ENTREGADO);

        PedidoCompra pedido2 = new PedidoCompra("PED-002", new BigDecimal("89.99"), "Suministros para computadora", cliente1);
        pedido2.setFechaPedido(LocalDateTime.now().minusDays(2));
        pedido2.setEstado(PedidoCompra.EstadoPedido.EN_PROCESO);

        // Crear pedidos para cliente2
        PedidoCompra pedido3 = new PedidoCompra("PED-003", new BigDecimal("299.50"), "Equipos de limpieza industrial", cliente2);
        pedido3.setFechaPedido(LocalDateTime.now().minusDays(7));
        pedido3.setEstado(PedidoCompra.EstadoPedido.ENVIADO);

        PedidoCompra pedido4 = new PedidoCompra("PED-004", new BigDecimal("45.25"), "Materiales de construcción", cliente2);
        pedido4.setFechaPedido(LocalDateTime.now().minusDays(1));
        pedido4.setEstado(PedidoCompra.EstadoPedido.PENDIENTE);

        // Crear pedidos para cliente3
        PedidoCompra pedido5 = new PedidoCompra("PED-005", new BigDecimal("1250.00"), "Maquinaria para taller", cliente3);
        pedido5.setFechaPedido(LocalDateTime.now().minusDays(10));
        pedido5.setEstado(PedidoCompra.EstadoPedido.ENTREGADO);

        PedidoCompra pedido6 = new PedidoCompra("PED-006", new BigDecimal("75.80"), "Herramientas menores", cliente3);
        pedido6.setFechaPedido(LocalDateTime.now().minusDays(3));
        pedido6.setEstado(PedidoCompra.EstadoPedido.EN_PROCESO);

        PedidoCompra pedido7 = new PedidoCompra("PED-007", new BigDecimal("520.40"), "Equipo de seguridad industrial", cliente3);
        pedido7.setFechaPedido(LocalDateTime.now().minusHours(6));
        pedido7.setEstado(PedidoCompra.EstadoPedido.PENDIENTE);

        // Crear pedidos para cliente4
        PedidoCompra pedido8 = new PedidoCompra("PED-008", new BigDecimal("180.90"), "Productos farmacéuticos", cliente4);
        pedido8.setFechaPedido(LocalDateTime.now().minusDays(4));
        pedido8.setEstado(PedidoCompra.EstadoPedido.CANCELADO);

        PedidoCompra pedido9 = new PedidoCompra("PED-009", new BigDecimal("95.60"), "Material médico", cliente4);
        pedido9.setFechaPedido(LocalDateTime.now().minusDays(1));
        pedido9.setEstado(PedidoCompra.EstadoPedido.ENVIADO);

        // Crear pedidos para cliente5
        PedidoCompra pedido10 = new PedidoCompra("PED-010", new BigDecimal("340.25"), "Equipos deportivos", cliente5);
        pedido10.setFechaPedido(LocalDateTime.now().minusDays(6));
        pedido10.setEstado(PedidoCompra.EstadoPedido.ENTREGADO);

        PedidoCompra pedido11 = new PedidoCompra("PED-011", new BigDecimal("125.75"), "Accesorios para gimnasio", cliente5);
        pedido11.setFechaPedido(LocalDateTime.now().minusDays(2));
        pedido11.setEstado(PedidoCompra.EstadoPedido.EN_PROCESO);

        PedidoCompra pedido12 = new PedidoCompra("PED-012", new BigDecimal("67.30"), "Suplementos nutricionales", cliente5);
        pedido12.setFechaPedido(LocalDateTime.now().minusHours(12));
        pedido12.setEstado(PedidoCompra.EstadoPedido.PENDIENTE);

        // Guardar todos los pedidos
        pedidoRepository.save(pedido1);
        pedidoRepository.save(pedido2);
        pedidoRepository.save(pedido3);
        pedidoRepository.save(pedido4);
        pedidoRepository.save(pedido5);
        pedidoRepository.save(pedido6);
        pedidoRepository.save(pedido7);
        pedidoRepository.save(pedido8);
        pedidoRepository.save(pedido9);
        pedidoRepository.save(pedido10);
        pedidoRepository.save(pedido11);
        pedidoRepository.save(pedido12);

        System.out.println("✅ Datos iniciales cargados correctamente:");
        System.out.println("   - 5 clientes creados");
        System.out.println("   - 12 pedidos de compra creados");
        System.out.println("   - Diferentes estados de pedidos para demostración");
    }
}