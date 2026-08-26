const BASE_URL = 'http://localhost:8080';

export async function listarClientes() {
    const response = await fetch(`${BASE_URL}/contas`);
    return response.json();
}

export async function cadastrarCliente(cliente) {
    const response = await fetch(`${BASE_URL}/contas`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(cliente),
    });
    return response.json();
}