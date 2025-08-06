# Cliente API MongoDB CRUD

Cliente Java independiente para consumir las APIs del servidor CRUD MongoDB usando Java HttpClient nativo.

## Características

- **Java HttpClient nativo** (sin dependencias externas HTTP)
- **Operaciones CRUD completas** para entidad Cliente
- **Aplicación de consola interactiva**
- **Manejo de errores robusto**
- **Logging con SLF4J + Logback**
- **Serialización JSON con Jackson**
- **Soporte asíncrono y síncrono**

## Prerequisitos

- Java 17 o superior
- Maven 3.6+
- Servidor MongoDB CRUD ejecutándose en `http://localhost:8080`

## Estructura del Proyecto

```
api-client/
├── src/main/java/com/mongodb/client/
│   ├── Main.java                    # Aplicación de consola
│   ├── config/
│   │   └── HttpClientConfig.java    # Configuración HTTP Cliente
│   ├── model/
│   │   ├── Cliente.java            # Modelo de datos
│   │   └── ApiResponse.java        # Respuesta wrapper
│   └── service/
│       └── ClienteService.java     # Servicios API
├── src/main/resources/
│   └── logback.xml                 # Configuración logging
└── pom.xml                         # Dependencias Maven
```

## Instalación y Ejecución

### 1. Compilar el proyecto
```bash
cd /Users/mike/Desarrollo/academiaXidJulio25/proyectos/api-client
mvn clean compile
```

### 2. Ejecutar aplicación
```bash
mvn exec:java -Dexec.mainClass="com.mongodb.client.Main"
```

### 3. Crear JAR ejecutable
```bash
mvn clean package
java -jar target/api-client-1.0.0.jar
```

## APIs Disponibles

El cliente consume las siguientes APIs del servidor:

### Endpoints Soportados
- `POST /api/clientes` - Crear cliente
- `GET /api/clientes` - Obtener todos los clientes  
- `GET /api/clientes/{id}` - Obtener cliente por ID
- `GET /api/clientes/email/{email}` - Obtener cliente por email
- `GET /api/clientes/search?nombre={nombre}` - Buscar por nombre
- `PUT /api/clientes/{id}` - Actualizar cliente
- `DELETE /api/clientes/{id}` - Eliminar por ID
- `DELETE /api/clientes/email/{email}` - Eliminar por email

## Uso de la Aplicación de Consola

La aplicación presenta un menú interactivo:

```
=== MENÚ PRINCIPAL ===
1. Crear cliente
2. Listar todos los clientes
3. Buscar cliente por ID
4. Buscar cliente por email
5. Buscar clientes por nombre
6. Actualizar cliente
7. Eliminar cliente por ID
8. Eliminar cliente por email
9. Salir
```

### Ejemplo de Uso Programático

```java
ClienteService service = new ClienteService();

// Crear cliente
Cliente nuevoCliente = new Cliente("Juan Pérez", "juan@email.com", "123456789");
ApiResponse<Cliente> response = service.crearCliente(nuevoCliente);

if (response.isSuccess()) {
    System.out.println("Cliente creado: " + response.getData());
}

// Obtener todos los clientes
ApiResponse<List<Cliente>> clientes = service.obtenerTodosLosClientes();
clientes.getData().forEach(System.out::println);

// Uso asíncrono
service.crearClienteAsync(nuevoCliente)
    .thenAccept(result -> System.out.println("Resultado: " + result));
```

## Configuración

### Configurar URL del servidor
Editar `HttpClientConfig.java`:
```java
private static final String BASE_URL = "http://localhost:8080/api/clientes";
```

### Configurar timeouts
```java
private static final Duration CONNECT_TIMEOUT = Duration.ofSeconds(10);
private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(30);
```

## Logging

Los logs se generan en:
- **Consola**: Nivel INFO para nuestra aplicación
- **Archivo**: `logs/api-client.log` (rotación diaria)

## Tecnologías Utilizadas

- **Java 17**: Lenguaje base
- **HttpClient**: Cliente HTTP nativo de Java
- **Jackson**: Serialización JSON
- **SLF4J + Logback**: Logging
- **Maven**: Gestión de dependencias
- **Maven Shade Plugin**: Creación JAR ejecutable

## Estructura JSON Cliente

```json
{
    "id": "string",
    "nombre": "string", 
    "email": "string",
    "telefono": "string",
    "fechaRegistro": "2025-01-01T12:00:00"
}
```

## Manejo de Errores

El cliente maneja automáticamente:
- **Errores de conexión**
- **Timeouts**  
- **Códigos de estado HTTP**
- **Errores de serialización JSON**
- **Validaciones de entrada**

Todos los errores se registran en logs y se presentan de manera amigable al usuario.