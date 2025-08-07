package com.mongodb.crud.service.impl;

import com.mongodb.crud.model.Cliente;
import com.mongodb.crud.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente("Juan Pérez", "juan@email.com", "123456789");
        cliente.setId("1");
    }

    @Test
    void testSave_WithNullFechaRegistro() {
        Cliente clienteSinFecha = new Cliente();
        clienteSinFecha.setFechaRegistro(null);
        
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteSinFecha);
        
        Cliente resultado = clienteService.save(clienteSinFecha);
        
        assertNotNull(resultado.getFechaRegistro());
        verify(clienteRepository).save(clienteSinFecha);
    }

    @Test
    void testSave_WithExistingFechaRegistro() {
        LocalDateTime fechaExistente = LocalDateTime.of(2023, 1, 1, 12, 0);
        cliente.setFechaRegistro(fechaExistente);
        
        when(clienteRepository.save(cliente)).thenReturn(cliente);
        
        Cliente resultado = clienteService.save(cliente);
        
        assertEquals(fechaExistente, resultado.getFechaRegistro());
        verify(clienteRepository).save(cliente);
    }

    @Test
    void testFindAll() {
        List<Cliente> clientes = Arrays.asList(cliente, new Cliente("María", "maria@email.com", "987654321"));
        when(clienteRepository.findAll()).thenReturn(clientes);
        
        List<Cliente> resultado = clienteService.findAll();
        
        assertEquals(2, resultado.size());
        verify(clienteRepository).findAll();
    }

    @Test
    void testFindById_Exists() {
        when(clienteRepository.findById("1")).thenReturn(Optional.of(cliente));
        
        Optional<Cliente> resultado = clienteService.findById("1");
        
        assertTrue(resultado.isPresent());
        assertEquals(cliente, resultado.get());
        verify(clienteRepository).findById("1");
    }

    @Test
    void testFindById_NotExists() {
        when(clienteRepository.findById("999")).thenReturn(Optional.empty());
        
        Optional<Cliente> resultado = clienteService.findById("999");
        
        assertFalse(resultado.isPresent());
        verify(clienteRepository).findById("999");
    }

    @Test
    void testFindByEmail() {
        when(clienteRepository.findByEmail("juan@email.com")).thenReturn(Optional.of(cliente));
        
        Optional<Cliente> resultado = clienteService.findByEmail("juan@email.com");
        
        assertTrue(resultado.isPresent());
        assertEquals(cliente, resultado.get());
        verify(clienteRepository).findByEmail("juan@email.com");
    }

    @Test
    void testFindByNombre() {
        List<Cliente> clientes = Arrays.asList(cliente);
        when(clienteRepository.findByNombreContainingIgnoreCase("Juan")).thenReturn(clientes);
        
        List<Cliente> resultado = clienteService.findByNombre("Juan");
        
        assertEquals(1, resultado.size());
        assertEquals(cliente, resultado.get(0));
        verify(clienteRepository).findByNombreContainingIgnoreCase("Juan");
    }

    @Test
    void testUpdate_ClienteExists() {
        Cliente clienteActualizado = new Cliente("Juan Carlos", "juancarlos@email.com", "111222333");
        when(clienteRepository.findById("1")).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
        
        Cliente resultado = clienteService.update("1", clienteActualizado);
        
        assertNotNull(resultado);
        verify(clienteRepository).findById("1");
        verify(clienteRepository).save(any(Cliente.class));
    }

    @Test
    void testUpdate_ClienteNotExists() {
        Cliente clienteActualizado = new Cliente("Juan Carlos", "juancarlos@email.com", "111222333");
        when(clienteRepository.findById("999")).thenReturn(Optional.empty());
        
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> clienteService.update("999", clienteActualizado));
        
        assertEquals("Cliente not found with id: 999", exception.getMessage());
        verify(clienteRepository).findById("999");
        verify(clienteRepository, never()).save(any());
    }

    @Test
    void testDeleteById() {
        doNothing().when(clienteRepository).deleteById("1");
        
        clienteService.deleteById("1");
        
        verify(clienteRepository).deleteById("1");
    }

    @Test
    void testDeleteByEmail() {
        doNothing().when(clienteRepository).deleteByEmail("juan@email.com");
        
        clienteService.deleteByEmail("juan@email.com");
        
        verify(clienteRepository).deleteByEmail("juan@email.com");
    }

    @Test
    void testExistsByEmail_True() {
        when(clienteRepository.existsByEmail("juan@email.com")).thenReturn(true);
        
        boolean resultado = clienteService.existsByEmail("juan@email.com");
        
        assertTrue(resultado);
        verify(clienteRepository).existsByEmail("juan@email.com");
    }

    @Test
    void testExistsByEmail_False() {
        when(clienteRepository.existsByEmail("noexiste@email.com")).thenReturn(false);
        
        boolean resultado = clienteService.existsByEmail("noexiste@email.com");
        
        assertFalse(resultado);
        verify(clienteRepository).existsByEmail("noexiste@email.com");
    }
}