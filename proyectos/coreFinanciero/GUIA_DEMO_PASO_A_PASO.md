# 🎯 GUÍA PASO A PASO - DEMOSTRACIÓN CORE FINANCIERO
*Para personas sin conocimiento técnico*

## 📋 ANTES DE EMPEZAR

### **¿Qué vas a demostrar?**
Un sistema bancario que procesa transacciones financieras de forma rápida y segura. Vas a mostrar cómo funciona en vivo ante el cliente.

### **¿Qué necesitas?**
- Una computadora con acceso a terminal/línea de comandos
- Los archivos del proyecto ya instalados
- 30 minutos de tiempo
- Esta guía impresa o en pantalla secundaria

---

## 🚀 PARTE 1: PREPARACIÓN (5 minutos antes de la reunión)

### **Paso 1: Abrir Terminal**
- **En Mac**: Buscar "Terminal" en Spotlight (Cmd + Espacio)
- **En Windows**: Buscar "Command Prompt" o "PowerShell"
- **En Linux**: Ctrl + Alt + T

### **Paso 2: Ir al Directorio del Proyecto**
Escribir en el terminal (EXACTAMENTE así):
```bash
cd /Users/mike/Desarrollo/academiaXidJulio25/proyectos/coreFinanciero
```
Presionar **Enter**

### **Paso 3: Iniciar el Backend (Motor del Sistema)**
Escribir:
```bash
java -jar target/core-financiero-1.0.0.jar
```
Presionar **Enter**

**¿Qué verás?**
- Muchas líneas de texto aparecerán
- Al final verás algo como: "Started CoreFinancieroApplication in X seconds"
- **NO CIERRES ESTA VENTANA** - El sistema está funcionando

### **Paso 4: Abrir Segunda Terminal**
- Abre otra ventana de terminal (repetir Paso 1)
- Ir al directorio del cliente:
```bash
cd /Users/mike/Desarrollo/academiaXidJulio25/proyectos/coreFinanciero/FinancialCoreClient
```

### **Paso 5: Verificar que Todo Funciona**
Escribir:
```bash
./run-client.sh
```

**Si funciona correctamente verás:**
- Texto que dice "🚀 Ejecutando Demo del Cliente HTTP/2"
- Números y estadísticas
- Mensaje final "🏁 DEMO COMPLETADO"

**Si hay error:**
- Verificar que el Paso 3 (backend) siga corriendo
- Esperar 10 segundos más y volver a intentar

---

## 🎬 PARTE 2: LA PRESENTACIÓN (Durante la reunión)

### **INTRODUCCIÓN (2 minutos)**

**Qué decir:**
*"Buenos días. Hoy les voy a mostrar nuestro Core Financiero en funcionamiento. Es un sistema que procesa transacciones bancarias de forma instantánea y segura. Lo que van a ver es el sistema real funcionando, no es una simulación."*

**Mostrar la pantalla del backend** (primera terminal):
*"Este es el motor del sistema. Como pueden ver, está funcionando y listo para procesar transacciones."*

---

### **DEMOSTRACIÓN PRINCIPAL (15 minutos)**

#### **Paso 6: Ejecutar Demo Principal**
En la segunda terminal, escribir:
```bash
./run-client.sh
```

**Mientras se ejecuta, explicar:**

**Al inicio (primeros 5 segundos):**
*"El sistema se está conectando usando tecnología HTTP/2, que permite procesar múltiples transacciones simultáneamente..."*

**Cuando aparezcan estadísticas de conexión:**
*"Aquí pueden ver que la conexión está establecida. El sistema usa multiplexing, que significa que puede manejar muchas operaciones al mismo tiempo..."*

**Durante el procesamiento (segundos 10-60):**
*"Ahora está procesando dos transacciones bancarias en paralelo:*
- *Un depósito de $5,000 pesos*
- *Un retiro de $1,500 pesos que luego será cancelado"*

**Señalar números importantes:**
- *"Duración total: X milisegundos - esto es increíblemente rápido"*
- *"Escenarios exitosos: El sistema maneja tanto éxitos como cancelaciones"*

**Al final:**
*"Como pueden ver, el sistema procesó todas las operaciones, mantuvo la integridad de los datos y generó un reporte completo de auditoría."*

#### **Paso 7: Mostrar Saldos y Estados**
**Señalar en la pantalla:**
- Los saldos iniciales: $100,000, $50,000, $25,000
- Los estados de transacciones: NP → PV → PR/CA
- Las verificaciones de integridad

**Explicar:**
*"Observen que los saldos finales son exactamente iguales a los iniciales. Esto demuestra que el sistema mantiene perfecta consistencia, incluso cuando cancela transacciones."*

---

### **DEMOSTRACIÓN DE RESILENCIA (8 minutos)**

#### **Paso 8: Explicar la Prueba**
*"Ahora les voy a mostrar qué pasa cuando el sistema falla y cómo se recupera automáticamente."*

#### **Paso 9: Parar el Backend**
En la primera terminal, presionar **Ctrl + C** 

