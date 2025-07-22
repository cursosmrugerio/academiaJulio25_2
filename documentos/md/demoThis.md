# Demostración del uso de `this` en Java

Este documento muestra diferentes formas de usar la palabra clave `this` en Java con ejemplos prácticos.

## Código de ejemplo completo

```java
public class Empleado {
    private String nombre;
    private int edad;
    private double salario;
    
    // Constructor principal
    public Empleado(String nombre, int edad, double salario) {
        this.nombre = nombre;    // 1. Disambiguación de parámetros
        this.edad = edad;
        this.salario = salario;
    }
    
    // Constructor sobrecargado
    public Empleado(String nombre) {
        this(nombre, 0, 0.0);    // 2. Llamada a otro constructor
    }
    
    // Método para establecer datos
    public void setDatos(String nombre, int edad) {
        this.nombre = nombre;    // 3. Referencia a campo de instancia
        this.edad = edad;
        this.actualizarEstado(); // 4. Llamada a método de la misma clase
    }
    
    private void actualizarEstado() {
        System.out.println("Estado actualizado para: " + this.nombre);
    }
    
    // Método que retorna la instancia actual
    public Empleado aumentarSalario(double porcentaje) {
        this.salario += this.salario * (porcentaje / 100);
        return this;             // 5. Retorno de la instancia actual (fluent interface)
    }
    
    // Método que compara con otra instancia
    public boolean esIgual(Empleado otro) {
        return this.equals(otro); // 6. Llamada explícita a método heredado
    }
    
    // Método que pasa la instancia actual como parámetro
    public void registrarEnSistema() {
        SistemaRRHH.registrar(this); // 7. Pasar instancia actual como argumento
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;        // 8. Comparación de referencias
        if (obj == null || this.getClass() != obj.getClass()) return false;
        
        Empleado empleado = (Empleado) obj;
        return this.edad == empleado.edad &&
               Double.compare(empleado.salario, this.salario) == 0 &&
               this.nombre.equals(empleado.nombre);
    }
    
    // Clase interna que accede a la clase externa
    public class InformeEmpleado {
        public void generarInforme() {
            // 9. Acceso a la instancia de la clase externa
            System.out.println("Informe de: " + Empleado.this.nombre);
            System.out.println("Edad: " + Empleado.this.edad);
        }
    }
    
    @Override
    public String toString() {
        return "Empleado{nombre='" + this.nombre + "', edad=" + this.edad + 
               ", salario=" + this.salario + "}";
    }
}

// Clase auxiliar
class SistemaRRHH {
    public static void registrar(Empleado empleado) {
        System.out.println("Registrando: " + empleado);
    }
}
```

## Explicación de cada uso de `this`

### 1. Disambiguación de parámetros
```java
this.nombre = nombre;
```
**Propósito:** Distingue entre el parámetro del método y el campo de la clase cuando tienen el mismo nombre.

### 2. Llamada a constructor
```java
this(nombre, 0, 0.0);
```
**Propósito:** Invoca otro constructor de la misma clase. Debe ser la primera línea del constructor y permite reutilizar código de inicialización.

### 3. Referencia a campo de instancia
```java
this.nombre = nombre;
```
**Propósito:** Hace referencia explícita al campo de la instancia actual (aunque no es necesario si no hay ambigüedad).

### 4. Llamada a método
```java
this.actualizarEstado();
```
**Propósito:** Invoca un método de la misma instancia (el `this` es opcional aquí, pero hace el código más explícito).

### 5. Retorno de instancia actual (Fluent Interface)
```java
return this;
```
**Propósito:** Permite encadenar métodos creando un patrón fluent interface para mayor legibilidad.

### 6. Llamada explícita a método heredado
```java
return this.equals(otro);
```
**Propósito:** Referencia explícita al método de la instancia actual, útil para claridad en el código.

### 7. Pasar instancia como argumento
```java
SistemaRRHH.registrar(this);
```
**Propósito:** Pasa la instancia actual como parámetro a otro método o clase.

