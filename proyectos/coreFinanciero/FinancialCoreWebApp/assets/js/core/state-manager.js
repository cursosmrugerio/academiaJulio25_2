/**
 * ======================================
 * FINANCIAL CORE WEBAPP - STATE MANAGER
 * Gestor de estado global de la aplicación
 * ======================================
 */

class StateManager {
    constructor() {
        this.state = {
            // Configuración de conexión
            connection: {
                status: 'checking', // checking, online, offline
                baseUrl: 'http://localhost:8080',
                lastCheck: null,
                latency: null
            },
            
            // Usuario y empresa actual
            user: {
                name: 'WEBAPP_USER',
                empresa: '001-001',
                permissions: ['read', 'write', 'process']
            },
            
            // Datos en caché
            cache: {
                saldos: null,
                movimientos: null,
                catalogoOperaciones: null,
                fechaSistema: null,
                lastUpdate: null
            },
            
            // Estado de la UI
            ui: {
                currentView: 'dashboard',
                theme: 'light',
                sidebarOpen: false,
                loading: false,
                notifications: []
            },
            
            // Métricas y actividad
            metrics: {
                totalRequests: 0,
                successfulRequests: 0,
                averageLatency: 0,
                activities: []
            },
            
            // Escenarios en ejecución
            scenarios: {
                running: null,
                history: []
            }
        };
        
        this.listeners = new Map();
        this.init();
    }

    /**
     * Inicializar el state manager
     */
    init() {
        // Cargar estado persistido
        this.loadPersistedState();
        
        // Configurar listeners de eventos
        this.setupEventListeners();
        
        // Inicializar UI
        this.initializeUI();
        
        console.log('🔧 State Manager initialized', this.state);
    }

    /**
     * Cargar estado desde localStorage
     */
    loadPersistedState() {
        const saved = StorageUtils.get('financial-core-state', {});
        
        // Cargar configuración de usuario
        if (saved.user) {
            this.state.user = { ...this.state.user, ...saved.user };
        }
        
        // Cargar configuración de UI
        if (saved.ui) {
            this.state.ui = { ...this.state.ui, ...saved.ui };
        }
        
        // Cargar configuración de conexión
        if (saved.connection?.baseUrl) {
            this.state.connection.baseUrl = saved.connection.baseUrl;
        }
    }

    /**
     * Persistir estado en localStorage
     */
    persistState() {
        const toPersist = {
            user: this.state.user,
            ui: {
                theme: this.state.ui.theme,
                currentView: this.state.ui.currentView
            },
            connection: {
                baseUrl: this.state.connection.baseUrl
            }
        };
        
        StorageUtils.set('financial-core-state', toPersist);
    }

    /**
     * Configurar listeners de eventos
     */
    setupEventListeners() {
        // Escuchar actividad de API
        EventUtils.on('api-activity', (event) => {
            this.recordActivity(event.detail);
        });
        
        // Escuchar cambios de conectividad
        window.addEventListener('online', () => {
            this.updateConnectionStatus('online');
        });
        
        window.addEventListener('offline', () => {
            this.updateConnectionStatus('offline');
        });
        
        // Escuchar cambios de visibilidad
        document.addEventListener('visibilitychange', () => {
            if (!document.hidden) {
                this.checkConnection();
            }
        });
    }

    /**
     * Inicializar UI basada en el estado
     */
    initializeUI() {
        // Aplicar tema
        ThemeUtils.setTheme(this.state.ui.theme);
        
        // Configurar empresa
        const empresaSelector = document.getElementById('empresaSelector');
        if (empresaSelector) {
            empresaSelector.value = this.state.user.empresa;
        }
        
        // Verificar conexión inicial
        setTimeout(() => this.checkConnection(), 500);
    }

    // =====================================
    // GETTERS Y SETTERS
    // =====================================

    /**
     * Obtener estado completo
     */
    getState() {
        return { ...this.state };
    }

    /**
     * Obtener parte específica del estado
     */
    get(path) {
        return this.getNestedValue(this.state, path);
    }

    /**
     * Actualizar estado
     */
    set(path, value) {
        this.setNestedValue(this.state, path, value);
        this.notifyListeners(path, value);
        this.persistState();
    }

    /**
     * Obtener valor anidado usando dot notation
     */
    getNestedValue(obj, path) {
        return path.split('.').reduce((curr, key) => curr?.[key], obj);
    }

    /**
     * Establecer valor anidado usando dot notation
     */
    setNestedValue(obj, path, value) {
        const keys = path.split('.');
        const lastKey = keys.pop();
        const target = keys.reduce((curr, key) => {
            if (!curr[key]) curr[key] = {};
            return curr[key];
        }, obj);
        target[lastKey] = value;
    }

    // =====================================
    // CONEXIÓN
    // =====================================

