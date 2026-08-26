import { useState, useEffect } from 'react';
import { listarClientes } from '../services/api';

function ListaClientes() {
    const [clientes, setClientes] = useState([]);
    const [carregando, setCarregando] = useState(true);
    const [erro, setErro] = useState(null);

    useEffect(() => {
        listarClientes()
            .then((dados) => {
                setClientes(dados);
                setCarregando(false);
            })
            .catch((err) => {
                setErro('Não foi possível carregar os clientes. A API está rodando?');
                setCarregando(false);
            });
    }, []);

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
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default ListaClientes;