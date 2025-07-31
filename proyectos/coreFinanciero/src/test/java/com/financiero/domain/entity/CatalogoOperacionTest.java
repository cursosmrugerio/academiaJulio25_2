package com.financiero.domain.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para CatalogoOperacion")
class CatalogoOperacionTest {

    private CatalogoOperacion catalogoOperacion;
    private CatalogoOperacionId id;

    @BeforeEach
    void setUp() {
        id = new CatalogoOperacionId("GRP001", "EMP001", "DEPOSITO");
        catalogoOperacion = new CatalogoOperacion();
        catalogoOperacion.setId(id);
        catalogoOperacion.setDescripcion("Depósito en cuenta");
        catalogoOperacion.setClaveAfectaSaldo("I");
        catalogoOperacion.setEstatus("A");
        catalogoOperacion.setObservaciones("Operación de depósito");
    }

    @Test
    @DisplayName("Debe crear CatalogoOperacion con constructor vacío")
    void debeCrearCatalogoOperacionConConstructorVacio() {
        CatalogoOperacion catalogo = new CatalogoOperacion();
        assertNotNull(catalogo);
        assertNull(catalogo.getId());
        assertNull(catalogo.getDescripcion());
        assertNull(catalogo.getClaveAfectaSaldo());
        assertEquals("A", catalogo.getEstatus()); // Valor por defecto
        assertNull(catalogo.getObservaciones());
    }

    @Test
    @DisplayName("Debe crear CatalogoOperacion con constructor completo")
    void debeCrearCatalogoOperacionConConstructorCompleto() {
        String descripcion = "Retiro de efectivo";
        String claveAfecta = "D";
        
        CatalogoOperacion catalogo = new CatalogoOperacion(id, descripcion, claveAfecta);
        
        assertEquals(id, catalogo.getId());
        assertEquals(descripcion, catalogo.getDescripcion());
        assertEquals(claveAfecta, catalogo.getClaveAfectaSaldo());
        assertEquals("A", catalogo.getEstatus()); // Valor por defecto
    }

    @Test
    @DisplayName("Debe manejar getters y setters correctamente")
    void debeManejarGettersYSettersCorrectamente() {
        CatalogoOperacion catalogo = new CatalogoOperacion();
        CatalogoOperacionId nuevoId = new CatalogoOperacionId("GRP002", "EMP002", "RETIRO");
        
        catalogo.setId(nuevoId);
        catalogo.setDescripcion("Nueva descripción");
        catalogo.setClaveAfectaSaldo("D");
        catalogo.setEstatus("S");
        catalogo.setObservaciones("Nuevas observaciones");
        
        assertEquals(nuevoId, catalogo.getId());
        assertEquals("Nueva descripción", catalogo.getDescripcion());
        assertEquals("D", catalogo.getClaveAfectaSaldo());
        assertEquals("S", catalogo.getEstatus());
        assertEquals("Nuevas observaciones", catalogo.getObservaciones());
    }

    @Test
    @DisplayName("Debe identificar si incrementa saldo")
    void debeIdentificarSiIncrementaSaldo() {
        assertTrue(catalogoOperacion.incrementaSaldo());
        
        catalogoOperacion.setClaveAfectaSaldo("D");
        assertFalse(catalogoOperacion.incrementaSaldo());
        
        catalogoOperacion.setClaveAfectaSaldo("N");
        assertFalse(catalogoOperacion.incrementaSaldo());
    }

    @Test
    @DisplayName("Debe identificar si decrementa saldo")
    void debeIdentificarSiDecrementaSaldo() {
        assertFalse(catalogoOperacion.decrementaSaldo());
        
        catalogoOperacion.setClaveAfectaSaldo("D");
        assertTrue(catalogoOperacion.decrementaSaldo());
        
        catalogoOperacion.setClaveAfectaSaldo("N");
        assertFalse(catalogoOperacion.decrementaSaldo());
    }

    @Test
    @DisplayName("Debe identificar si no afecta saldo")
    void debeIdentificarSiNoAfectaSaldo() {
        assertFalse(catalogoOperacion.noAfectaSaldo());
        
        catalogoOperacion.setClaveAfectaSaldo("N");
        assertTrue(catalogoOperacion.noAfectaSaldo());
        
        catalogoOperacion.setClaveAfectaSaldo("D");
        assertFalse(catalogoOperacion.noAfectaSaldo());
    }

    @Test
    @DisplayName("Debe identificar si está activa")
    void debeIdentificarSiEstaActiva() {
        assertTrue(catalogoOperacion.estaActiva());
        
        catalogoOperacion.setEstatus("S");
        assertFalse(catalogoOperacion.estaActiva());
    }

    @Test
    @DisplayName("Debe identificar si está suspendida")
    void debeIdentificarSiEstaSuspendida() {
        assertFalse(catalogoOperacion.estaSuspendida());
        
        catalogoOperacion.setEstatus("S");
        assertTrue(catalogoOperacion.estaSuspendida());
    }