    /**
     * Verificar estado de conexión
     */
    async checkConnection() {
        this.set('connection.status', 'checking');
        
        try {
            const startTime = performance.now();
            const isHealthy = await apiClient.isHealthy();
            const endTime = performance.now();
            const latency = Math.round(endTime - startTime);
            
            this.set('connection.status', isHealthy ? 'online' : 'offline');
            this.set('connection.latency', latency);
            this.set('connection.lastCheck', new Date().toISOString());
            
            // Actualizar UI
            this.updateConnectionUI();
            
            return isHealthy;
        } catch (error) {
            console.error('Connection check failed:', error);
            this.set('connection.status', 'offline');
            this.updateConnectionUI();
            return false;
        }
    }

    /**
     * Actualizar estado de conexión
     */
    updateConnectionStatus(status) {
        this.set('connection.status', status);
        this.updateConnectionUI();
    }

    /**
     * Actualizar UI de conexión
     */
    updateConnectionUI() {
        const statusIndicator = document.getElementById('statusIndicator');
        const statusText = document.getElementById('statusText');
        
        if (!statusIndicator || !statusText) return;
        
        const status = this.get('connection.status');
        const latency = this.get('connection.latency');
        
        // Limpiar clases previas
        statusIndicator.className = 'status-indicator';
        
        switch (status) {
            case 'online':
                statusIndicator.classList.add('online');
                statusText.textContent = `Core Financiero (${latency}ms)`;
                break;
            case 'offline':
                statusIndicator.classList.add('offline');
                statusText.textContent = 'Sin conexión';
                break;
            case 'checking':
                statusIndicator.classList.add('checking');
                statusText.textContent = 'Verificando...';
                break;
        }
    }

    // =====================================
    // USUARIO Y EMPRESA
    // =====================================

    /**
     * Cambiar empresa actual
     */
    async changeEmpresa(empresa) {
        const oldEmpresa = this.get('user.empresa');
        this.set('user.empresa', empresa);
        
        try {
            // Limpiar caché al cambiar empresa
            this.clearCache();
            
            // Recargar datos básicos
            await this.loadBasicData();
            
            NotificationSystem.show(`Empresa cambiada a ${empresa}`, 'success');
        } catch (error) {
            // Revertir cambio si falla
            this.set('user.empresa', oldEmpresa);
            NotificationSystem.show('Error al cambiar empresa', 'error');
        }
    }

    /**
     * Obtener empresa actual
     */
    getCurrentEmpresa() {
        return this.get('user.empresa');
    }

    // =====================================
    // CACHÉ DE DATOS
    // =====================================

    /**
     * Obtener saldos (con caché)
     */
    async getSaldos(forceRefresh = false) {
        const cached = this.get('cache.saldos');
        const lastUpdate = this.get('cache.lastUpdate');
        
        // Usar caché si es reciente (menos de 5 minutos)
        if (!forceRefresh && cached && lastUpdate) {
            const age = Date.now() - new Date(lastUpdate).getTime();
            if (age < 300000) { // 5 minutos
                return cached;
            }
        }
        
        try {
            const response = await apiClient.consultarSaldos(this.getCurrentEmpresa());
            if (response.success) {
                this.set('cache.saldos', response.data);
                this.set('cache.lastUpdate', new Date().toISOString());
                return response.data;
            }
        } catch (error) {
            console.error('Error loading saldos:', error);
        }
        
        return cached || [];
    }

    /**
     * Obtener fecha del sistema (con caché)
     */
    async getFechaSistema(forceRefresh = false) {
        const cached = this.get('cache.fechaSistema');
        
        if (!forceRefresh && cached) {
            return cached;
        }
        
        try {
            const response = await apiClient.obtenerFechaSistema(this.getCurrentEmpresa());
            if (response.success) {
                this.set('cache.fechaSistema', response.data);
                return response.data;
            }
        } catch (error) {
            console.error('Error loading fecha sistema:', error);
        }
        
        return cached;
    }

    /**
     * Limpiar caché
     */
    clearCache() {
        this.set('cache', {
            saldos: null,
            movimientos: null,
            catalogoOperaciones: null,
            fechaSistema: null,
            lastUpdate: null
        });
    }

    /**
     * Cargar datos básicos
     */
    async loadBasicData() {
        try {
            await Promise.all([
                this.getSaldos(true),
                this.getFechaSistema(true)
            ]);
        } catch (error) {
            console.error('Error loading basic data:', error);
        }
    }

    // =====================================
    // ACTIVIDAD Y MÉTRICAS
    // =====================================

    /**
     * Registrar actividad de API
     */
    recordActivity(activity) {
        // Actualizar métricas
        this.set('metrics.totalRequests', this.get('metrics.totalRequests') + 1);
        
        if (activity.success) {
            const successful = this.get('metrics.successfulRequests') + 1;
            this.set('metrics.successfulRequests', successful);
        }
        
        // Calcular latencia promedio
        const currentAvg = this.get('metrics.averageLatency');
        const total = this.get('metrics.totalRequests');
        const newAvg = ((currentAvg * (total - 1)) + activity.duration) / total;
        this.set('metrics.averageLatency', Math.round(newAvg));
        
        // Agregar a historial de actividades (mantener últimas 50)
        const activities = this.get('metrics.activities') || [];
        activities.unshift(activity);
        if (activities.length > 50) {
            activities.splice(50);
        }
        this.set('metrics.activities', activities);
        
        // Emitir evento para actualizar UI
        EventUtils.emit('activity-recorded', activity);
    }

