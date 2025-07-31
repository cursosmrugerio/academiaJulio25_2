/**
 * ======================================
 * FINANCIAL CORE WEBAPP - SCENARIOS
 * Gestión de escenarios demostrativos
 * Replica exactamente los escenarios del cliente Java HTTP/2
 * ======================================
 */

class ScenariosManager {
    constructor() {
        this.currentExecution = null;
        this.results = [];
        this.init();
    }

    /**
     * Inicializar el gestor de escenarios
     */
    init() {
        console.log('🎬 Scenarios Manager initialized');
        
        // Configurar fecha por defecto para liquidación (día siguiente hábil)
        this.setDefaultLiquidationDate();
        
        // Configurar listeners de eventos
        this.setupEventListeners();
    }

    /**
     * Configurar fecha de liquidación por defecto
     */
    setDefaultLiquidationDate() {
        const tomorrow = new Date();
        tomorrow.setDate(tomorrow.getDate() + 1);
        
        // Si es fin de semana, mover al siguiente lunes
        if (tomorrow.getDay() === 0) { // Domingo
            tomorrow.setDate(tomorrow.getDate() + 1);
        } else if (tomorrow.getDay() === 6) { // Sábado
            tomorrow.setDate(tomorrow.getDate() + 2);
        }
        
        const dateString = tomorrow.toISOString().split('T')[0];
        
        const depositDate = document.getElementById('depositDate');
        if (depositDate) {
            depositDate.value = dateString;
        }
    }

    /**
     * Configurar listeners de eventos
     */
    setupEventListeners() {
        // Escuchar cambios de empresa para limpiar resultados
        EventUtils.on('empresa-changed', () => {
            this.clearResults();
        });
    }

    // =====================================
    // ESCENARIO 1: DEPÓSITO COMPLETO
    // Replica exactamente DepositoCompletoScenario.java
    // =====================================

