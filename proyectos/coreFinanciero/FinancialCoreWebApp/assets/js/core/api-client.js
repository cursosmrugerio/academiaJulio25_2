/**
 * ======================================
 * FINANCIAL CORE API CLIENT
 * Cliente HTTP REST que replica la funcionalidad del cliente Java HTTP/2
 * ======================================
 */

class FinancialCoreApiClient {
    constructor(baseUrl = 'http://localhost:8080') {
        this.baseUrl = baseUrl;
        this.defaultHeaders = {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'User-Agent': 'FinancialCore-WebApp/1.0.0'
        };
        this.timeout = 30000; // 30 seconds
        this.retryAttempts = 3;
        this.retryDelay = 1000; // 1 second
    }

    /**
     * Método genérico para realizar requests HTTP
     * @param {string} endpoint - Endpoint de la API
     * @param {Object} options - Opciones del request
     * @returns {Promise<ApiResponse>}
     */
    async request(endpoint, options = {}) {
        const url = `${this.baseUrl}${endpoint}`;
        const config = {
            method: options.method || 'GET',
            headers: { ...this.defaultHeaders, ...options.headers },
            signal: AbortSignal.timeout(this.timeout),
            ...options
        };

        // Agregar body para métodos que no sean GET
        if (config.method !== 'GET' && options.body) {
            config.body = JSON.stringify(options.body);
        }

        // Log del request (solo en desarrollo)
        if (window.DEBUG) {
            console.log(`🌐 ${config.method} ${url}`, options.body || '');
        }

        let lastError;
        
        // Retry logic
        for (let attempt = 1; attempt <= this.retryAttempts; attempt++) {
            try {
                const startTime = performance.now();
                const response = await fetch(url, config);
                const endTime = performance.now();
                const duration = Math.round(endTime - startTime);

                // Parse response
                let data;
                const contentType = response.headers.get('content-type');
                
                if (contentType && contentType.includes('application/json')) {
                    data = await response.json();
                } else {
                    data = await response.text();
                }

                const result = {
                    success: response.ok,
                    status: response.status,
                    statusText: response.statusText,
                    data: data,
                    duration: duration,
                    headers: Object.fromEntries(response.headers.entries()),
                    error: response.ok ? null : this.extractErrorMessage(data)
                };

                // Log de la respuesta
                if (window.DEBUG) {
                    console.log(`📡 Response ${response.status} (${duration}ms)`, result);
                }

                // Agregar actividad al log
                this.logActivity(config.method, endpoint, response.status, duration, response.ok);

                return result;

            } catch (error) {
                lastError = error;
                
                // Si es timeout o error de red, reintentar
                if (attempt < this.retryAttempts && this.isRetryableError(error)) {
                    console.warn(`⚠️ Request failed (attempt ${attempt}/${this.retryAttempts}), retrying...`, error.message);
                    await this.delay(this.retryDelay * attempt);
                    continue;
                }
                
                // Log del error
                console.error(`❌ Request failed after ${attempt} attempts:`, error);
                
                return {
                    success: false,
                    status: 0,
                    statusText: 'Network Error',
                    data: null,
                    duration: 0,
                    headers: {},
                    error: this.getErrorMessage(error)
                };
            }
        }
    }

    /**
     * Verifica si un error es reintentable
     */
    isRetryableError(error) {
        return error.name === 'AbortError' || 
               error.name === 'TypeError' || 
               error.message.includes('fetch');
    }

    /**
     * Extrae mensaje de error de la respuesta
     */
    extractErrorMessage(data) {
        if (typeof data === 'string') return data;
        if (data?.mensaje) return data.mensaje;
        if (data?.message) return data.message;
        if (data?.error) return data.error;
        return 'Error desconocido';
    }

    /**
     * Obtiene mensaje de error amigable
     */
    getErrorMessage(error) {
        if (error.name === 'AbortError') {
            return 'Timeout: La operación tardó demasiado tiempo';
        }
        if (error.name === 'TypeError') {
            return 'Error de conexión: Verifique que el servidor esté disponible';
        }
        return error.message || 'Error desconocido';
    }

