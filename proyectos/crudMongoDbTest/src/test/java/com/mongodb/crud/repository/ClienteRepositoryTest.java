package com.mongodb.crud.repository;

import com.mongodb.crud.model.Cliente;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

@DataMongoTest
@ActiveProfiles("test")
class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    private Cliente cliente1;
    private Cliente cliente2;

    @BeforeEach
    void setUp() {
        cliente1 = new Cliente("Juan Pérez", "juan@email.com", "123456789");
        cliente2 = new Cliente("María García", "maria@email.com", "987654321");
        
        clienteRepository.save(cliente1);
        clienteRepository.save(cliente2);
    }

    @AfterEach
    void tearDown() {
        clienteRepository.deleteAll();
    }

    @Test
    void testFindByEmail_Exists() {
        Optional<Cliente> resultado = clienteRepository.findByEmail("juan@email.com");
        
        assertTrue(resultado.isPresent());
        assertEquals("Juan Pérez", resultado.get().getNombre());
        assertEquals("juan@email.com", resultado.get().getEmail());
    }

    @Test
    void testFindByEmail_NotExists() {
        Optional<Cliente> resultado = clienteRepository.findByEmail("noexiste@email.com");
        
        assertFalse(resultado.isPresent());
    }

    @Test
    void testFindByEmail_CaseSensitive() {
        Optional<Cliente> resultado = clienteRepository.findByEmail("JUAN@EMAIL.COM");
        
        assertFalse(resultado.isPresent());
    }

    @Test
    void testFindByNombreContainingIgnoreCase_ExactMatch() {
        List<Cliente> resultado = clienteRepository.findByNombreContainingIgnoreCase("Juan Pérez");
        
        assertEquals(1, resultado.size());
        assertEquals("Juan Pérez", resultado.get(0).getNombre());
    }

    @Test
    void testFindByNombreContainingIgnoreCase_PartialMatch() {
        List<Cliente> resultado = clienteRepository.findByNombreContainingIgnoreCase("Juan");
        
        assertEquals(1, resultado.size());
        assertEquals("Juan Pérez", resultado.get(0).getNombre());
    }

    @Test
    void testFindByNombreContainingIgnoreCase_CaseInsensitive() {
        List<Cliente> resultado = clienteRepository.findByNombreContainingIgnoreCase("juan");
        
        assertEquals(1, resultado.size());
        assertEquals("Juan Pérez", resultado.get(0).getNombre());
    }

    @Test
    void testFindByNombreContainingIgnoreCase_MultipleResults() {
        Cliente cliente3 = new Cliente("Juan Carlos", "juancarlos@email.com", "555666777");
        clienteRepository.save(cliente3);
        
        List<Cliente> resultado = clienteRepository.findByNombreContainingIgnoreCase("Juan");
        
        assertEquals(2, resultado.size());
    }

    @Test
    void testFindByNombreContainingIgnoreCase_NoMatch() {
        List<Cliente> resultado = clienteRepository.findByNombreContainingIgnoreCase("Pedro");
        
        assertTrue(resultado.isEmpty());
    }

    @Test
    void testExistsByEmail_True() {
        boolean existe = clienteRepository.existsByEmail("juan@email.com");
        
        assertTrue(existe);
    }

    @Test
    void testExistsByEmail_False() {
        boolean existe = clienteRepository.existsByEmail("noexiste@email.com");
        
        assertFalse(existe);
    }

    @Test
    void testExistsByEmail_CaseSensitive() {
        boolean existe = clienteRepository.existsByEmail("JUAN@EMAIL.COM");
        
        assertFalse(existe);
    }

    @Test
    void testDeleteByEmail_Exists() {
        assertTrue(clienteRepository.existsByEmail("juan@email.com"));
        
        clienteRepository.deleteByEmail("juan@email.com");
        
        assertFalse(clienteRepository.existsByEmail("juan@email.com"));
        assertTrue(clienteRepository.existsByEmail("maria@email.com"));
    }

    @Test
    void testDeleteByEmail_NotExists() {
        long countBefore = clienteRepository.count();
        
        clienteRepository.deleteByEmail("noexiste@email.com");
        
        assertEquals(countBefore, clienteRepository.count());
    }

    @Test
    void testSaveAndFindById() {
        Cliente nuevoCliente = new Cliente("Pedro López", "pedro@email.com", "111222333");
        Cliente savedCliente = clienteRepository.save(nuevoCliente);
        
        assertNotNull(savedCliente.getId());
        
        Optional<Cliente> encontrado = clienteRepository.findById(savedCliente.getId());
        assertTrue(encontrado.isPresent());
        assertEquals("Pedro López", encontrado.get().getNombre());
    }

    @Test
    void testFindAll() {
        List<Cliente> todos = clienteRepository.findAll();
        
        assertEquals(2, todos.size());
    }

    @Test
    void testCount() {
        long count = clienteRepository.count();
        
        assertEquals(2, count);
    }

    @Test
    void testDeleteAll() {
        clienteRepository.deleteAll();
        
        assertEquals(0, clienteRepository.count());
    }

    @Test
    void testSaveClienteWithDuplicateEmail() {
        Cliente clienteDuplicado = new Cliente("Otro Juan", "juan@email.com", "999888777");
        
        Cliente saved = clienteRepository.save(clienteDuplicado);
        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals("Otro Juan", saved.getNombre());
        assertEquals("juan@email.com", saved.getEmail());
    }
}