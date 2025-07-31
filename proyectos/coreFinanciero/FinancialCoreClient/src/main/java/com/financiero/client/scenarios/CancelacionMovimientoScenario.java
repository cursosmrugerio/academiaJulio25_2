package com.financiero.client.scenarios;

import com.financiero.client.FinancialCoreHttp2Client;
import com.financiero.client.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CancelacionMovimientoScenario {
    
    private static final Logger logger = LoggerFactory.getLogger(CancelacionMovimientoScenario.class);
    
    private final FinancialCoreHttp2Client client;
    private final String claveGrupoEmpresa;
    private final String claveEmpresa;
    private final String claveUsuario;
    
    public CancelacionMovimientoScenario(FinancialCoreHttp2Client client, 
                                        String claveGrupoEmpresa, 
                                        String claveEmpresa, 
                                        String claveUsuario) {
        this.client = client;
        this.claveGrupoEmpresa = claveGrupoEmpresa;
        this.claveEmpresa = claveEmpresa;
        this.claveUsuario = claveUsuario;
    }
    
    public CompletableFuture<ScenarioResult> ejecutar(Long idPreMovimiento, 
                                                     Long idCuenta, 
                                                     BigDecimal importe, 
                                                     LocalDate fechaLiquidacion,
                                                     String nota) {
        
        logger.info("=== INICIANDO ESCENARIO: CANCELACIÓN DE MOVIMIENTO ===");
        
        ScenarioResult result = new ScenarioResult("Cancelación de Movimiento");
        LocalDate fechaProceso = LocalDate.now();
        
        return CompletableFuture.supplyAsync(() -> {
            try {
                // Paso 1: Consultar saldos iniciales
                List<SaldoResponse> saldosIniciales = client.movementProcessor()
                    .consultarSaldosActuales(claveGrupoEmpresa, claveEmpresa)
                    .get();
                
                result.addStep("Saldos iniciales consultados", 
                             "Saldos antes de la operación: " + formatearSaldos(saldosIniciales));
                
                // Paso 2: Crear pre-movimiento de retiro
                PreMovimientoRequest preMovimiento = PreMovimientoRequest.builder()
                    .empresa(claveGrupoEmpresa, claveEmpresa)
                    .idPreMovimiento(idPreMovimiento)
                    .retiro(importe, "MXN")
                    .cuenta(idCuenta)
                    .fechaLiquidacion(fechaLiquidacion)
                    .usuario(claveUsuario)
                    .nota(nota)
                    .build();
                
                ApiResponse responseCreacion = client.movement()
                    .crearPreMovimiento(preMovimiento)
                    .get();
                
                if (!responseCreacion.isSuccess()) {
                    throw new RuntimeException("Error creando pre-movimiento: " + responseCreacion.getMessage());
                }
                
                result.addStep("Pre-movimiento de retiro creado", 
                             "Pre-movimiento " + idPreMovimiento + " de retiro creado");
                
                // Paso 3: Procesar pre-movimiento a virtual
                ProcesarMovimientosRequest requestProceso = ProcesarMovimientosRequest.builder()
                    .empresa(claveGrupoEmpresa, claveEmpresa)
                    .fechaProceso(fechaProceso)
                    .claveUsuario(claveUsuario)
                    .build();
                
                ApiResponse responseVirtual = client.movementProcessor()
                    .procesarPreMovimientos(requestProceso)
                    .get();
                
                if (!responseVirtual.isSuccess()) {
                    throw new RuntimeException("Error procesando a virtual: " + responseVirtual.getMessage());
                }
                
                result.addStep("Procesado a virtual (PV)", 
                             "Retiro procesado a estado virtual - saldos afectados temporalmente");
                
                // Paso 4: Consultar saldos después del procesamiento virtual  
                List<SaldoResponse> saldosVirtuales = client.movementProcessor()
                    .consultarSaldosActuales(claveGrupoEmpresa, claveEmpresa)
                    .get();
                
                result.addStep("Saldos virtuales consultados", 
                             "Saldos con retiro virtual: " + formatearSaldos(saldosVirtuales));
                
                // Paso 5: Obtener ID del movimiento creado
                List<MovimientoResponse> movimientosVirtuales = client.movementProcessor()
                    .consultarMovimientosVirtuales(claveGrupoEmpresa, claveEmpresa)
                    .get();
                
                if (movimientosVirtuales.isEmpty()) {
                    throw new RuntimeException("No se encontraron movimientos virtuales para cancelar");
                }
                
                MovimientoResponse movimientoACancelar = movimientosVirtuales.stream()
                    .filter(m -> idPreMovimiento.equals(m.getIdPreMovimiento()))
                    .findFirst()
                    .orElse(movimientosVirtuales.get(movimientosVirtuales.size() - 1));
                
                Long idMovimiento = movimientoACancelar.getIdMovimiento();
                result.addStep("Movimiento identificado", 
                             "Movimiento " + idMovimiento + " identificado para cancelación");
                
                // Paso 6: Cancelar el movimiento
                ApiResponse responseCancelacion = client.movementProcessor()
                    .cancelarMovimiento(claveGrupoEmpresa, claveEmpresa, idMovimiento, claveUsuario)
                    .get();
                
                if (!responseCancelacion.isSuccess()) {
                    throw new RuntimeException("Error cancelando movimiento: " + responseCancelacion.getMessage());
                }
                
                result.addStep("Movimiento cancelado (CA)", 
                             "Movimiento " + idMovimiento + " cancelado exitosamente");
                
                // Paso 7: Consultar saldos después de la cancelación
                List<SaldoResponse> saldosFinales = client.movementProcessor()
                    .consultarSaldosActuales(claveGrupoEmpresa, claveEmpresa)
                    .get();
                
                result.addStep("Saldos finales consultados", 
                             "Saldos después de cancelación: " + formatearSaldos(saldosFinales));
                
                // Paso 8: Verificar reversión de saldos
                BigDecimal impactoFinal = calcularImpactoSaldo(saldosIniciales, saldosFinales, idCuenta);
                
                boolean saldosRevertidos = impactoFinal.compareTo(BigDecimal.ZERO) == 0;
                result.addStep("Reversión verificada", 
                             "Saldos revertidos correctamente: " + saldosRevertidos + 
                             " (diferencia: " + impactoFinal + ")");
                
                result.setSuccess(saldosRevertidos);
                result.setSummary(String.format(
                    "Movimiento de retiro por %s MXN cancelado exitosamente. " +
                    "Flujo: NP → PV → CA. Saldos revertidos: %s", 
                    importe, saldosRevertidos ? "✅" : "❌"));
                
                logger.info("=== ESCENARIO DE CANCELACIÓN COMPLETADO ===");
                
                return result;
                
            } catch (Exception e) {
                logger.error("Error ejecutando escenario de cancelación: {}", e.getMessage(), e);
                result.setSuccess(false);
                result.setError("Error en escenario: " + e.getMessage());
                return result;
            }
        });
    }
    
    private String formatearSaldos(List<SaldoResponse> saldos) {
        if (saldos.isEmpty()) {
            return "Sin saldos";
        }
        
        StringBuilder sb = new StringBuilder();
        for (SaldoResponse saldo : saldos) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(String.format("Cta:%d=%s %s", 
                                  saldo.getIdCuenta(), 
                                  saldo.getSaldoEfectivo(), 
                                  saldo.getClaveDivisa()));
        }
        return sb.toString();
    }
    
    private BigDecimal calcularImpactoSaldo(List<SaldoResponse> saldosIniciales, 
                                           List<SaldoResponse> saldosFinales, 
                                           Long idCuenta) {
        BigDecimal saldoInicial = encontrarSaldoPorCuenta(saldosIniciales, idCuenta);
        BigDecimal saldoFinal = encontrarSaldoPorCuenta(saldosFinales, idCuenta);
        
        return saldoFinal.subtract(saldoInicial);
    }
    
    private BigDecimal encontrarSaldoPorCuenta(List<SaldoResponse> saldos, Long idCuenta) {
        return saldos.stream()
            .filter(s -> idCuenta.equals(s.getIdCuenta()))
            .map(SaldoResponse::getSaldoEfectivo)
            .findFirst()
            .orElse(BigDecimal.ZERO);
    }
}