### 8. Comparación de referencias
```java
if (this == obj) return true;
```
**Propósito:** Compara si dos referencias apuntan al mismo objeto en memoria (identidad, no igualdad).

### 9. Acceso desde clase interna
```java
Empleado.this.nombre
```
**Propósito:** Desde una clase interna, accede específicamente a la instancia de la clase externa cuando hay ambigüedad.

## Ejemplo de uso práctico

```java
public class Main {
    public static void main(String[] args) {
        // Creación y uso con fluent interface
        Empleado emp = new Empleado("Juan", 30, 50000)
            .aumentarSalario(10)  // Fluent interface gracias a 'return this'
            .aumentarSalario(5);
        
        // Registro en sistema
        emp.registrarEnSistema();
        
        // Uso de clase interna
        Empleado.InformeEmpleado informe = emp.new InformeEmpleado();
        informe.generarInforme();
        
        // Salida esperada:
        // Registrando: Empleado{nombre='Juan', edad=30, salario=57750.0}
        // Informe de: Juan
        // Edad: 30
    }
}
```

## Puntos clave sobre `this`

- **`this` es una referencia** a la instancia actual del objeto
- **Es implícito** en la mayoría de casos, pero útil para claridad
- **Obligatorio** cuando hay conflicto de nombres entre parámetros y campos
- **Útil para fluent interfaces** al retornar `this`
- **Necesario en clases internas** para acceder a la clase externa
- **No disponible en métodos estáticos** porque no hay instancia

---

# Ejemplo Avanzado: Sistema de Cache con Builder Pattern y Method References

## Código completo del ejemplo avanzado