    /**
     * Obtener métricas de rendimiento
     */
    getPerformanceMetrics() {
        const metrics = this.get('metrics');
        const successRate = metrics.totalRequests > 0 
            ? Math.round((metrics.successfulRequests / metrics.totalRequests) * 100)
            : 0;
            
        return {
            ...metrics,
            successRate
        };
    }

    // =====================================
    // VISTAS Y UI
    // =====================================

    /**
     * Cambiar vista actual
     */
    setCurrentView(view) {
        const oldView = this.get('ui.currentView');
        this.set('ui.currentView', view);
        
        // Actualizar UI
        this.updateViewUI(oldView, view);
        
        EventUtils.emit('view-changed', { from: oldView, to: view });
    }

    /**
     * Actualizar UI de vistas
     */
    updateViewUI(oldView, newView) {
        // Actualizar navegación
        document.querySelectorAll('.nav-btn').forEach(btn => {
            btn.classList.remove('active');
            if (btn.dataset.view === newView) {
                btn.classList.add('active');
            }
        });
        
        // Mostrar/ocultar vistas
        document.querySelectorAll('.view').forEach(view => {
            view.classList.remove('active');
            if (view.id === `${newView}-view`) {
                view.classList.add('active');
            }
        });
    }

    /**
     * Alternar tema
     */
    toggleTheme() {
        const currentTheme = this.get('ui.theme');
        const newTheme = currentTheme === 'light' ? 'dark' : 'light';
        this.set('ui.theme', newTheme);
        ThemeUtils.setTheme(newTheme);
    }

    // =====================================
    // ESCENARIOS
    // =====================================

    /**
     * Iniciar ejecución de escenario
     */
    startScenario(scenarioType) {
        const scenario = {
            type: scenarioType,
            startTime: Date.now(),
            status: 'running',
            steps: [],
            results: null
        };
        
        this.set('scenarios.running', scenario);
        EventUtils.emit('scenario-started', scenario);
    }

    /**
     * Finalizar ejecución de escenario
     */
    finishScenario(results) {
        const running = this.get('scenarios.running');
        if (!running) return;
        
        const completedScenario = {
            ...running,
            endTime: Date.now(),
            duration: Date.now() - running.startTime,
            status: results.success ? 'completed' : 'failed',
            results
        };
        
        // Agregar al historial
        const history = this.get('scenarios.history') || [];
        history.unshift(completedScenario);
        if (history.length > 20) {
            history.splice(20);
        }
        
        this.set('scenarios.history', history);
        this.set('scenarios.running', null);
        
        EventUtils.emit('scenario-finished', completedScenario);
    }

    // =====================================
    // LISTENERS
    // =====================================

    /**
     * Suscribirse a cambios de estado
     */
    subscribe(path, callback) {
        if (!this.listeners.has(path)) {
            this.listeners.set(path, new Set());
        }
        this.listeners.get(path).add(callback);
        
        // Retornar función para desuscribirse
        return () => {
            const pathListeners = this.listeners.get(path);
            if (pathListeners) {
                pathListeners.delete(callback);
                if (pathListeners.size === 0) {
                    this.listeners.delete(path);
                }
            }
        };
    }

    /**
     * Notificar a listeners de cambios
     */
    notifyListeners(path, value) {
        // Notificar listeners específicos del path
        const pathListeners = this.listeners.get(path);
        if (pathListeners) {
            pathListeners.forEach(callback => {
                try {
                    callback(value, path);
                } catch (error) {
                    console.error('Error in state listener:', error);
                }
            });
        }
        
        // Notificar listeners globales
        const globalListeners = this.listeners.get('*');
        if (globalListeners) {
            globalListeners.forEach(callback => {
                try {
                    callback(value, path);
                } catch (error) {
                    console.error('Error in global state listener:', error);
                }
            });
        }
    }

    // =====================================
    // DEBUGGING
    // =====================================

    /**
     * Obtener información de debug
     */
    getDebugInfo() {
        return {
            state: this.state,
            listeners: Array.from(this.listeners.keys()),
            performance: this.getPerformanceMetrics(),
            cacheStatus: {
                saldos: !!this.get('cache.saldos'),
                fechaSistema: !!this.get('cache.fechaSistema'),
                lastUpdate: this.get('cache.lastUpdate')
            }
        };
    }
}

// =====================================
// INSTANCIA GLOBAL
// =====================================

// Crear instancia global del state manager
window.stateManager = new StateManager();

// Exponer en window para debugging
if (window.DEBUG) {
    window.getState = () => stateManager.getState();
    window.getDebugInfo = () => stateManager.getDebugInfo();
    console.log('🔧 State Manager debug functions available: getState(), getDebugInfo()');
}

// Exportar para uso en módulos
if (typeof module !== 'undefined' && module.exports) {
    module.exports = StateManager;
}