    /**
     * Registra actividad en el sistema
     */
    logActivity(method, endpoint, status, duration, success) {
        const activity = {
            timestamp: new Date().toISOString(),
            method,
            endpoint,
            status,
            duration,
            success
        };

        // Emitir evento para el dashboard
        window.dispatchEvent(new CustomEvent('api-activity', { detail: activity }));
    }

    /**
     * Delay utility para retries
     */
    async delay(ms) {
        return new Promise(resolve => setTimeout(resolve, ms));
    }

    // =====================================
    // MOVIMIENTOS API
    // =====================================

    /**
     * Crear pre-movimiento
     */
    async crearPreMovimiento(request) {
        return this.request('/api/v1/movimientos/pre-movimiento', {
            method: 'POST',
            body: request
        });
    }

    /**
     * Agregar detalle a pre-movimiento
     */
    async crearPreMovimientoDetalle(request) {
        return this.request('/api/v1/movimientos/pre-movimiento-detalle', {
            method: 'POST',
            body: request
        });
    }

    /**
     * Obtener pre-movimiento específico
     */
    async obtenerPreMovimiento(empresa, idPreMovimiento) {
        const [grupoEmpresa, claveEmpresa] = empresa.split('-');
        return this.request(
            `/api/v1/movimientos/pre-movimiento/${grupoEmpresa}/${claveEmpresa}/${idPreMovimiento}`
        );
    }

    /**
     * Obtener movimientos pendientes
     */
    async obtenerMovimientosPendientes(empresa, fechaLiquidacion) {
        const [grupoEmpresa, claveEmpresa] = empresa.split('-');
        const params = new URLSearchParams({
            claveGrupoEmpresa: grupoEmpresa,
            claveEmpresa: claveEmpresa,
            fechaLiquidacion: fechaLiquidacion
        });
        
        return this.request(`/api/v1/movimientos/pendientes?${params.toString()}`);
    }

    /**
     * Procesar movimientos pendientes
     */
    async procesarMovimientosPendientes(empresa) {
        const [grupoEmpresa, claveEmpresa] = empresa.split('-');
        const params = new URLSearchParams({
            claveGrupoEmpresa: grupoEmpresa,
            claveEmpresa: claveEmpresa
        });
        
        return this.request(`/api/v1/movimientos/procesar-pendientes?${params.toString()}`, {
            method: 'POST'
        });
    }

    /**
     * Obtener detalles de pre-movimiento
     */
    async obtenerDetallesPreMovimiento(empresa, idPreMovimiento) {
        const [grupoEmpresa, claveEmpresa] = empresa.split('-');
        return this.request(
            `/api/v1/movimientos/detalles/${grupoEmpresa}/${claveEmpresa}/${idPreMovimiento}`
        );
    }

    /**
     * Calcular total de conceptos
     */
    async calcularTotalConceptos(empresa, idPreMovimiento) {
        const [grupoEmpresa, claveEmpresa] = empresa.split('-');
        return this.request(
            `/api/v1/movimientos/total-conceptos/${grupoEmpresa}/${claveEmpresa}/${idPreMovimiento}`
        );
    }

    // =====================================
    // PROCESAMIENTO API
    // =====================================

    /**
     * Procesar pre-movimientos (NP → PV)
     */
    async procesarPreMovimientos(request) {
        return this.request('/api/v1/movimientos/procesar-pre-movimientos', {
            method: 'POST',
            body: request
        });
    }

    /**
     * Procesar movimientos virtuales a reales (PV → PR)
     */
    async procesarVirtualesAReales(request) {
        return this.request('/api/v1/movimientos/procesar-virtuales-a-reales', {
            method: 'POST',
            body: request
        });
    }

    /**
     * Cancelar movimiento (→ CA)
     */
    async cancelarMovimiento(empresa, idMovimiento, usuario) {
        const [grupoEmpresa, claveEmpresa] = empresa.split('-');
        const params = new URLSearchParams({ claveUsuario: usuario });
        
        return this.request(
            `/api/v1/movimientos/${grupoEmpresa}/${claveEmpresa}/${idMovimiento}/cancelar?${params.toString()}`,
            { method: 'POST' }
        );
    }

