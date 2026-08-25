package Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Conexao.Conexao;
import Model.Cliente;
import Model.Pessoa;

/**
 * Classe responsável por toda a comunicação com o banco de dados
 * relacionada à tabela USUARIOS (contas de clientes e administradores).
 */
public class ContaRepository {

    public boolean senhaJaExiste(String senha) throws SQLException {
        String sql = "SELECT COUNT(*) FROM USUARIOS WHERE SENHA = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, senha);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        }
    }

    public void inserirUsuario(String nome, String cpf, String email, String senha, String tipo) throws SQLException {
        String sql = "INSERT INTO USUARIOS (NOME, CPF, EMAIL, SENHA, TIPO) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, nome);
            ps.setString(2, cpf);
            ps.setString(3, email);
            ps.setString(4, senha);
            ps.setString(5, tipo);
            ps.executeUpdate();
        }
    }

    public Pessoa buscarPorEmailSenha(String email, String senha) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND senha = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, senha);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Pessoa p = new Pessoa(rs.getString("NOME"), rs.getString("CPF"), rs.getString("EMAIL"), rs.getString("SENHA"));
                p.setId(rs.getInt("ID"));
                p.setTipo(rs.getString("TIPO"));
                return p;
            }
            return null;
        }
    }

    public Cliente buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Cliente(rs.getString("NOME"), rs.getString("CPF"), rs.getString("EMAIL"), rs.getString("SENHA"));
            }
            return null;
        }
    }

    public boolean deletarPorId(int id) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id = ? AND tipo = 'Cliente'";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean atualizarCliente(int id, Cliente dados) throws SQLException {
        String sql = "UPDATE usuarios SET nome = ?, cpf = ?, email = ?, senha = ? WHERE id = ? AND tipo = 'Cliente'";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dados.getNome());
            stmt.setString(2, dados.getCpf());
            stmt.setString(3, dados.getEmail());
            stmt.setString(4, dados.getSenha());
            stmt.setInt(5, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<Cliente> listarTodos() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE tipo = 'Cliente'";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getString("nome"), rs.getString("cpf"), rs.getString("email"), "0"
                );
                cliente.setId(rs.getInt("id"));
                clientes.add(cliente);
            }
        }
        return clientes;
    }

    public boolean existeAdministrador() throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE tipo = 'Administrador'";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            return rs.next();
        }
    }
}