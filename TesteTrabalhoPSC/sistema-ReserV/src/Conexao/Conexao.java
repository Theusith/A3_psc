package Conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Conexao {

    private static final String URL = "jdbc:mysql://localhost:3306/testedb";
    private static final String USUARIO = "root";
    private static final String SENHA = "54678";

    private static Connection conexao = null;

    private Conexao() {
        // Construtor privado para evitar instanciação direta
    }
    public static Connection getConexao() throws SQLException {
        if (conexao == null || conexao.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            } catch (ClassNotFoundException e) {
                throw new SQLException("Driver JDBC não encontrado.", e);
            }
        }
        return conexao;
    }

    public static PreparedStatement prepareStatement(String sql) throws SQLException {
        return getConexao().prepareStatement(sql);
    }
}
