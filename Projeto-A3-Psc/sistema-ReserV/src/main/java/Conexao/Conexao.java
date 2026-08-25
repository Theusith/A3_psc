/**
 * Classe para gerenciar a conexão com o banco de dados PostgreSQL.
 */
package Conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * A classe Conexao fornece métodos estáticos para obter uma conexão com o banco de dados PostgreSQL
 * e criar objetos PreparedStatement para consultas SQL parametrizadas.
 *
 * As credenciais são configuradas em tempo de execução pela classe DatabaseConfig,
 * que lê os valores do application.properties. Isso evita senhas escritas
 * diretamente no código-fonte.
 */
public class Conexao {

    private static String url = "jdbc:postgresql://localhost:5432/sistema_reserv";
    private static String usuario = "sith";
    private static String senha = "";

    private static Connection conexao = null;

    private Conexao() {
        // Construtor privado para evitar instanciação direta
    }

    /**
     * Define as credenciais de conexão. Chamado automaticamente pela
     * classe DatabaseConfig na inicialização da aplicação Spring Boot.
     *
     * @param novaUrl     URL JDBC de conexão.
     * @param novoUsuario Usuário do banco de dados.
     * @param novaSenha   Senha do banco de dados.
     */
    public static void configurar(String novaUrl, String novoUsuario, String novaSenha) {
        url = novaUrl;
        usuario = novoUsuario;
        senha = novaSenha;
        conexao = null; // força nova conexão com as credenciais atualizadas
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
                Class.forName("org.postgresql.Driver");
                conexao = DriverManager.getConnection(url, usuario, senha);
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