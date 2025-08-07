package com.mongodb.crud.controller;

import com.mongodb.crud.model.Cliente;
import com.mongodb.crud.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Autowired
    private ObjectMapper objectMapper;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente("Juan Pérez", "juan@email.com", "123456789");
        cliente.setId("1");
    }

    @Test
    void testCreateCliente_Success() throws Exception {
        when(clienteService.existsByEmail("juan@email.com")).thenReturn(false);
        when(clienteService.save(any(Cliente.class))).thenReturn(cliente);

        mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Juan Pérez"))
                .andExpect(jsonPath("$.email").value("juan@email.com"));

        verify(clienteService).existsByEmail("juan@email.com");
        verify(clienteService).save(any(Cliente.class));
    }

    @Test
    void testCreateCliente_EmailExists() throws Exception {
        when(clienteService.existsByEmail("juan@email.com")).thenReturn(true);

        mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isConflict());

        verify(clienteService).existsByEmail("juan@email.com");
        verify(clienteService, never()).save(any());
    }

    @Test
    void testCreateCliente_InternalError() throws Exception {
        when(clienteService.existsByEmail("juan@email.com")).thenThrow(new RuntimeException());

        mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testGetAllClientes_Success() throws Exception {
        List<Cliente> clientes = Arrays.asList(cliente, new Cliente("María", "maria@email.com", "987654321"));
        when(clienteService.findAll()).thenReturn(clientes);

        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nombre").value("Juan Pérez"));

        verify(clienteService).findAll();
    }

    @Test
    void testGetAllClientes_InternalError() throws Exception {
        when(clienteService.findAll()).thenThrow(new RuntimeException());

        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testGetClienteById_Success() throws Exception {
        when(clienteService.findById("1")).thenReturn(Optional.of(cliente));

        mockMvc.perform(get("/api/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan Pérez"))
                .andExpect(jsonPath("$.email").value("juan@email.com"));

        verify(clienteService).findById("1");
    }

    @Test
    void testGetClienteById_NotFound() throws Exception {
        when(clienteService.findById("999")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/clientes/999"))
                .andExpect(status().isNotFound());

        verify(clienteService).findById("999");
    }

    @Test
    void testGetClienteById_InternalError() throws Exception {
        when(clienteService.findById("1")).thenThrow(new RuntimeException());

        mockMvc.perform(get("/api/clientes/1"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testGetClienteByEmail_Success() throws Exception {
        when(clienteService.findByEmail("juan@email.com")).thenReturn(Optional.of(cliente));

        mockMvc.perform(get("/api/clientes/email/juan@email.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan Pérez"));

        verify(clienteService).findByEmail("juan@email.com");
    }

    @Test
    void testGetClienteByEmail_NotFound() throws Exception {
        when(clienteService.findByEmail("noexiste@email.com")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/clientes/email/noexiste@email.com"))
                .andExpect(status().isNotFound());

        verify(clienteService).findByEmail("noexiste@email.com");
    }

    @Test
    void testSearchClientesByNombre_Success() throws Exception {
        List<Cliente> clientes = Arrays.asList(cliente);
        when(clienteService.findByNombre("Juan")).thenReturn(clientes);

        mockMvc.perform(get("/api/clientes/search").param("nombre", "Juan"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Juan Pérez"));

        verify(clienteService).findByNombre("Juan");
    }

    @Test
    void testUpdateCliente_Success() throws Exception {
        Cliente clienteActualizado = new Cliente("Juan Carlos", "juancarlos@email.com", "111222333");
        when(clienteService.update(anyString(), any(Cliente.class))).thenReturn(clienteActualizado);

        mockMvc.perform(put("/api/clientes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteActualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan Carlos"));

        verify(clienteService).update(anyString(), any(Cliente.class));
    }

    @Test
    void testUpdateCliente_NotFound() throws Exception {
        Cliente clienteActualizado = new Cliente("Juan Carlos", "juancarlos@email.com", "111222333");
        when(clienteService.update(anyString(), any(Cliente.class))).thenThrow(new RuntimeException());

        mockMvc.perform(put("/api/clientes/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteActualizado)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteCliente_Success() throws Exception {
        when(clienteService.findById("1")).thenReturn(Optional.of(cliente));
        doNothing().when(clienteService).deleteById("1");

        mockMvc.perform(delete("/api/clientes/1"))
                .andExpect(status().isNoContent());

        verify(clienteService).findById("1");
        verify(clienteService).deleteById("1");
    }

    @Test
    void testDeleteCliente_NotFound() throws Exception {
        when(clienteService.findById("999")).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/clientes/999"))
                .andExpect(status().isNotFound());

        verify(clienteService).findById("999");
        verify(clienteService, never()).deleteById("999");
    }

    @Test
    void testDeleteClienteByEmail_Success() throws Exception {
        when(clienteService.existsByEmail("juan@email.com")).thenReturn(true);
        doNothing().when(clienteService).deleteByEmail("juan@email.com");

        mockMvc.perform(delete("/api/clientes/email/juan@email.com"))
                .andExpect(status().isNoContent());

        verify(clienteService).existsByEmail("juan@email.com");
        verify(clienteService).deleteByEmail("juan@email.com");
    }

    @Test
    void testDeleteClienteByEmail_NotFound() throws Exception {
        when(clienteService.existsByEmail("noexiste@email.com")).thenReturn(false);

        mockMvc.perform(delete("/api/clientes/email/noexiste@email.com"))
                .andExpect(status().isNotFound());

        verify(clienteService).existsByEmail("noexiste@email.com");
        verify(clienteService, never()).deleteByEmail("noexiste@email.com");
    }
}