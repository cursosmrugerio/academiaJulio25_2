# Guía Funcional - Core Financiero

## 🏦 ¿Qué es Core Financiero?

**Core Financiero** es el corazón digital de las operaciones de tesorería de una institución financiera. Imagínelo como el cerebro que controla todos los movimientos de dinero, desde un simple depósito hasta complejas operaciones de inversión.

En términos simples, es un sistema que **registra, valida, procesa y controla** cada peso que entra y sale de la institución, asegurando que todo esté debidamente documentado, autorizado y cumpliendo con las regulaciones financieras.

### **Migración Exitosa de PL/SQL a Java**
Este sistema representa una **modernización tecnológica completa**, migrando desde procedimientos almacenados en PL/SQL hacia una arquitectura moderna basada en **Spring Boot**, manteniendo toda la lógica de negocio crítica mientras se mejora la mantenibilidad, escalabilidad y capacidad de integración.

---

## 💡 ¿Por Qué es Fundamental para las Instituciones Financieras?

### **El Problema que Resuelve**

Imagine una institución financiera sin un sistema como Core Financiero:

- **Caos en los registros**: Los movimientos de dinero se registrarían manualmente o en sistemas separados
- **Errores humanos**: Sin validaciones automáticas, los errores serían frecuentes y costosos
- **Falta de control**: No habría forma de saber en tiempo real cuánto dinero hay disponible
- **Riesgo regulatorio**: Incumplimiento de normativas por falta de trazabilidad
- **Pérdidas financieras**: Operaciones duplicadas, saldos incorrectos, fraudes no detectados

### **La Solución que Proporciona**

Core Financiero actúa como un **sistema nervioso central** que:

1. **Captura** cada operación financiera
2. **Valida** que cumple con las reglas de negocio
3. **Procesa** la operación de forma segura
4. **Actualiza** automáticamente los saldos
5. **Registra** todo para auditoría y cumplimiento

---

## 🎯 Funciones Principales y su Importancia

### 1. **Gestión de Pre-Movimientos** 📝

#### ¿Qué es?
Un "pre-movimiento" es como un **borrador** de una operación financiera. Es la intención de realizar una transacción que aún no ha sido ejecutada definitivamente.

#### ¿Por qué es importante?
- **Control previo**: Permite revisar y validar operaciones antes de que afecten los saldos reales
- **Prevención de errores**: Los errores se pueden corregir antes de causar impacto financiero
- **Flujo de aprobación**: Las operaciones grandes pueden requerir múltiples aprobaciones
- **Planificación**: Permite programar operaciones para fechas futuras

#### Ejemplo Práctico
Un cliente solicita un préstamo de $100,000:
1. **Pre-movimiento**: Se registra la intención de otorgar el préstamo con `POST /api/v1/movimientos/pre-movimiento`
2. **Validación**: El sistema verifica automáticamente fechas de liquidación, saldos disponibles y reglas de negocio
3. **Detalles**: Se agregan conceptos como intereses y comisiones con `POST /api/v1/movimientos/pre-movimiento-detalle`
4. **Procesamiento**: Se convierte en movimiento real usando `POST /api/v1/movimientos/procesar-pre-movimientos`
5. **Finalización**: Se confirma con `POST /api/v1/movimientos/procesar-virtuales-a-reales`

#### Funcionalidades Avanzadas de Pre-Movimientos
- **Consulta de pendientes**: `GET /api/v1/movimientos/pendientes` permite ver todos los pre-movimientos por fecha de liquidación
- **Cálculo automático**: `GET /api/v1/movimientos/total-conceptos/{id}` suma automáticamente todos los conceptos
- **Procesamiento masivo**: `POST /api/v1/movimientos/procesar-pendientes` procesa todos los movimientos pendientes de una empresa

---

### 2. **Procesamiento de Movimientos** ⚡

#### Estados del Movimiento

**🔵 No Procesado (NP)**
- La operación está registrada pero no ha sido validada
- **Importancia**: Permite detectar y corregir errores antes del procesamiento

**🟡 Procesado Virtual (PV)**
- La operación pasó todas las validaciones pero no afecta saldos reales
- **Importancia**: Estado de seguridad que permite revisiones finales

