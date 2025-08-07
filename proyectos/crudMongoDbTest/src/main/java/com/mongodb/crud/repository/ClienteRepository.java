package com.mongodb.crud.repository;

import com.mongodb.crud.model.Cliente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface ClienteRepository extends MongoRepository<Cliente, String> {
    
    Optional<Cliente> findByEmail(String email);
    
    List<Cliente> findByNombreContainingIgnoreCase(String nombre);
    
    boolean existsByEmail(String email);
    
    void deleteByEmail(String email);
}