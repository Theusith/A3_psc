import { useState, useEffect } from 'react';
import { cadastrarCliente, atualizarCliente } from '../services/api';

function FormularioCliente({ clienteParaEditar, aoSalvar, aoCancelar }) {
    const [formulario, setFormulario] = useState({
        nome: '',
        cpf: '',
        email: '',
        senha: '',
    });
    const [enviando, setEnviando] = useState(false);
    const [mensagem, setMensagem] = useState(null);

    const modoEdicao = Boolean(clienteParaEditar);

    useEffect(() => {
        if (clienteParaEditar) {
            setFormulario({
                nome: clienteParaEditar.nome || '',
                cpf: clienteParaEditar.cpf || '',
                email: clienteParaEditar.email || '',
                senha: '',
            });
        }
    }, [clienteParaEditar]);

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
            if (modoEdicao) {
                await atualizarCliente(clienteParaEditar.id, formulario);
                setMensagem({ tipo: 'sucesso', texto: 'Cliente atualizado com sucesso!' });
            } else {
                await cadastrarCliente(formulario);
                setMensagem({ tipo: 'sucesso', texto: 'Cliente cadastrado com sucesso!' });
                setFormulario({ nome: '', cpf: '', email: '', senha: '' });
            }
            if (aoSalvar) {
                aoSalvar();
            }
        } catch (erro) {
            setMensagem({ tipo: 'erro', texto: 'Não foi possível salvar o cliente.' });
        } finally {
            setEnviando(false);
        }
    }

    return (
        <form onSubmit={enviarFormulario}>
            <h2>{modoEdicao ? 'Editar cliente' : 'Cadastrar cliente'}</h2>

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
                <label>Senha {modoEdicao && '(deixe em branco para manter a atual)'}</label>
                <input
                    name="senha"
                    type="password"
                    value={formulario.senha}
                    onChange={atualizarCampo}
                    required={!modoEdicao}
                />
            </div>

            <button type="submit" disabled={enviando}>
                {enviando ? 'Salvando...' : modoEdicao ? 'Atualizar' : 'Cadastrar'}
            </button>

            {modoEdicao && (
                <button type="button" onClick={aoCancelar}>
                    Cancelar
                </button>
            )}

            {mensagem && (
                <p style={{ color: mensagem.tipo === 'sucesso' ? 'green' : 'red' }}>
                    {mensagem.texto}
                </p>
            )}
        </form>
    );
}

export default FormularioCliente;