```java
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.*;

public class DatabaseManager<T> {
    private final Map<String, T> cache = new ConcurrentHashMap<>();
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final List<CacheListener<T>> listeners = new ArrayList<>();
    private final String connectionUrl;
    private volatile boolean connected = false;
    
    private DatabaseManager(Builder<T> builder) {
        this.connectionUrl = builder.connectionUrl;
        this.cache.putAll(builder.initialData);
        this.listeners.addAll(builder.listeners);
    }
    
    // 10. Method reference con 'this'
    public Optional<T> find(String key) {
        return Optional.ofNullable(cache.get(key))
                .map(this::validateAndReturn);  // Method reference a método de instancia
    }
    
    private T validateAndReturn(T value) {
        System.out.println("Validando valor para instancia: " + this.hashCode());
        return value;
    }
    
    // 11. Callback que pasa 'this' como contexto
    public void save(String key, T value) {
        cache.put(key, value);
        // Pasar 'this' como contexto al callback
        notifyListeners(listener -> listener.onSave(this, key, value));
    }
    
    private void notifyListeners(Consumer<CacheListener<T>> action) {
        listeners.forEach(action);
    }
    
    // 12. Uso en synchronized block para monitor de instancia
    public synchronized DatabaseManager<T> clearCache() {
        synchronized(this) {  // Explícito: usando 'this' como monitor
            cache.clear();
            System.out.println("Cache limpiado para instancia: " + this.hashCode());
        }
        return this;  // Fluent interface
    }
    
    // 13. Uso en reflection con 'this'
    public void inspectInstance() {
        Class<?> thisClass = this.getClass();
        System.out.println("Inspeccionando instancia de: " + thisClass.getSimpleName());
        
        // Usar 'this' para obtener información de runtime
        Arrays.stream(thisClass.getDeclaredFields())
              .filter(field -> !field.getName().equals("this$0"))  // Excluir referencia externa
              .forEach(field -> {
                  try {
                      field.setAccessible(true);
                      Object value = field.get(this);  // Pasar 'this' para reflection
                      System.out.println(field.getName() + ": " + value);
                  } catch (IllegalAccessException e) {
                      System.err.println("No se puede acceder al campo: " + field.getName());
                  }
              });
    }
    
    // 14. Weak reference para evitar memory leaks
    public WeakReference<DatabaseManager<T>> getWeakReference() {
        return new WeakReference<>(this);  // Crear weak reference a 'this'
    }
    
    // 15. Clone personalizado usando 'this'
    public DatabaseManager<T> createSimilar() {
        return new Builder<T>()
                .withConnectionUrl(this.connectionUrl)  // Usar datos de 'this'
                .withInitialData(new HashMap<>(this.cache))
                .build();
    }
    
    // 16. Clase interna compleja con múltiples niveles
    public class TransactionManager {
        private final String transactionId;
        
        public TransactionManager(String transactionId) {
            this.transactionId = transactionId;  // Campo de clase interna
        }
        
        public void executeInTransaction(Runnable operation) {
            System.out.println("Iniciando transacción: " + this.transactionId);
            
            // 17. Referencia a clase externa desde clase interna
            DatabaseManager.this.lock.writeLock().lock();
            try {
                operation.run();
                // Acceso a campos de la clase externa
                System.out.println("Transacción en DB: " + DatabaseManager.this.connectionUrl);
            } finally {
                DatabaseManager.this.lock.writeLock().unlock();
            }
        }
        
        // 18. Clase interna anidada con acceso multinivel
        public class TransactionLogger {
            public void log(String message) {
                // Acceso a tres niveles: Logger -> TransactionManager -> DatabaseManager
                System.out.println("[" + TransactionManager.this.transactionId + "] " +
                                 "en DB(" + DatabaseManager.this.connectionUrl + "): " + message);
            }
            
            public void logCacheSize() {
                // Acceso directo a la clase más externa
                System.out.println("Cache actual: " + DatabaseManager.this.cache.size() + " elementos");
            }
        }
    }
    
    // 19. Builder pattern avanzado con validaciones
    public static class Builder<T> {
        private String connectionUrl;
        private Map<String, T> initialData = new HashMap<>();
        private List<CacheListener<T>> listeners = new ArrayList<>();
        
        public Builder<T> withConnectionUrl(String url) {
            this.connectionUrl = url;
            return this;  // Retorno de 'this' para fluent interface
        }
        
        public Builder<T> withInitialData(Map<String, T> data) {
            this.initialData.putAll(data);
            return this;
        }
        
        public Builder<T> addListener(CacheListener<T> listener) {
            this.listeners.add(listener);
            return this;
        }
        
        // 20. Validación usando 'this' antes de construir
        public DatabaseManager<T> build() {
            validateBuilder(this);  // Pasar 'this' para validación
            return new DatabaseManager<>(this);  // Pasar 'this' al constructor
        }
        
        private void validateBuilder(Builder<T> builder) {
            if (builder.connectionUrl == null) {
                throw new IllegalStateException("Connection URL es requerida");
            }
        }
    }
    
    // 21. Uso con CompletableFuture y method references
    public CompletableFuture<T> findAsync(String key) {
        return CompletableFuture
                .supplyAsync(() -> cache.get(key))
                .thenApply(this::validateAndReturn)  // Method reference con 'this'
                .whenComplete(this::handleAsyncResult);  // Otro method reference
    }
    
    private void handleAsyncResult(T result, Throwable throwable) {
        if (throwable != null) {
            System.err.println("Error en instancia " + this.hashCode() + ": " + throwable);
        }
    }
    
    // 22. Implementación de equals/hashCode usando 'this'
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;  // Comparación de referencia rápida
        if (!(obj instanceof DatabaseManager)) return false;
        
        DatabaseManager<?> that = (DatabaseManager<?>) obj;
        return Objects.equals(this.connectionUrl, that.connectionUrl) &&
               Objects.equals(this.cache, that.cache);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.connectionUrl, this.cache);
    }
}

// Interface para callbacks
interface CacheListener<T> {
    void onSave(DatabaseManager<T> source, String key, T value);
}

// 23. Implementación de listener que usa la referencia pasada
class LoggingCacheListener<T> implements CacheListener<T> {
    @Override
    public void onSave(DatabaseManager<T> source, String key, T value) {
        System.out.println("Cache actualizado en instancia " + source.hashCode() + 
                         ": " + key + " = " + value);
    }
}
```

## Ejemplo de uso del código avanzado

