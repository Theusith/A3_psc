package Service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal que inicia a aplicação como uma API REST.
 * Ao rodar esta classe, o Spring Boot sobe um servidor web embutido
 * (por padrão em http://localhost:8080) e expõe os endpoints REST
 * definidos nas classes anotadas com @RestController.
 */
@SpringBootApplication(scanBasePackages = {"Service", "Controller", "Repository", "Model", "Conexao"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}