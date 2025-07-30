package com.financiero.application.service;

import com.financiero.domain.entity.ParametroSistema;
import com.financiero.domain.entity.ParametroSistemaId;
import com.financiero.domain.entity.DiaLiquidacion;
import com.financiero.domain.entity.DiaLiquidacionId;
import com.financiero.infrastructure.repository.ParametroSistemaRepository;
import com.financiero.infrastructure.repository.DiaLiquidacionRepository;
import com.financiero.exception.BusinessException;
import com.financiero.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
public class FechaService {
    
    private static final Logger logger = LoggerFactory.getLogger(FechaService.class);
    
    private final ParametroSistemaRepository parametroSistemaRepository;
    private final DiaLiquidacionRepository diaLiquidacionRepository;
    
    @Autowired
    public FechaService(ParametroSistemaRepository parametroSistemaRepository,
                       DiaLiquidacionRepository diaLiquidacionRepository) {
        this.parametroSistemaRepository = parametroSistemaRepository;
        this.diaLiquidacionRepository = diaLiquidacionRepository;
    }
    
    /**
     * Equivalente a la función RecorreFecha del PL/SQL
     * Actualiza la fecha medio de la tabla de parámetros al siguiente día hábil
     */
    public String recorrerFecha(String claveGrupoEmpresa, String claveEmpresa) {
        logger.info("Iniciando recorrido de fecha para empresa: {}-{}", claveGrupoEmpresa, claveEmpresa);
        
        try {
            // Obtener el parámetro del sistema
            ParametroSistemaId parametroId = new ParametroSistemaId(claveGrupoEmpresa, claveEmpresa, "SYSTEM");
            ParametroSistema parametroSistema = parametroSistemaRepository.findById(parametroId)
                .orElseThrow(() -> new NotFoundException("Parámetro del sistema no encontrado"));
            
            LocalDate fechaActual = parametroSistema.getFechaMedio();
            
            // Buscar la fecha de liquidación T+1 para la fecha actual
            DiaLiquidacionId diaLiquidacionId = new DiaLiquidacionId(
                claveGrupoEmpresa, claveEmpresa, "T+1", fechaActual);
            
            Optional<DiaLiquidacion> diaLiquidacion = diaLiquidacionRepository.findById(diaLiquidacionId);
            
            if (diaLiquidacion.isPresent()) {
                LocalDate nuevaFecha = diaLiquidacion.get().getFechaLiquidacion();
                parametroSistema.actualizarFechaMedio(nuevaFecha);
                parametroSistemaRepository.save(parametroSistema);
                
                logger.info("Fecha actualizada de {} a {} para empresa: {}-{}", 
                    fechaActual, nuevaFecha, claveGrupoEmpresa, claveEmpresa);
            } else {
                throw new BusinessException("No se encontró fecha de liquidación T+1 para la fecha: " + fechaActual);
            }
            
            return "El proceso ha terminado";
            
        } catch (Exception e) {
            logger.error("Error al recorrer fecha para empresa: {}-{}", claveGrupoEmpresa, claveEmpresa, e);
            throw new BusinessException("Error al recorrer fecha: " + e.getMessage());
        }
    }
    
    /**
     * Equivalente a la función dameFechaSistema del PL/SQL
     * Obtiene la fecha actual del sistema
     */
    @Transactional(readOnly = true)
    public LocalDate obtenerFechaSistema(String claveGrupoEmpresa, String claveEmpresa) {
        logger.debug("Obteniendo fecha del sistema para empresa: {}-{}", claveGrupoEmpresa, claveEmpresa);
        
        try {
            Optional<LocalDate> fechaSistema = parametroSistemaRepository.findFechaSistema(
                claveGrupoEmpresa, claveEmpresa);
            
            if (fechaSistema.isPresent()) {
                logger.debug("Fecha del sistema obtenida: {} para empresa: {}-{}", 
                    fechaSistema.get(), claveGrupoEmpresa, claveEmpresa);
                return fechaSistema.get();
            } else {
                logger.warn("No se encontró fecha del sistema para empresa: {}-{}", claveGrupoEmpresa, claveEmpresa);
                throw new NotFoundException("Fecha del sistema no encontrada para la empresa: " + 
                    claveGrupoEmpresa + "-" + claveEmpresa);
            }
            
        } catch (Exception e) {
            logger.error("Error al obtener fecha del sistema para empresa: {}-{}", 
                claveGrupoEmpresa, claveEmpresa, e);
            throw new BusinessException("Error al obtener fecha del sistema: " + e.getMessage());
        }
    }
    
    /**
     * Valida si una fecha es un día hábil (no fin de semana)
     */
    public boolean esDiaHabil(LocalDate fecha) {
        int dayOfWeek = fecha.getDayOfWeek().getValue();
        return dayOfWeek >= 1 && dayOfWeek <= 5; // Lunes a Viernes
    }
    
    /**
     * Obtiene el siguiente día hábil a partir de una fecha dada
     */
    public LocalDate obtenerSiguienteDiaHabil(LocalDate fecha) {
        LocalDate siguienteFecha = fecha.plusDays(1);
        while (!esDiaHabil(siguienteFecha)) {
            siguienteFecha = siguienteFecha.plusDays(1);
        }
        return siguienteFecha;
    }
    
    /**
     * Actualiza la fecha del sistema manualmente
     */
    public void actualizarFechaSistema(String claveGrupoEmpresa, String claveEmpresa, LocalDate nuevaFecha) {
        logger.info("Actualizando fecha del sistema a {} para empresa: {}-{}", 
            nuevaFecha, claveGrupoEmpresa, claveEmpresa);
        
        ParametroSistemaId parametroId = new ParametroSistemaId(claveGrupoEmpresa, claveEmpresa, "SYSTEM");
        ParametroSistema parametroSistema = parametroSistemaRepository.findById(parametroId)
            .orElseThrow(() -> new NotFoundException("Parámetro del sistema no encontrado"));
        
        parametroSistema.actualizarFechaMedio(nuevaFecha);
        parametroSistemaRepository.save(parametroSistema);
        
        logger.info("Fecha del sistema actualizada exitosamente");
    }
}