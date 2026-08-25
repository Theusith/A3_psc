/**
 * Classe para gerenciar a conexão com o banco de dados MySQL.
 */
package Conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * A classe Conexao fornece métodos estáticos para obter uma conexão com o banco de dados MySQL
 * e criar objetos PreparedStatement para consultas SQL parametrizadas.
 */
public class Conexao {

    private static final String URL = "jdbc:mysql://localhost:3306/testedb";
    private static final String USUARIO = "root";
    private static final String SENHA = "54678";

    private static Connection conexao = null;

    private Conexao() {
        // Construtor privado para evitar instanciação direta
    }

    /**
     * Obtém a conexão com o banco de dados.
     *
     * @return A conexão ativa com o banco de dados.
     * @throws SQLException Se ocorrer um erro ao conectar ao banco de dados.
     */

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
    /**
     * Cria um objeto PreparedStatement para a consulta SQL fornecida.
     *
     * @param sql A consulta SQL a ser preparada.
     * @return Um PreparedStatement configurado com a consulta fornecida.
     * @throws SQLException Se ocorrer um erro ao preparar o statement SQL.
     */

    public static PreparedStatement prepareStatement(String sql) throws SQLException {
        return getConexao().prepareStatement(sql);
    }
}
