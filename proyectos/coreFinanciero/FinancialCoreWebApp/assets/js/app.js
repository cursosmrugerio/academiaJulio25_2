/**
 * ======================================
 * FINANCIAL CORE WEBAPP - MAIN APPLICATION
 * Controlador principal de la aplicación
 * ======================================
 */

class FinancialCoreApp {
    constructor() {
        this.version = '1.0.0';
        this.initialized = false;
        this.components = {};
        this.routes = {
            'dashboard': () => this.showView('dashboard'),
            'scenarios': () => this.showView('scenarios'),
            'movements': () => this.showView('movements'),
            'balances': () => this.showView('balances'),
            'settings': () => this.showView('settings')
        };
    }

    /**
     * Inicializar la aplicación
     */
    async init() {
        if (this.initialized) return;

        console.log(`🚀 Initializing Financial Core WebApp v${this.version}`);

        try {
            // Configurar modo debug si estamos en desarrollo
            this.setupDebugMode();

            // Configurar manejadores de eventos globales
            this.setupGlobalEventHandlers();

            // Configurar navegación
            this.setupNavigation();

            // Configurar controles de empresa
            this.setupEmpresaControls();

            // Configurar controles de tema
            this.setupThemeControls();

            // Verificar conectividad inicial
            await this.checkInitialConnection();

            // Inicializar componentes
            this.initializeComponents();

            // Configurar router básico
            this.setupRouting();

            // Mostrar vista inicial
            this.showInitialView();

            // Configurar actualizaciones periódicas
            this.setupPeriodicUpdates();

            this.initialized = true;
            console.log('✅ Financial Core WebApp initialized successfully');

            // Mostrar notificación de bienvenida
            setTimeout(() => {
                NotificationSystem.show('FinancialCore WebApp iniciado correctamente', 'success');
            }, 1000);

        } catch (error) {
            console.error('❌ Failed to initialize app:', error);
            NotificationSystem.show('Error al inicializar la aplicación', 'error');
        }
    }

    /**
     * Configurar modo debug
     */
    setupDebugMode() {
        if (window.location.hostname === 'localhost' || 
            window.location.hostname === '127.0.0.1' ||
            window.location.search.includes('debug=true')) {
            
            window.DEBUG = true;
            window.app = this;
            console.log('🔧 Debug mode enabled - Access app via window.app');
        }
    }

    /**
     * Configurar manejadores de eventos globales
     */
    setupGlobalEventHandlers() {
        // Manejo de errores no capturados
        window.addEventListener('error', (event) => {
            console.error('Uncaught error:', event.error);
            NotificationSystem.show('Error inesperado en la aplicación', 'error');
        });

        // Manejo de promesas rechazadas no capturadas
        window.addEventListener('unhandledrejection', (event) => {
            console.error('Unhandled promise rejection:', event.reason);
            NotificationSystem.show('Error en operación asíncrona', 'error');
        });

        // Manejo de cambios de conectividad
        window.addEventListener('online', () => {
            NotificationSystem.show('Conexión restaurada', 'success');
            this.handleConnectionRestored();
        });

        window.addEventListener('offline', () => {
            NotificationSystem.show('Sin conexión a internet', 'warning');
            this.handleConnectionLost();
        });

        // Manejo de cambios de visibilidad de la página
        document.addEventListener('visibilitychange', () => {
            if (!document.hidden) {
                this.handlePageVisible();
            }
        });

        // Manejo de redimensionamiento
        window.addEventListener('resize', EventUtils.debounce(() => {
            this.handleWindowResize();
        }, 250));

        // Prevenir cierre accidental durante operaciones críticas
        window.addEventListener('beforeunload', (event) => {
            if (stateManager.get('scenarios.running')) {
                event.preventDefault();
                event.returnValue = 'Hay un escenario en ejecución. ¿Está seguro de salir?';
            }
        });
    }

