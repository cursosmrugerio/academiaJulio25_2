# Plan de Desarrollo Frontend Integrado - Core Financiero

## 🎯 Objetivo Revisado
Desarrollar una aplicación frontend usando **HTML, CSS y JavaScript vanilla** integrada directamente dentro del proyecto Spring Boot existente, aprovechando la capacidad de Spring Boot para servir contenido estático y crear una solución monolítica completa.

---

## 🏗️ Arquitectura de Integración

### **Ventajas de la Integración**
- ✅ **Despliegue único**: Un solo JAR contiene backend y frontend
- ✅ **Sin CORS**: Frontend y backend en el mismo dominio
- ✅ **Configuración simplificada**: Una sola aplicación para mantener
- ✅ **URLs relativas**: Consumo directo de APIs sin configuración adicional
- ✅ **Desarrollo unificado**: Un solo proyecto, un solo repositorio

### **Estructura del Proyecto Integrado**
```
core-financiero/
├── src/
│   ├── main/
│   │   ├── java/com/financiero/          # Backend Spring Boot
│   │   └── resources/
│   │       ├── static/                   # 🆕 Frontend Assets
│   │       │   ├── index.html
│   │       │   ├── css/
│   │       │   │   ├── main.css
│   │       │   │   ├── components.css
│   │       │   │   └── responsive.css
│   │       │   ├── js/
│   │       │   │   ├── app.js
│   │       │   │   ├── router.js
│   │       │   │   ├── api/
│   │       │   │   │   ├── apiClient.js
│   │       │   │   │   ├── movimientosApi.js
│   │       │   │   │   ├── fechasApi.js
│   │       │   │   │   └── liquidacionApi.js
│   │       │   │   ├── components/
│   │       │   │   │   ├── dashboard.js
│   │       │   │   │   ├── movimientos.js
│   │       │   │   │   ├── saldos.js
│   │       │   │   │   └── common.js
│   │       │   │   ├── modules/
│   │       │   │   │   ├── procesador.js
│   │       │   │   │   ├── liquidacion.js
│   │       │   │   │   └── fechas.js
│   │       │   │   └── utils/
│   │       │   │       ├── validation.js
│   │       │   │       ├── formatting.js
│   │       │   │       └── constants.js
│   │       │   └── assets/
│   │       │       ├── images/
│   │       │       ├── icons/
│   │       │       └── fonts/
│   │       ├── application.yml
│   │       ├── schema.sql
│   │       └── data.sql
│   └── test/                            # Tests existentes
├── pom.xml
└── README.md
```

---

## 📋 Plan de Implementación por Fases

### **🔧 Fase 1: Configuración Base (Días 1-2)**

#### **1.1 Configuración Spring Boot**
- Configurar Spring Boot para servir contenido estático desde `/static`
- Agregar configuración de CORS si es necesario
- Configurar rutas para SPA (Single Page Application)

#### **1.2 Estructura Base del Frontend**
```
src/main/resources/static/
├── index.html                 # Página principal
├── css/
│   ├── reset.css             # Reset CSS
│   ├── variables.css         # Variables CSS (colores, fuentes)
│   ├── layout.css            # Layout principal
│   └── components.css        # Estilos de componentes
└── js/
    ├── app.js                # Punto de entrada
    ├── config.js             # Configuración global
    └── utils/
        └── domUtils.js       # Utilidades DOM
```

#### **1.3 Configuración Inicial**
- Crear archivo de configuración para URLs de API
- Implementar sistema de enrutamiento SPA básico
- Configurar estructura base HTML con navegación

---

### **⚡ Fase 2: Core del Sistema (Días 3-7)**

#### **2.1 Cliente API Base**
```javascript
// js/api/apiClient.js - Cliente HTTP reutilizable
class ApiClient {
    constructor(baseURL = '/api/v1') {
        this.baseURL = baseURL;
    }
    
    async request(endpoint, options = {}) {
        // Implementación con manejo de errores
        // Headers automáticos
        // Validación de respuestas
    }
}
```

#### **2.2 Módulos API Específicos**
- **movimientosApi.js**: Todas las operaciones de movimientos
- **fechasApi.js**: Gestión de fechas del sistema
- **liquidacionApi.js**: Operaciones de liquidación
- **saldosApi.js**: Consultas de saldos

