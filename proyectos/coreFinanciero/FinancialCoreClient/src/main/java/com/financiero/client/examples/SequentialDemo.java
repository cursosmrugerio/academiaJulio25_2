package com.financiero.client.examples;

import com.financiero.client.FinancialCoreHttp2Client;
import com.financiero.client.config.Http2Configuration;
import com.financiero.client.service.BalanceClient;
import com.financiero.client.service.MovementClient;
import com.financiero.client.service.MovementProcessorClient;
import com.financiero.client.model.SaldoResponse;
import com.financiero.client.model.PreMovimientoRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class SequentialDemo {
    
    private static final Logger logger = LoggerFactory.getLogger(SequentialDemo.class);
    
    private static final String BASE_URL = "http://localhost:8080";
    private static final String GRUPO_EMPRESA = "001";
    private static final String EMPRESA = "001";
    private static final String USUARIO = "DEMO_USER";
    
    public static void main(String[] args) {
        logger.info("🧪 DEMO SECUENCIAL - CORE FINANCIERO");
        logger.info("=====================================");
        
        Http2Configuration config = new Http2Configuration.Builder()
            .baseUrl(BASE_URL)
            .maxIdleConnections(5)
            .enableLogging(true)
            .build();
            
        try (FinancialCoreHttp2Client client = new FinancialCoreHttp2Client(config)) {
            
            // Verificar conectividad
            logger.info("🔍 Verificando conectividad...");
            if (!client.isHealthy()) {
                logger.error("❌ No se puede conectar al servidor");
                return;
            }
            logger.info("✅ Conectividad OK");
            
            BalanceClient balanceClient = new BalanceClient(client);
            MovementClient movementClient = new MovementClient(client);
            MovementProcessorClient processorClient = new MovementProcessorClient(client);
            
            // Test 1: Consultar saldos
            logger.info("\n=== TEST 1: CONSULTA DE SALDOS ===");
            try {
                List<SaldoResponse> saldos = balanceClient.consultarSaldos(GRUPO_EMPRESA, EMPRESA, LocalDate.now()).get();
                logger.info("✅ Saldos consultados exitosamente:");
                saldos.forEach(saldo -> 
                    logger.info("   Cuenta {} - {} {}", saldo.getIdCuenta(), saldo.getSaldoEfectivo(), saldo.getClaveDivisa())
                );
            } catch (Exception e) {
                logger.error("❌ Error consultando saldos: {}", e.getMessage());
            }
            
            // Test 2: Crear pre-movimiento individual
            logger.info("\n=== TEST 2: CREAR PRE-MOVIMIENTO ===");
            try {
                PreMovimientoRequest request = new PreMovimientoRequest();
                request.setClaveGrupoEmpresa(GRUPO_EMPRESA);
                request.setClaveEmpresa(EMPRESA);
                request.setIdPreMovimiento(20001L);
                request.setIdCuenta(1001L);
                request.setClaveDivisa("MXN");
                request.setFechaOperacion(LocalDate.now());
                request.setFechaLiquidacion(LocalDate.now().plusDays(1));
                request.setFechaAplicacion(LocalDate.now().plusDays(1));
                request.setClaveOperacion("DEP");
                request.setImporteNeto(new BigDecimal("1000.00"));
                request.setPrecioOperacion(BigDecimal.ONE);
                request.setTipoCambio(BigDecimal.ONE);
                request.setClaveMercado("SPOT");
                request.setClaveMedio("WEB");
                request.setTextoNota("Demo secuencial");
                request.setTextoReferencia("REF-DEMO-001");
                request.setClaveUsuario(USUARIO);
                
                String resultado = movementClient.crearPreMovimiento(request).get();
                logger.info("✅ Pre-movimiento creado: {}", resultado);
                
            } catch (Exception e) {
                logger.error("❌ Error creando pre-movimiento: {}", e.getMessage());
            }
            
            // Test 3: Procesamiento simple
            logger.info("\n=== TEST 3: PROCESAMIENTO SIMPLE ===");
            try {
                Thread.sleep(1000); // Esperar un momento entre operaciones
                processorClient.procesarPreMovimientos(GRUPO_EMPRESA, EMPRESA, LocalDate.now()).get();
                logger.info("✅ Pre-movimientos procesados exitosamente");
            } catch (Exception e) {
                logger.error("❌ Error procesando: {}", e.getMessage());
            }
            
            // Test 4: Saldos finales
            logger.info("\n=== TEST 4: SALDOS FINALES ===");
            try {
                List<SaldoResponse> saldosFinales = balanceClient.consultarSaldos(GRUPO_EMPRESA, EMPRESA, LocalDate.now()).get();
                logger.info("✅ Saldos finales:");
                saldosFinales.forEach(saldo -> 
                    logger.info("   Cuenta {} - {} {}", saldo.getIdCuenta(), saldo.getSaldoEfectivo(), saldo.getClaveDivisa())
                );
            } catch (Exception e) {
                logger.error("❌ Error consultando saldos finales: {}", e.getMessage());
            }
            
        } catch (Exception e) {
            logger.error("❌ Error en demo: {}", e.getMessage(), e);
        }
        
        logger.info("\n🏁 DEMO SECUENCIAL COMPLETADO");
    }
}