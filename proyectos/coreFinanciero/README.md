# Core Financiero

Sistema financiero migrado de PL/SQL (Oracle) a Spring Boot 3.5.4 con base de datos H2 en memoria.

## Descripción

Este proyecto es una migración completa del paquete PL/SQL `PKG_PROCESOS` a una aplicación Spring Boot moderna siguiendo principios de Domain-Driven Design (DDD) y arquitectura en capas.

## Funcionalidades Migradas

### Funciones PL/SQL Originales:
- **RecorreFecha**: Actualiza la fecha medio al siguiente día hábil
- **CreaFechaLiquiacionAnio**: Genera fechas de liquidación para días hábiles del año
- **pGeneraPreMovto**: Crea movimientos de tesorería (abonos/cargos)
- **pGeneraPreMovtoDet**: Crea detalles de conceptos para operaciones
- **dameFechaSistema**: Obtiene la fecha actual del sistema

### APIs REST Disponibles:
- `/api/v1/fechas/*` - Operaciones de fechas del sistema
- `/api/v1/liquidacion/*` - Gestión de fechas de liquidación
- `/api/v1/movimientos/*` - Manejo de pre-movimientos de tesorería

## Tecnologías Utilizadas

- **Java 21**
- **Spring Boot 3.5.4**
- **Spring Data JPA**
- **H2 Database** (en memoria)
- **Maven** como build tool
- **MapStruct** para mapeo de DTOs
- **SpringDoc OpenAPI** para documentación
- **JUnit 5** y **Mockito** para testing

## Estructura del Proyecto

```
src/main/java/com/financiero/
├── CoreFinancieroApplication.java          # Clase principal
├── application/
│   ├── dto/                               # DTOs para transferencia de datos
│   └── service/                           # Servicios de aplicación
├── domain/
│   └── entity/                            # Entidades del dominio
├── infrastructure/
│   └── repository/                        # Repositorios JPA
├── presentation/
│   └── controller/                        # Controladores REST
└── exception/                             # Manejo de excepciones
```

## Modelo de Datos

### Entidades Principales:
- **ParametroSistema**: Parámetros del sistema financiero
- **DiaLiquidacion**: Fechas de liquidación para operaciones
- **DiaFestivo**: Días festivos por país y empresa
- **Prestamo**: Información de préstamos
- **PreMovimiento**: Pre-movimientos de tesorería
- **PreMovimientoDetalle**: Conceptos detallados de pre-movimientos

## Instalación y Ejecución

### Prerrequisitos:
- Java 21+
- Maven 3.9+

### Pasos:

1. **Clonar el repositorio**
   ```bash
   cd proyectos/coreFinanciero
   ```

2. **Compilar el proyecto**
   ```bash
   mvn clean compile
   ```

3. **Ejecutar tests**
   ```bash
   mvn test
   ```

4. **Ejecutar la aplicación**
   ```bash
   mvn spring-boot:run
   ```

5. **Acceder a la aplicación**
   - API: http://localhost:8080
   - H2 Console: http://localhost:8080/h2-console
   - Swagger UI: http://localhost:8080/swagger-ui.html

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

## Ejemplos de Uso

### 1. Obtener fecha del sistema:
```bash
GET /api/v1/fechas/sistema?claveGrupoEmpresa=GRP001&claveEmpresa=EMP001
```

### 2. Recorrer fecha al siguiente día hábil:
```bash
POST /api/v1/fechas/recorrer?claveGrupoEmpresa=GRP001&claveEmpresa=EMP001
```

### 3. Crear pre-movimiento:
```bash
POST /api/v1/movimientos/pre-movimiento
Content-Type: application/json

{
  "claveGrupoEmpresa": "GRP001",
  "claveEmpresa": "EMP001",
  "idPreMovimiento": 10005,
  "fechaLiquidacion": "2025-01-17",
  "idCuenta": 100001,
  "idPrestamo": 1001,
  "claveDivisa": "MXN",
  "claveOperacion": "PAGO",
  "importeNeto": 25000.00,
  "claveMedio": "TRANSFERENCIA",
  "claveMercado": "PRESTAMO",
  "nota": "Pago de préstamo",
  "claveUsuario": "USUARIO01"
}
```

### 4. Generar fechas de liquidación para un año:
```bash
POST /api/v1/liquidacion/crear-fechas-anio?claveGrupoEmpresa=GRP001&claveEmpresa=EMP001&anio=2025
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

## Testing

El proyecto incluye tests unitarios usando JUnit 5 y Mockito:

```bash
# Ejecutar todos los tests
mvn test

# Ejecutar tests con reporte de cobertura
mvn test jacoco:report
```

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

## Licencia

Este proyecto es una migración académica del sistema PL/SQL original.

---

**Versión**: 1.0.0  
**Autor**: Sistema Core Financiero  
**Fecha**: Enero 2025