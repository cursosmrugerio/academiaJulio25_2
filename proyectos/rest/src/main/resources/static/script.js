// API Base URL
const API_BASE_URL = 'http://localhost:8080/api/clientes';

// DOM Elements
const clienteForm = document.getElementById('cliente-form');
const formTitle = document.getElementById('form-title');
const submitBtn = document.getElementById('submit-btn');
const cancelBtn = document.getElementById('cancel-btn');
const clienteIdInput = document.getElementById('cliente-id');
const nombreInput = document.getElementById('nombre');
const emailInput = document.getElementById('email');
const telefonoInput = document.getElementById('telefono');
const searchInput = document.getElementById('search-input');
const searchBtn = document.getElementById('search-btn');
const loadAllBtn = document.getElementById('load-all-btn');
const clientesTbody = document.getElementById('clientes-tbody');
const confirmModal = document.getElementById('confirm-modal');
const confirmDeleteBtn = document.getElementById('confirm-delete-btn');
const cancelDeleteBtn = document.getElementById('cancel-delete-btn');
const messageArea = document.getElementById('message-area');

// Global variables
let editingClienteId = null;
let clienteToDelete = null;

// Initialize application
document.addEventListener('DOMContentLoaded', function() {
    console.log('DOM loaded, initializing application...');
    setupEventListeners();
    loadAllClientes();
});

// Event Listeners
function setupEventListeners() {
    console.log('Setting up event listeners...');
    
    if (clienteForm) {
        clienteForm.addEventListener('submit', handleFormSubmit);
        console.log('Form submit listener added');
    }
    
    if (cancelBtn) {
        cancelBtn.addEventListener('click', cancelEdit);
        console.log('Cancel button listener added');
    }
    
    if (searchBtn) {
        searchBtn.addEventListener('click', handleSearch);
        console.log('Search button listener added');
    }
    
    if (loadAllBtn) {
        loadAllBtn.addEventListener('click', loadAllClientes);
        console.log('Load all button listener added');
    }
    
    if (searchInput) {
        searchInput.addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                handleSearch();
            }
        });
    }
    
    if (confirmDeleteBtn) {
        confirmDeleteBtn.addEventListener('click', confirmDelete);
    }
    
    if (cancelDeleteBtn) {
        cancelDeleteBtn.addEventListener('click', closeConfirmModal);
    }
    
    // Close modal when clicking outside
    if (confirmModal) {
        confirmModal.addEventListener('click', function(e) {
            if (e.target === confirmModal) {
                closeConfirmModal();
            }
        });
    }
}

// API Functions
async function apiRequest(url, options = {}) {
    try {
        const response = await fetch(url, {
            headers: {
                'Content-Type': 'application/json',
                ...options.headers
            },
            ...options
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || `HTTP error! status: ${response.status}`);
        }

        const contentType = response.headers.get('content-type');
        if (contentType && contentType.includes('application/json')) {
            return await response.json();
        } else {
            return await response.text();
        }
    } catch (error) {
        console.error('API Request Error:', error);
        throw error;
    }
}

async function getAllClientes() {
    return await apiRequest(API_BASE_URL);
}

async function getClienteById(id) {
    return await apiRequest(`${API_BASE_URL}/${id}`);
}

async function createCliente(cliente) {
    return await apiRequest(API_BASE_URL, {
        method: 'POST',
        body: JSON.stringify(cliente)
    });
}

async function updateCliente(id, cliente) {
    return await apiRequest(`${API_BASE_URL}/${id}`, {
        method: 'PUT',
        body: JSON.stringify(cliente)
    });
}

async function deleteCliente(id) {
    return await apiRequest(`${API_BASE_URL}/${id}`, {
        method: 'DELETE'
    });
}

async function searchClientesByNombre(nombre) {
    return await apiRequest(`${API_BASE_URL}/nombre/${encodeURIComponent(nombre)}`);
}

// UI Functions
function showMessage(message, type = 'info') {
    const messageDiv = document.createElement('div');
    messageDiv.className = `message ${type}`;
    messageDiv.textContent = message;
    
    messageArea.appendChild(messageDiv);
    
    setTimeout(() => {
        messageDiv.remove();
    }, 5000);
}

function clearForm() {
    clienteForm.reset();
    clienteIdInput.value = '';
    editingClienteId = null;
    formTitle.textContent = 'Agregar Cliente';
    submitBtn.textContent = 'Guardar Cliente';
    cancelBtn.style.display = 'none';
}

