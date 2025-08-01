package com.financiero.application.service;

import com.financiero.domain.entity.*;
import com.financiero.domain.enums.MovimientoEstado;
import com.financiero.infrastructure.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MovimientoProcesadorService {
    
    private static final Logger logger = LoggerFactory.getLogger(MovimientoProcesadorService.class);
    
    @Autowired
    private MovimientoRepository movimientoRepository;
    
    @Autowired
    private MovimientoDetalleRepository movimientoDetalleRepository;
    
    @Autowired
    private CatalogoOperacionRepository catalogoOperacionRepository;
    
    @Autowired
    private SaldoRepository saldoRepository;
    
    @Autowired
    private PreMovimientoRepository preMovimientoRepository;
    
    
    public void procesarPreMovimientos(String claveGrupoEmpresa, String claveEmpresa, LocalDate fechaProceso) {
        logger.info("Iniciando procesamiento de pre-movimientos para empresa {}-{} fecha {}", 
                   claveGrupoEmpresa, claveEmpresa, fechaProceso);
        
        List<PreMovimiento> preMovimientosPendientes = preMovimientoRepository
            .findByIdClaveGrupoEmpresaAndIdClaveEmpresaAndSituacionPreMovimiento(claveGrupoEmpresa, claveEmpresa, "NP");
        
        for (PreMovimiento preMovimiento : preMovimientosPendientes) {
            try {
                procesarPreMovimiento(preMovimiento, fechaProceso);
            } catch (Exception e) {
                logger.error("Error procesando pre-movimiento {}: {}", 
                           preMovimiento.getId().getIdPreMovimiento(), e.getMessage());
            }
        }
        
        logger.info("Finalizó procesamiento de {} pre-movimientos", preMovimientosPendientes.size());
    }
    
    private void procesarPreMovimiento(PreMovimiento preMovimiento, LocalDate fechaProceso) {
        PreMovimientoId preId = preMovimiento.getId();
        
        Optional<CatalogoOperacion> catalogoOp = catalogoOperacionRepository
            .findByEmpresaAndOperacion(preId.getClaveGrupoEmpresa(), preId.getClaveEmpresa(), 
                                     preMovimiento.getClaveOperacion());
        
        if (!catalogoOp.isPresent() || !"A".equals(catalogoOp.get().getEstatus())) {
            throw new RuntimeException("Operación no válida o inactiva: " + preMovimiento.getClaveOperacion());
        }
        
        Long nuevoIdMovimiento = obtenerSiguienteIdMovimiento(preId.getClaveGrupoEmpresa(), preId.getClaveEmpresa());
        
        Movimiento nuevoMovimiento = crearMovimientoDesdePreMovimiento(preMovimiento, nuevoIdMovimiento, fechaProceso);
        
        movimientoRepository.save(nuevoMovimiento);
        
        List<PreMovimientoDetalle> detalles = preMovimiento.getDetalles();
        for (PreMovimientoDetalle detalle : detalles) {
            MovimientoDetalle movimientoDetalle = crearDetalleMovimiento(nuevoMovimiento, detalle);
            movimientoDetalleRepository.save(movimientoDetalle);
        }
        
        if ("I".equals(catalogoOp.get().getClaveAfectaSaldo()) || "D".equals(catalogoOp.get().getClaveAfectaSaldo())) {
            afectarSaldo(nuevoMovimiento, catalogoOp.get());
        }
        
        preMovimiento.setSituacionPreMovimiento("PR");
        preMovimiento.setIdMovimiento(nuevoIdMovimiento);
        preMovimientoRepository.save(preMovimiento);
        
        logger.info("Pre-movimiento {} procesado exitosamente como movimiento {}", 
                   preId.getIdPreMovimiento(), nuevoIdMovimiento);
    }
    
    private Movimiento crearMovimientoDesdePreMovimiento(PreMovimiento preMovimiento, Long idMovimiento, LocalDate fechaProceso) {
        PreMovimientoId preId = preMovimiento.getId();
        
        MovimientoId movimientoId = new MovimientoId(
            preId.getClaveGrupoEmpresa(),
            preId.getClaveEmpresa(),
            idMovimiento
        );
        
        Movimiento movimiento = new Movimiento();
        movimiento.setId(movimientoId);
        movimiento.setIdCuenta(preMovimiento.getIdCuenta());
        movimiento.setClaveDivisa(preMovimiento.getClaveDivisa());
        movimiento.setFechaOperacion(preMovimiento.getFechaOperacion());
        movimiento.setFechaLiquidacion(preMovimiento.getFechaLiquidacion());
        movimiento.setFechaAplicacion(preMovimiento.getFechaAplicacion());
        movimiento.setClaveOperacion(preMovimiento.getClaveOperacion());
        movimiento.setImporteNeto(preMovimiento.getImporteNeto());
        movimiento.setPrecioOperacion(preMovimiento.getPrecioOperacion());
        movimiento.setTipoCambio(preMovimiento.getTipoCambio());
        movimiento.setIdPrestamo(preMovimiento.getIdPrestamo());
        movimiento.setClaveMercado(preMovimiento.getClaveMercado());
        movimiento.setClaveMedio(preMovimiento.getClaveMedio());
        movimiento.setNota(preMovimiento.getNota());
        movimiento.setIdGrupo(preMovimiento.getIdGrupo());
        movimiento.setIdPreMovimiento(preId.getIdPreMovimiento());
        movimiento.setSituacionMovimiento("PV");
        movimiento.setFechaHoraRegistro(LocalDateTime.now());
        movimiento.setClaveUsuario(preMovimiento.getClaveUsuario());
        movimiento.setNumeroPagoAmortizacion(preMovimiento.getNumeroPagoAmortizacion());
        
        return movimiento;
    }
    
    private MovimientoDetalle crearDetalleMovimiento(Movimiento movimiento, PreMovimientoDetalle preDetalle) {
        MovimientoDetalleId detalleId = new MovimientoDetalleId(
            movimiento.getId().getClaveGrupoEmpresa(),
            movimiento.getId().getClaveEmpresa(),
            movimiento.getId().getIdMovimiento(),
            preDetalle.getId().getClaveConcepto()
        );
        
        MovimientoDetalle detalle = new MovimientoDetalle();
        detalle.setId(detalleId);
        detalle.setImporteConcepto(preDetalle.getImporteConcepto());
        detalle.setNota(preDetalle.getNota());
        
        return detalle;
    }
    
    private void afectarSaldo(Movimiento movimiento, CatalogoOperacion catalogoOperacion) {
        if (movimiento.getIdCuenta() == null || movimiento.getClaveDivisa() == null) {
            logger.warn("Movimiento {} no tiene cuenta o divisa definida", movimiento.getId().getIdMovimiento());
            return;
        }
        
        LocalDate fechaSaldo = movimiento.getFechaLiquidacion() != null ? 
            movimiento.getFechaLiquidacion() : movimiento.getFechaOperacion();
        
        Optional<Saldo> saldoOpt = saldoRepository.findByCuentaFechaDivisa(
            movimiento.getId().getClaveGrupoEmpresa(),
            movimiento.getId().getClaveEmpresa(),
            fechaSaldo,
            movimiento.getIdCuenta(),
            movimiento.getClaveDivisa()
        );
        
        Saldo saldo;
        if (saldoOpt.isPresent()) {
            saldo = saldoOpt.get();
        } else {
            SaldoId saldoId = new SaldoId(
                movimiento.getId().getClaveGrupoEmpresa(),
                movimiento.getId().getClaveEmpresa(),
                fechaSaldo,
                movimiento.getIdCuenta(),
                movimiento.getClaveDivisa()
            );
            saldo = new Saldo(saldoId, BigDecimal.ZERO);
        }
        
        int factorAfectacion = obtenerFactorAfectacion(catalogoOperacion.getClaveAfectaSaldo());
        saldo.afectarSaldo(movimiento.getImporteNeto(), factorAfectacion);
        
        saldoRepository.save(saldo);
        
        logger.debug("Saldo afectado para cuenta {} divisa {} fecha {}: factor={}, importe={}", 
                    movimiento.getIdCuenta(), movimiento.getClaveDivisa(), fechaSaldo,
                    factorAfectacion, movimiento.getImporteNeto());
    }
    
    private int obtenerFactorAfectacion(String claveAfectaSaldo) {
        switch (claveAfectaSaldo) {
            case "I": return 1;
            case "D": return -1;
            default: return 0;
        }
    }
    
    private synchronized Long obtenerSiguienteIdMovimiento(String claveGrupoEmpresa, String claveEmpresa) {
        Long maxId = movimientoRepository.findMaxIdMovimiento(claveGrupoEmpresa, claveEmpresa);
        return (maxId != null ? maxId : 0L) + 1;
    }
    
    public void procesarMovimientosVirtualesAReales(String claveGrupoEmpresa, String claveEmpresa, LocalDate fechaProceso) {
        logger.info("Procesando movimientos virtuales a reales para empresa {}-{} fecha {}", 
                   claveGrupoEmpresa, claveEmpresa, fechaProceso);
        
        List<Movimiento> movimientosVirtuales = movimientoRepository
            .findByEmpresaAndSituacion(claveGrupoEmpresa, claveEmpresa, "PV");
        
        for (Movimiento movimiento : movimientosVirtuales) {
            try {
                procesarMovimientoVirtualAReal(movimiento, fechaProceso);
            } catch (Exception e) {
                logger.error("Error procesando movimiento virtual {}: {}", 
                           movimiento.getId().getIdMovimiento(), e.getMessage());
            }
        }
        
        logger.info("Finalizó procesamiento de {} movimientos virtuales", movimientosVirtuales.size());
    }
    
    private void procesarMovimientoVirtualAReal(Movimiento movimiento, LocalDate fechaProceso) {
        MovimientoEstado estadoActual = MovimientoEstado.fromCodigo(movimiento.getSituacionMovimiento());
        
        if (estadoActual.puedeTransicionarA(MovimientoEstado.PROCESADO_REAL)) {
            movimiento.setSituacionMovimiento(MovimientoEstado.PROCESADO_REAL.getCodigo());
            movimiento.setFechaHoraActivacion(LocalDateTime.now());
            
            movimientoRepository.save(movimiento);
            
            logger.info("Movimiento {} procesado de virtual a real", movimiento.getId().getIdMovimiento());
        } else {
            logger.warn("Movimiento {} no puede transicionar a estado real desde {}", 
                       movimiento.getId().getIdMovimiento(), estadoActual);
        }
    }
    
    public void cancelarMovimiento(String claveGrupoEmpresa, String claveEmpresa, Long idMovimiento, String claveUsuario) {
        Optional<Movimiento> movimientoOpt = movimientoRepository.findById(
            new MovimientoId(claveGrupoEmpresa, claveEmpresa, idMovimiento)
        );
        
        if (!movimientoOpt.isPresent()) {
            throw new RuntimeException("Movimiento no encontrado: " + idMovimiento);
        }
        
        Movimiento movimiento = movimientoOpt.get();
        MovimientoEstado estadoActual = MovimientoEstado.fromCodigo(movimiento.getSituacionMovimiento());
        
        if (!estadoActual.puedeTransicionarA(MovimientoEstado.CANCELADO)) {
            throw new RuntimeException("Movimiento no puede ser cancelado desde estado: " + estadoActual);
        }
        
        Optional<CatalogoOperacion> catalogoOp = catalogoOperacionRepository
            .findByEmpresaAndOperacion(claveGrupoEmpresa, claveEmpresa, movimiento.getClaveOperacion());
        
        if (catalogoOp.isPresent() && 
            ("I".equals(catalogoOp.get().getClaveAfectaSaldo()) || "D".equals(catalogoOp.get().getClaveAfectaSaldo()))) {
            reversarAfectacionSaldo(movimiento, catalogoOp.get());
        }
        
        movimiento.setSituacionMovimiento(MovimientoEstado.CANCELADO.getCodigo());
        movimiento.setClaveUsuarioCancela(claveUsuario);
        movimiento.setFechaHoraActivacion(LocalDateTime.now());
        
        movimientoRepository.save(movimiento);
        
        logger.info("Movimiento {} cancelado por usuario {}", idMovimiento, claveUsuario);
    }
    
    private void reversarAfectacionSaldo(Movimiento movimiento, CatalogoOperacion catalogoOperacion) {
        if (movimiento.getIdCuenta() == null || movimiento.getClaveDivisa() == null) {
            return;
        }
        
        LocalDate fechaSaldo = movimiento.getFechaLiquidacion() != null ? 
            movimiento.getFechaLiquidacion() : movimiento.getFechaOperacion();
        
        Optional<Saldo> saldoOpt = saldoRepository.findByCuentaFechaDivisa(
            movimiento.getId().getClaveGrupoEmpresa(),
            movimiento.getId().getClaveEmpresa(),
            fechaSaldo,
            movimiento.getIdCuenta(),
            movimiento.getClaveDivisa()
        );
        
        if (saldoOpt.isPresent()) {
            Saldo saldo = saldoOpt.get();
            int factorAfectacion = obtenerFactorAfectacion(catalogoOperacion.getClaveAfectaSaldo());
            saldo.afectarSaldo(movimiento.getImporteNeto(), -factorAfectacion);
            
            saldoRepository.save(saldo);
            
            logger.debug("Saldo reversado para movimiento cancelado {}", movimiento.getId().getIdMovimiento());
        }
    }
}