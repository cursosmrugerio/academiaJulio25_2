/**
 * ======================================
 * FINANCIAL CORE WEBAPP - UTILITIES
 * Utilidades generales del sistema
 * ======================================
 */

// =====================================
// SISTEMA DE NOTIFICACIONES
// =====================================

class NotificationSystem {
    static show(message, type = 'info', duration = 5000) {
        // Usar SweetAlert2 si está disponible
        if (typeof Swal !== 'undefined') {
            Swal.fire({
                title: this.getTitle(type),
                text: message,
                icon: type === 'error' ? 'error' : type === 'success' ? 'success' : 'info',
                timer: duration,
                timerProgressBar: true,
                showConfirmButton: false,
                toast: true,
                position: 'top-end',
                background: 'white',
                color: '#374151'
            });
        } else {
            // Fallback a toast nativo
            this.showNativeToast(message, type, duration);
        }
    }

    static showNativeToast(message, type, duration) {
        const container = document.getElementById('toast-container') || this.createToastContainer();
        
        const toast = document.createElement('div');
        toast.className = `toast ${type}`;
        toast.innerHTML = `
            <div class="toast-content">
                <div class="toast-icon">${this.getIcon(type)}</div>
                <div class="toast-message">${message}</div>
                <button class="toast-close" onclick="this.parentElement.parentElement.remove()">×</button>
            </div>
        `;

        container.appendChild(toast);

        // Mostrar toast
        setTimeout(() => toast.classList.add('show'), 100);

        // Auto-remove
        setTimeout(() => {
            toast.classList.remove('show');
            setTimeout(() => toast.remove(), 300);
        }, duration);
    }

    static createToastContainer() {
        const container = document.createElement('div');
        container.id = 'toast-container';
        container.className = 'toast-container';
        document.body.appendChild(container);
        return container;
    }

    static async confirm(message, title = '¿Confirmar operación?') {
        if (typeof Swal !== 'undefined') {
            const result = await Swal.fire({
                title: title,
                text: message,
                icon: 'question',
                showCancelButton: true,
                confirmButtonText: 'Sí, continuar',
                cancelButtonText: 'Cancelar',
                confirmButtonColor: '#2563eb',
                cancelButtonColor: '#6b7280',
                background: 'white',
                color: '#374151'
            });
            
            return result.isConfirmed;
        } else {
            return confirm(`${title}\n\n${message}`);
        }
    }

    static getTitle(type) {
        const titles = {
            success: '✅ Éxito',
            error: '❌ Error',
            warning: '⚠️ Advertencia',
            info: 'ℹ️ Información'
        };
        return titles[type] || 'Notificación';
    }

    static getIcon(type) {
        const icons = {
            success: '✅',
            error: '❌',
            warning: '⚠️',
            info: 'ℹ️'
        };
        return icons[type] || 'ℹ️';
    }
}

// =====================================
// UTILIDADES DE FORMATO
// =====================================

class FormatUtils {
    /**
     * Formatear cantidad monetaria
     */
    static formatCurrency(amount, currency = 'MXN', locale = 'es-MX') {
        if (amount === null || amount === undefined) return '$0.00';
        
        return new Intl.NumberFormat(locale, {
            style: 'currency',
            currency: currency,
            minimumFractionDigits: 2,
            maximumFractionDigits: 2
        }).format(amount);
    }

    /**
     * Formatear número con separadores de miles
     */
    static formatNumber(number, decimals = 2, locale = 'es-MX') {
        if (number === null || number === undefined) return '0';
        
        return new Intl.NumberFormat(locale, {
            minimumFractionDigits: decimals,
            maximumFractionDigits: decimals
        }).format(number);
    }

    /**
     * Formatear fecha
     */
    static formatDate(date, format = 'short', locale = 'es-MX') {
        if (!date) return '';
        
        const dateObj = typeof date === 'string' ? new Date(date) : date;
        
        if (format === 'short') {
            return dateObj.toLocaleDateString(locale);
        } else if (format === 'long') {
            return dateObj.toLocaleDateString(locale, {
                weekday: 'long',
                year: 'numeric',
                month: 'long',
                day: 'numeric'
            });
        } else if (format === 'datetime') {
            return dateObj.toLocaleString(locale);
        } else if (format === 'iso') {
            return dateObj.toISOString().split('T')[0];
        }
        
        return dateObj.toLocaleDateString(locale);
    }

    /**
     * Formatear tiempo transcurrido
     */
    static formatDuration(milliseconds) {
        if (milliseconds < 1000) {
            return `${milliseconds}ms`;
        } else if (milliseconds < 60000) {
            return `${(milliseconds / 1000).toFixed(1)}s`;
        } else {
            const minutes = Math.floor(milliseconds / 60000);
            const seconds = Math.floor((milliseconds % 60000) / 1000);
            return `${minutes}m ${seconds}s`;
        }
    }

