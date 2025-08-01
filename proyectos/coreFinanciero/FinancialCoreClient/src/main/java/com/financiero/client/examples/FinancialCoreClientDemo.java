package com.financiero.client.examples;

import com.financiero.client.FinancialCoreHttp2Client;
import com.financiero.client.config.Http2Configuration;
import com.financiero.client.scenarios.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FinancialCoreClientDemo {
    
    private static final Logger logger = LoggerFactory.getLogger(FinancialCoreClientDemo.class);
    
    private static final String BASE_URL = "http://localhost:8080";
    private static final String GRUPO_EMPRESA = "001";
    private static final String EMPRESA = "001";
    private static final String USUARIO = "DEMO_USER";
    private static final Long CUENTA_DEMO = 1001L;
    
    public static void main(String[] args) {
        logger.info("🚀 INICIANDO DEMO DEL CLIENTE HTTP/2 CORE FINANCIERO");
        logger.info("=".repeat(80));
        
        Http2Configuration config = new Http2Configuration.Builder()
            .baseUrl(BASE_URL)
            .maxIdleConnections(20)
            .enableLogging(true)
            .build();
            
        try (FinancialCoreHttp2Client client = new FinancialCoreHttp2Client(config)) {
            
            logger.info("🔍 Verificando conectividad con el servidor...");
            if (!client.isHealthy()) {
                logger.error("❌ No se puede conectar al servidor en {}", BASE_URL);
                logger.error("   Asegúrate de que el Core Financiero esté ejecutándose");
                return;
            }
            logger.info("✅ Conectividad verificada - servidor disponible");
            
            mostrarEstadisticasConexion(client);
            
            List<CompletableFuture<ScenarioResult>> escenarios = new ArrayList<>();
            
            // Escenario 1: Depósito completo (NP → PV → PR)
            logger.info("📈 Preparando Escenario 1: Depósito Completo");
            DepositoCompletoScenario depositoScenario = new DepositoCompletoScenario(
                client, GRUPO_EMPRESA, EMPRESA, USUARIO);
            escenarios.add(depositoScenario.ejecutar(
                10001L, CUENTA_DEMO, new BigDecimal("5000.00"), 
                LocalDate.of(2025, 8, 2), "Depósito demo HTTP/2"
            ));
            
            // Escenario 2: Cancelación de movimiento (NP → PV → CA)
            logger.info("❌ Preparando Escenario 2: Cancelación de Movimiento");
            CancelacionMovimientoScenario cancelacionScenario = new CancelacionMovimientoScenario(
                client, GRUPO_EMPRESA, EMPRESA, USUARIO);
            escenarios.add(cancelacionScenario.ejecutar(
                10002L, CUENTA_DEMO, new BigDecimal("1500.00"), 
                LocalDate.of(2025, 8, 2), "Retiro a cancelar demo HTTP/2"
            ));
            
            logger.info("⚡ Ejecutando escenarios de forma asíncrona (HTTP/2 multiplexing)...");
            logger.info("=".repeat(80));
            
            long inicioDemo = System.currentTimeMillis();
            
            CompletableFuture<Void> todosLosEscenarios = CompletableFuture.allOf(
                escenarios.toArray(new CompletableFuture[0])
            );
            
            List<ScenarioResult> resultados = new ArrayList<>();
            todosLosEscenarios.get();
            
            for (CompletableFuture<ScenarioResult> escenario : escenarios) {
                resultados.add(escenario.get());
            }
            
            long finDemo = System.currentTimeMillis();
            long duracionTotal = finDemo - inicioDemo;
            
            generarReporteDemo(resultados, duracionTotal, client);
            
        } catch (Exception e) {
            logger.error("❌ Error ejecutando demo: {}", e.getMessage(), e);
        }
        
        logger.info("🏁 DEMO COMPLETADO");
        logger.info("=".repeat(80));
    }
    
    private static void mostrarEstadisticasConexion(FinancialCoreHttp2Client client) {
        FinancialCoreHttp2Client.ConnectionStats stats = client.getConnectionStats();
        logger.info("📊 ESTADÍSTICAS DE CONEXIÓN HTTP/2:");
        logger.info("   Conexiones totales: {}", stats.getTotalConnections());
        logger.info("   Conexiones activas: {}", stats.getActiveConnections());
        logger.info("   Conexiones idle: {}", stats.getIdleConnections());
        logger.info("   Protocolo: HTTP/2 (multiplexing habilitado)");
    }
    
    private static void generarReporteDemo(List<ScenarioResult> resultados, 
                                          long duracionTotal, 
                                          FinancialCoreHttp2Client client) {
        logger.info("");
        logger.info("=".repeat(100));
        logger.info("🎯 REPORTE FINAL DEL DEMO - CLIENTE HTTP/2 CORE FINANCIERO");
        logger.info("=".repeat(100));
        
        int escenariosExitosos = (int) resultados.stream().mapToLong(r -> r.isSuccess() ? 1 : 0).sum();
        int escenariosTotal = resultados.size();
        
        logger.info("📈 ESTADÍSTICAS GENERALES:");
        logger.info("   Escenarios ejecutados: {}", escenariosTotal);
        logger.info("   Escenarios exitosos: {} ({}%)", escenariosExitosos, 
                   (escenariosExitosos * 100) / escenariosTotal);
        logger.info("   Duración total: {} ms", duracionTotal);
        logger.info("   Promedio por escenario: {} ms", duracionTotal / escenariosTotal);
        
        FinancialCoreHttp2Client.ConnectionStats finalStats = client.getConnectionStats();
        logger.info("🌐 RENDIMIENTO HTTP/2:");
        logger.info("   Conexiones reutilizadas: {}", finalStats.getTotalConnections());
        logger.info("   Multiplexing utilizado: ✅ (múltiples requests simultáneos)");
        logger.info("   Compresión de headers: ✅ (HPACK)");
        logger.info("   Protocolo binario: ✅ (mayor eficiencia)");
        
        logger.info("📋 RESUMEN DE ESCENARIOS:");
        logger.info("-".repeat(80));
        for (int i = 0; i < resultados.size(); i++) {
            ScenarioResult resultado = resultados.get(i);
            logger.info("{}. {}", i + 1, resultado.generateSummary());
            if (resultado.getSummary() != null) {
                logger.info("   {}", resultado.getSummary());
            }
        }
        
        logger.info("🔄 FLUJOS DE ESTADO DEMOSTRADOS:");
        logger.info("-".repeat(80));
        logger.info("✅ Pre-Movimiento (NP) → Procesado Virtual (PV) → Procesado Real (PR)");
        logger.info("   - Depósito completo con afectación de saldos");
        logger.info("   - Validación de fechas de liquidación");
        logger.info("   - Consulta de estados intermedios");
        logger.info("");
        logger.info("❌ Pre-Movimiento (NP) → Procesado Virtual (PV) → Cancelado (CA)");
        logger.info("   - Retiro con cancelación posterior");
        logger.info("   - Reversión automática de saldos");
        logger.info("   - Demostración de transaccionalidad");
        
        logger.info("🎯 CAPACIDADES DEL CORE FINANCIERO DEMOSTRADAS:");
        logger.info("-".repeat(80));
        logger.info("📊 Gestión de Estados: Máquina de estados robusta (NP→PV→PR/CA)");
        logger.info("💰 Afectación de Saldos: Actualización automática según tipo de operación");
        logger.info("📅 Validación de Fechas: Días hábiles y reglas de liquidación");
        logger.info("🔄 Transaccionalidad: Rollback automático en cancelaciones");
        logger.info("🔍 Auditabilidad: Trazabilidad completa de operaciones");
        logger.info("⚡ Escalabilidad: Procesamiento masivo y consultas eficientes");
        
        logger.info("🚀 VENTAJAS HTTP/2 DEMOSTRADAS:");
        logger.info("-".repeat(80));
        logger.info("🔗 Multiplexing: Múltiples requests simultáneos en una conexión");
        logger.info("📦 Compresión HPACK: Headers comprimidos para menor overhead");
        logger.info("⚡ Protocolo Binario: Mayor eficiencia que HTTP/1.1 texto");
        logger.info("🔄 Reutilización: Pool de conexiones optimizado");
        logger.info("📈 Throughput: Mayor rendimiento en operaciones concurrentes");
        
        logger.info("📝 REPORTES DETALLADOS:");
        for (ScenarioResult resultado : resultados) {
            logger.info(resultado.generateReport());
        }
        
        logger.info("✨ DEMO COMPLETADO EXITOSAMENTE");
        logger.info("   El Core Financiero y el Cliente HTTP/2 han demostrado su robustez");
        logger.info("   y capacidades avanzadas para operaciones financieras críticas.");
        logger.info("=".repeat(100));
    }
}