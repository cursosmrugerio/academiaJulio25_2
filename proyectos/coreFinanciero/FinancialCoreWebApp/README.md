# FinancialCore WebApp

[![Version](https://img.shields.io/badge/version-1.0.0-blue.svg)](https://github.com/yourusername/financial-core-webapp)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](LICENSE)
[![PWA](https://img.shields.io/badge/PWA-enabled-purple.svg)](https://web.dev/progressive-web-apps/)

Una aplicación web moderna que replica la funcionalidad del cliente Java HTTP/2 para interactuar con el Core Financiero. Desarrollada con tecnologías web estándar (HTML, CSS, JavaScript) para proporcionar una alternativa web nativa al cliente de escritorio existente.

## 🚀 Características

### ✨ Funcionalidad Principal
- **Escenarios Demostrativos**: Replica exactamente los escenarios del cliente Java
  - Depósito Completo (NP → PV → PR)
  - Cancelación de Movimiento (NP → PV → CA)
  - Health Check completo del sistema
- **Dashboard en Tiempo Real**: Métricas, gráficos y actividad del sistema
- **API REST Completa**: Cliente JavaScript que consume todos los endpoints del Core Financiero
- **Gestión de Estado**: Sistema robusto de gestión de estado de la aplicación

### 🎨 Experiencia de Usuario
- **Diseño Responsive**: Funciona perfectamente en desktop, tablet y móvil
- **Progressive Web App (PWA)**: Instalable y funciona offline
- **Tema Claro/Oscuro**: Soporte completo para preferencias del usuario
- **Notificaciones**: Sistema de notificaciones con SweetAlert2
- **Animaciones Fluidas**: Transiciones y animaciones modernas

### 🛠️ Tecnología
- **Vanilla JavaScript**: Sin frameworks, máximo rendimiento
- **CSS Moderno**: Grid, Flexbox, Custom Properties
- **Service Worker**: Capacidades offline y PWA
- **Chart.js**: Visualizaciones de datos interactivas
- **HTML5 Semántico**: Estructura accesible y SEO-friendly

## 📋 Requisitos Previos

- **Core Financiero**: El backend debe estar ejecutándose en `http://localhost:8080`
- **Navegador Moderno**: Chrome 90+, Firefox 88+, Safari 14+, Edge 90+
- **Servidor Web**: Para servir los archivos estáticos (puede ser local)

## 🚀 Instalación y Uso

### Opción 1: Servidor Local Simple
```bash
# Navegar al directorio del proyecto
cd FinancialCoreWebApp

# Usar Python (si está instalado)
python -m http.server 8000

# O usar Node.js (si está instalado)
npx serve -s . -l 8000

# Abrir en el navegador
open http://localhost:8000
```

### Opción 2: Servidor Web Tradicional
1. Copiar todos los archivos a su servidor web (Apache, Nginx, etc.)
2. Configurar para servir archivos estáticos
3. Acceder mediante su dominio o IP

### Opción 3: Desarrollo Local
```bash
# Clonar el repositorio
git clone [repository-url]
cd FinancialCoreWebApp

# Abrir index.html directamente en el navegador
# (Algunas funcionalidades pueden requerir servidor local)
```

## 📖 Guía de Uso

### 1. Configuración Inicial
- Al abrir la aplicación, se verificará automáticamente la conexión al Core Financiero
- El indicador de estado mostrará el estado de la conexión en tiempo real
- La empresa por defecto es `001-001`, pero puede cambiarse en el selector

### 2. Dashboard Principal
- **Métricas en Tiempo Real**: Saldos totales, movimientos del día, pendientes
- **Gráfico de Distribución**: Visualización de saldos por cuenta
- **Log de Actividad**: Registro en tiempo real de todas las operaciones API
- **Auto-refresh**: Los datos se actualizan automáticamente cada 30 segundos

### 3. Escenarios Demostrativos

#### Depósito Completo
1. Configurar importe y cuenta en el formulario
2. Hacer clic en "Ejecutar Depósito"
3. El sistema ejecutará automáticamente:
   - Consulta de saldos iniciales
   - Creación de pre-movimiento
   - Procesamiento a virtual (NP → PV)
   - Procesamiento a real (PV → PR)
   - Verificación de saldos finales

#### Cancelación de Movimiento
1. Configurar importe y cuenta para el movimiento a cancelar
2. Hacer clic en "Ejecutar Cancelación"
3. El sistema ejecutará:
   - Creación de movimiento de retiro
   - Procesamiento a virtual
   - Cancelación del movimiento (PV → CA)
   - Verificación de reversión de saldos

#### Health Check
1. Hacer clic en "Ejecutar Health Check"
2. Verificación completa de:
   - Conectividad básica
   - APIs de fechas, saldos, catálogo
   - Validaciones de días hábiles
   - Métricas de latencia

### 4. Navegación y Controles
- **Menú Principal**: Dashboard, Escenarios, Movimientos, Saldos, Configuración
- **Selector de Empresa**: Cambiar contexto empresarial
- **Toggle de Tema**: Alternar entre tema claro y oscuro
- **Responsive**: Menú hamburguesa en dispositivos móviles

## 🏗️ Arquitectura

### Estructura de Archivos
```
FinancialCoreWebApp/
├── index.html                 # Página principal
├── manifest.json             # Configuración PWA
├── sw.js                     # Service Worker
├── assets/
│   ├── css/
│   │   ├── main.css         # Estilos principales
│   │   ├── components.css   # Componentes UI
│   │   └── responsive.css   # Estilos responsive
│   └── js/
│       ├── app.js           # Controlador principal
│       ├── core/
│       │   ├── api-client.js    # Cliente API REST
│       │   ├── utils.js         # Utilidades generales
│       │   └── state-manager.js # Gestión de estado
│       └── components/
│           ├── dashboard.js     # Componente dashboard
│           └── scenarios.js     # Gestión de escenarios
```

### Componentes Principales

#### API Client (`api-client.js`)
- Cliente HTTP REST completo
- Manejo de retry y timeout
- Logging de actividad
- Construcción automática de requests

#### State Manager (`state-manager.js`)
- Gestión centralizada del estado
- Persistencia en localStorage
- Sistema de eventos
- Caché inteligente

#### Dashboard Component (`dashboard.js`)
- Métricas en tiempo real
- Visualizaciones con Chart.js
- Auto-refresh configurable
- Log de actividad

#### Scenarios Manager (`scenarios.js`)
- Escenarios financieros completos
- Logging detallado de pasos
- Validaciones de parámetros
- Exportación de resultados

## 🔧 Configuración

### Variables de Configuración
La aplicación se configura automáticamente, pero puede personalizar:

```javascript
// En api-client.js
const API_BASE_URL = 'http://localhost:8080';

// En state-manager.js
const DEFAULT_EMPRESA = '001-001';

// En app.js
const AUTO_REFRESH_INTERVAL = 30000; // 30 segundos
```

### Personalización de Tema
```css
/* En main.css - Personalizar colores */
:root {
  --primary-color: #2563eb;
  --success-color: #10b981;
  --warning-color: #f59e0b;
  --error-color: #ef4444;
}
```

## 🐛 Resolución de Problemas

### Problemas Comunes

**❌ "Sin conexión al Core Financiero"**
- Verificar que el backend esté ejecutándose en `http://localhost:8080`
- Revisar configuración de CORS en el servidor
- Comprobar firewall y conectividad de red

**❌ "Error en escenarios"**
- Verificar que la empresa configurada exista en el sistema
- Comprobar que las cuentas especificadas sean válidas
- Revisar logs del servidor para errores específicos

**❌ "La aplicación no se instala como PWA"**
- Verificar que se esté sirviendo vía HTTPS (en producción)
- Comprobar que el manifest.json sea accesible
- Verificar que el service worker se registre correctamente

### Debugging
```javascript
// Habilitar modo debug en la consola
window.DEBUG = true;

// Obtener información de estado
console.log(getState());

// Obtener información de debugging
console.log(getAppDebugInfo());

// Exportar datos para análisis
exportAppData();
```

## 📱 Progressive Web App (PWA)

La aplicación está completamente configurada como PWA:

- **Instalable**: Puede instalarse en el dispositivo como app nativa
- **Offline**: Funcionalidad básica disponible sin conexión
- **Responsive**: Funciona perfectamente en todos los tamaños de pantalla
- **Fast**: Carga rápida gracias al service worker y caché

### Instalación como PWA
1. Abrir la aplicación en Chrome/Edge
2. Buscar el ícono de "Instalar" en la barra de direcciones
3. Hacer clic en "Instalar"
4. La app se agregará al menú de aplicaciones del sistema

## 🤝 Contribuciones

1. Fork el proyecto
2. Crear una rama para la funcionalidad (`git checkout -b feature/AmazingFeature`)
3. Commit los cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abrir un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

## 👥 Soporte

Para soporte técnico o preguntas:

- **Issues**: Crear un issue en elRepositorioDescripción del problema con pasos para reproducir
- **Email**: [su-email@empresa.com]
- **Documentación**: Revisar este README y comentarios en el código

## 🚀 Próximas Versiones

### v1.1.0 (Planeada)
- [ ] Gestión completa de movimientos
- [ ] Reportes y exportación de datos
- [ ] Configuración avanzada de empresas
- [ ] Métricas históricas y tendencias

### v1.2.0 (Planeada)
- [ ] Autenticación y autorización
- [ ] Múltiples usuarios
- [ ] Notificaciones push
- [ ] Integración con calendarios

---

**FinancialCore WebApp** - Una moderna interfaz web para el Core Financiero 💼✨