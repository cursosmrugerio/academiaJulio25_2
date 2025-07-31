package com.financiero.client;

import com.financiero.client.config.Http2Configuration;
import com.financiero.client.examples.FinancialCoreClientDemo;
import com.financiero.client.model.*;
import com.financiero.client.scenarios.*;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * CLI para el cliente HTTP/2 del Core Financiero
 * 
 * Permite ejecutar operaciones y escenarios desde línea de comandos
 */
@Command(name = "financial-core-client", 
         version = "1.0.0",
         description = "Cliente HTTP/2 para el Core Financiero",
         mixinStandardHelpOptions = true)
public class FinancialCoreClientCLI implements Callable<Integer> {
    
    @Option(names = {"-u", "--url"}, 
            description = "URL base del servidor (default: http://localhost:8080)")
    private String baseUrl = "http://localhost:8080";
    
    @Option(names = {"-g", "--grupo"}, 
            description = "Clave grupo empresa (default: 001)")
    private String claveGrupoEmpresa = "001";
    
    @Option(names = {"-e", "--empresa"}, 
            description = "Clave empresa (default: 001)")
    private String claveEmpresa = "001";
    
    @Option(names = {"-U", "--usuario"}, 
            description = "Clave usuario (default: CLI_USER)")
    private String claveUsuario = "CLI_USER";
    
    @Option(names = {"-v", "--verbose"}, 
            description = "Habilitar logging detallado")
    private boolean verbose = false;
    
    @Parameters(description = "Comando a ejecutar: demo, deposito, cancelacion, consultar-saldos, health")
    private String comando;
    
    public static void main(String[] args) {
        int exitCode = new CommandLine(new FinancialCoreClientCLI()).execute(args);
        System.exit(exitCode);
    }
    
    @Override
    public Integer call() throws Exception {
        System.out.println("🏦 Cliente HTTP/2 Core Financiero v1.0.0");
        System.out.println("=" .repeat(50));
        
        if (comando == null) {
            System.out.println("❌ Debe especificar un comando");
            System.out.println("Comandos disponibles:");
            System.out.println("  demo                - Ejecutar demo completo");
            System.out.println("  deposito            - Ejecutar escenario de depósito");
            System.out.println("  cancelacion         - Ejecutar escenario de cancelación");
            System.out.println("  consultar-saldos    - Consultar saldos actuales");
            System.out.println("  health              - Verificar conectividad");
            return 1;
        }
        
        // Configurar cliente HTTP/2
        Http2Configuration.Builder configBuilder = new Http2Configuration.Builder()
            .baseUrl(baseUrl);
        
        if (verbose) {
            configBuilder.enableLogging(true)
                         .loggingLevel(okhttp3.logging.HttpLoggingInterceptor.Level.BODY);
        }
        
        Http2Configuration config = configBuilder.build();
        
        try (FinancialCoreHttp2Client client = new FinancialCoreHttp2Client(config)) {
            
            System.out.printf("🌐 Conectando a: %s%n", baseUrl);
            System.out.printf("🏢 Empresa: %s-%s%n", claveGrupoEmpresa, claveEmpresa);
            System.out.printf("👤 Usuario: %s%n", claveUsuario);
            System.out.println();
            
            return switch (comando.toLowerCase()) {
                case "demo" -> ejecutarDemo();
                case "deposito" -> ejecutarEscenarioDeposito(client);
                case "cancelacion" -> ejecutarEscenarioCancelacion(client);
                case "consultar-saldos" -> consultarSaldos(client);
                case "health" -> verificarHealth(client);
                default -> {
                    System.out.println("❌ Comando no reconocido: " + comando);
                    yield 1;
                }
            };
            
        } catch (Exception e) {
            System.err.printf("❌ Error: %s%n", e.getMessage());
            if (verbose) {
                e.printStackTrace();
            }
            return 1;
        }
    }
    
    private int ejecutarDemo() {
        System.out.println("🚀 Ejecutando demo completo...");
        try {
            FinancialCoreClientDemo.main(new String[0]);
            return 0;
        } catch (Exception e) {
            System.err.printf("❌ Error en demo: %s%n", e.getMessage());
            return 1;
        }
    }
    
