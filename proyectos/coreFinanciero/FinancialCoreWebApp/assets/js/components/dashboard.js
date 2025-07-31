/**
 * ======================================
 * FINANCIAL CORE WEBAPP - DASHBOARD
 * Componente principal del dashboard con métricas y gráficos
 * ======================================
 */

class DashboardComponent {
    constructor() {
        this.charts = {};
        this.refreshInterval = null;
        this.lastUpdate = null;
        this.init();
    }

    /**
     * Inicializar el dashboard
     */
    init() {
        console.log('📊 Dashboard initialized');
        
        // Configurar listeners de eventos
        this.setupEventListeners();
        
        // Cargar datos iniciales
        setTimeout(() => this.loadInitialData(), 1000);
        
        // Configurar auto-refresh cada 30 segundos
        this.setupAutoRefresh();
    }

    /**
     * Configurar listeners de eventos
     */
    setupEventListeners() {
        // Escuchar cambios de empresa
        EventUtils.on('empresa-changed', () => {
            this.loadInitialData();
        });
        
        // Escuchar actividad de API
        EventUtils.on('api-activity', (event) => {
            this.updateActivityLog(event.detail);
        });
        
        // Escuchar completación de escenarios
        EventUtils.on('scenario-completed', (event) => {
            this.updateMetricsAfterScenario();
        });
        
        // Escuchar cambios de conexión
        stateManager.subscribe('connection.status', (status) => {
            this.updateSystemHealthMetric(status);
        });
    }

    /**
     * Cargar datos iniciales del dashboard
     */
    async loadInitialData() {
        try {
            this.showLoading(true);
            
            // Cargar datos en paralelo
            const [saldosData, fechaData, healthData] = await Promise.allSettled([
                this.loadSaldosData(),
                this.loadFechaData(), 
                this.loadHealthData()
            ]);
            
            // Procesar resultados
            if (saldosData.status === 'fulfilled') {
                this.updateSaldosMetrics(saldosData.value);
                this.updateBalanceChart(saldosData.value);
            }
            
            if (fechaData.status === 'fulfilled') {
                this.updateDateInfo(fechaData.value);
            }
            
            if (healthData.status === 'fulfilled') {
                this.updateSystemHealthMetric('online');
            } else {
                this.updateSystemHealthMetric('offline');
            }
            
            // Cargar métricas de actividad
            this.updateActivityMetrics();
            
            this.lastUpdate = new Date();
            
        } catch (error) {
            console.error('Error loading dashboard data:', error);
            NotificationSystem.show('Error cargando datos del dashboard', 'error');
        } finally {
            this.showLoading(false);
        }
    }

    /**
     * Cargar datos de saldos
     */
    async loadSaldosData() {
        const response = await apiClient.consultarSaldos(stateManager.getCurrentEmpresa());
        if (!response.success) {
            throw new Error(response.error);
        }
        return response.data;
    }

    /**
     * Cargar datos de fecha del sistema
     */
    async loadFechaData() {
        const response = await apiClient.obtenerFechaSistema(stateManager.getCurrentEmpresa());
        if (!response.success) {
            throw new Error(response.error);
        }
        return response.data;
    }

    /**
     * Cargar datos de health check
     */
    async loadHealthData() {
        const response = await apiClient.healthCheck();
        if (!response.success) {
            throw new Error(response.error);
        }
        return response.data;
    }

    // =====================================
    // ACTUALIZACIÓN DE MÉTRICAS
    // =====================================

    /**
     * Actualizar métricas de saldos
     */
    updateSaldosMetrics(saldos) {
        if (!saldos || saldos.length === 0) return;
        
        // Calcular saldo total
        const totalBalance = saldos.reduce((sum, saldo) => sum + saldo.saldoEfectivo, 0);
        
        // Actualizar UI
        const totalBalanceElement = document.getElementById('totalBalance');
        if (totalBalanceElement) {
            const currentValue = parseFloat(totalBalanceElement.textContent.replace(/[^0-9.-]/g, '')) || 0;
            
            if (currentValue !== totalBalance) {
                AnimationUtils.animateCounter(totalBalanceElement, currentValue, totalBalance, 1500);
                
                // Calcular cambio porcentual
                const changePercent = currentValue > 0 ? ((totalBalance - currentValue) / currentValue * 100) : 0;
                this.updateBalanceChange(changePercent);
            } else {
                totalBalanceElement.textContent = formatCurrency(totalBalance);
            }
        }
        
        // Actualizar contador de cuentas
        this.updateAccountsCount(saldos.length);
    }

