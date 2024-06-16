package Controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Conexao.Conexao;
import Model.Cliente;
import Model.Pessoa;

/**
 * Classe responsável por gerenciar operações relacionadas a usuários do sistema.
 */
public class GerenciadorContas {
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
        Connection conn = null;
        PreparedStatement psDados = null;
        PreparedStatement psVerificarSenha = null; // Para verificar se a senha já existe

        String sqlDados = "INSERT INTO USUARIOS (NOME, CPF, EMAIL, SENHA, TIPO) VALUES (?, ?, ?, ?, ?)";
        String sqlVerificarSenha = "SELECT COUNT(*) FROM USUARIOS WHERE SENHA = ?"; // Consulta para verificar a senha


        try {
            conn = Conexao.getConexao();
            conn.setAutoCommit(false); // Desabilita autocommit para transação

            psVerificarSenha = conn.prepareStatement(sqlVerificarSenha);
            psVerificarSenha.setString(1, usuario.getSenha());
            ResultSet rs = psVerificarSenha.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count > 0) {
                System.out.println("Senha já utilizada por outro usuário. Escolha outra senha.");
                return;
            }else{
                // Inserção na tabela USUARIO
                psDados = conn.prepareStatement(sqlDados, PreparedStatement.RETURN_GENERATED_KEYS);
                psDados.setString(1, usuario.getNome());
                psDados.setString(2, usuario.getCpf());
                psDados.setString(3, usuario.getEmail());
                psDados.setString(4, usuario.getSenha());
                psDados.setString(5, usuario.getTipo());
                psDados.executeUpdate();
                System.out.println("Usuário cadastrado com sucesso!");
            }

            conn.commit(); // Commit da transação

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback em caso de erro
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            try {
                if (psDados != null) psDados.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Adiciona um administrador padrão ao sistema, caso não exista.
     */
    public void adicionarAdministradorPadrao() {
        boolean adicionado = false;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            Connection conexao = Conexao.getConexao(); // Obtém a conexão existente
            String sql = "SELECT * FROM usuarios WHERE tipo = 'Administrador' "; // Sua consulta SQL
            stmt = conexao.prepareStatement(sql);
            rs = stmt.executeQuery();

            if (!rs.next()) {
                Pessoa ps = new Pessoa("Administrador", "000000000000", "Administrador@adm.com", "Administrador");
                String tipo = "Administrador";
                ps.setMatricula("01");
                ps.setTipo(tipo);
                Connection conn = null;
                PreparedStatement psDados = null;

                String sqlDados = "INSERT INTO USUARIOS (NOME, CPF, EMAIL, SENHA, TIPO) VALUES (?, ?, ?, ?, ?)";

                try {
                    conn = Conexao.getConexao();
                    conn.setAutoCommit(false); // Desabilita autocommit para transação

                    // Inserção na tabela USUARIO
                    psDados = conn.prepareStatement(sqlDados, PreparedStatement.RETURN_GENERATED_KEYS);
                    psDados.setString(1, ps.getNome());
                    psDados.setString(2, ps.getCpf());
                    psDados.setString(3, ps.getEmail());
                    psDados.setString(4, ps.getSenha());
                    psDados.setString(5, ps.getTipo());
                    psDados.executeUpdate();
                    System.out.println("Administrador Cadastrado com sucesso!");

                    conn.commit(); // Commit da transação

                } catch (SQLException e) {
                    if (conn != null) {
                        try {
                            conn.rollback(); // Rollback em caso de erro
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                    e.printStackTrace();
                } finally {
                    try {
                        if (psDados != null) psDados.close();
                        if (conn != null) conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Fechar recursos (ResultSet, PreparedStatement) aqui
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
    /**
     * Autentica uma pessoa no sistema.
     *
     * @param email Email da pessoa.
     * @param senha Senha da pessoa.
     * @return Objeto Pessoa se autenticado com sucesso, ou null se não encontrado.
     */
    public Pessoa autenticarPessoa (String email, String senha){
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Pessoa cl = null;

        try {
            Connection conexao = Conexao.getConexao();
            String sql = "SELECT * FROM usuarios WHERE email = ? and senha = ?";
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, senha);
            rs = stmt.executeQuery();

            if (rs.next()) {
                int id1 = rs.getInt("ID");
                String nome = rs.getString("NOME");
                String cpf = rs.getString("CPF");
                email = rs.getString("EMAIL");
                senha = rs.getString("SENHA");
                String matricula = rs.getString("MATRICULA");
                String tipo = rs.getString("TIPO");
                cl = new Pessoa(nome, cpf, email, senha);
                cl.setId(id1);
                cl.setTipo(tipo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Fechar recursos (ResultSet, PreparedStatement) aqui
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cl; // Pessoa não encontrada ou senha incorreta
    }
    /**
     * Obtém um cliente do sistema pelo ID.
     *
     * @param id ID do cliente a ser obtido.
     * @return Objeto Cliente se encontrado, ou null se não encontrado.
     */
    public Cliente obterClientePorId ( int id){
        Cliente cliente = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            Connection conexao = Conexao.getConexao(); // Obtém a conexão existente
            String sql = "SELECT * FROM usuarios WHERE id = ?"; // Sua consulta SQL
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                String id1 = rs.getString("id");
                String nome = rs.getString("NOME");
                String cpf = rs.getString("CPF");
                String email = rs.getString("EMAIL");
                String senha = rs.getString("SENHA");
                cliente = new Cliente(nome, cpf, email, senha);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Fechar recursos (ResultSet, PreparedStatement) aqui
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cliente;
    }
    /**
     * Deleta um cliente do sistema pelo ID.
     *
     * @param idCliente ID do cliente a ser deletado.
     */
    public void deletarClientePorId(int idCliente) {
        String sql = "DELETE FROM usuarios WHERE id = ? AND tipo = 'Cliente'"; // Verifica se é cliente

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Cliente deletado com sucesso!");
            } else {
                System.out.println("Cliente não encontrado ou não é um cliente.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao deletar cliente: " + e.getMessage());
            // Tratar o erro de forma mais adequada (rollback, log, etc.)
        }
    }
    /**
     * Altera os dados de um cliente pelo ID.
     *
     * @param idCliente  ID do cliente a ser alterado.
     * @param novosDados Novos dados do cliente.
     */
    public void alterarClientePorId(int idCliente, Cliente novosDados) {
        String sql = "UPDATE usuarios SET nome = ?, cpf = ?, email = ?, senha = ? WHERE id = ? AND tipo = 'Cliente'";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, novosDados.getNome());
            stmt.setString(2, novosDados.getCpf());
            stmt.setString(3, novosDados.getEmail());
            stmt.setString(4, novosDados.getSenha());
            stmt.setInt(5, idCliente);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Dados do cliente atualizados com sucesso!");
            } else {
                System.out.println("Cliente não encontrado ou não é um cliente.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar dados do cliente: " + e.getMessage());
            // Tratar o erro de forma mais adequada (rollback, log, etc.)
        }
    }
    /**
     * Lista todos os clientes cadastrados no sistema.
     *
     * @return Lista de objetos Cliente.
     */
    public List<Cliente> visualizarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE tipo = 'Cliente'";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                int id = (rs.getInt("id"));
                String nome = (rs.getString("nome"));
                String cpf = (rs.getString("cpf"));
                String email = (rs.getString("email"));

                Cliente cliente = new Cliente(nome, cpf, email, "0");
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setEmail(rs.getString("email"));
                clientes.add(cliente);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao visualizar clientes: " + e.getMessage());
            // Tratar o erro de forma mais adequada (rollback, log, etc.)
        }

        return clientes;
    }

}


