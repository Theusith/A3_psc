package Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Model.Cliente;
import Model.Pessoa;
import Repository.ContaRepository;

/**
 * Classe responsável por gerenciar operações relacionadas a usuários do sistema.
 */
public class GerenciadorContas {

    private ContaRepository repository = new ContaRepository();

    /**
     * Construtor da classe. Adiciona um administrador padrão ao ser instanciada.
     */
    public GerenciadorContas() {
        adicionarAdministradorPadrao();
    }

    /**
     * Cadastra um novo cliente no sistema.
     *
     * @param usuario Objeto Cliente a ser cadastrado.
     */
    public void cadastrarCliente(Cliente usuario) {
        try {
            if (repository.senhaJaExiste(usuario.getSenha())) {
                System.out.println("Senha já utilizada por outro usuário. Escolha outra senha.");
                return;
            }
            repository.inserirUsuario(usuario.getNome(), usuario.getCpf(), usuario.getEmail(), usuario.getSenha(), usuario.getTipo());
            System.out.println("Usuário cadastrado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adiciona um administrador padrão ao sistema, caso não exista.
     */
    public void adicionarAdministradorPadrao() {
        try {
            if (repository.existeAdministrador()) {
                return;
            }
            Pessoa ps = new Pessoa("Administrador", "000000000000", "Administrador@adm.com", "Administrador");
            ps.setMatricula("01");
            ps.setTipo("Administrador");

            repository.inserirUsuario(ps.getNome(), ps.getCpf(), ps.getEmail(), ps.getSenha(), ps.getTipo());
            System.out.println("Administrador Cadastrado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Autentica uma pessoa no sistema.
     *
     * @param email Email da pessoa.
     * @param senha Senha da pessoa.
     * @return Objeto Pessoa se autenticado com sucesso, ou null se não encontrado.
     */
    public Pessoa autenticarPessoa(String email, String senha) {
        try {
            return repository.buscarPorEmailSenha(email, senha);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Obtém um cliente do sistema pelo ID.
     *
     * @param id ID do cliente a ser obtido.
     * @return Objeto Cliente se encontrado, ou null se não encontrado.
     */
    public Cliente obterClientePorId(int id) {
        try {
            return repository.buscarPorId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Deleta um cliente do sistema pelo ID.
     *
     * @param idCliente ID do cliente a ser deletado.
     */
    public void deletarClientePorId(int idCliente) {
        try {
            boolean sucesso = repository.deletarPorId(idCliente);
            if (sucesso) {
                System.out.println("Cliente deletado com sucesso!");
            } else {
                System.out.println("Cliente não encontrado ou não é um cliente.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao deletar cliente: " + e.getMessage());
        }
    }

    /**
     * Altera os dados de um cliente pelo ID.
     *
     * @param idCliente  ID do cliente a ser alterado.
     * @param novosDados Novos dados do cliente.
     */
    public void alterarClientePorId(int idCliente, Cliente novosDados) {
        try {
            boolean sucesso = repository.atualizarCliente(idCliente, novosDados);
            if (sucesso) {
                System.out.println("Dados do cliente atualizados com sucesso!");
            } else {
                System.out.println("Cliente não encontrado ou não é um cliente.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar dados do cliente: " + e.getMessage());
        }
    }

    /**
     * Lista todos os clientes cadastrados no sistema.
     *
     * @return Lista de objetos Cliente.
     */
    public List<Cliente> visualizarClientes() {
        try {
            return repository.listarTodos();
        } catch (SQLException e) {
            System.err.println("Erro ao visualizar clientes: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}