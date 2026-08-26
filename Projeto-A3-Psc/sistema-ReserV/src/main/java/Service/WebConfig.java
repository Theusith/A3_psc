package Service;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuração de CORS (Cross-Origin Resource Sharing).
 *
 * Por padrão, navegadores bloqueiam requisições feitas via JavaScript de uma
 * origem (ex: http://localhost:3000, onde rodaria uma interface gráfica)
 * para outra origem (ex: http://localhost:8080, onde roda esta API).
 *
 * Esta classe libera explicitamente essa comunicação, permitindo que uma
 * interface gráfica (React, Vue, HTML simples, etc.) consuma os endpoints
 * desta API a partir do navegador.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }
}