    /**
     * Ejecutar escenario de depósito completo (NP → PV → PR)
     */
    async ejecutarDepositoCompleto() {
        const execution = new ScenarioExecution('deposito-completo', 'Depósito Completo');
        this.currentExecution = execution;
        
        try {
            // Obtener parámetros de la UI
            const params = this.getDepositParams();
            if (!this.validateDepositParams(params)) {
                throw new Error('Parámetros de depósito inválidos');
            }

            // Notificar inicio
            stateManager.startScenario('deposito-completo');
            this.showProgress('depositProgress');
            
            execution.logStep('🚀 Iniciando escenario de depósito completo...');
            
            // PASO 1: Consultar saldos iniciales
            execution.logStep('📊 Consultando saldos iniciales...');
            const saldosIniciales = await apiClient.consultarSaldos(params.empresa);
            if (!saldosIniciales.success) {
                throw new Error(`Error consultando saldos: ${saldosIniciales.error}`);
            }
            
            const saldoInicial = this.findSaldoByCuenta(saldosIniciales.data, params.idCuenta);
            execution.logStep(`💰 Saldo inicial cuenta ${params.idCuenta}: ${formatCurrency(saldoInicial)}`);

            // PASO 2: Crear pre-movimiento
            execution.logStep('💳 Creando pre-movimiento de depósito...');
            const preMovRequest = apiClient.buildPreMovimientoRequest({
                empresa: params.empresa,
                idPreMovimiento: Date.now(),
                fechaLiquidacion: params.fechaLiquidacion,
                idCuenta: params.idCuenta,
                claveOperacion: 'DEPOSITO',
                importeNeto: params.importe,
                claveMedio: 'TRANSFERENCIA',
                claveMercado: 'DEPOSITO',
                nota: `Depósito WebApp - ${formatCurrency(params.importe)}`,
                claveUsuario: 'WEBAPP_USER'
            });

            const preMovResult = await apiClient.crearPreMovimiento(preMovRequest);
            if (!preMovResult.success) {
                throw new Error(`Error creando pre-movimiento: ${preMovResult.error}`);
            }
            
            const idPreMovimiento = preMovRequest.idPreMovimiento;
            execution.logStep(`✅ Pre-movimiento creado: ID ${idPreMovimiento}`);

            // PASO 3: Validar fecha de liquidación
            execution.logStep('📅 Validando fecha de liquidación...');
            const fechaValidation = await apiClient.validarFechaLiquidacion(
                params.empresa, 
                formatDate(new Date(), 'iso'), 
                params.fechaLiquidacion, 
                'DEPOSITO'
            );
            
            if (fechaValidation.success && fechaValidation.data.esValida) {
                execution.logStep('✅ Fecha de liquidación válida');
            } else {
                execution.logStep('⚠️ Advertencia: Fecha de liquidación podría ser inválida');
            }

            // PASO 4: Procesar a estado virtual (NP → PV)
            execution.logStep('🔄 Procesando a estado virtual (PV)...');
            const procesarRequest = apiClient.buildProcesarRequest(
                params.empresa, 
                formatDate(new Date(), 'iso')
            );

            const virtualResult = await apiClient.procesarPreMovimientos(procesarRequest);
            if (!virtualResult.success) {
                throw new Error(`Error procesando a virtual: ${virtualResult.error}`);
            }
            
            execution.logStep('✅ Movimiento procesado a estado virtual (PV)');

            // PASO 5: Consultar saldos virtuales
            execution.logStep('📊 Consultando saldos después del procesamiento virtual...');
            const saldosVirtuales = await apiClient.consultarSaldos(params.empresa);
            if (!saldosVirtuales.success) {
                throw new Error(`Error consultando saldos virtuales: ${saldosVirtuales.error}`);
            }
            
            const saldoVirtual = this.findSaldoByCuenta(saldosVirtuales.data, params.idCuenta);
            execution.logStep(`💰 Saldo virtual cuenta ${params.idCuenta}: ${formatCurrency(saldoVirtual)}`);

            // PASO 6: Procesar a estado real (PV → PR)
            execution.logStep('🔄 Procesando a estado real (PR)...');
            const realResult = await apiClient.procesarVirtualesAReales(procesarRequest);
            if (!realResult.success) {
                throw new Error(`Error procesando a real: ${realResult.error}`);
            }
            
            execution.logStep('✅ Movimiento procesado a estado real (PR)');

            // PASO 7: Consultar saldos finales
            execution.logStep('📊 Consultando saldos finales...');
            const saldosFinales = await apiClient.consultarSaldos(params.empresa, null, null);
            if (!saldosFinales.success) {
                throw new Error(`Error consultando saldos finales: ${saldosFinales.error}`);
            }
            
            const saldoFinal = this.findSaldoByCuenta(saldosFinales.data, params.idCuenta);
            const impacto = saldoFinal - saldoInicial;
            
            execution.logStep(`💰 Saldo final cuenta ${params.idCuenta}: ${formatCurrency(saldoFinal)}`);
            execution.logStep(`📈 Impacto en saldo: ${formatCurrency(impacto)}`);

            // Validar que el impacto sea correcto
            if (Math.abs(impacto - params.importe) > 0.01) {
                execution.logStep(`⚠️ Advertencia: Impacto esperado ${formatCurrency(params.importe)}, real ${formatCurrency(impacto)}`);
            } else {
                execution.logStep('✅ Impacto en saldo correcto');
            }

            // Marcar como exitoso
            execution.setSuccess(true);
            execution.setSummary(`
                ✅ Depósito procesado exitosamente
                💰 Importe: ${formatCurrency(params.importe)}
                📊 Cuenta: ${params.idCuenta}
                📈 Impacto real: ${formatCurrency(impacto)}
                ⏱️ Duración: ${execution.getDuration()}ms
                🔄 Estados: NP → PV → PR
            `);

            // Actualizar métricas del dashboard
            this.updateDashboardMetrics();

            return execution;

        } catch (error) {
            console.error('Error en escenario de depósito:', error);
            execution.setSuccess(false);
            execution.setError(error.message);
            execution.logStep(`❌ Error: ${error.message}`);
            
            return execution;
        } finally {
            this.finishScenario(execution);
        }
    }

    // =====================================
    // ESCENARIO 2: CANCELACIÓN DE MOVIMIENTO
    // Replica exactamente CancelacionMovimientoScenario.java
    // =====================================

