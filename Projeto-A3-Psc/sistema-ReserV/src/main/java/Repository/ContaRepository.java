package Repository;

import java.sql.*;
import Conexao.Conexao;
import Model.Cliente;
import Model.Pessoa;

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
}