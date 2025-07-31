# 🏦 Core Financiero - Sistema de Tesorería Modernizado

[![Java](https://img.shields.io/badge/Java-21-orange)](https://openjdk.java.net/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.4-green)](https://spring.io/projects/spring-boot)
[![Test Coverage](https://img.shields.io/badge/Coverage-75%25-brightgreen)](https://www.jacoco.org/jacoco/)
[![API Documentation](https://img.shields.io/badge/API-Swagger/OpenAPI-blue)](http://localhost:8080/swagger-ui.html)

## 📋 Descripción

**Core Financiero** es una aplicación de tesorería financiera de clase empresarial, resultado de una **migración exitosa y completa** desde procedimientos almacenados PL/SQL hacia una arquitectura moderna basada en **Spring Boot**. 

El sistema gestiona operaciones críticas de tesorería incluyendo procesamiento de movimientos, gestión de liquidación, control de saldos y validación de fechas operativas, todo con **APIs REST completas** y **75% de cobertura de pruebas** garantizada por JaCoCo.

## 🚀 Funcionalidades Principales

### ✅ Migración 100% Exitosa desde PL/SQL

| Función PL/SQL Original | API REST Modernizada | Descripción |
|-------------------------|----------------------|-------------|
| `RecorreFecha` | `POST /api/v1/fechas/recorrer` | Avanza fecha sistema al siguiente día hábil |
| `CreaFechaLiquiacionAnio` | `POST /api/v1/liquidacion/crear-fechas-anio` | Genera fechas de liquidación del año |
| `pGeneraPreMovto` | `POST /api/v1/movimientos/pre-movimiento` | Crea pre-movimientos de tesorería |
| `pGeneraPreMovtoDet` | `POST /api/v1/movimientos/pre-movimiento-detalle` | Agrega conceptos detallados |
| `dameFechaSistema` | `GET /api/v1/fechas/sistema` | Obtiene fecha actual del sistema |

### 🔄 Procesamiento de Movimientos (Nuevas Capacidades)
- **Procesamiento masivo**: `POST /api/v1/movimientos/procesar-pre-movimientos`
- **Conversión a real**: `POST /api/v1/movimientos/procesar-virtuales-a-reales`
- **Cancelación controlada**: `POST /api/v1/movimientos/{id}/cancelar`
- **Consulta de movimientos**: `GET /api/v1/movimientos/{empresa}`
- **Monitoreo de pendientes**: `GET /api/v1/movimientos/pendientes-procesamiento`

### 💰 Gestión de Saldos y Catálogos
- **Consulta de saldos**: `GET /api/v1/movimientos/saldos/{empresa}`
- **Catálogo de operaciones**: `GET /api/v1/movimientos/catalogo-operaciones/{empresa}`
- **Total de conceptos**: `GET /api/v1/movimientos/total-conceptos/{id}`

### 📅 Validación y Fechas Avanzadas
- **Validación de fechas**: `GET /api/v1/liquidacion/validar-fecha`
- **Validación días hábiles**: `GET /api/v1/fechas/validar-dia-habil`
- **Actualización manual**: `PUT /api/v1/fechas/sistema`

## 🛠️ Stack Tecnológico

### **Core Framework**
- **Java 21** - Última versión LTS con performance mejorado
- **Spring Boot 3.5.4** - Framework empresarial moderno
- **Spring Data JPA** - Persistencia declarativa
- **Spring Web** - APIs REST robustas

### **Base de Datos y Persistencia**
- **H2 Database** - Base de datos en memoria para desarrollo
- **JPA/Hibernate** - ORM empresarial
- **Flyway/Liquibase** ready - Para migraciones de BD

### **Calidad y Testing** 
- **JUnit 5** - Framework de testing moderno
- **Mockito** - Mocking para pruebas unitarias
- **TestContainers** ready - Para pruebas de integración
- **JaCoCo** - **75% cobertura de pruebas garantizada**

### **Herramientas de Desarrollo**
- **Maven** - Gestión de dependencias y build
- **MapStruct** - Mapeo declarativo de DTOs
- **SpringDoc OpenAPI** - Documentación automática de APIs
- **Spring Boot Actuator** - Monitoreo y métricas

### **Arquitectura y Patrones**
- **Clean Architecture** - Separación clara de responsabilidades  
- **Domain-Driven Design (DDD)** - Modelado basado en dominio
- **SOLID Principles** - Principios de diseño aplicados
- **Bean Validation** - Validaciones declarativas

## 🏗️ Arquitectura del Proyecto

### **Estructura de Capas (Clean Architecture)**

```
src/main/java/com/financiero/
├── CoreFinancieroApplication.java          # 🚀 Clase principal Spring Boot
│
├── presentation/                           # 🌐 CAPA DE PRESENTACIÓN
│   └── controller/                         # Controladores REST
│       ├── FechaController.java           # APIs de fechas del sistema
│       ├── LiquidacionController.java     # APIs de liquidación
│       ├── MovimientoController.java      # APIs de pre-movimientos
│       └── MovimientoProcesadorController.java # APIs de procesamiento
│
├── application/                            # 📋 CAPA DE APLICACIÓN
│   ├── dto/                               # Data Transfer Objects
│   │   ├── request/                       # DTOs de entrada
│   │   └── response/                      # DTOs de salida
│   ├── service/                           # Servicios de aplicación
│   │   ├── FechaService.java             # Lógica de fechas
│   │   ├── LiquidacionService.java       # Lógica de liquidación
│   │   ├── MovimientoService.java        # Lógica de movimientos
│   │   └── MovimientoProcesadorService.java # Lógica de procesamiento
│   └── mapper/                            # Mappers DTO ↔ Entity
│
├── domain/                                 # 🏛️ CAPA DE DOMINIO
│   ├── entity/                            # Entidades del dominio
│   │   ├── PreMovimiento.java            # Pre-movimientos de tesorería
│   │   ├── Movimiento.java               # Movimientos procesados
│   │   ├── Saldo.java                    # Saldos de cuentas
│   │   ├── ParametroSistema.java         # Parámetros del sistema
│   │   └── DiaLiquidacion.java          # Fechas de liquidación
│   └── enums/                             # Enumeraciones del dominio
│
├── infrastructure/                         # 🔧 CAPA DE INFRAESTRUCTURA
│   └── repository/                        # Repositorios JPA
│       ├── PreMovimientoRepository.java
│       ├── MovimientoRepository.java
│       ├── SaldoRepository.java
│       └── ParametroSistemaRepository.java
│
└── config/                                # ⚙️ CONFIGURACIÓN
    ├── SwaggerConfig.java                 # Configuración OpenAPI
    ├── JpaConfig.java                     # Configuración JPA
    └── GlobalExceptionHandler.java        # Manejo global de errores
```

## 📊 Modelo de Datos Empresarial

### **Entidades del Dominio Financiero**

| Entidad | Propósito | Clave |
|---------|-----------|--------|
| **ParametroSistema** | Configuración del sistema por empresa | `claveGrupoEmpresa` + `claveEmpresa` + `clave` |
| **PreMovimiento** | Pre-movimientos de tesorería | `claveGrupoEmpresa` + `claveEmpresa` + `idPreMovimiento` |
| **PreMovimientoDetalle** | Conceptos detallados (intereses, comisiones) | FK PreMovimiento + `claveConcepto` |
| **Movimiento** | Movimientos procesados que afectan saldos | `claveGrupoEmpresa` + `claveEmpresa` + `idMovimiento` |
| **Saldo** | Saldos efectivos por cuenta y divisa | `claveGrupoEmpresa` + `claveEmpresa` + `fechaFoto` + `idCuenta` + `claveDivisa` |
| **DiaLiquidacion** | Fechas válidas de liquidación | `claveGrupoEmpresa` + `claveEmpresa` + `fechaLiquidacion` |
| **DiaFestivo** | Días no laborables por país | `codigoPais` + `fechaFestivo` |
| **CatalogoOperacion** | Tipos de operaciones disponibles | `claveGrupoEmpresa` + `claveEmpresa` + `claveOperacion` |

### **Estados de Movimientos**
- **NP (No Procesado)**: Movimiento registrado, pendiente de validación
- **PV (Procesado Virtual)**: Validado, listo para procesamiento real  
- **PR (Procesado Real)**: Ejecutado completamente, saldo afectado
- **CA (Cancelado)**: Movimiento cancelado, efectos revertidos

### **Relaciones Clave**
- `PreMovimiento` → `PreMovimientoDetalle` (1:N)
- `PreMovimiento` → `Movimiento` (1:1 al procesar)
- `Movimiento` → `Saldo` (afectación automática)
- `CatalogoOperacion` → validación de operaciones permitidas

## 🚀 Instalación y Ejecución

### **Prerrequisitos**
- ☕ **Java 21+** (LTS recomendado)
- 📦 **Maven 3.9+** 
- 🐳 **Docker** (opcional, para bases de datos externas)

### **Pasos de Instalación**

#### 1️⃣ **Obtener el Código**
```bash
# Navegar al directorio del proyecto
cd proyectos/coreFinanciero
```

#### 2️⃣ **Compilar y Preparar**
```bash
# Limpiar y compilar
mvn clean compile

# Descargar dependencias
mvn dependency:resolve
```

#### 3️⃣ **Ejecutar Pruebas (75% Cobertura)**
```bash
# Ejecutar todas las pruebas
mvn test

# Generar reporte de cobertura JaCoCo
mvn test jacoco:report

# Ver reporte de cobertura
open target/site/jacoco/index.html
```

#### 4️⃣ **Ejecutar la Aplicación**
```bash
# Modo desarrollo
mvn spring-boot:run

# O compilar y ejecutar JAR
mvn clean package
java -jar target/core-financiero-1.0.0.jar
```

#### 5️⃣ **Acceso a la Aplicación**

| Servicio | URL | Descripción |
|----------|-----|-------------|
| **API Base** | http://localhost:8080 | Endpoint base de APIs |
| **Swagger UI** | http://localhost:8080/swagger-ui.html | 📚 Documentación interactiva |
| **OpenAPI JSON** | http://localhost:8080/v3/api-docs | 📄 Especificación OpenAPI |
| **H2 Console** | http://localhost:8080/h2-console | 💾 Consola de base de datos |
| **Actuator Health** | http://localhost:8080/actuator/health | ❤️ Estado de la aplicación |
| **Actuator Metrics** | http://localhost:8080/actuator/metrics | 📊 Métricas de rendimiento |

## Configuración de Base de Datos

La aplicación utiliza H2 en memoria con datos iniciales precargados:

### Conexión H2:
- **URL**: `jdbc:h2:mem:financiero`
- **Usuario**: `sa`
- **Contraseña**: *(vacía)*

### Datos Iniciales:
- Parámetros del sistema para 3 empresas
- Días festivos de México 2025
- Fechas de liquidación base
- Préstamos y pre-movimientos de ejemplo

## 📋 Ejemplos de Uso Completos

### **🔄 Flujo Completo: Procesamiento de Movimiento**

#### 1️⃣ **Crear Pre-Movimiento**
```bash
curl -X POST "http://localhost:8080/api/v1/movimientos/pre-movimiento" \
  -H "Content-Type: application/json" \
  -d '{
    "claveGrupoEmpresa": "001",
    "claveEmpresa": "001",
    "idPreMovimiento": 99999,
    "fechaLiquidacion": "2025-02-03",
    "idCuenta": 100001,
    "claveDivisa": "MXN",
    "claveOperacion": "DEPOSITO",
    "importeNeto": 50000.00,
    "claveMedio": "TRANSFERENCIA",
    "claveMercado": "DEPOSITO",
    "nota": "Depósito cliente VIP",
    "claveUsuario": "OPERADOR01"
  }'
```

#### 2️⃣ **Agregar Conceptos Detallados**
```bash
curl -X POST "http://localhost:8080/api/v1/movimientos/pre-movimiento-detalle" \
  -H "Content-Type: application/json" \
  -d '{
    "claveGrupoEmpresa": "001",
    "claveEmpresa": "001",
    "idPreMovimiento": 99999,
    "claveConcepto": "COMISION",
    "importeConcepto": 250.00,
    "nota": "Comisión por operación"
  }'
```

#### 3️⃣ **Procesar a Estado Virtual**
```bash
curl -X POST "http://localhost:8080/api/v1/movimientos/procesar-pre-movimientos" \
  -H "Content-Type: application/json" \
  -d '{
    "claveGrupoEmpresa": "001",
    "claveEmpresa": "001",
    "fechaProceso": "2025-01-31"
  }'
```

#### 4️⃣ **Procesar a Estado Real**
```bash
curl -X POST "http://localhost:8080/api/v1/movimientos/procesar-virtuales-a-reales" \
  -H "Content-Type: application/json" \
  -d '{
    "claveGrupoEmpresa": "001",
    "claveEmpresa": "001",
    "fechaProceso": "2025-01-31"
  }'
```

### **📊 Consultas y Reportes**

#### **Consultar Saldos**
```bash
# Saldos actuales de la empresa
curl "http://localhost:8080/api/v1/movimientos/saldos/001/001"

# Saldos en fecha específica
curl "http://localhost:8080/api/v1/movimientos/saldos/001/001?fechaFoto=2025-01-30"

# Saldo de cuenta específica
curl "http://localhost:8080/api/v1/movimientos/saldos/001/001?idCuenta=100001"
```

#### **Consultar Movimientos**
```bash
# Movimientos por situación
curl "http://localhost:8080/api/v1/movimientos/001/001?situacion=PV"

# Movimientos por fecha
curl "http://localhost:8080/api/v1/movimientos/001/001?fechaOperacion=2025-01-31"

# Movimiento específico
curl "http://localhost:8080/api/v1/movimientos/001/001/12345"
```

### **📅 Gestión de Fechas**

#### **Operaciones de Fechas**
```bash
# Obtener fecha del sistema
curl "http://localhost:8080/api/v1/fechas/sistema?claveGrupoEmpresa=001&claveEmpresa=001"

# Validar día hábil
curl "http://localhost:8080/api/v1/fechas/validar-dia-habil?fecha=2025-02-14"

# Recorrer fecha al siguiente día hábil
curl -X POST "http://localhost:8080/api/v1/fechas/recorrer?claveGrupoEmpresa=001&claveEmpresa=001"
```

#### **Fechas de Liquidación**
```bash
# Generar fechas del año
curl -X POST "http://localhost:8080/api/v1/liquidacion/crear-fechas-anio?claveGrupoEmpresa=001&claveEmpresa=001&anio=2025"

# Validar fecha de liquidación
curl "http://localhost:8080/api/v1/liquidacion/validar-fecha?claveGrupoEmpresa=001&claveEmpresa=001&fechaOperacion=2025-01-31&fechaLiquidacion=2025-02-03&claveMercado=DEPOSITO"
```

### **🛠️ Operaciones Administrativas**

#### **Cancelar Movimiento**
```bash
curl -X POST "http://localhost:8080/api/v1/movimientos/001/001/12345/cancelar?claveUsuario=SUPERVISOR01"
```

#### **Consultar Catálogo de Operaciones**
```bash
# Todas las operaciones
curl "http://localhost:8080/api/v1/movimientos/catalogo-operaciones/001/001"

# Solo operaciones activas
curl "http://localhost:8080/api/v1/movimientos/catalogo-operaciones/001/001?estatus=A"
```

## Arquitectura

### Capas de la Aplicación:
1. **Presentation Layer** (`controller/`): Controllers REST
2. **Application Layer** (`service/`, `dto/`): Lógica de aplicación y DTOs
3. **Domain Layer** (`entity/`): Entidades del dominio con lógica de negocio
4. **Infrastructure Layer** (`repository/`): Persistencia y acceso a datos

### Principios Aplicados:
- **Domain-Driven Design (DDD)**
- **Clean Architecture**
- **SOLID Principles**
- **Separation of Concerns**

## 🧪 Testing y Calidad de Código

### **Cobertura de Pruebas del 75% (Garantizada por JaCoCo)**

El proyecto mantiene altos estándares de calidad con pruebas exhaustivas:

#### **Tipos de Pruebas Implementadas**
- ✅ **Pruebas Unitarias** - Lógica de servicios y componentes
- ✅ **Pruebas de Integración** - Interacción con base de datos
- ✅ **Pruebas de Controladores** - Validación de APIs REST
- ✅ **Pruebas de Repositorio** - Verificación de consultas JPA

#### **Comandos de Testing**
```bash
# Ejecutar todas las pruebas
mvn test

# Ejecutar con reporte de cobertura
mvn clean test jacoco:report

# Ver reporte de cobertura en navegador
open target/site/jacoco/index.html

# Ejecutar solo pruebas unitarias
mvn test -Dtest="*Test"

# Ejecutar solo pruebas de integración
mvn test -Dtest="*IT"

# Verificar que la cobertura cumple el mínimo (75%)
mvn verify
```

#### **Estructura de Pruebas**
```
src/test/java/
├── unit/                          # Pruebas unitarias
│   ├── service/                   # Tests de servicios
│   ├── controller/                # Tests de controladores
│   └── repository/                # Tests de repositorios
├── integration/                   # Pruebas de integración
│   ├── MovimientoIntegrationTest.java
│   └── FechaIntegrationTest.java
└── config/                        # Configuración de tests
    └── TestConfig.java
```

### **Validaciones y Calidad**
- **Bean Validation**: Validaciones declarativas en DTOs
- **Global Exception Handler**: Manejo centralizado de errores
- **Transactional Tests**: Rollback automático de datos de prueba
- **Test Profiles**: Configuraciones específicas para testing

## Manejo de Errores

Sistema de manejo global de excepciones con:
- **BusinessException**: Errores de lógica de negocio
- **NotFoundException**: Recursos no encontrados
- **ValidationException**: Errores de validación
- **GlobalExceptionHandler**: Manejo centralizado

## Documentación API

La documentación de la API está disponible a través de Swagger UI en:
http://localhost:8080/swagger-ui.html

## Monitoreo

Endpoints de Actuator disponibles:
- `/actuator/health` - Estado de la aplicación
- `/actuator/info` - Información de la aplicación
- `/actuator/metrics` - Métricas de la aplicación

## Desarrollo

### Agregar nuevas funcionalidades:
1. Crear entidad en `domain/entity/`
2. Crear repositorio en `infrastructure/repository/`
3. Implementar servicio en `application/service/`
4. Crear DTOs en `application/dto/`
5. Implementar controller en `presentation/controller/`
6. Agregar tests correspondientes

### Consideraciones:
- Seguir convenciones de nomenclatura existentes
- Implementar validaciones apropiadas
- Agregar documentación Swagger
- Incluir tests unitarios
- Mantener principios DDD

## 🎯 Logros de la Modernización

### **✅ Migración Exitosa Completada**

| Aspecto | Antes (PL/SQL) | Después (Spring Boot) | Mejora |
|---------|----------------|----------------------|---------|
| **Arquitectura** | Procedimientos en BD | Clean Architecture | 🚀 **Mantenibilidad** |
| **Testing** | Manual/Limitado | 75% automatizado | 🧪 **Calidad garantizada** |
| **APIs** | Llamadas directas a BD | REST + OpenAPI | 🌐 **Integración moderna** |
| **Documentación** | Estática/Desactualizada | Auto-generada | 📚 **Siempre actualizada** |
| **Despliegues** | Scripts manuales | JAR ejecutable | 🚀 **DevOps ready** |
| **Escalabilidad** | Limitada por BD | Horizontal/Cloud | 📈 **Crecimiento sostenible** |

### **🏆 Beneficios Empresariales Alcanzados**
- **100% de funcionalidad preservada** desde el sistema PL/SQL original
- **75% de cobertura de pruebas** que garantiza confiabilidad
- **APIs REST completas** listas para integración con cualquier frontend
- **Documentación automática** que se mantiene siempre actualizada
- **Arquitectura empresarial** preparada para crecimiento y nuevas necesidades

### **🔮 Preparado para el Futuro**
- ✅ **Microservicios**: Arquitectura lista para dividir en servicios independientes
- ✅ **Cloud Native**: Preparado para contenedores y Kubernetes
- ✅ **API-First**: Integración sencilla con sistemas externos
- ✅ **Monitoring**: Actuator endpoints para observabilidad
- ✅ **Security**: Base sólida para implementar seguridad empresarial

---

## 📞 Soporte y Contribución

### **Documentación Adicional**
- 📚 **[API Documentation](API_DOCUMENTATION.md)** - Documentación completa de endpoints
- 🏛️ **[Guía Funcional](GUIA_FUNCIONAL.md)** - Guía de negocio y valor empresarial
- 🚀 **Swagger UI**: http://localhost:8080/swagger-ui.html

### **Información del Proyecto**
- **Versión**: 1.0.0
- **Tecnología**: Spring Boot 3.5.4 + Java 21
- **Cobertura**: 75% garantizada por JaCoCo
- **Estado**: ✅ Producción Ready
- **Fecha**: Enero 2025

---

*"De PL/SQL a Spring Boot: Una modernización exitosa que preserva toda la funcionalidad crítica mientras habilita el futuro digital de las operaciones financieras."*