    /**
     * Configurar navegación
     */
    setupNavigation() {
        // Navegación principal
        document.querySelectorAll('.nav-btn').forEach(button => {
            button.addEventListener('click', (e) => {
                e.preventDefault();
                const view = button.dataset.view;
                if (view && this.routes[view]) {
                    this.navigateTo(view);
                }
            });
        });

        // Botón de menú móvil
        const menuToggle = document.getElementById('menuToggle');
        const sidebar = document.querySelector('.sidebar');
        
        if (menuToggle && sidebar) {
            menuToggle.addEventListener('click', () => {
                sidebar.classList.toggle('open');
                stateManager.set('ui.sidebarOpen', sidebar.classList.contains('open'));
            });
        }

        // Cerrar sidebar al hacer clic fuera (móvil)
        document.addEventListener('click', (e) => {
            const sidebar = document.querySelector('.sidebar');
            const menuToggle = document.getElementById('menuToggle');
            
            if (sidebar && menuToggle && 
                !sidebar.contains(e.target) && 
                !menuToggle.contains(e.target) &&
                sidebar.classList.contains('open')) {
                
                sidebar.classList.remove('open');
                stateManager.set('ui.sidebarOpen', false);
            }
        });
    }

    /**
     * Configurar controles de empresa
     */
    setupEmpresaControls() {
        const empresaSelector = document.getElementById('empresaSelector');
        
        if (empresaSelector) {
            empresaSelector.addEventListener('change', async (e) => {
                const nuevaEmpresa = e.target.value;
                
                if (ValidationUtils.isValidEmpresaCode(nuevaEmpresa)) {
                    await this.changeEmpresa(nuevaEmpresa);
                } else {
                    NotificationSystem.show('Código de empresa inválido', 'error');
                    e.target.value = stateManager.getCurrentEmpresa();
                }
            });
        }
    }

    /**
     * Configurar controles de tema
     */
    setupThemeControls() {
        const themeToggle = document.getElementById('themeToggle');
        
        if (themeToggle) {
            themeToggle.addEventListener('click', () => {
                stateManager.toggleTheme();
            });
        }
    }

    /**
     * Verificar conectividad inicial
     */
    async checkInitialConnection() {
        try {
            stateManager.set('ui.loading', true);
            await stateManager.checkConnection();
        } catch (error) {
            console.error('Initial connection check failed:', error);
        } finally {
            stateManager.set('ui.loading', false);
        }
    }

    /**
     * Inicializar componentes
     */
    initializeComponents() {
        // Los componentes ya están inicializados globalmente
        // Dashboard: window.dashboardComponent
        // Scenarios: window.scenariosManager
        // State Manager: window.stateManager
        // API Client: window.apiClient

        this.components = {
            dashboard: window.dashboardComponent,
            scenarios: window.scenariosManager,
            stateManager: window.stateManager,
            apiClient: window.apiClient
        };

        console.log('📦 Components initialized:', Object.keys(this.components));
    }

    /**
     * Configurar routing básico
     */
    setupRouting() {
        // Manejar cambios de hash para navegación básica
        window.addEventListener('hashchange', () => {
            const hash = window.location.hash.slice(1);
            if (hash && this.routes[hash]) {
                this.navigateTo(hash);
            }
        });

        // Manejar navegación con historial
        window.addEventListener('popstate', () => {
            const currentView = stateManager.get('ui.currentView');
            this.showView(currentView);
        });
    }

    /**
     * Mostrar vista inicial
     */
    showInitialView() {
        const hash = window.location.hash.slice(1);
        const initialView = hash && this.routes[hash] ? hash : 'dashboard';
        this.navigateTo(initialView);
    }

    /**
     * Configurar actualizaciones periódicas
     */
    setupPeriodicUpdates() {
        // Verificar conexión cada 2 minutos
        setInterval(() => {
            if (stateManager.get('connection.status') !== 'offline') {
                stateManager.checkConnection();
            }
        }, 120000);

        // Limpiar métricas antiguas cada hora
        setInterval(() => {
            this.cleanupOldMetrics();
        }, 3600000);
    }