**🟢 Procesado Real (PR)**
- La operación está completamente ejecutada y afecta saldos
- **Importancia**: Estado final que confirma la ejecución exitosa

**🔴 Cancelado (CA)**
- La operación fue cancelada y cualquier efecto fue revertido
- **Importancia**: Permite deshacer operaciones erróneas sin impacto permanente

#### ¿Por qué estos estados son críticos?

**Para la Institución:**
- **Seguridad**: Múltiples puntos de control antes de afectar dinero real
- **Auditoría**: Trazabilidad completa de cada cambio de estado
- **Cumplimiento**: Satisface requerimientos regulatorios de control interno

**Para los Clientes:**
- **Confianza**: Sus operaciones están protegidas por múltiples validaciones
- **Transparencia**: Pueden conocer el estado exacto de sus transacciones a través de APIs REST
- **Corrección**: Errores pueden ser corregidos sin afectar sus cuentas mediante `POST /{id}/cancelar`

#### Capacidades Operativas del Sistema
- **Consulta de movimientos**: `GET /api/v1/movimientos/{empresa}` con filtros por situación y fecha
- **Seguimiento individual**: `GET /api/v1/movimientos/{empresa}/{id}` para movimientos específicos
- **Cancelación controlada**: Reversión automática de saldos al cancelar operaciones
- **Consulta de catálogo**: `GET /api/v1/movimientos/catalogo-operaciones` para operaciones disponibles
- **Monitoreo de pendientes**: `GET /api/v1/movimientos/pendientes-procesamiento` para supervisión operativa

---

### 3. **Gestión de Fechas y Liquidación** 📅

#### ¿Qué son las Fechas de Liquidación?

En el mundo financiero, las operaciones no siempre se **ejecutan** el mismo día que se **liquidan** (cuando realmente se transfiere el dinero).

#### Tipos de Liquidación:

**T+0**: Liquidación el mismo día
- **Uso**: Operaciones urgentes, transferencias interbancarias
- **Importancia**: Para operaciones que requieren disponibilidad inmediata

**T+1, T+2, T+3**: Liquidación en 1, 2 o 3 días hábiles
- **Uso**: Operaciones estándar, inversiones, pagos programados
- **Importancia**: Permite optimizar flujos de efectivo y cumplir con estándares del mercado

#### ¿Por qué es fundamental?

**Gestión de Liquidez:**
- Permite saber exactamente cuánto dinero estará disponible cada día
- Facilita la planificación de inversiones y préstamos
- Evita problemas de liquidez que podrían causar incumplimientos

**Cumplimiento Normativo:**
- Las regulaciones financieras exigen tiempos específicos de liquidación
- Diferentes tipos de operación tienen diferentes requerimientos
- El incumplimiento puede resultar en multas severas

**Eficiencia Operativa:**
- Optimiza el uso del capital disponible
- Reduce costos financieros por manejo inadecuado de liquidez
- Mejora la rentabilidad de la institución

#### Funcionalidades Automatizadas de Fechas
- **Creación masiva**: `POST /api/v1/liquidacion/crear-fechas-anio` genera todas las fechas hábiles del año
- **Validación inteligente**: `GET /api/v1/liquidacion/validar-fecha` verifica fechas de liquidación según tipo de operación
- **Gestión del sistema**: `POST /api/v1/fechas/recorrer` avanza automáticamente al siguiente día hábil
- **Consulta de fechas**: `GET /api/v1/fechas/sistema` obtiene la fecha operativa actual
- **Verificación de días hábiles**: `GET /api/v1/fechas/validar-dia-habil` confirma si una fecha es operativa
- **Actualización manual**: `PUT /api/v1/fechas/sistema` permite ajustes controlados de fecha sistema

---

### 4. **Control de Saldos** 💰

#### ¿Cómo Funciona?

Cada operación financiera puede:
- **Incrementar saldos** (depósitos, pagos recibidos, intereses ganados)
- **Decrementar saldos** (retiros, pagos realizados, comisiones)
- **No afectar saldos** (consultas, cambios de información)

#### ¿Por qué es Crítico?

