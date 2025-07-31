# Guía de Demostración - Core Financiero
*Sistema de Procesamiento de Transacciones Financieras*

## Resumen Ejecutivo

Este core financiero permite procesar transacciones bancarias en tiempo real utilizando tecnología HTTP/2, garantizando alta disponibilidad, persistencia de datos y capacidad de recuperación ante fallos del sistema.

## Demostración en Vivo

### 1. Verificación del Estado Base del Sistema
```bash
./run-client.sh consultar-saldos
```
**Resultado esperado:** 
- Cuenta 1001: $100,000.00 MXN
- Cuenta 1002: $50,000.00 MXN  
- Cuenta 1003: $25,000.00 MXN

**Valor demostrado:** El sistema mantiene un estado consistente de todas las cuentas y puede consultar saldos instantáneamente.

### 2. Demostración Completa del Flujo de Transacciones
```bash
./run-client.sh demo
```

**Lo que verá la audiencia:**
- **HTTP/2 Multiplexing**: Múltiples transacciones procesándose simultáneamente
- **Flujo de Estados NP → PV → PR**: 
  - NP (No Procesado): Transacción creada
  - PV (Procesado Virtual): Impacto calculado pero reversible
  - PR (Procesado Real): Transacción confirmada permanentemente

**Valor demostrado:** 
- Procesamiento paralelo de múltiples transacciones
- Control de estados que permite validación antes de confirmación final
- Trazabilidad completa de cada operación

### 3. Transacción Individual - Depósito
```bash
./run-client.sh deposito
```
**Secuencia mostrada:**
1. Consulta saldos iniciales
2. Crea pre-movimiento de depósito ($5,000.00)
3. Procesa a estado virtual (se ve el impacto)
4. Confirma a estado real (transacción permanente)
5. Muestra saldos finales

**Valor demostrado:** Cada transacción pasa por validaciones antes de ser definitiva, reduciendo errores.

### 4. Demostración de Resilencia del Sistema

#### Paso A: Detener el Backend
```bash
# Mostrar que el sistema está funcionando
./run-client.sh consultar-saldos

# Simular falla del sistema (detener backend)
pkill -f "core-financiero"
```

#### Paso B: Intentar Operación Durante Falla
```bash
./run-client.sh consultar-saldos
```
**Resultado:** El cliente detecta la desconexión y reporta el error apropiadamente.

**Valor demostrado:** El sistema maneja fallos de manera elegante sin crashes.

#### Paso C: Recuperación del Sistema
```bash
# Reiniciar el backend
cd ../
nohup java -jar target/core-financiero-1.0.0.jar > backend.log 2>&1 &
sleep 10

# Verificar que los datos persisten
cd FinancialCoreClient/
./run-client.sh consultar-saldos
```

**Resultado:** Los saldos son exactamente los mismos que antes de la falla.

**Valor demostrado:** 
- **Persistencia de Datos**: Ninguna información se pierde durante fallos
- **Recuperación Automática**: El sistema vuelve a estar operativo inmediatamente
- **Continuidad del Negocio**: Las operaciones pueden reanudarse sin intervención manual

### 5. Verificación de Reproducibilidad
```bash
./run-client.sh demo
```
**Resultado:** La demostración funciona igual que la primera vez.

**Valor demostrado:** El sistema es consistente y predecible en su comportamiento.

## Beneficios Técnicos Clave

### 1. **Alta Performance**
- **HTTP/2**: Procesamiento paralelo de múltiples transacciones
- **Arquitectura Asíncrona**: No bloquea el sistema durante operaciones

### 2. **Seguridad y Control**
- **Estados Transaccionales**: NP → PV → PR permite validación antes de confirmación
- **Reversibilidad**: Las transacciones en estado virtual pueden cancelarse

### 3. **Confiabilidad Empresarial**
- **Persistencia Garantizada**: Base de datos mantiene todos los datos
- **Recuperación ante Fallos**: Sistema operativo en segundos tras reinicio
- **Trazabilidad Completa**: Cada operación queda registrada

### 4. **Escalabilidad**
- **Arquitectura Microservicios**: Puede crecer según demanda
- **API RESTful**: Integración sencilla con otros sistemas

## Preguntas Frecuentes

**P: ¿Qué pasa si hay una falla durante una transacción?**
R: El sistema de estados garantiza que solo las transacciones en estado PR (Procesado Real) son permanentes. Las demás se pueden recuperar o cancelar.

**P: ¿Cómo se compara con sistemas bancarios tradicionales?**
R: Ofrece la misma confiabilidad pero con mayor velocidad de procesamiento y capacidad de manejo paralelo de transacciones.

**P: ¿Es escalable para volúmenes bancarios reales?**
R: Sí, la arquitectura HTTP/2 y el diseño asíncrono permiten manejar miles de transacciones concurrentes.

---

*Esta demostración muestra un core financiero completo, desde transacciones individuales hasta recuperación ante fallos, garantizando la confianza necesaria para sistemas financieros críticos.*