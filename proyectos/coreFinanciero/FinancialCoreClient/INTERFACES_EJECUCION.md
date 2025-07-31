# 🚀 **Interfaces de Ejecución del Cliente HTTP/2**

El cliente HTTP/2 del Core Financiero ofrece tres formas diferentes de ejecutarse, cada una diseñada para casos de uso específicos:

---

## 1. **📟 CLI Interactivo: `FinancialCoreClientCLI`**

**Propósito**: Interfaz de línea de comandos para ejecutar operaciones específicas del Core Financiero.

**Características**:
- Comandos individuales y focalizados
- Parámetros configurables (URL, empresa, usuario)
- Salida concisa y directa
- Ideal para pruebas puntuales y integración con scripts

**Comandos disponibles**:
```bash
java -jar financial-core-http2-client-1.0.0.jar [COMANDO] [OPCIONES]

# Comandos:
deposito            # Ejecuta solo el escenario de depósito
cancelacion         # Ejecuta solo el escenario de cancelación  
consultar-saldos    # Consulta saldos actuales
health              # Verifica conectividad con el servidor
demo                # Ejecuta demo completo con múltiples escenarios
```

**Opciones configurables**:
```bash
-u, --url URL       # URL del servidor (default: http://localhost:8080)
-g, --grupo GRUPO   # Clave grupo empresa (default: 001)
-e, --empresa EMP   # Clave empresa (default: 001)
-U, --usuario USER  # Clave usuario (default: CLI_USER)
-v, --verbose       # Logging detallado
```

**Ejemplo de uso**:
```bash
java -jar target/financial-core-http2-client-1.0.0.jar deposito -v
java -jar target/financial-core-http2-client-1.0.0.jar health -u http://servidor:8080
```

---

## 2. **📊 Demo Programático: `FinancialCoreClientDemo`**

**Propósito**: Demostración completa y automatizada que ejecuta múltiples escenarios simultáneamente.

**Características**:
- **Ejecución Asíncrona**: Múltiples escenarios corriendo en paralelo usando HTTP/2 multiplexing
- **Reportes Detallados**: Análisis completo del rendimiento y resultados
- **Métricas de Conexión**: Estadísticas de uso de HTTP/2
- **Validación Completa**: Verificación de todos los flujos de estado

**Lo que ejecuta**:
```java
// Escenarios simultáneos:
1. DepositoCompletoScenario    // NP → PV → PR (depósito $5,000)
2. CancelacionMovimientoScenario // NP → PV → CA (retiro $1,500 cancelado)

// Verificaciones:
- Conectividad inicial
- Saldos antes/durante/después
- Impacto en saldos
- Tiempos de ejecución
- Estadísticas HTTP/2
```

**Salida del reporte**:
```
🎯 REPORTE FINAL DEL DEMO - CLIENTE HTTP/2 CORE FINANCIERO
===============================================================================
📈 ESTADÍSTICAS GENERALES:
   Escenarios ejecutados: 2
   Escenarios exitosos: 2 (100%)
   Duración total: 1247 ms
   Promedio por escenario: 623 ms

🌐 RENDIMIENTO HTTP/2:
   Conexiones reutilizadas: 1
   Multiplexing utilizado: ✅ (múltiples requests simultáneos)
   Compresión de headers: ✅ (HPACK)
   Protocolo binario: ✅ (mayor eficiencia)

🔄 FLUJOS DE ESTADO DEMOSTRADOS:
✅ Pre-Movimiento (NP) → Procesado Virtual (PV) → Procesado Real (PR)
❌ Pre-Movimiento (NP) → Procesado Virtual (PV) → Cancelado (CA)
```

---

## 3. **⚡ Script de Ejecución: `run-client.sh`**

**Propósito**: Script bash inteligente que simplifica la ejecución y manejo del cliente.

**Características**:
- **Verificación de Prerequisitos**: Valida Java, versión, y archivos JAR
- **Interfaz Amigable**: Colores, ayuda contextual, y manejo de errores
- **Parámetros Flexibles**: Soporte completo para todas las opciones del CLI
- **Ejecución Simplificada**: Un comando para cualquier operación

**Funciones del script**:

```bash
# Verificaciones automáticas:
✅ Java instalado y versión correcta
✅ Archivo JAR compilado existe
✅ Permisos de ejecución

# Comandos simplificados:
./run-client.sh demo                    # Demo completo
./run-client.sh deposito               # Solo depósito
./run-client.sh cancelacion            # Solo cancelación
./run-client.sh consultar-saldos       # Solo consulta saldos
./run-client.sh health                 # Solo verificar salud

# Con parámetros:
./run-client.sh deposito -u http://servidor:8080 -g 002 -e 003 -v
./run-client.sh health --verbose
```

**Ventajas del script**:
- **Colores y formato**: Salida visual clara con emojis y colores
- **Manejo de errores**: Mensajes informativos si faltan prerequisitos
- **Ayuda integrada**: `./run-client.sh --help`
- **Validaciones**: Previene errores comunes antes de ejecutar

---

## **🎯 Casos de Uso por Interfaz**

| Interfaz | Caso de Uso | Ventaja Principal |
|----------|-------------|-------------------|
| **CLI** | Pruebas puntuales, scripts automatizados | Control granular y salida específica |
| **Demo** | Presentaciones, validación completa | Reporte integral y ejecución paralela |
| **Script** | Uso cotidiano, desarrollo local | Simplicidad y verificación automática |

## **📋 Flujo de Trabajo Recomendado**

1. **Desarrollo/Testing**: Usar `run-client.sh health` para verificar conectividad
2. **Pruebas Específicas**: Usar `run-client.sh deposito` o `run-client.sh cancelacion`
3. **Demostraciones**: Usar `run-client.sh demo` para mostrar capacidades completas
4. **Integración**: Usar CLI directo en pipelines y automatizaciones

## **🔧 Ejemplos Prácticos**

### Verificar que el servidor esté funcionando:
```bash
./run-client.sh health -v
```

### Ejecutar un depósito con parámetros personalizados:
```bash
./run-client.sh deposito -u http://prod-server:8080 -g 002 -e 005 -U PROD_USER -v
```

### Ver demo completo con métricas detalladas:
```bash
./run-client.sh demo -v
```

### Consultar saldos actuales:
```bash
./run-client.sh consultar-saldos
```

## **🚀 Ventajas del Diseño**

- **Flexibilidad**: Tres niveles de interacción según la necesidad
- **Reutilización**: Mismo core, diferentes interfaces
- **Mantenibilidad**: Separación clara de responsabilidades
- **Usabilidad**: Desde scripts simples hasta demos completos
- **Escalabilidad**: Fácil agregar nuevos comandos y opciones

Cada interfaz complementa a las otras, proporcionando flexibilidad total para diferentes necesidades de uso del cliente HTTP/2.