**Precisión Financiera:**
- Cada centavo debe estar correctamente contabilizado
- Los errores en saldos pueden causar pérdidas millonarias
- La confianza del cliente depende de saldos exactos

**Gestión de Riesgos:**
- Previene sobregiros no autorizados
- Identifica operaciones sospechosas o inusuales
- Mantiene límites de exposición por cliente o producto

**Regulatorio:**
- Los reguladores exigen conciliaciones diarias exactas
- Los saldos incorrectos pueden indicar fraude o mala gestión
- Requerido para reportes de capital y liquidez

#### Capacidades de Consulta de Saldos
- **Saldos por empresa**: `GET /api/v1/movimientos/saldos/{empresa}` obtiene posiciones actuales
- **Saldos históricos**: Filtro por `fechaFoto` para consultar saldos en fechas específicas
- **Saldos por cuenta**: Filtro por `idCuenta` para consultas detalladas
- **Actualización automática**: Los saldos se actualizan instantáneamente con cada movimiento procesado
- **Multidivisa**: Soporte para múltiples divisas con control independiente por moneda

---

## 🚀 Arquitectura Moderna y APIs REST

### **Transformación Tecnológica**

El sistema ha evolucionado de una arquitectura basada en procedimientos almacenados PL/SQL hacia una **arquitectura moderna de microservicios** con las siguientes características:

#### **Stack Tecnológico Actual**
- **Framework**: Spring Boot 3.5.4 con Java 21
- **APIs**: REST completas con documentación OpenAPI/Swagger
- **Base de Datos**: Soporte para H2 (desarrollo) y bases relacionales empresariales
- **Arquitectura**: Clean Architecture con separación clara de capas
- **Testing**: Cobertura de pruebas del 75% con JaCoCo
- **Validaciones**: Bean Validation con anotaciones declarativas

#### **Beneficios de la Modernización**
- **Integración**: APIs REST facilitan integración con sistemas externos
- **Mantenimiento**: Código Java más legible y mantenible que PL/SQL
- **Escalabilidad**: Arquitectura preparada para crecimiento horizontal
- **Testing**: Pruebas unitarias e integración automatizadas
- **Documentación**: APIs autodocumentadas con Swagger UI
- **DevOps**: Preparado para pipelines de CI/CD modernos

#### **Endpoints Organizados por Funcionalidad**

**🔄 Procesamiento de Movimientos**
- `POST /api/v1/movimientos/procesar-pre-movimientos`
- `POST /api/v1/movimientos/procesar-virtuales-a-reales`
- `POST /api/v1/movimientos/{id}/cancelar`

**📊 Consultas y Reportes**
- `GET /api/v1/movimientos/{empresa}` - Movimientos por empresa
- `GET /api/v1/movimientos/saldos/{empresa}` - Consulta de saldos
- `GET /api/v1/movimientos/catalogo-operaciones/{empresa}` - Operaciones disponibles

**📅 Gestión de Fechas**
- `POST /api/v1/fechas/recorrer` - Avance de fecha sistema
- `GET /api/v1/fechas/validar-dia-habil` - Validación de días hábiles
- `POST /api/v1/liquidacion/crear-fechas-anio` - Generación masiva de fechas

---

## 🏗️ Arquitectura del Flujo Operativo

### **Flujo Típico de una Operación**

```
1. CLIENTE SOLICITA OPERACIÓN
   ↓
2. REGISTRO DE PRE-MOVIMIENTO
   📡 POST /api/v1/movimientos/pre-movimiento
   ↓ (Validaciones automáticas: fechas, saldos, reglas)
3. AGREGAR CONCEPTOS DETALLADOS
   📡 POST /api/v1/movimientos/pre-movimiento-detalle
   ↓ (Intereses, comisiones, etc.)
4. VERIFICACIÓN DE REGLAS DE NEGOCIO
   📡 GET /api/v1/liquidacion/validar-fecha
   📡 GET /api/v1/fechas/validar-dia-habil
   ↓ (¿Fecha válida? ¿Saldo suficiente? ¿Cliente autorizado?)
5. PROCESAMIENTO VIRTUAL
   📡 POST /api/v1/movimientos/procesar-pre-movimientos
   ↓ (Estado PV - Listo para ejecución)
6. APROBACIÓN FINAL Y PROCESAMIENTO REAL
   📡 POST /api/v1/movimientos/procesar-virtuales-a-reales
   ↓ (Estado PR - Saldo afectado automáticamente)
7. CONFIRMACIÓN Y SEGUIMIENTO
   📡 GET /api/v1/movimientos/{empresa}/{id}
   📡 GET /api/v1/movimientos/saldos/{empresa}
```