    /**
     * Consultar movimientos por empresa
     */
    async consultarMovimientos(empresa, situacion = null, fechaOperacion = null) {
        const [grupoEmpresa, claveEmpresa] = empresa.split('-');
        let url = `/api/v1/movimientos/${grupoEmpresa}/${claveEmpresa}`;
        
        const params = new URLSearchParams();
        if (situacion) params.append('situacion', situacion);
        if (fechaOperacion) params.append('fechaOperacion', fechaOperacion);
        
        if (params.toString()) url += `?${params.toString()}`;
        
        return this.request(url);
    }

    /**
     * Consultar movimiento específico
     */
    async consultarMovimiento(empresa, idMovimiento) {
        const [grupoEmpresa, claveEmpresa] = empresa.split('-');
        return this.request(`/api/v1/movimientos/${grupoEmpresa}/${claveEmpresa}/${idMovimiento}`);
    }

    /**
     * Consultar movimientos pendientes de procesamiento
     */
    async consultarMovimientosPendientesProcesamiento(empresa) {
        const [grupoEmpresa, claveEmpresa] = empresa.split('-');
        return this.request(`/api/v1/movimientos/pendientes-procesamiento/${grupoEmpresa}/${claveEmpresa}`);
    }

    // =====================================
    // SALDOS API
    // =====================================

    /**
     * Consultar saldos por empresa
     */
    async consultarSaldos(empresa, fechaFoto = null, idCuenta = null) {
        const [grupoEmpresa, claveEmpresa] = empresa.split('-');
        let url = `/api/v1/movimientos/saldos/${grupoEmpresa}/${claveEmpresa}`;
        
        const params = new URLSearchParams();
        if (fechaFoto) params.append('fechaFoto', fechaFoto);
        if (idCuenta) params.append('idCuenta', idCuenta);
        
        if (params.toString()) url += `?${params.toString()}`;
        
        return this.request(url);
    }

    // =====================================
    // CATÁLOGO API
    // =====================================

    /**
     * Consultar catálogo de operaciones
     */
    async consultarCatalogoOperaciones(empresa, estatus = null) {
        const [grupoEmpresa, claveEmpresa] = empresa.split('-');
        let url = `/api/v1/movimientos/catalogo-operaciones/${grupoEmpresa}/${claveEmpresa}`;
        
        if (estatus) {
            url += `?estatus=${estatus}`;
        }
        
        return this.request(url);
    }

    // =====================================
    // FECHAS API
    // =====================================

    /**
     * Obtener fecha del sistema
     */
    async obtenerFechaSistema(empresa) {
        const [grupoEmpresa, claveEmpresa] = empresa.split('-');
        const params = new URLSearchParams({
            claveGrupoEmpresa: grupoEmpresa,
            claveEmpresa: claveEmpresa
        });
        
        return this.request(`/api/v1/fechas/sistema?${params.toString()}`);
    }

    /**
     * Recorrer fecha del sistema
     */
    async recorrerFecha(empresa) {
        const [grupoEmpresa, claveEmpresa] = empresa.split('-');
        const params = new URLSearchParams({
            claveGrupoEmpresa: grupoEmpresa,
            claveEmpresa: claveEmpresa
        });
        
        return this.request(`/api/v1/fechas/recorrer?${params.toString()}`, {
            method: 'POST'
        });
    }

    /**
     * Actualizar fecha del sistema
     */
    async actualizarFechaSistema(empresa, nuevaFecha) {
        const [grupoEmpresa, claveEmpresa] = empresa.split('-');
        const params = new URLSearchParams({
            claveGrupoEmpresa: grupoEmpresa,
            claveEmpresa: claveEmpresa,
            nuevaFecha: nuevaFecha
        });
        
        return this.request(`/api/v1/fechas/sistema?${params.toString()}`, {
            method: 'PUT'
        });
    }

    /**
     * Validar día hábil
     */
    async validarDiaHabil(fecha) {
        const params = new URLSearchParams({ fecha: fecha });
        return this.request(`/api/v1/fechas/validar-dia-habil?${params.toString()}`);
    }

    // =====================================
    // LIQUIDACIÓN API
    // =====================================