    /**
     * Formatear tamaño de archivo
     */
    static formatFileSize(bytes) {
        if (bytes === 0) return '0 Bytes';
        
        const k = 1024;
        const sizes = ['Bytes', 'KB', 'MB', 'GB'];
        const i = Math.floor(Math.log(bytes) / Math.log(k));
        
        return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i];
    }

    /**
     * Formatear estado de movimiento
     */
    static formatMovementStatus(status) {
        const statusMap = {
            'NP': { text: 'No Procesado', class: 'status-np' },
            'PV': { text: 'Procesado Virtual', class: 'status-pv' },
            'PR': { text: 'Procesado Real', class: 'status-pr' },
            'CA': { text: 'Cancelado', class: 'status-ca' }
        };
        
        return statusMap[status] || { text: status, class: 'status-unknown' };
    }
}

// =====================================
// UTILIDADES DE VALIDACIÓN
// =====================================

class ValidationUtils {
    /**
     * Validar email
     */
    static isValidEmail(email) {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(email);
    }

    /**
     * Validar fecha
     */
    static isValidDate(dateString) {
        const date = new Date(dateString);
        return date instanceof Date && !isNaN(date);
    }

    /**
     * Validar que la fecha sea futura
     */
    static isFutureDate(dateString) {
        const date = new Date(dateString);
        const today = new Date();
        today.setHours(0, 0, 0, 0);
        return date > today;
    }

    /**
     * Validar que la fecha sea día hábil (no fin de semana)
     */
    static isWeekday(dateString) {
        const date = new Date(dateString);
        const dayOfWeek = date.getDay();
        return dayOfWeek >= 1 && dayOfWeek <= 5; // Lunes a Viernes
    }

    /**
     * Validar importe monetario
     */
    static isValidAmount(amount) {
        const num = parseFloat(amount);
        return !isNaN(num) && num > 0 && num <= 999999999.99;
    }

    /**
     * Validar código de empresa
     */
    static isValidEmpresaCode(code) {
        const pattern = /^\d{3}-\d{3}$/;
        return pattern.test(code);
    }

    /**
     * Validar ID de cuenta
     */
    static isValidAccountId(id) {
        const num = parseInt(id);
        return !isNaN(num) && num > 0;
    }
}

// =====================================
// UTILIDADES DE ALMACENAMIENTO LOCAL
// =====================================

class StorageUtils {
    /**
     * Guardar en localStorage con manejo de errores
     */
    static set(key, value) {
        try {
            const serializedValue = JSON.stringify(value);
            localStorage.setItem(key, serializedValue);
            return true;
        } catch (error) {
            console.error('Error saving to localStorage:', error);
            return false;
        }
    }

    /**
     * Obtener de localStorage con manejo de errores
     */
    static get(key, defaultValue = null) {
        try {
            const item = localStorage.getItem(key);
            return item ? JSON.parse(item) : defaultValue;
        } catch (error) {
            console.error('Error reading from localStorage:', error);
            return defaultValue;
        }
    }

    /**
     * Remover de localStorage
     */
    static remove(key) {
        try {
            localStorage.removeItem(key);
            return true;
        } catch (error) {
            console.error('Error removing from localStorage:', error);
            return false;
        }
    }

    /**
     * Limpiar localStorage
     */
    static clear() {
        try {
            localStorage.clear();
            return true;
        } catch (error) {
            console.error('Error clearing localStorage:', error);
            return false;
        }
    }

    /**
     * Guardar configuración de usuario
     */
    static saveUserPreferences(preferences) {
        return this.set('financial-core-preferences', preferences);
    }

    /**
     * Obtener configuración de usuario
     */
    static getUserPreferences() {
        return this.get('financial-core-preferences', {
            theme: 'light',
            empresa: '001-001',
            language: 'es'
        });
    }
}

// =====================================
// UTILIDADES DE TEMA
// =====================================

class ThemeUtils {
    static init() {
        const savedTheme = StorageUtils.get('theme', 'light');
        this.setTheme(savedTheme);
        
        // Escuchar cambios del sistema
        if (window.matchMedia) {
            window.matchMedia('(prefers-color-scheme: dark)').addEventListener('change', (e) => {
                if (!StorageUtils.get('theme-manual', false)) {
                    this.setTheme(e.matches ? 'dark' : 'light');
                }
            });
        }
    }

    static setTheme(theme) {
        document.documentElement.setAttribute('data-theme', theme);
        StorageUtils.set('theme', theme);
        
        // Actualizar icono del botón
        const themeToggle = document.getElementById('themeToggle');
        if (themeToggle) {
            const icon = themeToggle.querySelector('.theme-icon');
            if (icon) {
                icon.textContent = theme === 'dark' ? '☀️' : '🌙';
            }
        }
    }

    static toggleTheme() {
        const currentTheme = document.documentElement.getAttribute('data-theme') || 'light';
        const newTheme = currentTheme === 'light' ? 'dark' : 'light';
        this.setTheme(newTheme);
        StorageUtils.set('theme-manual', true);
    }

    static getCurrentTheme() {
        return document.documentElement.getAttribute('data-theme') || 'light';
    }
}

// =====================================
// UTILIDADES DE ANIMACIONES
// =====================================