### **Validaciones Automáticas que Protegen la Institución**

**Validación de Fechas:**
- ¿Es día hábil?
- ¿La fecha de liquidación es válida para este tipo de operación?
- ¿No es una fecha pasada incorrecta?

**Validación de Fondos:**
- ¿Hay saldo suficiente para la operación?
- ¿Se respetan los límites establecidos?
- ¿La operación no excede los límites regulatorios?

**Validación de Integridad:**
- ¿Los datos son consistentes?
- ¿La operación está completa?
- ¿Todos los campos requeridos están presentes?

---

## 🧪 Calidad y Confiabilidad del Sistema

### **Cobertura de Pruebas del 75%**

El sistema mantiene una **cobertura de pruebas del 75%** garantizada por JaCoCo, lo que significa:

#### **Tipos de Pruebas Implementadas**
- **Pruebas Unitarias**: Verifican la lógica individual de cada componente
- **Pruebas de Integración**: Validan la interacción entre servicios y base de datos
- **Pruebas de Controladores**: Confirman el funcionamiento correcto de las APIs REST
- **Pruebas de Repositorio**: Aseguran la correcta persistencia de datos

#### **Beneficios de la Alta Cobertura**
- **Confianza**: Cada cambio está respaldado por pruebas automatizadas
- **Calidad**: Detección temprana de errores antes de producción
- **Mantenimiento**: Facilita refactorizaciones seguras del código
- **Documentación viva**: Las pruebas sirven como documentación del comportamiento esperado

#### **Validaciones Automáticas en Código**
- **Bean Validation**: Validaciones declarativas en DTOs y entidades
- **Transacciones**: Manejo automático de rollback en caso de errores
- **Logging**: Trazabilidad completa de operaciones para auditoría
- **Manejo de errores**: Respuestas HTTP estructuradas para diferentes tipos de error

### **Migración Exitosa desde PL/SQL**

La migración ha preservado **100% de la funcionalidad crítica** mientras mejora:
- **Mantenibilidad**: Código Java más legible que procedimientos PL/SQL
- **Testing**: Imposible hacer pruebas unitarias efectivas en PL/SQL
- **Integración**: APIs REST vs. llamadas directas a base de datos
- **Escalabilidad**: Arquitectura de capas vs. lógica en base de datos
- **DevOps**: Despliegues automatizados vs. scripts manuales de BD

---

## 💼 Impacto en Diferentes Áreas de la Institución

### **Para la Dirección General**
- **Visibilidad**: Conocimiento exacto de la posición financiera en tiempo real
- **Control**: Supervisión efectiva de todas las operaciones
- **Estrategia**: Datos precisos para toma de decisiones estratégicas

### **Para Tesorería**
- **Liquidez**: Gestión óptima de flujos de efectivo
- **Inversiones**: Información precisa para decisiones de inversión
- **Riesgos**: Identificación temprana de exposiciones

### **Para Operaciones**
- **Eficiencia**: Procesamiento automatizado reduce errores y tiempos
- **Capacidad**: Manejo de mayor volumen de operaciones
- **Calidad**: Consistencia en el procesamiento

### **Para Auditoría y Cumplimiento**
- **Trazabilidad**: Registro completo de todas las operaciones
- **Controles**: Validaciones automáticas que previenen errores
- **Reportes**: Información estructurada para reguladores

### **Para Servicio al Cliente**
- **Confiabilidad**: Operaciones procesadas correctamente
- **Transparencia**: Estado claro de todas las transacciones
- **Velocidad**: Procesamiento eficiente de solicitudes

---

## 🚨 Riesgos que Mitiga

### **Riesgo Operativo**
- **Errores humanos**: Automatización reduce errores manuales
- **Fraude interno**: Controles automáticos detectan anomalías
- **Pérdida de datos**: Registro sistemático previene pérdidas de información