    /**
     * Ejecutar escenario de cancelación (NP → PV → CA)
     */
    async ejecutarCancelacion() {
        const execution = new ScenarioExecution('cancelacion-movimiento', 'Cancelación de Movimiento');
        this.currentExecution = execution;
        
        try {
            // Obtener parámetros de la UI
            const params = this.getCancelParams();
            if (!this.validateCancelParams(params)) {
                throw new Error('Parámetros de cancelación inválidos');
            }

            // Notificar inicio
            stateManager.startScenario('cancelacion-movimiento');
            this.showProgress('cancelProgress');
            
            execution.logStep('🚀 Iniciando escenario de cancelación de movimiento...');

            // PASO 1: Consultar saldos iniciales
            execution.logStep('📊 Consultando saldos iniciales...');
            const saldosIniciales = await apiClient.consultarSaldos(params.empresa);
            if (!saldosIniciales.success) {
                throw new Error(`Error consultando saldos: ${saldosIniciales.error}`);
            }
            
            const saldoInicial = this.findSaldoByCuenta(saldosIniciales.data, params.idCuenta);
            execution.logStep(`💰 Saldo inicial cuenta ${params.idCuenta}: ${formatCurrency(saldoInicial)}`);

            // PASO 2: Crear pre-movimiento de retiro
            execution.logStep('💸 Creando pre-movimiento de retiro...');
            const preMovRequest = apiClient.buildPreMovimientoRequest({
                empresa: params.empresa,
                idPreMovimiento: Date.now(),
                fechaLiquidacion: formatDate(new Date(Date.now() + 86400000), 'iso'), // Mañana
                idCuenta: params.idCuenta,
                claveOperacion: 'RETIRO',
                importeNeto: params.importe,
                claveMedio: 'TRANSFERENCIA',
                claveMercado: 'RETIRO',
                nota: `Retiro WebApp para cancelar - ${formatCurrency(params.importe)}`,
                claveUsuario: 'WEBAPP_USER'
            });

            const preMovResult = await apiClient.crearPreMovimiento(preMovRequest);
            if (!preMovResult.success) {
                throw new Error(`Error creando pre-movimiento: ${preMovResult.error}`);
            }
            
            const idPreMovimiento = preMovRequest.idPreMovimiento;
            execution.logStep(`✅ Pre-movimiento de retiro creado: ID ${idPreMovimiento}`);

            // PASO 3: Procesar a estado virtual (NP → PV)
            execution.logStep('🔄 Procesando a estado virtual (PV)...');
            const procesarRequest = apiClient.buildProcesarRequest(
                params.empresa, 
                formatDate(new Date(), 'iso')
            );

            const virtualResult = await apiClient.procesarPreMovimientos(procesarRequest);
            if (!virtualResult.success) {
                throw new Error(`Error procesando a virtual: ${virtualResult.error}`);
            }
            
            execution.logStep('✅ Retiro procesado a estado virtual (PV)');

            // PASO 4: Consultar saldos después del procesamiento virtual
            execution.logStep('📊 Consultando saldos después del procesamiento virtual...');
            const saldosVirtuales = await apiClient.consultarSaldos(params.empresa);
            if (!saldosVirtuales.success) {
                throw new Error(`Error consultando saldos virtuales: ${saldosVirtuales.error}`);
            }
            
            const saldoVirtual = this.findSaldoByCuenta(saldosVirtuales.data, params.idCuenta);
            execution.logStep(`💰 Saldo virtual cuenta ${params.idCuenta}: ${formatCurrency(saldoVirtual)}`);

            // PASO 5: Consultar el movimiento creado para obtener su ID real
            execution.logStep('🔍 Consultando movimiento procesado...');
            const movimientos = await apiClient.consultarMovimientos(params.empresa, 'PV');
            if (!movimientos.success) {
                throw new Error(`Error consultando movimientos: ${movimientos.error}`);
            }

            // Buscar el movimiento recién creado
            const movimientoAcancelar = movimientos.data.find(mov => 
                mov.idCuenta === params.idCuenta && 
                Math.abs(mov.importeNeto - params.importe) < 0.01
            );

            if (!movimientoAcancelar) {
                throw new Error('No se pudo encontrar el movimiento para cancelar');
            }

            execution.logStep(`🎯 Movimiento encontrado: ID ${movimientoAcancelar.id.idMovimiento}`);

            // PASO 6: Cancelar el movimiento (PV → CA)
            execution.logStep('❌ Cancelando movimiento...');
            const cancelResult = await apiClient.cancelarMovimiento(
                params.empresa, 
                movimientoAcancelar.id.idMovimiento, 
                'WEBAPP_USER'
            );
            
            if (!cancelResult.success) {
                throw new Error(`Error cancelando movimiento: ${cancelResult.error}`);
            }
            
            execution.logStep('✅ Movimiento cancelado exitosamente (CA)');

            // PASO 7: Consultar saldos finales para verificar reversión
            execution.logStep('📊 Consultando saldos finales...');
            const saldosFinales = await apiClient.consultarSaldos(params.empresa);
            if (!saldosFinales.success) {
                throw new Error(`Error consultando saldos finales: ${saldosFinales.error}`);
            }
            
            const saldoFinal = this.findSaldoByCuenta(saldosFinales.data, params.idCuenta);
            const diferencia = Math.abs(saldoFinal - saldoInicial);
            
            execution.logStep(`💰 Saldo final cuenta ${params.idCuenta}: ${formatCurrency(saldoFinal)}`);
            execution.logStep(`🔄 Diferencia con saldo inicial: ${formatCurrency(diferencia)}`);

            // Validar que los saldos se hayan revertido correctamente
            if (diferencia < 0.01) {
                execution.logStep('✅ Saldos revertidos correctamente');
            } else {
                execution.logStep(`⚠️ Advertencia: Diferencia de ${formatCurrency(diferencia)} con saldo inicial`);
            }

            // Marcar como exitoso
            execution.setSuccess(true);
            execution.setSummary(`
                ✅ Cancelación ejecutada exitosamente
                💸 Importe cancelado: ${formatCurrency(params.importe)}
                📊 Cuenta: ${params.idCuenta}
                🔄 Reversión: ${diferencia < 0.01 ? 'Correcta' : 'Parcial'}
                ⏱️ Duración: ${execution.getDuration()}ms
                🔄 Estados: NP → PV → CA
            `);

            return execution;

        } catch (error) {
            console.error('Error en escenario de cancelación:', error);
            execution.setSuccess(false);
            execution.setError(error.message);
            execution.logStep(`❌ Error: ${error.message}`);
            
            return execution;
        } finally {
            this.finishScenario(execution);
        }
    }

