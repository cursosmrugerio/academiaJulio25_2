package com.academy.rest.service;

import com.academy.rest.entity.Cliente;

import java.util.List;
import java.util.Optional;

public interface IClienteService {

    List<Cliente> getAllClientes();

    Optional<Cliente> getClienteById(Long id);

    Cliente createCliente(Cliente cliente);

    Cliente updateCliente(Long id, Cliente clienteDetails);

    boolean deleteCliente(Long id);

    Optional<Cliente> getClienteByEmail(String email);

    List<Cliente> getClientesByNombre(String nombre);

    Optional<Cliente> getClienteByTelefono(String telefono);

    List<Cliente> getAllClientesOrderByFechaRegistro();
}