class AnimationUtils {
    /**
     * Animar contador numérico
     */
    static animateCounter(element, start, end, duration = 1000) {
        const startTime = performance.now();
        const range = end - start;

        const step = (currentTime) => {
            const elapsed = currentTime - startTime;
            const progress = Math.min(elapsed / duration, 1);
            
            // Easing function (ease-out)
            const easeOut = 1 - Math.pow(1 - progress, 3);
            const current = start + (range * easeOut);
            
            element.textContent = FormatUtils.formatNumber(current, 0);
            
            if (progress < 1) {
                requestAnimationFrame(step);
            } else {
                element.textContent = FormatUtils.formatNumber(end, 0);
            }
        };

        requestAnimationFrame(step);
    }

    /**
     * Animar barra de progreso
     */
    static animateProgress(element, percentage, duration = 500) {
        element.style.transition = `width ${duration}ms ease-out`;
        element.style.width = `${percentage}%`;
    }

    /**
     * Fade in element
     */
    static fadeIn(element, duration = 300) {
        element.style.opacity = '0';
        element.style.display = 'block';
        
        let start = null;
        const step = (timestamp) => {
            if (!start) start = timestamp;
            const progress = timestamp - start;
            const opacity = Math.min(progress / duration, 1);
            
            element.style.opacity = opacity;
            
            if (progress < duration) {
                requestAnimationFrame(step);
            }
        };
        
        requestAnimationFrame(step);
    }

    /**
     * Fade out element
     */
    static fadeOut(element, duration = 300) {
        let start = null;
        const step = (timestamp) => {
            if (!start) start = timestamp;
            const progress = timestamp - start;
            const opacity = Math.max(1 - (progress / duration), 0);
            
            element.style.opacity = opacity;
            
            if (progress < duration) {
                requestAnimationFrame(step);
            } else {
                element.style.display = 'none';
            }
        };
        
        requestAnimationFrame(step);
    }
}

// =====================================
// UTILIDADES DE EVENTOS
// =====================================

class EventUtils {
    /**
     * Debounce function
     */
    static debounce(func, wait, immediate = false) {
        let timeout;
        return function executedFunction(...args) {
            const later = () => {
                timeout = null;
                if (!immediate) func(...args);
            };
            const callNow = immediate && !timeout;
            clearTimeout(timeout);
            timeout = setTimeout(later, wait);
            if (callNow) func(...args);
        };
    }

    /**
     * Throttle function
     */
    static throttle(func, limit) {
        let inThrottle;
        return function(...args) {
            if (!inThrottle) {
                func.apply(this, args);
                inThrottle = true;
                setTimeout(() => inThrottle = false, limit);
            }
        };
    }

    /**
     * Emitir evento personalizado
     */
    static emit(eventName, detail = null) {
        const event = new CustomEvent(eventName, { detail });
        window.dispatchEvent(event);
    }

    /**
     * Escuchar evento personalizado
     */
    static on(eventName, callback) {
        window.addEventListener(eventName, callback);
    }

    /**
     * Remover listener de evento
     */
    static off(eventName, callback) {
        window.removeEventListener(eventName, callback);
    }
}

// =====================================
// UTILIDADES DE CLIPBOARD
// =====================================

class ClipboardUtils {
    /**
     * Copiar texto al clipboard
     */
    static async copy(text) {
        try {
            if (navigator.clipboard && window.isSecureContext) {
                await navigator.clipboard.writeText(text);
                return true;
            } else {
                // Fallback para navegadores antiguos
                const textArea = document.createElement('textarea');
                textArea.value = text;
                textArea.style.position = 'fixed';
                textArea.style.left = '-999999px';
                textArea.style.top = '-999999px';
                document.body.appendChild(textArea);
                textArea.focus();
                textArea.select();
                
                const success = document.execCommand('copy');
                textArea.remove();
                return success;
            }
        } catch (error) {
            console.error('Error copying to clipboard:', error);
            return false;
        }
    }

    /**
     * Leer del clipboard
     */
    static async read() {
        try {
            if (navigator.clipboard && window.isSecureContext) {
                return await navigator.clipboard.readText();
            }
        } catch (error) {
            console.error('Error reading from clipboard:', error);
        }
        return null;
    }
}

// =====================================
// EXPORTAR UTILIDADES GLOBALMENTE
// =====================================

window.NotificationSystem = NotificationSystem;
window.FormatUtils = FormatUtils;
window.ValidationUtils = ValidationUtils;
window.StorageUtils = StorageUtils;
window.ThemeUtils = ThemeUtils;
window.AnimationUtils = AnimationUtils;
window.EventUtils = EventUtils;
window.ClipboardUtils = ClipboardUtils;

// Inicializar tema al cargar
document.addEventListener('DOMContentLoaded', () => {
    ThemeUtils.init();
});

// Aliases para uso más sencillo
window.notify = NotificationSystem.show.bind(NotificationSystem);
window.confirm = NotificationSystem.confirm.bind(NotificationSystem);
window.formatCurrency = FormatUtils.formatCurrency.bind(FormatUtils);
window.formatDate = FormatUtils.formatDate.bind(FormatUtils);