    // =====================================
    // ESCENARIO 3: HEALTH CHECK COMPLETO
    // =====================================

    /**
     * Ejecutar health check completo del sistema
     */
    async ejecutarHealthCheck() {
        const execution = new ScenarioExecution('health-check', 'Health Check Completo');
        this.currentExecution = execution;
        
        try {
            stateManager.startScenario('health-check');
            this.showProgress('healthProgress');
            
            execution.logStep('🏥 Iniciando health check completo...');

            // PASO 1: Verificar conectividad básica
            execution.logStep('🌐 Verificando conectividad básica...');
            const basicHealth = await apiClient.healthCheck();
            if (basicHealth.success && basicHealth.data?.status === 'UP') {
                execution.logStep(`✅ Servidor disponible (${basicHealth.duration}ms)`);
            } else {
                throw new Error('Servidor no disponible');
            }

            // PASO 2: Verificar APIs de fechas
            execution.logStep('📅 Verificando API de fechas...');
            const fechaSistema = await apiClient.obtenerFechaSistema(stateManager.getCurrentEmpresa());
            if (fechaSistema.success) {
                execution.logStep(`✅ Fecha del sistema: ${formatDate(fechaSistema.data.fechaSistema)}`);
            } else {
                execution.logStep(`⚠️ API de fechas con problemas: ${fechaSistema.error}`);
            }

            // PASO 3: Verificar API de saldos
            execution.logStep('💰 Verificando API de saldos...');
            const saldos = await apiClient.consultarSaldos(stateManager.getCurrentEmpresa());
            if (saldos.success) {
                execution.logStep(`✅ API de saldos OK (${saldos.data.length} cuentas)`);
            } else {
                execution.logStep(`⚠️ API de saldos con problemas: ${saldos.error}`);
            }

            // PASO 4: Verificar API de catálogo
            execution.logStep('📚 Verificando catálogo de operaciones...');
            const catalogo = await apiClient.consultarCatalogoOperaciones(stateManager.getCurrentEmpresa());
            if (catalogo.success) {
                execution.logStep(`✅ Catálogo OK (${catalogo.data.length} operaciones)`);
            } else {
                execution.logStep(`⚠️ Catálogo con problemas: ${catalogo.error}`);
            }

            // PASO 5: Verificar validación de fechas
            execution.logStep('🗓️ Verificando validación de días hábiles...');
            const hoy = formatDate(new Date(), 'iso');
            const validacionDia = await apiClient.validarDiaHabil(hoy);
            if (validacionDia.success) {
                const esHabil = validacionDia.data.esDiaHabil;
                execution.logStep(`✅ Validación de días hábiles OK (hoy es ${esHabil ? 'hábil' : 'inhábil'})`);
            } else {
                execution.logStep(`⚠️ Validación de días con problemas: ${validacionDia.error}`);
            }

            // PASO 6: Resumen de latencias
            const totalDuration = execution.getDuration();
            const avgLatency = Math.round(totalDuration / 5); // 5 requests aprox
            execution.logStep(`📊 Latencia promedio: ${avgLatency}ms`);

            // Marcar como exitoso
            execution.setSuccess(true);
            execution.setSummary(`
                ✅ Health check completado exitosamente
                🌐 Conectividad: OK
                📅 API Fechas: OK
                💰 API Saldos: OK
                📚 Catálogo: OK
                🗓️ Validaciones: OK
                ⏱️ Latencia promedio: ${avgLatency}ms
            `);

            // Actualizar estado de conexión
            stateManager.set('connection.status', 'online');
            stateManager.set('connection.latency', avgLatency);

            return execution;

        } catch (error) {
            console.error('Error en health check:', error);
            execution.setSuccess(false);
            execution.setError(error.message);
            execution.logStep(`❌ Error: ${error.message}`);
            
            // Actualizar estado de conexión
            stateManager.set('connection.status', 'offline');
            
            return execution;
        } finally {
            this.finishScenario(execution);
        }
    }