    /**
     * Actualizar cambio en balance
     */
    updateBalanceChange(changePercent) {
        const balanceChangeElement = document.getElementById('balanceChange');
        if (!balanceChangeElement) return;
        
        balanceChangeElement.className = 'metric-change';
        
        if (changePercent > 0) {
            balanceChangeElement.classList.add('positive');
            balanceChangeElement.textContent = `+${changePercent.toFixed(2)}%`;
        } else if (changePercent < 0) {
            balanceChangeElement.classList.add('negative');
            balanceChangeElement.textContent = `${changePercent.toFixed(2)}%`;
        } else {
            balanceChangeElement.textContent = '0.00%';
        }
    }

    /**
     * Actualizar contador de cuentas
     */
    updateAccountsCount(count) {
        // Esta información se puede mostrar en algún lugar del dashboard
        console.log(`Dashboard: ${count} cuentas activas`);
    }

    /**
     * Actualizar métricas de movimientos de hoy
     */
    async updateMovimientosHoy() {
        try {
            const hoy = formatDate(new Date(), 'iso');
            const response = await apiClient.consultarMovimientos(
                stateManager.getCurrentEmpresa(), 
                null, 
                hoy
            );
            
            if (response.success) {
                const count = response.data.length;
                const todayMovementsElement = document.getElementById('todayMovements');
                
                if (todayMovementsElement) {
                    const currentValue = parseInt(todayMovementsElement.textContent) || 0;
                    AnimationUtils.animateCounter(todayMovementsElement, currentValue, count, 1000);
                }
                
                // Actualizar cambio
                const movementsChangeElement = document.getElementById('movementsChange');
                if (movementsChangeElement) {
                    movementsChangeElement.textContent = count > 0 ? 'Activo' : 'Sin actividad';
                }
            }
        } catch (error) {
            console.error('Error updating movements count:', error);
        }
    }

    /**
     * Actualizar métricas de pendientes
     */
    async updatePendientesCount() {
        try {
            const response = await apiClient.consultarMovimientosPendientesProcesamiento(
                stateManager.getCurrentEmpresa()
            );
            
            if (response.success) {
                const count = response.data.length;
                const pendingCountElement = document.getElementById('pendingCount');
                
                if (pendingCountElement) {
                    const currentValue = parseInt(pendingCountElement.textContent) || 0;
                    AnimationUtils.animateCounter(pendingCountElement, currentValue, count, 800);
                }
                
                // Actualizar estado
                const pendingStatusElement = document.getElementById('pendingStatus');
                if (pendingStatusElement) {
                    if (count === 0) {
                        pendingStatusElement.textContent = 'Al día';
                        pendingStatusElement.className = 'metric-status success';
                    } else if (count < 5) {
                        pendingStatusElement.textContent = 'Normal';
                        pendingStatusElement.className = 'metric-status warning';
                    } else {
                        pendingStatusElement.textContent = 'Requiere atención';
                        pendingStatusElement.className = 'metric-status danger';
                    }
                }
            }
        } catch (error) {
            console.error('Error updating pending count:', error);
        }
    }

    /**
     * Actualizar métrica de estado del sistema
     */
    updateSystemHealthMetric(status) {
        const systemHealthElement = document.getElementById('systemHealth');
        const healthStatusElement = document.getElementById('healthStatus');
        
        if (!systemHealthElement || !healthStatusElement) return;
        
        const latency = stateManager.get('connection.latency');
        
        switch (status) {
            case 'online':
                systemHealthElement.textContent = 'En línea';
                systemHealthElement.className = 'metric-value success';
                healthStatusElement.textContent = latency ? `${latency}ms` : 'OK';
                healthStatusElement.className = 'metric-status success';
                break;
            case 'offline':
                systemHealthElement.textContent = 'Sin conexión';
                systemHealthElement.className = 'metric-value danger';
                healthStatusElement.textContent = 'Error';
                healthStatusElement.className = 'metric-status danger';
                break;
            case 'checking':
                systemHealthElement.textContent = 'Verificando...';
                systemHealthElement.className = 'metric-value warning';
                healthStatusElement.textContent = '...';
                healthStatusElement.className = 'metric-status warning';
                break;
        }
    }

