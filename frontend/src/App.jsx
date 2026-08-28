import { useState } from 'react';
import ListaClientes from './components/ListaClientes';
import FormularioCliente from './components/FormularioCliente';

function App() {
    const [chaveLista, setChaveLista] = useState(0);
    const [clienteEditando, setClienteEditando] = useState(null);

    function recarregarLista() {
        setChaveLista((anterior) => anterior + 1);
        setClienteEditando(null);
    }

    function iniciarEdicao(cliente) {
        setClienteEditando(cliente);
    }

    function cancelarEdicao() {
        setClienteEditando(null);
    }

    return (
        <div>
            <h1>Sistema de Reservas</h1>
            <FormularioCliente
                clienteParaEditar={clienteEditando}
                aoSalvar={recarregarLista}
                aoCancelar={cancelarEdicao}
            />
            <ListaClientes key={chaveLista} aoEditar={iniciarEdicao} />
        </div>
    )
}

export default App