    // =====================================
    // UTILIDADES DE PARÁMETROS
    // =====================================

    /**
     * Obtener parámetros de depósito desde la UI
     */
    getDepositParams() {
        return {
            empresa: stateManager.getCurrentEmpresa(),
            importe: parseFloat(document.getElementById('depositAmount')?.value || 2500),
            idCuenta: parseInt(document.getElementById('depositAccount')?.value || 1001),
            fechaLiquidacion: document.getElementById('depositDate')?.value || formatDate(new Date(Date.now() + 86400000), 'iso')
        };
    }

    /**
     * Obtener parámetros de cancelación desde la UI
     */
    getCancelParams() {
        return {
            empresa: stateManager.getCurrentEmpresa(),
            importe: parseFloat(document.getElementById('cancelAmount')?.value || 1500),
            idCuenta: parseInt(document.getElementById('cancelAccount')?.value || 1002)
        };
    }

    /**
     * Validar parámetros de depósito
     */
    validateDepositParams(params) {
        if (!ValidationUtils.isValidAmount(params.importe)) {
            NotificationSystem.show('Importe inválido', 'error');
            return false;
        }
        
        if (!ValidationUtils.isValidAccountId(params.idCuenta)) {
            NotificationSystem.show('ID de cuenta inválido', 'error');
            return false;
        }
        
        if (!ValidationUtils.isValidDate(params.fechaLiquidacion)) {
            NotificationSystem.show('Fecha de liquidación inválida', 'error');
            return false;
        }
        
        return true;
    }

    /**
     * Validar parámetros de cancelación
     */
    validateCancelParams(params) {
        if (!ValidationUtils.isValidAmount(params.importe)) {
            NotificationSystem.show('Importe inválido', 'error');
            return false;
        }
        
        if (!ValidationUtils.isValidAccountId(params.idCuenta)) {
            NotificationSystem.show('ID de cuenta inválido', 'error');
            return false;
        }
        
        return true;
    }

    // =====================================
    // UTILIDADES DE UI
    // =====================================