### **Riesgo de Liquidez**
- **Descalces**: Previene situaciones donde no hay efectivo suficiente
- **Planificación**: Proyecciones precisas de flujos futuros
- **Optimización**: Uso eficiente del capital disponible

### **Riesgo de Cumplimiento**
- **Regulatorio**: Cumple automáticamente con normativas
- **Auditoría**: Facilita inspecciones y auditorías
- **Reportes**: Genera información requerida por autoridades

### **Riesgo Reputacional**
- **Confianza del cliente**: Operaciones precisas y oportunas
- **Transparencia**: Información clara y accesible
- **Profesionalismo**: Imagen de institución bien gestionada

---

## 📈 Beneficios Medibles

### **Eficiencia Operativa**
- **Reducción de tiempo**: Procesamiento automático vs. manual
- **Mayor volumen**: Capacidad de procesar más operaciones
- **Menos errores**: Validaciones automáticas reducen errores humanos

### **Ahorro de Costos**
- **Personal**: Menos necesidad de procesamiento manual
- **Errores**: Reducción de costos por corrección de errores
- **Multas**: Evita sanciones por incumplimiento normativo

### **Mejora en Ingresos**
- **Optimización**: Mejor uso del capital disponible
- **Velocidad**: Procesamiento más rápido permite más operaciones
- **Confianza**: Clientes satisfechos generan más negocio

---

## 🔮 Valor a Futuro

### **Escalabilidad**
- Preparado para crecimiento del negocio
- Capacidad de integrar nuevos productos financieros
- Adaptable a cambios regulatorios

### **Innovación**
- Base sólida para nuevas funcionalidades
- Integración con tecnologías emergentes (APIs, blockchain, IA)
- Facilitador de transformación digital

### **Competitividad**
- Operaciones más eficientes que la competencia
- Mejor servicio al cliente
- Capacidad de respuesta más rápida al mercado

---

## 🎯 Conclusión

Core Financiero no es simplemente un sistema de software; es la **columna vertebral digital modernizada** que permite a una institución financiera operar de manera segura, eficiente y competitiva en el mercado actual.

### **Logros de la Modernización**

La exitosa migración de PL/SQL a Java Spring Boot ha resultado en:

1. **Protege** el dinero con arquitectura moderna y 75% de cobertura de pruebas
2. **Asegura** el cumplimiento con APIs trazables y validaciones automáticas
3. **Optimiza** recursos con procesamiento eficiente y consultas inteligentes
4. **Facilita** integración con APIs REST autodocumentadas
5. **Genera** confianza con arquitectura empresarial probada

### **Ventajas Competitivas Actuales**

**Vs. Sistemas Legacy:**
- ✅ **APIs REST** vs. procedimientos de base de datos
- ✅ **75% de cobertura** vs. pruebas manuales
- ✅ **Documentación automática** vs. documentación desactualizada
- ✅ **Arquitectura en capas** vs. lógica monolítica en BD
- ✅ **DevOps ready** vs. despliegues manuales

**Vs. Competencia:**
- 🚀 **Procesamiento transaccional** en tiempo real
- 🔒 **Múltiples validaciones** antes de afectar saldos
- 📊 **APIs completas** para integración con cualquier frontend
- 🧪 **Calidad garantizada** por pruebas automatizadas
- 📈 **Escalabilidad** probada en arquitectura moderna

Sin un sistema como Core Financiero modernizado, una institución financiera no solo no puede competir, sino que enfrenta riesgos operativos, regulatorios y tecnológicos que pueden comprometer su viabilidad en el mercado digital actual.

### **El Futuro es Ahora**

Este sistema representa la **transformación digital exitosa** de procesos financieros críticos, estableciendo las bases para:
- Integración con sistemas de última generación
- Adopción de nuevas tecnologías (APIs, microservicios, cloud)
- Cumplimiento proactivo de regulaciones cambiantes
- Operación eficiente en mercados digitales competitivos

---

*"En el mundo financiero, la precisión no es una opción, es una obligación. Core Financiero modernizado convierte esa obligación en una fortaleza competitiva del siglo XXI."*