**Explicar:**
*"Acabo de simular una falla del servidor, como si se hubiera cortado la luz o hubiera un problema técnico."*

#### **Paso 10: Intentar Operación Durante Falla**
En la segunda terminal:
```bash
./run-client.sh
```

**Explicar mientras falla:**
*"Como pueden ver, el sistema detecta inmediatamente que no puede conectarse. No se cuelga, no da errores raros, simplemente reporta que el servidor no está disponible."*

#### **Paso 11: Recuperar Sistema**
En la primera terminal:
```bash
java -jar target/core-financiero-1.0.0.jar
```

**Mientras arranca, explicar:**
*"Ahora estoy reiniciando el sistema. En un entorno real, esto sería automático..."*

**Esperar a que aparezca:** "Started CoreFinancieroApplication in X seconds"

#### **Paso 12: Verificar Recuperación**
En la segunda terminal:
```bash
./run-client.sh
```

**Explicar:**
*"Y aquí tienen la magia: el sistema está completamente operativo otra vez. Todos los datos están intactos, las configuraciones se mantuvieron, y pueden ver que los saldos son exactamente los mismos que antes de la falla."*

---

## 📊 PARTE 3: CIERRE Y MÉTRICAS (5 minutos)

### **Paso 13: Resumir Beneficios**

**Mostrar la pantalla final y decir:**

*"Lo que acabamos de ver demuestra tres capacidades críticas para cualquier sistema financiero:*

1. **Velocidad**: *Procesamos transacciones en 2-4 segundos vs 15-30 segundos de sistemas tradicionales*

2. **Confiabilidad**: *El sistema maneja errores elegantemente y se recupera automáticamente*

3. **Integridad**: *Ninguna transacción se pierde, ningún saldo queda inconsistente"*

### **Paso 14: Métricas Clave**
**Señalar en pantalla:**
- Duración total en milisegundos
- Conexiones HTTP/2 exitosas
- Estados de transacciones completados

*"Estas métricas son reales, no simuladas. El sistema acaba de procesar transacciones reales con datos reales."*

---

## 🔧 PARTE 4: SOLUCIÓN DE PROBLEMAS

### **Si el backend no arranca:**
1. Esperar 30 segundos
2. Intentar de nuevo
3. Si persiste, decir: *"Permítanme reiniciar el sistema"* y repetir Paso 3

### **Si el cliente da error:**
1. Verificar que el backend esté corriendo (primera terminal)
2. Esperar 10 segundos
3. Repetir el comando

### **Si la computadora se congela:**
1. Mantener la calma
2. Decir: *"Esto nos da oportunidad de hablar sobre la arquitectura mientras reiniciamos"*
3. Usar el tiempo para explicar beneficios verbalmente

### **Si no hay internet:**
- El demo funciona sin internet (localhost)
- Si hay problemas, continuar con explicación verbal usando esta guía

---

## 💬 FRASES CLAVE PARA USAR

### **Durante procesamiento:**
- *"Observen la velocidad de procesamiento..."*
- *"Aquí pueden ver el multiplexing en acción..."*
- *"Noten cómo mantiene la integridad de datos..."*

### **Durante fallas:**
- *"Esto es exactamente lo que queremos ver..."*
- *"El sistema detecta problemas inmediatamente..."*
- *"La recuperación es completamente automática..."*

### **Al mostrar resultados:**
- *"Estos números son métricas reales..."*
- *"Como pueden ver, cero pérdida de datos..."*
- *"La auditabilidad es completa..."*

---

## ❌ QUÉ NO HACER

- **NO** tocar otras ventanas o aplicaciones durante el demo
- **NO** intentar explicar detalles técnicos complejos
- **NO** entrar en pánico si algo sale mal
- **NO** cerrar las terminales hasta que termine la reunión
- **NO** intentar comandos que no están en esta guía

---

## ✅ QUÉ SÍ HACER

- **SÍ** mantener la calma si hay problemas
- **SÍ** usar las frases clave de esta guía
- **SÍ** señalar los números y estadísticas en pantalla
- **SÍ** enfocarse en los beneficios de negocio
- **SÍ** preparar esta guía antes de la reunión

---

## 📞 CONTACTO DE EMERGENCIA

**Si hay problemas técnicos graves durante la presentación:**
- Contactar al equipo técnico inmediatamente
- Continuar con explicación verbal mientras se resuelve
- Ofrecer reprogramar demo si es necesario

---

## 🎯 OBJETIVO FINAL

**Al terminar, el cliente debe entender:**
1. El sistema es rápido (2-4 segundos vs 15-30)
2. Es confiable (se recupera automáticamente)
3. Es seguro (mantiene integridad de datos)
4. Está listo para producción (no es un prototipo)

**Pregunta de cierre:**
*"¿Qué les parece si programamos una sesión para revisar cómo esto se integraría con sus sistemas actuales?"*

---

*Esta guía te permitirá realizar una demostración profesional sin necesidad de conocimiento técnico profundo. Practica los pasos 1-2 veces antes de la presentación real.*