# API Documentation - Core Financiero Backend

## 📋 Índice
- [Descripción General](#descripción-general)
- [Configuración Base](#configuración-base)
- [Endpoints por Módulo](#endpoints-por-módulo)
  - [Procesador de Movimientos](#procesador-de-movimientos)
  - [Gestión de Movimientos](#gestión-de-movimientos)
  - [Liquidación](#liquidación)
  - [Fechas del Sistema](#fechas-del-sistema)
- [Estructuras de Datos](#estructuras-de-datos)
- [Manejo de Errores](#manejo-de-errores)
- [Validaciones](#validaciones)

---

## 📖 Descripción General

**Core Financiero** es una aplicación Spring Boot que gestiona operaciones de tesorería financiera. La aplicación ha sido migrada desde PL/SQL a Java y proporciona APIs REST completas para el procesamiento de movimientos, liquidación y gestión de fechas.

### Características Principales
- **Procesamiento de Movimientos Financieros**: Gestión completa del ciclo de vida de movimientos
- **Validación de Fechas de Liquidación**: Validación de días hábiles y fechas de operación
- **Gestión de Saldos**: Afectación automática de saldos según tipo de operación
- **Estados de Movimiento**: Control de flujo de estados (NP → PV → PR/CA)
- **Auditoría Completa**: Registro de usuario, fechas y direcciones IP

---

## ⚙️ Configuración Base

| Configuración | Valor |
|---------------|-------|
| **URL Base** | `/api/v1` |
| **Puerto** | `8080` (por defecto) |
| **Spring Boot** | `3.5.4` |
| **Java** | `21` |
| **Base de Datos** | H2 (desarrollo) |
| **Documentación** | Swagger/OpenAPI 2.7.0 |

### URLs de Documentación
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`

---

## 🔌 Endpoints por Módulo

### 💰 Procesador de Movimientos
**Base Path**: `/api/v1/movimientos`

#### 1. **Procesar Pre-Movimientos**
```http
POST /api/v1/movimientos/procesar-pre-movimientos
```

**📌 Propósito**: Procesa todos los pre-movimientos pendientes de una empresa para una fecha específica, convirtiéndolos en movimientos activos que afectan saldos.

**📥 Parámetros de Entrada**:
```json
{
  "claveGrupoEmpresa": "string (requerido, max 10)",
  "claveEmpresa": "string (requerido, max 10)", 
  "fechaProceso": "date (requerido, formato ISO)",
  "claveUsuario": "string (opcional, max 20)"
}
```

**📤 Respuesta Esperada**:
```json
{
  "status": "success",
  "message": "Pre-movimientos procesados exitosamente",
  "fecha_proceso": "2025-01-31",
  "empresa": "001-001"
}
```

---

#### 2. **Procesar Movimientos Virtuales a Reales**
```http
POST /api/v1/movimientos/procesar-virtuales-a-reales
```

**📌 Propósito**: Convierte movimientos en estado "Procesado Virtual" (PV) a "Procesado Real" (PR), finalizando el procesamiento y confirmando la afectación de saldos.

**📥 Parámetros de Entrada**: *Misma estructura que el endpoint anterior*

**📤 Respuesta Esperada**:
```json
{
  "status": "success",
  "message": "Movimientos virtuales procesados a reales exitosamente",
  "fecha_proceso": "2025-01-31",
  "empresa": "001-001"
}
```

---

#### 3. **Cancelar Movimiento**
```http
POST /api/v1/movimientos/{claveGrupoEmpresa}/{claveEmpresa}/{idMovimiento}/cancelar
```

**📌 Propósito**: Cancela un movimiento específico, revirtiendo cualquier afectación de saldo y marcándolo como cancelado (CA).

**📥 Parámetros**:
- **Path Variables**:
  - `claveGrupoEmpresa` (string): Clave del grupo empresarial
  - `claveEmpresa` (string): Clave de la empresa
  - `idMovimiento` (long): ID único del movimiento
- **Query Parameters**:
  - `claveUsuario` (string, requerido): Usuario que realiza la cancelación

**📤 Respuesta Esperada**:
```json
{
  "status": "success",
  "message": "Movimiento cancelado exitosamente",
  "id_movimiento": 12345,
  "usuario_cancela": "USR001"
}
```

---

#### 4. **Consultar Movimientos por Empresa**
```http
GET /api/v1/movimientos/{claveGrupoEmpresa}/{claveEmpresa}
```

**📌 Propósito**: Obtiene una lista de movimientos filtrados por empresa y opcionalmente por situación o fecha de operación.

**📥 Parámetros**:
- **Path Variables**: Identificadores de empresa
- **Query Parameters (opcionales)**:
  - `situacion` (string): Estado del movimiento (NP, PV, PR, CA)
  - `fechaOperacion` (date): Fecha de operación en formato ISO

**📤 Respuesta Esperada**:
```json
[
  {
    "id": {
      "claveGrupoEmpresa": "001",
      "claveEmpresa": "001", 
      "idMovimiento": 12345
    },
    "idCuenta": 1001,
    "claveDivisa": "MXN",
    "fechaOperacion": "2025-01-31",
    "fechaLiquidacion": "2025-02-03",
    "claveOperacion": "DEPOSITO",
    "importeNeto": 10000.00,
    "situacionMovimiento": "PV",
    "claveUsuario": "USR001"
  }
]
```

---

#### 5. **Consultar Movimiento Específico**
```http
GET /api/v1/movimientos/{claveGrupoEmpresa}/{claveEmpresa}/{idMovimiento}
```

**📌 Propósito**: Obtiene los detalles completos de un movimiento específico.

**📥 Parámetros**: Path variables con identificadores únicos

**📤 Respuesta Esperada**: Objeto MovimientoDTO completo o HTTP 404 si no existe

---

#### 6. **Consultar Saldos**
```http
GET /api/v1/movimientos/saldos/{claveGrupoEmpresa}/{claveEmpresa}
```

**📌 Propósito**: Obtiene los saldos de cuentas por empresa, con filtros opcionales por fecha o cuenta específica.

**📥 Parámetros**:
- **Query Parameters (opcionales)**:
  - `fechaFoto` (date): Fecha específica para el saldo
  - `idCuenta` (long): ID de cuenta específica

**📤 Respuesta Esperada**:
```json
[
  {
    "id": {
      "claveGrupoEmpresa": "001",
      "claveEmpresa": "001",
      "fechaFoto": "2025-01-31",
      "idCuenta": 1001,
      "claveDivisa": "MXN"
    },
    "saldoEfectivo": 50000.00
  }
]
```

---

### 📊 Gestión de Movimientos
**Base Path**: `/api/v1/movimientos`

#### 1. **Generar Pre-Movimiento**
```http
POST /api/v1/movimientos/pre-movimiento
```

**📌 Propósito**: Crea un nuevo pre-movimiento (estado inicial antes del procesamiento). Equivale al procedimiento `pGeneraPreMovto` del PL/SQL original.

**📥 Parámetros de Entrada**:
```json
{
  "claveGrupoEmpresa": "string (requerido, max 10)",
  "claveEmpresa": "string (requerido, max 10)",
  "idPreMovimiento": "long (requerido)",
  "fechaLiquidacion": "date (requerido, fecha futura)",
  "idCuenta": "long (opcional)",
  "idPrestamo": "long (opcional)",
  "claveDivisa": "string (max 5)",
  "claveOperacion": "string (requerido, max 10)",
  "importeNeto": "decimal (requerido, min 0.01, max 13 dígitos + 2 decimales)",
  "claveMedio": "string (requerido, max 20)",
  "claveMercado": "string (requerido, max 20)",
  "nota": "string (opcional, max 500)",
  "idGrupo": "long (opcional)",
  "claveUsuario": "string (requerido, max 20)",
  "fechaValor": "date (opcional)",
  "numeroPagoAmortizacion": "integer (opcional)"
}
```

**📤 Respuesta Esperada**:
```json
{
  "status": 201,
  "message": "Pre-movimiento generado exitosamente",
  "idPreMovimiento": 12345
}
```

---

#### 2. **Generar Detalle de Pre-Movimiento**
```http
POST /api/v1/movimientos/pre-movimiento-detalle
```

**📌 Propósito**: Agrega conceptos detallados a un pre-movimiento existente (intereses, comisiones, etc.). Equivale al procedimiento `pGeneraPreMovtoDet` del PL/SQL.

**📥 Parámetros de Entrada**:
```json
{
  "claveGrupoEmpresa": "string (requerido)",
  "claveEmpresa": "string (requerido)",
  "idPreMovimiento": "long (requerido)",
  "claveConcepto": "string (requerido, max 10)",
  "importeConcepto": "decimal (requerido, min 0.01)",
  "nota": "string (opcional, max 500)"
}
```

---

### 📅 Liquidación
**Base Path**: `/api/v1/liquidacion`

#### 1. **Crear Fechas de Liquidación del Año**
```http
POST /api/v1/liquidacion/crear-fechas-anio
```

**📌 Propósito**: Genera todas las fechas de liquidación para los días hábiles de un año específico. Equivale a la función `CreaFechaLiquiacionAnio` del PL/SQL.

**📥 Parámetros**:
```http
?claveGrupoEmpresa=001&claveEmpresa=001&anio=2025
```
- `anio`: Año entre 2020-2050

**📤 Respuesta Esperada**:
```json
{
  "status": "success",
  "message": "El proceso ha terminado",
  "anio": 2025,
  "fechas_creadas": 252
}
```

---

#### 2. **Validar Fecha de Liquidación**
```http
GET /api/v1/liquidacion/validar-fecha
```

**📌 Propósito**: Valida si una fecha de liquidación es correcta para una operación específica según las reglas de negocio.

**📥 Parámetros**:
```http
?claveGrupoEmpresa=001&claveEmpresa=001&fechaOperacion=2025-01-31&fechaLiquidacion=2025-02-03&claveMercado=DEPOSITO
```

**📤 Respuesta Esperada**:
```json
{
  "valida": true,
  "tipoLiquidacion": "T+1",
  "esDiaHabil": true
}
```

---

### 🗓️ Fechas del Sistema
**Base Path**: `/api/v1/fechas`

#### 1. **Recorrer Fecha del Sistema**
```http
POST /api/v1/fechas/recorrer
```

**📌 Propósito**: Actualiza la fecha del sistema al siguiente día hábil. Equivale a la función `RecorreFecha` del PL/SQL.

**📥 Parámetros**:
```http
?claveGrupoEmpresa=001&claveEmpresa=001
```

**📤 Respuesta Esperada**:
```json
{
  "status": "success",
  "message": "El proceso ha terminado",
  "fechaAnterior": "2025-01-31",
  "fechaNueva": "2025-02-03"
}
```

---

#### 2. **Obtener Fecha del Sistema**
```http
GET /api/v1/fechas/sistema
```

**📌 Propósito**: Obtiene la fecha actual del sistema para una empresa. Equivale a la función `dameFechaSistema` del PL/SQL.

**📥 Parámetros**:
```http
?claveGrupoEmpresa=001&claveEmpresa=001
```

**📤 Respuesta Esperada**:
```json
{
  "fechaSistema": "2025-01-31",
  "esDiaHabil": true
}
```

---

#### 3. **Actualizar Fecha del Sistema**
```http
PUT /api/v1/fechas/sistema
```

**📌 Propósito**: Actualiza manualmente la fecha del sistema para una empresa específica.

**📥 Parámetros**:
```http
?claveGrupoEmpresa=001&claveEmpresa=001&nuevaFecha=2025-02-01
```

---

#### 4. **Validar Día Hábil**
```http
GET /api/v1/fechas/validar-dia-habil
```

**📌 Propósito**: Verifica si una fecha específica es día hábil (no fin de semana ni festivo).

**📥 Parámetros**:
```http
?fecha=2025-01-31
```

**📤 Respuesta Esperada**:
```json
{
  "fecha": "2025-01-31",
  "esDiaHabil": true,
  "siguienteDiaHabil": "2025-02-03",
  "diaSemana": "viernes"
}
```

---

## 📋 Estructuras de Datos

### Estados de Movimiento
| Código | Descripción | Transiciones Permitidas |
|--------|-------------|------------------------|
| **NP** | No Procesado | → PV, PR |
| **PV** | Procesado Virtual | → PR, CA |
| **PR** | Procesado Real | ❌ (Final) |
| **CA** | Cancelado | ❌ (Final) |

### Tipos de Liquidación
- **T+0**: Liquidación el mismo día
- **T+1**: Liquidación al día siguiente
- **T+2 a T+5**: Liquidación en 2 a 5 días hábiles
- **AYER**: Referencia al día anterior
- **FM-1, FM00, FM01**: Fechas de fin de mes

---

## ⚠️ Manejo de Errores

### Estructura de Respuesta de Error
```json
{
  "timestamp": "2025-01-31T12:00:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Descripción detallada del error",
  "path": "/api/v1/movimientos/procesar",
  "errorCode": "BUSINESS_ERROR",
  "fieldErrors": {
    "claveGrupoEmpresa": "No puede estar vacío",
    "importeNeto": "Debe ser mayor a 0.01"
  }
}
```

### Códigos de Estado HTTP
| Código | Descripción | Uso |
|--------|-------------|-----|
| **200** | OK | Consultas exitosas |
| **201** | Created | Creación exitosa |
| **400** | Bad Request | Error de validación o negocio |
| **404** | Not Found | Recurso no encontrado |
| **500** | Internal Server Error | Error interno del servidor |

---

## ✅ Validaciones

### Validaciones de Campos
| Tipo | Anotación | Descripción |
|------|-----------|-------------|
| **Requerido** | `@NotBlank`, `@NotNull` | Campo obligatorio |
| **Longitud** | `@Size(max=10)` | Longitud máxima de cadena |
| **Numérico** | `@DecimalMin("0.01")` | Valor mínimo |
| **Precisión** | `@Digits(integer=13, fraction=2)` | Dígitos enteros y decimales |
| **Fecha** | `@Future` | Fecha futura requerida |
| **Patrón** | `@Pattern(regexp="...")` | Formato específico |

### Reglas de Negocio
1. **Fechas de Liquidación**: No pueden ser anteriores a la fecha de operación
2. **Estados de Movimiento**: Solo se permiten transiciones válidas
3. **Importes**: Deben ser positivos y con máximo 2 decimales
4. **Días Hábiles**: Validación automática excluyendo fines de semana y festivos
5. **Operaciones**: Deben existir en el catálogo y estar activas

---

## 🚀 Ejemplos de Uso

### Ejemplo Completo: Crear y Procesar un Movimiento

#### 1. Crear Pre-Movimiento
```bash
curl -X POST "http://localhost:8080/api/v1/movimientos/pre-movimiento" \
  -H "Content-Type: application/json" \
  -d '{
    "claveGrupoEmpresa": "001",
    "claveEmpresa": "001",
    "idPreMovimiento": 12345,
    "fechaLiquidacion": "2025-02-03",
    "idCuenta": 1001,
    "claveDivisa": "MXN",
    "claveOperacion": "DEPOSITO",
    "importeNeto": 10000.00,
    "claveMedio": "TRANSFERENCIA",
    "claveMercado": "DEPOSITO",
    "nota": "Depósito de cliente",
    "claveUsuario": "USR001"
  }'
```

#### 2. Procesar Pre-Movimientos
```bash
curl -X POST "http://localhost:8080/api/v1/movimientos/procesar-pre-movimientos" \
  -H "Content-Type: application/json" \
  -d '{
    "claveGrupoEmpresa": "001",
    "claveEmpresa": "001",
    "fechaProceso": "2025-01-31"
  }'
```

#### 3. Convertir a Real
```bash
curl -X POST "http://localhost:8080/api/v1/movimientos/procesar-virtuales-a-reales" \
  -H "Content-Type: application/json" \
  -d '{
    "claveGrupoEmpresa": "001",
    "claveEmpresa": "001",
    "fechaProceso": "2025-01-31"
  }'
```

---

## 📚 Documentación Adicional

Para información detallada sobre cada endpoint, incluyendo ejemplos interactivos y esquemas de datos completos, visite:

**Swagger UI**: `http://localhost:8080/swagger-ui.html`

Esta documentación proporciona una interfaz interactiva donde puede probar todos los endpoints directamente desde el navegador.