package com.financiero.domain.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para ParametroSistema")
class ParametroSistemaTest {

    private ParametroSistema parametroSistema;
    private ParametroSistemaId id;
    private LocalDate fechaMedio;

    @BeforeEach
    void setUp() {
        fechaMedio = LocalDate.of(2025, 1, 15);
        id = new ParametroSistemaId("GRP001", "EMP001", "SYSTEM");
        parametroSistema = new ParametroSistema();
        parametroSistema.setId(id);
        parametroSistema.setFechaMedio(fechaMedio);
    }

    @Test
    @DisplayName("Debe crear ParametroSistema con constructor vacío")
    void debeCrearParametroSistemaConConstructorVacio() {
        ParametroSistema parametro = new ParametroSistema();
        assertNotNull(parametro);
        assertNull(parametro.getId());
        assertNull(parametro.getFechaMedio());
    }

    @Test
    @DisplayName("Debe crear ParametroSistema con constructor completo")
    void debeCrearParametroSistemaConConstructorCompleto() {
        ParametroSistema parametro = new ParametroSistema(id, fechaMedio);
        
        assertEquals(id, parametro.getId());
        assertEquals(fechaMedio, parametro.getFechaMedio());
    }

    @Test
    @DisplayName("Debe manejar getters y setters correctamente")
    void debeManejarGettersYSettersCorrectamente() {
        ParametroSistema parametro = new ParametroSistema();
        ParametroSistemaId nuevoId = new ParametroSistemaId("GRP002", "EMP002", "CUSTOM");
        LocalDate nuevaFecha = LocalDate.of(2025, 2, 20);
        
        parametro.setId(nuevoId);
        parametro.setFechaMedio(nuevaFecha);
        
        assertEquals(nuevoId, parametro.getId());
        assertEquals(nuevaFecha, parametro.getFechaMedio());
    }

    @Test
    @DisplayName("Debe actualizar fecha medio correctamente")
    void debeActualizarFechaMedioCorrectamente() {
        LocalDate nuevaFecha = LocalDate.of(2025, 3, 10);
        
        parametroSistema.actualizarFechaMedio(nuevaFecha);
        
        assertEquals(nuevaFecha, parametroSistema.getFechaMedio());
    }

    @Test
    @DisplayName("Debe identificar si es sistema correctamente")
    void debeIdentificarSiEsSistema() {
        assertTrue(parametroSistema.esSistema());
        
        ParametroSistemaId idCustom = new ParametroSistemaId("GRP001", "EMP001", "CUSTOM");
        parametroSistema.setId(idCustom);
        assertFalse(parametroSistema.esSistema());
        
        ParametroSistemaId idUser = new ParametroSistemaId("GRP001", "EMP001", "USER");
        parametroSistema.setId(idUser);
        assertFalse(parametroSistema.esSistema());
    }

    @Test
    @DisplayName("Debe implementar equals correctamente")
    void debeImplementarEqualsCorrectamente() {
        ParametroSistema parametro1 = new ParametroSistema(id, fechaMedio);
        ParametroSistema parametro2 = new ParametroSistema(id, LocalDate.of(2025, 2, 20));
        ParametroSistemaId idDiferente = new ParametroSistemaId("GRP002", "EMP002", "OTHER");
        ParametroSistema parametro3 = new ParametroSistema(idDiferente, fechaMedio);

        // Mismo objeto
        assertEquals(parametro1, parametro1);
        
        // Misma ID, diferentes fechas
        assertEquals(parametro1, parametro2);
        
        // Diferente ID
        assertNotEquals(parametro1, parametro3);
        
        // Comparación con null
        assertNotEquals(parametro1, null);
        
        // Comparación con objeto de clase diferente
        assertNotEquals(parametro1, "string");
    }

    @Test
    @DisplayName("Debe implementar hashCode correctamente")
    void debeImplementarHashCodeCorrectamente() {
        ParametroSistema parametro1 = new ParametroSistema(id, fechaMedio);
        ParametroSistema parametro2 = new ParametroSistema(id, LocalDate.of(2025, 2, 20));
        
        assertEquals(parametro1.hashCode(), parametro2.hashCode());
    }

    @Test
    @DisplayName("Debe generar toString correctamente")
    void debeGenerarToStringCorrectamente() {
        String toString = parametroSistema.toString();
        
        assertNotNull(toString);
        assertTrue(toString.contains("ParametroSistema"));
        assertTrue(toString.contains("id="));
        assertTrue(toString.contains("fechaMedio="));
    }

    @Test
    @DisplayName("Debe manejar ID nulo en métodos de dominio")
    void debeManejarIdNuloEnMetodosDeDominio() {
        ParametroSistema parametroConIdNulo = new ParametroSistema();
        
        assertThrows(NullPointerException.class, () -> parametroConIdNulo.esSistema());
    }

    @Test
    @DisplayName("Debe manejar clave medio nula en métodos de dominio")
    void debeManejarClaveMedioNulaEnMetodosDeDominio() {
        ParametroSistemaId idNulo = new ParametroSistemaId();
        ParametroSistema parametroConClaveNula = new ParametroSistema();
        parametroConClaveNula.setId(idNulo);
        
        assertFalse(parametroConClaveNula.esSistema());
    }

    @Test
    @DisplayName("Debe permitir actualizar fecha medio con nulo")
    void debePermitirActualizarFechaMedioConNulo() {
        parametroSistema.actualizarFechaMedio(null);
        assertNull(parametroSistema.getFechaMedio());
    }

    @Test
    @DisplayName("Debe manejar equals con objetos nulos")
    void debeManejarEqualsConObjetosNulos() {
        ParametroSistema parametroConNulos = new ParametroSistema();
        ParametroSistema otroParametroConNulos = new ParametroSistema();
        
        assertEquals(parametroConNulos, otroParametroConNulos);
        assertNotEquals(parametroSistema, parametroConNulos);
    }
}