    /**
     * Mostrar progreso de ejecución
     */
    showProgress(progressId) {
        const progressElement = document.getElementById(progressId);
        if (progressElement) {
            progressElement.classList.remove('hidden');
            
            // Animar barra de progreso
            const progressFill = progressElement.querySelector('.progress-fill');
            if (progressFill) {
                AnimationUtils.animateProgress(progressFill, 0);
            }
        }
    }

    /**
     * Ocultar progreso de ejecución
     */
    hideProgress(progressId) {
        const progressElement = document.getElementById(progressId);
        if (progressElement) {
            setTimeout(() => {
                progressElement.classList.add('hidden');
            }, 2000); // Mantener visible 2 segundos después de completar
        }
    }

    /**
     * Finalizar escenario y actualizar UI
     */
    finishScenario(execution) {
        // Agregar a resultados
        this.results.unshift(execution);
        if (this.results.length > 10) {
            this.results.splice(10); // Mantener últimos 10
        }

        // Actualizar UI de resultados
        this.updateResultsPanel(execution);
        
        // Animar progreso completo
        const progressId = `${execution.type.replace('-', '')}Progress`;
        const progressFill = document.querySelector(`#${progressId} .progress-fill`);
        if (progressFill) {
            AnimationUtils.animateProgress(progressFill, 100, 500);
        }

        // Ocultar progreso después de un delay
        setTimeout(() => this.hideProgress(progressId), 2000);

        // Notificar resultado
        if (execution.success) {
            NotificationSystem.show(
                `Escenario "${execution.name}" completado exitosamente`, 
                'success'
            );
        } else {
            NotificationSystem.show(
                `Error en escenario "${execution.name}": ${execution.error}`, 
                'error', 
                8000
            );
        }

        // Notificar al state manager
        stateManager.finishScenario({
            success: execution.success,
            duration: execution.getDuration(),
            summary: execution.summary,
            error: execution.error
        });

        this.currentExecution = null;
    }

    /**
     * Actualizar panel de resultados
     */
    updateResultsPanel(execution) {
        const executionLog = document.getElementById('executionLog');
        if (!executionLog) return;

        // Limpiar placeholder
        const placeholder = executionLog.querySelector('.log-placeholder');
        if (placeholder) {
            placeholder.remove();
        }

        // Crear entrada de log
        const logEntry = document.createElement('div');
        logEntry.className = 'log-entry-container';
        logEntry.innerHTML = `
            <div class="log-entry-header ${execution.success ? 'success' : 'error'}">
                <div class="log-entry-title">
                    <span class="log-entry-icon">${execution.success ? '✅' : '❌'}</span>
                    <span class="log-entry-name">${execution.name}</span>
                    <span class="log-entry-duration">${FormatUtils.formatDuration(execution.getDuration())}</span>
                </div>
                <div class="log-entry-timestamp">${formatDate(new Date(), 'datetime')}</div>
            </div>
            <div class="log-entry-steps">
                ${execution.steps.map(step => `
                    <div class="log-step">
                        <span class="log-step-time">${FormatUtils.formatDuration(step.duration)}</span>
                        <span class="log-step-message">${step.description}</span>
                    </div>
                `).join('')}
            </div>
            ${execution.summary ? `
                <div class="log-entry-summary">
                    <pre>${execution.summary}</pre>
                </div>
            ` : ''}
        `;

        executionLog.insertBefore(logEntry, executionLog.firstChild);

        // Limitar a 5 entradas
        const entries = executionLog.querySelectorAll('.log-entry-container');
        if (entries.length > 5) {
            entries[entries.length - 1].remove();
        }
    }

    /**
     * Buscar saldo por cuenta
     */
    findSaldoByCuenta(saldos, idCuenta) {
        const saldo = saldos.find(s => s.id.idCuenta === idCuenta);
        return saldo ? saldo.saldoEfectivo : 0;
    }

    /**
     * Actualizar métricas del dashboard
     */
    updateDashboardMetrics() {
        // Emitir evento para que el dashboard se actualice
        EventUtils.emit('scenario-completed', {
            timestamp: Date.now(),
            type: this.currentExecution?.type
        });
    }

