package com.mongodb.crud.integration;

import com.mongodb.crud.model.Cliente;
import com.mongodb.crud.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("ClienteRepository Integration Tests")
class ClienteRepositoryIntegrationTest {

    @Autowired
    private ClienteRepository clienteRepository;

    private Cliente clienteTest;

    @BeforeEach
    void setUp() {
        clienteRepository.deleteAll();
        clienteTest = new Cliente("Integration Test", "integration@test.com", "123456789");
        clienteRepository.save(clienteTest);
    }

    @Test
    @DisplayName("Should find cliente by email using custom query method")
    void testFindByEmailCustomQuery() {
        Optional<Cliente> found = clienteRepository.findByEmail("integration@test.com");
        
        assertTrue(found.isPresent());
        assertEquals("Integration Test", found.get().getNombre());
        assertEquals("integration@test.com", found.get().getEmail());
    }

    @Test
    @DisplayName("Should find clients by partial name ignoring case")
    void testFindByNombreContainingIgnoreCase() {
        Cliente cliente2 = new Cliente("Integration User", "user@test.com", "987654321");
        clienteRepository.save(cliente2);
        
        List<Cliente> foundByIntegration = clienteRepository.findByNombreContainingIgnoreCase("integration");
        List<Cliente> foundByINTEGRATION = clienteRepository.findByNombreContainingIgnoreCase("INTEGRATION");
        
        assertEquals(2, foundByIntegration.size());
        assertEquals(2, foundByINTEGRATION.size());
    }

    @Test
    @DisplayName("Should check if email exists in database")
    void testExistsByEmail() {
        boolean exists = clienteRepository.existsByEmail("integration@test.com");
        boolean notExists = clienteRepository.existsByEmail("nonexistent@test.com");
        
        assertTrue(exists);
        assertFalse(notExists);
    }

    @Test
    @DisplayName("Should delete cliente by email using custom method")
    void testDeleteByEmail() {
        assertTrue(clienteRepository.existsByEmail("integration@test.com"));
        
        clienteRepository.deleteByEmail("integration@test.com");
        
        assertFalse(clienteRepository.existsByEmail("integration@test.com"));
        assertEquals(0, clienteRepository.count());
    }

    @Test
    @DisplayName("Should handle repository operations with multiple clients")
    void testMultipleClientOperations() {
        Cliente cliente2 = new Cliente("Test User 2", "test2@email.com", "111222333");
        Cliente cliente3 = new Cliente("Test User 3", "test3@email.com", "444555666");
        
        clienteRepository.saveAll(List.of(cliente2, cliente3));
        
        assertEquals(3, clienteRepository.count());
        
        List<Cliente> allClientes = clienteRepository.findAll();
        assertEquals(3, allClientes.size());
        
        List<Cliente> testUsers = clienteRepository.findByNombreContainingIgnoreCase("test");
        assertEquals(3, testUsers.size());
    }

    @Test
    @DisplayName("Should handle edge cases in repository queries")
    void testRepositoryEdgeCases() {
        List<Cliente> emptyResult = clienteRepository.findByNombreContainingIgnoreCase("nonexistent");
        assertTrue(emptyResult.isEmpty());
        
        Optional<Cliente> notFound = clienteRepository.findByEmail("notfound@test.com");
        assertFalse(notFound.isPresent());
        
        assertDoesNotThrow(() -> clienteRepository.deleteByEmail("nonexistent@test.com"));
    }
}