    // =====================================
    // NAVEGACIÓN
    // =====================================

    /**
     * Navegar a una vista
     */
    navigateTo(view) {
        if (!this.routes[view]) {
            console.warn(`Unknown route: ${view}`);
            return;
        }

        // Actualizar estado
        stateManager.setCurrentView(view);

        // Actualizar URL sin recargar
        if (window.location.hash.slice(1) !== view) {
            window.history.pushState({ view }, '', `#${view}`);
        }

        // Ejecutar ruta
        this.routes[view]();
    }

    /**
     * Mostrar vista
     */
    showView(view) {
        // Ocultar todas las vistas
        document.querySelectorAll('.view').forEach(viewElement => {
            viewElement.classList.remove('active');
        });

        // Remover clase activa de navegación
        document.querySelectorAll('.nav-btn').forEach(btn => {
            btn.classList.remove('active');
        });

        // Mostrar vista seleccionada
        const viewElement = document.getElementById(`${view}-view`);
        if (viewElement) {
            viewElement.classList.add('active');
        }

        // Activar botón de navegación
        const navButton = document.querySelector(`[data-view="${view}"]`);
        if (navButton) {
            navButton.classList.add('active');
        }

        // Cerrar sidebar en móvil
        const sidebar = document.querySelector('.sidebar');
        if (sidebar && sidebar.classList.contains('open')) {
            sidebar.classList.remove('open');
            stateManager.set('ui.sidebarOpen', false);
        }

        console.log(`📱 Showing view: ${view}`);
    }

    // =====================================
    // GESTIÓN DE EMPRESA
    // =====================================

    /**
     * Cambiar empresa
     */
    async changeEmpresa(nuevaEmpresa) {
        const empresaAnterior = stateManager.getCurrentEmpresa();
        
        try {
            // Mostrar loading
            this.showLoading('Cambiando empresa...');

            // Cambiar empresa en el state manager
            await stateManager.changeEmpresa(nuevaEmpresa);

            // Emitir evento para que los componentes se actualicen
            EventUtils.emit('empresa-changed', {
                anterior: empresaAnterior,
                nueva: nuevaEmpresa
            });

            console.log(`🏢 Empresa changed: ${empresaAnterior} → ${nuevaEmpresa}`);

        } catch (error) {
            console.error('Error changing empresa:', error);
            
            // Revertir selector
            const empresaSelector = document.getElementById('empresaSelector');
            if (empresaSelector) {
                empresaSelector.value = empresaAnterior;
            }
            
            NotificationSystem.show('Error al cambiar empresa', 'error');
        } finally {
            this.hideLoading();
        }
    }

    // =====================================
    // GESTIÓN DE CONEXIÓN
    // =====================================

    /**
     * Manejar conexión restaurada
     */
    async handleConnectionRestored() {
        await stateManager.checkConnection();
        
        // Refrescar datos si estamos en dashboard
        if (stateManager.get('ui.currentView') === 'dashboard') {
            this.components.dashboard?.loadInitialData();
        }
    }

    /**
     * Manejar pérdida de conexión
     */
    handleConnectionLost() {
        stateManager.set('connection.status', 'offline');
    }

    /**
     * Manejar página visible
     */
    handlePageVisible() {
        // Verificar conexión cuando la página vuelva a ser visible
        if (stateManager.get('connection.status') !== 'online') {
            stateManager.checkConnection();
        }
    }

    /**
     * Manejar redimensionamiento de ventana
     */
    handleWindowResize() {
        // Cerrar sidebar si la ventana se hace grande
        if (window.innerWidth > 768) {
            const sidebar = document.querySelector('.sidebar');
            if (sidebar && sidebar.classList.contains('open')) {
                sidebar.classList.remove('open');
                stateManager.set('ui.sidebarOpen', false);
            }
        }
    }

    // =====================================
    // UI UTILITIES
    // =====================================