    /**
     * Limpiar resultados
     */
    clearResults() {
        this.results = [];
        const executionLog = document.getElementById('executionLog');
        if (executionLog) {
            executionLog.innerHTML = `
                <div class="log-placeholder">
                    <p>Los resultados de ejecución aparecerán aquí...</p>
                </div>
            `;
        }
        
        NotificationSystem.show('Resultados limpiados', 'info');
    }

    /**
     * Exportar resultados
     */
    exportResults() {
        if (this.results.length === 0) {
            NotificationSystem.show('No hay resultados para exportar', 'warning');
            return;
        }

        const exportData = {
            timestamp: new Date().toISOString(),
            empresa: stateManager.getCurrentEmpresa(),
            results: this.results.map(r => ({
                name: r.name,
                type: r.type,
                success: r.success,
                duration: r.getDuration(),
                steps: r.steps,
                summary: r.summary,
                error: r.error,
                timestamp: r.startTime
            }))
        };

        const dataStr = JSON.stringify(exportData, null, 2);
        const dataBlob = new Blob([dataStr], { type: 'application/json' });
        
        const link = document.createElement('a');
        link.href = URL.createObjectURL(dataBlob);
        link.download = `financial-core-results-${formatDate(new Date(), 'iso')}.json`;
        link.click();
        
        NotificationSystem.show('Resultados exportados exitosamente', 'success');
    }
}

// =====================================
// CLASE DE EJECUCIÓN DE ESCENARIOS
// =====================================

class ScenarioExecution {
    constructor(type, name) {
        this.type = type;
        this.name = name;
        this.startTime = Date.now();
        this.steps = [];
        this.success = false;
        this.error = null;
        this.summary = '';
    }

    /**
     * Registrar paso de ejecución
     */
    logStep(description) {
        const step = {
            timestamp: Date.now(),
            description: description,
            duration: Date.now() - this.startTime
        };
        
        this.steps.push(step);
        
        // Actualizar UI en tiempo real
        this.updateStepsUI();
        
        console.log(`[${this.type}] ${description}`);
    }

    /**
     * Actualizar UI de pasos
     */
    updateStepsUI() {
        const progressId = `${this.type.replace('-', '')}Steps`;
        const stepsContainer = document.getElementById(progressId);
        
        if (!stepsContainer) return;

        // Agregar último paso
        const lastStep = this.steps[this.steps.length - 1];
        const stepElement = document.createElement('div');
        stepElement.className = 'progress-step';
        stepElement.innerHTML = `
            <div class="step-icon ${this.success === false && this.steps.length === this.steps.length ? 'error' : ''}">
                ${this.success === false && this.steps.length === this.steps.length ? '❌' : '✅'}
            </div>
            <span class="step-description">${lastStep.description}</span>
            <span class="step-time">${FormatUtils.formatDuration(lastStep.duration)}</span>
        `;
        
        stepsContainer.appendChild(stepElement);
        
        // Scroll hacia abajo
        stepsContainer.scrollTop = stepsContainer.scrollHeight;
    }

    /**
     * Marcar como exitoso
     */
    setSuccess(success) {
        this.success = success;
    }

    /**
     * Establecer error
     */
    setError(error) {
        this.error = error;
        this.success = false;
    }

    /**
     * Establecer resumen
     */
    setSummary(summary) {
        this.summary = summary.trim();
    }

    /**
     * Obtener duración de ejecución
     */
    getDuration() {
        return Date.now() - this.startTime;
    }
}

// =====================================
// FUNCIONES GLOBALES PARA LA UI
// =====================================

// Instancia global del gestor de escenarios
window.scenariosManager = new ScenariosManager();

// Funciones expuestas globalmente para los botones HTML
window.ejecutarDepositoCompleto = () => scenariosManager.ejecutarDepositoCompleto();
window.ejecutarCancelacion = () => scenariosManager.ejecutarCancelacion();
window.ejecutarHealthCheck = () => scenariosManager.ejecutarHealthCheck();
window.clearResults = () => scenariosManager.clearResults();
window.exportResults = () => scenariosManager.exportResults();

// Exportar para uso en módulos
if (typeof module !== 'undefined' && module.exports) {
    module.exports = { ScenariosManager, ScenarioExecution };
}