```java
public class AdvancedExample {
    public static void main(String[] args) {
        // Crear instancia usando Builder pattern
        DatabaseManager<String> db = new DatabaseManager.Builder<String>()
                .withConnectionUrl("jdbc:postgresql://localhost:5432/testdb")
                .addListener(new LoggingCacheListener<>())
                .withInitialData(Map.of("user1", "Alice", "user2", "Bob"))
                .build();
        
        // Usar method references con 'this'
        db.find("user1").ifPresent(System.out::println);
        
        // Transacciones con clases internas anidadas
        DatabaseManager<String>.TransactionManager txManager = 
                db.new TransactionManager("TX-001");
        
        DatabaseManager<String>.TransactionManager.TransactionLogger logger = 
                txManager.new TransactionLogger();
        
        txManager.executeInTransaction(() -> {
            db.save("user3", "Charlie");
            logger.log("Usuario agregado");
            logger.logCacheSize();
        });
        
        // Async operations con method references
        db.findAsync("user2")
          .thenAccept(result -> System.out.println("Resultado async: " + result));
        
        // Reflexión e inspección
        db.inspectInstance();
        
        // Crear instancia similar
        DatabaseManager<String> db2 = db.createSimilar();
        
        // Verificar igualdad
        System.out.println("Son iguales: " + db.equals(db2));
        System.out.println("Hash db1: " + db.hashCode());
        System.out.println("Hash db2: " + db2.hashCode());
    }
}
```

## Explicación de los usos avanzados de `this`

### 10. Method Reference con `this`
```java
.map(this::validateAndReturn)
```
**Propósito:** Crea una referencia a un método de la instancia actual, útil con streams y programación funcional.

### 11. Callback que pasa `this` como contexto
```java
listener.onSave(this, key, value)
```
**Propósito:** Pasa la instancia actual como contexto a callbacks, permitiendo que el listener acceda al objeto fuente.

### 12. Synchronized con `this` como monitor
```java
synchronized(this) { ... }
```
**Propósito:** Usa la instancia actual como monitor para sincronización, controlando acceso concurrente.

### 13. Reflection con `this`
```java
Object value = field.get(this);
```
**Propósito:** Pasa la instancia actual a la API de reflection para inspeccionar campos y métodos en runtime.

### 14. Weak Reference
```java
return new WeakReference<>(this);
```
**Propósito:** Crea una referencia débil a la instancia actual para evitar memory leaks en caches o listeners.

### 15. Clone/Copy usando datos de `this`
```java
.withConnectionUrl(this.connectionUrl)
```
**Propósito:** Usa los datos de la instancia actual para crear objetos similares o copias.

### 16-18. Clases internas anidadas multinivel
```java
DatabaseManager.this.connectionUrl
TransactionManager.this.transactionId
```
**Propósito:** Accede a diferentes niveles de instancias en clases internas anidadas.

### 19-20. Builder pattern con validación
```java
validateBuilder(this);
return new DatabaseManager<>(this);
```
**Propósito:** Pasa la instancia del builder para validación y construcción del objeto final.

### 21. Method references en programación asíncrona
```java
.thenApply(this::validateAndReturn)
.whenComplete(this::handleAsyncResult)
```
**Propósito:** Usa method references con `this` en chains de CompletableFuture para procesamiento asíncrono.

### 22. Optimización en equals()
```java
if (this == obj) return true;
```
**Propósito:** Optimización rápida en equals() verificando si ambas referencias apuntan al mismo objeto.

### 23. Contexto en callbacks
```java
System.out.println("Cache actualizado en instancia " + source.hashCode());
```
**Propósito:** El listener recibe la referencia del objeto fuente para logging o procesamiento adicional.

## Patrones avanzados identificados

- **Observer Pattern** con `this` como sujeto observado
- **Builder Pattern** con fluent interface usando `return this`
- **Method References** para programación funcional
- **Weak References** para gestión de memoria
- **Reflection** para introspección en runtime
- **Synchronization** usando `this` como monitor
- **Async Programming** con method references
- **Multi-level Inner Classes** con acceso controlado a instancias externas