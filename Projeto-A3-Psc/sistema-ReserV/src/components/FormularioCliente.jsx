import { useState } from 'react';
import { cadastrarCliente } from '../services/api';

function FormularioCliente({ aoCadastrar }) {
    const [formulario, setFormulario] = useState({
        nome: '',
        cpf: '',
        email: '',
        senha: '',
    });
    const [enviando, setEnviando] = useState(false);
    const [mensagem, setMensagem] = useState(null);

    function atualizarCampo(evento) {
        const { name, value } = evento.target;
        setFormulario((anterior) => ({
            ...anterior,
            [name]: value,
        }));
    }

    async function enviarFormulario(evento) {
        evento.preventDefault();
        setEnviando(true);
        setMensagem(null);

        try {
            await cadastrarCliente(formulario);
            setMensagem({ tipo: 'sucesso', texto: 'Cliente cadastrado com sucesso!' });
            setFormulario({ nome: '', cpf: '', email: '', senha: '' });
            if (aoCadastrar) {
                aoCadastrar();
            }
        } catch (erro) {
            setMensagem({ tipo: 'erro', texto: 'Não foi possível cadastrar o cliente.' });
        } finally {
            setEnviando(false);
        }
    }

    return (
        <form onSubmit={enviarFormulario}>
            <h2>Cadastrar cliente</h2>

            <div>
                <label>Nome</label>
                <input
                    name="nome"
                    value={formulario.nome}
                    onChange={atualizarCampo}
                    required
                />
            </div>

            <div>
                <label>CPF</label>
                <input
                    name="cpf"
                    value={formulario.cpf}
                    onChange={atualizarCampo}
                    required
                />
            </div>

            <div>
                <label>Email</label>
                <input
                    name="email"
                    type="email"
                    value={formulario.email}
                    onChange={atualizarCampo}
                    required
                />
            </div>

            <div>
                <label>Senha</label>
                <input
                    name="senha"
                    type="password"
                    value={formulario.senha}
                    onChange={atualizarCampo}
                    required
                />
            </div>

            <button type="submit" disabled={enviando}>
                {enviando ? 'Cadastrando...' : 'Cadastrar'}
            </button>

            {mensagem && (
                <p style={{ color: mensagem.tipo === 'sucesso' ? 'green' : 'red' }}>
                    {mensagem.texto}
                </p>
            )}
        </form>
    );
}

export default FormularioCliente;