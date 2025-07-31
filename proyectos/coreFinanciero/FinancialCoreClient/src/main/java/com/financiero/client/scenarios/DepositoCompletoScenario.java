package com.financiero.client.scenarios;

import com.financiero.client.FinancialCoreHttp2Client;
import com.financiero.client.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DepositoCompletoScenario {
    
    private static final Logger logger = LoggerFactory.getLogger(DepositoCompletoScenario.class);
    
    private final FinancialCoreHttp2Client client;
    private final String claveGrupoEmpresa;
    private final String claveEmpresa;
    private final String claveUsuario;
    
    public DepositoCompletoScenario(FinancialCoreHttp2Client client, 
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
        
        logger.info("=== INICIANDO ESCENARIO: DEPÓSITO COMPLETO ===");
        
        ScenarioResult result = new ScenarioResult("Depósito Completo");
        LocalDate fechaProceso = LocalDate.now();
        
        return CompletableFuture.supplyAsync(() -> {
            try {
                // Paso 1: Consultar saldos iniciales
                List<SaldoResponse> saldosIniciales = client.movementProcessor()
                    .consultarSaldosActuales(claveGrupoEmpresa, claveEmpresa)
                    .get();
                
                result.addStep("Saldos iniciales consultados", 
                             "Saldos antes del depósito: " + formatearSaldos(saldosIniciales));
                
                // Paso 2: Crear pre-movimiento de depósito
                PreMovimientoRequest preMovimiento = PreMovimientoRequest.builder()
                    .empresa(claveGrupoEmpresa, claveEmpresa)
                    .idPreMovimiento(idPreMovimiento)
                    .deposito(importe, "MXN")
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
                
                result.addStep("Pre-movimiento creado", 
                             "Pre-movimiento " + idPreMovimiento + " creado exitosamente");
                
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
                             "Movimiento procesado a estado virtual");
                
                // Paso 4: Consultar saldos virtuales
                List<SaldoResponse> saldosVirtuales = client.movementProcessor()
                    .consultarSaldosActuales(claveGrupoEmpresa, claveEmpresa)
                    .get();
                
                result.addStep("Saldos virtuales consultados", 
                             "Saldos con afectación virtual: " + formatearSaldos(saldosVirtuales));
                
                // Paso 5: Procesar virtual a real
                ApiResponse responseReal = client.movementProcessor()
                    .procesarMovimientosVirtualesAReales(requestProceso)
                    .get();
                
                if (!responseReal.isSuccess()) {
                    throw new RuntimeException("Error procesando a real: " + responseReal.getMessage());
                }
                
                result.addStep("Procesado a real (PR)", 
                             "Movimientos procesados a estado real final");
                
                // Paso 6: Consultar saldos finales
                List<SaldoResponse> saldosFinales = client.movementProcessor()
                    .consultarSaldosActuales(claveGrupoEmpresa, claveEmpresa)
                    .get();
                
                result.addStep("Saldos finales consultados", 
                             "Saldos con afectación real: " + formatearSaldos(saldosFinales));
                
                // Calcular impacto en saldos
                BigDecimal impactoSaldo = calcularImpactoSaldo(saldosIniciales, saldosFinales, idCuenta);
                result.addStep("Impacto calculado", 
                             "Incremento en saldo de cuenta " + idCuenta + ": " + impactoSaldo);
                
                result.setSuccess(true);
                result.setSummary(String.format(
                    "Depósito de %s MXN procesado exitosamente. " +
                    "Flujo: NP → PV → PR. Impacto en saldo: +%s MXN", 
                    importe, impactoSaldo));
                
                logger.info("=== ESCENARIO COMPLETADO EXITOSAMENTE ===");
                return result;
                
            } catch (Exception e) {
                logger.error("Error ejecutando escenario de depósito: {}", e.getMessage(), e);
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