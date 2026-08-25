package Conexao;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Classe responsável por ler as credenciais do banco de dados a partir do
 * application.properties e repassá-las para a classe Conexao na
 * inicialização da aplicação.
 *
 * Isso permite manter a classe Conexao simples (métodos estáticos, sem
 * depender do Spring diretamente) enquanto a senha real fica fora do
 * código-fonte.
 */
@Component
public class DatabaseConfig {

    @Value("${db.url}")
    private String url;

    @Value("${db.usuario}")
    private String usuario;

    @Value("${db.senha}")
    private String senha;

    @PostConstruct
    public void inicializar() {
        Conexao.configurar(url, usuario, senha);
    }
}