    /**
     * Crear fechas de liquidación del año
     */
    async crearFechasLiquidacionAnio(empresa, anio) {
        const [grupoEmpresa, claveEmpresa] = empresa.split('-');
        const params = new URLSearchParams({
            claveGrupoEmpresa: grupoEmpresa,
            claveEmpresa: claveEmpresa,
            anio: anio.toString()
        });
        
        return this.request(`/api/v1/liquidacion/crear-fechas-anio?${params.toString()}`, {
            method: 'POST'
        });
    }

    /**
     * Validar fecha de liquidación
     */
    async validarFechaLiquidacion(empresa, fechaOperacion, fechaLiquidacion, claveMercado) {
        const [grupoEmpresa, claveEmpresa] = empresa.split('-');
        const params = new URLSearchParams({
            claveGrupoEmpresa: grupoEmpresa,
            claveEmpresa: claveEmpresa,
            fechaOperacion: fechaOperacion,
            fechaLiquidacion: fechaLiquidacion,
            claveMercado: claveMercado
        });
        
        return this.request(`/api/v1/liquidacion/validar-fecha?${params.toString()}`);
    }

    // =====================================
    // HEALTH CHECK API
    // =====================================

    /**
     * Health check del sistema
     */
    async healthCheck() {
        return this.request('/actuator/health');
    }

    /**
     * Health check detallado
     */
    async healthCheckDetailed() {
        return this.request('/actuator/health/detail');
    }

    /**
     * Métricas del sistema
     */
    async getMetrics() {
        return this.request('/actuator/metrics');
    }

    /**
     * Info de la aplicación
     */
    async getInfo() {
        return this.request('/actuator/info');
    }

    // =====================================
    // UTILIDADES
    // =====================================

    /**
     * Verificar conectividad básica
     */
    async isHealthy() {
        try {
            const response = await this.healthCheck();
            return response.success && response.data?.status === 'UP';
        } catch (error) {
            return false;
        }
    }

    /**
     * Construir request para pre-movimiento
     */
    buildPreMovimientoRequest(params) {
        const [grupoEmpresa, claveEmpresa] = params.empresa.split('-');
        
        return {
            claveGrupoEmpresa: grupoEmpresa,
            claveEmpresa: claveEmpresa,
            idPreMovimiento: params.idPreMovimiento || Date.now(),
            fechaLiquidacion: params.fechaLiquidacion,
            idCuenta: params.idCuenta,
            idPrestamo: params.idPrestamo || null,
            claveDivisa: params.claveDivisa || 'MXN',
            claveOperacion: params.claveOperacion,
            importeNeto: params.importeNeto,
            claveMedio: params.claveMedio || 'TRANSFERENCIA',
            claveMercado: params.claveMercado || params.claveOperacion,
            nota: params.nota || '',
            idGrupo: params.idGrupo || null,
            claveUsuario: params.claveUsuario || 'WEBAPP_USER',
            fechaValor: params.fechaValor || null,
            numeroPagoAmortizacion: params.numeroPagoAmortizacion || null
        };
    }

    /**
     * Construir request para procesamiento
     */
    buildProcesarRequest(empresa, fechaProceso, usuario = 'WEBAPP_USER') {
        const [grupoEmpresa, claveEmpresa] = empresa.split('-');
        
        return {
            claveGrupoEmpresa: grupoEmpresa,
            claveEmpresa: claveEmpresa,
            fechaProceso: fechaProceso,
            claveUsuario: usuario
        };
    }

    /**
     * Formatear respuesta para mostrar
     */
    formatResponse(response) {
        return {
            success: response.success,
            message: response.error || 'Operación exitosa',
            data: response.data,
            duration: response.duration,
            status: response.status
        };
    }
}

// =====================================
// INSTANCIA GLOBAL
// =====================================

// Crear instancia global del cliente API
window.apiClient = new FinancialCoreApiClient();

// Habilitar debug en desarrollo
if (window.location.hostname === 'localhost' || window.location.hostname === '127.0.0.1') {
    window.DEBUG = true;
    console.log('🔧 Debug mode enabled');
}

// Exportar para uso en módulos
if (typeof module !== 'undefined' && module.exports) {
    module.exports = FinancialCoreApiClient;
}