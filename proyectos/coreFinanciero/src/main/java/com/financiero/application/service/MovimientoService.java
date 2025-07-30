package com.financiero.application.service;

import com.financiero.domain.entity.*;
import com.financiero.infrastructure.repository.PreMovimientoRepository;
import com.financiero.infrastructure.repository.PreMovimientoDetalleRepository;
import com.financiero.exception.BusinessException;
import com.financiero.exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class MovimientoService {
    
    private static final Logger logger = LoggerFactory.getLogger(MovimientoService.class);
    
    private final PreMovimientoRepository preMovimientoRepository;
    private final PreMovimientoDetalleRepository preMovimientoDetalleRepository;
    private final FechaService fechaService;
    private final LiquidacionService liquidacionService;
    
    @Autowired
    public MovimientoService(PreMovimientoRepository preMovimientoRepository,
                           PreMovimientoDetalleRepository preMovimientoDetalleRepository,
                           FechaService fechaService,
                           LiquidacionService liquidacionService) {
        this.preMovimientoRepository = preMovimientoRepository;
        this.preMovimientoDetalleRepository = preMovimientoDetalleRepository;
        this.fechaService = fechaService;
        this.liquidacionService = liquidacionService;
    }
    
    /**
     * Equivalente al procedimiento pGeneraPreMovto del PL/SQL
     * Genera un movimiento de abono o cargo de tesorería
     */
    public String generarPreMovimiento(String claveGrupoEmpresa, String claveEmpresa, 
                                     Long idPreMovimiento, LocalDate fechaLiquidacion,
                                     Long idCuenta, Long idPrestamo, String claveDivisa,
                                     String claveOperacion, BigDecimal importeNeto,
                                     String claveMedio, String claveMercado, String nota,
                                     Long idGrupo, String claveUsuario, LocalDate fechaValor,
                                     Integer numeroPagoAmortizacion) {
        
        logger.info("Generando pre-movimiento {} para empresa: {}-{}", 
            idPreMovimiento, claveGrupoEmpresa, claveEmpresa);
        
        try {
            // Obtener fecha del sistema
            LocalDate fechaOperacion = fechaService.obtenerFechaSistema(claveGrupoEmpresa, claveEmpresa);
            
            // Validar fechas
            validarFechasMovimiento(claveGrupoEmpresa, claveEmpresa, fechaOperacion, 
                fechaLiquidacion, claveMercado);
            
            // Crear el pre-movimiento
            PreMovimientoId preMovimientoId = new PreMovimientoId(
                claveGrupoEmpresa, claveEmpresa, idPreMovimiento);
            
            PreMovimiento preMovimiento = new PreMovimiento(preMovimientoId);
            preMovimiento.setFechaOperacion(fechaOperacion);
            preMovimiento.setFechaLiquidacion(fechaLiquidacion);
            preMovimiento.setFechaAplicacion(fechaValor);
            preMovimiento.setIdCuenta(idCuenta);
            preMovimiento.setIdPrestamo(idPrestamo);
            preMovimiento.setClaveDivisa(claveDivisa);
            preMovimiento.setClaveOperacion(claveOperacion);
            preMovimiento.setImporteNeto(importeNeto);
            preMovimiento.setPrecioOperacion(BigDecimal.ZERO);
            preMovimiento.setTipoCambio(BigDecimal.ZERO);
            preMovimiento.setClaveMedio(claveMedio);
            preMovimiento.setClaveMercado(claveMercado);
            preMovimiento.setNota(nota);
            preMovimiento.setIdGrupo(idGrupo);
            preMovimiento.setIdMovimiento(0L);
            preMovimiento.setClaveUsuario(claveUsuario);
            preMovimiento.setSituacionPreMovimiento("NP");
            preMovimiento.setNumeroPagoAmortizacion(numeroPagoAmortizacion);
            
            // Guardar el pre-movimiento
            preMovimientoRepository.save(preMovimiento);
            
            logger.info("Pre-movimiento {} creado exitosamente", idPreMovimiento);
            return "Pre-movimiento generado exitosamente";
            
        } catch (BusinessException | ValidationException e) {
            logger.error("Error de negocio al generar pre-movimiento {}: {}", idPreMovimiento, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error al generar pre-movimiento {}", idPreMovimiento, e);
            throw new BusinessException("Error al insertar el premovimiento: " + e.getMessage());
        }
    }
    
    /**
     * Equivalente al procedimiento pGeneraPreMovtoDet del PL/SQL
     * Genera un registro con el detalle de un concepto de una operación de tesorería
     */
    public String generarPreMovimientoDetalle(String claveGrupoEmpresa, String claveEmpresa,
                                            Long idPreMovimiento, String claveConcepto,
                                            BigDecimal importeNeto, String nota) {
        
        logger.info("Generando detalle de pre-movimiento {} concepto {} para empresa: {}-{}", 
            idPreMovimiento, claveConcepto, claveGrupoEmpresa, claveEmpresa);
        
        try {
            // Crear el detalle del pre-movimiento
            PreMovimientoDetalleId detalleId = new PreMovimientoDetalleId(
                claveGrupoEmpresa, claveEmpresa, idPreMovimiento, claveConcepto);
            
            PreMovimientoDetalle detalle = new PreMovimientoDetalle(detalleId, importeNeto, nota);
            
            // Guardar el detalle
            preMovimientoDetalleRepository.save(detalle);
            
            logger.info("Detalle de pre-movimiento creado exitosamente para concepto {}", claveConcepto);
            return "Detalle de pre-movimiento generado exitosamente";
            
        } catch (Exception e) {
            logger.error("Error al generar detalle de pre-movimiento para concepto {}", claveConcepto, e);
            throw new BusinessException("Error al insertar el concepto: " + e.getMessage());
        }
    }
    
    /**
     * Valida las fechas del movimiento
     */
    private void validarFechasMovimiento(String claveGrupoEmpresa, String claveEmpresa,
                                       LocalDate fechaOperacion, LocalDate fechaLiquidacion,
                                       String claveMercado) {
        
        // Si no es préstamo, validar fechas
        if (!"PRESTAMO".equals(claveMercado)) {
            if (fechaLiquidacion.isBefore(fechaOperacion)) {
                throw new ValidationException("Fecha de liquidación incorrecta");
            }
            
            // Validar que la fecha de liquidación sea correcta
            boolean fechaValida = liquidacionService.validarFechaLiquidacion(
                claveGrupoEmpresa, claveEmpresa, fechaOperacion, fechaLiquidacion, claveMercado);
            
            if (!fechaValida) {
                throw new ValidationException("Fecha de liquidación incorrecta: " + fechaLiquidacion);
            }
        }
    }
    
    /**
     * Obtiene los pre-movimientos pendientes por fecha de liquidación
     */
    @Transactional(readOnly = true)
    public List<PreMovimiento> obtenerMovimientosPendientes(String claveGrupoEmpresa, 
                                                          String claveEmpresa, 
                                                          LocalDate fechaLiquidacion) {
        return preMovimientoRepository.findPendientesByFechaLiquidacion(
            claveGrupoEmpresa, claveEmpresa, fechaLiquidacion);
    }
    
    /**
     * Procesa los movimientos pendientes
     */
    public void procesarMovimientosPendientes(String claveGrupoEmpresa, String claveEmpresa) {
        logger.info("Procesando movimientos pendientes para empresa: {}-{}", claveGrupoEmpresa, claveEmpresa);
        
        List<PreMovimiento> movimientosPendientes = preMovimientoRepository
            .findByIdClaveGrupoEmpresaAndIdClaveEmpresaAndSituacionPreMovimiento(
                claveGrupoEmpresa, claveEmpresa, "NP");
        
        for (PreMovimiento movimiento : movimientosPendientes) {
            try {
                // Lógica de procesamiento del movimiento
                movimiento.marcarComoProcesado();
                preMovimientoRepository.save(movimiento);
                
                logger.debug("Movimiento {} procesado exitosamente", 
                    movimiento.getId().getIdPreMovimiento());
                
            } catch (Exception e) {
                logger.error("Error al procesar movimiento {}: {}", 
                    movimiento.getId().getIdPreMovimiento(), e.getMessage());
            }
        }
        
        logger.info("Procesamiento de movimientos pendientes completado");
    }
    
    /**
     * Obtiene un pre-movimiento por su ID
     */
    @Transactional(readOnly = true)
    public PreMovimiento obtenerPreMovimiento(String claveGrupoEmpresa, String claveEmpresa, 
                                            Long idPreMovimiento) {
        return preMovimientoRepository.findByIdClaveGrupoEmpresaAndIdClaveEmpresaAndIdIdPreMovimiento(
            claveGrupoEmpresa, claveEmpresa, idPreMovimiento)
            .orElseThrow(() -> new BusinessException("Pre-movimiento no encontrado: " + idPreMovimiento));
    }
    
    /**
     * Obtiene los detalles de un pre-movimiento
     */
    @Transactional(readOnly = true)
    public List<PreMovimientoDetalle> obtenerDetallesPreMovimiento(String claveGrupoEmpresa, 
                                                                 String claveEmpresa, 
                                                                 Long idPreMovimiento) {
        return preMovimientoDetalleRepository.findByIdClaveGrupoEmpresaAndIdClaveEmpresaAndIdIdPreMovimiento(
            claveGrupoEmpresa, claveEmpresa, idPreMovimiento);
    }
    
    /**
     * Calcula el total de conceptos de un pre-movimiento
     */
    @Transactional(readOnly = true)
    public BigDecimal calcularTotalConceptos(String claveGrupoEmpresa, String claveEmpresa, 
                                           Long idPreMovimiento) {
        BigDecimal total = preMovimientoDetalleRepository.sumImporteConceptoByPreMovimiento(
            claveGrupoEmpresa, claveEmpresa, idPreMovimiento);
        
        return total != null ? total : BigDecimal.ZERO;
    }
}