function populateForm(cliente) {
    clienteIdInput.value = cliente.id;
    nombreInput.value = cliente.nombre;
    emailInput.value = cliente.email;
    telefonoInput.value = cliente.telefono || '';
    editingClienteId = cliente.id;
    formTitle.textContent = 'Editar Cliente';
    submitBtn.textContent = 'Actualizar Cliente';
    cancelBtn.style.display = 'inline-block';
}

function formatDate(dateString) {
    if (!dateString) return 'N/A';
    const date = new Date(dateString);
    return date.toLocaleDateString('es-ES', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit'
    });
}

function renderClientesTable(clientes) {
    clientesTbody.innerHTML = '';
    
    if (!clientes || clientes.length === 0) {
        clientesTbody.innerHTML = `
            <tr>
                <td colspan="6" style="text-align: center; color: #666;">
                    No se encontraron clientes
                </td>
            </tr>
        `;
        return;
    }

    clientes.forEach(cliente => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${cliente.id}</td>
            <td>${cliente.nombre}</td>
            <td>${cliente.email}</td>
            <td>${cliente.telefono || 'N/A'}</td>
            <td>${formatDate(cliente.fechaRegistro)}</td>
            <td>
                <div class="action-buttons">
                    <button class="btn-warning" onclick="editCliente(${cliente.id})">
                        Editar
                    </button>
                    <button class="btn-danger" onclick="showDeleteConfirm(${cliente.id})">
                        Eliminar
                    </button>
                </div>
            </td>
        `;
        clientesTbody.appendChild(row);
    });
}

// Event Handlers
async function handleFormSubmit(e) {
    e.preventDefault();
    
    const formData = new FormData(clienteForm);
    const cliente = {
        nombre: formData.get('nombre').trim(),
        email: formData.get('email').trim(),
        telefono: formData.get('telefono').trim()
    };

    // Basic validation
    if (!cliente.nombre || !cliente.email) {
        showMessage('Nombre y email son requeridos', 'error');
        return;
    }

    try {
        if (editingClienteId) {
            await updateCliente(editingClienteId, cliente);
            showMessage('Cliente actualizado exitosamente', 'success');
        } else {
            await createCliente(cliente);
            showMessage('Cliente creado exitosamente', 'success');
        }
        
        clearForm();
        loadAllClientes();
    } catch (error) {
        showMessage(error.message, 'error');
    }
}

function cancelEdit() {
    clearForm();
}

async function handleSearch() {
    const searchTerm = searchInput.value.trim();
    
    if (!searchTerm) {
        showMessage('Ingrese un término de búsqueda', 'error');
        return;
    }

    try {
        const clientes = await searchClientesByNombre(searchTerm);
        renderClientesTable(clientes);
        showMessage(`Se encontraron ${clientes.length} resultado(s)`, 'info');
    } catch (error) {
        showMessage('Error al buscar clientes: ' + error.message, 'error');
        renderClientesTable([]);
    }
}

async function loadAllClientes() {
    console.log('Loading all clientes...');
    try {
        const clientes = await getAllClientes();
        console.log('Clientes loaded:', clientes);
        renderClientesTable(clientes);
        if (searchInput) searchInput.value = '';
    } catch (error) {
        console.error('Error loading clientes:', error);
        showMessage('Error al cargar clientes: ' + error.message, 'error');
        renderClientesTable([]);
    }
}

async function editCliente(id) {
    try {
        const cliente = await getClienteById(id);
        populateForm(cliente);
        
        // Scroll to form
        document.querySelector('.form-section').scrollIntoView({ 
            behavior: 'smooth' 
        });
    } catch (error) {
        showMessage('Error al cargar cliente: ' + error.message, 'error');
    }
}

function showDeleteConfirm(id) {
    clienteToDelete = id;
    confirmModal.style.display = 'block';
}

function closeConfirmModal() {
    confirmModal.style.display = 'none';
    clienteToDelete = null;
}

async function confirmDelete() {
    if (!clienteToDelete) return;

    try {
        await deleteCliente(clienteToDelete);
        showMessage('Cliente eliminado exitosamente', 'success');
        loadAllClientes();
        
        // If we were editing this client, clear the form
        if (editingClienteId == clienteToDelete) {
            clearForm();
        }
    } catch (error) {
        showMessage('Error al eliminar cliente: ' + error.message, 'error');
    } finally {
        closeConfirmModal();
    }
}

// Make functions available globally for onclick handlers
window.editCliente = editCliente;
window.showDeleteConfirm = showDeleteConfirm;