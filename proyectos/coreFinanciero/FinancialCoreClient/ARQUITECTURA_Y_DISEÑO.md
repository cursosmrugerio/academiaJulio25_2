# 🏗️ Arquitectura y Diseño - FinancialCoreClient

## 📋 Resumen Ejecutivo

El **FinancialCoreClient** es un cliente HTTP/2 robusto diseñado para demostrar y validar las capacidades del Core Financiero. La solución implementa patrones de arquitectura empresarial, utilizando tecnologías modernas de Java para garantizar alta performance, mantenibilidad y escalabilidad.

---

## 🎯 Objetivos de Diseño

### Objetivos Principales
1. **Demostración de Capacidades**: Mostrar todas las funcionalidades del Core Financiero
2. **Validación de Performance**: Probar HTTP/2 multiplexing y procesamiento asíncrono
3. **Facilidad de Uso**: Interfaces múltiples (CLI, Demo, Script) para diferentes audiencias
4. **Robustez Empresarial**: Manejo de errores, logging, métricas y recuperación ante fallos

### Criterios de Decisión
- **Tecnología HTTP/2**: Para maximizar performance con multiplexing
- **Java 21**: LTS más reciente con características modernas
- **Arquitectura por Capas**: Separación clara de responsabilidades
- **Patrón Builder**: Para configuración flexible
- **Factory Pattern**: Para creación de clientes especializados

---

## 🏛️ Arquitectura General

```
┌─────────────────────────────────────────────────────────────┐
│                    CAPA DE PRESENTACIÓN                     │
├─────────────────────────────────────────────────────────────┤
│  CLI (PicoCLI)     │  Demo Programático  │  Script Bash     │
│  FinancialCore     │  FinancialCore      │  run-client.sh   │
│  ClientCLI         │  ClientDemo         │                  │
└─────────────────────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────┐
│                     CAPA DE ORQUESTACIÓN                    │
├─────────────────────────────────────────────────────────────┤
│              FinancialCoreHttp2Client                       │
│  • Pool de conexiones HTTP/2                               │
│  • Gestión de servicios                                    │
│  • Métricas y monitoreo                                    │
│  • Configuración centralizada                              │
└─────────────────────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────┐
│                     CAPA DE SERVICIOS                       │
├─────────────────────────────────────────────────────────────┤
│ MovementProcessor │ Movement │ Liquidation │ DateSystem     │
│ Client            │ Client   │ Client      │ Client         │
└─────────────────────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────┐
│                    CAPA DE ESCENARIOS                       │
├─────────────────────────────────────────────────────────────┤
│ DepositoCompleto  │ CancelacionMovimiento │ ScenarioResult │
│ Scenario          │ Scenario              │                │
└─────────────────────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────┐
│                   CAPA DE TRANSPORTE                        │
├─────────────────────────────────────────────────────────────┤
│              OkHttp3 + HTTP/2 + Jackson                    │
│  • Multiplexing de conexiones                              │
│  • Serialización/Deserialización JSON                      │
│  • Pool de conexiones optimizado                           │
│  • Logging e interceptores                                 │
└─────────────────────────────────────────────────────────────┘
```

---

## 🔧 Stack Tecnológico

### Core Framework
| Tecnología | Versión | Propósito | Justificación |
|------------|---------|-----------|---------------|
| **Java** | 21 LTS | Lenguaje base | LTS con características modernas (Records, Pattern Matching, Virtual Threads) |
| **Maven** | 3.9+ | Build System | Estándar empresarial, gestión de dependencias robusta |

### HTTP & Comunicaciones
| Tecnología | Versión | Propósito | Justificación |
|------------|---------|-----------|---------------|
| **OkHttp3** | 4.12.0 | Cliente HTTP/2 | Mejor performance HTTP/2, multiplexing nativo, pool de conexiones |
| **Jackson** | 2.17.2 | Serialización JSON | Estándar de facto, soporte completo para Java Time API |

### CLI & Interfaz de Usuario
| Tecnología | Versión | Propósito | Justificación |
|------------|---------|-----------|---------------|
| **PicoCLI** | 4.7.6 | Framework CLI | CLI empresarial robusto, ayuda automática, validación de parámetros |

### Logging & Monitoreo
| Tecnología | Versión | Propósito | Justificación |
|------------|---------|-----------|---------------|
| **SLF4J** | 2.0.13 | API de Logging | Estándar de logging, abstracción flexible |
| **Logback** | 1.5.6 | Implementación Logging | Performance superior, configuración flexible |
| **Micrometer** | 1.13.2 | Métricas | Métricas empresariales, integración con sistemas de monitoreo |

### Testing
| Tecnología | Versión | Propósito | Justificación |
|------------|---------|-----------|---------------|
| **JUnit 5** | 5.10.3 | Framework de Testing | Estándar moderno, soporte para tests parametrizados |
| **Mockito** | 5.12.0 | Mocking | Mejor framework de mocking para Java |
| **MockWebServer** | 4.12.0 | Testing HTTP | Testing de clientes HTTP sin servidor real |

---