#### **2.3 Sistema de Componentes**
```javascript
// js/components/BaseComponent.js
class BaseComponent {
    constructor(containerId) {
        this.container = document.getElementById(containerId);
        this.state = {};
    }
    
    render() { /* Template rendering */ }
    bindEvents() { /* Event binding */ }
    update(newState) { /* State management */ }
}
```

---

### **🎨 Fase 3: Módulos Funcionales (Días 8-14)**

#### **3.1 Dashboard Principal**
- **Componente**: `DashboardComponent`
- **Funcionalidades**:
  - Resumen de movimientos pendientes
  - Estado de saldos principales
  - Alertas y notificaciones
  - Accesos rápidos a funciones principales

#### **3.2 Módulo de Movimientos**
- **Componentes**:
  - `PreMovimientoForm`: Crear pre-movimientos
  - `MovimientosList`: Listar y filtrar movimientos
  - `MovimientoDetail`: Detalles de movimiento específico
  - `MovimientoProcesador`: Procesar operaciones

#### **3.3 Módulo de Saldos**
- **Componentes**:
  - `SaldosTable`: Tabla de saldos por cuenta
  - `SaldosChart`: Gráficos de evolución (Canvas/SVG)
  - `SaldosFilters`: Filtros por fecha, cuenta, divisa

#### **3.4 Módulo de Fechas**
- **Componentes**:
  - `FechaSistema`: Mostrar y actualizar fecha del sistema
  - `CalendarioLiquidacion`: Calendario de días hábiles
  - `ValidadorFechas`: Validación de fechas de operación

---

### **🔄 Fase 4: Operaciones Avanzadas (Días 15-18)**

#### **4.1 Procesamiento de Movimientos**
- Interfaz para procesar pre-movimientos en lote
- Conversión de movimientos virtuales a reales
- Cancelación de movimientos con confirmación

#### **4.2 Gestión de Liquidación**
- Crear fechas de liquidación para el año
- Validar fechas de liquidación
- Mantenimiento de días festivos

#### **4.3 Reportes y Consultas**
- Generación de reportes básicos
- Exportación de datos (CSV/Excel via JavaScript)
- Consultas avanzadas con filtros múltiples

---

### **💎 Fase 5: UX/UI y Optimización (Días 19-21)**

#### **5.1 Diseño Responsive**
```css
/* CSS Grid para layout principal */
.main-layout {
    display: grid;
    grid-template-areas: 
        "header header"
        "sidebar main"
        "footer footer";
    grid-template-columns: 250px 1fr;
    grid-template-rows: 60px 1fr 40px;
    min-height: 100vh;
}

/* Responsive breakpoints */
@media (max-width: 768px) {
    .main-layout {
        grid-template-columns: 1fr;
        grid-template-areas: 
            "header"
            "main"
            "footer";
    }
}
```

#### **5.2 Sistema de Temas**
- Variables CSS para colores institucionales
- Modo oscuro/claro opcional
- Estilos consistentes en todos los componentes

#### **5.3 Optimizaciones de Performance**
- Lazy loading de módulos
- Debounce en búsquedas
- Paginación eficiente
- Cache local de datos frecuentes

---

## 🛠️ Configuración Spring Boot

### **Configuración Estática**
```yaml
# application.yml
spring:
  web:
    resources:
      static-locations: classpath:/static/
      cache-period: 3600 # 1 hora en desarrollo
  
  mvc:
    # Configurar para SPA
    view:
      suffix: .html
    static-path-pattern: /**
```

### **Controlador para SPA**
```java
@Controller
public class FrontendController {
    
    @RequestMapping(value = "/{path:[^\\.]*}", method = RequestMethod.GET)
    public String forward() {
        return "forward:/index.html";
    }
}
```

---

## 🎨 Estándares de Desarrollo

### **JavaScript**
- **ES6+ Features**: Arrow functions, destructuring, async/await
- **Modular**: Cada funcionalidad en módulos separados
- **Naming**: camelCase para variables, PascalCase para clases
- **Comments**: JSDoc para funciones públicas

### **CSS**
- **Methodology**: BEM (Block Element Modifier)
- **Variables**: CSS Custom Properties
- **Mobile First**: Responsive design desde mobile
- **Flexbox/Grid**: Para layouts modernos

### **HTML**
- **Semantic**: Uso de elementos semánticos HTML5
- **Accessibility**: Atributos ARIA donde sea necesario
- **Performance**: Minificación en producción

