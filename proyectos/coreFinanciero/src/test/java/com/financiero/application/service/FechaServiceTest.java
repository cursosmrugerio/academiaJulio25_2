package com.financiero.application.service;

import com.financiero.domain.entity.ParametroSistema;
import com.financiero.domain.entity.ParametroSistemaId;
import com.financiero.domain.entity.DiaLiquidacion;
import com.financiero.domain.entity.DiaLiquidacionId;
import com.financiero.infrastructure.repository.ParametroSistemaRepository;
import com.financiero.infrastructure.repository.DiaLiquidacionRepository;
import com.financiero.exception.BusinessException;
import com.financiero.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FechaServiceTest {
    
    @Mock
    private ParametroSistemaRepository parametroSistemaRepository;
    
    @Mock
    private DiaLiquidacionRepository diaLiquidacionRepository;
    
    @InjectMocks
    private FechaService fechaService;
    
    private String claveGrupoEmpresa;
    private String claveEmpresa;
    private LocalDate fechaActual;
    private LocalDate fechaSiguiente;
    
    @BeforeEach
    void setUp() {
        claveGrupoEmpresa = "GRP001";
        claveEmpresa = "EMP001";
        fechaActual = LocalDate.of(2025, 1, 15);
        fechaSiguiente = LocalDate.of(2025, 1, 16);
    }
    
    @Test
    void testRecorrerFecha_Exitoso() {
        // Arrange
        ParametroSistemaId parametroId = new ParametroSistemaId(claveGrupoEmpresa, claveEmpresa, "SYSTEM");
        ParametroSistema parametroSistema = new ParametroSistema(parametroId, fechaActual);
        
        DiaLiquidacionId diaLiquidacionId = new DiaLiquidacionId(
            claveGrupoEmpresa, claveEmpresa, "T+1", fechaActual);
        DiaLiquidacion diaLiquidacion = new DiaLiquidacion(diaLiquidacionId, fechaSiguiente);
        
        when(parametroSistemaRepository.findById(parametroId)).thenReturn(Optional.of(parametroSistema));
        when(diaLiquidacionRepository.findById(diaLiquidacionId)).thenReturn(Optional.of(diaLiquidacion));
        when(parametroSistemaRepository.save(any(ParametroSistema.class))).thenReturn(parametroSistema);
        
        // Act
        String resultado = fechaService.recorrerFecha(claveGrupoEmpresa, claveEmpresa);
        
        // Assert
        assertEquals("El proceso ha terminado", resultado);
        verify(parametroSistemaRepository).findById(parametroId);
        verify(diaLiquidacionRepository).findById(diaLiquidacionId);
        verify(parametroSistemaRepository).save(parametroSistema);
        assertEquals(fechaSiguiente, parametroSistema.getFechaMedio());
    }
    
    @Test
    void testRecorrerFecha_ParametroNoEncontrado() {
        // Arrange
        ParametroSistemaId parametroId = new ParametroSistemaId(claveGrupoEmpresa, claveEmpresa, "SYSTEM");
        when(parametroSistemaRepository.findById(parametroId)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(NotFoundException.class, () -> 
            fechaService.recorrerFecha(claveGrupoEmpresa, claveEmpresa));
        
        verify(parametroSistemaRepository).findById(parametroId);
        verifyNoInteractions(diaLiquidacionRepository);
    }
    
    @Test
    void testRecorrerFecha_FechaLiquidacionNoEncontrada() {
        // Arrange
        ParametroSistemaId parametroId = new ParametroSistemaId(claveGrupoEmpresa, claveEmpresa, "SYSTEM");
        ParametroSistema parametroSistema = new ParametroSistema(parametroId, fechaActual);
        
        DiaLiquidacionId diaLiquidacionId = new DiaLiquidacionId(
            claveGrupoEmpresa, claveEmpresa, "T+1", fechaActual);
        
        when(parametroSistemaRepository.findById(parametroId)).thenReturn(Optional.of(parametroSistema));
        when(diaLiquidacionRepository.findById(diaLiquidacionId)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(BusinessException.class, () -> 
            fechaService.recorrerFecha(claveGrupoEmpresa, claveEmpresa));
        
        verify(parametroSistemaRepository).findById(parametroId);
        verify(diaLiquidacionRepository).findById(diaLiquidacionId);
        verify(parametroSistemaRepository, never()).save(any());
    }
    
    @Test
    void testObtenerFechaSistema_Exitoso() {
        // Arrange
        when(parametroSistemaRepository.findFechaSistema(claveGrupoEmpresa, claveEmpresa))
            .thenReturn(Optional.of(fechaActual));
        
        // Act
        LocalDate resultado = fechaService.obtenerFechaSistema(claveGrupoEmpresa, claveEmpresa);
        
        // Assert
        assertEquals(fechaActual, resultado);
        verify(parametroSistemaRepository).findFechaSistema(claveGrupoEmpresa, claveEmpresa);
    }
    
    @Test
    void testObtenerFechaSistema_NoEncontrada() {
        // Arrange
        when(parametroSistemaRepository.findFechaSistema(claveGrupoEmpresa, claveEmpresa))
            .thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(NotFoundException.class, () -> 
            fechaService.obtenerFechaSistema(claveGrupoEmpresa, claveEmpresa));
        
        verify(parametroSistemaRepository).findFechaSistema(claveGrupoEmpresa, claveEmpresa);
    }
    
    @Test
    void testEsDiaHabil() {
        // Test días hábiles (Lunes a Viernes)
        assertTrue(fechaService.esDiaHabil(LocalDate.of(2025, 1, 13))); // Lunes
        assertTrue(fechaService.esDiaHabil(LocalDate.of(2025, 1, 14))); // Martes
        assertTrue(fechaService.esDiaHabil(LocalDate.of(2025, 1, 15))); // Miércoles
        assertTrue(fechaService.esDiaHabil(LocalDate.of(2025, 1, 16))); // Jueves
        assertTrue(fechaService.esDiaHabil(LocalDate.of(2025, 1, 17))); // Viernes
        
        // Test fines de semana
        assertFalse(fechaService.esDiaHabil(LocalDate.of(2025, 1, 18))); // Sábado
        assertFalse(fechaService.esDiaHabil(LocalDate.of(2025, 1, 19))); // Domingo
    }
    
    @Test
    void testObtenerSiguienteDiaHabil() {
        // Desde miércoles al jueves
        LocalDate miercoles = LocalDate.of(2025, 1, 15);
        LocalDate jueves = LocalDate.of(2025, 1, 16);
        assertEquals(jueves, fechaService.obtenerSiguienteDiaHabil(miercoles));
        
        // Desde viernes al lunes siguiente
        LocalDate viernes = LocalDate.of(2025, 1, 17);
        LocalDate lunes = LocalDate.of(2025, 1, 20);
        assertEquals(lunes, fechaService.obtenerSiguienteDiaHabil(viernes));
        
        // Desde sábado al lunes
        LocalDate sabado = LocalDate.of(2025, 1, 18);
        assertEquals(lunes, fechaService.obtenerSiguienteDiaHabil(sabado));
    }
    
    @Test
    void testActualizarFechaSistema_Exitoso() {
        // Arrange
        LocalDate nuevaFecha = LocalDate.of(2025, 1, 20);
        ParametroSistemaId parametroId = new ParametroSistemaId(claveGrupoEmpresa, claveEmpresa, "SYSTEM");
        ParametroSistema parametroSistema = new ParametroSistema(parametroId, fechaActual);
        
        when(parametroSistemaRepository.findById(parametroId)).thenReturn(Optional.of(parametroSistema));
        when(parametroSistemaRepository.save(any(ParametroSistema.class))).thenReturn(parametroSistema);
        
        // Act
        fechaService.actualizarFechaSistema(claveGrupoEmpresa, claveEmpresa, nuevaFecha);
        
        // Assert
        verify(parametroSistemaRepository).findById(parametroId);
        verify(parametroSistemaRepository).save(parametroSistema);
        assertEquals(nuevaFecha, parametroSistema.getFechaMedio());
    }
    
    @Test
    void testActualizarFechaSistema_ParametroNoEncontrado() {
        // Arrange
        LocalDate nuevaFecha = LocalDate.of(2025, 1, 20);
        ParametroSistemaId parametroId = new ParametroSistemaId(claveGrupoEmpresa, claveEmpresa, "SYSTEM");
        
        when(parametroSistemaRepository.findById(parametroId)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(NotFoundException.class, () -> 
            fechaService.actualizarFechaSistema(claveGrupoEmpresa, claveEmpresa, nuevaFecha));
        
        verify(parametroSistemaRepository).findById(parametroId);
        verify(parametroSistemaRepository, never()).save(any());
    }
}