## 📦 Estructura de Paquetes

```
com.financiero.client/
├── 🏛️ FinancialCoreHttp2Client.java      # Cliente principal (Facade Pattern)
├── 🖥️ FinancialCoreClientCLI.java        # CLI con PicoCLI
├── config/
│   └── 🔧 Http2Configuration.java        # Configuración HTTP/2 (Builder Pattern)
├── service/                              # Clientes especializados por dominio
│   ├── 💰 MovementProcessorClient.java   # Procesamiento de movimientos
│   ├── 📊 MovementClient.java            # Gestión de movimientos
│   ├── 🏦 LiquidationClient.java         # Liquidaciones
│   └── 📅 DateSystemClient.java          # Sistema de fechas
├── model/                                # DTOs y modelos de datos
│   ├── 📄 ApiResponse.java               # Respuesta genérica de API
│   ├── 💳 PreMovimientoRequest.java      # Request para pre-movimientos
│   ├── 🔄 ProcesarMovimientosRequest.java# Request para procesamiento
│   ├── 💵 SaldoResponse.java             # Respuesta de saldos
│   └── 📈 MovimientoResponse.java        # Respuesta de movimientos
├── scenarios/                            # Escenarios de negocio completos
│   ├── 🎬 DepositoCompletoScenario.java  # Escenario NP → PV → PR
│   ├── ❌ CancelacionMovimientoScenario.java # Escenario NP → PV → CA
│   └── 📋 ScenarioResult.java            # Resultado de escenarios
└── examples/
    └── 🚀 FinancialCoreClientDemo.java   # Demo programático completo
```

---

## 🏗️ Patrones de Diseño Implementados

### 1. **Facade Pattern** - `FinancialCoreHttp2Client`
```java
public class FinancialCoreHttp2Client implements Closeable {
    // Oculta la complejidad de múltiples clientes especializados
    private final MovementProcessorClient movementProcessorClient;
    private final MovementClient movementClient;
    private final LiquidationClient liquidationClient;
    
    // Interfaz simplificada
    public MovementProcessorClient movementProcessor() { return movementProcessorClient; }
    public MovementClient movement() { return movementClient; }
}
```

### 2. **Builder Pattern** - Configuración y Requests
```java
// Configuración HTTP/2
Http2Configuration config = new Http2Configuration.Builder()
    .baseUrl("http://localhost:8080")
    .maxIdleConnections(20)
    .enableLogging(true)
    .build();

// Requests de negocio
PreMovimientoRequest request = PreMovimientoRequest.builder()
    .empresa("001", "001")
    .deposito(new BigDecimal("5000.00"), "MXN")
    .cuenta(1001L)
    .build();
```

### 3. **Factory Pattern** - Configuraciones Especializadas
```java
public static Http2Configuration defaultConfig() { ... }
public static Http2Configuration productionConfig(String baseUrl) { ... }
public static Http2Configuration testingConfig() { ... }
```

### 4. **Command Pattern** - CLI con PicoCLI
```java
@Command(name = "deposito", description = "Ejecutar escenario de depósito")
public class DepositoCommand implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        // Lógica del comando
    }
}
```

### 5. **Strategy Pattern** - Escenarios de Negocio
```java
public interface FinancialScenario {
    CompletableFuture<ScenarioResult> ejecutar(ParametrosEscenario parametros);
}
```

---

## 🔄 Flujo de Arquitectura

### 1. **Inicialización del Cliente**
```java
// 1. Configuración
Http2Configuration config = Http2Configuration.defaultConfig();

// 2. Cliente principal (Facade)
FinancialCoreHttp2Client client = new FinancialCoreHttp2Client(config);

// 3. Pool de conexiones HTTP/2 (automático)
// 4. Clientes especializados (automático)
// 5. Métricas y logging (automático)
```

### 2. **Ejecución de Escenarios**
```java
// 1. Selección de escenario
DepositoCompletoScenario scenario = new DepositoCompletoScenario(client, ...);

// 2. Ejecución asíncrona
CompletableFuture<ScenarioResult> future = scenario.ejecutar(...);

// 3. HTTP/2 Multiplexing (automático)
// 4. Procesamiento paralelo de requests
// 5. Agregación de resultados
```

### 3. **Manejo de Respuestas**
```java
// 1. Deserialización JSON automática
ApiResponse response = client.movement().crearPreMovimiento(request).get();

// 2. Validación de respuesta
if (response.isSuccess()) {
    // Procesar resultado exitoso
} else {
    // Manejo de errores
}
```

---

## ⚡ Características de Performance

### HTTP/2 Multiplexing
- **Una sola conexión TCP** para múltiples requests simultáneos
- **Eliminación de Head-of-Line blocking** del HTTP/1.1
- **Compresión HPACK** para headers repetitivos
- **Server Push** preparado (si el servidor lo soporta)

### Pool de Conexiones Optimizado
```java
ConnectionPool connectionPool = new ConnectionPool(
    maxIdleConnections: 10,        // Conexiones idle máximas
    keepAliveDuration: 5 minutos,  // Tiempo de vida
    TimeUnit.MILLISECONDS
);
```