---

## 📱 Responsive Design Strategy

### **Breakpoints**
```css
/* Mobile First Approach */
:root {
    --mobile: 480px;
    --tablet: 768px;
    --desktop: 1024px;
    --large: 1200px;
}

/* Components adaptables */
.data-table {
    /* Mobile: Stack cards */
    display: block;
}

@media (min-width: 768px) {
    .data-table {
        /* Tablet+: Table layout */
        display: table;
    }
}
```

### **Navigation Pattern**
- **Mobile**: Hamburger menu colapsible
- **Tablet**: Sidebar colapsible
- **Desktop**: Sidebar fijo con sub-navegación

---

## 🔒 Seguridad y Validación

### **Client-Side Validation**
```javascript
class FormValidator {
    static validatePreMovimiento(data) {
        const errors = {};
        
        if (!data.claveGrupoEmpresa?.trim()) {
            errors.claveGrupoEmpresa = 'Campo requerido';
        }
        
        if (!data.importeNeto || data.importeNeto <= 0) {
            errors.importeNeto = 'Debe ser mayor a 0';
        }
        
        return { isValid: Object.keys(errors).length === 0, errors };
    }
}
```

### **Error Handling**
- Manejo centralizado de errores HTTP
- Mensajes user-friendly
- Logging de errores para debugging
- Fallbacks para cuando APIs no responden

---

## 📊 Testing Strategy

### **Manual Testing Checklist**
- [ ] Navegación entre módulos
- [ ] Formularios con validación
- [ ] Responsive en diferentes dispositivos
- [ ] Manejo de errores de API
- [ ] Performance con datos reales

### **Browser Compatibility**
- **Primary**: Chrome, Firefox, Safari, Edge (últimas 2 versiones)
- **Secondary**: iOS Safari, Chrome Mobile

---

## 🚀 Deployment & Production

### **Build Process**
```bash
# Desarrollo
mvn spring-boot:run

# Producción
mvn clean package
java -jar target/core-financiero-1.0.0.jar
```

### **Optimización para Producción**
- Minificación de CSS/JS
- Compresión de imágenes
- Configurar cache headers apropiados
- CDN para assets estáticos (opcional)

---

## 📈 Roadmap Futuro

### **Mejoras Potenciales**
- **PWA**: Service Workers para funcionalidad offline
- **WebSockets**: Actualizaciones en tiempo real
- **Charts**: Integración con Chart.js o D3.js
- **Exportación**: PDF generation client-side
- **Tema**: Personalización por usuario

### **Monitoreo**
- Google Analytics para uso
- Error tracking client-side
- Performance monitoring

---

## 🎯 Entregables por Fase

### **Fase 1**: Configuración Base
- [ ] Configuración Spring Boot completa
- [ ] Estructura de carpetas implementada
- [ ] Página principal funcional
- [ ] Sistema de navegación básico

### **Fase 2**: Core del Sistema
- [ ] Cliente API implementado
- [ ] Sistema de componentes base
- [ ] Manejo de estados
- [ ] Error handling centralizado

### **Fase 3**: Módulos Funcionales
- [ ] Dashboard operativo
- [ ] CRUD de movimientos completo
- [ ] Consultas de saldos
- [ ] Gestión de fechas

### **Fase 4**: Operaciones Avanzadas
- [ ] Procesamiento de movimientos
- [ ] Validaciones de liquidación
- [ ] Reportes básicos

### **Fase 5**: UX/UI Final
- [ ] Diseño responsive completo
- [ ] Optimizaciones de performance
- [ ] Testing integral
- [ ] Documentación de usuario

---

## ⏱️ Timeline Estimado

| Fase | Duración | Hitos Principales |
|------|----------|-------------------|
| **Fase 1** | 2 días | Configuración y estructura base |
| **Fase 2** | 5 días | APIs y componentes core |
| **Fase 3** | 7 días | Módulos funcionales principales |
| **Fase 4** | 4 días | Operaciones avanzadas |
| **Fase 5** | 3 días | UX/UI y optimización |
| **Total** | **21 días** | Aplicación completa funcional |

Este plan integrado aprovecha las ventajas de tener frontend y backend en una sola aplicación Spring Boot, simplificando el desarrollo, despliegue y mantenimiento del sistema Core Financiero.