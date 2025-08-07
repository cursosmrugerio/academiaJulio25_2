package com.mongodb.crud.service;

import com.mongodb.crud.model.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteService {
    
    Cliente save(Cliente cliente);
    
    List<Cliente> findAll();
    
    Optional<Cliente> findById(String id);
    
    Optional<Cliente> findByEmail(String email);
    
    List<Cliente> findByNombre(String nombre);
    
    Cliente update(String id, Cliente cliente);
    
    void deleteById(String id);
    
    void deleteByEmail(String email);
    
    boolean existsByEmail(String email);
}