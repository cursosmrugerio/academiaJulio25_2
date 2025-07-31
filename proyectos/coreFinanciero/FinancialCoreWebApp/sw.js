/**
 * ======================================
 * FINANCIAL CORE WEBAPP - SERVICE WORKER
 * Service Worker para capacidades PWA
 * ======================================
 */

const CACHE_NAME = 'financial-core-v1.0.0';
const ASSETS_TO_CACHE = [
    '/',
    '/index.html',
    '/manifest.json',
    '/assets/css/main.css',
    '/assets/css/components.css',
    '/assets/css/responsive.css',
    '/assets/js/app.js',
    '/assets/js/core/api-client.js',
    '/assets/js/core/utils.js',
    '/assets/js/core/state-manager.js',
    '/assets/js/components/dashboard.js',
    '/assets/js/components/scenarios.js'
];

// URLs que siempre deben ir a la red (API calls)
const NETWORK_ONLY = [
    '/api/',
    '/actuator/'
];

// Instalación del Service Worker
self.addEventListener('install', (event) => {
    console.log('🔧 Service Worker installing...');
    
    event.waitUntil(
        caches.open(CACHE_NAME)
            .then((cache) => {
                console.log('📦 Caching app assets');
                return cache.addAll(ASSETS_TO_CACHE);
            })
            .then(() => {
                console.log('✅ Service Worker installed');
                return self.skipWaiting();
            })
            .catch((error) => {
                console.error('❌ Service Worker installation failed:', error);
            })
    );
});

// Activación del Service Worker
self.addEventListener('activate', (event) => {
    console.log('🚀 Service Worker activating...');
    
    event.waitUntil(
        caches.keys()
            .then((cacheNames) => {
                return Promise.all(
                    cacheNames.map((cacheName) => {
                        if (cacheName !== CACHE_NAME) {
                            console.log('🗑️ Deleting old cache:', cacheName);
                            return caches.delete(cacheName);
                        }
                    })
                );
            })
            .then(() => {
                console.log('✅ Service Worker activated');
                return self.clients.claim();
            })
    );
});

// Intercepción de requests
self.addEventListener('fetch', (event) => {
    const { request } = event;
    const url = new URL(request.url);
    
    // Estrategia Network Only para API calls
    if (NETWORK_ONLY.some(pattern => url.pathname.startsWith(pattern))) {
        event.respondWith(
            fetch(request)
                .catch(() => {
                    // Si falla la red, retornar una respuesta de error
                    return new Response(
                        JSON.stringify({
                            success: false,
                            error: 'Sin conexión de red',
                            offline: true
                        }),
                        {
                            status: 503,
                            statusText: 'Service Unavailable',
                            headers: { 'Content-Type': 'application/json' }
                        }
                    );
                })
        );
        return;
    }
    
    // Estrategia Cache First para assets estáticos
    if (request.method === 'GET') {
        event.respondWith(
            caches.match(request)
                .then((cachedResponse) => {
                    if (cachedResponse) {
                        // Encontrado en caché, pero también actualizar en background
                        fetchAndCache(request);
                        return cachedResponse;
                    }
                    
                    // No está en caché, intentar red
                    return fetchAndCache(request);
                })
                .catch(() => {
                    // Si todo falla, mostrar página offline para navegación
                    if (request.mode === 'navigate') {
                        return caches.match('/index.html');
                    }
                    
                    // Para otros recursos, retornar error
                    return new Response('Recurso no disponible offline', {
                        status: 404,
                        statusText: 'Not Found'
                    });
                })
        );
    }
});

/**
 * Obtener de la red y actualizar caché
 */
async function fetchAndCache(request) {
    try {
        const response = await fetch(request);
        
        // Solo cachear respuestas exitosas
        if (response.status === 200) {
            const cache = await caches.open(CACHE_NAME);
            cache.put(request, response.clone());
        }
        
        return response;
    } catch (error) {
        console.error('Fetch failed:', error);
        throw error;
    }
}

// Manejo de mensajes desde la aplicación
self.addEventListener('message', (event) => {
    if (event.data && event.data.type === 'SKIP_WAITING') {
        self.skipWaiting();
    }
    
    if (event.data && event.data.type === 'GET_CACHE_STATUS') {
        caches.keys().then((cacheNames) => {
            event.ports[0].postMessage({
                caches: cacheNames,
                currentCache: CACHE_NAME
            });
        });
    }
    
    if (event.data && event.data.type === 'CLEAR_CACHE') {
        caches.delete(CACHE_NAME).then(() => {
            event.ports[0].postMessage({ success: true });
        });
    }
});

// Manejo de sincronización en background (cuando esté disponible)
self.addEventListener('sync', (event) => {
    if (event.tag === 'background-sync') {
        console.log('🔄 Background sync triggered');
        
        event.waitUntil(
            // Aquí se pueden agregar tareas de sincronización
            Promise.resolve()
        );
    }
});

// Notificaciones push (cuando esté disponible)
self.addEventListener('push', (event) => {
    if (event.data) {
        const data = event.data.json();
        
        const options = {
            body: data.body || 'Nueva notificación',
            icon: '/assets/images/icon-192.png',
            badge: '/assets/images/icon-192.png',
            vibrate: [200, 100, 200],
            tag: data.tag || 'default',
            data: data.data || {},
            actions: data.actions || []
        };
        
        event.waitUntil(
            self.registration.showNotification(data.title || 'FinancialCore', options)
        );
    }
});

// Manejo de clics en notificaciones
self.addEventListener('notificationclick', (event) => {
    event.notification.close();
    
    const urlToOpen = event.notification.data?.url || '/';
    
    event.waitUntil(
        clients.matchAll({ type: 'window', includeUncontrolled: true })
            .then((clientList) => {
                // Buscar si ya hay una ventana abierta
                for (const client of clientList) {
                    if (client.url === urlToOpen && 'focus' in client) {
                        return client.focus();
                    }
                }
                
                // Si no hay ventana abierta, abrir nueva
                if (clients.openWindow) {
                    return clients.openWindow(urlToOpen);
                }
            })
    );
});

console.log('📱 FinancialCore Service Worker loaded');