    /**
     * Actualizar métricas de actividad
     */
    updateActivityMetrics() {
        const metrics = stateManager.getPerformanceMetrics();
        
        // Actualizar información de requests
        console.log('Activity metrics:', metrics);
        
        // Aquí se pueden agregar más métricas visuales si se desea
    }

    /**
     * Actualizar métricas después de ejecutar un escenario
     */
    async updateMetricsAfterScenario() {
        // Esperar un poco para que los cambios se propaguen
        setTimeout(async () => {
            await this.loadSaldosData().then(data => {
                this.updateSaldosMetrics(data);
                this.updateBalanceChart(data);
            }).catch(console.error);
            
            await this.updateMovimientosHoy();
            await this.updatePendientesCount();
        }, 1000);
    }

    // =====================================
    // GRÁFICOS
    // =====================================

    /**
     * Actualizar gráfico de distribución de saldos
     */
    updateBalanceChart(saldos) {
        const canvas = document.getElementById('balanceChart');
        if (!canvas || !saldos || saldos.length === 0) return;
        
        const ctx = canvas.getContext('2d');
        
        // Destruir gráfico anterior si existe
        if (this.charts.balance) {
            this.charts.balance.destroy();
        }
        
        // Preparar datos
        const data = saldos.map(saldo => ({
            label: `Cuenta ${saldo.id.idCuenta}`,
            value: saldo.saldoEfectivo,
            currency: saldo.id.claveDivisa
        }));
        
        // Filtrar saldos positivos para el gráfico
        const positiveBalances = data.filter(item => item.value > 0);
        
        if (positiveBalances.length === 0) {
            this.showEmptyChart(canvas);
            return;
        }
        
        // Crear gráfico
        this.charts.balance = new Chart(ctx, {
            type: 'doughnut',
            data: {
                labels: positiveBalances.map(item => item.label),
                datasets: [{
                    data: positiveBalances.map(item => item.value),
                    backgroundColor: [
                        '#3b82f6', // Blue
                        '#10b981', // Green
                        '#f59e0b', // Yellow
                        '#8b5cf6', // Purple
                        '#ef4444', // Red
                        '#06b6d4', // Cyan
                        '#84cc16', // Lime
                        '#f97316'  // Orange
                    ],
                    borderWidth: 2,
                    borderColor: '#ffffff'
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: {
                        position: 'bottom',
                        labels: {
                            padding: 20,
                            usePointStyle: true,
                            font: {
                                size: 12
                            }
                        }
                    },
                    tooltip: {
                        callbacks: {
                            label: function(context) {
                                const item = positiveBalances[context.dataIndex];
                                return `${item.label}: ${formatCurrency(item.value, item.currency)}`;
                            }
                        }
                    }
                },
                animation: {
                    animateRotate: true,
                    duration: 1500
                }
            }
        });
    }

    /**
     * Mostrar gráfico vacío
     */
    showEmptyChart(canvas) {
        const ctx = canvas.getContext('2d');
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        
        ctx.fillStyle = '#9ca3af';
        ctx.font = '14px Inter, sans-serif';
        ctx.textAlign = 'center';
        ctx.textBaseline = 'middle';
        ctx.fillText('Sin datos para mostrar', canvas.width / 2, canvas.height / 2);
    }

    // =====================================
    // LOG DE ACTIVIDAD
    // =====================================

    /**
     * Actualizar log de actividad
     */
    updateActivityLog(activity) {
        const activityList = document.getElementById('activityList');
        if (!activityList) return;
        
        // Crear elemento de actividad
        const activityItem = document.createElement('div');
        activityItem.className = 'activity-item';
        
        const icon = activity.success ? '✅' : '❌';
        const statusText = activity.success ? 'exitoso' : 'falló';
        const method = activity.method || 'REQUEST';
        const endpoint = activity.endpoint || '';
        const duration = activity.duration || 0;
        
        activityItem.innerHTML = `
            <div class="activity-icon">${icon}</div>
            <div class="activity-content">
                <div class="activity-title">
                    ${method} ${endpoint.split('/').pop() || 'API'} ${statusText}
                </div>
                <div class="activity-time">
                    ${FormatUtils.formatDuration(duration)} - ${formatDate(new Date(), 'datetime')}
                </div>
            </div>
        `;
        
        // Insertar al inicio
        activityList.insertBefore(activityItem, activityList.firstChild);
        
        // Limitar a 10 elementos
        const items = activityList.querySelectorAll('.activity-item');
        if (items.length > 10) {
            items[items.length - 1].remove();
        }
        
        // Animar entrada
        activityItem.style.opacity = '0';
        activityItem.style.transform = 'translateX(-20px)';
        
        setTimeout(() => {
            activityItem.style.transition = 'all 0.3s ease';
            activityItem.style.opacity = '1';
            activityItem.style.transform = 'translateX(0)';
        }, 50);
    }

    /**
     * Actualizar información de fecha
     */
    updateDateInfo(fechaData) {
        if (!fechaData) return;
        
        // Actualizar información de fecha en el dashboard
        // Esto se puede expandir según las necesidades
        console.log('Fecha del sistema:', fechaData);
    }

    // =====================================
    // AUTO-REFRESH Y UTILIDADES
    // =====================================

    /**
     * Configurar auto-refresh
     */
    setupAutoRefresh() {
        // Refresh cada 30 segundos solo si la vista actual es dashboard
        this.refreshInterval = setInterval(() => {
            if (stateManager.get('ui.currentView') === 'dashboard') {
                this.refreshData();
            }
        }, 30000);
    }

    /**
     * Refresh datos sin recargar completamente
     */
    async refreshData() {
        try {
            // Solo refrescar datos críticos
            const saldosData = await this.loadSaldosData();
            this.updateSaldosMetrics(saldosData);
            
            await this.updateMovimientosHoy();
            await this.updatePendientesCount();
            
            this.lastUpdate = new Date();
            
        } catch (error) {
            console.error('Error refreshing dashboard data:', error);
        }
    }

    /**
     * Mostrar/ocultar loading
     */
    showLoading(show) {
        const loadingElements = document.querySelectorAll('.dashboard-loading');
        loadingElements.forEach(element => {
            element.style.display = show ? 'block' : 'none';
        });
    }

    /**
     * Destruir el dashboard
     */
    destroy() {
        // Limpiar interval
        if (this.refreshInterval) {
            clearInterval(this.refreshInterval);
        }
        
        // Destruir gráficos
        Object.values(this.charts).forEach(chart => {
            if (chart && typeof chart.destroy === 'function') {
                chart.destroy();
            }
        });
        
        this.charts = {};
    }

    /**
     * Obtener estadísticas del dashboard
     */
    getStats() {
        return {
            lastUpdate: this.lastUpdate,
            chartsCount: Object.keys(this.charts).length,
            refreshInterval: this.refreshInterval ? 30000 : 0
        };
    }
}

// =====================================
// FUNCIONES GLOBALES
// =====================================

// Instancia global del dashboard
window.dashboardComponent = new DashboardComponent();

// Función para refrescar manualmente
window.refreshDashboard = () => {
    dashboardComponent.loadInitialData();
    NotificationSystem.show('Dashboard actualizado', 'success');
};

// Función para obtener estadísticas
window.getDashboardStats = () => dashboardComponent.getStats();

// Cleanup al salir
window.addEventListener('beforeunload', () => {
    if (window.dashboardComponent) {
        dashboardComponent.destroy();
    }
});

// Exportar para uso en módulos
if (typeof module !== 'undefined' && module.exports) {
    module.exports = DashboardComponent;
}