### Operaciones Asíncronas
- **CompletableFuture** para todas las operaciones de red
- **ExecutorService** con thread pool optimizado
- **Non-blocking I/O** completo

---

## 🛡️ Manejo de Errores y Resilencia

### Estrategia de Retry
```java
OkHttpClient.Builder builder = new OkHttpClient.Builder()
    .retryOnConnectionFailure(true)  // Retry automático
    .connectTimeout(30, TimeUnit.SECONDS)
    .readTimeout(60, TimeUnit.SECONDS)
    .writeTimeout(60, TimeUnit.SECONDS);
```

### Manejo de Excepciones por Capas
1. **Capa de Transporte**: IOException, TimeoutException
2. **Capa de Servicios**: ApiException, SerializationException  
3. **Capa de Escenarios**: BusinessException con contexto
4. **Capa de Presentación**: User-friendly error messages

### Circuit Breaker Pattern (Preparado)
```java
// Configuración lista para Hystrix o Resilience4j
private final MeterRegistry meterRegistry; // Métricas para decisiones
```

---

## 📊 Métricas y Monitoreo

### Métricas Implementadas
```java
// Tiempo de respuesta por endpoint
Timer.Sample sample = Timer.start(meterRegistry);
// ... llamada HTTP ...
sample.stop(Timer.builder("http.request.duration")
    .tag("endpoint", "/api/v1/movimientos")
    .register(meterRegistry));

// Contador de errores
meterRegistry.counter("http.request.errors", 
    "endpoint", "/api/v1/movimientos",
    "status", "500").increment();
```

### Logging Estructurado
```java
logger.info("Ejecutando escenario de depósito - ID: {}, Cuenta: {}, Importe: {}", 
           idPreMovimiento, idCuenta, importe);
```

---

## 🧪 Estrategia de Testing

### Testing por Capas
```java
// 1. Unit Tests - Lógica de negocio
@Test
void testDepositoScenarioLogic() { ... }

// 2. Integration Tests - Comunicación HTTP
@Test
void testMovementClientIntegration() {
    MockWebServer server = new MockWebServer();
    // Test real HTTP calls
}

// 3. Contract Tests - Validación de APIs
@Test  
void testApiResponseDeserialization() { ... }
```

### Test Doubles
- **MockWebServer** para simular el backend
- **Mockito** para mocking de dependencias
- **TestContainers** (preparado) para tests de integración completos

---

## 🚀 Decisiones de Diseño Clave

### 1. **¿Por qué OkHttp3 sobre HttpClient nativo?**
- **HTTP/2 Multiplexing más maduro** en OkHttp3
- **Interceptores** para logging y headers personalizados
- **Connection Pool** más configurable
- **Ecosistema** más rico (MockWebServer, etc.)

### 2. **¿Por qué PicoCLI sobre Apache Commons CLI?**
- **Annotations-based** más limpio que programático
- **Ayuda automática** y validación de parámetros
- **Subcommands** nativos
- **Type conversion** automático

### 3. **¿Por qué Jackson sobre Gson?**
- **Performance superior** en benchmarks
- **Java Time API** support nativo
- **Streaming API** para datos grandes
- **Ecosistema Spring** compatible

### 4. **¿Por qué Arquitectura por Capas?**
- **Separación de responsabilidades** clara
- **Testability** mejorada
- **Maintainability** a largo plazo
- **Escalabilidad** para nuevas funcionalidades

---

## 📈 Escalabilidad y Extensibilidad

### Agregar Nuevos Servicios
```java
// 1. Crear cliente especializado
public class NewServiceClient extends BaseServiceClient { ... }

// 2. Registrar en el cliente principal
private final NewServiceClient newServiceClient;

// 3. Exponer en facade
public NewServiceClient newService() { return newServiceClient; }
```

### Agregar Nuevos Escenarios
```java
// 1. Implementar interfaz común
public class NuevoEscenario implements FinancialScenario { ... }

// 2. Registrar en CLI
case "nuevo-escenario" -> ejecutarNuevoEscenario(client);
```

### Configuraciones por Ambiente
```java
// Desarrollo
Http2Configuration.defaultConfig()

// Testing
Http2Configuration.testingConfig()

// Producción
Http2Configuration.productionConfig("https://prod.financiero.com")
```

---

## 🎯 Conclusiones

El **FinancialCoreClient** implementa una arquitectura robusta y escalable que cumple con los estándares empresariales de Java. La solución combina:

- **Performance de clase empresarial** con HTTP/2 multiplexing
- **Flexibilidad de uso** con múltiples interfaces (CLI, Demo, Script)
- **Mantenibilidad** con patrones de diseño well-established
- **Observabilidad** con métricas y logging estructurado
- **Testability** con estrategia de testing por capas

Esta arquitectura permite tanto demostraciones ejecutivas como validaciones técnicas profundas del Core Financiero, estableciendo las bases para un cliente de producción completo.

---

*Documento técnico generado para el proyecto FinancialCoreClient v1.0.0*