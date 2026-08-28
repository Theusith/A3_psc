import { useState, useEffect } from 'react';
import { listarClientes, deletarCliente } from '../services/api';

function ListaClientes({ aoEditar }) {
    const [clientes, setClientes] = useState([]);
    const [carregando, setCarregando] = useState(true);
    const [erro, setErro] = useState(null);

    useEffect(() => {
        carregarClientes();
    }, []);

    function carregarClientes() {
        setCarregando(true);
        listarClientes()
            .then((dados) => {
                setClientes(dados);
                setCarregando(false);
            })
            .catch((err) => {
                setErro('Não foi possível carregar os clientes. A API está rodando?');
                setCarregando(false);
            });
    }

    async function handleExcluir(id) {
        const confirmar = window.confirm('Tem certeza que deseja excluir este cliente?');
        if (!confirmar) {
            return;
        }

        const sucesso = await deletarCliente(id);
        if (sucesso) {
            carregarClientes();
        } else {
            alert('Não foi possível excluir o cliente.');
        }
    }

    if (carregando) {
        return <p>Carregando...</p>;
    }

    if (erro) {
        return <p style={{ color: 'red' }}>{erro}</p>;
    }

    return (
        <div>
            <h2>Clientes cadastrados</h2>
            <ul>
                {clientes.map((cliente) => (
                    <li key={cliente.id}>
                        {cliente.nome} — {cliente.email}
                        <button onClick={() => aoEditar(cliente)}>
                            Editar
                        </button>
                        <button onClick={() => handleExcluir(cliente.id)}>
                            Excluir
                        </button>
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default ListaClientes;