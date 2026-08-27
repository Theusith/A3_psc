import { useState } from 'react';
import ListaClientes from './components/ListaClientes';
import FormularioCliente from './components/FormularioCliente';

function App() {
    const [chaveLista, setChaveLista] = useState(0);

    function recarregarLista() {
        setChaveLista((anterior) => anterior + 1);
    }

    return (
        <div>
            <h1>Sistema de Reservas</h1>
            <FormularioCliente aoCadastrar={recarregarLista} />
            <ListaClientes key={chaveLista} />
        </div>
    )
}

export default App