    @Test
    @DisplayName("Debe activar operación")
    void debeActivarOperacion() {
        catalogoOperacion.setEstatus("S");
        catalogoOperacion.activar();
        assertEquals("A", catalogoOperacion.getEstatus());
        assertTrue(catalogoOperacion.estaActiva());
    }

    @Test
    @DisplayName("Debe suspender operación")
    void debeSuspenderOperacion() {
        catalogoOperacion.suspender();
        assertEquals("S", catalogoOperacion.getEstatus());
        assertTrue(catalogoOperacion.estaSuspendida());
    }

    @Test
    @DisplayName("Debe calcular factor de afectación para incremento")
    void debeCalcularFactorDeAfectacionParaIncremento() {
        // Incrementa sin cancelación
        assertEquals(1, catalogoOperacion.calcularFactorAfectacion(false));
        
        // Incrementa con cancelación
        assertEquals(-1, catalogoOperacion.calcularFactorAfectacion(true));
    }

    @Test
    @DisplayName("Debe calcular factor de afectación para decremento")
    void debeCalcularFactorDeAfectacionParaDecremento() {
        catalogoOperacion.setClaveAfectaSaldo("D");
        
        // Decrementa sin cancelación
        assertEquals(-1, catalogoOperacion.calcularFactorAfectacion(false));
        
        // Decrementa con cancelación
        assertEquals(1, catalogoOperacion.calcularFactorAfectacion(true));
    }

    @Test
    @DisplayName("Debe calcular factor de afectación para no afectar")
    void debeCalcularFactorDeAfectacionParaNoAfectar() {
        catalogoOperacion.setClaveAfectaSaldo("N");
        
        // No afecta sin cancelación
        assertEquals(0, catalogoOperacion.calcularFactorAfectacion(false));
        
        // No afecta con cancelación
        assertEquals(0, catalogoOperacion.calcularFactorAfectacion(true));
    }

    @Test
    @DisplayName("Debe implementar equals correctamente")
    void debeImplementarEqualsCorrectamente() {
        CatalogoOperacion catalogo1 = new CatalogoOperacion(id, "Desc1", "I");
        CatalogoOperacion catalogo2 = new CatalogoOperacion(id, "Desc2", "D");
        CatalogoOperacionId idDiferente = new CatalogoOperacionId("GRP002", "EMP002", "OTRO");
        CatalogoOperacion catalogo3 = new CatalogoOperacion(idDiferente, "Desc1", "I");

        // Mismo objeto
        assertEquals(catalogo1, catalogo1);
        
        // Misma ID, diferentes datos
        assertEquals(catalogo1, catalogo2);
        
        // Diferente ID
        assertNotEquals(catalogo1, catalogo3);
        
        // Comparación con null
        assertNotEquals(catalogo1, null);
        
        // Comparación con objeto de clase diferente
        assertNotEquals(catalogo1, "string");
    }

    @Test
    @DisplayName("Debe implementar hashCode correctamente")
    void debeImplementarHashCodeCorrectamente() {
        CatalogoOperacion catalogo1 = new CatalogoOperacion(id, "Desc1", "I");
        CatalogoOperacion catalogo2 = new CatalogoOperacion(id, "Desc2", "D");
        
        assertEquals(catalogo1.hashCode(), catalogo2.hashCode());
    }

    @Test
    @DisplayName("Debe generar toString correctamente")
    void debeGenerarToStringCorrectamente() {
        String toString = catalogoOperacion.toString();
        
        assertNotNull(toString);
        assertTrue(toString.contains("CatalogoOperacion"));
        assertTrue(toString.contains("id="));
        assertTrue(toString.contains("descripcion="));
        assertTrue(toString.contains("claveAfectaSaldo="));
        assertTrue(toString.contains("estatus="));
    }

    @Test
    @DisplayName("Debe manejar clave afecta saldo nula en métodos de dominio")
    void debeManejarClaveAfectaSaldoNulaEnMetodosDeDominio() {
        catalogoOperacion.setClaveAfectaSaldo(null);
        
        assertFalse(catalogoOperacion.incrementaSaldo());
        assertFalse(catalogoOperacion.decrementaSaldo());
        assertFalse(catalogoOperacion.noAfectaSaldo());
        // Cuando claveAfectaSaldo es null, el factor es -1 (comportamiento real del código)
        assertEquals(-1, catalogoOperacion.calcularFactorAfectacion(false));
        assertEquals(1, catalogoOperacion.calcularFactorAfectacion(true));
    }

    @Test
    @DisplayName("Debe manejar estatus nulo en métodos de dominio")
    void debeManejarEstatusNuloEnMetodosDeDominio() {
        catalogoOperacion.setEstatus(null);
        
        assertFalse(catalogoOperacion.estaActiva());
        assertFalse(catalogoOperacion.estaSuspendida());
    }
}