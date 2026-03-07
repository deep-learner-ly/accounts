const API_BASE_URL = window.API_BASE_URL || 'http://localhost:8080/api';

// Helper to get token
function getToken() {
    return localStorage.getItem('token');
}

// Check if user is logged in
function checkLogin() {
    const token = getToken();
    if (!token) {
        window.location.href = 'login.html';
        return false;
    }
    return true;
}

// Generic fetch wrapper with auth header
async function apiFetch(endpoint, options = {}) {
    const token = getToken();
    const headers = {
        'Content-Type': 'application/json',
        ...(token ? { 'Authorization': `Bearer ${token}` } : {})
    };

    const response = await fetch(`${API_BASE_URL}${endpoint}`, {
        ...options,
        headers: {
            ...headers,
            ...options.headers
        }
    });

    if (response.status === 401 || response.status === 403) {
        // Token expired or invalid
        localStorage.removeItem('token');
        window.location.href = 'login.html';
        throw new Error('Unauthorized');
    }

    if (!response.ok) {
        const errorData = await response.json().catch(() => ({}));
        throw new Error(errorData.message || 'API Error');
    }

    return response.json();
}

// API Methods
async function login(phone, password) {
    const data = await apiFetch('/auth/login', {
        method: 'POST',
        body: JSON.stringify({ phone, password })
    });
    if (data.token) {
        localStorage.setItem('token', data.token);
    }
    return data;
}

async function register(phone, password) {
    const data = await apiFetch('/auth/register', {
        method: 'POST',
        body: JSON.stringify({ phone, password })
    });
    if (data.token) {
        localStorage.setItem('token', data.token);
    }
    return data;
}

async function fetchTransactions() {
    return apiFetch('/transactions');
}

async function addTransaction(transaction) {
    return apiFetch('/transactions', {
        method: 'POST',
        body: JSON.stringify(transaction)
    });
}

async function deleteTransaction(id) {
    return apiFetch(`/transactions/${id}`, {
        method: 'DELETE'
    });
}

async function fetchStats(period = 'monthly') {
    return apiFetch(`/transactions/stats?type=${period}`);
}