    private int ejecutarEscenarioDeposito(FinancialCoreHttp2Client client) {
        System.out.println("💰 Ejecutando escenario de depósito...");
        
        try {
            // Verificar conectividad
            if (!client.isHealthy()) {
                System.out.println("❌ Servidor no disponible");
                return 1;
            }
            
            DepositoCompletoScenario scenario = new DepositoCompletoScenario(
                client, claveGrupoEmpresa, claveEmpresa, claveUsuario);
            
            ScenarioResult resultado = scenario.ejecutar(
                System.currentTimeMillis(), // ID único
                1001L, // Cuenta demo
                new BigDecimal("2500.00"),
                LocalDate.now().plusDays(1),
                "Depósito desde CLI"
            ).get();
            
            System.out.println(resultado.generateReport());
            return resultado.isSuccess() ? 0 : 1;
            
        } catch (Exception e) {
            System.err.printf("❌ Error en escenario de depósito: %s%n", e.getMessage());
            return 1;
        }
    }
    
    private int ejecutarEscenarioCancelacion(FinancialCoreHttp2Client client) {
        System.out.println("❌ Ejecutando escenario de cancelación...");
        
        try {
            // Verificar conectividad
            if (!client.isHealthy()) {
                System.out.println("❌ Servidor no disponible");
                return 1;
            }
            
            CancelacionMovimientoScenario scenario = new CancelacionMovimientoScenario(
                client, claveGrupoEmpresa, claveEmpresa, claveUsuario);
            
            ScenarioResult resultado = scenario.ejecutar(
                System.currentTimeMillis(), // ID único
                1001L, // Cuenta demo
                new BigDecimal("1000.00"),
                LocalDate.now().plusDays(1),
                "Retiro a cancelar desde CLI"
            ).get();
            
            System.out.println(resultado.generateReport());
            return resultado.isSuccess() ? 0 : 1;
            
        } catch (Exception e) {
            System.err.printf("❌ Error en escenario de cancelación: %s%n", e.getMessage());
            return 1;
        }
    }
    
    private int consultarSaldos(FinancialCoreHttp2Client client) {
        System.out.println("💳 Consultando saldos...");
        
        try {
            List<SaldoResponse> saldos = client.movementProcessor()
                .consultarSaldosActuales(claveGrupoEmpresa, claveEmpresa)
                .get();
            
            System.out.println("\\n📊 SALDOS ACTUALES:");
            System.out.println("-".repeat(60));
            
            if (saldos.isEmpty()) {
                System.out.println("No hay saldos registrados");
            } else {
                System.out.printf("%-10s %-8s %-15s %-12s%n", 
                                "CUENTA", "DIVISA", "SALDO", "FECHA");
                System.out.println("-".repeat(60));
                
                for (SaldoResponse saldo : saldos) {
                    System.out.printf("%-10d %-8s %15.2f %-12s%n",
                                    saldo.getIdCuenta(),
                                    saldo.getClaveDivisa(),
                                    saldo.getSaldoEfectivo(),
                                    saldo.getFechaFoto());
                }
            }
            
            return 0;
            
        } catch (Exception e) {
            System.err.printf("❌ Error consultando saldos: %s%n", e.getMessage());
            return 1;
        }
    }
    
    private int verificarHealth(FinancialCoreHttp2Client client) {
        System.out.println("🏥 Verificando estado del servidor...");
        
        try {
            boolean healthy = client.isHealthy();
            
            if (healthy) {
                System.out.println("✅ Servidor disponible y funcionando");
                
                // Mostrar estadísticas de conexión
                FinancialCoreHttp2Client.ConnectionStats stats = client.getConnectionStats();
                System.out.println("\\n📊 Estadísticas de conexión HTTP/2:");
                System.out.printf("   Conexiones totales: %d%n", stats.getTotalConnections());
                System.out.printf("   Conexiones activas: %d%n", stats.getActiveConnections());
                System.out.printf("   Conexiones idle: %d%n", stats.getIdleConnections());
                System.out.println("   Protocolo: HTTP/2 ✅");
                
                return 0;
            } else {
                System.out.println("❌ Servidor no disponible");
                System.out.printf("   Verificar que el Core Financiero esté ejecutándose en %s%n", baseUrl);
                return 1;
            }
            
        } catch (Exception e) {
            System.err.printf("❌ Error verificando health: %s%n", e.getMessage());
            return 1;
        }
    }
}