    /**
     * Mostrar loading global
     */
    showLoading(message = 'Cargando...') {
        let loader = document.getElementById('globalLoader');
        
        if (!loader) {
            loader = document.createElement('div');
            loader.id = 'globalLoader';
            loader.className = 'global-loader';
            loader.innerHTML = `
                <div class="loader-backdrop"></div>
                <div class="loader-content">
                    <div class="loader-spinner"></div>
                    <div class="loader-message">${message}</div>
                </div>
            `;
            document.body.appendChild(loader);
        } else {
            loader.querySelector('.loader-message').textContent = message;
        }
        
        loader.style.display = 'flex';
        stateManager.set('ui.loading', true);
    }

    /**
     * Ocultar loading global
     */
    hideLoading() {
        const loader = document.getElementById('globalLoader');
        if (loader) {
            loader.style.display = 'none';
        }
        stateManager.set('ui.loading', false);
    }

    /**
     * Limpiar métricas antiguas
     */
    cleanupOldMetrics() {
        const activities = stateManager.get('metrics.activities') || [];
        const oneDayAgo = Date.now() - (24 * 60 * 60 * 1000);
        
        const recentActivities = activities.filter(activity => {
            const activityTime = new Date(activity.timestamp).getTime();
            return activityTime > oneDayAgo;
        });
        
        if (recentActivities.length !== activities.length) {
            stateManager.set('metrics.activities', recentActivities);
            console.log(`🧹 Cleaned up ${activities.length - recentActivities.length} old activities`);
        }
    }

    // =====================================
    // DEBUG UTILITIES
    // =====================================

    /**
     * Obtener información de debug
     */
    getDebugInfo() {
        return {
            version: this.version,
            initialized: this.initialized,
            components: Object.keys(this.components),
            currentView: stateManager.get('ui.currentView'),
            connectionStatus: stateManager.get('connection.status'),
            metrics: stateManager.getPerformanceMetrics(),
            state: stateManager.getDebugInfo()
        };
    }

    /**
     * Reinicializar aplicación
     */
    async reinitialize() {
        console.log('🔄 Reinitializing application...');
        
        this.initialized = false;
        
        // Limpiar componentes
        if (this.components.dashboard) {
            this.components.dashboard.destroy();
        }
        
        // Limpiar caché
        stateManager.clearCache();
        
        // Reinicializar
        await this.init();
    }

    /**
     * Exportar datos de la aplicación
     */
    exportAppData() {
        const data = {
            timestamp: new Date().toISOString(),
            version: this.version,
            state: stateManager.getState(),
            scenarios: this.components.scenarios?.results || [],
            debugInfo: this.getDebugInfo()
        };

        const dataStr = JSON.stringify(data, null, 2);
        const dataBlob = new Blob([dataStr], { type: 'application/json' });
        
        const link = document.createElement('a');
        link.href = URL.createObjectURL(dataBlob);
        link.download = `financial-core-app-data-${FormatUtils.formatDate(new Date(), 'iso')}.json`;
        link.click();
        
        NotificationSystem.show('Datos de la aplicación exportados', 'success');
    }
}

// =====================================
// INICIALIZACIÓN
// =====================================

// Crear instancia global de la aplicación
window.financialCoreApp = new FinancialCoreApp();

// Inicializar cuando el DOM esté listo
if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', () => {
        window.financialCoreApp.init();
    });
} else {
    window.financialCoreApp.init();
}

// Exponer funciones útiles globalmente
window.exportAppData = () => window.financialCoreApp.exportAppData();
window.reinitializeApp = () => window.financialCoreApp.reinitialize();

// Funciones de debugging (solo en modo debug)
if (window.DEBUG) {
    window.getAppDebugInfo = () => window.financialCoreApp.getDebugInfo();
    console.log('🔧 Debug functions available: getAppDebugInfo(), exportAppData(), reinitializeApp()');
}

// Exportar para uso en módulos
if (typeof module !== 'undefined' && module.exports) {
    module.exports = FinancialCoreApp;
}