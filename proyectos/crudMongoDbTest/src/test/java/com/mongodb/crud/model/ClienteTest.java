package com.mongodb.crud.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

class ClienteTest {

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
    }

    @Test
    void testDefaultConstructor() {
        assertNull(cliente.getId());
        assertNull(cliente.getNombre());
        assertNull(cliente.getEmail());
        assertNull(cliente.getTelefono());
        assertNotNull(cliente.getFechaRegistro());
        assertTrue(cliente.getFechaRegistro().isBefore(LocalDateTime.now().plusSeconds(1)));
        assertTrue(cliente.getFechaRegistro().isAfter(LocalDateTime.now().minusSeconds(1)));
    }

    @Test
    void testParameterizedConstructor() {
        Cliente clienteWithParams = new Cliente("Juan Pérez", "juan@email.com", "123456789");
        
        assertEquals("Juan Pérez", clienteWithParams.getNombre());
        assertEquals("juan@email.com", clienteWithParams.getEmail());
        assertEquals("123456789", clienteWithParams.getTelefono());
        assertNotNull(clienteWithParams.getFechaRegistro());
    }

    @Test
    void testSettersAndGetters() {
        cliente.setId("123");
        cliente.setNombre("María García");
        cliente.setEmail("maria@email.com");
        cliente.setTelefono("987654321");
        LocalDateTime fecha = LocalDateTime.now();
        cliente.setFechaRegistro(fecha);

        assertEquals("123", cliente.getId());
        assertEquals("María García", cliente.getNombre());
        assertEquals("maria@email.com", cliente.getEmail());
        assertEquals("987654321", cliente.getTelefono());
        assertEquals(fecha, cliente.getFechaRegistro());
    }

    @Test
    void testNullValues() {
        cliente.setNombre(null);
        cliente.setEmail(null);
        cliente.setTelefono(null);

        assertNull(cliente.getNombre());
        assertNull(cliente.getEmail());
        assertNull(cliente.getTelefono());
    }

    @Test
    void testEmptyStrings() {
        cliente.setNombre("");
        cliente.setEmail("");
        cliente.setTelefono("");

        assertEquals("", cliente.getNombre());
        assertEquals("", cliente.getEmail());
        assertEquals("", cliente.getTelefono());
    }

    @Test
    void testFechaRegistroNotNull() {
        Cliente nuevoCliente = new Cliente("Test", "test@email.com", "123");
        assertNotNull(nuevoCliente.getFechaRegistro());
    }
}